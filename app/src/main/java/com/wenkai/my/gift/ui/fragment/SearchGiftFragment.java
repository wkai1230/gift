package com.wenkai.my.gift.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wenkai.my.gift.BaseFragment;
import com.wenkai.my.gift.R;
import com.wenkai.my.gift.bean.SearchGiftRecycleInfo;
import com.wenkai.my.gift.constants.Constants;
import com.wenkai.my.gift.http.IOkCallBack;
import com.wenkai.my.gift.http.OkHttpTool;
import com.wenkai.my.gift.ui.adapter.SearchGiftRecycleAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchGiftFragment extends BaseFragment {
    @Bind(R.id.recycle_view_search_fragment)
    RecyclerView rvHotContent;

    private List<SearchGiftRecycleInfo.DataEntity.ItemsEntity> searchContentList = new ArrayList<>();

    private OnFragmentInteractionListener mListener;
    private View view;
    private SearchGiftRecycleAdapter adapter;

    public SearchGiftFragment() {
    }

    public static SearchGiftFragment newInstance(String enCode) {
        SearchGiftFragment fragment = new SearchGiftFragment();
        Bundle bundle = new Bundle();
        bundle.putString("enCode",enCode);
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
        if (view == null) {
            view = inflater.inflate(R.layout.search_gift, container, false);
        }
        ButterKnife.bind(this,view);

        Bundle arguments = getArguments();
        String enCode = arguments.getString("enCode");

//        initView();
        initDate(enCode);

        return view;
    }

    private void initView() {
        rvHotContent.setLayoutManager(new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL));
        adapter = new SearchGiftRecycleAdapter(getActivity(),searchContentList);
        rvHotContent.setAdapter(adapter);
    }

    private void initDate(String enCode) {
        if (searchContentList.isEmpty()){
            OkHttpTool.newInstance(getActivity()).okGet(Constants.SEARCH_GIFT + enCode, SearchGiftRecycleInfo.class, new IOkCallBack<SearchGiftRecycleInfo>() {
                @Override
                public void onSuccess(SearchGiftRecycleInfo resultInfo) {
                    searchContentList.addAll(resultInfo.getData().getItems());
                    initView();
//                    adapter.notifyDataSetChanged();
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
