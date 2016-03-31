package com.wenkai.my.gift.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wenkai.my.gift.BaseActivity;
import com.wenkai.my.gift.R;
import com.wenkai.my.gift.bean.CategoryGiftActivityInfo;
import com.wenkai.my.gift.constants.Constants;
import com.wenkai.my.gift.http.IOkCallBack;
import com.wenkai.my.gift.http.OkHttpTool;
import com.wenkai.my.gift.ui.adapter.GiftRecyclerAdapter;
import com.wenkai.my.gift.ui.adapter.HotRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by my on 2016/3/25.
 */
public class CategoryGiftActivity extends BaseActivity {
    @Bind(R.id.recycle_view_category_gift_activity)
    RecyclerView rvShow;
    @Bind(R.id.tv_category_gift_activity)
    TextView tvShow;
    @Bind(R.id.btn_category_gift_activity)
    ImageButton btnBack;

    private int id;
    private String title;

    private List<CategoryGiftActivityInfo.DataEntity.ItemsEntity> dateList = new ArrayList<>();
    private GiftRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_category);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);
        title = intent.getStringExtra("title");


        tvShow.setText(title);

        initDate();

        initListener();
    }

    private void initDate() {
        if (dateList.isEmpty()){
            OkHttpTool.newInstance(this).okGet(Constants.CATEGORY_GIFT_CONTENT_HEART + id + Constants.CATEGORY_GIFT_CONTENT_END
                    , CategoryGiftActivityInfo.class, new IOkCallBack<CategoryGiftActivityInfo>() {
                @Override
                public void onSuccess(CategoryGiftActivityInfo resultInfo) {
                    dateList.addAll(resultInfo.getData().getItems());
                    adapter.notifyDataSetChanged();
                }
            });
        }

        rvShow.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter = new GiftRecyclerAdapter(this, dateList);
        rvShow.setAdapter(adapter);
    }

    private void initListener() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
