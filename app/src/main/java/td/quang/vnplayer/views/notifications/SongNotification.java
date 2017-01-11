package td.quang.vnplayer.views.notifications;

import android.content.Context;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import td.quang.vnplayer.R;
import td.quang.vnplayer.models.objects.Song;

/**
 * Created by djwag on 1/11/2017.
 */

public class SongNotification {
    private static SongNotification instance;
    private RemoteViews remoteViews;

    public static synchronized SongNotification getInstance() {
        if (instance == null) instance = new SongNotification();
        return instance;
    }

    public void showNotification(Context mContext, Song mSong) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
        remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.layout_notification);
        builder.setCustomContentView(remoteViews);
    }
}
