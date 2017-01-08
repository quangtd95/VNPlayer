package td.quang.vnplayer.views.fragments.playing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import td.quang.vnplayer.R;
import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.views.BaseFragment;
import td.quang.vnplayer.views.adapters.SongAdapter;

/**
 * Created by Quang_TD on 12/31/2016.
 */

public class PlayingListFragment extends BaseFragment {
    private static PlayingListFragment instance;
    @BindView(R.id.rvList)
    RecyclerView mRecyclerView;
    private View view;
    private List<Song> songs;
    private SongAdapter songAdapter;

    public static PlayingListFragment getInstance() {
        if (instance == null) {
            instance = new PlayingListFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void init() {

    }
}
