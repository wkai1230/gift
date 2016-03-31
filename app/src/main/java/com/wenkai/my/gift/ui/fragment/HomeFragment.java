package com.wenkai.my.gift.ui.fragment;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.PaintDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wenkai.my.gift.BaseFragment;
import com.wenkai.my.gift.R;
import com.wenkai.my.gift.bean.ChannelsInfo;
import com.wenkai.my.gift.constants.Constants;
import com.wenkai.my.gift.http.HttpUtils;
import com.wenkai.my.gift.http.IOkCallBack;
import com.wenkai.my.gift.http.OkHttpTool;
import com.wenkai.my.gift.http.RequestCallBack;
import com.wenkai.my.gift.ui.activity.SearchActivity;
import com.wenkai.my.gift.ui.adapter.HomePagerAdapter;
import com.wenkai.my.gift.ui.adapter.PopGridAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 精选界面
 * created by wkai at 2016.3.15
 */
public class HomeFragment extends BaseFragment {
    private View view;

    /**
     * Fragment和Activity通信的接口，采用接口回调
     */
    private OnFragmentInteractionListener mListener;

    private List<ChannelsInfo.DataEntity.ChannelsEntity> dateList = new ArrayList<ChannelsInfo.DataEntity.ChannelsEntity>();
    private List<BaseFragment> fragments = new ArrayList<BaseFragment>();
    private List<String> titleLists = new ArrayList<String>();
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ImageButton mIbSearch;
    private ImageButton mIbDown;
    private ImageButton mIbUp;
    private TextView mTvPopShow;
    private PopupWindow popWindow;

    /**
     * 无参构造器
     */
    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * 工厂方法模式，用于创建当前的Fragment对象
     * @return
     */
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null){
            view =  inflater.inflate(R.layout.fragment_home, container, false);
            mTabLayout = (TabLayout)view.findViewById(R.id.tl_home_fragment);
            mViewPager = (ViewPager)view.findViewById(R.id.vp_home);
            mIbSearch = (ImageButton) view.findViewById(R.id.ib_search_home);
            mIbDown = (ImageButton) view.findViewById(R.id.ib_home_fragment_down);
            mIbUp = (ImageButton) view.findViewById(R.id.ib_home_fragment_up);
            mTvPopShow = (TextView) view.findViewById(R.id.tv_pop_show);
        }

        mIbUp.setVisibility(View.GONE);
        mTvPopShow.setVisibility(View.GONE);
        showPopWindow(view);
        initListener();
        initDate();
        return view;
    }

    private void initListener() {
        mIbSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });

        mIbDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 设置好参数之后再show
                popShow();
                popWindow.showAsDropDown(view);
            }
        });

        mIbUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popDismiss();
                popWindow.dismiss();
            }
        });
    }

    private void showPopWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.pop_window, null);
        GridView gvPopWindow = (GridView)contentView.findViewById(R.id.gv_pop_window);
        PopGridAdapter popAdapter = new PopGridAdapter(getActivity(),titleLists);
        gvPopWindow.setAdapter(popAdapter);

        gvPopWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mViewPager.setCurrentItem(i);
                popWindow.dismiss();
            }
        });

        popWindow = new PopupWindow(contentView,
                GridLayout.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.WRAP_CONTENT, true);

        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popWindow.setTouchable(true);
             return false;
            }
        });


        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (!popWindow.isShowing()) {
                    //改变显示的按钮图片为正常状态
                    popDismiss();
                }
            }
        });

        popWindow.setOutsideTouchable(true);
        popWindow.setTouchable(false);

        popWindow.setBackgroundDrawable(new PaintDrawable());
    }

    private void popShow() {
        mIbDown.setVisibility(View.GONE);
        mIbUp.setVisibility(View.VISIBLE);
        mTabLayout.setVisibility(View.GONE);
        mTvPopShow.setVisibility(View.VISIBLE);
    }

    private void popDismiss() {
        mIbDown.setVisibility(View.VISIBLE);
        mIbUp.setVisibility(View.GONE);
        mTabLayout.setVisibility(View.VISIBLE);
        mTvPopShow.setVisibility(View.GONE);
    }

    private void initDate() {
        OkHttpTool.newInstance(getActivity()).okGet(Constants.HOME_TABLAYOUT_URL, ChannelsInfo.class, new IOkCallBack<ChannelsInfo>() {
            @Override
            public void onSuccess(ChannelsInfo resultInfo) {
                dateList.addAll(resultInfo.getData().getChannels());
                setupViewPager();
            }
        });
    }

    private void setupViewPager() {
        fragments.add(HomeHeartFragment.newInstance());
        titleLists.add(dateList.get(0).getName());
        for (int i = 1 ; i < dateList.size() ; i ++){
            titleLists.add(dateList.get(i).getName());
            fragments.add(HomeOtherFragment.newInstance(dateList.get(i).getId()));
        }
        HomePagerAdapter adapter = new HomePagerAdapter(getChildFragmentManager(),fragments,titleLists);
        mViewPager.setAdapter(adapter);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setupWithViewPager(mViewPager);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
