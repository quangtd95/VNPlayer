package td.quang.vnplayer.views.activities;

import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.views.adapters.SongAdapter;

/**
 * Created by djwag on 1/8/2017.
 */

public interface IMainView {
    void swapPlaying(Song song);

    void play(Song song);

    void setSongAdapter(SongAdapter songAdapter);

    void resumeView();

    void pauseView();


    void setTimeSeekbar(int mCurrentTime, int visible);
}
