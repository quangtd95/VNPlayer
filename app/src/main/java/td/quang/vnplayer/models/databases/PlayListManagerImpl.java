package td.quang.vnplayer.models.databases;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.presenters.playoffline.OnPreparePlaylistListener;

/**
 * Created by Quang_TD on 1/16/2017.
 */
public class PlayListManagerImpl implements PlayListManager {
    private static PlayListManagerImpl instance;
    private OnPreparePlaylistListener onPreparePlaylistListener;

    private PlayListManagerImpl() {
    }

    public static PlayListManagerImpl getInstance() {
        if (instance == null) {
            instance = new PlayListManagerImpl();
        }
        return instance;
    }

    @Override
    public void setOnPreparePlaylistListener(OnPreparePlaylistListener onPreparePlaylistListener) {
        this.onPreparePlaylistListener = onPreparePlaylistListener;
    }

    @Override
    public void createPlaylist(List<Song> songs, int position) {
        deletePlaylist();
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            for (int i = 0; i < songs.size(); i++) {
                realm1.copyToRealm(songs.get(i));
            }
        });
        realm.close();
        onPreparePlaylistListener.onPrepareSuccess(songs.get(position), position);
    }

    @Override
    public void deletePlaylist() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<Song> results = realm.where(Song.class).findAll();
        results.deleteAllFromRealm();
        realm.commitTransaction();
        realm.close();
    }

    @Override public void addToPlaylist(Song song) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(song);
        realm.commitTransaction();
        realm.close();
    }

    @Override public void removeItemPlaylist(Song song) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            RealmResults<Song> results = realm1.where(Song.class).equalTo("id", song.getId()).findAll();
            results.deleteAllFromRealm();
        });
        realm.close();
    }


    @Override public Song getSongById(String id) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Song realmResults = realm.where(Song.class).equalTo("id", id).findFirst();
        realm.commitTransaction();
        realm.close();
        return realmResults;
    }

    @Override public List<Song> getPlaylist() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<Song> results = realm.where(Song.class).findAll();
        realm.commitTransaction();
        ArrayList<Song> songs = new ArrayList<>();
        songs.addAll(realm.copyFromRealm(results));
        realm.close();
        return songs;
    }


}
