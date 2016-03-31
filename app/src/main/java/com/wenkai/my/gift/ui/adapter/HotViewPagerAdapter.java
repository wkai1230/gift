package com.wenkai.my.gift.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.wenkai.my.gift.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by my on 2016/3/25.
 */
public class HotViewPagerAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> fragmentList;
    private List<String> titleList;

    public HotViewPagerAdapter(FragmentManager fm,List<BaseFragment> fragmentList, List<String> titleList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
            return titleList.get(position);
    }
}
