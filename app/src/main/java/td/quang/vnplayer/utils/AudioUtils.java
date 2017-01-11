package td.quang.vnplayer.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.Log;

import td.quang.vnplayer.models.objects.Song;

/**
 * Created by djwag on 1/7/2017.
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

            // if rawArt is null then no cover art is embedded in the file or is not
            // recognized as such.
            if (null != rawArt) {
                art = BitmapFactory.decodeByteArray(rawArt, 0, rawArt.length, bfo);
            }


            // Code that uses the cover art retrieved below.
            return art;
        } catch (Exception e) {
            return null;
        }
    }

    public static int getDuration(Context mContext, Song song) {
        Uri uri = song.getSource();
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        if (uri == null) Log.e("TAGG", "uri null");
        mmr.setDataSource(mContext, uri);
        String durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        return Integer.parseInt(durationStr);

    }

    public static String convertIntToTime(int duration) {
        int s = duration / 1000;
        int m = s / 60;
        s = s - m * 60;
        int h = m / 60;
        int i = m = m - h * 60;
        StringBuilder result = new StringBuilder();
        if (h > 0) {
            result.append((h < 10) ? "0" : "");
            result.append(h + ":");
        }
        Log.e("TAGG", result.toString());
        result.append((m < 10) ? "0" : "");
        result.append(m + ":");
        Log.e("TAGG", result.toString());
        result.append((s < 10) ? "0" : "");
        result.append(s);
        Log.e("TAGG", result.toString());
        return result.toString();
    }
}
