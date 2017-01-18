package td.quang.vnplayer.views.notifications;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v7.app.NotificationCompat;

import td.quang.vnplayer.R;
import td.quang.vnplayer.models.objects.Song;

/**
 * Created by Quang_TD on 1/18/2017.
 */

public class FirebaseDownloadNotification {
    private static final int NOTIFICATION_ID_DOWNLOAD = 1003;
    private static FirebaseDownloadNotification instance;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilderDownload;
    private boolean isOnGoing;

    private FirebaseDownloadNotification() {
    }

    public static synchronized FirebaseDownloadNotification getInstance() {
        if (instance == null) instance = new FirebaseDownloadNotification();
        return instance;
    }

    public boolean showDownLoadNotification(Context mContext, Song song) {
        if (isOnGoing) return false;
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilderDownload = new NotificationCompat.Builder(mContext);

        mBuilderDownload.setContentTitle("Downloading " + song.getTitle())
                .setContentText("download in progress")
                .setSmallIcon(R.drawable.ic_cloud_download_black_24dp);
        isOnGoing = true;
        mBuilderDownload.setOngoing(isOnGoing);
        return true;
    }

    public void setProgress(int progress) {
        mBuilderDownload.setProgress(100, progress, false);
        mNotificationManager.notify(NOTIFICATION_ID_DOWNLOAD, mBuilderDownload.build());
    }

    public void setFinished(boolean isSuccess) {
        isOnGoing = false;
        mBuilderDownload.setOngoing(isOnGoing);
        mBuilderDownload.setProgress(0, 0, false);
        if (isSuccess) {
            mBuilderDownload.setContentText("download is completed");
        } else {
            mBuilderDownload.setContentText("download is interrupted");
        }
        mNotificationManager.notify(NOTIFICATION_ID_DOWNLOAD, mBuilderDownload.build());
    }
}
