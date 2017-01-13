package td.quang.vnplayer.presenters.playoffline;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import td.quang.vnplayer.broadcasts.ControlMusicBroadcast;
import td.quang.vnplayer.broadcasts.MusicServiceReceiver;
import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.views.activities.IMainView;
import td.quang.vnplayer.views.adapters.SongAdapter;

/**
 * Created by djwag on 1/8/2017.
 */

public class PlayOfflinePresenterImpl implements PlayOfflinePresenter {

    private IMainView mMainView;
    private MusicServiceReceiver mMusicServiceReceiver;
    private SongAdapter mSongAdapter;

    public PlayOfflinePresenterImpl(IMainView iMainView) {
        this.mMainView = iMainView;
    }

    public void setSongAdapter(SongAdapter songAdapter) {
        this.mSongAdapter = songAdapter;
    }

    @Override
    public void nextRandom() {
        Song mRandomSong = mSongAdapter.getNextRandomSong();
        mMainView.play(mRandomSong);
        mMainView.swapPlaying(mRandomSong);
    }

    @Override public void prevRandom() {
        Song mPrevRandomSong = mSongAdapter.getPrevRandomSong();
        mMainView.play(mPrevRandomSong);
        mMainView.swapPlaying(mPrevRandomSong);
    }

    public void registerBroadcast(Context mContext) {
        mMusicServiceReceiver = new MusicServiceReceiver(this);
        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(MusicServiceReceiver.ACTION_UPDATE_TIME);
        mIntentFilter.addAction(MusicServiceReceiver.ACTION_COMPLETE);
        mContext.registerReceiver(mMusicServiceReceiver, mIntentFilter);
    }

    public void unregisterBroadcast(Context mContext) {
        mContext.unregisterReceiver(mMusicServiceReceiver);
    }


    @Override
    public void play(Context context, Song song) {
        Intent intent = new Intent();
        intent.setAction(ControlMusicBroadcast.ACTION_PLAY);
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", song);
        intent.putExtras(bundle);
        context.sendBroadcast(intent);
    }

    @Override
    public void pause(Context context) {
        Intent intent = new Intent();
        intent.setAction(ControlMusicBroadcast.ACTION_PAUSE);
        context.sendBroadcast(intent);
    }

    @Override
    public void resume(Context context) {
        Intent intent = new Intent();
        intent.setAction(ControlMusicBroadcast.ACTION_RESUME);
        context.sendBroadcast(intent);
    }

    @Override
    public void next() {
        mMainView.play(mSongAdapter.getNextSong());
        mMainView.swapPlaying(mSongAdapter.getNextSong());
        mSongAdapter.moveToNextSong();
    }

    @Override
    public void prev() {
        mMainView.play(mSongAdapter.getPrevSong());
        mMainView.swapPlaying(mSongAdapter.getPrevSong());
        mSongAdapter.moveToPrevSong();
    }

    @Override public void setRepeat(Context mContext, boolean b) {
        Intent intent = new Intent();
        intent.setAction(ControlMusicBroadcast.ACTION_REPEAT);
        intent.putExtra("repeat", b);
        mContext.sendBroadcast(intent);

    }

    @Override public void setShuffle(Context mContext, boolean b) {
        Intent intent = new Intent();
        intent.setAction(ControlMusicBroadcast.ACTION_SHUFFLE);
        intent.putExtra("shuffle", b);
        mContext.sendBroadcast(intent);
        mSongAdapter.setShuffle(b);
    }

    @Override
    public void seekTo(Context context, int position) {
        Intent intent = new Intent();
        intent.setAction(ControlMusicBroadcast.ACTION_SEEK);
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        intent.putExtras(bundle);
        context.sendBroadcast(intent);
    }

    @Override
    public void onReceiveTimeValue(int duration, int visible) {
        mMainView.setTimeSeekbar(duration, visible);
    }

}
