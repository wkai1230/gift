package com.wenkai.my.gift.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wenkai.my.gift.BaseFragment;
import com.wenkai.my.gift.R;
import com.wenkai.my.gift.bean.HomeHeartExpandInfo;
import com.wenkai.my.gift.bean.HotContextInfo;
import com.wenkai.my.gift.constants.Constants;
import com.wenkai.my.gift.http.IOkCallBack;
import com.wenkai.my.gift.http.OkHttpTool;
import com.wenkai.my.gift.ui.activity.HotContentActivity;
import com.wenkai.my.gift.ui.adapter.HotRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HotFragment extends BaseFragment {
    @Bind(R.id.recycle_view_hot_fragment)
    RecyclerView rvHotContent;

    private List<HotContextInfo.DataEntity.ItemsEntity> hotContentList = new ArrayList<>();

    private OnFragmentInteractionListener mListener;
    private View view;
    private HotRecyclerAdapter adapter;

    public HotFragment() {
    }

    public static HotFragment newInstance() {
        HotFragment fragment = new HotFragment();
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
            view = inflater.inflate(R.layout.fragment_hot, container, false);
        }
        ButterKnife.bind(this, view);
        //显示热门内容
        setupHotContent();

        return view;
    }

    private void setupHotContent() {
        if (hotContentList.isEmpty()) {
            OkHttpTool.newInstance(getActivity()).okGet(Constants.HOT_CONTENT_URL, HotContextInfo.class, new IOkCallBack<HotContextInfo>() {
                @Override
                public void onSuccess(HotContextInfo resultInfo) {
                    hotContentList.addAll(resultInfo.getData().getItems());
                    rvHotContent.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                    adapter = new HotRecyclerAdapter(getActivity(), hotContentList);
                    rvHotContent.setAdapter(adapter);
                }
            });
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
