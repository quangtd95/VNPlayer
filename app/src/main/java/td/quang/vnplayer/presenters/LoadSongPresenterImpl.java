package td.quang.vnplayer.presenters;

import android.util.Log;

import java.util.List;

import td.quang.vnplayer.interfaces.loadsong.LoadSongInteractor;
import td.quang.vnplayer.interfaces.loadsong.LoadSongPresenter;
import td.quang.vnplayer.interfaces.loadsong.LoadSongView;
import td.quang.vnplayer.interfaces.loadsong.listeners.OnDeleteFinishedListener;
import td.quang.vnplayer.interfaces.loadsong.listeners.OnLoadFinishedListener;
import td.quang.vnplayer.models.loadsong.LoadSongInteratorImpl;
import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.views.adapters.SongAdapter;

/**
 * Created by djwag on 1/7/2017.
 */

public class LoadSongPresenterImpl implements LoadSongPresenter, OnLoadFinishedListener, OnDeleteFinishedListener {

    private static LoadSongPresenter sInstance;
    private LoadSongView mView;
    private SongAdapter mAdapter;
    private LoadSongInteractor mInteractor;

    private LoadSongPresenterImpl() {
        mInteractor = LoadSongInteratorImpl.getInstance();
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
    public void loadSong() {
        Log.e("TAGG", "loadsong presenter");
        mView.showLoading();
        mInteractor.loadSong(this);
    }

    @Override
    public void deleteSong(String filePath, int position) {
        mInteractor.deleteSong(this, filePath, position);
    }

    @Override
    public void onLoadFail() {
        mView.hideLoading();
        mView.showDialogLoadFail();
    }

    @Override
    public void onLoadSuccess(List<Song> songs) {
        mView.hideLoading();
        mAdapter.setData(songs);
        mAdapter.notifyDataSetChanged();
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
