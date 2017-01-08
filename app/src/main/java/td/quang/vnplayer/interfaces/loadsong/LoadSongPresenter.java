package td.quang.vnplayer.interfaces.loadsong;

import td.quang.vnplayer.views.adapters.SongAdapter;

/**
 * Created by djwag on 1/7/2017.
 */

public interface LoadSongPresenter {
    void init(LoadSongView view, SongAdapter songAdapter);

    void loadSong();

    void deleteSong(String filePath, int position);
}
