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
    public static final String ACTION_PLAY = "PLAY";
    public static final String ACTION_STOP = "STOP";
    public static final String ACTION_RESUME = "RESUME";
    public static final String ACTION_PAUSE = "PAUSE";
    private MusicService musicService;

    public void setMusicService(MusicService musicService) {
        this.musicService = musicService;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("TAGG", "onreceive");
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
    }

    private void onPauseAction(Intent intent) {
        musicService.pause();
    }

    private void onResumeAction(Intent intent) {
        musicService.resume();
    }


    private void onPlayAction(Intent intent) {
        Song song = intent.getExtras().getParcelable("data");
        musicService.play(song);

    }

    private void onStopAction(Intent intent) {
        musicService.stop();
    }
}