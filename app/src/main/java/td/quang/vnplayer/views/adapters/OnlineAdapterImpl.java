package td.quang.vnplayer.views.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;

import td.quang.vnplayer.R;
import td.quang.vnplayer.models.objects.OnlineSong;
import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.presenters.playoffline.PlayOfflinePresenterImpl;
import td.quang.vnplayer.utils.RandomColor;
import td.quang.vnplayer.views.fragments.home.OnlineFragment;

/**
 * Created by Quang_TD on 1/4/2017.
 */

public class OnlineAdapterImpl extends RecyclerView.Adapter<OnlineAdapterImpl.OnlineHolder> implements OnlineAdapter {
    private final PlayOfflinePresenterImpl mPresenter;
    private ArrayList<OnlineSong> onlineSongs;
    private LinkedList<Song> songs;
    private Context mContext;

    public OnlineAdapterImpl(OnlineFragment songsFragment) {
        OnlineFragment mFragment = songsFragment;
        mContext = mFragment.getContext();
        mPresenter = PlayOfflinePresenterImpl.getInstance();
        songs = new LinkedList<>();
    }

    @Override public void setData(ArrayList<OnlineSong> songs) {
        this.onlineSongs = songs;
    }

    @Override public void playSongOnClick(int position) {
        songs.clear();
        for (int i = 0; i < onlineSongs.size(); i++) {
            songs.add(onlineSongs.get(i).toOffline());
        }
        mPresenter.createPlayList(songs, position);
        Toast.makeText(mContext, onlineSongs.get(position).getFilePath(), Toast.LENGTH_SHORT).show();
    }



    @Override
    public int getItemCount() {
        return (onlineSongs == null) ? 0 : onlineSongs.size();
    }

    @Override
    public OnlineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_song_home, parent, false);
        return new OnlineHolder(view);
    }

    @Override
    public void onBindViewHolder(OnlineHolder holder, int position) {

        OnlineSong song = onlineSongs.get(position);
        holder.tvSongTitle.setText(song.getTitle());
        holder.tvSongArtist.setText(song.getArtist());
        holder.ivSongThumb.setBackgroundColor(RandomColor.getColor());
        holder.ivSongThumb.setText(String.valueOf(song.getTitle().charAt(0)));
        holder.cardViewSong.setOnClickListener(v -> playSongOnClick(position));
    }

    static class OnlineHolder extends RecyclerView.ViewHolder {
        TextView ivSongThumb;
        TextView tvSongTitle;
        TextView tvSongArtist;
        ImageButton btnSongOption;
        CardView cardViewSong;

        private OnlineHolder(View itemView) {
            super(itemView);
            ivSongThumb = (TextView) itemView.findViewById(R.id.tvSongThumb);
            tvSongTitle = (TextView) itemView.findViewById(R.id.tvSongTitle);
            tvSongArtist = (TextView) itemView.findViewById(R.id.tvSongArtist);
            btnSongOption = (ImageButton) itemView.findViewById(R.id.btnSongOption);
            cardViewSong = (CardView) itemView.findViewById(R.id.cardLayoutSong);
        }
    }


}
