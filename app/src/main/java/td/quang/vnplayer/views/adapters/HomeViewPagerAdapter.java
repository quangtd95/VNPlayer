package td.quang.vnplayer.views.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import td.quang.vnplayer.views.fragments.home.HomeBaseFragment;

/**
 * Created by djwag on 1/4/2017.
 */

public class HomeViewPagerAdapter extends FragmentPagerAdapter {
    List<HomeBaseFragment> fragments;

    public HomeViewPagerAdapter(FragmentManager fm, List<HomeBaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getName();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return (fragments == null) ? 0 : fragments.size();
    }
}
