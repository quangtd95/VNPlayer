package td.quang.vnplayer.views.activities;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import td.quang.vnplayer.R;

class SlidingPanelListener implements SlidingUpPanelLayout.PanelSlideListener {
    private View dragView;
    private Context mContext;

    SlidingPanelListener(Context mContext, View dragView) {
        this.mContext = mContext;
        this.dragView = dragView;
    }

    @Override
    public void onPanelSlide(View panel, float slideOffset) {
    }

    @Override
    public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
        if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
            if (dragView.getVisibility() == View.GONE) {
                return;
            }
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_fade_down);
            dragView.startAnimation(animation);
            Handler handler = new Handler();
            handler.postDelayed(() -> dragView.setVisibility(View.GONE), animation.getDuration());
        }
        if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
            if (dragView.getVisibility() == View.VISIBLE) {
                return;
            }
            dragView.setVisibility(View.VISIBLE);
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_fade_up);
            dragView.startAnimation(animation);

        }
    }
}