package td.quang.vnplayer.views.fragments.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import td.quang.vnplayer.R;
import td.quang.vnplayer.models.objects.Album;
import td.quang.vnplayer.views.BaseFragment;
import td.quang.vnplayer.views.adapters.AlbumAdapter;

/**
 * Created by Quang_TD on 12/28/2016.
 */
//@EFragment(R.layout.fragment_list)
public class AlbumsFragment extends BaseFragment {
    //    @ViewById(R.id.rvList)
    private RecyclerView mRecyclerView;
    private AlbumAdapter albumAdapter;
    private List<Album> albums;
    private View view;

    public AlbumsFragment() {
        setName("Album");
    }
    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvList);
        afterView();
        return view;
    }

    @Override
    protected void afterView() {
        albums = new ArrayList<>();
        albums.add(new Album("#####", "Adam lambert", ""));
        albums.add(new Album("#####", "Adam lambert", ""));
        albums.add(new Album("#####", "Adam lambert", ""));
        albums.add(new Album("#####", "Adam lambert", ""));
        albums.add(new Album("#####", "Adam lambert", ""));
        albumAdapter = new AlbumAdapter(getContext(), albums);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setAdapter(albumAdapter);
    }
}
