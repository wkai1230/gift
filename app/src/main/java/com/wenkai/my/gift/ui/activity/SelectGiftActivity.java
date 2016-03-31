package com.wenkai.my.gift.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.wenkai.my.gift.BaseActivity;
import com.wenkai.my.gift.R;
import com.wenkai.my.gift.bean.CategoryGiftActivityInfo;
import com.wenkai.my.gift.constants.Constants;
import com.wenkai.my.gift.http.IOkCallBack;
import com.wenkai.my.gift.http.OkHttpTool;
import com.wenkai.my.gift.ui.adapter.GiftRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by my on 2016/3/29.
 */
public class SelectGiftActivity extends BaseActivity {
    @Bind(R.id.rv_select_gift)
    RecyclerView rcSelect;

    private List<CategoryGiftActivityInfo.DataEntity.ItemsEntity> dateList = new ArrayList<>();
    private GiftRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_gift);
        ButterKnife.bind(this);

        initDate();
    }

    private void initDate() {
        if (dateList.isEmpty()) {
            OkHttpTool.newInstance(this).okGet(Constants.SELECT_GIFT
                    , CategoryGiftActivityInfo.class, new IOkCallBack<CategoryGiftActivityInfo>() {
                @Override
                public void onSuccess(CategoryGiftActivityInfo resultInfo) {
                    dateList.addAll(resultInfo.getData().getItems());
                    adapter.notifyDataSetChanged();
                }
            });
            rcSelect.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            adapter = new GiftRecyclerAdapter(this, dateList);
            rcSelect.setAdapter(adapter);
        }
    }
}
