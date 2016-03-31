package com.handmark.pulltorefresh.library.internal;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.R;

/**
 * Created by yangjw on 2016/3/17.
 */
public class CustomHeaderLoadingLayout extends LoadingLayout{

    AnimationDrawable animationDrawable;

    public CustomHeaderLoadingLayout(Context context, PullToRefreshBase.Mode mode, PullToRefreshBase.Orientation scrollDirection, TypedArray attrs) {
        super(context, mode, scrollDirection, attrs);
        mHeaderImage.setImageResource(R.drawable.box_anim);
        animationDrawable = (AnimationDrawable) mHeaderImage.getDrawable();
        mHeaderImage2.setImageResource(R.drawable.heart);
    }

    /**
     * 设置图片默认资源
     * @return
     */
    @Override
    protected int getDefaultDrawableResId() {
        return R.drawable.box_01;
    }

    @Override
    protected void onLoadingDrawableSet(Drawable imageDrawable) {
    }

    /**
     * 下拉时回调的方法
     * @param scaleOfLayout
     */
    @Override
    protected void onPullImpl(float scaleOfLayout) {
    }

    /**
     * 下拉时执行
     */
    @Override
    protected void pullToRefreshImpl() {
    }

    /**
     * 刷新过程中执行
     */
    @Override
    protected void refreshingImpl() {
        animationDrawable.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mHeaderImage2.setVisibility(VISIBLE);
                AnimatorSet animatorSet = new AnimatorSet();
                ObjectAnimator translation = ObjectAnimator.ofFloat(mHeaderImage2,"translationY",0,-100);
                translation.setDuration(1000);
                ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mHeaderImage2, "alpha", 0f, 1.0f, 1.0f, 0f);
                alphaAnimator.setDuration(1500);
                animatorSet.play(translation).with(alphaAnimator);
                animatorSet.start();
            }
        },500);
    }

    /**
     * 松开时执行
     */
    @Override
    protected void releaseToRefreshImpl() {
    }

    /**
     * 当一个刷新动作结束时，重置方法
     */
    @Override
    protected void resetImpl() {
        mHeaderImage.clearAnimation();
        mHeaderImage.setImageResource(R.drawable.box_anim);
        animationDrawable = (AnimationDrawable) mHeaderImage.getDrawable();
    }
}
