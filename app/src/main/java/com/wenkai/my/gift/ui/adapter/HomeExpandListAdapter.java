package com.wenkai.my.gift.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wenkai.my.gift.R;
import com.wenkai.my.gift.bean.HomeHeartExpandInfo;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by my on 2016/3/15.
 */
public class HomeExpandListAdapter extends BaseExpandableListAdapter {
    private  Map<String,List<HomeHeartExpandInfo.DataEntity.ItemsEntity>> mExpandDates;
    private List<String> keyList;
    private Context context;

    public HomeExpandListAdapter( Context context, Map<String,List<HomeHeartExpandInfo.DataEntity.ItemsEntity>> mExpandDates,List<String> keyList) {
        this.context = context;
        this.mExpandDates = mExpandDates;
        this.keyList = keyList;
    }



    @Override
    public int getGroupCount() {
        return keyList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        if (mExpandDates != null && keyList != null
                && keyList.size() > i && mExpandDates.get(keyList.get(i)) != null) {
            return mExpandDates.get(keyList.get(i)).size();
        }
        return 0;
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        GroupViewHolder holder = null;
        if (view != null){
            holder = (GroupViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.home_heart_group_item,null);
            holder = new GroupViewHolder(view);
            view.setTag(holder);
        }
        holder.mDateText.setText(keyList.get(i));
        return view;
    }
    class GroupViewHolder{
        @Bind(R.id.tv_home_group)
        TextView mDateText;

        public GroupViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildViewHolder holder = null;
        if (view != null){
            holder = (ChildViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.home_heart_child_item,null);
            holder = new ChildViewHolder(view);
            view.setTag(holder);
        }
        HomeHeartExpandInfo.DataEntity.ItemsEntity itemsEntity = mExpandDates.get(keyList.get(i)).get(i1);
        holder.tvLike.setText(itemsEntity.getLikes_count() + "");
        holder.mTitle.setText(itemsEntity.getTitle());
        Picasso.with(context).load(itemsEntity.getCover_image_url()).into(holder.mContent);

        if ( i < 3 ){
            holder.ivNew.setImageResource(R.drawable.ig_marker_fresh);
        }else {
            holder.ivNew.setImageResource(R.drawable.empty);
        }
        return view;
    }

    class ChildViewHolder{
        @Bind(R.id.tv_home_heart_child)
        TextView mTitle;
        @Bind(R.id.iv_home_heart_child)
        ImageView mContent;
        @Bind(R.id.iv_new_title)
        ImageView ivNew;
        @Bind(R.id.tv_home_heart_like)
        TextView tvLike;

        public ChildViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
