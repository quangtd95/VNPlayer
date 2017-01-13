package td.quang.vnplayer.views.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import td.quang.vnplayer.R;
import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.utils.RandomColor;
import td.quang.vnplayer.views.activities.IMainView;
import td.quang.vnplayer.views.fragments.home.SongsFragment;

/**
 * Created by djwag on 1/4/2017.
 */

public class SongAdapterImpl extends RecyclerView.Adapter<SongAdapterImpl.SongHolder> implements SongAdapter {
    private ArrayList<Song> songs;
    private Context mContext;
    private SongsFragment mFragment;
    private IMainView mIMainView;
    private int mCurrentPosition;
    private Song mCurrentSong;
    private Stack<Integer> mRandomList;

    public SongAdapterImpl(SongsFragment songsFragment) {
        mFragment = songsFragment;
        mContext = mFragment.getContext();
        mRandomList = new Stack<>();
    }


    @Override
    public void setPlayingView(IMainView IMainView) {
        this.mIMainView = IMainView;
    }

    @Override
    public void setData(ArrayList<Song> songs) {
        this.songs = songs;
    }

    @Override
    public void removeItem(int position) {
        songs.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, songs.size());
    }

    @Override public void notifyData() {
        this.notifyDataSetChanged();
    }

    @Override public void playSongOnClick(int position) {
        mCurrentPosition = position;
        mCurrentSong = songs.get(mCurrentPosition);
        mIMainView.swapPlaying(mCurrentSong);
        mIMainView.play(mCurrentSong);
        mRandomList.clear();
        mRandomList.add(position);
    }

    @Override public Song getNextSong() {
        if (mCurrentPosition == (songs.size() - 1)) return mCurrentSong;
        return songs.get(mCurrentPosition + 1);
    }

    @Override public Song getFirstSong() {
        return songs.get(0);
    }

    @Override public Song getPrevSong() {
        if (mCurrentPosition == 0) return songs.get(mCurrentPosition);
        return songs.get(mCurrentPosition - 1);
    }

    @Override public void moveToNextSong() {
        mCurrentPosition++;
        if (mCurrentPosition == songs.size()) mCurrentPosition++;
    }

    @Override public void moveToPrevSong() {
        mCurrentPosition--;
        if (mCurrentPosition == -1) mCurrentPosition = 0;
    }


    @Override public void deleteSong(Song song, int position) {
        mFragment.showDialogConfirmDelete(song.getFilePath(), position);
    }

    @Override public Song getNextRandomSong() {
        mRandomList.add(new Random().nextInt(songs.size()));
        Log.e("TAGG", "random list next = " + mRandomList.size() + "");
        return songs.get(mRandomList.peek());
    }

    public Song getPrevRandomSong() {
        Log.e("TAGG", "random list prev pop= " + mRandomList.size() + "");
        if (mRandomList.size() > 1) mRandomList.pop();
        return songs.get(mRandomList.peek());

    }

    @Override
    public void setShuffle(boolean b) {
        if (b) {
            mRandomList.clear();
            mRandomList.add(mCurrentPosition);
            Log.e("TAGG", "random list set sufer = " + mRandomList.size() + "");
        } else {
            mRandomList.clear();
        }
    }

    @Override
    public int getItemCount() {
        return (songs == null) ? 0 : songs.size();
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
            playSongOnClick(position);
        });
    }

    private void showMenu(View view, int position) {
        Song song = songs.get(position);
        PopupMenu popupMenu = new PopupMenu(mContext, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_song_option, popupMenu.getMenu());
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.delete) {
                deleteSong(song, position);
            }
            if (item.getItemId() == R.id.play) {
                playSongOnClick(position);
            }
            return false;
        });
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
