package td.quang.vnplayer.views.notifications;

/**
 * Created by djwag on 1/11/2017.
 */

public class SongNotification {
    private static SongNotification instance;

    public static synchronized SongNotification getInstance() {
        if (instance == null) instance = new SongNotification();
        return instance;
    }
}
