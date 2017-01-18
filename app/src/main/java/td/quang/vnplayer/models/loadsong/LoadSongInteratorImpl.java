package td.quang.vnplayer.models.loadsong;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;

import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.presenters.loadsong.OnDeleteFinishedListener;
import td.quang.vnplayer.presenters.loadsong.OnLoadFinishedListener;

/**
 * Created by Quang_TD on 1/7/2017.
 */

public class LoadSongInteratorImpl implements LoadSongInteractor {

    @Override
    public void loadSong(Context mContext, OnLoadFinishedListener listener) {
        ArrayList<Song> songs = new ArrayList<>();
        Cursor cursor = mContext.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, null, null, String.format("%s %s", MediaStore.Audio.Media.TITLE, "ASC"));
        assert cursor != null;
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

    /**
     * @param listener: listener
     * @param filePath: path of song needing delete
     * @param position: position of song in recycleview , use this to notifydeleteItem
     */
    @Override
    public void deleteSong(Context mContext, OnDeleteFinishedListener listener, String filePath, int position) {
        mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(filePath)));
        File file = new File(filePath);
        boolean b1 = file.delete();
        if (b1) {
            listener.onDeleteSuccess(position);
        } else {
            listener.onDeleteFail();
        }
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
