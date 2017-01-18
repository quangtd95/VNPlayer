package td.quang.vnplayer.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import td.quang.vnplayer.services.MusicService;

/**
 * Created by Quang_TD on 1/9/2017.
 */
public class BroadcastToService extends BroadcastReceiver {
    public static final String ACTION_PLAY = "TD.QUANG.VNPLAYER.CONTROLMUSICBROADCAST.PLAY";
    public static final String ACTION_STOP = "TD.QUANG.VNPLAYER.CONTROLMUSICBROADCAST.STOP";
    public static final String ACTION_RESUME = "TD.QUANG.VNPLAYER.CONTROLMUSICBROADCAST.RESUME";
    public static final String ACTION_PAUSE = "TD.QUANG.VNPLAYER.CONTROLMUSICBROADCAST.PAUSE";
    public static final String ACTION_SEEK = "TD.QUANG.VNPLAYER.CONTROLMUSICBROADCAST.SEEK";
    public static final String ACTION_REPEAT = "TD.QUANG.VNPLAYER.CONTROLMUSICBROADCAST.REPEAT";
    public static final String ACTION_SHUFFLE = "TD.QUANG.VNPLAYER.CONTROLMUSICBROADCAST.SUFFLE";
    public static final String ACTION_GET_CURRENT_STATE = "TD.QUANG.VNPLAYER.CONTROLMUSICBROADCAST.GET_CURRENT_STATE";
    public static final String ACTION_NEW_PLAYLIST = "TD.QUANG.VNPLAYER.CONTROLMUSICBROADCAST.NEW_PLAYLIST";
    public static final String ACTION_NEXT = "TD.QUANG.VNPLAYER.CONTROLMUSICBROADCAST.NEXT";
    public static final String ACTION_PREV = "TD.QUANG.VNPLAYER.CONTROLMUSICBROADCAST.PREV";
    public static final String ACTION_REMOVE_IN_PLAYLIST = "TD.QUANG.VNPLAYER.CONTROLMUSICBROADCAST.REMOVE_IN_PLAYLIST";
    public static final String ACTION_SCHEDULE = "TD.QUANG.VNPLAYER.CONTROLMUSICBROADCAST.SCHEDULE";

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
        if (action.equalsIgnoreCase(ACTION_GET_CURRENT_STATE)) {
            onGetCurrentStateAction(intent);
        }
        if (action.equalsIgnoreCase(ACTION_NEW_PLAYLIST)) {
            onNewPlayListAction(intent);
        }
        if (action.equalsIgnoreCase(ACTION_NEXT)) {
            onNextAction(intent);
        }
        if (action.equalsIgnoreCase(ACTION_PREV)) {
            onPrevAction(intent);
        }
        if (action.equalsIgnoreCase(ACTION_REMOVE_IN_PLAYLIST)) {
            onRemoveInPlayListAction(intent);
        }
        if (action.equalsIgnoreCase(ACTION_SCHEDULE)) {
            onScheduleAction(intent);
        }

    }

    private void onScheduleAction(Intent intent) {
        Bundle bundle = intent.getExtras();
        int minutes = bundle.getInt("minute");
        musicService.setSchedule(minutes);
    }

    private void onRemoveInPlayListAction(Intent intent) {
        Bundle bundle = intent.getExtras();
        int position = bundle.getInt("position");
        musicService.removeInPlayList(position);
    }

    private void onPrevAction(Intent intent) {
        musicService.prev();
    }

    private void onNextAction(Intent intent) {
        musicService.next();
    }


    private void onPauseAction(Intent intent) {
        musicService.pause();
    }

    private void onResumeAction(Intent intent) {
        musicService.resume();
    }

    private void onNewPlayListAction(Intent intent) {
        Bundle bundle = intent.getExtras();
        int position = bundle.getInt("position");
        musicService.getPlaylist(position);
    }

    private void onGetCurrentStateAction(Intent intent) {
        musicService.getCurrentState();
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


    private void onPlayAction(Intent intent) {
        int position = intent.getExtras().getInt("position");
        musicService.play(position);
    }

    private void onStopAction(Intent intent) {
        musicService.stop();
    }
}