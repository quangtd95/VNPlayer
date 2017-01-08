package td.quang.vnplayer.views.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.hanks.htextview.HTextView;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import td.quang.vnplayer.R;
import td.quang.vnplayer.views.BaseActivity;

/**
 * Created by djwag on 1/4/2017.
 */

public class SplashActivity extends BaseActivity {

    @BindView(R.id.llLayout) LinearLayout linearLayout;
    @BindView(R.id.tvSlogan) HTextView tvSlogan;
    @BindInt(R.integer.splash_time) int timeSplash;

    private Animation animation;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        addComponents();
        addAnimations();
    }

    private void addAnimations() {
        linearLayout.startAnimation(animation);
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, timeSplash);
    }

    @Override
    public void addComponents() {
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_alpha);
        tvSlogan.animateText("Quang TD95");
        tvSlogan.invalidate();


    }
}
