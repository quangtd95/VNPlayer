package td.quang.vnplayer.presenters.playoffline;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import td.quang.vnplayer.broadcasts.ControlMusicBroadcast;
import td.quang.vnplayer.broadcasts.MusicServiceReceiver;
import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.views.activities.MainView;
import td.quang.vnplayer.views.adapters.SongAdapter;

/**
 * Created by djwag on 1/8/2017.
 */

public class PlayOfflinePresenterImpl implements PlayOfflinePresenter {

    private MainView mMainView;
    private MusicServiceReceiver mMusicServiceReceiver;
    private SongAdapter mSongAdapter;

    public PlayOfflinePresenterImpl(MainView mainView) {
        this.mMainView = mainView;
    }

    public void setSongAdapter(SongAdapter songAdapter) {
        this.mSongAdapter = songAdapter;
    }


    public void registerBroadcast() {
        mMusicServiceReceiver = new MusicServiceReceiver(this, mMainView);
        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(MusicServiceReceiver.ACTION_UPDATE_TIME);
        mIntentFilter.addAction(MusicServiceReceiver.ACTION_COMPLETE);
        mIntentFilter.addAction(MusicServiceReceiver.ACTION_NEXT);
        mIntentFilter.addAction(MusicServiceReceiver.ACTION_PREV);
        mIntentFilter.addAction(MusicServiceReceiver.ACTION_PAUSE);
        mIntentFilter.addAction(MusicServiceReceiver.ACTION_RESUME);
        mMainView.getContext().registerReceiver(mMusicServiceReceiver, mIntentFilter);
    }

    public void unregisterBroadcast() {
        mMainView.getContext().unregisterReceiver(mMusicServiceReceiver);
    }


    @Override
    public void play(Song song) {
        Intent intent = new Intent();
        intent.setAction(ControlMusicBroadcast.ACTION_PLAY);
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", song);
        intent.putExtras(bundle);
        mMainView.getContext().sendBroadcast(intent);
    }

    @Override
    public void pause() {
        Intent intent = new Intent();
        intent.setAction(ControlMusicBroadcast.ACTION_PAUSE);
        mMainView.getContext().sendBroadcast(intent);
        mMainView.setIsPause(true);
        mMainView.pauseView();
    }

    @Override
    public void resume() {
        Intent intent = new Intent();
        intent.setAction(ControlMusicBroadcast.ACTION_RESUME);
        mMainView.getContext().sendBroadcast(intent);
        mMainView.setIsPause(false);
        mMainView.resumeView();
    }


    @Override
    public void next() {
        if (mMainView.isShuffle()) {
            nextRandom();
            return;
        }
        Song song = mSongAdapter.getNextSong();
        mMainView.playView(song);
        mMainView.play(song);

        mMainView.setIsPause(false);
        mSongAdapter.moveToNextSong();
    }

    @Override
    public void prev() {
        if (mMainView.isShuffle()) {
            prevRandom();
            return;
        }
        Song song = mSongAdapter.getPrevSong();
        mMainView.playView(song);
        mMainView.play(song);

        mMainView.setIsPause(false);
        mSongAdapter.moveToPrevSong();
    }

    @Override
    public void nextRandom() {
        Song mRandomSong = mSongAdapter.getNextRandomSong();
        mMainView.playView(mRandomSong);
        mMainView.play(mRandomSong);
        mMainView.setIsPause(false);

    }

    @Override public void prevRandom() {
        Song mPrevRandomSong = mSongAdapter.getPrevRandomSong();
        mMainView.playView(mPrevRandomSong);
        mMainView.play(mPrevRandomSong);
        mMainView.setIsPause(false);
    }

    @Override public void setRepeat(boolean b) {
        Intent intent = new Intent();
        intent.setAction(ControlMusicBroadcast.ACTION_REPEAT);
        intent.putExtra("repeat", b);
        mMainView.getContext().sendBroadcast(intent);

    }

    @Override public void setShuffle(boolean b) {
        Intent intent = new Intent();
        intent.setAction(ControlMusicBroadcast.ACTION_SHUFFLE);
        intent.putExtra("shuffle", b);
        mMainView.getContext().sendBroadcast(intent);
        mSongAdapter.setShuffle(b);
    }

    @Override
    public void seekTo(int position) {
        Intent intent = new Intent();
        intent.setAction(ControlMusicBroadcast.ACTION_SEEK);
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        intent.putExtras(bundle);
        mMainView.getContext().sendBroadcast(intent);
    }

    @Override
    public void onReceiveTimeValue(int duration, int visible) {
        mMainView.setTimeSeekbar(duration, visible);
    }

}
