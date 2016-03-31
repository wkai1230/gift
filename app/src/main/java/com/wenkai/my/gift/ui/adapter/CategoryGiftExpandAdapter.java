package com.wenkai.my.gift.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wenkai.my.gift.R;
import com.wenkai.my.gift.bean.CategoryExpandInfo;
import com.wenkai.my.gift.bean.CategoryGiftInfo;
import com.wenkai.my.gift.ui.activity.CategoryGiftActivity;
import com.wenkai.my.gift.ui.activity.CategoryShowActivity;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by my on 2016/3/22.
 */
public class CategoryGiftExpandAdapter extends BaseExpandableListAdapter {
    private Map<String,List<CategoryGiftInfo.DataEntity.CategoriesEntity>> map;
    private List<String> keyList;
    private Context mContext;


    public CategoryGiftExpandAdapter(Context mContext,Map<String,List<CategoryGiftInfo.DataEntity.CategoriesEntity>> map, List<String> keyList) {
        this.map = map;
        this.keyList = keyList;
        this.mContext = mContext;
    }

    @Override
    public int getGroupCount() {
        return keyList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        if (map != null && keyList != null
                && keyList.size() > i && map.get(keyList.get(i)) != null) {
            return map.get(keyList.get(i)).size();
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
            view = LayoutInflater.from(mContext).inflate(R.layout.category_gift_title_item,null);
            holder = new GroupViewHolder(view);
            view.setTag(holder);
        }
        holder.tvTitle.setText(keyList.get(i));

        if (i == 0) {
            holder.llGift.setVisibility(View.GONE);
            holder.tvLine1.setVisibility(View.GONE);
            holder.tvTitle.setVisibility(View.GONE);
            holder.tvLine2.setVisibility(View.GONE);
        } else {
            holder.llGift.setVisibility(View.VISIBLE);
            holder.tvLine1.setVisibility(View.VISIBLE);
            holder.tvTitle.setVisibility(View.VISIBLE);
            holder.tvLine2.setVisibility(View.VISIBLE);
        }
        return view;
    }

    class GroupViewHolder{
        @Bind(R.id.tv_category_gift_title1)
        TextView tvTitle;
        @Bind(R.id.tv_gift_line1)
        TextView tvLine1;
        @Bind(R.id.tv_gift_line2)
        TextView tvLine2;
        @Bind(R.id.ll_gift)
        LinearLayout llGift;


        public GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildHolder holder = null;
        if (view != null){
            holder = (ChildHolder) view.getTag();
        }else {
            view = LayoutInflater.from(mContext).inflate(R.layout.category_gift_context,null);
            holder = new ChildHolder(view);
            view.setTag(holder);
        }

        final List<CategoryGiftInfo.DataEntity.CategoriesEntity.SubcategoriesEntity> subcategories = map.get(keyList.get(i)).get(i1).getSubcategories();
        CategoryGiftGridAdapter adapter = new CategoryGiftGridAdapter(mContext,subcategories);
        holder.gvContent.setAdapter(adapter);

        holder.gvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int id = subcategories.get(i).getId();
                String title = subcategories.get(i).getName();

                Intent intent = new Intent(mContext, CategoryGiftActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("title",title);
                mContext.startActivity(intent);
            }
        });

        return view;
    }

    class ChildHolder{
        @Bind(R.id.gv_category_gift_content)
        GridView gvContent;

        public ChildHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
