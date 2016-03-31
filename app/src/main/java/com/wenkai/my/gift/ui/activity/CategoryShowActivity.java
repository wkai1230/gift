package com.wenkai.my.gift.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wenkai.my.gift.BaseActivity;
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

/**
 * Created by my on 2016/3/24.
 */
public class CategoryShowActivity extends BaseActivity {
    @Bind(R.id.recycle_view_category_show)
    RecyclerView rvShow;
    @Bind(R.id.tv_category_show)
    TextView tvShow;
    @Bind(R.id.btn_category_show)
    ImageButton btnBack;

    private int id;

    private ShowListInfo.DataEntity dateEntity ;
    private List<ShowListInfo.DataEntity.PostsEntity> dateList = new ArrayList<>();
    private ShowListAdapter adapter;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_show);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);
        title = intent.getStringExtra("title");

        tvShow.setText(title);

        initDate();
        initListener();
    }

    private void initListener() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initDate() {
        if (dateEntity == null) {
            OkHttpTool.newInstance(this).okGet(Constants.HOME_LIST_SHOW_HEADER + id + Constants.HOME_LIST_SHOW_END
                    , ShowListInfo.class, new IOkCallBack<ShowListInfo>() {
                @Override
                public void onSuccess(ShowListInfo resultInfo) {
                    dateEntity = resultInfo.getData();
                    dateList.addAll(dateEntity.getPosts());

                    adapter.notifyDataSetChanged();
                }
            });
        }

        rvShow.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter = new ShowListAdapter(this,dateList);
        rvShow.setAdapter(adapter);
    }
}
