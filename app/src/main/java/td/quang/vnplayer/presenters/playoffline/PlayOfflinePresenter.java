package td.quang.vnplayer.presenters.playoffline;

import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.views.adapters.SongAdapter;

/**
 * Created by djwag on 1/8/2017.
 */

public interface PlayOfflinePresenter {

    void play(Song song);

    void pause();

    void resume();

    void next();

    void prev();

    void setRepeat(boolean b);

    void setShuffle(boolean b);

    void seekTo(int position);

    void onReceiveTimeValue(int duration, int visible);

    void registerBroadcast();

    void unregisterBroadcast();

    void setSongAdapter(SongAdapter songAdapter);

    void nextRandom();

    void prevRandom();
}
