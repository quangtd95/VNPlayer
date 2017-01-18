package td.quang.vnplayer.views.activities;

import android.widget.SeekBar;

import td.quang.vnplayer.presenters.playoffline.MainPresenter;

/**
 * Created by Quang_TD on 1/11/2017.
 */

public class SeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
    private MainPresenter mMainPresenter;

    public SeekBarChangeListener(MainPresenter mainPresenter) {
        this.mMainPresenter = mainPresenter;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser)
            mMainPresenter.seekTo(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
