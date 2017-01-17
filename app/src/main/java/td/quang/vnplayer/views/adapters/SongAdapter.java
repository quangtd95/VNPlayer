package td.quang.vnplayer.views.adapters;

import java.util.ArrayList;

import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.views.activities.MainView;

/**
 * Created by Quang_TD on 1/10/2017.
 */

public interface SongAdapter {

    void setPlayingView(MainView MainView);

    void setData(ArrayList<Song> songs);

    void removeItem(int position);

    void notifyData();

    void playSongOnClick(int position);

    void deleteSong(Song song, int position);


}
