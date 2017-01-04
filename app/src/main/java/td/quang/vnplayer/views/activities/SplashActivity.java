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

import butterknife.BindInt;
import butterknife.ButterKnife;
import td.quang.vnplayer.R;
import td.quang.vnplayer.views.BaseActivity;

/**
 * Created by djwag on 1/4/2017.
 */

public class SplashActivity extends BaseActivity {
    @BindInt(R.integer.splash_time)
    int timeSplash;
    private LinearLayout linearLayout;
    private Animation animation;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        setContentView(R.layout.activity_splash);
        linearLayout = (LinearLayout) this.findViewById(R.id.llLayout);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_alpha);

    }
}
