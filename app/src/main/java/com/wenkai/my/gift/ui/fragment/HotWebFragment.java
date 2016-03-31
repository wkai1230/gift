package com.wenkai.my.gift.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.wenkai.my.gift.BaseFragment;
import com.wenkai.my.gift.R;
import com.wenkai.my.gift.bean.HotLikeInfo;
import com.wenkai.my.gift.bean.MyGridView;
import com.wenkai.my.gift.bean.MyRecyclerView;
import com.wenkai.my.gift.constants.Constants;
import com.wenkai.my.gift.http.IOkCallBack;
import com.wenkai.my.gift.http.OkHttpTool;
import com.wenkai.my.gift.ui.adapter.HotLikeGridAdapter;
import com.wenkai.my.gift.ui.adapter.HotLikeRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HotWebFragment extends BaseFragment {
    @Bind(R.id.wv_hot_pic)
    WebView wbHot;
    @Bind(R.id.rc_hot_like)
    MyGridView rvHotLike;

    private List<HotLikeInfo.DataEntity.RecommendItemsEntity> dataList = new ArrayList<>();
    private OnFragmentInteractionListener mListener;
    private HotLikeGridAdapter adapter;

    public HotWebFragment() {
    }

    public static HotWebFragment newInstance(String webHtml,int id) {
        HotWebFragment fragment = new HotWebFragment();
        Bundle args = new Bundle();
        args.putString("webHtml", webHtml);
        args.putInt("id",id);
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
        View view = inflater.inflate(R.layout.fragment_hot_web, container, false);
        ButterKnife.bind(this,view);

        Bundle arguments = getArguments();
        String webHtml = arguments.getString("webHtml");
        int id = arguments.getInt("id");
        initDate(id);

        wbHot.getSettings().setJavaScriptEnabled(true);
        wbHot.loadDataWithBaseURL(null, webHtml, "text/html", "utf-8", null);

        return view;
    }

    private void initDate(int id) {
        if (dataList.isEmpty()) {
            OkHttpTool.newInstance(getActivity()).okGet(Constants.HOT_LIKE_HEADER + id + Constants.HOT_LIKE_END
                    , HotLikeInfo.class, new IOkCallBack<HotLikeInfo>() {
                @Override
                public void onSuccess(HotLikeInfo resultInfo) {
                    dataList.addAll(resultInfo.getData().getRecommend_items());
                    adapter.notifyDataSetChanged();
                }
            });
        }

        adapter = new HotLikeGridAdapter(getActivity(),dataList);
        rvHotLike.setAdapter(adapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
