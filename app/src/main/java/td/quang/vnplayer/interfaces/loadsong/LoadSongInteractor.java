package td.quang.vnplayer.interfaces.loadsong;

import td.quang.vnplayer.interfaces.loadsong.listeners.OnDeleteFinishedListener;
import td.quang.vnplayer.interfaces.loadsong.listeners.OnLoadFinishedListener;

/**
 * Created by djwag on 1/7/2017.
 */

public interface LoadSongInteractor {
    void loadSong(OnLoadFinishedListener listener);

    void deleteSong(OnDeleteFinishedListener listener, String filePath, int position);
}
