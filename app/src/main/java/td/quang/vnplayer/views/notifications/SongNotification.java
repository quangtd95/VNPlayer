package td.quang.vnplayer.views.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import lombok.Getter;
import td.quang.vnplayer.R;
import td.quang.vnplayer.broadcasts.BroadcastToService;
import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.utils.AudioUtils;
import td.quang.vnplayer.views.activities.MainActivity_;


/**
 * Created by Quang_TD on 1/11/2017.
 */

public class SongNotification {
    private static final int NOTIFICATION_ID_SONG_CONTROL = 1000;
    private static SongNotification instance;
    private RemoteViews mRemoteView;

    private NotificationCompat.Builder mBuilderSongControl;

    private NotificationManager mNotificationManager;
    @Getter private boolean isShow;


    public static synchronized SongNotification getInstance() {
        if (instance == null) instance = new SongNotification();
        return instance;
    }

    public void showNotification(Context mContext, Song song) {
        //intent
        Intent intent = new Intent(mContext, MainActivity_.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);

        Intent intentNext = new Intent();
        intentNext.setAction(BroadcastToService.ACTION_NEXT);
        PendingIntent pendingIntentNext = PendingIntent.getBroadcast(mContext, 0, intentNext, 0);

        Intent intentPrev = new Intent();
        intentPrev.setAction(BroadcastToService.ACTION_PREV);
        PendingIntent pendingIntentPrev = PendingIntent.getBroadcast(mContext, 0, intentPrev, 0);

        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilderSongControl = new NotificationCompat.Builder(mContext);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            mBuilderSongControl.setSmallIcon(R.mipmap.ic_launcher);
            mBuilderSongControl.setContentTitle(song.getTitle())
                    .setStyle(
                            new NotificationCompat.BigTextStyle()
                                    .bigText(song.getTitle()))
                    .setAutoCancel(true).setDefaults(Notification.DEFAULT_SOUND)
                    .setContentText(song.getArtist());
        } else {
            mRemoteView = new RemoteViews(mContext.getPackageName(),
                    R.layout.layout_notification);
            mRemoteView.setTextViewText(R.id.tvNotiTitle, song.getTitle());
            mRemoteView.setTextViewText(R.id.tvNotiArtist, song.getArtist());

            Bitmap bitmap = AudioUtils.getAlbumCover(mContext, song.getFilePath());
            if (bitmap != null) {
                mRemoteView.setImageViewBitmap(R.id.ivNotiThumb, bitmap);
            } else {
                mRemoteView.setImageViewResource(R.id.ivNotiThumb, R.drawable.icon_thumbnail);
            }

            mRemoteView.setOnClickPendingIntent(R.id.btnNotiPrev, pendingIntentPrev);
            mRemoteView.setOnClickPendingIntent(R.id.btnNotiNext, pendingIntentNext);

            mBuilderSongControl.setContent(mRemoteView);
            mBuilderSongControl.setSmallIcon(R.drawable.icon_thumbnail);
            mBuilderSongControl.setAutoCancel(false);
            mBuilderSongControl.setOngoing(true);
            mBuilderSongControl.setContentIntent(pendingIntent);
            mNotificationManager.notify(NOTIFICATION_ID_SONG_CONTROL, mBuilderSongControl.build());
            isShow = true;

        }
    }

    public void updateNotification(Context mContext, boolean isPause) {
        Intent intentResume = new Intent();
        intentResume.setAction(BroadcastToService.ACTION_RESUME);
        PendingIntent pendingIntentResume = PendingIntent.getBroadcast(mContext, 0, intentResume, 0);

        Intent intentPause = new Intent();
        intentPause.setAction(BroadcastToService.ACTION_PAUSE);
        PendingIntent pendingIntentPause = PendingIntent.getBroadcast(mContext, 0, intentPause, 0);

        if (isPause) {
            mRemoteView.setImageViewResource(R.id.btnNotiPlay, R.drawable.ic_play_arrow_black_36dp);
            mRemoteView.setOnClickPendingIntent(R.id.btnNotiPlay, pendingIntentResume);
        } else {
            mRemoteView.setImageViewResource(R.id.btnNotiPlay, R.drawable.ic_pause_black_36dp);
            mRemoteView.setOnClickPendingIntent(R.id.btnNotiPlay, pendingIntentPause);
        }
        setCancelable(!isPause);

        mNotificationManager.notify(NOTIFICATION_ID_SONG_CONTROL, mBuilderSongControl.build());
    }

    public void updateNotification(Context mContext, Song song) {
        mRemoteView.setTextViewText(R.id.tvNotiTitle, song.getTitle());
        mRemoteView.setTextViewText(R.id.tvNotiArtist, song.getArtist());
        Bitmap bitmap = AudioUtils.getAlbumCover(mContext, song.getFilePath());
        if (bitmap != null) {
            mRemoteView.setImageViewBitmap(R.id.ivNotiThumb, bitmap);
        } else {
            mRemoteView.setImageViewResource(R.id.ivNotiThumb, R.drawable.icon_thumbnail);
        }
        mNotificationManager.notify(NOTIFICATION_ID_SONG_CONTROL, mBuilderSongControl.build());
    }

    public void setCancelable(boolean b) {
        mBuilderSongControl.setOngoing(b);
        if (b) isShow = false;
    }


}
