package td.quang.vnplayer.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import td.quang.vnplayer.presenters.playoffline.PlayOfflinePresenter;

/**
 * Created by djwag on 1/11/2017.
 */

public class UpdateUIBroadcast extends BroadcastReceiver {
    public static final String ACTION_UPDATE_TIME = "TD.QUANG.VNPLAYER.UPDATE_TIME";
    public static final String ACTION_COMPLETE = "TD.QUANG.VNPLAYER.COMPLETE";

    private PlayOfflinePresenter mPresenter;

    public UpdateUIBroadcast(PlayOfflinePresenter mPresenter) {
        this.mPresenter = mPresenter;
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
    }

    private void completeAction(Intent intent) {
        mPresenter.next();
    }

    private void updateTimeAction(Intent intent) {
        int mCurrentTime = intent.getIntExtra("currentTime", 0);
        int visible = intent.getIntExtra("visible", -1);
        mPresenter.onReceiveTimeValue(mCurrentTime, visible);
    }


}
