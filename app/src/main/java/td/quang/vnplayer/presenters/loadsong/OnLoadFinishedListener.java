package td.quang.vnplayer.presenters.loadsong;

import java.util.ArrayList;

import td.quang.vnplayer.models.objects.Song;

/**
 * Created by Quang_TD on 1/7/2017.
 */

public interface OnLoadFinishedListener {

    void onLoadFail();

    void onLoadSuccess(ArrayList<Song> songs);

}
