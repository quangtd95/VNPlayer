package td.quang.vnplayer.views.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import td.quang.vnplayer.R;
import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.presenters.playoffline.PlayOfflinePresenter;
import td.quang.vnplayer.presenters.playoffline.PlayOfflinePresenterImpl;
import td.quang.vnplayer.views.BaseActivity;
import td.quang.vnplayer.views.BaseFragment;
import td.quang.vnplayer.views.adapters.MyViewPagerAdapter;
import td.quang.vnplayer.views.adapters.SongAdapter;
import td.quang.vnplayer.views.dialogs.MyDialog;
import td.quang.vnplayer.views.fragments.home.AlbumsFragment;
import td.quang.vnplayer.views.fragments.home.CloudFragment;
import td.quang.vnplayer.views.fragments.home.OnlineFragment;
import td.quang.vnplayer.views.fragments.home.SongsFragment;
import td.quang.vnplayer.views.fragments.playing.PlayingFragment;
import td.quang.vnplayer.views.fragments.playing.PlayingFragment_;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity implements MainView, SearchView.OnQueryTextListener, EventFromFragmentListener {
    @ViewById(R.id.slidingPanel) SlidingUpPanelLayout slidingPanel;
    @ViewById(R.id.tabLayout) TabLayout mTabLayout;
    @ViewById(R.id.viewPagerHome) ViewPager mViewPagerHome;
    @ViewById(R.id.toolbar) Toolbar toolbar;

    private SongAdapter mSongAdapter;
    private PlayOfflinePresenter mPresenter;
    private OnlineFragment onlineFragment;
    private SweetAlertDialog dialogLoading;
    private PlayingFragment playingFragment;
    private SearchView mSearchView;

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem itemSearch = menu.findItem(R.id.action_searchc);
        mSearchView = (SearchView) itemSearch.getActionView();
        mSearchView.setOnQueryTextListener(this);
        return true;
    }

    @Override protected void afterView() {
        setSupportActionBar(toolbar);
        addPlayingFragment();
        setUpHomeViewPager();

        mPresenter = PlayOfflinePresenterImpl.getInstance();
        mPresenter.setMainView(this);
        mPresenter.registerBroadcast();
        getCurrentState();

    }

    private void addPlayingFragment() {
        playingFragment = new PlayingFragment_();
        playingFragment.setListener(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frameLayoutPlaying, playingFragment);
        fragmentTransaction.commit();
    }

    private void setUpHomeViewPager() {
        List<BaseFragment> mHomeFragments = new ArrayList<>();
        SongsFragment songsFragment = new SongsFragment();
        songsFragment.setMainView(this);
        mHomeFragments.add(songsFragment);
        mHomeFragments.add(new AlbumsFragment());
        onlineFragment = new OnlineFragment();
        onlineFragment.setMainView(this);
        mHomeFragments.add(onlineFragment);
        mHomeFragments.add(new CloudFragment());

        MyViewPagerAdapter mHomeViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), mHomeFragments);
        mViewPagerHome.setAdapter(mHomeViewPagerAdapter);
        mViewPagerHome.setOffscreenPageLimit(4);
        mTabLayout.setupWithViewPager(mViewPagerHome, true);
        mTabLayout.dispatchSetSelected(true);
    }

    private void getCurrentState() {
        mPresenter.getCurrentState();
    }

    @Override public void setCurrentState(Intent intent) {
        Bundle bundle = intent.getExtras();
        boolean mIsShuffle = bundle.getBoolean("shuffle");
        boolean mIsRepeat = bundle.getBoolean("repeat");
        boolean mIsPlayed = bundle.getBoolean("playing");
        Song song = bundle.getParcelable("song");
        int currentPosition = bundle.getInt("position", 0);
        int mCurrentTime = bundle.getInt("time", 0);

        if (song != null) {
            playView(song, currentPosition, !mIsPlayed);
            playingFragment.setCurrentTime(mCurrentTime);
            slidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        }
        playingFragment.setRepeatView(mIsRepeat);
        playingFragment.setShuffleView(mIsShuffle);
    }

    public void setUpSlidingPanel(RelativeLayout dragView) {
        final TypedArray styledAttributes = this.getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.actionBarSize});
        int height = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        slidingPanel.setPanelHeight(height);
        slidingPanel.addPanelSlideListener(new SlidingPanelListener(this, dragView));

    }

    @Override public void slidingDown() {
        slidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
    }

    @Override public void slidingUp() {
        slidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
    }


    @Override public void setTimeSeekBar(int mCurrentTime, int visible) {
        playingFragment.setTimeSeekBar(mCurrentTime, visible);
    }

    @Override public void setCurrentPosition(int position) {
        playingFragment.setCurrentPositionPlaylist(position);
    }

    @Override public Context getContext() {
        return this;
    }

    @Override public void updatePlayList() {
        playingFragment.updatePlayList();
    }

    public void setSongAdapter(SongAdapter songAdapter) {
        this.mSongAdapter = songAdapter;
    }

    @Override public boolean onQueryTextSubmit(String query) {
        if (mViewPagerHome.getCurrentItem() != 2) {
            MyDialog.showError(this, "just only use to search online!");
            return true;
        }
        onlineFragment.search(query);
        mSearchView.clearFocus();
        return true;
    }

    @Override public boolean onQueryTextChange(String newText) {
        if (mViewPagerHome.getCurrentItem() != 2) {
            return true;
        }
        return true;
    }

    @Override public void onPlayNewAction() {
        mSongAdapter.playSongOnClick(0);
    }

    @Override public void playView(Song song, int position, boolean isPause) {
        slidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
        playingFragment.setPlayingView(song);
        playingFragment.setCurrentPositionPlaylist(position);
        if (isPause) playingFragment.setPauseView();
        else {
            playingFragment.setResumeView();
        }
    }

    @Override public void onResumeAction() {
        mPresenter.resume();
    }

    @Override public void pauseView() {
        playingFragment.setPauseView();
    }

    @Override public void resumeView() {
        playingFragment.setResumeView();
    }

    @Override public void onPauseAction() {
        mPresenter.pause();
    }

    @Override public void onNextAction() {
        mPresenter.next();
    }

    @Override public void onPrevAction() {
        mPresenter.prev();
    }

    @Override public void onShuffleAction(boolean mIsShuffle) {
        mPresenter.setShuffle(mIsShuffle);
    }

    @Override public void onRepeatAction(boolean mIsRepeat) {
        mPresenter.setRepeat(mIsRepeat);
    }

    @Override public void showLoading() {
        dialogLoading = MyDialog.showLoading(getContext());
    }

    @Override public void hideLoading() {
        MyDialog.hideLoading(dialogLoading);
    }

    @Override public void showSuccess(String message) {
        MyDialog.showSuccess(getContext(), message);
    }

    @Override public void showError(String message) {
        MyDialog.showError(getContext());
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        mPresenter.unregisterBroadcast();
    }

    @Override public void onBackPressed() {
        if (slidingPanel.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
            slidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }
}


