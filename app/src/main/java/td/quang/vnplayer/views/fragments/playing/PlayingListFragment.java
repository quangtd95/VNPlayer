package td.quang.vnplayer.views.fragments.playing;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import td.quang.vnplayer.R;
import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.views.BaseFragment;
import td.quang.vnplayer.views.adapters.SongAdapterImpl;

/**
 * Created by Quang_TD on 12/31/2016.
 */
@EFragment(R.layout.fragment_list)
public class PlayingListFragment extends BaseFragment {
    @ViewById(R.id.rvList)
    RecyclerView mRecyclerView;
    private View view;
    private List<Song> songs;
    private SongAdapterImpl songAdapter;


    @Override
    protected void afterView() {

    }
}
