package td.quang.vnplayer.presenters.loadsong;

import android.content.Context;

import td.quang.vnplayer.views.activities.MainView;
import td.quang.vnplayer.views.adapters.SongAdapter;

/**
 * Created by Quang_TD on 1/7/2017.
 */

public interface LoadSongPresenter {
    void init(MainView mMainView, SongAdapter songAdapter);

    void loadSong(Context mContext);

    void deleteSong(Context mContext, String filePath, int position);
}
