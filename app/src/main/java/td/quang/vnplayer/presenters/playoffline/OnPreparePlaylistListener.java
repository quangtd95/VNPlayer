package td.quang.vnplayer.presenters.playoffline;

import td.quang.vnplayer.models.objects.Song;

/**
 * Created by Quang_TD on 1/16/2017.
 */

public interface OnPreparePlaylistListener {
    //vi tri dau tien se phat
    void onPrepareSuccess(Song song, int position);

    void onPrepareFail();
}
