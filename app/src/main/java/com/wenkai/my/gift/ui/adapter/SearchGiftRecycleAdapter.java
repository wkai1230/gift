package com.wenkai.my.gift.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wenkai.my.gift.R;
import com.wenkai.my.gift.bean.SearchGiftRecycleInfo;
import com.wenkai.my.gift.ui.activity.HotContentActivity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchGiftRecycleAdapter extends RecyclerView.Adapter<SearchGiftRecycleAdapter.ViewHolder> {
    private List<SearchGiftRecycleInfo.DataEntity.ItemsEntity> dateList ;
    private Context mContext;

    public SearchGiftRecycleAdapter(Context mContext, List<SearchGiftRecycleInfo.DataEntity.ItemsEntity> dateList) {
        this.dateList = dateList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.search_gift_recycler_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        SearchGiftRecycleInfo.DataEntity.ItemsEntity itemsEntity = dateList.get(position);

        holder.tvSearchTitle.setText(itemsEntity.getName());
        holder.tvSearchPrice.setText(itemsEntity.getPrice());
        Picasso.with(mContext).load(itemsEntity.getCover_image_url()).into(holder.ivSearchContent);

        //将收藏数保留一位小数
        double favorites = dateList.get(position).getFavorites_count() / 1000D;
        NumberFormat nf = new DecimalFormat("0.0");
        double result = Double.parseDouble(nf.format(favorites));
        holder.tvSearchFavorite.setText(result + "k");
    }

    @Override
    public int getItemCount() {
        return dateList == null ? 0 :dateList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_search_gift_title)
        TextView tvSearchTitle;
        @Bind(R.id.tv_search_gift_price)
        TextView tvSearchPrice;
        @Bind(R.id.tv_search_gift_favorite)
        TextView tvSearchFavorite;
        @Bind(R.id.iv_search_gift_content)
        ImageView ivSearchContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SearchGiftRecycleInfo.DataEntity.ItemsEntity itemsEntity = dateList.get(getPosition());
                    Intent intent = new Intent(mContext, HotContentActivity.class);
                    intent.putExtra("id", itemsEntity.getId());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
