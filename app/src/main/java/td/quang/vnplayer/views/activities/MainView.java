package td.quang.vnplayer.views.activities;

import android.content.Context;
import android.content.Intent;

import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.views.adapters.SongAdapter;

/**
 * Created by Quang_TD on 1/8/2017.
 */

public interface MainView {

    void setCurrentState(Intent intent);

    void setSongAdapter(SongAdapter songAdapter);

    void playView(Song song, int position, boolean isPause);

    void pauseView();

    void resumeView();

    void setCurrentPosition(int position);

    void setTimeSeekBar(int mCurrentTime, int visible);

    Context getContext();

    void updatePlayList();

    void showLoading();

    void hideLoading();

    void showSuccess(String message);

    void showError(String message);
}
