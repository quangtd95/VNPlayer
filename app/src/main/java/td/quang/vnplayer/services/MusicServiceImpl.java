package td.quang.vnplayer.services;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import td.quang.vnplayer.broadcasts.BroadCastToUI;
import td.quang.vnplayer.broadcasts.BroadcastToService;
import td.quang.vnplayer.models.databases.PlayListManager;
import td.quang.vnplayer.models.databases.PlayListManagerImpl;
import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.views.notifications.SongNotification;

public class MusicServiceImpl extends Service implements MusicService, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    private MediaPlayer mMediaPlayer;
    private boolean mIsPlaying;
    private Thread mThreadUpdateSeekbar;
    private int mCurrentTime;
    private Song mCurrentSong;
    private boolean mIsRepeat;
    private boolean mIsShuffle;

    private SongNotification mSongNotification;
    private PlayListManager mPlayListManager;
    private LinkedList<Song> mPlayList;
    private LinkedList<Integer> mRandomPosition;
    private int mCurrentPosition;
    private Handler mHandler;

    private boolean b;

    @Override
    public void onCreate() {
        super.onCreate();
        BroadcastToService mBroadcastToService = new BroadcastToService();
        mBroadcastToService.setMusicService(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadcastToService.ACTION_PLAY);
        filter.addAction(BroadcastToService.ACTION_STOP);
        filter.addAction(BroadcastToService.ACTION_PAUSE);
        filter.addAction(BroadcastToService.ACTION_RESUME);
        filter.addAction(BroadcastToService.ACTION_SEEK);
        filter.addAction(BroadcastToService.ACTION_REPEAT);
        filter.addAction(BroadcastToService.ACTION_SHUFFLE);
        filter.addAction(BroadcastToService.ACTION_GET_CURRENT_STATE);
        filter.addAction(BroadcastToService.ACTION_NEW_PLAYLIST);
        filter.addAction(BroadcastToService.ACTION_NEXT);
        filter.addAction(BroadcastToService.ACTION_REMOVE_IN_PLAYLIST);
        filter.addAction(BroadcastToService.ACTION_PREV);
        filter.addAction(BroadcastToService.ACTION_SCHEDULE);
        registerReceiver(mBroadcastToService, filter);

        mSongNotification = SongNotification.getInstance();
        mPlayListManager = PlayListManagerImpl.getInstance();
        mPlayList = new LinkedList<>();
        mRandomPosition = new LinkedList<>();

    }

    @Nullable @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void play(int position) {
        mCurrentSong = mPlayList.get(position);
        mCurrentPosition = position;
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setWakeMode(getApplication(), PowerManager.PARTIAL_WAKE_LOCK);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        } else {
            mMediaPlayer.reset();
        }
        try {
            mMediaPlayer.setDataSource(getApplicationContext(), mCurrentSong.getSource());
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setOnCompletionListener(this);
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            Log.e("TAGG", "Loi o  day" + e.getMessage() + e.getCause() + e.getStackTrace());

        }
        mIsPlaying = true;

        if (!mSongNotification.isShow()) mSongNotification.showNotification(this, mCurrentSong);
        else {
            mSongNotification.updateNotification(this, mCurrentSong);
        }
        mSongNotification.updateNotification(this, false);
        updateUI(mCurrentSong, position, !mIsPlaying);
    }

    @Override public void stop() {
        mMediaPlayer.stop();
        mMediaPlayer.release();
        mMediaPlayer = null;
        mIsPlaying = false;
        mSongNotification.updateNotification(this, true);
    }

    @Override
    public void resume() {
        if (!mIsPlaying) {
            mMediaPlayer.start();
            mSongNotification.updateNotification(this, false);
        }
        mIsPlaying = true;
        updateUI(mCurrentSong, mCurrentPosition, false);
    }

    @Override
    public void pause() {
        if (mIsPlaying) {
            mMediaPlayer.pause();
            mSongNotification.updateNotification(this, true);
            mIsPlaying = false;
            updateUI(mCurrentSong, mCurrentPosition, true);
        }
    }

    @Override
    public void next() {
        if (mIsShuffle) {
            mCurrentPosition = new Random().nextInt(mPlayList.size());
            mRandomPosition.add(mCurrentPosition);
        } else {
            mCurrentPosition++;
            if (mCurrentPosition == mPlayList.size()) {
                mCurrentPosition = 0;
            }
        }

        play(mCurrentPosition);
        updateUI(mCurrentSong, mCurrentPosition, false);
    }

    @Override
    public void prev() {
        if (mIsShuffle) {
            if (mRandomPosition.size() >= 2) {
                mRandomPosition.removeLast();
            }
            mCurrentPosition = mRandomPosition.peekLast();
        } else {
            mCurrentPosition--;
            if (mCurrentPosition == -1) {
                mCurrentPosition = 0;
            }
        }

        play(mCurrentPosition);
        updateUI(mCurrentSong, mCurrentPosition, false);
    }

    @Override
    public void seek(int position) {
        mMediaPlayer.seekTo(position);
    }

    private void updateUI(Song song, int position, boolean isPause) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("song", song);
        bundle.putInt("position", position);
        bundle.putBoolean("pause", isPause);
        sendToUI(bundle, BroadCastToUI.ACTION_UPDATE_SONG);
    }

    @Override
    public void setRepeat(boolean mIsRepeat) {
        this.mIsRepeat = mIsRepeat;
    }

    @Override public void setShuffle(boolean mIsShuffle) {
        this.mIsShuffle = mIsShuffle;
        if (!mIsShuffle) {
            mRandomPosition.clear();
            mRandomPosition.add(mCurrentPosition);
        }
    }

    @Override public void getCurrentState() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("shuffle", mIsShuffle);
        bundle.putBoolean("repeat", mIsRepeat);
        bundle.putBoolean("playing", mIsPlaying);
        bundle.putParcelable("song", mCurrentSong);
        bundle.putInt("position", mCurrentPosition);
        bundle.putInt("time", mCurrentTime);
        sendToUI(bundle, BroadCastToUI.ACTION_RECEIVE_CURRENT_STATE);
    }

    @Override
    public void getPlaylist(int position) {
        mCurrentPosition = position;
        mPlayList.clear();
        mPlayList.addAll(mPlayListManager.getPlaylist());
        play(mCurrentPosition);
    }

    @Override public void removeInPlayList(int position) {
        mPlayList.remove(position);
        if (mCurrentPosition == position) {
            mCurrentPosition--;
            if (mCurrentPosition <= 0) mCurrentPosition = 0;
            if (mCurrentPosition >= mPlayList.size()) mCurrentPosition = mPlayList.size() - 1;

            play(mCurrentPosition);
        } else {
            if (mCurrentPosition > position) {
                mCurrentPosition--;
            }
        }
        updateUI(mCurrentSong, mCurrentPosition, !mIsPlaying);
    }

    @Override public void setSchedule(int minutes) {
        if (mHandler == null) {
            mHandler = new Handler();
        } else {
            mHandler.removeCallbacksAndMessages(null);
        }
        if (minutes != 0) {
            mHandler.postDelayed(() -> pause(), minutes * 60 * 1000);
        } else {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mIsRepeat) {
            mp.seekTo(0);
            mp.start();
        } else {
            Intent intent = new Intent();
            intent.setAction(BroadCastToUI.ACTION_COMPLETE);
            intent.putExtra("shuffle", mIsShuffle);
            sendBroadcast(intent);
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        if (mThreadUpdateSeekbar == null) {
            mThreadUpdateSeekbar = new Thread(() -> {
                int mDuration = mp.getDuration();
                while (mCurrentTime < mDuration) {
                    Intent intent = new Intent();
                    intent.setAction(BroadCastToUI.ACTION_UPDATE_TIME);
                    mCurrentTime = mp.getCurrentPosition();

                    intent.putExtra("currentTime", mCurrentTime);
                    if (mIsPlaying) {
                        intent.putExtra("visible", View.VISIBLE);
                    } else {
                        intent.putExtra("visible", b ? View.VISIBLE : View.INVISIBLE);
                        b = !b;
                    }
                    sendBroadcast(intent);

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        Log.e("TAGG", "lỗi thread duration ");
                    }
                }
            });
            mThreadUpdateSeekbar.start();
        }
        if (mThreadUpdateSeekbar.getState() == Thread.State.TERMINATED) {
            mThreadUpdateSeekbar = null;
        }

    }

    private void sendToUI(Bundle bundle, String action) {
        Intent intent = new Intent();
        intent.setAction(action);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        sendBroadcast(intent);

    }
}