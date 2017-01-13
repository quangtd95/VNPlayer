package td.quang.vnplayer.views.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import td.quang.vnplayer.R;
import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.views.activities.MainActivity_;

/**
 * Created by djwag on 1/11/2017.
 */

public class SongNotification {
    private static SongNotification instance;
    private RemoteViews remoteViews;
    private NotificationCompat.Builder mBuilder;

    public static synchronized SongNotification getInstance() {
        if (instance == null) instance = new SongNotification();
        return instance;
    }

    public void showNotificationControl(Context mContext, Song mSong) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(),
                R.layout.layout_notification);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                mContext);
        mBuilder.setSmallIcon(R.drawable.ic_launcher).setContent(
                remoteViews);
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(mContext, MainActivity_.class);
        // The stack builder object will contain an artificial back stack for
        // the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity_.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);
//        remoteViews.setOnClickPendingIntent(R.id.button1, resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(100, mBuilder.build());
        Log.e("TAGG", "noti");
    }
}
