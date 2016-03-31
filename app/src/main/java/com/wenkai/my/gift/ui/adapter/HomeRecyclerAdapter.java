package com.wenkai.my.gift.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wenkai.my.gift.R;
import com.wenkai.my.gift.bean.HomeHeartExpandInfo;
import com.wenkai.my.gift.bean.HomeHeartRecyclerInfo;
import com.wenkai.my.gift.ui.activity.HomeHeartExpandActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by my on 2016/3/17.
 */
public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder> {

    private List<HomeHeartRecyclerInfo.DataEntity.SecondaryBannersEntity> recyclerList;
    private Context mContext;

    public HomeRecyclerAdapter(Context mContext,List<HomeHeartRecyclerInfo.DataEntity.SecondaryBannersEntity> recyclerList) {
        this.recyclerList = recyclerList;
        this.mContext = mContext;
    }

    /**
     * 创建ViewHolder对象,并且对Item的布局进行初始化
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_heart_recycler_item,null);
        return new ViewHolder(view);
    }

    /**
     * 此方法是给Item布局中的控件赋值
     * 参数一：onCreateViewHolder方法返回的ViewHolder对象
     * 参数二：Item的索引。
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(mContext).load(recyclerList.get(position).getImage_url()).into(holder.imageView);
    }

    /**
     * 返回Item的个数---也数据数据源的大小
     * @return
     */
    @Override
    public int getItemCount() {
        return recyclerList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_home_heart_recycler);

        }
    }
}
