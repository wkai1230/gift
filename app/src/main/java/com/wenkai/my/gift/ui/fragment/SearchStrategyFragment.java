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
import com.wenkai.my.gift.bean.ShowListInfo;
import com.wenkai.my.gift.constants.Constants;
import com.wenkai.my.gift.http.IOkCallBack;
import com.wenkai.my.gift.http.OkHttpTool;
import com.wenkai.my.gift.ui.adapter.ShowListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchStrategyFragment extends BaseFragment {
    @Bind(R.id.rv_search_strategy)
    RecyclerView rvSearchStrategy;

    private List<ShowListInfo.DataEntity.PostsEntity> dateList = new ArrayList<>();
    private OnFragmentInteractionListener mListener;
    private ShowListAdapter adapter;

    public SearchStrategyFragment() {
    }

    public static SearchStrategyFragment newInstance(String enCode) {
        SearchStrategyFragment fragment = new SearchStrategyFragment();
        Bundle args = new Bundle();
        args.putString("enCode",enCode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_strategy, container, false);
        ButterKnife.bind(this, view);

        String enCode = getArguments().getString("enCode");

        initDate(enCode);
        return view;
    }

    private void initDate(String enCode) {
        if (dateList.isEmpty()) {
            OkHttpTool.newInstance(getActivity()).okGet(Constants.SEARCH_STRATEGY + enCode, ShowListInfo.class
                    , new IOkCallBack<ShowListInfo>() {
                @Override
                public void onSuccess(ShowListInfo resultInfo) {
                    dateList.addAll(resultInfo.getData().getPosts());
                    adapter.notifyDataSetChanged();
                }
            });
        }

        rvSearchStrategy.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        adapter = new ShowListAdapter(getActivity(),dateList);
        rvSearchStrategy.setAdapter(adapter);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}