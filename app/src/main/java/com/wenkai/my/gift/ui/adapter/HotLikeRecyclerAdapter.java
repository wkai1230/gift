package com.wenkai.my.gift.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wenkai.my.gift.R;
import com.wenkai.my.gift.bean.HotContextInfo;
import com.wenkai.my.gift.bean.HotLikeInfo;
import com.wenkai.my.gift.ui.activity.HotContentActivity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by my on 2016/3/18.
 */
public class HotLikeRecyclerAdapter extends RecyclerView.Adapter<HotLikeRecyclerAdapter.ViewHolder> {
    private List<HotLikeInfo.DataEntity.RecommendItemsEntity> dateList ;
    private Context mContext;
    private LayoutInflater inflater;

    public HotLikeRecyclerAdapter(Context mContext,List<HotLikeInfo.DataEntity.RecommendItemsEntity> dateList) {
        this.dateList = dateList;
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.hot_like_recycler_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HotLikeInfo.DataEntity.RecommendItemsEntity data = dateList.get(position);
        holder.tvHotTitle.setText(data.getName());
        holder.tvHotPrice.setText(data.getPrice());
        Picasso.with(mContext).load(data.getCover_image_url()).into(holder.ivHotContent);

        int favorites = data.getFavorites_count();
        holder.tvHotFavorite.setText(favorites+"");
    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_hot_like_title)
        TextView tvHotTitle;
        @Bind(R.id.tv_hot_like_price)
        TextView tvHotPrice;
        @Bind(R.id.tv_hot_like_favorite)
        TextView tvHotFavorite;
        @Bind(R.id.iv_hot_like_content)
        ImageView ivHotContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    HotContextInfo.DataEntity.ItemsEntity itemsEntity = dateList.get(getPosition());
//                    Intent intent = new Intent(mContext, HotContentActivity.class);
//                    intent.putExtra("id", itemsEntity.getData().getId());
//                    mContext.startActivity(intent);
//                }
//            });
        }
    }
}
