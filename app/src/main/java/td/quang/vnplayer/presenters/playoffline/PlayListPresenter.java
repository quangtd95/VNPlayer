package td.quang.vnplayer.presenters.playoffline;

import android.content.Context;

import java.util.List;

import td.quang.vnplayer.models.objects.Song;

/**
 * Created by Quang_TD on 1/17/2017.
 */

public interface PlayListPresenter {
    List<Song> getPlayList();

    void removeSong(Context mContext, Song song, int position);

    void playInPlayList(Context mContext, int position);
}
