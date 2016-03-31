package com.wenkai.my.gift.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wenkai.my.gift.BaseActivity;
import com.wenkai.my.gift.R;
import com.wenkai.my.gift.bean.HomeHeartActivityLikeInfo;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by my on 2016/3/24.
 */
public class HomeHeartActivityLikeAdapter extends BaseAdapter {
    private List<HomeHeartActivityLikeInfo.DataEntity.RecommendPostsEntity> dateList;
    private Context mContext;

    public HomeHeartActivityLikeAdapter( Context mContext,List<HomeHeartActivityLikeInfo.DataEntity.RecommendPostsEntity> dateList) {
        this.dateList = dateList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return dateList == null ? 0 : dateList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.home_heart_activity_item,null);
            holder = new ViewHolder(view);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tvTitle.setText(dateList.get(i).getTitle());
        Picasso.with(mContext).load(dateList.get(i).getCover_image_url()).into(holder.ivContext);

        return view;
    }
    class ViewHolder {
        @Bind(R.id.iv_home_heart_activity_item)
        ImageView ivContext;
        @Bind(R.id.tv_home_heart_activity_item)
        TextView tvTitle;

        public ViewHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }

}
