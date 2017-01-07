package td.quang.vnplayer.interfaces.loadsong.listeners;

import java.util.List;

import td.quang.vnplayer.models.objects.Song;

/**
 * Created by djwag on 1/7/2017.
 */

public interface OnLoadFinishedListener {
    void onLoadFail();

    void onLoadSuccess(List<Song> songs);
}
