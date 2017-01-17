package td.quang.vnplayer.presenters.playoffline;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;

import td.quang.vnplayer.broadcasts.BroadcastToService;
import td.quang.vnplayer.models.databases.PlayListManager;
import td.quang.vnplayer.models.databases.PlayListManagerImpl;
import td.quang.vnplayer.models.objects.Song;


public class PlayListPresenterImpl implements PlayListPresenter {
    private static PlayListPresenterImpl instance;
    private PlayListManager mPlayListManager;

    private PlayListPresenterImpl() {
        mPlayListManager = PlayListManagerImpl.getInstance();
    }

    public static synchronized PlayListPresenterImpl getInstance() {
        if (instance == null) instance = new PlayListPresenterImpl();
        return instance;
    }

    @Override public List<Song> getPlayList() {
        return mPlayListManager.getPlaylist();
    }

    @Override public void removeSong(Context mContext, Song song, int position) {
        mPlayListManager.removeItemPlaylist(song);
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        intent.setAction(BroadcastToService.ACTION_REMOVE_IN_PLAYLIST);
        intent.putExtras(bundle);
        mContext.sendBroadcast(intent);
    }

    @Override public void playInPlayList(Context mContext, int position) {
        //phat bai hat position
        Intent intent = new Intent();
        intent.setAction(BroadcastToService.ACTION_PLAY);
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        intent.putExtras(bundle);
        mContext.sendBroadcast(intent);

    }
}
