package td.quang.vnplayer.services;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import java.io.IOException;

import td.quang.vnplayer.broadcasts.ControlMusicBroadcast;
import td.quang.vnplayer.broadcasts.MusicServiceReceiver;
import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.views.notifications.SongNotification;

/**
 * Created by djwag on 1/9/2017.
 */

public class MusicServiceImpl extends Service implements MusicService, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    private ControlMusicBroadcast controlMusicBroadcast;
    private MediaPlayer mMediaPlayer;
    private boolean mIsPlaying;
    private Thread threadUpdateSeekbar;
    private int mCurrentPosition;
    private Song mCurrentSong;
    private boolean mIsRepeat;
    private boolean b;
    private boolean mIsShuffle;
    private SongNotification mSongNotification;


    @Override
    public void onCreate() {
        super.onCreate();
        controlMusicBroadcast = new ControlMusicBroadcast();
        controlMusicBroadcast.setMusicService(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ControlMusicBroadcast.ACTION_PLAY);
        filter.addAction(ControlMusicBroadcast.ACTION_STOP);
        filter.addAction(ControlMusicBroadcast.ACTION_PAUSE);
        filter.addAction(ControlMusicBroadcast.ACTION_RESUME);
        filter.addAction(ControlMusicBroadcast.ACTION_SEEK);
        filter.addAction(ControlMusicBroadcast.ACTION_REPEAT);
        filter.addAction(ControlMusicBroadcast.ACTION_SHUFFLE);
        filter.addAction(ControlMusicBroadcast.ACTION_GET_CURRENT_STATE);
        registerReceiver(controlMusicBroadcast, filter);
        mSongNotification = SongNotification.getInstance();
    }

    @Nullable @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void play(Song song) {
        mCurrentSong = song;
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setWakeMode(getApplication(), PowerManager.PARTIAL_WAKE_LOCK);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        } else {
            mMediaPlayer.reset();
        }

        try {
            mMediaPlayer.setDataSource(getApplicationContext(), song.getSource());
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setOnCompletionListener(this);
            mMediaPlayer.prepare();
        } catch (IOException e) {
            Log.e("TAGG", "Music service setDatasource", e);
        }
        mIsPlaying = true;

        if (!mSongNotification.isShow()) mSongNotification.showNotification(this, song);
        else {
            mSongNotification.updateNotification(this, song);
        }
        mSongNotification.updateNotification(this, false);
    }

    @Override public void stop() {
        mMediaPlayer.stop();
        mMediaPlayer.release();
        mMediaPlayer = null;
        mIsPlaying = false;
        mSongNotification.updateNotification(this, true);
    }

    @Override public void resume() {
        if (!mIsPlaying) {
            mMediaPlayer.start();
            mSongNotification.updateNotification(this, false);
        }
        mIsPlaying = true;
    }

    @Override public void pause() {
        if (mIsPlaying) {
            mMediaPlayer.pause();
            mSongNotification.updateNotification(this, true);
        }
        mIsPlaying = false;
    }

    @Override
    public void seek(int position) {
        mMediaPlayer.seekTo(position);

    }

    @Override
    public void setRepeat(boolean b) {
        mIsRepeat = b;
    }

    @Override public void setShuffle(boolean b) {
        mIsShuffle = b;
    }

    @Override public void getCurrentState() {
        Intent intent = new Intent();
        intent.setAction(MusicServiceReceiver.ACTION_RECEIVE_CURRENT_STATE);
        intent.putExtra("shuffle", mIsShuffle);
        intent.putExtra("repeat", mIsRepeat);
        intent.putExtra("playing", mIsPlaying);
        intent.putExtra("song", mCurrentSong);
        intent.putExtra("position", mCurrentPosition);
        sendBroadcast(intent);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mIsRepeat) {
            mp.seekTo(0);
            mp.start();
        } else {
            Intent intent = new Intent();
            intent.setAction(MusicServiceReceiver.ACTION_COMPLETE);
            intent.putExtra("shuffle", mIsShuffle);
            sendBroadcast(intent);
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        if (threadUpdateSeekbar == null) {
            threadUpdateSeekbar = new Thread(() -> {
                int mDuration = mp.getDuration();
                while (mCurrentPosition < mDuration) {
                    Intent intent = new Intent();
                    intent.setAction(MusicServiceReceiver.ACTION_UPDATE_TIME);
                    mCurrentPosition = mp.getCurrentPosition();
                    intent.putExtra("currentTime", mCurrentPosition);
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
                    }
                }
            });
            threadUpdateSeekbar.start();
        }
        if (threadUpdateSeekbar.isInterrupted()) {
            threadUpdateSeekbar = null;
        }

    }
}