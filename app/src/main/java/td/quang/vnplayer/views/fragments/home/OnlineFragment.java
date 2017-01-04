
package td.quang.vnplayer.views.fragments.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Quang_TD on 12/28/2016.
 */

public class OnlineFragment extends HomeBaseFragment {
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void init() {

    }
}
