package td.quang.vnplayer.views.fragments.playing;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import de.hdodenhof.circleimageview.CircleImageView;
import td.quang.vnplayer.R;
import td.quang.vnplayer.views.BaseFragment;

/**
 * Created by Quang_TD on 1/17/2017.
 */

public class AlbumCoverFragment extends BaseFragment {

    private CircleImageView ivAlbumCover;
    private Animation mAnim;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_image_cover, container, false);
        ivAlbumCover = (CircleImageView) mView.findViewById(R.id.ivImageAlbumCover);
        afterView();
        return mView;
    }

    @Override protected void afterView() {
        mAnim = AnimationUtils.loadAnimation(getContext(), R.anim.anim_rotate);
    }

    public void startAnimation() {
        ivAlbumCover.startAnimation(mAnim);

    }

    public void stopAnimation() {
        ivAlbumCover.clearAnimation();
    }

    public void setAlbumCover(Bitmap albumCover) {
        ivAlbumCover.setImageBitmap(albumCover);
    }

    public void setAlbumCoverDefault() {
        ivAlbumCover.setImageDrawable(getResources().getDrawable(R.drawable.cover_thumbnail));
    }
}
