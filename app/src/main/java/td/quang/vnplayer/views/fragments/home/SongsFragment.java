package td.quang.vnplayer.views.fragments.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.pedant.SweetAlert.SweetAlertDialog;
import td.quang.vnplayer.R;
import td.quang.vnplayer.presenters.loadsong.LoadSongPresenter;
import td.quang.vnplayer.presenters.loadsong.LoadSongPresenterImpl;
import td.quang.vnplayer.views.BaseFragment;
import td.quang.vnplayer.views.activities.PlayingView;
import td.quang.vnplayer.views.adapters.SongAdapter;
import td.quang.vnplayer.views.dialogs.MyDialog;

/**
 * Created by Quang_TD on 12/28/2016.
 */
//@EFragment(R.layout.fragment_list)
public class SongsFragment extends BaseFragment implements LoadSongView {

    //    @ViewById(R.id.rvList)
    private RecyclerView mRecyclerView;

    private SongAdapter songAdapter;
    private LoadSongPresenter presenter;
    private PlayingView playingView;
    private View view;
    private SweetAlertDialog dialogLoading;

    public SongsFragment() {
        setName("Song");
    }

    public void setPlayingView(PlayingView playingView) {
        this.playingView = playingView;
    }


    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvList);
        afterView();
        return view;
    }

    @Override
    protected void afterView() {
        Log.e("TAGG", "afterView Song Fragment");
        songAdapter = new SongAdapter(this);
        songAdapter.setPlayingView(playingView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(songAdapter);
        presenter = LoadSongPresenterImpl.getInstance();
        presenter.init(this, songAdapter);
        refreshListSong();
    }

    @Override
    public void refreshListSong() {
        Log.e("TAGG", "load song at fragment");
        presenter.loadSong();
    }

    @Override
    public void showLoading() {
        dialogLoading = MyDialog.showLoading(getContext());
    }

    @Override
    public void hideLoading() {
        MyDialog.hideLoading(dialogLoading);
    }

    @Override
    public void showDialogLoadFail() {
        MyDialog.showError(getContext());
    }

    @Override
    public void showDialogConfirmDelete(String filePath, int position) {
        SweetAlertDialog dialog = MyDialog.showConfirm(getContext());
        dialog.setConfirmClickListener(sweetAlertDialog -> {
            presenter.deleteSong(filePath, position);
            dialog.dismiss();
        }).show();
    }

    @Override
    public void showDeleteSuccess() {
        MyDialog.showSuccess(getContext());
    }

    @Override
    public void showDeleteError() {
        MyDialog.showError(getContext());
    }


}
