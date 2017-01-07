package td.quang.vnplayer.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import td.quang.vnplayer.R;
import td.quang.vnplayer.models.objects.Album;

/**
 * Created by djwag on 1/4/2017.
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumHolder> {
    private Context mContext;
    private List<Album> albums;

    public AlbumAdapter(Context mContext, List<Album> albums) {
        this.mContext = mContext;
        this.albums = albums;
    }

    @Override
    public AlbumHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_albums, parent, false);
        return new AlbumHolder(view);
    }

    @Override
    public void onBindViewHolder(AlbumHolder holder, int position) {
        Album album = albums.get(position);
        holder.tvAlbum.setText(album.getAlbum());
        holder.tvArtist.setText(album.getArtist());

    }

    @Override
    public int getItemCount() {
        return (albums == null) ? 0 : albums.size();
    }

    static class AlbumHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvAlbum)
        TextView tvAlbum;
        @BindView(R.id.tvArtist)
        TextView tvArtist;
        @BindView(R.id.txtThumb)
        ImageView ivThumb;
        @BindView(R.id.btnOption)
        ImageButton btnOption;

        public AlbumHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
