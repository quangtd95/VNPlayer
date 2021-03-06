package td.quang.vnplayer.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.Log;

import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.models.objects.SongMetadata;

/**
 * Created by Quang_TD on 1/7/2017.
 */

public class AudioUtils {
    public static Bitmap getAlbumCover(Context mContext, String filePath) {
        try {
            Uri uri = Uri.parse(filePath);
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            byte[] rawArt;
            Bitmap art = null;
            BitmapFactory.Options bfo = new BitmapFactory.Options();
            mmr.setDataSource(mContext, uri);
            rawArt = mmr.getEmbeddedPicture();

            if (null != rawArt) {
                art = BitmapFactory.decodeByteArray(rawArt, 0, rawArt.length, bfo);
            }
            return art;
        } catch (Exception e) {
            Log.e("TAGG", e.toString());
            return null;
        }
    }

    public static int getDuration(Context mContext, Song song) {
        if (song.getDuration() > 0) return song.getDuration();
        try {
            Uri uri = song.getSource();
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(mContext, uri);
            String durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            return Integer.parseInt(durationStr);
        } catch (Exception e) {
            return -1;
        }

    }

    public static String convertIntToTime(int duration) {
        int s = duration / 1000;
        int m = s / 60;
        s = s - m * 60;
        int h = m / 60;
        StringBuilder result = new StringBuilder();
        if (h > 0) {
            result.append((h < 10) ? "0" : "");
            result.append(h).append(":");
        }
        result.append((m < 10) ? "0" : "");
        result.append(m).append(":");
        result.append((s < 10) ? "0" : "");
        result.append(s);
        return result.toString();
    }

    public static Song convertToSong(SongMetadata songMetadata) {
        return new Song.Builder()
                .setDuration(songMetadata.getDuration())
                .setTitle(songMetadata.getTitle())
                .setArtist(songMetadata.getArtist())
                .setFilePath(songMetadata.getFilePath())
                .build();
    }
}
