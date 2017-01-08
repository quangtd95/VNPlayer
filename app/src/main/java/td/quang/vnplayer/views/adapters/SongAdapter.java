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

import butterknife.BindView;
import butterknife.ButterKnife;
import td.quang.vnplayer.R;
import td.quang.vnplayer.interfaces.playoffline.PlayingView;
import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.utils.RandomColor;
import td.quang.vnplayer.views.fragments.home.SongsFragment;

/**
 * Created by djwag on 1/4/2017.
 */

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongHolder> {
    private List<Song> songs;
    private Context mContext;
    private SongsFragment fragment;
    private PlayingView playingView;

    public SongAdapter(SongsFragment songsFragment) {
        fragment = songsFragment;
        mContext = fragment.getContext();
    }

    public void setPlayingView(PlayingView playingView) {
        this.playingView = playingView;
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_songs, parent, false);
        return new SongHolder(view);
    }

    @Override
    public void onBindViewHolder(SongHolder holder, int position) {
        Song song = songs.get(position);
        holder.tvTitle.setText(song.getTitle());
        holder.tvArtist.setText(song.getArtist());
        holder.ivThumb.setBackgroundColor(RandomColor.getColor());
        holder.ivThumb.setText(song.getTitle().charAt(0) + "");
        holder.btnOption.setOnClickListener(v -> {
            showMenu(v, position);
        });
        holder.cardView.setOnLongClickListener(v -> {
            showMenu(holder.btnOption, position);
            return false;
        });
        holder.cardView.setOnClickListener(v -> playingView.swapPlaying(songs.get(position)));
    }

    private void showMenu(View view, int position) {
        Song song = songs.get(position);
        PopupMenu popupMenu = new PopupMenu(mContext, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_song_option, popupMenu.getMenu());
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.delete) {
                fragment.showDialogConfirmDelete(song.getFilePath(), position);
            }
            if (item.getItemId() == R.id.play) {
                playingView.swapPlaying(songs.get(position));
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return (songs == null) ? 0 : songs.size();
    }
    static class SongHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtThumb)
        TextView ivThumb;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvArtist)
        TextView tvArtist;
        @BindView(R.id.btnOption)
        ImageButton btnOption;
        @BindView(R.id.cardLayout)
        CardView cardView;

        public SongHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
