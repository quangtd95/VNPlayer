package td.quang.vnplayer.views.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import lombok.Setter;
import td.quang.vnplayer.R;
import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.presenters.playoffline.PlayListPresenter;
import td.quang.vnplayer.presenters.playoffline.PlayListPresenterImpl;

/**
 * Created by Quang_TD on 1/17/2017.
 */

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.PlayListHolder> {
    private static final int PLAYING = 0;
    private static final int NON_PLAYING = 1;
    private Context mContext;
    private List<Song> mPlaylist;
    @Setter private int mPositionOfPlaying;
    private PlayListPresenter playListPresenter;

    public PlayListAdapter(Context mContext, List<Song> mPlaylist) {
        this.mContext = mContext;
        this.mPlaylist = mPlaylist;
        playListPresenter = PlayListPresenterImpl.getInstance();
    }

    public void removeItem(int position) {
        mPlaylist.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mPlaylist.size());
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mPositionOfPlaying) return PLAYING;
        return NON_PLAYING;
    }

    @Override public PlayListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == NON_PLAYING) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_song_playlist, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_song_playlist_isplaying, parent, false);
        }
        return new PlayListHolder(view);
    }

    @Override public void onBindViewHolder(PlayListHolder holder, int position) {
        Song song = mPlaylist.get(position);
        holder.tvSongTitle.setText(song.getTitle());
        holder.tvSongArtist.setText(song.getArtist());
        holder.btnSongOption.setOnClickListener(v -> showMenu(holder.btnSongOption, position));
        holder.cardLayoutSong.setOnClickListener(v -> onCLickInPlaylist(mContext, position));
    }

    private void onCLickInPlaylist(Context mContext, int position) {
        playListPresenter.playInPlayList(mContext, position);
    }

    private void showMenu(View view, int position) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_song_playlist, popupMenu.getMenu());
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.play) {
                onCLickInPlaylist(mContext, position);
            }
            if (item.getItemId() == R.id.removeThisSong) {
                removeItem(position);
                playListPresenter.removeSong(mContext, mPlaylist.get(position), position);
            }
            return false;
        });
    }

    @Override public int getItemCount() {
        return (mPlaylist == null) ? 0 : mPlaylist.size();
    }

    static class PlayListHolder extends RecyclerView.ViewHolder {
        TextView tvSongTitle;
        TextView tvSongArtist;
        ImageButton btnSongOption;
        CardView cardLayoutSong;

        private PlayListHolder(View itemView) {
            super(itemView);
            tvSongTitle = (TextView) itemView.findViewById(R.id.tvSongTitle);
            tvSongArtist = (TextView) itemView.findViewById(R.id.tvSongArtist);
            btnSongOption = (ImageButton) itemView.findViewById(R.id.btnSongOption);
            cardLayoutSong = (CardView) itemView.findViewById(R.id.cardLayoutSong);
        }
    }
}
