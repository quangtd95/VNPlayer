package td.quang.vnplayer.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import lombok.Setter;
import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.presenters.playoffline.MainPresenter;
import td.quang.vnplayer.views.activities.MainView;

/**
 * Created by Quang_TD on 1/11/2017.
 */

public class BroadCastToUI extends BroadcastReceiver {
    public static final String ACTION_UPDATE_TIME = "TD.QUANG.VNPLAYER.MUSICSERVICERECEIVER.UPDATE_TIME";
    public static final String ACTION_COMPLETE = "TD.QUANG.VNPLAYER.MUSICSERVICERECEIVER.COMPLETE";
    public static final String ACTION_NEXT = "TD.QUANG.VNPLAYER.MUSICSERVICERECEIVER.NEXT";
    public static final String ACTION_PREV = "TD.QUANG.VNPLAYER.MUSICSERVICERECEIVER.PREV";
    public static final String ACTION_PAUSE = "TD.QUANG.VNPLAYER.MUSICSERVICERECEIVER.PAUSE";
    public static final String ACTION_RESUME = "TD.QUANG.VNPLAYER.MUSICSERVICERECEIVER.RESUME";
    public static final String ACTION_RECEIVE_CURRENT_STATE = "TD.QUANG.VNPLAYER.MUSICSERVICERECEIVER.RECEIVE_CURRENT_STATE";
    public static final String ACTION_UPDATE_SONG = "TD.QUANG.VNPLAYER.MUSICSERVICERECEIVER.UPDATE_SONG";
    public static final String ACTION_UPDATE_PLAYLIST = "TD.QUANG.VNPLAYER.MUSICSERVICERECEIVER.UPDATE_PLAYLIST";
    public static final String ACTION_UPDATE_CLOUD = "TD.QUANG.VNPLAYER.MUSICSERVICERECEIVER.UPDATE_CLOUD";

    @Setter private MainPresenter mMainPresenter;
    @Setter private MainView mMainView;

    public BroadCastToUI(MainPresenter mMainPresenter, MainView mainView) {
        setMMainPresenter(mMainPresenter);
        setMMainView(mainView);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equalsIgnoreCase(ACTION_UPDATE_TIME)) {
            updateTimeAction(intent);
        }
        if (action.equalsIgnoreCase(ACTION_COMPLETE)) {
            completeAction(intent);
        }
        if (action.equalsIgnoreCase(ACTION_NEXT)) {
            nextAction(intent);
        }
        if (action.equalsIgnoreCase(ACTION_PREV)) {
            prevAction(intent);
        }
        if (action.equalsIgnoreCase(ACTION_PAUSE)) {
            pauseAction(intent);
        }
        if (action.equalsIgnoreCase(ACTION_RESUME)) {
            resumeAction(intent);
        }
        if (action.equalsIgnoreCase(ACTION_RECEIVE_CURRENT_STATE)) {
            receiveCurrentState(intent);
        }
        if (action.equalsIgnoreCase(ACTION_UPDATE_SONG)) {
            updateSongAction(intent);
        }
        if (action.equalsIgnoreCase(ACTION_UPDATE_PLAYLIST)) {
            updatePlayListAction(intent);
        }
        if (action.equalsIgnoreCase(ACTION_UPDATE_CLOUD)) {
            updateCloudAction(intent);
        }

    }

    private void updateCloudAction(Intent intent) {
        mMainPresenter.getAllFromCloud();
    }

    private void updatePlayListAction(Intent intent) {
        Bundle bundle = intent.getExtras();
        int position = bundle.getInt("position");
        mMainPresenter.updatePositionPlayList(position);

    }

    private void updateSongAction(Intent intent) {
        Bundle bundle = intent.getExtras();
        Song song = bundle.getParcelable("song");
        int position = bundle.getInt("position");
        boolean isPause = bundle.getBoolean("pause");
        mMainPresenter.updateView(song, position, isPause);
    }

    private void receiveCurrentState(Intent intent) {
        mMainPresenter.setCurrentState(intent);
    }

    private void resumeAction(Intent intent) {
        mMainPresenter.resume();
    }

    private void pauseAction(Intent intent) {
        mMainPresenter.pause();
    }

    private void prevAction(Intent intent) {
        mMainPresenter.prev();

    }

    private void nextAction(Intent intent) {
        mMainPresenter.next();
    }

    private void completeAction(Intent intent) {
//        boolean mIsSuffer = intent.getBooleanExtra("suffer", false);
//        if (!mIsSuffer) {
        mMainPresenter.next();
//        } else {
//            mMainPresenter.nextRandom();
//        }
    }

    private void updateTimeAction(Intent intent) {
        int mCurrentTime = intent.getIntExtra("currentTime", 0);
        int visible = intent.getIntExtra("visible", -1);
        mMainPresenter.onReceiveTimeValue(mCurrentTime, visible);
    }


}
