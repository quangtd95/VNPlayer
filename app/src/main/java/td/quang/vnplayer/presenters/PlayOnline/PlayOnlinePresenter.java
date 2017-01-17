package td.quang.vnplayer.presenters.playonline;

import td.quang.vnplayer.views.activities.MainView;
import td.quang.vnplayer.views.fragments.home.LoadSongView;

/**
 * Created by Quang_TD on 1/14/2017.
 */

public interface PlayOnlinePresenter {
    void search(String keyword);

    void setView(MainView mMainView, LoadSongView loadSongView);

}
