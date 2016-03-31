package com.wenkai.my.gift.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wenkai.my.gift.R;

import java.util.List;

/**
 * Created by my on 2016/3/21.
 */
public class  CategoryGiftListAdapter extends BaseAdapter{

    private Context mContext;
    private List<String> titleList;
    public int selectedId = 0;

    public CategoryGiftListAdapter(Context mContext, List<String> titleList) {
        this.mContext = mContext;
        this.titleList = titleList;
    }

    @Override
    public int getCount() {
        return titleList == null ? 0 : titleList.size();
    }

    @Override
    public Object getItem(int i) {
        return titleList == null ? null : titleList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.category_gift_list_item,null);
            viewHolder = new ViewHolder();
            viewHolder.tvGiftList = (TextView) view.findViewById(R.id.tv_category_gift);
            viewHolder.ivGiftList = (ImageView) view.findViewById(R.id.iv_gift_list);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tvGiftList.setText(titleList.get(i));

        if (selectedId == i){
            viewHolder.tvGiftList.setBackgroundResource(R.drawable.write_empty);
            viewHolder.tvGiftList.setTextColor(Color.RED);
            viewHolder.ivGiftList.setImageResource(R.drawable.red_empty);
        } else {
            viewHolder.tvGiftList.setBackgroundResource(android.R.drawable.list_selector_background);
            viewHolder.ivGiftList.setImageResource(android.R.drawable.list_selector_background);
            viewHolder.tvGiftList.setTextColor(Color.BLACK);
        }
        return view;
    }

    class ViewHolder{
        private ImageView ivGiftList;
        private TextView tvGiftList;
    }
}
