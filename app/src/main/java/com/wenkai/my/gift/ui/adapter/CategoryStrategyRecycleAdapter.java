package com.wenkai.my.gift.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.wenkai.my.gift.R;
import com.wenkai.my.gift.bean.CategoryHeaderRecyclerInfo;
import com.wenkai.my.gift.bean.HomeHeartExpandInfo;
import com.wenkai.my.gift.ui.activity.HomeHeartExpandActivity;
import com.wenkai.my.gift.ui.activity.ListShowActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by my on 2016/3/21.
 */
public class CategoryStrategyRecycleAdapter extends RecyclerView.Adapter<CategoryStrategyRecycleAdapter.RecyclerViewHolder> {
    private Context mContext;
    private List<CategoryHeaderRecyclerInfo.DataEntity.CollectionsEntity> recyclerList;

    public CategoryStrategyRecycleAdapter(Context mContext, List<CategoryHeaderRecyclerInfo.DataEntity.CollectionsEntity> recyclerList) {
        this.mContext = mContext;
        this.recyclerList = recyclerList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.category_recycler_item,null);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Picasso.with(mContext).load(recyclerList.get(position).getBanner_image_url()).into(holder.rivCategoryStrategy);
    }

    @Override
    public int getItemCount() {
        return recyclerList == null ? 0 : recyclerList.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.riv_category_strategy)
        RoundedImageView rivCategoryStrategy;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    CategoryHeaderRecyclerInfo.DataEntity.CollectionsEntity collectionsEntity = recyclerList.get(getPosition());
                    Intent intent = new Intent(mContext, ListShowActivity.class);
                    intent.putExtra("id", collectionsEntity.getId());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
