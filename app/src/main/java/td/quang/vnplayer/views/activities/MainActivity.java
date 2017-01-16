package td.quang.vnplayer.views.activities;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import td.quang.vnplayer.utils.AudioUtils;
import td.quang.vnplayer.views.BaseActivity;
import td.quang.vnplayer.views.BaseFragment;
import td.quang.vnplayer.views.adapters.HomeViewPagerAdapter;
import td.quang.vnplayer.views.adapters.SongAdapter;
import td.quang.vnplayer.views.customviews.MyButton;
import td.quang.vnplayer.views.dialogs.MyDialog;
import td.quang.vnplayer.views.dialogs.MyToast;
import td.quang.vnplayer.views.fragments.home.AlbumsFragment;
import td.quang.vnplayer.views.fragments.home.CloudFragment;
import td.quang.vnplayer.views.fragments.home.OnlineFragment;
import td.quang.vnplayer.views.fragments.home.SongsFragment;
import td.quang.vnplayer.views.fragments.playing.PlayingListFragment;

@EActivity(R.layout.activity_main)

public class MainActivity extends BaseActivity implements MainView, SearchView.OnQueryTextListener {
    @ViewById(R.id.slidingPanel) SlidingUpPanelLayout slidingPanel;
    @ViewById(R.id.tabLayout) TabLayout mTabLayout;
    @ViewById(R.id.viewPager) ViewPager mViewPager;
    @ViewById(R.id.barPlaying) View barPlaying;
    @ViewById(R.id.heading) View heading;
    @ViewById(R.id.btnShuffle) MyButton btnShuffle;
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
    @ViewById(R.id.toolbar) Toolbar toolbar;

    TextView tvBarTitle;
    TextView tvHeadTitle;
    TextView tvBarArtist;
    TextView tvHeadArtist;
    ImageButton btnBarPlay;
    ImageView ivBarThumb;
    LinearLayout llLayoutBarDetail;

    private HomeViewPagerAdapter mViewPagerAdapter;
    private SongAdapter mSongAdapter;
    private Animation mAnim;
    private boolean mIsShuffle;
    private boolean mIsRepeat;
    private boolean mIsPause;
    private boolean mIsPlayed;
    private List<BaseFragment> mFragments;
    private PlayOfflinePresenter mPresenter;
    private SearchView searchView;
    private OnlineFragment onlineFragment;


    @Override
    protected void afterView() {
        setSupportActionBar(toolbar);

        tvBarTitle = (TextView) barPlaying.findViewById(R.id.tvBarTitle);
        tvBarArtist = (TextView) barPlaying.findViewById(R.id.tvBarArtist);
        btnBarPlay = (ImageButton) barPlaying.findViewById(R.id.btnBarPlay);
        ivBarThumb = (ImageView) barPlaying.findViewById(R.id.ivBarThumb);
        tvHeadTitle = (TextView) heading.findViewById(R.id.tvHeadTitle);
        tvHeadArtist = (TextView) heading.findViewById(R.id.tvHeadArtist);
        llLayoutBarDetail = (LinearLayout) barPlaying.findViewById(R.id.llLayoutBarDetail);

        mFragments = new ArrayList<>();
        SongsFragment songsFragment = new SongsFragment();
        songsFragment.setMainView(this);
        mFragments.add(songsFragment);
        mFragments.add(new AlbumsFragment());
        onlineFragment = new OnlineFragment();
        mFragments.add(onlineFragment);
        mFragments.add(new CloudFragment());

        mViewPagerAdapter = new HomeViewPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);
        mTabLayout.setupWithViewPager(mViewPager, true);
        mTabLayout.dispatchSetSelected(true);

        final TypedArray styledAttributes = this.getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.actionBarSize});
        int height = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        slidingPanel.setPanelHeight(height);
        slidingPanel.addPanelSlideListener(new SlidingPanelListener(this, barPlaying));

        mAnim = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);

        PlayingListFragment playingListFragment = new PlayingListFragment();
        mPresenter = new PlayOfflinePresenterImpl(this);
        seekBarTime.setOnSeekBarChangeListener(new SeekBarChangeListener(mPresenter));
        mPresenter.registerBroadcast();


    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem itemSearch = menu.findItem(R.id.action_searchc);
        searchView = (SearchView) itemSearch.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Click
    public void btnPlayClicked() {
        if (!mIsPlayed) {
            playView(mSongAdapter.getFirstSong());
            play(mSongAdapter.getFirstSong());
        } else if (mIsPause) {
            resumeView();
            mPresenter.resume();
        } else {
            pauseView();
            mPresenter.pause();
        }
    }

    @Click
    public void btnBarPlayClicked() {
        if (!mIsPlayed) {
            playView(mSongAdapter.getFirstSong());
            play(mSongAdapter.getFirstSong());

        } else if (mIsPause) {
            resumeView();
            mPresenter.resume();
        } else {
            pauseView();
            mPresenter.pause();

        }
    }

    @Click
    public void llLayoutBarDetailClicked() {
        slidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
    }

    @Click
    public void btnNextClicked() {
        if (!mIsPlayed) {
            return;
        }
        if (mIsShuffle) {
            mPresenter.nextRandom();
        } else {
            mPresenter.next();
        }
    }

    @Click
    public void btnPrevClicked() {
        if (!mIsPlayed) {
            return;
        }
        if (mIsShuffle) {
            mPresenter.prevRandom();
        } else {
            mPresenter.prev();
        }
    }

    @Click
    public void btnHomeClicked() {
        slidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
    }

    @Click
    public void btnShuffleClicked() {
        sufferEvent();
    }

    @Click
    public void btnRepeatClicked() {
        repeatEvent();
    }

    @Override
    public void playView(Song song) {
        mIsPlayed = true;
        tvBarTitle.setText(song.getTitle());
        tvBarArtist.setText(song.getArtist());
        tvHeadTitle.setText(song.getTitle());
        tvHeadArtist.setText(song.getArtist());
        slidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
        ivImageAlbumCover.startAnimation(mAnim);
        btnPlay.setText(getResources().getString(R.string.ic_pause));
        btnBarPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_circle_outline_white_24dp));
        int maxDuration = AudioUtils.getDuration(this, song);
        seekBarTime.setMax(maxDuration);
        seekBarTime.setProgress(0);
        tvDuration.setText(AudioUtils.convertIntToTime(maxDuration));
        Bitmap cover = AudioUtils.getAlbumCover(this, song.getFilePath());

        if (cover != null) {
            ivImageAlbumCover.setImageBitmap(cover);
            ivBarThumb.setImageBitmap(cover);
        } else {
            ivImageAlbumCover.setImageDrawable(getResources().getDrawable(R.drawable.cover_thumbnail));
            ivBarThumb.setImageDrawable(getResources().getDrawable(R.drawable.cover_thumbnail));
        }

    }

    @Override public void play(Song song) {
        mPresenter.play(song);
    }

    @Override
    public void pauseView() {
        ivImageAlbumCover.clearAnimation();
        btnPlay.setText(getResources().getString(R.string.ic_play));
        btnBarPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_circle_outline_white_24dp));
        mIsPause = true;
    }

    @Override
    public void resumeView() {
        ivImageAlbumCover.startAnimation(mAnim);
        btnPlay.setText(getResources().getString(R.string.ic_pause));
        btnBarPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_circle_outline_white_24dp));
        mIsPause = false;
    }

    @Override
    public void setTimeSeekbar(int mCurrentTime, int visible) {
        if (!mIsPlayed) return;
        seekBarTime.setProgress(mCurrentTime);
        tvCurrentTime.setVisibility(visible);
        tvCurrentTime.setText(AudioUtils.convertIntToTime(mCurrentTime));
    }

    @Override
    public void setIsPause(boolean b) {
        mIsPause = b;

    }

    @Override public boolean isShuffle() {
        return mIsShuffle;
    }

    @Override public Context getContext() {
        return this;
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
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.flContainer, playingListFragment);
//        fragmentTransaction.commit();
    }

    private void repeatEvent() {
        if (mIsRepeat) {
            btnRepeat.setTextColor(getResources().getColor(R.color.colorAccentDark));
        } else {
            btnRepeat.setTextColor(getResources().getColor(R.color.colorAccent));
        }
        mIsRepeat = !mIsRepeat;
        mPresenter.setRepeat(mIsRepeat);
        MyToast.show(btnRepeat, mIsRepeat ? "lặp lại" : "tắt lặp lại");
    }

    private void sufferEvent() {
        if (mIsShuffle) {
            btnShuffle.setTextColor(getResources().getColor(R.color.colorAccentDark));
            MyToast.show(btnShuffle, "tắt trộn bài");
        } else {
            btnShuffle.setTextColor(getResources().getColor(R.color.colorAccent));
            MyToast.show(btnShuffle, "bật trộn bài");
        }
        mIsShuffle = !mIsShuffle;
        mPresenter.setShuffle(mIsShuffle);
    }

    public void setSongAdapter(SongAdapter songAdapter) {
        this.mSongAdapter = songAdapter;
        mPresenter.setSongAdapter(songAdapter);
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        mPresenter.unregisterBroadcast();
    }

    @Override public boolean onQueryTextSubmit(String query) {
        if (mViewPager.getCurrentItem() != 2) {
            MyDialog.showError(this, "just use to search online!");
            return true;
        }
        onlineFragment.search(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (mViewPager.getCurrentItem() != 2) {
            return true;
        }
        return true;
    }
}


