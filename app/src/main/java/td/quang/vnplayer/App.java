package td.quang.vnplayer;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by Quang_TD on 1/17/2017.
 */

public class App extends Application {
    @Override public void onCreate() {
        super.onCreate();
        Realm.init(getApplicationContext());
    }
}
