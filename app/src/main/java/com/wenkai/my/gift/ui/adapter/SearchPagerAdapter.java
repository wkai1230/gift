package com.wenkai.my.gift.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import com.wenkai.my.gift.BaseFragment;

import java.util.List;

/**
 * Created by my on 2016/3/25.
 */
public class SearchPagerAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> fragmentList;
    private FragmentManager fm;

    public SearchPagerAdapter(FragmentManager fm, List<BaseFragment> fragmentList) {
        super(fm);
        this.fm = fm;
        this.fragmentList = fragmentList;
    }

    public void setFragments(List<BaseFragment> fragments) {
        if(this.fragmentList != null){
            FragmentTransaction ft = fm.beginTransaction();
            for(Fragment f:this.fragmentList){
                ft.remove(f);
            }
            ft.commit();
            ft=null;
            fm.executePendingTransactions();
        }
        this.fragmentList = fragments;
        notifyDataSetChanged();
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
