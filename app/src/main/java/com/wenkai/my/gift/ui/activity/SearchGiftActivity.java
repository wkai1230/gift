package com.wenkai.my.gift.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wenkai.my.gift.BaseFragment;
import com.wenkai.my.gift.R;
import com.wenkai.my.gift.ui.adapter.HotViewPagerAdapter;
import com.wenkai.my.gift.ui.adapter.SearchPagerAdapter;
import com.wenkai.my.gift.ui.fragment.SearchGiftFragment;
import com.wenkai.my.gift.ui.fragment.SearchStrategyFragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchGiftActivity extends AppCompatActivity {

    @Bind(R.id.et_search_gift)
    EditText etSearch;
    @Bind(R.id.rg_search_gift)
    RadioGroup rgSearch;
    @Bind(R.id.vp_search_gift)
    ViewPager vpSearch;
    @Bind(R.id.btn_search_gift_back)
    ImageButton ibBack;
    @Bind(R.id.tv_search_gift)
    TextView tvSearch;
    @Bind(R.id.ib_search_gift_sort)
    ImageButton ibSort;
    @Bind(R.id.tv_line1_search)
    TextView tvLine1;
    @Bind(R.id.tv_line2_search)
    TextView tvLine2;
    @Bind(R.id.rb_search_gift)
    RadioButton rbGift;
    @Bind(R.id.fl_search)
    FrameLayout flSearch;


    private String title;
    private String enCode;

    private List<BaseFragment> fragmentList = new ArrayList<>();
    private SearchPagerAdapter adapter;
    private FragmentManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_gift);

        ButterKnife.bind(this);

        manager = getSupportFragmentManager();

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        enCode = intent.getStringExtra("enCode");

        setupView();
        initListener();

        rbGift.setChecked(true);
        etSearch.setText(title);

    }

    private void initListener() {
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(etSearch.getEditableText() == null)) {
                    ibSort.setVisibility(View.VISIBLE);
                    etSearch.clearFocus();

                    String str = etSearch.getEditableText().toString();
                    try {
                        enCode = URLEncoder.encode(str, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    setupView();
                } else {
                    Toast.makeText(SearchGiftActivity.this,"请输入关键字进行搜索。",Toast.LENGTH_SHORT).show();
                }
            }
        });

        rgSearch.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_search_gift:
                        tvLine1.setBackgroundColor(Color.RED);
                        tvLine2.setBackgroundColor(Color.GRAY);
                        vpSearch.setCurrentItem(0);
                    break;
                    case R.id.rb_search_strategy:
                        tvLine1.setBackgroundColor(Color.GRAY);
                        tvLine2.setBackgroundColor(Color.RED);
                        vpSearch.setCurrentItem(1);
                        break;
                }
            }
        });

        etSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    tvSearch.setVisibility(View.VISIBLE);
                    ibSort.setVisibility(View.GONE);
                }
            }
        });

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setupView() {
        tvSearch.setVisibility(View.GONE);
//        if (!fragmentList.isEmpty()){
//            fragmentList.clear();
//            FragmentTransaction fragmentTransaction = manager.beginTransaction();
//            for (int i = 0 ; i < fragmentList.size();i++) {
//                fragmentTransaction.remove(fragmentList.get(i));
//            }
//            fragmentTransaction.commit();
//        }

        SearchGiftFragment searchGiftFragment = SearchGiftFragment.newInstance(enCode);
        fragmentList.add(searchGiftFragment);
        SearchStrategyFragment searchStrategyFragment = SearchStrategyFragment.newInstance(enCode);
        fragmentList.add(searchStrategyFragment);


        adapter = new SearchPagerAdapter(manager,fragmentList);
        vpSearch.setAdapter(adapter);
    }
}
