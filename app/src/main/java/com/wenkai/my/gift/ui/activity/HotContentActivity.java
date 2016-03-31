package com.wenkai.my.gift.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.squareup.picasso.Picasso;
import com.wenkai.my.gift.BaseActivity;
import com.wenkai.my.gift.BaseFragment;
import com.wenkai.my.gift.R;
import com.wenkai.my.gift.bean.HotActivityInfo;
import com.wenkai.my.gift.bean.MyScrollView;
import com.wenkai.my.gift.bean.MyViewPager;
import com.wenkai.my.gift.constants.Constants;
import com.wenkai.my.gift.http.IOkCallBack;
import com.wenkai.my.gift.http.OkHttpTool;
import com.wenkai.my.gift.shear.ShearShow;
import com.wenkai.my.gift.ui.adapter.HotViewPagerAdapter;
import com.wenkai.my.gift.ui.fragment.HotCommentFragment;
import com.wenkai.my.gift.ui.fragment.HotWebFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class HotContentActivity extends BaseActivity {
    @Bind(R.id.convenient_banner_hot)
    ConvenientBanner cbHotPager;
    @Bind(R.id.tv_title_hot_activity)
    TextView tvTitle;
    @Bind(R.id.tv_price_hot_activity)
    TextView tvPrice;
    @Bind(R.id.tv_hot)
    TextView tvHot;
    @Bind(R.id.vp_hot_activity)
    MyViewPager vpHotContent;
    @Bind(R.id.tl_hot_activity)
    TabLayout tlHot;
    @Bind(R.id.sv_hot)
    NestedScrollView svHot;
    @Bind(R.id.fl_hot)
    FrameLayout tbHot;
    @Bind(R.id.ib_hot_share)
    ImageButton ibShare;

    private HotWebFragment webFragment;

    private List<BaseFragment> fragmentList = new ArrayList<>();
    private HotActivityInfo.DataEntity dataEntity;
    private int id;
    private FragmentManager manager;
    private HotCommentFragment commentFragment;
    private HotViewPagerAdapter hotViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_content);

        manager = getSupportFragmentManager();
        ButterKnife.bind(this);


        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);

        initDate();
        initListener();
    }

    private void initListener() {
        svHot.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                tbHot.setBackgroundColor(0xffff2d47);
                float scroll = scrollY / 500f;
                tbHot.setAlpha(scroll);
            }
        });


        ibShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShearShow.show(HotContentActivity.this);
            }
        });
    }

    private List<String> titleList = new ArrayList<>();
    private void initView() {
            cbHotPager.setPages(new CBViewHolderCreator() {
                @Override
                public Object createHolder() {
                    return new BannerHolder();
                }
            }, dataEntity.getImage_urls())
                    .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);

        tvTitle.setText(dataEntity.getName());
        tvPrice.setText(dataEntity.getPrice());
        tvHot.setText(dataEntity.getDescription());
        titleList.add("图文简介");
        titleList.add("评论("+dataEntity.getComments_count()+")");

        webFragment = HotWebFragment.newInstance(dataEntity.getDetail_html(),dataEntity.getId());
        fragmentList.add(webFragment);
        commentFragment = HotCommentFragment.newInstance(dataEntity.getId());
        fragmentList.add(commentFragment);

        hotViewPagerAdapter = new HotViewPagerAdapter(getSupportFragmentManager(),fragmentList,titleList);
        vpHotContent.setAdapter(hotViewPagerAdapter);

        tlHot.setTabMode(TabLayout.MODE_FIXED);
        tlHot.setupWithViewPager(vpHotContent);
    }

    private void initDate() {
        if (dataEntity == null) {
            OkHttpTool.newInstance(this).okGet(Constants.HOT_ACTIVITY + id, HotActivityInfo.class
                    , new IOkCallBack<HotActivityInfo>() {
                @Override
                public void onSuccess(HotActivityInfo resultInfo) {
                    dataEntity = resultInfo.getData();
                    initView();
                }
            });
        }
    }

    class BannerHolder implements Holder<String> {

        private ImageView mBannerImageView;

        @Override
        public View createView(Context context) {
            mBannerImageView = new ImageView(HotContentActivity.this);
            mBannerImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return mBannerImageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            Picasso.with(HotContentActivity.this).load(data).into(mBannerImageView);
        }
    }
}
