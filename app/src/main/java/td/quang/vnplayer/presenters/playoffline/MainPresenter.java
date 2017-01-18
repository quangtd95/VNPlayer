package td.quang.vnplayer.presenters.playoffline;

import android.content.Context;
import android.content.Intent;

import java.util.List;

import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.views.activities.MainView;

/**
 * Created by Quang_TD on 1/8/2017.
 */

public interface MainPresenter {
    void setMainView(MainView mainView);

    void createPlayList(List<Song> songs, int position);

    void getCurrentState();

    void setCurrentState(Intent intent);

    void play(int position);

    void pause();

    void resume();

    void next();

    void prev();

    void updateView(Song song, int position, boolean ispause);

    void setRepeat(boolean b);

    void setShuffle(boolean b);

    void seekTo(int position);

    void onReceiveTimeValue(int duration, int visible);

    void registerBroadcast();

    void unregisterBroadcast();

    void updatePositionPlayList(int position);

    void uploadToCloud(Context mContext, Song song);
}
