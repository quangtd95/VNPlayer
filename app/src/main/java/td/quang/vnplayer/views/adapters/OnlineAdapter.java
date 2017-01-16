package td.quang.vnplayer.views.adapters;

import java.util.ArrayList;

import td.quang.vnplayer.models.objects.OnlineSong;
import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.views.activities.MainView;

/**
 * Created by djwag on 1/10/2017.
 */

public interface OnlineAdapter {
    void setPlayingView(MainView MainView);

    void setData(ArrayList<OnlineSong> songs);

    void removeItem(int position);

    void notifyData();

    void playSongOnClick(int position);

    Song getNextSong();

    Song getFirstSong();

    Song getPrevSong();

    void moveToNextSong();

    void moveToPrevSong();

    void deleteSong(Song song, int position);

    Song getNextRandomSong();

    Song getPrevRandomSong();

    void setShuffle(boolean b);

}
