package td.quang.vnplayer.presenters.playonline;

import java.util.ArrayList;

import td.quang.vnplayer.models.network.MyRetrofit;
import td.quang.vnplayer.models.objects.OnlineSong;
import td.quang.vnplayer.views.activities.MainView;
import td.quang.vnplayer.views.fragments.home.LoadSongView;

/**
 * Created by Quang_TD on 1/14/2017.
 */

public class PlayOnlinePresenterImpl implements PlayOnlinePresenter, OnQueryFinishedListener {

    private MainView mMainView;
    private LoadSongView mLoadSongView;

    public void setView(MainView mMainView, LoadSongView mLoadSongView) {
        this.mMainView = mMainView;
        this.mLoadSongView = mLoadSongView;
    }

    @Override
    public void search(String keyword) {
        MyRetrofit myRetrofit = new MyRetrofit();
        myRetrofit.setQueryFinishedListener(this);
        myRetrofit.search(keyword);
        mMainView.showLoading();
    }

    @Override
    public void onQuerySuccess(ArrayList<OnlineSong> onlineSongs) {
        mMainView.hideLoading();
        mMainView.showSuccess("there are " + onlineSongs.size() + " result.");
        mLoadSongView.refreshListSong(onlineSongs);
    }

    @Override
    public void onQueryFail() {
        mMainView.hideLoading();
        mMainView.showError("cann't receive data");
    }
}
