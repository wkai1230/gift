package com.wenkai.my.gift.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.wenkai.my.gift.BaseFragment;
import com.wenkai.my.gift.R;
import com.wenkai.my.gift.bean.CategoryGiftInfo;
import com.wenkai.my.gift.constants.Constants;
import com.wenkai.my.gift.http.IOkCallBack;
import com.wenkai.my.gift.http.OkHttpTool;
import com.wenkai.my.gift.ui.activity.SelectGiftActivity;
import com.wenkai.my.gift.ui.adapter.CategoryGiftExpandAdapter;
import com.wenkai.my.gift.ui.adapter.CategoryGiftGridAdapter;
import com.wenkai.my.gift.ui.adapter.CategoryGiftListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;


public class CategoryGiftFragment extends BaseFragment {
    @Bind(R.id.lv_gift_group)
    ListView lvGiftCategory;
    @Bind(R.id.elv_gift_child)
    ExpandableListView elvGiftCategory;
    @Bind(R.id.ll_category_gift_select)
    LinearLayout llCategorySelect;

    private Map<String,List<CategoryGiftInfo.DataEntity.CategoriesEntity>> map = new HashMap<>();
    private List<CategoryGiftInfo.DataEntity.CategoriesEntity> dateList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();

    private View view;
    private OnFragmentInteractionListener mListener;
    private CategoryGiftListAdapter listAdapter;
    private CategoryGiftExpandAdapter giftExpandAdapter;

    public CategoryGiftFragment() {
    }

    public static CategoryGiftFragment newInstance() {
        CategoryGiftFragment fragment = new CategoryGiftFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_category_gift, container, false);
        }
        ButterKnife.bind(this,view);

        initDate();
        initAdapter();
        initListener();

        return view;
    }

    private void initListener() {
        lvGiftCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                listAdapter.selectedId = i;
                listAdapter.notifyDataSetChanged();

                elvGiftCategory.setSelectedGroup(i);

                if (i >= 1) {
                    lvGiftCategory.setSelection(i - 1);
                } else {
                    lvGiftCategory.setSelection(i);
                }
            }
        });

        elvGiftCategory.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int firstVisiblePosition = elvGiftCategory.getFirstVisiblePosition() / 2;
                listAdapter.selectedId = firstVisiblePosition;
                listAdapter.notifyDataSetChanged();

                if (firstVisiblePosition >= 1) {
                    lvGiftCategory.setSelection(firstVisiblePosition - 1);
                } else {
                    lvGiftCategory.setSelection(firstVisiblePosition);
                }
                return false;
            }
        });

        llCategorySelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SelectGiftActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initAdapter() {
        giftExpandAdapter = new CategoryGiftExpandAdapter(getActivity(),map,titleList);
        elvGiftCategory.setAdapter(giftExpandAdapter);

        listAdapter = new CategoryGiftListAdapter(getActivity(),titleList);
        lvGiftCategory.setAdapter(listAdapter);
    }

    private void initDate() {
        if (dateList.isEmpty()) {
            OkHttpTool.newInstance(getActivity()).okGet(Constants.CATEGORY_Gift_URL, CategoryGiftInfo.class, new IOkCallBack<CategoryGiftInfo>() {
                @Override
                public void onSuccess(CategoryGiftInfo resultInfo) {
                    dateList.addAll(resultInfo.getData().getCategories());
                    for (int i = 0; i < dateList.size() ; i ++){
                        String key = dateList.get(i).getName();
                        List<CategoryGiftInfo.DataEntity.CategoriesEntity> valueList =
                                map.get(key);
                        if (valueList == null) {
                            valueList = new ArrayList<CategoryGiftInfo.DataEntity.CategoriesEntity>();
                            valueList.add(dateList.get(i));
                            titleList.add(key);
                            map.put(key,valueList);
                        } else {
                            valueList.add(dateList.get(i));
                        }
                    }

                    for (int i = 0; i < titleList.size(); i++) {
                        elvGiftCategory.expandGroup(i);
                    }

                    elvGiftCategory.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                        @Override
                        public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                            return true;
                        }
                    });

                    giftExpandAdapter.notifyDataSetChanged();
                    listAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
