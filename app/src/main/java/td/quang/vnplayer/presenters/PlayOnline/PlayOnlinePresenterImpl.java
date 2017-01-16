package td.quang.vnplayer.presenters.playonline;

import java.util.ArrayList;

import td.quang.vnplayer.models.network.MyRetrofit;
import td.quang.vnplayer.models.objects.OnlineSong;
import td.quang.vnplayer.views.fragments.home.LoadSongView;

/**
 * Created by djwag on 1/14/2017.
 */

public class PlayOnlinePresenterImpl implements PlayOnlinePresenter, OnQueryFinishedListener {
    private LoadSongView loadSongView;


    public void setLoadSongView(LoadSongView loadSongView) {
        this.loadSongView = loadSongView;
    }

    @Override
    public void search(String keyword) {
        MyRetrofit myRetrofit = new MyRetrofit();
        myRetrofit.setQueryFinishedListener(this);
        myRetrofit.search(keyword);
        loadSongView.showLoading();
    }

    @Override
    public void onQuerySuccess(ArrayList<OnlineSong> onlineSongs) {
        loadSongView.hideLoading();
        loadSongView.showSuccess("there are " + onlineSongs.size() + " result.");
        loadSongView.refreshListSong(onlineSongs);

    }

    @Override
    public void onQueryFail() {
        loadSongView.hideLoading();
        loadSongView.showError("error");
    }
}
