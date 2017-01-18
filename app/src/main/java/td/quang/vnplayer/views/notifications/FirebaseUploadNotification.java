package td.quang.vnplayer.views.notifications;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v7.app.NotificationCompat;

import td.quang.vnplayer.R;
import td.quang.vnplayer.models.objects.Song;

/**
 * Created by Quang_TD on 1/18/2017.
 */

public class FirebaseUploadNotification {
    private static final int NOTIFICATION_ID_UPLOAD = 1002;
    private static FirebaseUploadNotification instance;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilderUpload;
    private boolean isOnGoing;

    private FirebaseUploadNotification() {
    }

    public static synchronized FirebaseUploadNotification getInstance() {
        if (instance == null) instance = new FirebaseUploadNotification();
        return instance;
    }

    /**
     * @param mContext: context
     * @param song:     song to upload
     * @return true if show notification success. only upload 1 song/time
     */
    public boolean showUpLoadNotification(Context mContext, Song song) {
        if (isOnGoing) return false;
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilderUpload = new NotificationCompat.Builder(mContext);

        mBuilderUpload.setContentTitle("Uploading " + song.getTitle())
                .setContentText("upload in progress")
                .setSmallIcon(R.drawable.ic_cloud_upload_black_24dp);

        isOnGoing = true;
        mBuilderUpload.setOngoing(isOnGoing);
        return true;
    }

    public void setProgress(int progress) {
        mBuilderUpload.setProgress(100, progress, false);
        mNotificationManager.notify(NOTIFICATION_ID_UPLOAD, mBuilderUpload.build());
    }

    public void setFinished(boolean isSuccess) {
        isOnGoing = false;
        mBuilderUpload.setOngoing(isOnGoing);
        mBuilderUpload.setProgress(0, 0, false);
        if (isSuccess) {
            mBuilderUpload.setContentText("upload is completed");
        } else {
            mBuilderUpload.setContentText("upload is interrupted");
        }
        mNotificationManager.notify(NOTIFICATION_ID_UPLOAD, mBuilderUpload.build());
    }
}
