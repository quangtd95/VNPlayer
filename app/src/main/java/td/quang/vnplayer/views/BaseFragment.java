package td.quang.vnplayer.views;

import android.support.v4.app.Fragment;

/**
 * Created by djwag on 1/4/2017.
 */

public abstract class BaseFragment extends Fragment {
    private String name;

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected abstract void afterView();
}
