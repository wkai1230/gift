package com.wenkai.my.gift.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wenkai.my.gift.R;
import com.wenkai.my.gift.bean.CategoryGiftInfo;
import com.wenkai.my.gift.bean.HotLikeInfo;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by my on 2016/3/19.
 */
public class HotLikeGridAdapter extends BaseAdapter {
    private List<HotLikeInfo.DataEntity.RecommendItemsEntity> dateList;
    private Context mContext;

    public HotLikeGridAdapter(Context context,List<HotLikeInfo.DataEntity.RecommendItemsEntity> dateList) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.hot_like_recycler_item,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        HotLikeInfo.DataEntity.RecommendItemsEntity data = dateList.get(i);
        holder.tvHotTitle.setText(data.getName());
        holder.tvHotPrice.setText(data.getPrice());
        holder.tvHotFavorite.setText( data.getFavorites_count()+"");
        Picasso.with(mContext).load(data.getCover_image_url()).into(holder.ivHotContent);

        return view;
    }
    class ViewHolder{
        @Bind(R.id.tv_hot_like_title)
        TextView tvHotTitle;
        @Bind(R.id.tv_hot_like_price)
        TextView tvHotPrice;
        @Bind(R.id.tv_hot_like_favorite)
        TextView tvHotFavorite;
        @Bind(R.id.iv_hot_like_content)
        ImageView ivHotContent;

        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
