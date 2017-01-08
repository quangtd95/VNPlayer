package td.quang.vnplayer.views.fragments.home;

import td.quang.vnplayer.views.BaseFragment;

/**
 * Created by Quang_TD on 12/28/2016.
 */

public class CloudFragment extends BaseFragment {
    private static CloudFragment instance;

    public static CloudFragment getInstance() {
        if (instance == null) {
            synchronized (CloudFragment.class) {
                if (instance == null) {
                    instance = new CloudFragment();
                    instance.setName("Cloud");
                }
            }
        }
        return instance;
    }


    @Override protected void afterView() {

    }
}
