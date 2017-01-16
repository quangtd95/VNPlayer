
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
import td.quang.vnplayer.presenters.playonline.PlayOnlinePresenter;
import td.quang.vnplayer.presenters.playonline.PlayOnlinePresenterImpl;
import td.quang.vnplayer.views.BaseFragment;
import td.quang.vnplayer.views.adapters.OnlineAdapterImpl;
import td.quang.vnplayer.views.dialogs.MyDialog;

/**
 * Created by Quang_TD on 12/28/2016.
 */

public class OnlineFragment extends BaseFragment implements LoadSongView {
    private SweetAlertDialog dialogLoading;
    private View mView;
    private RecyclerView mRecyclerView;
    private OnlineAdapterImpl onlineAdapter;
    private PlayOnlinePresenter playOnlinePresenter;

    public OnlineFragment() {
        setName("Online");
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_list, container, false);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.rvList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        onlineAdapter = new OnlineAdapterImpl(this);
        mRecyclerView.setAdapter(onlineAdapter);
        afterView();
        return mView;
    }

    @Override
    protected void afterView() {
        playOnlinePresenter = new PlayOnlinePresenterImpl();
        playOnlinePresenter.setLoadSongView(this);
    }

    @Override public void refreshListSong() {

    }

    @Override
    public void refreshListSong(ArrayList<OnlineSong> list) {
        onlineAdapter.setData(list);
        onlineAdapter.notifyDataSetChanged();

    }

    @Override public void showLoading() {
        dialogLoading = MyDialog.showLoading(getContext());
    }

    @Override public void hideLoading() {
        MyDialog.hideLoading(dialogLoading);
    }

    @Override public void showDialogLoadFail() {
        MyDialog.showError(getContext());
    }

    @Override public void showDialogConfirmDelete(String filePath, int position) {

    }

    @Override public void showSuccess(String message) {
        MyDialog.showSuccess(getContext(), message);
    }

    @Override public void showError(String message) {
        MyDialog.showError(getContext());
    }

    public void search(String keyword) {
        playOnlinePresenter.search(keyword);
    }
}
