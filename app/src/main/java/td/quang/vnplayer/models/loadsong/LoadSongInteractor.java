package td.quang.vnplayer.models.loadsong;

import td.quang.vnplayer.presenters.loadsong.OnDeleteFinishedListener;
import td.quang.vnplayer.presenters.loadsong.OnLoadFinishedListener;

/**
 * Created by djwag on 1/7/2017.
 */

public interface LoadSongInteractor {
    void loadSong(OnLoadFinishedListener listener);

    void deleteSong(OnDeleteFinishedListener listener, String filePath, int position);
}
