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
        TextView tvAlbum;
        TextView tvArtist;
        ImageView ivThumb;
        ImageButton btnOption;
        public AlbumHolder(View itemView) {
            super(itemView);
            tvAlbum = (TextView) itemView.findViewById(R.id.tvAlbum);
            tvArtist = (TextView) itemView.findViewById(R.id.tvArtist);
            ivThumb = (ImageView) itemView.findViewById(R.id.ivThumb);
            btnOption = (ImageButton) itemView.findViewById(R.id.btnOption);
        }
    }
}
