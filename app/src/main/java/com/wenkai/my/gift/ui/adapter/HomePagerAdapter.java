package com.wenkai.my.gift.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.wenkai.my.gift.BaseFragment;

import java.util.List;

/**
 * Created by my on 2016/3/15.
 */
public class HomePagerAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> fragments;
    private List<String> titleLists;
    public HomePagerAdapter(FragmentManager fm, List<BaseFragment> fragments,List<String> titleLists) {
        super(fm);
        this.fragments = fragments;
        this.titleLists = titleLists;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments == null ? null : fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleLists.get(position);
    }
}
