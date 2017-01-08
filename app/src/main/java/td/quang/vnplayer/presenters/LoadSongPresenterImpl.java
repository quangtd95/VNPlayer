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

    private static LoadSongPresenter instance;
    private LoadSongView view;
    private SongAdapter songAdapter;
    private LoadSongInteractor loadSongInteractor;

    private LoadSongPresenterImpl() {
        loadSongInteractor = LoadSongInteratorImpl.getInstance();
    }

    public static synchronized LoadSongPresenter getInstance() {
        if (instance == null) {
            instance = new LoadSongPresenterImpl();
        }
        return instance;
    }

    @Override
    public void init(LoadSongView view, SongAdapter songAdapter) {
        this.view = view;
        this.songAdapter = songAdapter;
    }

    @Override
    public void loadSong() {
        Log.e("TAGG", "loadsong presenter");
        view.showLoading();
        loadSongInteractor.loadSong(this);
    }

    @Override
    public void deleteSong(String filePath, int position) {
        loadSongInteractor.deleteSong(this, filePath, position);
    }

    @Override
    public void onLoadFail() {
        view.hideLoading();
        view.showDialogLoadFail();
    }

    @Override
    public void onLoadSuccess(List<Song> songs) {
        view.hideLoading();
        songAdapter.setData(songs);
        songAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDeleteSuccess(int position) {
        view.showDeleteSuccess();
        songAdapter.removeItem(position);

    }

    @Override
    public void onDeleteFail() {
        view.showDeleteError();
    }
}
