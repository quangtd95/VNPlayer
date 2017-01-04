package td.quang.vnplayer.views.fragments.playing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        songs = new ArrayList<>();
        Song.Builder builder = new Song.Builder("3 ThangBan", "", 1);
        builder.setArtist("karik");
        Song song1 = builder.build();
        songs.add(song1);
        Song.Builder builder1 = new Song.Builder("Enjoy your life", "", 2);
        builder1.setArtist("karik");
        Song song2 = builder1.build();
        songs.add(song2);
        for (int i = 0; i < 100; i++) {
            songs.add((new Random().nextBoolean()) ? song1 : song2);
        }
        songAdapter = new SongAdapter(getContext(), songs);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(songAdapter);
    }
}
