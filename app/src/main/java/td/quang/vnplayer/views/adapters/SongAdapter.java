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

import td.quang.vnplayer.R;
import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.utils.RandomColor;
import td.quang.vnplayer.views.activities.PlayingView;
import td.quang.vnplayer.views.fragments.home.SongsFragment;

/**
 * Created by djwag on 1/4/2017.
 */

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongHolder> {
    private List<Song> songs;
    private Context mContext;
    private SongsFragment mFragment;
    private PlayingView mPlayingView;
    public SongAdapter(SongsFragment songsFragment) {
        mFragment = songsFragment;
        mContext = mFragment.getContext();
    }


    public void setPlayingView(PlayingView playingView) {
        this.mPlayingView = playingView;
    }

    public void setData(List<Song> songs) {
        this.songs = songs;
    }

    public void removeItem(int position) {
        songs.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, songs.size());
    }

    @Override
    public SongHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_song, parent, false);
        return new SongHolder(view);
    }

    @Override
    public void onBindViewHolder(SongHolder holder, int position) {
        Song song = songs.get(position);
        holder.tvSongTitle.setText(song.getTitle());
        holder.tvSongArtist.setText(song.getArtist());
        holder.ivSongThumb.setBackgroundColor(RandomColor.getColor());
        holder.ivSongThumb.setText(String.valueOf(song.getTitle().charAt(0)));
        holder.btnSongOption.setOnClickListener(v -> showMenu(v, position));
        holder.cardViewSong.setOnLongClickListener(v -> {
            showMenu(holder.btnSongOption, position);
            return false;
        });
        holder.cardViewSong.setOnClickListener(v -> {
            mPlayingView.swapPlaying(songs.get(position));
            mPlayingView.setCurrentSong(songs.get(position));
        });
    }

    private void showMenu(View view, int position) {
        Song song = songs.get(position);
        PopupMenu popupMenu = new PopupMenu(mContext, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_song_option, popupMenu.getMenu());
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.delete) {
                mFragment.showDialogConfirmDelete(song.getFilePath(), position);
            }
            if (item.getItemId() == R.id.play) {
                mPlayingView.swapPlaying(songs.get(position));
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return (songs == null) ? 0 : songs.size();
    }

    static class SongHolder extends RecyclerView.ViewHolder {
        TextView ivSongThumb;
        TextView tvSongTitle;
        TextView tvSongArtist;
        ImageButton btnSongOption;
        CardView cardViewSong;

        private SongHolder(View itemView) {
            super(itemView);
            ivSongThumb = (TextView) itemView.findViewById(R.id.tvSongThumb);
            tvSongTitle = (TextView) itemView.findViewById(R.id.tvSongTitle);
            tvSongArtist = (TextView) itemView.findViewById(R.id.tvSongArtist);
            btnSongOption = (ImageButton) itemView.findViewById(R.id.btnSongOption);
            cardViewSong = (CardView) itemView.findViewById(R.id.cardLayoutSong);
        }
    }
}
