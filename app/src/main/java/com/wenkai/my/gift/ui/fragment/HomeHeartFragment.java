package com.wenkai.my.gift.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.squareup.picasso.Picasso;
import com.wenkai.my.gift.BaseFragment;
import com.wenkai.my.gift.R;
import com.wenkai.my.gift.bean.HomeHeartExpandInfo;
import com.wenkai.my.gift.bean.HomeHeartRecyclerInfo;
import com.wenkai.my.gift.bean.HomeHeartViewPagerInfo;
import com.wenkai.my.gift.constants.Constants;
import com.wenkai.my.gift.http.IOkCallBack;
import com.wenkai.my.gift.http.OkHttpTool;
import com.wenkai.my.gift.ui.activity.HomeHeartExpandActivity;
import com.wenkai.my.gift.ui.activity.ListShowActivity;
import com.wenkai.my.gift.ui.adapter.HomeExpandListAdapter;
import com.wenkai.my.gift.ui.adapter.HomeRecyclerAdapter;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeHeartFragment extends BaseFragment {

    @Bind(R.id.lv_home_heart)
    PullToRefreshExpandableListView mRefreshView;

    private List<HomeHeartExpandInfo.DataEntity.ItemsEntity> listInfo = new ArrayList<>();
    private List<HomeHeartViewPagerInfo.DataEntity.BannersEntity> pagerList = new ArrayList<>();
    private List<HomeHeartRecyclerInfo.DataEntity.SecondaryBannersEntity> recyclerList = new ArrayList<>();
    /**
     * 分组数据源
     */
    private Map<String, List<HomeHeartExpandInfo.DataEntity.ItemsEntity>> mExpandDates = new HashMap<String, List<HomeHeartExpandInfo.DataEntity.ItemsEntity>>();
    private List<String> keyList = new ArrayList<String>();

    /**
     * HeaderView数据源
     */
    private List<String> headerPagerUrlList = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    private HomeExpandListAdapter mExpandAdapter;
    private HeaderView headerView;
    private HomeRecyclerAdapter recycleAdapter;
    private ExpandableListView mExpandableListView;

    public HomeHeartFragment() {
    }

    public static HomeHeartFragment newInstance() {
        HomeHeartFragment fragment = new HomeHeartFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_heart, container, false);
        ButterKnife.bind(this, view);

        clearData();

        mExpandableListView = mRefreshView.getRefreshableView();

        setupHeaderView();
        setupExpandListView();
        getHttpDate();

        initListener();

        return view;
    }

    private void clearData() {
        listInfo.clear();
        mExpandDates.clear();
        keyList.clear();
    }

    private void initListener() {
        mRefreshView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ExpandableListView>() {
            //下拉刷新
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                clearData();
                getHttpDate();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //当请求数据完成之后，执行onRefreshComplete方法，结束刷新动画
                        mRefreshView.onRefreshComplete();
                    }
                },1500);
            }

            //加载更多
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {

            }
        });


        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                HomeHeartExpandInfo.DataEntity.ItemsEntity itemsEntity = mExpandDates.get(keyList.get(i)).get(i1);

                Intent intent = new Intent(getActivity(), HomeHeartExpandActivity.class);
                intent.putExtra("webContentUrl", itemsEntity.getContent_url());
                intent.putExtra("webImageUrl", itemsEntity.getCover_webp_url());
                intent.putExtra("title", itemsEntity.getTitle());
                intent.putExtra("id", itemsEntity.getId());
                startActivity(intent);
                return true;
            }
        });
        headerView.cbHomeHeader.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (pagerList != null && pagerList.get(position) != null && pagerList.get(position).getTarget() != null) {
                    int id = pagerList.get(position).getTarget().getId();

                    Intent intent = new Intent(getActivity(), ListShowActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                }
            }
        });
    }

    private void setupHeaderView() {
        //获取ViewPager数据源
        OkHttpTool.newInstance(getActivity()).okGet(Constants.HOME_HEART_VIEW_PAGER_URL, HomeHeartViewPagerInfo.class, new IOkCallBack<HomeHeartViewPagerInfo>() {
            @Override
            public void onSuccess(HomeHeartViewPagerInfo resultInfo) {
                if (headerPagerUrlList.isEmpty()) {
                    pagerList.addAll(resultInfo.getData().getBanners());
                    for (int i = 0; i < pagerList.size(); i++) {
                        headerPagerUrlList.add(pagerList.get(i).getImage_url());
                    }
                }

                //设置ConvenientBanner
                headerView.cbHomeHeader.setPages(new CBViewHolderCreator() {
                    @Override
                    public Object createHolder() {
                        return new BannerHeaderView();
                    }
                }, headerPagerUrlList)
                        .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                        .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
            }
        });


        //获取RecyclerView数据源
        if (recyclerList.isEmpty()) {
            OkHttpTool.newInstance(getActivity()).okGet(Constants.HOME_HEART_RECYCLER_URL, HomeHeartRecyclerInfo.class, new IOkCallBack<HomeHeartRecyclerInfo>() {
                @Override
                public void onSuccess(HomeHeartRecyclerInfo resultInfo) {
                    recyclerList.addAll(resultInfo.getData().getSecondary_banners());
                    recycleAdapter.notifyDataSetChanged();
                }
            });
        }

        //加载Header视图
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.home_heart_hander_view, null);
        headerView = new HeaderView(view);

        headerView.rvHomeHeader.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recycleAdapter = new HomeRecyclerAdapter(getActivity(), recyclerList);
        headerView.rvHomeHeader.setAdapter(recycleAdapter);

        mExpandableListView.addHeaderView(view);
    }

    class BannerHeaderView implements Holder<String> {
        private ImageView mBannerImageView;

        @Override
        public View createView(Context context) {
            mBannerImageView = new ImageView(getActivity());
            mBannerImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return mBannerImageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            Picasso.with(getActivity()).load(data).into(mBannerImageView);
        }
    }

    class HeaderView {
        @Bind(R.id.convenient_banner_home_heart)
        ConvenientBanner cbHomeHeader;
        @Bind(R.id.recycler_view_home_heart)
        RecyclerView rvHomeHeader;

        public HeaderView(View view) {
            ButterKnife.bind(this, view);
        }
    }

    //获取ExpandList数据源
    private void getHttpDate() {
        if (keyList.isEmpty()) {
            OkHttpTool.newInstance(getActivity()).okGet(Constants.HOME_HEART_EXPAND_URL, HomeHeartExpandInfo.class, new IOkCallBack<HomeHeartExpandInfo>() {
                @Override
                public void onSuccess(HomeHeartExpandInfo homeHeartExpandInfo) {

                    //接收到服务器返回的数据
                    listInfo.addAll(homeHeartExpandInfo.getData().getItems());
                    for (int i = 0; i < listInfo.size(); i++) {
                        HomeHeartExpandInfo.DataEntity.ItemsEntity itemsEntity = listInfo.get(i);
                        //将获取时间转换为字符串形式
                        long time = itemsEntity.getPublished_at() * 1000L;
                        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd E");
                        String day = simple.format(new Date(time));

                        //每组的数据集合
                        List<HomeHeartExpandInfo.DataEntity.ItemsEntity> entityList = mExpandDates.get(day);
                        //将数据进行分组
                        if (entityList != null) {
                            entityList.add(itemsEntity);
                        } else {
                            keyList.add(day);
                            entityList = new ArrayList<>();
                            entityList.add(itemsEntity);
                            mExpandDates.put(day, entityList);
                        }
                    }


                    mExpandAdapter.notifyDataSetChanged();
                    for (int i = 0; i < keyList.size(); i++) {
                        mExpandableListView.expandGroup(i);
                    }

                }
            });
        }
    }

    //设置ExpandListView
    private void setupExpandListView() {
        mExpandAdapter = new HomeExpandListAdapter(getActivity(), mExpandDates, keyList);
        mExpandableListView.setAdapter(mExpandAdapter);

        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return true;
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        if (headerView != null) {
            headerView.cbHomeHeader.startTurning(3000);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if (headerView != null) {
            headerView.cbHomeHeader.stopTurning();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
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
}
