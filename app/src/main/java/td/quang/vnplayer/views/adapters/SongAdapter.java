package td.quang.vnplayer.views.adapters;

import java.util.ArrayList;

import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.views.activities.IMainView;

/**
 * Created by djwag on 1/10/2017.
 */

public interface SongAdapter {
    void setPlayingView(IMainView IMainView);

    void setData(ArrayList<Song> songs);

    void removeItem(int position);

    void notifyData();

    void playSongOnClick(int position);

    Song getNextSong();

    Song getPrevSong();

    void setNextSong();

    void setPrevSong();

    void deleteSong(Song song, int position);
}
