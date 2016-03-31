package com.wenkai.my.gift.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wenkai.my.gift.BaseActivity;
import com.wenkai.my.gift.R;
import com.wenkai.my.gift.bean.HomeHeartActivityLikeInfo;
import com.wenkai.my.gift.bean.MyListView;
import com.wenkai.my.gift.constants.Constants;
import com.wenkai.my.gift.http.IOkCallBack;
import com.wenkai.my.gift.http.OkHttpTool;
import com.wenkai.my.gift.ui.adapter.HomeHeartActivityLikeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by my on 2016/3/23.
 */
public class HomeHeartExpandActivity extends BaseActivity {
    private WebView wvHome;
    private ImageView ivHome;
    private TextView tvHome;
    private ImageButton ibHome;

    private List<HomeHeartActivityLikeInfo.DataEntity.RecommendPostsEntity> dateList = new ArrayList<>();
    private int id;
    private HomeHeartActivityLikeAdapter adapter;
    private MyListView lvHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_expand);

        Intent intent = getIntent();
        String webContentUrl = intent.getStringExtra("webContentUrl");
        String webImageUrl = intent.getStringExtra("webImageUrl");
        String title = intent.getStringExtra("title");
        id = intent.getIntExtra("id",0);

        initView();
        initDate();

        tvHome.setText(title);
        Picasso.with(this).load(webImageUrl).into(ivHome);
        wvHome.loadUrl(webContentUrl);

        initListener();
    }

    private void initDate() {
        if (dateList.isEmpty()){
            OkHttpTool.newInstance(this).okGet(
                    Constants.HOME_HEART_ACTIVITY_LIKE_HEADER + id + Constants.HOME_HEART_ACTIVITY_LIKE_END
                    , HomeHeartActivityLikeInfo.class, new IOkCallBack<HomeHeartActivityLikeInfo>() {
                        @Override
                        public void onSuccess(HomeHeartActivityLikeInfo resultInfo) {
                            dateList.addAll(resultInfo.getData().getRecommend_posts());
                            adapter.notifyDataSetChanged();
                        }
                    });
        }

        adapter = new HomeHeartActivityLikeAdapter(this,dateList);
        lvHome.setAdapter(adapter);
    }

    private void initView() {
        wvHome = (WebView) findViewById(R.id.wv_home_heart_activity);
        ivHome = (ImageView) findViewById(R.id.iv_home_heart_activity);
        tvHome = (TextView) findViewById(R.id.tv_home_heart_activity);
        ibHome = (ImageButton) findViewById(R.id.ib_home_heart_activity);
        lvHome = (MyListView)findViewById(R.id.lv_home_heart_activity);
    }

    private void initListener() {
        ibHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        lvHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String webContentUrl = dateList.get(i).getContent_url();
                String webImageUrl = dateList.get(i).getCover_image_url();
                String title = dateList.get(i).getTitle();
                int id = dateList.get(i).getId();

                Intent intent = new Intent(HomeHeartExpandActivity.this,LikeActivity.class);
                intent.putExtra("webContentUrl",webContentUrl);
                intent.putExtra("webImageUrl",webImageUrl);
                intent.putExtra("title",title);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
    }
}
