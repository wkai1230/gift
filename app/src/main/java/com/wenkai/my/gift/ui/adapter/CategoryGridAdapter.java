package com.wenkai.my.gift.ui.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wenkai.my.gift.R;
import com.wenkai.my.gift.bean.CategoryExpandInfo;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by my on 2016/3/19.
 */
public class CategoryGridAdapter extends BaseAdapter {
    private List<CategoryExpandInfo.DataEntity.ChannelGroupsEntity.ChannelsEntity> dateList;
    private Context mContext;

    public CategoryGridAdapter(Context context,List<CategoryExpandInfo.DataEntity.ChannelGroupsEntity.ChannelsEntity> dateList) {
        this.dateList = dateList;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return dateList == null ? 0 : dateList.size();
    }

    @Override
    public Object getItem(int i) {
        return dateList == null ? null : dateList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.category_grid_item,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tvCategoryGrid.setText(dateList.get(i).getName());
        Picasso.with(mContext).load(dateList.get(i).getIcon_url()).into(holder.ivCategoryGrid);
        return view;
    }
    class ViewHolder{
        @Bind(R.id.iv_category_grid)
        ImageView ivCategoryGrid;
        @Bind(R.id.tv_category_grid)
        TextView tvCategoryGrid;

        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
