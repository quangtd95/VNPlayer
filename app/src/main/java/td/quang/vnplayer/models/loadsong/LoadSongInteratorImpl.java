package td.quang.vnplayer.models.loadsong;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;

import td.quang.vnplayer.App;
import td.quang.vnplayer.interfaces.loadsong.LoadSongInteractor;
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
        /*new Thread(() -> {
            LoadSongInteratorImpl.this.run(listener);
        }).start();*/
        LoadSongInteratorImpl.this.run(listener);
    }

    public void run(OnLoadFinishedListener listener) {
        ArrayList<Song> songs = new ArrayList<>();
        Cursor cursor = mContext.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, null, null, MediaStore.Audio.Media.TITLE + " ASC");
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            songs.add(convert(cursor));
        }
        cursor.close();

        if (songs.size() != 0) {
            listener.onLoadSuccess(songs);
        } else {
            listener.onLoadFail();
        }

    }

    private Song convert(Cursor cursor) {
        String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
        String id = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
        String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));

        Song.Builder builder = new Song.Builder()
                .setTitle(title)
                .setArtist(artist)
                .setId(id);
        return builder.build();
    }

    private Bitmap getAlbumCover(String filePath) {
        try {
            Uri uri = Uri.parse(filePath);
            Log.e("TAGG", filePath);
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            byte[] rawArt;
            Bitmap art = null;
            BitmapFactory.Options bfo = new BitmapFactory.Options();
            mmr.setDataSource(mContext, uri);
            rawArt = mmr.getEmbeddedPicture();

// if rawArt is null then no cover art is embedded in the file or is not
// recognized as such.
            if (null != rawArt)
                art = BitmapFactory.decodeByteArray(rawArt, 0, rawArt.length, bfo);

// Code that uses the cover art retrieved below.
            return art;
        } catch (Exception e) {
            return null;
        }
    }

    private int getDuration(String filePath) {
        try {
            Uri uri = Uri.parse(filePath);
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(mContext, uri);
            String durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            int millSecond = Integer.parseInt(durationStr);
            Log.e("TAGG", millSecond + "");
            return millSecond;
        } catch (Exception e) {
            return 0;
        }
    }
}
