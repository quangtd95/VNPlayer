package td.quang.vnplayer.services;

import td.quang.vnplayer.models.objects.Song;

/**
 * Created by djwag on 1/9/2017.
 */

public interface MusicService {
    void play(Song song);

    void stop();

    void resume();

    void pause();

    void seek(int position);

    void setRepeat(boolean b);

    void setShuffle(boolean b);

    void getCurrentState();
}
