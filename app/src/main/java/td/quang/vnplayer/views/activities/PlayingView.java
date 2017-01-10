package td.quang.vnplayer.views.activities;

import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.views.adapters.SongAdapter;

/**
 * Created by djwag on 1/8/2017.
 */

public interface PlayingView {
    void swapPlaying(Song song);

    void play(Song song);

    void setCurrentSong(Song song);

    void setNextSong(Song song);

    void setPrevSong(Song song);

    void setSongAdapter(SongAdapter songAdapter);

    void resume();

    void pause();

    void next();

    void prev();


}
