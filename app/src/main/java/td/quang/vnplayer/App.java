package td.quang.vnplayer;

import android.app.Application;
import android.content.Context;

/**
 * Created by djwag on 1/7/2017.
 */

public class App extends Application {
    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
