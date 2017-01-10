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

import java.io.IOException;

import td.quang.vnplayer.broadcasts.ControlMusicBroadcast;
import td.quang.vnplayer.models.objects.Song;

/**
 * Created by djwag on 1/9/2017.
 */

public class MusicServiceImpl extends Service implements MusicService, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    private ControlMusicBroadcast controlMusicBroadcast;
    private MediaPlayer mMediaPlayer;
    private boolean mIsPlaying;


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
        registerReceiver(controlMusicBroadcast, filter);
    }

    @Nullable @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void play(Song song) {


        Log.e("TAGG", "Play service");
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setWakeMode(getApplication(), PowerManager.PARTIAL_WAKE_LOCK);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        } else {
            mMediaPlayer.reset();
        }

        try {
            if (song == null) Log.e("TAGG", "song null");
            Log.e("TAGG", String.valueOf(song.getFilePath()));
            mMediaPlayer.setDataSource(getApplicationContext(), song.getSource());
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mIsPlaying = true;
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnCompletionListener(this);
    }

    @Override public void stop() {
        mMediaPlayer.stop();
        mMediaPlayer.release();
        mMediaPlayer = null;
        mIsPlaying = false;

    }

    @Override public void resume() {
        if (!mIsPlaying) {
            mMediaPlayer.start();
        }
        mIsPlaying = true;
    }

    @Override public void pause() {
        if (mIsPlaying) {
            mMediaPlayer.pause();
        }
        mIsPlaying = false;
    }

    @Override public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }
}
