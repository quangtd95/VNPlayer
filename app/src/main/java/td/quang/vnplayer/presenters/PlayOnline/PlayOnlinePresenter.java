package td.quang.vnplayer.presenters.playonline;

import td.quang.vnplayer.views.fragments.home.LoadSongView;

/**
 * Created by djwag on 1/14/2017.
 */

public interface PlayOnlinePresenter {
    void search(String keyword);

    void setLoadSongView(LoadSongView loadSongView);

}
