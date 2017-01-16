package td.quang.vnplayer.views.activities;

import android.content.Context;

import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.views.adapters.SongAdapter;

/**
 * Created by djwag on 1/8/2017.
 */

public interface MainView {
    void playView(Song song);

    void play(Song song);

    void setSongAdapter(SongAdapter songAdapter);

    void resumeView();

    void pauseView();

    void setTimeSeekbar(int mCurrentTime, int visible);

    void setIsPause(boolean b);

    boolean isShuffle();

    Context getContext();
}
