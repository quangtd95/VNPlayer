package td.quang.vnplayer.views.fragments.home;

/**
 * Created by Quang_TD on 12/28/2016.
 */

public class CloudFragment extends HomeBaseFragment {
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

    @Override
    public void init() {

    }
}
