
package td.quang.vnplayer.views.fragments.home;

import td.quang.vnplayer.views.BaseFragment;

/**
 * Created by Quang_TD on 12/28/2016.
 */

public class OnlineFragment extends BaseFragment {
    private static OnlineFragment instance;

    public static OnlineFragment getInstance() {
        if (instance == null) {
            synchronized (OnlineFragment.class) {
                if (instance == null) {
                    instance = new OnlineFragment();
                    instance.setName("Online");
                }
            }
        }
        return instance;
    }


    @Override protected void afterView() {

    }
}
