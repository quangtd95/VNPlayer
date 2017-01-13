package td.quang.vnplayer.presenters.playoffline;

import android.content.Context;

import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.views.adapters.SongAdapter;

/**
 * Created by djwag on 1/8/2017.
 */

public interface PlayOfflinePresenter {

    void play(Context mContext, Song song);

    void pause(Context mContext);

    void resume(Context mContext);

    void next();

    void prev();

    void setRepeat(Context mContext, boolean b);

    void setShuffle(Context mContext, boolean b);

    void seekTo(Context mContext, int position);

    void onReceiveTimeValue(int duration, int visible);

    void registerBroadcast(Context mContext);

    public void unregisterBroadcast(Context mContext);

    void setSongAdapter(SongAdapter songAdapter);

    void nextRandom();

    void prevRandom();
}
