package td.quang.vnplayer.views;

import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * Created by djwag on 1/4/2017.
 */
@EActivity
public abstract class BaseActivity extends AppCompatActivity {
    @AfterViews
    public void init() {
        afterView();
    }

    protected abstract void afterView();
}
