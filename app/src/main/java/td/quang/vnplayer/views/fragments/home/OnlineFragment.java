
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
import td.quang.vnplayer.views.activities.MainView;
import td.quang.vnplayer.views.adapters.OnlineAdapterImpl;

/**
 * Created by Quang_TD on 12/28/2016.
 */

public class OnlineFragment extends BaseFragment implements LoadSongView {
    private SweetAlertDialog dialogLoading;
    private OnlineAdapterImpl onlineAdapter;
    private PlayOnlinePresenter playOnlinePresenter;
    private MainView mMainView;


    public OnlineFragment() {
        setName("Online");
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView mRecyclerView = (RecyclerView) mView.findViewById(R.id.rvList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        onlineAdapter = new OnlineAdapterImpl(this);
        mRecyclerView.setAdapter(onlineAdapter);
        afterView();
        return mView;
    }

    public void setMainView(MainView mainView) {
        this.mMainView = mainView;
    }

    @Override
    protected void afterView() {
        playOnlinePresenter = new PlayOnlinePresenterImpl();
        playOnlinePresenter.setView(mMainView, this);
    }

    @Override public void refreshListSong() {

    }

    @Override
    public void refreshListSong(ArrayList<OnlineSong> list) {
        onlineAdapter.setData(list);
        onlineAdapter.notifyDataSetChanged();

    }


    @Override public void showDialogConfirmDelete(String filePath, int position) {

    }

    public void search(String keyword) {
        playOnlinePresenter.search(keyword);
    }
}
