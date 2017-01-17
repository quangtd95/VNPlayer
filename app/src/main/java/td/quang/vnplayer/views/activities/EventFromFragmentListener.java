package td.quang.vnplayer.views.activities;

import android.widget.RelativeLayout;

/**
 * Created by Quang_TD on 1/17/2017.
 */

public interface EventFromFragmentListener {
    void slidingUp();

    void onPlayNewAction();

    void onResumeAction();

    void onPauseAction();

    void onNextAction();

    void onPrevAction();

    void onShuffleAction(boolean mIsShuffle);

    void onRepeatAction(boolean mIsRepeat);

    void setUpSlidingPanel(RelativeLayout dragView);

    void slidingDown();
}
