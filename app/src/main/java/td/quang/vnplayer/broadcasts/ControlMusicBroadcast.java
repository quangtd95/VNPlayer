package td.quang.vnplayer.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.services.MusicService;

/**
 * Created by djwag on 1/9/2017.
 */
public class ControlMusicBroadcast extends BroadcastReceiver {
    public static final String ACTION_PLAY = "TD.QUANG.VNPLAYER.PLAY";
    public static final String ACTION_STOP = "TD.QUANG.VNPLAYER.STOP";
    public static final String ACTION_RESUME = "TD.QUANG.VNPLAYER.RESUME";
    public static final String ACTION_PAUSE = "TD.QUANG.VNPLAYER.PAUSE";
    public static final String ACTION_NEXT = "TD.QUANG.VNPLAYER.NEXT";
    public static final String ACTION_PREV = "TD.QUANG.VNPLAYER.PREV";
    public static final String ACTION_SEEK = "TD.QUANG.VNPLAYER.SEEK";

    private MusicService musicService;

    public void setMusicService(MusicService musicService) {
        this.musicService = musicService;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equalsIgnoreCase(ACTION_PLAY)) {
            onPlayAction(intent);
        }
        if (action.equalsIgnoreCase(ACTION_STOP)) {
            onStopAction(intent);
        }
        if (action.equalsIgnoreCase(ACTION_RESUME)) {
            onResumeAction(intent);
        }
        if (action.equalsIgnoreCase(ACTION_PAUSE)) {
            onPauseAction(intent);
        }
        if (action.equalsIgnoreCase(ACTION_NEXT)) {

        }
        if (action.equalsIgnoreCase(ACTION_PREV)) {

        }
        if (action.equalsIgnoreCase(ACTION_SEEK)) {
            onSeekAction(intent);
        }
    }

    private void onSeekAction(Intent intent) {
        Log.e("TAGG", "on seek action broadcast");
        int position = intent.getIntExtra("position", -1);
        musicService.seek(position);
    }
    private void onPauseAction(Intent intent) {
        musicService.pause();
    }

    private void onResumeAction(Intent intent) {
        musicService.resume();
    }


    private void onPlayAction(Intent intent) {
        Song song = intent.getExtras().getParcelable("data");
        Log.e("TAGG", "BroadCast + " + song.getTitle());
        musicService.play(song);

    }

    private void onStopAction(Intent intent) {
        musicService.stop();
    }
}