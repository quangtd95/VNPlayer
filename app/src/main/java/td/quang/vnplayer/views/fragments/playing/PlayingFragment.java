package td.quang.vnplayer.views.fragments.playing;

import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;
import td.quang.vnplayer.R;
import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.presenters.playoffline.PlayOfflinePresenterImpl;
import td.quang.vnplayer.utils.AudioUtils;
import td.quang.vnplayer.views.BaseFragment;
import td.quang.vnplayer.views.activities.EventFromFragmentListener;
import td.quang.vnplayer.views.activities.SeekBarChangeListener;
import td.quang.vnplayer.views.adapters.MyViewPagerAdapter;
import td.quang.vnplayer.views.customviews.MyButton;


/**
 * Created by Quang_TD on 1/17/2017.
 */
@EFragment(R.layout.fragment_playing)
public class PlayingFragment extends BaseFragment {

    @ViewById(R.id.viewPagerPlaying) ViewPager mViewPagerPlaying;
    @ViewById(R.id.barPlaying) RelativeLayout dragView;
    @ViewById(R.id.heading) View heading;
    @ViewById(R.id.tvBarTitle) TextView tvBarTitle;
    @ViewById(R.id.tvBarArtist) TextView tvBarArtist;
    @ViewById(R.id.btnBarPlay) ImageButton btnBarPlay;
    @ViewById(R.id.ivBarThumb) ImageView ivBarThumb;
    @ViewById(R.id.llLayoutBarDetail) LinearLayout llLayoutBarDetail;
    @ViewById(R.id.tvHeadTitle) TextView tvHeadTitle;
    @ViewById(R.id.tvHeadArtist) TextView tvHeadArtist;
    @ViewById(R.id.seekBarTime) SeekBar seekBarTime;
    @ViewById(R.id.btnRepeat) MyButton btnRepeat;
    @ViewById(R.id.btnShuffle) MyButton btnShuffle;
    @ViewById(R.id.btnPlay) MyButton btnPlay;
    @ViewById(R.id.tvDuration) TextView tvDuration;
    @ViewById(R.id.tvCurrentTime) TextView tvCurrentTime;
    @ViewById(R.id.btnNext) MyButton btnNext;
    @ViewById(R.id.btnPrev) MyButton btnPrev;
    @ViewById(R.id.btnHome) MyButton btnHome;
    @ViewById(R.id.btnList) MyButton btnList;
    private PlayListFragment playListFragment;
    private AlbumCoverFragment albumCoverFragment;
    private boolean mIsRepeat;
    private boolean mIsShuffle;
    private boolean mIsPlayed;
    private boolean mIsPause;
    private boolean misShowPlaylist;

    @Setter
    private EventFromFragmentListener listener;

    @AfterViews
    protected void afterView() {
        PlayOfflinePresenterImpl mPresenter = PlayOfflinePresenterImpl.getInstance();
        setUpPlayingViewPager();
        seekBarTime.setOnSeekBarChangeListener(new SeekBarChangeListener(mPresenter));
        listener.setUpSlidingPanel(dragView);
    }

    public void setUpPlayingViewPager() {
        List<BaseFragment> mPlayingFragments = new ArrayList<>();
        albumCoverFragment = new AlbumCoverFragment();
        playListFragment = new PlayListFragment();
        mPlayingFragments.add(albumCoverFragment);
        mPlayingFragments.add(playListFragment);
        MyViewPagerAdapter mPlayingViewPagerAdapter = new MyViewPagerAdapter(getFragmentManager(), mPlayingFragments);
        mViewPagerPlaying.setAdapter(mPlayingViewPagerAdapter);


    }

    public void setRepeatView(boolean mIsRepeat) {
        if (mIsRepeat) {
            btnRepeat.setTextColor(getResources().getColor(R.color.colorAccent));
        } else {
            btnRepeat.setTextColor(getResources().getColor(R.color.colorAccentDark));
        }
    }

    public void setShuffleView(boolean mIsShuffle) {
        if (mIsShuffle) {
            btnShuffle.setTextColor(getResources().getColor(R.color.colorAccent));
        } else {
            btnShuffle.setTextColor(getResources().getColor(R.color.colorAccentDark));
        }
    }

    public void setPlayingView(Song song) {
        mIsPlayed = true;
        tvBarTitle.setText(song.getTitle());
        tvBarArtist.setText(song.getArtist());
        tvHeadTitle.setText(song.getTitle());
        tvHeadArtist.setText(song.getArtist());
        albumCoverFragment.startAnimation();
        btnPlay.setText(getResources().getString(R.string.ic_pause));
        btnBarPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_circle_outline_white_24dp));
        int maxDuration = AudioUtils.getDuration(getContext(), song);
        seekBarTime.setMax(maxDuration);
        seekBarTime.setProgress(0);
        tvDuration.setText(AudioUtils.convertIntToTime(maxDuration));
        Bitmap cover = AudioUtils.getAlbumCover(getContext(), song.getFilePath());

        if (cover != null) {
            albumCoverFragment.setAlbumCover(cover);
            ivBarThumb.setImageBitmap(cover);
        } else {
            albumCoverFragment.setAlbumCoverDefault();
            ivBarThumb.setImageDrawable(getResources().getDrawable(R.drawable.cover_thumbnail));
        }
    }

    public void setPauseView() {
        albumCoverFragment.stopAnimation();
        btnPlay.setText(getResources().getString(R.string.ic_play));
        btnBarPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_circle_outline_white_24dp));
    }

    public void setResumeView() {
        albumCoverFragment.startAnimation();
        btnPlay.setText(getResources().getString(R.string.ic_pause));
        btnBarPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_circle_outline_white_24dp));
    }

    public void setTimeSeekBar(int mCurrentTime, int visible) {
        seekBarTime.setProgress(mCurrentTime);
        tvCurrentTime.setVisibility(visible);
        tvCurrentTime.setText(AudioUtils.convertIntToTime(mCurrentTime));
    }

    @Click public void btnRepeatClicked() {
        mIsRepeat = !mIsRepeat;
        setRepeatView(mIsRepeat);
        listener.onRepeatAction(mIsRepeat);
    }

    @Click public void btnShuffleClicked() {
        mIsShuffle = !mIsShuffle;
        setShuffleView(mIsShuffle);
        listener.onShuffleAction(mIsShuffle);
    }

    @Click public void btnPlayClicked() {
        onPlayButton();
    }

    @Click public void btnBarPlayClicked() {
        onPlayButton();
    }

    @Click public void btnNextClicked() {
        if (!mIsPlayed) {
            return;
        }
        listener.onNextAction();
    }

    @Click public void btnPrevClicked() {
        if (!mIsPlayed) {
            return;
        }
        listener.onPrevAction();
    }

    @Click public void btnHomeClicked() {
        listener.slidingDown();
    }

    @Click public void llLayoutBarDetailClicked() {
        listener.slidingUp();
    }

    @Click public void btnListClicked() {
        misShowPlaylist = !misShowPlaylist;
        mViewPagerPlaying.setCurrentItem((misShowPlaylist) ? 1 : 0, true);
    }

    private void onPlayButton() {
        if (!mIsPlayed) {
            mIsPlayed = true;
            listener.onPlayNewAction();
        } else {
            if (mIsPause) {
                setResumeView();
                listener.onResumeAction();
            } else {
                setPauseView();
                listener.onPauseAction();
            }
            mIsPause = !mIsPause;
        }
    }

    public void setCurrentPositionPlaylist(int position) {
        playListFragment.setPositionPlaylist(position);
    }

    public void updatePlayList() {
        playListFragment.updatePlayList();
    }

    public void setCurrentTime(int mCurrentTime) {
        seekBarTime.setProgress(mCurrentTime);
    }


}
