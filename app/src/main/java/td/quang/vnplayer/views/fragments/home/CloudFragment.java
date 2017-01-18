package td.quang.vnplayer.views.fragments.home;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.LinkedList;
import java.util.List;

import td.quang.vnplayer.R;
import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.models.objects.SongMetadata;
import td.quang.vnplayer.utils.AudioUtils;
import td.quang.vnplayer.views.BaseFragment;
import td.quang.vnplayer.views.adapters.CloudAdapter;

/**
 * Created by Quang_TD on 12/28/2016.
 */
@EFragment(R.layout.fragment_list)
public class CloudFragment extends BaseFragment {
    @ViewById(R.id.rvList) RecyclerView mRecyclerView;
    private CloudAdapter cloudAdapter;
    private List<Song> songs;
    private List<String> nameUploader;

    public CloudFragment() {
        setName("Cloud");
    }


    @AfterViews
    @Override protected void afterView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        songs = new LinkedList<>();
        nameUploader = new LinkedList<>();
        cloudAdapter = new CloudAdapter(songs, nameUploader);
        mRecyclerView.setAdapter(cloudAdapter);
    }

    public void updateCloud(List<SongMetadata> mCloudSongs) {
        songs.clear();
        nameUploader.clear();
        for (int i = 0; i < mCloudSongs.size(); i++) {
            nameUploader.add(mCloudSongs.get(i).getNameUploader());
            songs.add(AudioUtils.convertToSong(mCloudSongs.get(i)));
        }
        cloudAdapter.notifyDataSetChanged();
    }
}
