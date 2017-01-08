package td.quang.vnplayer.presenters;

import td.quang.vnplayer.interfaces.playoffline.PlayOfflinePresenter;
import td.quang.vnplayer.models.objects.Song;

/**
 * Created by djwag on 1/8/2017.
 */

public class PlayOfflinePresenterImpl implements PlayOfflinePresenter {

    private static PlayOfflinePresenter instance;

    private PlayOfflinePresenterImpl() {
    }

    public static PlayOfflinePresenter getInstance() {
        if (instance == null) instance = new PlayOfflinePresenterImpl();
        return instance;
    }

    @Override public void play(Song song) {

    }

    @Override public void stop(Song song) {

    }

    @Override public void addQueue(Song song) {

    }

    @Override public void removeQueue(Song song) {

    }
}
