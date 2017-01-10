package td.quang.vnplayer.views.activities;

import td.quang.vnplayer.models.objects.Song;

/**
 * Created by djwag on 1/8/2017.
 */

public interface PlayingView {
    void swapPlaying(Song song);
    void play(Song song);

    void resume();

    void pause();
    void setCurrentSong(Song song);
}
