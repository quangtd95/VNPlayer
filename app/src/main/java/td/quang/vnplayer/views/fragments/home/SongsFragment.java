package td.quang.vnplayer.views.fragments.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import td.quang.vnplayer.R;
import td.quang.vnplayer.interfaces.loadsong.LoadSongPresenter;
import td.quang.vnplayer.interfaces.loadsong.LoadSongView;
import td.quang.vnplayer.presenters.LoadSongPresenterImpl;
import td.quang.vnplayer.views.activities.MainActivity;
import td.quang.vnplayer.views.adapters.SongAdapter;
import td.quang.vnplayer.views.dialogs.MyDialog;

/**
 * Created by Quang_TD on 12/28/2016.
 */

public class SongsFragment extends HomeBaseFragment implements LoadSongView {
    private static SongsFragment instance;

    @BindView(R.id.rvList)
    RecyclerView mRecyclerView;

    private SongAdapter songAdapter;
    private LoadSongPresenter presenter;

    private View view;
    private SweetAlertDialog dialogLoading;
    private MainActivity activity;

    public static SongsFragment getInstance() {
        if (instance == null) {
            synchronized (SongsFragment.class) {
                if (instance == null) {
                    instance = new SongsFragment();
                    instance.setName("Song");
                }
            }
        }
        return instance;
    }

    public void setActivity(MainActivity activity) {
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, null);
        ButterKnife.bind(this, view);
        init();
        presenter = LoadSongPresenterImpl.getInstance();
        presenter.init(this, songAdapter);
        refreshListSong();
        return view;
    }

    @Override
    public void init() {
        songAdapter = new SongAdapter(this);
        songAdapter.setPlayingView(activity);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(songAdapter);

    }

    @Override
    public void refreshListSong() {
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
