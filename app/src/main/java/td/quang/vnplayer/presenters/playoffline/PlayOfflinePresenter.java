package td.quang.vnplayer.presenters.playoffline;

import android.content.Context;

import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.views.adapters.SongAdapter;

/**
 * Created by djwag on 1/8/2017.
 */

public interface PlayOfflinePresenter {

    void play(Context context, Song song);

    void pause(Context context);

    void resume(Context context);

    void next();

    void prev();

    void seekTo(Context context, int position);

    void onReceiveTimeValue(int duration, int visible);

    void registerBroadcast();

    void setSongAdapter(SongAdapter songAdapter);

}
