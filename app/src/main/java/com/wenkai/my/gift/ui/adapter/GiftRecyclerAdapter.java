package com.wenkai.my.gift.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wenkai.my.gift.R;
import com.wenkai.my.gift.bean.CategoryGiftActivityInfo;
import com.wenkai.my.gift.bean.HotContextInfo;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by my on 2016/3/18.
 */
public class GiftRecyclerAdapter extends RecyclerView.Adapter<GiftRecyclerAdapter.ViewHolder> {
    private List<CategoryGiftActivityInfo.DataEntity.ItemsEntity> dateList ;
    private Context mContext;
    private LayoutInflater inflater;

    public GiftRecyclerAdapter(Context mContext, List<CategoryGiftActivityInfo.DataEntity.ItemsEntity> dateList) {
        this.dateList = dateList;
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.hot_recycler_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tvHotTitle.setText(dateList.get(position).getName());
        holder.tvHotPrice.setText(dateList.get(position).getPrice());
        Picasso.with(mContext).load(dateList.get(position).getCover_image_url()).into(holder.ivHotContent);

        //将收藏数保留一位小数
        double favorites = dateList.get(position).getFavorites_count() / 1000D;
        NumberFormat nf = new DecimalFormat("0.0");
        double result = Double.parseDouble(nf.format(favorites));
        holder.tvHotFavorite.setText(result + "k" );
    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_hot_title)
        TextView tvHotTitle;
        @Bind(R.id.tv_hot_price)
        TextView tvHotPrice;
        @Bind(R.id.tv_hot_favorite)
        TextView tvHotFavorite;
        @Bind(R.id.iv_hot_content)
        ImageView ivHotContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
