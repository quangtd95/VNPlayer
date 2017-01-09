package td.quang.vnplayer.views.activities;

import android.annotation.TargetApi;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import td.quang.vnplayer.R;
import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.presenters.playoffline.PlayOfflinePresenter;
import td.quang.vnplayer.presenters.playoffline.PlayOfflinePresenterImpl;
import td.quang.vnplayer.utils.AudioUtils;
import td.quang.vnplayer.views.BaseActivity;
import td.quang.vnplayer.views.BaseFragment;
import td.quang.vnplayer.views.adapters.HomeViewPagerAdapter;
import td.quang.vnplayer.views.customviews.MyButton;
import td.quang.vnplayer.views.fragments.home.AlbumsFragment;
import td.quang.vnplayer.views.fragments.home.CloudFragment;
import td.quang.vnplayer.views.fragments.home.OnlineFragment;
import td.quang.vnplayer.views.fragments.home.SongsFragment;
import td.quang.vnplayer.views.fragments.playing.PlayingListFragment;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity implements OnClickListener, PlayingView {
    @ViewById(R.id.slidingPanel) SlidingUpPanelLayout slidingPanel;
    @ViewById(R.id.tabLayout) TabLayout mTabLayout;
    @ViewById(R.id.viewPager) ViewPager mViewPager;
    @ViewById(R.id.barPlaying) View barPlaying;
    @ViewById(R.id.heading) View heading;
    @ViewById(R.id.ivImageAlbumCover) CircleImageView ivImageAlbumCover;
    @ViewById(R.id.btnSuffer) MyButton btnSuffer;
    @ViewById(R.id.btnRepeat) MyButton btnRepeat;
    @ViewById(R.id.btnPlay) MyButton btnPlay;
    @ViewById(R.id.btnHome) MyButton btnHome;
    @ViewById(R.id.btnList) MyButton btnList;

    TextView tvBarTitle;
    TextView tvHeadTitle;
    TextView tvBarArtist;
    TextView tvHeadArtist;
    ImageButton btnBarPlay;
    ImageView ivBarThumb;

    private HomeViewPagerAdapter mAdapter;
    private Animation mAnim;
    private boolean mIsPlay = false;
    private boolean mIsSuffer = false;
    private boolean mIsRepeat = false;
    private List<BaseFragment> mFragments;
    private Song currentSong;
    private PlayOfflinePresenter offlinePresenter;
    private PlayingListFragment playingListFragment;

    @Override
    protected void afterView() {
        tvBarTitle = (TextView) barPlaying.findViewById(R.id.tvBarTitle);
        tvBarArtist = (TextView) barPlaying.findViewById(R.id.tvBarArtist);
        btnBarPlay = (ImageButton) barPlaying.findViewById(R.id.btnBarPlay);
        ivBarThumb = (ImageView) barPlaying.findViewById(R.id.ivBarThumb);
        tvHeadTitle = (TextView) heading.findViewById(R.id.tvHeadTitle);
        tvHeadArtist = (TextView) heading.findViewById(R.id.tvHeadArtist);

        mFragments = new ArrayList<>();
        SongsFragment songsFragment = new SongsFragment();
        songsFragment.setPlayingView(this);
        mFragments.add(songsFragment);
        mFragments.add(new AlbumsFragment());
        mFragments.add(new OnlineFragment());
        mFragments.add(new CloudFragment());

        mAdapter = new HomeViewPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager, true);
        mTabLayout.dispatchSetSelected(true);

        final TypedArray styledAttributes = this.getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.actionBarSize});
        int height = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        slidingPanel.setPanelHeight(height);
        mAnim = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);
        playingListFragment = new PlayingListFragment();
        offlinePresenter = new PlayOfflinePresenterImpl();
        addEvents();
    }

    private void addEvents() {
        slidingPanel.addPanelSlideListener(new SlidingPanelListener(this, barPlaying));
        btnSuffer.setOnClickListener(this);
        btnRepeat.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        btnHome.setOnClickListener(this);
        btnList.setOnClickListener(this);
        btnBarPlay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnPlay.getId() || v.getId() == btnBarPlay.getId()) {
            if (!mIsPlay) {
                play(currentSong);
                offlinePresenter.play(currentSong);
            } else {
                stop(currentSong);
                offlinePresenter.stop(currentSong);
            }
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
        if (mIsRepeat) {
            btnRepeat.setTextColor(getResources().getColor(R.color.colorAccentDark));
        } else {
            btnRepeat.setTextColor(getResources().getColor(R.color.colorAccent));
        }
        mIsRepeat = !mIsRepeat;
    }

    private void sufferEvent() {
        if (mIsSuffer) {
            btnSuffer.setTextColor(getResources().getColor(R.color.colorAccentDark));
        } else {
            btnSuffer.setTextColor(getResources().getColor(R.color.colorAccent));
        }
        mIsSuffer = !mIsSuffer;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void play(Song song) {
        ivImageAlbumCover.startAnimation(mAnim);
        btnPlay.setText(getResources().getString(R.string.ic_pause));
        btnBarPlay.setImageDrawable(getDrawable(R.drawable.ic_pause_circle_outline_white_24dp));
        mIsPlay = true;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void stop(Song song) {
        ivImageAlbumCover.clearAnimation();
        btnPlay.setText(getResources().getString(R.string.ic_play));
        btnBarPlay.setImageDrawable(getDrawable(R.drawable.ic_play_circle_outline_white_24dp));
        mIsPlay = false;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void swapPlaying(Song song) {
        tvBarTitle.setText(song.getTitle());
        tvBarArtist.setText(song.getArtist());
        tvHeadTitle.setText(song.getTitle());
        tvHeadArtist.setText(song.getArtist());
        slidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
        Bitmap cover = AudioUtils.getAlbumCover(this, song.getFilePath());
        if (cover != null) {
            ivImageAlbumCover.setImageBitmap(cover);
            ivBarThumb.setImageBitmap(cover);
        } else {
            ivImageAlbumCover.setImageDrawable(getDrawable(R.drawable.cover_thumbnail));
            ivBarThumb.setImageDrawable(getDrawable(R.drawable.cover_thumbnail));
        }

    }

    @Override public void setCurrentSong(Song song) {
        this.currentSong = song;
    }

    @Override public void onBackPressed() {
        if (slidingPanel.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
            slidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }

}


