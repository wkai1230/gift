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
import com.wenkai.my.gift.bean.HomeHeartExpandInfo;
import com.wenkai.my.gift.bean.ShowListInfo;
import com.wenkai.my.gift.ui.activity.HomeHeartExpandActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by my on 2016/3/17.
 */
public class ShowListAdapter extends RecyclerView.Adapter<ShowListAdapter.ViewHolder> {

    private List<ShowListInfo.DataEntity.PostsEntity> dateList ;
    private Context mContext;

    public ShowListAdapter(Context mContext, List<ShowListInfo.DataEntity.PostsEntity> dateList) {
        this.dateList = dateList;
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_other_recycler_item,null);
        return new ViewHolder(view);
    }

    /**
     * 此方法是给Item布局中的控件赋值
     * 参数一：onCreateViewHolder方法返回的ViewHolder对象
     * 参数二：Item的索引。
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ShowListInfo.DataEntity.PostsEntity postsEntity = dateList.get(position);

        holder.tvLike.setText(postsEntity.getLikes_count()+"");
        holder.textView.setText(postsEntity.getTitle());
        Picasso.with(mContext).load(postsEntity.getCover_image_url()).into(holder.imageView);
    }

    /**
     * 返回Item的个数---也数据数据源的大小
     * @return
     */
    @Override
    public int getItemCount() {
        return dateList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView textView;
        public TextView tvLike;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_home_other);
            textView = (TextView) itemView.findViewById(R.id.tv_home_other);
            tvLike = (TextView) itemView.findViewById(R.id.tv_home_other_like);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShowListInfo.DataEntity.PostsEntity itemsEntity = dateList.get(getPosition());

                    Intent intent = new Intent(mContext, HomeHeartExpandActivity.class);
                    intent.putExtra("webContentUrl",itemsEntity.getContent_url());
                    intent.putExtra("webImageUrl",itemsEntity.getCover_webp_url());
                    intent.putExtra("title",itemsEntity.getTitle());
                    intent.putExtra("id",itemsEntity.getId());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
