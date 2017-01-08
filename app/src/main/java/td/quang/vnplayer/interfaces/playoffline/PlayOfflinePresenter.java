package td.quang.vnplayer.interfaces.playoffline;

import td.quang.vnplayer.models.objects.Song;

/**
 * Created by djwag on 1/8/2017.
 */

public interface PlayOfflinePresenter {
    void play(Song song);

    void stop(Song song);

    void addQueue(Song song);

    void removeQueue(Song song);
}
