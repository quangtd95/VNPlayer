package td.quang.vnplayer.views.activities;

import android.content.Intent;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.hanks.htextview.HTextView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.IntegerRes;

import td.quang.vnplayer.R;
import td.quang.vnplayer.services.MusicServiceImpl;
import td.quang.vnplayer.views.BaseActivity;

/**
 * Created by Quang_TD on 1/4/2017.
 */
@EActivity(R.layout.activity_splash)
public class SplashActivity extends BaseActivity {

    @ViewById(R.id.llLayout) LinearLayout linearLayout;
    @ViewById(R.id.tvSlogan) HTextView tvSlogan;
    @IntegerRes(R.integer.splash_time) int timeSplash;

    private void addAnimations() {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_alpha);
        linearLayout.startAnimation(animation);
        tvSlogan.animateText("Quang TD95");
        tvSlogan.invalidate();
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity_.class);
            startActivity(intent);
            finish();
        }, timeSplash);
    }


    @Override
    protected void afterView() {
        startService(new Intent(this, MusicServiceImpl.class));
        addAnimations();


    }
}
