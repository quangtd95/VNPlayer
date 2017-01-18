package td.quang.vnplayer.views.adapters;

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
import td.quang.vnplayer.presenters.playoffline.MainMainPresenterImpl;
import td.quang.vnplayer.presenters.playoffline.MainPresenter;
import td.quang.vnplayer.utils.RandomColor;

/**
 * Created by Quang_TD on 1/18/2017.
 */

public class CloudAdapter extends RecyclerView.Adapter<CloudAdapter.CloudHolder> {
    List<Song> songs;
    List<String> nameUploader;
    private MainPresenter mPresenter;

    public CloudAdapter(List<Song> songs, List<String> nameUploader) {
        this.songs = songs;
        this.nameUploader = nameUploader;
        mPresenter = MainMainPresenterImpl.getInstance();
    }

    public void playSongOnClick(int position) {
        mPresenter.createPlayList(songs, position);
    }


    /*













     */
    @Override public CloudHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_song_cloud, parent, false);
        return new CloudHolder(view);
    }

    @Override public void onBindViewHolder(CloudHolder holder, int position) {
        Song song = songs.get(position);
        holder.tvSongTitle.setText(song.getTitle());
        holder.tvSongArtist.setText(song.getArtist());
        holder.ivSongThumb.setBackgroundColor(RandomColor.getColor());
        holder.ivSongThumb.setText(String.valueOf(song.getTitle().charAt(0)));
        holder.tvNameUploader.setText(String.format("Upload by: %s", nameUploader.get(position)));
        holder.btnSongOption.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                showMenu(holder.btnSongOption, position);
            }
        });
        holder.cardViewSong.setOnClickListener(v -> playSongOnClick(position));

    }


    private void showMenu(View view, int position) {
        Song song = songs.get(position);
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_song_cloud, popupMenu.getMenu());
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.play) {
                playSongOnClick(position);
            }
            if (item.getItemId() == R.id.downloadCloudSong) {
                mPresenter.downloadFileFromCloud(song.getFilePath());
            }
            return false;
        });
    }

    @Override public int getItemCount() {
        return (songs != null) ? songs.size() : 0;
    }

    static class CloudHolder extends RecyclerView.ViewHolder {

        TextView ivSongThumb;
        TextView tvSongTitle;
        TextView tvSongArtist;
        TextView tvNameUploader;
        CardView cardViewSong;
        ImageButton btnSongOption;

        private CloudHolder(View itemView) {
            super(itemView);
            ivSongThumb = (TextView) itemView.findViewById(R.id.tvSongThumb);
            tvSongTitle = (TextView) itemView.findViewById(R.id.tvSongTitle);
            tvSongArtist = (TextView) itemView.findViewById(R.id.tvSongArtist);
            tvNameUploader = (TextView) itemView.findViewById(R.id.tvNameUploader);
            cardViewSong = (CardView) itemView.findViewById(R.id.cardLayoutSong);
            btnSongOption = (ImageButton) itemView.findViewById(R.id.btnSongOption);
        }
    }
}
