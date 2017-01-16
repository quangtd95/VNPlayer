package td.quang.vnplayer.presenters.playonline;

import java.util.ArrayList;

import td.quang.vnplayer.models.objects.OnlineSong;

/**
 * Created by djwag on 1/14/2017.
 */

public interface OnQueryFinishedListener {
    void onQuerySuccess(ArrayList<OnlineSong> onlineSongs);

    void onQueryFail();
}
