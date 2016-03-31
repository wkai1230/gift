package com.wenkai.my.gift.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.wenkai.my.gift.R;
import com.wenkai.my.gift.bean.CategoryExpandInfo;
import com.wenkai.my.gift.bean.MyGridView;
import com.wenkai.my.gift.ui.activity.CategoryShowActivity;
import com.wenkai.my.gift.ui.activity.ListShowActivity;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by my on 2016/3/19.
 */
public class CategoryExpandAdapter extends BaseExpandableListAdapter {

    private Map<String,List<CategoryExpandInfo.DataEntity.ChannelGroupsEntity>> map;
    private List<String> keyList;
    private Context mContext;

    public CategoryExpandAdapter(Context mContext,Map<String, List<CategoryExpandInfo.DataEntity.ChannelGroupsEntity>> map, List<String> keyList) {
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
        GroupHolder holder = null;
        if (view != null){
            holder = (GroupHolder) view.getTag();
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.category_strategy_name,null);
            holder = new GroupHolder(view);
            view.setTag(holder);
        }
        holder.tvName.setText(keyList.get(i));
        return view;
    }

    class GroupHolder{
        @Bind(R.id.tv_category_strategy_name)
        TextView tvName;

        public GroupHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }

    @Override
    public View getChildView(int i, final int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildHolder holder = null;
        if (view != null){
            holder = (ChildHolder) view.getTag();
        }else {
            view = LayoutInflater.from(mContext).inflate(R.layout.category_strategy_context,null);
            holder = new ChildHolder(view);
            view.setTag(holder);
        }
        final List<CategoryExpandInfo.DataEntity.ChannelGroupsEntity.ChannelsEntity> channels = map.get(keyList.get(i)).get(i1).getChannels();
        CategoryGridAdapter adapter = new CategoryGridAdapter(mContext,channels);
        holder.gvContent.setAdapter(adapter);

        holder.gvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(mContext, CategoryShowActivity.class);
                intent.putExtra("id", channels.get(i).getId());
                intent.putExtra("title",channels.get(i).getName());
                mContext.startActivity(intent);
            }
        });

        return view;
    }

    class ChildHolder{
        @Bind(R.id.gv_category_strategy_content)
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
