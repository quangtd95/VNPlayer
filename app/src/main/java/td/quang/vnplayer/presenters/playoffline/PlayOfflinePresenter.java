package td.quang.vnplayer.presenters.playoffline;

import android.content.Context;

import td.quang.vnplayer.models.objects.Song;

/**
 * Created by djwag on 1/8/2017.
 */

public interface PlayOfflinePresenter {

    void play(Context context, Song song);

    void pause(Context context);

    void resume(Context context);

    void next();

    void prev();

}
