package td.quang.vnplayer.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.services.MusicService;

/**
 * Created by djwag on 1/9/2017.
 */
public class ControlMusicBroadcast extends BroadcastReceiver {
    public static final String ACTION_PLAY = "TD.QUANG.VNPLAYER.CONTROLMUSICBROADCAST.PLAY";
    public static final String ACTION_STOP = "TD.QUANG.VNPLAYER.CONTROLMUSICBROADCAST.STOP";
    public static final String ACTION_RESUME = "TD.QUANG.VNPLAYER.CONTROLMUSICBROADCAST.RESUME";
    public static final String ACTION_PAUSE = "TD.QUANG.VNPLAYER.CONTROLMUSICBROADCAST.PAUSE";
    public static final String ACTION_SEEK = "TD.QUANG.VNPLAYER.CONTROLMUSICBROADCAST.SEEK";
    public static final String ACTION_REPEAT = "TD.QUANG.VNPLAYER.CONTROLMUSICBROADCAST.REPEAT";
    public static final String ACTION_SHUFFLE = "TD.QUANG.VNPLAYER.CONTROLMUSICBROADCAST.SUFFLE";

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
        if (action.equalsIgnoreCase(ACTION_SEEK)) {
            onSeekAction(intent);
        }
        if (action.equalsIgnoreCase(ACTION_REPEAT)) {
            onRepeatAction(intent);
        }
        if (action.equalsIgnoreCase(ACTION_SHUFFLE)) {
            onShuffleAction(intent);
        }

    }


    private void onShuffleAction(Intent intent) {
        boolean b = intent.getBooleanExtra("shuffle", false);
        musicService.setShuffle(b);
    }

    private void onRepeatAction(Intent intent) {
        boolean b = intent.getBooleanExtra("repeat", false);
        musicService.setRepeat(b);
    }

    private void onSeekAction(Intent intent) {
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
        musicService.play(song);

    }

    private void onStopAction(Intent intent) {
        musicService.stop();
    }
}