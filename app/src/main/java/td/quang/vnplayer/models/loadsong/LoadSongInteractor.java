package td.quang.vnplayer.models.loadsong;

import android.content.Context;

import td.quang.vnplayer.presenters.loadsong.OnDeleteFinishedListener;
import td.quang.vnplayer.presenters.loadsong.OnLoadFinishedListener;

/**
 * Created by Quang_TD on 1/7/2017.
 */

public interface LoadSongInteractor {
    void loadSong(Context context, OnLoadFinishedListener listener);

    void deleteSong(Context context, OnDeleteFinishedListener listener, String filePath, int position);
}
