package com.wenkai.my.gift.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wenkai.my.gift.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by my on 2016/3/29.
 */
public class PopGridAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> titleList;

    public PopGridAdapter(Context mContext, List<String> titleList) {
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
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.pop_grid_item, null);
            holder = new ViewHolder(view);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        int num = 0;

        if (!((num = titleList.size()%4) == 0)) {
            for (int k = 0 ; k < num ; k ++) {
                titleList.add(" ");
            }
        }

        holder.tvPop.setText(titleList.get(i));

        return view;
    }

    class ViewHolder{

        @Bind(R.id.tv_pop_window)
        TextView tvPop;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
