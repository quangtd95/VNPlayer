package td.quang.vnplayer.presenters.loadsong;

import android.content.Context;

import java.util.ArrayList;

import td.quang.vnplayer.models.loadsong.LoadSongInteractor;
import td.quang.vnplayer.models.loadsong.LoadSongInteratorImpl;
import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.views.activities.MainView;
import td.quang.vnplayer.views.adapters.SongAdapter;

/**
 * Created by Quang_TD on 1/7/2017.
 */

public class LoadSongPresenterImpl implements LoadSongPresenter, OnLoadFinishedListener, OnDeleteFinishedListener {

    private static LoadSongPresenter sInstance;
    private MainView mMainView;
    private SongAdapter mAdapter;
    private LoadSongInteractor mInteractor;

    private LoadSongPresenterImpl() {
        mInteractor = new LoadSongInteratorImpl();
    }

    public static synchronized LoadSongPresenter getInstance() {
        if (sInstance == null) {
            sInstance = new LoadSongPresenterImpl();
        }
        return sInstance;
    }

    @Override
    public void init(MainView mMainView, SongAdapter songAdapter) {
        this.mMainView = mMainView;
        this.mAdapter = songAdapter;
    }

    @Override
    public void loadSong(Context mContext) {
        mMainView.showLoading();
        mInteractor.loadSong(mContext, this);
    }

    @Override
    public void deleteSong(Context mContext, String filePath, int position) {
        mInteractor.deleteSong(mContext, this, filePath, position);
    }

    @Override
    public void onLoadFail() {
        mMainView.hideLoading();
        mMainView.showError("error when load music");
    }

    @Override
    public void onLoadSuccess(ArrayList<Song> songs) {
        mMainView.hideLoading();
        mAdapter.setData(songs);
        mAdapter.notifyData();
    }

    @Override
    public void onDeleteSuccess(int position) {
        mMainView.showSuccess(null);
        mAdapter.removeItem(position);
    }
    @Override
    public void onDeleteFail() {
        mMainView.showError(null);
    }
}
