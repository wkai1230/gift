package com.wenkai.my.gift.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wenkai.my.gift.BaseFragment;
import com.wenkai.my.gift.R;
import com.wenkai.my.gift.bean.HotCommentInfo;
import com.wenkai.my.gift.bean.MyListView;
import com.wenkai.my.gift.constants.Constants;
import com.wenkai.my.gift.http.IOkCallBack;
import com.wenkai.my.gift.http.OkHttpTool;
import com.wenkai.my.gift.ui.adapter.HotCommentListAdapter;

import java.util.ArrayList;
import java.util.List;

public class HotCommentFragment extends BaseFragment {
    private OnFragmentInteractionListener mListener;
    private MyListView lvComment;
    private List<HotCommentInfo.DataEntity.CommentsEntity> dataList = new ArrayList<>();
    private HotCommentListAdapter adapter;

    public HotCommentFragment() {
    }

    public static HotCommentFragment newInstance(int id) {
        HotCommentFragment fragment = new HotCommentFragment();
        Bundle args = new Bundle();
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
        View view = inflater.inflate(R.layout.fragment_hot_comment, container, false);

        Bundle arguments = getArguments();
        int id = arguments.getInt("id");

        initDate(id);

        lvComment = (MyListView) view.findViewById(R.id.lv_hot_comment);

        return view;
    }

    private void initDate(int id) {
        if (dataList.isEmpty()) {
            OkHttpTool.newInstance(getActivity()).okGet(Constants.HOT_COMMENTS_HEARD + id + Constants.HOT_COMMENTS_END
                    , HotCommentInfo.class, new IOkCallBack<HotCommentInfo>() {
                @Override
                public void onSuccess(HotCommentInfo resultInfo) {
                    dataList.addAll(resultInfo.getData().getComments());
                    adapter = new HotCommentListAdapter(getActivity(),dataList);
                    lvComment.setAdapter(adapter);
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
