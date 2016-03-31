package com.wenkai.my.gift.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.wenkai.my.gift.BaseFragment;
import com.wenkai.my.gift.R;
import com.wenkai.my.gift.ui.activity.SearchActivity;
import com.wenkai.my.gift.ui.adapter.CategoryPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CategoryFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener {
    @Bind(R.id.vp_category)
    ViewPager vpCategory;
    @Bind(R.id.rg_category)
    RadioGroup rgCategory;
    @Bind(R.id.rb_strategy_category)
    RadioButton rbStrategy;
    @Bind(R.id.rb_gift_category)
    RadioButton rbGift;
    @Bind(R.id.ib_category_search)
    ImageButton ibSearch;

    private List<BaseFragment> fragmentList = new ArrayList<>();

    private OnFragmentInteractionListener mListener;
    private View view;


    public CategoryFragment() {
    }

    public static CategoryFragment newInstance() {
        CategoryFragment fragment = new CategoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_category, container, false);
        }
        ButterKnife.bind(this, view);

        initFragmentList();
        CategoryPagerAdapter adapter = new CategoryPagerAdapter(getChildFragmentManager(),fragmentList);
        vpCategory.setAdapter(adapter);

        initListener();
        return view;
    }

    private void initListener() {
        rbStrategy.setChecked(true);
        rgCategory.setOnCheckedChangeListener(this);

        ibSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });

        vpCategory.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        rbStrategy.setChecked(true);
                        break;
                    case 1:
                        rbGift.setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initFragmentList() {
        CategoryStrategyFragment strategyFragment = CategoryStrategyFragment.newInstance();
        fragmentList.add(strategyFragment);

        CategoryGiftFragment giftFragment = CategoryGiftFragment.newInstance();
        fragmentList.add(giftFragment);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.rb_strategy_category:
                vpCategory.setCurrentItem(0);
                break;
            case R.id.rb_gift_category:
                vpCategory.setCurrentItem(1);
                break;
        }
    }
}
