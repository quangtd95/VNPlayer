package td.quang.vnplayer.views.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
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
import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.utils.RandomColor;

/**
 * Created by djwag on 1/4/2017.
 */

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongHolder> {
    private List<Song> songs;
    private Context mContext;

    public SongAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Song> songs) {
        this.songs = songs;
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
