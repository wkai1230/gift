package com.wenkai.my.gift.ui.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.wenkai.my.gift.BaseActivity;
import com.wenkai.my.gift.R;
import com.wenkai.my.gift.http.OkHttpTool;
import com.wenkai.my.gift.ui.fragment.CategoryFragment;
import com.wenkai.my.gift.ui.fragment.HomeFragment;
import com.wenkai.my.gift.ui.fragment.HotFragment;
import com.wenkai.my.gift.ui.fragment.ProfileFragment;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup mRadioGroup;
    private HomeFragment mHomeFragment;
    private HotFragment mHotFragment;
    private CategoryFragment mCategoryFragment;
    private ProfileFragment mProfileFragment;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private RadioButton rbHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = getSupportFragmentManager();

        initView();
        rbHome.setChecked(true);
    }

    private void initView() {
        mRadioGroup = (RadioGroup)findViewById(R.id.rg_home);
        rbHome = (RadioButton)findViewById(R.id.rb_home_home);

        mRadioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        transaction = manager.beginTransaction();
        hindFragment(transaction);
        switch (i){
            case R.id.rb_home_home:
                if (mHotFragment == null){
                    mHomeFragment = HomeFragment.newInstance();
                    transaction.add(R.id.fl_home,mHomeFragment);
                }
                transaction.show(mHomeFragment);
                break;
            case R.id.rb_hot_home:
                if (mHotFragment == null){
                    mHotFragment = HotFragment.newInstance();
                    transaction.add(R.id.fl_home,mHotFragment);
                }
                transaction.show(mHotFragment);
                break;
            case R.id.rb_category_home:
                if (mCategoryFragment == null){
                    mCategoryFragment = CategoryFragment.newInstance();
                    transaction.add(R.id.fl_home,mCategoryFragment);
                }
                transaction.show(mCategoryFragment);
                break;
            case R.id.rb_profile_home:
                if (mProfileFragment == null){
                    mProfileFragment = ProfileFragment.newInstance(null, null);
                    transaction.add(R.id.fl_home,mProfileFragment);
                }
                transaction.show(mProfileFragment);
                break;
        }
        transaction.commit();
    }

    private void hindFragment(FragmentTransaction transaction) {
        if (mHomeFragment != null){transaction.hide(mHomeFragment);}
        if (mHotFragment != null){transaction.hide(mHotFragment);}
        if (mCategoryFragment != null){transaction.hide(mCategoryFragment);}
        if (mProfileFragment != null){transaction.hide(mProfileFragment);}
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpTool.newInstance(this).cancel(this);
    }
}
