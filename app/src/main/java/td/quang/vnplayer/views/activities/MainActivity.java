package td.quang.vnplayer.views.activities;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import td.quang.vnplayer.R;
import td.quang.vnplayer.views.BaseActivity;
import td.quang.vnplayer.views.adapters.HomeViewPagerAdapter;
import td.quang.vnplayer.views.customviews.MyButton;
import td.quang.vnplayer.views.fragments.home.AlbumsFragment;
import td.quang.vnplayer.views.fragments.home.CloudFragment;
import td.quang.vnplayer.views.fragments.home.HomeBaseFragment;
import td.quang.vnplayer.views.fragments.home.OnlineFragment;
import td.quang.vnplayer.views.fragments.home.SongsFragment;
import td.quang.vnplayer.views.fragments.playing.PlayingListFragment;

public class MainActivity extends BaseActivity implements OnClickListener {
    @BindView(R.id.slidingPanel)
    SlidingUpPanelLayout slidingPanel;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.barPlaying)
    View barPlaying;
    @BindView(R.id.ivAlbumCover)
    CircleImageView ivAlbumCover;
    @BindView(R.id.btnSuffer)
    MyButton btnSuffer;
    @BindView(R.id.btnRepeat)
    MyButton btnRepeat;
    @BindView(R.id.btnPlay)
    MyButton btnPlay;
    @BindView(R.id.btnHome)
    MyButton btnHome;
    @BindView(R.id.btnList)
    MyButton btnList;

    private HomeViewPagerAdapter adapter;
    private List<HomeBaseFragment> fragments;
    private Animation animRotate;
    private boolean isPlay = false;
    private boolean isSuffer = false;
    private boolean isRepeat = false;
    private PlayingListFragment playingListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        addComponents();
        addEvents();

    }

    private void addEvents() {
        slidingPanel.addPanelSlideListener(new SlidingPanelListener(this, barPlaying));
        btnSuffer.setOnClickListener(this);
        btnRepeat.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        btnHome.setOnClickListener(this);
        btnList.setOnClickListener(this);
    }


    @Override
    public void addComponents() {

        fragments = new ArrayList<>();
        fragments.add(SongsFragment.getInstance());
        fragments.add(AlbumsFragment.getInstance());
        fragments.add(OnlineFragment.getInstance());
        fragments.add(CloudFragment.getInstance());

        adapter = new HomeViewPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager, true);
        mTabLayout.dispatchSetSelected(true);

        final TypedArray styledAttributes = this.getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.actionBarSize});
        int height = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        slidingPanel.setPanelHeight(height);
        animRotate = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);

        playingListFragment = PlayingListFragment.getInstance();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnPlay.getId()) {
            playEvent();
        }
        if (v.getId() == btnSuffer.getId()) {
            sufferEvent();
        }
        if (v.getId() == btnRepeat.getId()) {
            repeatEvent();
        }
        if (v.getId() == btnHome.getId()) {
            slidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        }
        if (v.getId() == btnList.getId()) {
            swapFragments();
        }
    }

    private void swapFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flContainer, playingListFragment);
        fragmentTransaction.commit();
    }

    private void repeatEvent() {
        if (isRepeat) {
            btnRepeat.setTextColor(getResources().getColor(R.color.colorAccentDark));
        } else {
            btnRepeat.setTextColor(getResources().getColor(R.color.colorAccent));
        }
        isRepeat = !isRepeat;
    }

    private void sufferEvent() {
        if (isSuffer) {
            btnSuffer.setTextColor(getResources().getColor(R.color.colorAccentDark));
        } else {
            btnSuffer.setTextColor(getResources().getColor(R.color.colorAccent));
        }
        isSuffer = !isSuffer;
    }

    private void playEvent() {
        if (isPlay) {
            ivAlbumCover.clearAnimation();
            btnPlay.setText(getResources().getString(R.string.ic_play));
        } else {
            ivAlbumCover.startAnimation(animRotate);
            btnPlay.setText(getResources().getString(R.string.ic_pause));
        }
        isPlay = !isPlay;
    }
}

class SlidingPanelListener implements SlidingUpPanelLayout.PanelSlideListener {
    private View dragView;
    private Context mContext;

    public SlidingPanelListener(Context mContext, View dragView) {
        this.mContext = mContext;
        this.dragView = dragView;
    }

    @Override
    public void onPanelSlide(View panel, float slideOffset) {

    }

    @Override
    public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
        if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
            if (dragView.getVisibility() == View.GONE) return;
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_fade_down);
            dragView.startAnimation(animation);
            Handler handler = new Handler();
            handler.postDelayed(() -> dragView.setVisibility(View.GONE), animation.getDuration());
        }
        if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
            if (dragView.getVisibility() == View.VISIBLE) return;
            dragView.setVisibility(View.VISIBLE);
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_fade_up);
            dragView.startAnimation(animation);

        }
    }
}

