package td.quang.vnplayer.views.activities;

import android.content.Context;
import android.widget.SeekBar;

import td.quang.vnplayer.presenters.playoffline.PlayOfflinePresenter;

/**
 * Created by djwag on 1/11/2017.
 */

public class SeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
    private PlayOfflinePresenter mPresenter;
    private Context mContext;

    public SeekBarChangeListener(Context mContext, PlayOfflinePresenter playOfflinePresenter) {
        this.mPresenter = playOfflinePresenter;
        this.mContext = mContext;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser)
            mPresenter.seekTo(mContext, progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
