package td.quang.vnplayer.presenters.loadsong;

import android.content.Context;

import td.quang.vnplayer.views.adapters.SongAdapter;
import td.quang.vnplayer.views.fragments.home.LoadSongView;

/**
 * Created by djwag on 1/7/2017.
 */

public interface LoadSongPresenter {
    void init(LoadSongView view, SongAdapter songAdapter);

    void loadSong(Context mContext);

    void deleteSong(Context mContext, String filePath, int position);
}
