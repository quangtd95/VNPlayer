package td.quang.vnplayer.views.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import td.quang.vnplayer.R;
import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.services.MusicServiceImpl;
import td.quang.vnplayer.views.activities.MainActivity_;

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

    public void showNotification(MusicServiceImpl musicService, Song song) {
        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(musicService)
                        .setOngoing(true)
                        .setAutoCancel(false)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(song.getTitle())
//                        .setContent(new RemoteViews(musicService.getPackageName(),R.layout.layout_notification))
                        .setContentText(song.getArtist());
// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(musicService, MainActivity_.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", song);
        bundle.putInt("position", 0);
        resultIntent.putExtras(bundle);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(musicService);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity_.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) musicService.getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(100, mBuilder.build());
    }
}
