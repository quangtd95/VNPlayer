package td.quang.vnplayer.presenters.playoffline;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import td.quang.vnplayer.broadcasts.ControlMusicBroadcast;
import td.quang.vnplayer.broadcasts.UpdateUIBroadcast;
import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.views.activities.IMainView;
import td.quang.vnplayer.views.adapters.SongAdapter;

/**
 * Created by djwag on 1/8/2017.
 */

public class PlayOfflinePresenterImpl implements PlayOfflinePresenter {

    private IMainView mMainView;
    private UpdateUIBroadcast mUpdateUIBroadcast;
    private SongAdapter mSongAdapter;

    public PlayOfflinePresenterImpl(IMainView iMainView) {
        this.mMainView = iMainView;
    }

    public void setSongAdapter(SongAdapter songAdapter) {
        this.mSongAdapter = songAdapter;
    }

    public void registerBroadcast(Context mContext) {
        mUpdateUIBroadcast = new UpdateUIBroadcast(this);
        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(UpdateUIBroadcast.ACTION_UPDATE_TIME);
        mIntentFilter.addAction(UpdateUIBroadcast.ACTION_COMPLETE);
        mContext.registerReceiver(mUpdateUIBroadcast, mIntentFilter);
    }


    @Override
    public void play(Context context, Song song) {
        Intent intent = new Intent();
        intent.setAction(ControlMusicBroadcast.ACTION_PLAY);
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", song);
        intent.putExtras(bundle);
        context.sendBroadcast(intent);
        Log.e("TAGG", "play presenter");
    }

    @Override
    public void pause(Context context) {
        Intent intent = new Intent();
        intent.setAction(ControlMusicBroadcast.ACTION_PAUSE);
        context.sendBroadcast(intent);
        Log.e("TAGG", "pauseView presenter");
    }

    @Override
    public void resume(Context context) {
        Intent intent = new Intent();
        intent.setAction(ControlMusicBroadcast.ACTION_RESUME);
        context.sendBroadcast(intent);
        Log.e("TAGG", "resumeView presenter");
    }

    @Override
    public void next() {
        mMainView.play(mSongAdapter.getNextSong());
        mMainView.swapPlaying(mSongAdapter.getNextSong());
        mSongAdapter.setNextSong();
    }

    @Override
    public void prev() {
        mMainView.play(mSongAdapter.getPrevSong());
        mMainView.swapPlaying(mSongAdapter.getPrevSong());
        mSongAdapter.setPrevSong();
    }

    @Override public void setRepeat(Context mContext, boolean b) {
        Intent intent = new Intent();
        intent.setAction(ControlMusicBroadcast.ACTION_REPEAT);
        intent.putExtra("repeat", b);
        mContext.sendBroadcast(intent);

    }

    @Override
    public void seekTo(Context context, int position) {
        Intent intent = new Intent();
        intent.setAction(ControlMusicBroadcast.ACTION_SEEK);
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        intent.putExtras(bundle);
        context.sendBroadcast(intent);
        Log.e("TAGG", "seek on presenter");
    }

    @Override
    public void onReceiveTimeValue(int duration, int visible) {
        mMainView.setTimeSeekbar(duration, visible);
    }

}
