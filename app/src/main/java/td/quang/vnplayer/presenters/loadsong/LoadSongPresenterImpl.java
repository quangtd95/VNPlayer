package td.quang.vnplayer.presenters.loadsong;

import android.content.Context;

import java.util.ArrayList;

import td.quang.vnplayer.models.loadsong.LoadSongInteractor;
import td.quang.vnplayer.models.loadsong.LoadSongInteratorImpl;
import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.views.adapters.SongAdapter;
import td.quang.vnplayer.views.fragments.home.LoadSongView;

/**
 * Created by djwag on 1/7/2017.
 */

public class LoadSongPresenterImpl implements LoadSongPresenter, OnLoadFinishedListener, OnDeleteFinishedListener {

    private static LoadSongPresenter sInstance;
    private LoadSongView mView;
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
    public void init(LoadSongView view, SongAdapter songAdapter) {
        this.mView = view;
        this.mAdapter = songAdapter;
    }

    @Override
    public void loadSong(Context mContext) {
        mView.showLoading();
        mInteractor.loadSong(mContext, this);
    }

    @Override
    public void deleteSong(Context mContext, String filePath, int position) {
        mInteractor.deleteSong(mContext, this, filePath, position);
    }

    @Override
    public void onLoadFail() {
        mView.hideLoading();
        mView.showDialogLoadFail();
    }

    @Override
    public void onLoadSuccess(ArrayList<Song> songs) {
        mView.hideLoading();
        mAdapter.setData(songs);
        mAdapter.notifyData();
    }

    @Override
    public void onDeleteSuccess(int position) {
        mView.showDeleteSuccess();
        mAdapter.removeItem(position);

    }

    @Override
    public void onDeleteFail() {
        mView.showDeleteError();
    }
}
