package td.quang.vnplayer.views.fragments.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import td.quang.vnplayer.R;
import td.quang.vnplayer.models.objects.OnlineSong;
import td.quang.vnplayer.presenters.loadsong.LoadSongPresenter;
import td.quang.vnplayer.presenters.loadsong.LoadSongPresenterImpl;
import td.quang.vnplayer.views.BaseFragment;
import td.quang.vnplayer.views.activities.MainView;
import td.quang.vnplayer.views.adapters.SongAdapterImpl;
import td.quang.vnplayer.views.dialogs.MyDialog;

/**
 * Created by Quang_TD on 12/28/2016.
 */

public class SongsFragment extends BaseFragment implements LoadSongView {

    private RecyclerView mRecyclerView;

    private SongAdapterImpl songAdapter;
    private LoadSongPresenter presenter;
    private MainView MainView;
    private View view;
    private SweetAlertDialog dialogLoading;
    public SongsFragment() {
        setName("Song");
    }

    public void setMainView(MainView mainView) {
        this.MainView = mainView;
    }


    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvList);
        afterView();
        return view;
    }


    @Override
    protected void afterView() {
        songAdapter = new SongAdapterImpl(this);
        songAdapter.setPlayingView(MainView);
        MainView.setSongAdapter(songAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(songAdapter);
        presenter = LoadSongPresenterImpl.getInstance();
        presenter.init(this, songAdapter);
        refreshListSong();
    }

    @Override
    public void refreshListSong() {
        presenter.loadSong(getContext());
    }

    @Override public void refreshListSong(ArrayList<OnlineSong> list) {

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
    public void showSuccess(String message) {
        MyDialog.showError(getContext());
    }

    @Override
    public void showError(String message) {
        MyDialog.showError(getContext());
    }

    @Override
    public void showDialogConfirmDelete(String filePath, int position) {
        SweetAlertDialog dialog = MyDialog.showConfirm(getContext());
        dialog.setConfirmClickListener(sweetAlertDialog -> {
            presenter.deleteSong(getContext(), filePath, position);
            dialog.dismiss();
        }).show();
    }
}
