package td.quang.vnplayer.views.fragments.playing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import td.quang.vnplayer.R;
import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.presenters.playoffline.PlayListPresenter;
import td.quang.vnplayer.presenters.playoffline.PlayListPresenterImpl;
import td.quang.vnplayer.views.BaseFragment;
import td.quang.vnplayer.views.adapters.PlayListAdapter;

/**
 * Created by Quang_TD on 12/31/2016.
 */
@EFragment(R.layout.fragment_list)
public class PlayListFragment extends BaseFragment {
    @ViewById(R.id.rvList)
    RecyclerView mRecyclerView;
    private List<Song> mPlaylist;
    private PlayListAdapter mPlayListAdapter;
    private PlayListPresenter mPlayListPresenter;

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_list, container, false);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.rvList);
        afterView();
        return mView;
    }

    @Override
    protected void afterView() {
        mPlayListPresenter = PlayListPresenterImpl.getInstance();
        mPlaylist = new ArrayList<>();
        mPlayListAdapter = new PlayListAdapter(getContext(), mPlaylist);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mPlayListAdapter);

    }

    public void setPositionPlaylist(int position) {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        mPlayListAdapter.setMPositionOfPlaying(position);
        mPlayListAdapter.notifyDataSetChanged();
        linearLayoutManager.scrollToPosition(position);
    }

    public void updatePlayList() {
        mPlaylist.clear();
        mPlaylist.addAll(mPlayListPresenter.getPlayList());
    }
}
