package com.wenkai.my.gift.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wenkai.my.gift.BaseFragment;
import com.wenkai.my.gift.R;
import com.wenkai.my.gift.bean.HomeHeartExpandInfo;
import com.wenkai.my.gift.constants.Constants;
import com.wenkai.my.gift.http.IOkCallBack;
import com.wenkai.my.gift.http.OkHttpTool;
import com.wenkai.my.gift.ui.adapter.HomeOtherRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeOtherFragment extends BaseFragment {
    @Bind(R.id.recycle_view_home_other)
    RecyclerView recyclerView;

    private OnFragmentInteractionListener mListener;
    private List<HomeHeartExpandInfo.DataEntity.ItemsEntity> dateList = new ArrayList<>();
    private HomeOtherRecyclerAdapter otherAdapter;

    public HomeOtherFragment() {
    }


    public static HomeOtherFragment newInstance(int id) {
        HomeOtherFragment fragment = new HomeOtherFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id",id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home_other, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        int id = bundle.getInt("id");
        setupView(id);
        return view;
    }


    private void setupView(int id) {
        if (dateList.isEmpty()) {
            OkHttpTool.newInstance(getActivity()).okGet(Constants.HOME_HEART_PAGE_URL_HEADER + id + Constants.HOME_HEART_PAGE_URL_END
                    , HomeHeartExpandInfo.class, new IOkCallBack<HomeHeartExpandInfo>() {
                @Override
                public void onSuccess(HomeHeartExpandInfo resultInfo) {
                    dateList.addAll(resultInfo.getData().getItems());
                    otherAdapter.notifyDataSetChanged();
                }
            });
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        otherAdapter = new HomeOtherRecyclerAdapter(getActivity(),dateList);
        recyclerView.setAdapter(otherAdapter);
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
