package td.quang.vnplayer.presenters.playoffline;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import java.util.List;

import td.quang.vnplayer.broadcasts.BroadCastToUI;
import td.quang.vnplayer.broadcasts.BroadcastToService;
import td.quang.vnplayer.models.databases.PlayListManager;
import td.quang.vnplayer.models.databases.PlayListManagerImpl;
import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.views.activities.MainView;

/**
 * Created by Quang_TD on 1/8/2017.
 */

public class PlayOfflinePresenterImpl implements PlayOfflinePresenter, OnPreparePlaylistListener {

    private static PlayOfflinePresenterImpl instance;
    private MainView mMainView;
    private BroadCastToUI mBroadCastToUI;
    private PlayListManager mPlayListManager;

    private PlayOfflinePresenterImpl() {
        mPlayListManager = PlayListManagerImpl.getInstance();
        mPlayListManager.setOnPreparePlaylistListener(this);
    }

    public static PlayOfflinePresenterImpl getInstance() {
        if (instance == null) instance = new PlayOfflinePresenterImpl();
        return instance;
    }

    @Override public void updatePositionPlayList(int position) {
        mMainView.setCurrentPosition(position);
    }

    public void registerBroadcast() {
        mBroadCastToUI = new BroadCastToUI(this, mMainView);
        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(BroadCastToUI.ACTION_UPDATE_TIME);
        mIntentFilter.addAction(BroadCastToUI.ACTION_COMPLETE);
        mIntentFilter.addAction(BroadCastToUI.ACTION_NEXT);
        mIntentFilter.addAction(BroadCastToUI.ACTION_PREV);
        mIntentFilter.addAction(BroadCastToUI.ACTION_PAUSE);
        mIntentFilter.addAction(BroadCastToUI.ACTION_RESUME);
        mIntentFilter.addAction(BroadCastToUI.ACTION_RECEIVE_CURRENT_STATE);
        mIntentFilter.addAction(BroadCastToUI.ACTION_UPDATE_SONG);
        mMainView.getContext().registerReceiver(mBroadCastToUI, mIntentFilter);
    }

    public void unregisterBroadcast() {
        mMainView.getContext().unregisterReceiver(mBroadCastToUI);
    }

    @Override public void setMainView(MainView mainView) {
        mMainView = mainView;
    }

    @Override public void createPlayList(List<Song> songs, int position) {
        mPlayListManager.createPlaylist(songs, position);
    }

    @Override
    public void getCurrentState() {
        Intent intent = new Intent();
        intent.setAction(BroadcastToService.ACTION_GET_CURRENT_STATE);
        mMainView.getContext().sendBroadcast(intent);
    }

    @Override public void setCurrentState(Intent intent) {
        mMainView.setCurrentState(intent);
    }

    @Override
    public void play(int position) {
        Intent intent = new Intent();
        intent.setAction(BroadcastToService.ACTION_PLAY);
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        intent.putExtras(bundle);
        mMainView.getContext().sendBroadcast(intent);
    }

    @Override
    public void pause() {
        Intent intent = new Intent();
        intent.setAction(BroadcastToService.ACTION_PAUSE);
        mMainView.getContext().sendBroadcast(intent);
        mMainView.pauseView();
    }

    @Override
    public void resume() {
        Intent intent = new Intent();
        intent.setAction(BroadcastToService.ACTION_RESUME);
        mMainView.getContext().sendBroadcast(intent);
        mMainView.resumeView();
    }


    @Override
    public void next() {
        Intent intent = new Intent();
        intent.setAction(BroadcastToService.ACTION_NEXT);
        mMainView.getContext().sendBroadcast(intent);
    }


    @Override
    public void prev() {

        Intent intent = new Intent();
        intent.setAction(BroadcastToService.ACTION_PREV);
        mMainView.getContext().sendBroadcast(intent);
    }

    @Override public void setRepeat(boolean b) {
        Intent intent = new Intent();
        intent.setAction(BroadcastToService.ACTION_REPEAT);
        intent.putExtra("repeat", b);
        mMainView.getContext().sendBroadcast(intent);

    }

    @Override public void setShuffle(boolean b) {
        Intent intent = new Intent();
        intent.setAction(BroadcastToService.ACTION_SHUFFLE);
        intent.putExtra("shuffle", b);
        mMainView.getContext().sendBroadcast(intent);
    }

    @Override
    public void seekTo(int position) {
        Intent intent = new Intent();
        intent.setAction(BroadcastToService.ACTION_SEEK);
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        intent.putExtras(bundle);
        mMainView.getContext().sendBroadcast(intent);
    }

    @Override
    public void updateView(Song song, int position, boolean isPause) {
        mMainView.playView(song, position, isPause);
    }

    @Override
    public void onReceiveTimeValue(int duration, int visible) {
        mMainView.setTimeSeekBar(duration, visible);
    }

    @Override public void onPrepareSuccess(Song song, int position) {
        Intent intent = new Intent();
        intent.setAction(BroadcastToService.ACTION_NEW_PLAYLIST);
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        intent.putExtras(bundle);
        mMainView.getContext().sendBroadcast(intent);
        mMainView.updatePlayList();
        mMainView.playView(song, position, false);
    }

    @Override public void onPrepareFail() {

    }


}
