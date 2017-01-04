package td.quang.vnplayer.views.fragments.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import td.quang.vnplayer.R;
import td.quang.vnplayer.views.BaseFragment;
import td.quang.vnplayer.views.adapters.HomeViewPagerAdapter;

/**
 * Created by Quang_TD on 12/28/2016.
 */

public class HomeFragment extends BaseFragment {
    List<HomeBaseFragment> fragments;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private HomeViewPagerAdapter adapter;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        init();
        return view;
    }


    @Override
    public void init() {
        mTabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);

        fragments = new ArrayList<>();
        fragments.add(OnlineFragment.getInstance());
        fragments.add(CloudFragment.getInstance());
        fragments.add(SongsFragment.getInstance());
        fragments.add(AlbumsFragment.getInstance());
        adapter = new HomeViewPagerAdapter(getFragmentManager(), fragments);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager, true);
        mTabLayout.dispatchSetSelected(true);
    }
}
