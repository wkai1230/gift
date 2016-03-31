package com.wenkai.my.gift.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import com.wenkai.my.gift.BaseFragment;
import com.wenkai.my.gift.R;
import com.wenkai.my.gift.bean.CategoryExpandInfo;
import com.wenkai.my.gift.bean.CategoryHeaderRecyclerInfo;
import com.wenkai.my.gift.constants.Constants;
import com.wenkai.my.gift.http.IOkCallBack;
import com.wenkai.my.gift.http.OkHttpTool;
import com.wenkai.my.gift.ui.activity.ListShowActivity;
import com.wenkai.my.gift.ui.adapter.CategoryExpandAdapter;
import com.wenkai.my.gift.ui.adapter.CategoryStrategyRecycleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CategoryStrategyFragment extends BaseFragment {
    @Bind(R.id.elv_category_strategy)
    ExpandableListView elvStrategy;

    Map<String,List<CategoryExpandInfo.DataEntity.ChannelGroupsEntity>> map = new HashMap<>();
    //Gson解析出的数据
    private List<CategoryExpandInfo.DataEntity.ChannelGroupsEntity> dateList = new ArrayList<>();
    //组名集合
    private List<String> keyList = new ArrayList<>();
    //头部RecycleView资源数组
    private List<CategoryHeaderRecyclerInfo.DataEntity.CollectionsEntity> recyclerList = new ArrayList<>();


    private OnFragmentInteractionListener mListener;
    private View view;
    private CategoryExpandAdapter adapter;
    private HeaderViewHolder headerViewHolder;
    private CategoryStrategyRecycleAdapter recycleAdapter;

    public CategoryStrategyFragment() {
    }

    public static CategoryStrategyFragment newInstance() {
        CategoryStrategyFragment fragment = new CategoryStrategyFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null){
            view = inflater.inflate(R.layout.fragment_category_strategy, container, false);
        }
        ButterKnife.bind(this, view);

        initHeader();
        initDate();
        initExpandList();

        return view;
    }

    private void initHeader() {
        if (recyclerList.isEmpty()) {
            OkHttpTool.newInstance(getActivity()).okGet(Constants.CATEGORY_STRATEGY_RECYCLER_URL
                    , CategoryHeaderRecyclerInfo.class, new IOkCallBack<CategoryHeaderRecyclerInfo>() {
                @Override
                public void onSuccess(CategoryHeaderRecyclerInfo resultInfo) {
                    recyclerList.addAll(resultInfo.getData().getCollections());

                    recycleAdapter.notifyDataSetChanged();
                }
            });
        }

        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.category_header,null);
        headerViewHolder = new HeaderViewHolder(headerView);

        headerViewHolder.rvCategoryStrategy.setLayoutManager(
                new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        recycleAdapter = new CategoryStrategyRecycleAdapter(getActivity(),recyclerList);
        headerViewHolder.rvCategoryStrategy.setAdapter(recycleAdapter);

        elvStrategy.addHeaderView(headerView);
    }

    class HeaderViewHolder{
        @Bind(R.id.recycle_view_category_strategy)
        RecyclerView rvCategoryStrategy;

        public HeaderViewHolder(View headerView) {
            ButterKnife.bind(this,headerView);
        }
    }

    private void initExpandList() {
        adapter = new CategoryExpandAdapter(getActivity(),map,keyList);
        elvStrategy.setAdapter(adapter);

        elvStrategy.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return true;
            }
        });
    }

    private void initDate() {
        if (dateList.isEmpty()){
            OkHttpTool.newInstance(getActivity()).okGet(Constants.CATEGORY_STRATEGY_URL, CategoryExpandInfo.class, new IOkCallBack<CategoryExpandInfo>() {
                @Override
                public void onSuccess(CategoryExpandInfo resultInfo) {
                    dateList.addAll(resultInfo.getData().getChannel_groups());

                    for (int i = 0 ; i < dateList.size()  ; i ++) {
                        CategoryExpandInfo.DataEntity.ChannelGroupsEntity groupsEntity = dateList.get(i);
                        String key = groupsEntity.getName();

                        List<CategoryExpandInfo.DataEntity.ChannelGroupsEntity> entityList = map.get(key);
                        if (entityList != null){
                            entityList.add(groupsEntity);
                        } else {
                            keyList.add(key);
                            entityList = new ArrayList<CategoryExpandInfo.DataEntity.ChannelGroupsEntity>();
                            entityList.add(groupsEntity);
                            map.put(key,entityList);
                        }
                    }
                    for (int i = 0 ; i < keyList.size() ; i ++) {
                        elvStrategy.expandGroup(i);
                    }

                   adapter.notifyDataSetChanged();
                }
            });
        }
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
