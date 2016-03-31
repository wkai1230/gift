package com.wenkai.my.gift.bean;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by my on 2016/3/10.
 */
public class MyViewGroup extends ViewGroup {
    public MyViewGroup(Context context) {
        this(context, null);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        int childCount = getChildCount();
        int measuredWidth = getMeasuredWidth();
        int sumWidth = 0;
        int n = 0;
        int index = 0;

        for (int k = 0 ; k < childCount ; k ++){
            View childView = getChildAt(k);
            int childWidth = childView.getMeasuredWidth();
//            int childHeight = childView.getMeasuredHeight();

            if ((sumWidth = sumWidth + childWidth + 10) < measuredWidth){
                childView.layout(sumWidth - childWidth + (index + 1) * 10, n * 60 + (n + 1) * 15,sumWidth + (index + 1) * 10 , (n + 1) * 60 + (n + 1) * 15);
            } else {
                sumWidth = childWidth+10;
                n++;
                index = 0;
                childView.layout(sumWidth - childWidth + (index + 1) * 10, n * 60 + (n + 1) * 15,sumWidth + (index + 1) * 10 , (n + 1) * 60 + (n + 1) * 15);
            }

            index++;
        }
    }
}
