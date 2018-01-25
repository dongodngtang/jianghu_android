/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: SortAdapter.java
 * Author: ZouWenJie
 * Version: 1.0
 * Create: 2015-10-23
 *
 */
package net.doyouhike.app.bbs.ui.adapter.road;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.entity.SelectCityModel;
import net.doyouhike.app.bbs.ui.adapter.action.SortAdapter;
import net.doyouhike.app.bbs.util.DensityUtil;

import java.util.List;

/**
 * 线路列表 城市
 * 2016.5.11
 *
 * 朱林
 */
public class RoadCityListAdapter extends BaseAdapter implements SectionIndexer {
    private List<SelectCityModel> list = null;
    private Context context;
    private String city;
    private boolean isInList;//定位城市是否在热门城市列表中

    public RoadCityListAdapter(Context context, List<SelectCityModel> list, String city) {
        this.context = context;
        this.list = list;
        this.city = city;
        isInList = isInHotCities();
    }

    /**
     * 判断是否在热门城市列表中
     */
    private boolean isInHotCities() {
        if (list != null && city != null) {
            for (int i = 0; i < list.size(); i++) {
                if (city.contains(list.get(i).getName())) {
                    isInList = true;
                    return isInList;
                }
            }
        }
        return false;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<SelectCityModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        if (list != null) {
            return this.list.size() + 1;
        }
        return 1;
    }

    public Object getItem(int position) {
        if (list != null) {
            return list.get(position);
        }
        return 0;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {





        if (position == 0) {

            view = LayoutInflater.from(context).inflate(
                    R.layout.item_select_city_listview_head, arg2, false);


            ImageView iv = (ImageView) view.findViewById(R.id.iv_select_city);
            TextView tvTitle = (TextView) view.findViewById(R.id.tv_select_city_title);
            TextView tvLetter = (TextView) view.findViewById(R.id.tv_select_city_catelog);
            tvLetter.setText("当前位置");
            if (isInList) {
                iv.setVisibility(View.VISIBLE);
                tvTitle.setText(city);
            } else {
                tvTitle.setText("其他");
            }

        } else {

            view = LayoutInflater.from(context).inflate(
                    R.layout.item_select_city_listview, arg2, false);


            ViewHolder viewHolder = new ViewHolder();

            viewHolder.tvTitle = (TextView) view
                    .findViewById(R.id.tv_select_city_title);
            viewHolder.tvLetter = (TextView) view
                    .findViewById(R.id.tv_select_city_catelog);

            final SelectCityModel mContent = list.get(position - 1);
            // 根据position获取分类的首字母的Char ascii值
            int section = getSectionForPosition(position - 1);

            // 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
            if ((position - 1) == getPositionForSection(section)) {
                viewHolder.tvLetter.setVisibility(View.VISIBLE);
                viewHolder.tvLetter.setText(mContent.getSortLetters());
                //viewHolder.line.setVisibility(View.VISIBLE);
            } else {
                viewHolder.tvLetter.setVisibility(View.GONE);
                //viewHolder.line.setVisibility(View.GONE);
            }

            viewHolder.tvTitle.setText(this.list.get(position - 1).getName());


        }

        return view;

    }

    final static class ViewHolder {
        TextView tvLetter;
        TextView tvTitle;
        ImageView iv;
        //View line;
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return list.get(position).getSortLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount() - 1; i++) {
            String sortStr = list.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }

    public String getCity() {
        return city;
    }

    /**
     * 提取英文的首字母，非英文字母用#代替。
     *
     * @param str
     * @return
     */
    private String getAlpha(String str) {
        String sortStr = str.trim().substring(0, 1).toUpperCase();
        // 正则表达式，判断首字母是否是英文字母
        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "#";
        }
    }

    @Override
    public Object[] getSections() {
        return null;
    }
}