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
import com.wenkai.my.gift.bean.HotCommentInfo;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by my on 2016/3/25.
 */
public class HotCommentListAdapter extends BaseAdapter {

    private Context mContext;
    private List<HotCommentInfo.DataEntity.CommentsEntity> dataList;

    public HotCommentListAdapter(Context mContext, List<HotCommentInfo.DataEntity.CommentsEntity> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 :dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList == null ? null : dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.hot_comment_item,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        HotCommentInfo.DataEntity.CommentsEntity commentsEntity = dataList.get(i);

        if (!commentsEntity.getUser().getAvatar_url().isEmpty()) {
            Picasso.with(mContext).load(commentsEntity.getUser().getAvatar_url()).into(holder.ivIcon);
        }
        holder.tvName.setText(commentsEntity.getUser().getNickname());
        holder.tvContent.setText(commentsEntity.getContent());

        long time = commentsEntity.getCreated_at();
        SimpleDateFormat simple = new SimpleDateFormat("MM-dd HH:mm");
        String day = simple.format(new Date(time));

        holder.tvTime.setText(day);
        return view;
    }

    class ViewHolder{
        @Bind(R.id.iv_hot_comment_icon)
        ImageView ivIcon;
        @Bind(R.id.tv_hot_comment_name)
        TextView tvName;
        @Bind(R.id.tv_hot_comment_time)
        TextView tvTime;
        @Bind(R.id.tv_hot_comment_content)
        TextView tvContent;

        public ViewHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }
}
