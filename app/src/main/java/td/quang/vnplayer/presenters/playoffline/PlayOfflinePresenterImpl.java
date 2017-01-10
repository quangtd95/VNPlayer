package td.quang.vnplayer.presenters.playoffline;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import td.quang.vnplayer.broadcasts.ControlMusicBroadcast;
import td.quang.vnplayer.models.objects.Song;

/**
 * Created by djwag on 1/8/2017.
 */

public class PlayOfflinePresenterImpl implements PlayOfflinePresenter {

    @Override public void play(Context context, Song song) {
        Intent intent = new Intent();
        intent.setAction(ControlMusicBroadcast.ACTION_PLAY);
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", song);
        intent.putExtras(bundle);
        context.sendBroadcast(intent);
        Log.e("TAGG", "play presenter");
    }

    @Override public void stop(Context context) {
        Intent intent = new Intent();
        intent.setAction(ControlMusicBroadcast.ACTION_STOP);
        context.sendBroadcast(intent);
        Log.e("TAGG", "stop presenter");

    }

    @Override public void pause(Context context) {
        Intent intent = new Intent();
        intent.setAction(ControlMusicBroadcast.ACTION_PAUSE);
        context.sendBroadcast(intent);
        Log.e("TAGG", "pause presenter");
    }

    @Override public void resume(Context context) {
        Intent intent = new Intent();
        intent.setAction(ControlMusicBroadcast.ACTION_RESUME);
        context.sendBroadcast(intent);
        Log.e("TAGG", "resume presenter");
    }

    @Override public void addQueue(Song song) {

    }

    @Override public void removeQueue(Song song) {

    }
}
