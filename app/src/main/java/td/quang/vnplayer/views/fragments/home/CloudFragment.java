package td.quang.vnplayer.views.fragments.home;

import td.quang.vnplayer.views.BaseFragment;

/**
 * Created by Quang_TD on 12/28/2016.
 */

public class CloudFragment extends BaseFragment {
    private static CloudFragment sInstance;

    public static CloudFragment getInstance() {
        if (sInstance == null) {
            synchronized (CloudFragment.class) {
                if (sInstance == null) {
                    sInstance = new CloudFragment();
                    sInstance.setName("Cloud");
                }
            }
        }
        return sInstance;
    }


    @Override protected void afterView() {

    }
}
