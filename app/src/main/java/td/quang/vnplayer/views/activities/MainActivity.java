package td.quang.vnplayer.views.activities;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import td.quang.vnplayer.R;
import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.presenters.playoffline.PlayOfflinePresenter;
import td.quang.vnplayer.presenters.playoffline.PlayOfflinePresenterImpl;
import td.quang.vnplayer.services.MusicServiceImpl;
import td.quang.vnplayer.utils.AudioUtils;
import td.quang.vnplayer.views.BaseActivity;
import td.quang.vnplayer.views.BaseFragment;
import td.quang.vnplayer.views.adapters.HomeViewPagerAdapter;
import td.quang.vnplayer.views.adapters.SongAdapter;
import td.quang.vnplayer.views.customviews.MyButton;
import td.quang.vnplayer.views.dialogs.MyToast;
import td.quang.vnplayer.views.fragments.home.AlbumsFragment;
import td.quang.vnplayer.views.fragments.home.CloudFragment;
import td.quang.vnplayer.views.fragments.home.OnlineFragment;
import td.quang.vnplayer.views.fragments.home.SongsFragment;
import td.quang.vnplayer.views.fragments.playing.PlayingListFragment;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity implements IMainView {
    @ViewById(R.id.slidingPanel) SlidingUpPanelLayout slidingPanel;
    @ViewById(R.id.tabLayout) TabLayout mTabLayout;
    @ViewById(R.id.viewPager) ViewPager mViewPager;
    @ViewById(R.id.barPlaying) View barPlaying;
    @ViewById(R.id.heading) View heading;
    @ViewById(R.id.btnSuffer) MyButton btnSuffer;
    @ViewById(R.id.btnRepeat) MyButton btnRepeat;
    @ViewById(R.id.btnPlay) MyButton btnPlay;
    @ViewById(R.id.btnHome) MyButton btnHome;
    @ViewById(R.id.btnList) MyButton btnList;
    @ViewById(R.id.btnNext) MyButton btnNext;
    @ViewById(R.id.btnPrev) MyButton btnPrev;
    @ViewById(R.id.seekBarTime) SeekBar seekBarTime;
    @ViewById(R.id.ivImageAlbumCover) CircleImageView ivImageAlbumCover;
    @ViewById(R.id.tvCurrentTime) TextView tvCurrentTime;
    @ViewById(R.id.tvDuration) TextView tvDuration;
    TextView tvBarTitle;
    TextView tvHeadTitle;
    TextView tvBarArtist;
    TextView tvHeadArtist;
    ImageButton btnBarPlay;
    ImageView ivBarThumb;

    private HomeViewPagerAdapter mViewPagerAdapter;
    private SongAdapter mSongAdapter;
    private Animation mAnim;
    private boolean mIsSuffer;
    private boolean mIsRepeat;
    private boolean mIsPause;
    private boolean mIsPlayed;
    private List<BaseFragment> mFragments;
    private PlayOfflinePresenter mPresenter;
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
        songsFragment.setIMainView(this);
        mFragments.add(songsFragment);
        mFragments.add(new AlbumsFragment());
        mFragments.add(new OnlineFragment());
        mFragments.add(new CloudFragment());

        mViewPagerAdapter = new HomeViewPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager, true);
        mTabLayout.dispatchSetSelected(true);

        final TypedArray styledAttributes = this.getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.actionBarSize});
        int height = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        slidingPanel.setPanelHeight(height);
        slidingPanel.addPanelSlideListener(new SlidingPanelListener(this, barPlaying));


        mAnim = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);

        playingListFragment = new PlayingListFragment();
        mPresenter = new PlayOfflinePresenterImpl(this, this);
        seekBarTime.setOnSeekBarChangeListener(new SeekBarChangeListener(this, mPresenter));
        mPresenter.registerBroadcast();
        startService(new Intent(MainActivity.this, MusicServiceImpl.class));

    }

    @Click
    public void btnPlayClicked() {
        if (!mIsPlayed) {
            play(mSongAdapter.getFirstSong());
            swapPlaying(mSongAdapter.getFirstSong());
        } else if (mIsPause) {
            resumeView();
        } else {
            pauseView();
        }
    }

    @Click
    public void btnBarPlayClicked() {
        if (!mIsPlayed) {
            play(mSongAdapter.getFirstSong());
            swapPlaying(mSongAdapter.getFirstSong());
        } else if (mIsPause) {
            resumeView();
        } else {
            pauseView();
        }
    }

    @Click
    public void btnNextClicked() {
        if (!mIsPlayed) return;
        mPresenter.next();
    }

    @Click
    public void btnPrevClicked() {
        if (!mIsPlayed) return;
        mPresenter.prev();
    }

    @Click
    public void btnHomeClicked() {
        slidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
    }

    @Click
    public void btnSufferClicked() {
        sufferEvent();
    }

    @Click
    public void btnRepeatClicked() {
        repeatEvent();
    }

    @Override
    public void play(Song song) {
        ivImageAlbumCover.startAnimation(mAnim);
        btnPlay.setText(getResources().getString(R.string.ic_pause));
        btnBarPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_circle_outline_white_24dp));
        int maxDuration = AudioUtils.getDuration(this, song.getFilePath());
        seekBarTime.setMax(maxDuration);
        seekBarTime.setProgress(0);
        tvDuration.setText(AudioUtils.convertIntToTime(maxDuration));
        mPresenter.play(this, song);
        mIsPlayed = true;

    }

    @Override
    public void pauseView() {
        ivImageAlbumCover.clearAnimation();
        btnPlay.setText(getResources().getString(R.string.ic_play));
        btnBarPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_circle_outline_white_24dp));
        mIsPause = true;
        mPresenter.pause(this);
    }

    @Override
    public void resumeView() {

        ivImageAlbumCover.startAnimation(mAnim);
        btnPlay.setText(getResources().getString(R.string.ic_pause));
        btnBarPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_circle_outline_white_24dp));
        mIsPause = false;
        mPresenter.resume(this);


    }

    @Override
    public void setTimeSeekbar(int mCurrentTime, int visible) {
        if (!mIsPlayed) return;
        seekBarTime.setProgress(mCurrentTime);
        tvCurrentTime.setVisibility(visible);
        tvCurrentTime.setText(AudioUtils.convertIntToTime(mCurrentTime));
    }


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
            ivImageAlbumCover.setImageDrawable(getResources().getDrawable(R.drawable.cover_thumbnail));
            ivBarThumb.setImageDrawable(getResources().getDrawable(R.drawable.cover_thumbnail));
        }

    }

    @Override
    public void onBackPressed() {
        if (slidingPanel.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
            slidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {
            super.onBackPressed();
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
            MyToast.show(btnSuffer, "tắt trộn bài");
        } else {
            btnSuffer.setTextColor(getResources().getColor(R.color.colorAccent));
            MyToast.show(btnSuffer, "bật trộn bài");
        }
        mIsSuffer = !mIsSuffer;
    }

    public void setSongAdapter(SongAdapter songAdapter) {
        this.mSongAdapter = songAdapter;
        mPresenter.setSongAdapter(songAdapter);
    }
}


