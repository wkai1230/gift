package com.wenkai.my.gift.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wenkai.my.gift.BaseActivity;
import com.wenkai.my.gift.R;
import com.wenkai.my.gift.bean.MyViewGroup;
import com.wenkai.my.gift.bean.SearchHotInfo;
import com.wenkai.my.gift.constants.Constants;
import com.wenkai.my.gift.http.IOkCallBack;
import com.wenkai.my.gift.http.OkHttpTool;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by my on 2016/3/26.
 */
public class SearchActivity extends BaseActivity{
    @Bind(R.id.btn_search_back)
    ImageButton ibBack;
    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.tv_search)
    TextView tvSearch;
    @Bind(R.id.vg_search)
    MyViewGroup vgSearch;
    @Bind(R.id.ll_search_select)
    LinearLayout llSearchSelect;

    private SearchHotInfo.DataEntity dataEntity = null;
    private List<String> hotList = new ArrayList<>();
    private String enCode;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        initDate();
        initListener();
    }

    private void initListener() {
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = etSearch.getEditableText().toString();
                if (str == null || str.trim().length() == 0){
                    Toast.makeText(SearchActivity.this,"请输入关键字进行搜索。",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    try {
                        enCode = URLEncoder.encode(str,"UTF-8");
                        title =str;
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                search();
            }
        });

        llSearchSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this,SelectGiftActivity.class);
                startActivity(intent);
            }
        });
    }

    private void search() {
        Intent intent = new Intent(this,SearchGiftActivity.class);
        intent.putExtra("enCode",enCode);
        intent.putExtra("title",title);
        startActivity(intent);
    }

    private void initDate() {
        if (dataEntity == null) {
            OkHttpTool.newInstance(this).okGet(Constants.SEARCH_HOT_WORD, SearchHotInfo.class
                    , new IOkCallBack<SearchHotInfo>() {
                @Override
                public void onSuccess(SearchHotInfo resultInfo) {
                    dataEntity = resultInfo.getData();
                    hotList.addAll(dataEntity.getHot_words());
                    setKeyWord();
                }
            });
        }
    }

    private void setKeyWord() {
        TextView tvView = null;
        for (int i = 0 ; i < hotList.size() ; i ++){
            tvView = new TextView(this);
            tvView.setBackgroundResource(R.drawable.shape_shape_group);
            tvView.setHeight(30);
            tvView.setClickable(true);
            tvView.setPadding(20, 10, 20, 0);
            tvView.setGravity(Gravity.CENTER);
            tvView.setText(hotList.get(i));
            tvView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView text = (TextView) view;
                    try {
                        enCode = URLEncoder.encode(text.getText().toString(), "UTF-8");
                        title = text.getText().toString();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    search();
                }
            });
            vgSearch.addView(tvView);
        }
    }

}
