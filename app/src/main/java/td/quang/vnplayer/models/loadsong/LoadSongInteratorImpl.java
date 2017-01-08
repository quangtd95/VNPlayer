package td.quang.vnplayer.models.loadsong;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;

import td.quang.vnplayer.App;
import td.quang.vnplayer.interfaces.loadsong.LoadSongInteractor;
import td.quang.vnplayer.interfaces.loadsong.listeners.OnDeleteFinishedListener;
import td.quang.vnplayer.interfaces.loadsong.listeners.OnLoadFinishedListener;
import td.quang.vnplayer.models.objects.Song;

/**
 * Created by djwag on 1/7/2017.
 */

public class LoadSongInteratorImpl implements LoadSongInteractor {
    private static LoadSongInteratorImpl instance;
    private Context mContext;

    private LoadSongInteratorImpl() {
        mContext = App.getContext();
    }

    public static synchronized LoadSongInteratorImpl getInstance() {
        if (instance == null) {
            instance = new LoadSongInteratorImpl();
        }
        return instance;
    }

    @Override
    public void loadSong(OnLoadFinishedListener listener) {
        ArrayList<Song> songs = new ArrayList<>();
        Cursor cursor = mContext.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, null, null, MediaStore.Audio.Media.TITLE + " ASC");
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            songs.add(convert(cursor));
        }
        Log.e("TAGG", "load song interator");
        cursor.close();
        if (songs.size() != 0) {
            listener.onLoadSuccess(songs);
        } else {
            listener.onLoadFail();
        }
    }

    @Override
    public void deleteSong(OnDeleteFinishedListener listener, String filePath, int position) {
        int b = App.getContext().getContentResolver().
                delete(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        MediaStore.Audio.Media.DATA + " ='" + filePath + "'", null);
        App.getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(filePath)));
        if (b != 0) {
            listener.onDeleteSuccess(position);
        } else listener.onDeleteFail();
    }


    private Song convert(Cursor cursor) {
        String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
        String id = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
        String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
        String filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));

        Song.Builder builder = new Song.Builder()
                .setTitle(title)
                .setArtist(artist)
                .setId(id)
                .setFilePath(filePath);
        return builder.build();
    }
}
