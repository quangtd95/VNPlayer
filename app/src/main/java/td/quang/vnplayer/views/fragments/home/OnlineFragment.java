
package td.quang.vnplayer.views.fragments.home;

import td.quang.vnplayer.views.BaseFragment;

/**
 * Created by Quang_TD on 12/28/2016.
 */

public class OnlineFragment extends BaseFragment {
    private static OnlineFragment sInstance;

    public static OnlineFragment getInstance() {
        if (sInstance == null) {
            synchronized (OnlineFragment.class) {
                if (sInstance == null) {
                    sInstance = new OnlineFragment();
                    sInstance.setName("Online");
                }
            }
        }
        return sInstance;
    }


    @Override protected void afterView() {

    }
}
