package com.wenkai.my.gift.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wenkai.my.gift.BaseFragment;

import java.util.List;

/**
 * Created by my on 2016/3/19.
 */
public class CategoryPagerAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> fragmentList;

    public CategoryPagerAdapter(FragmentManager fm,List<BaseFragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
