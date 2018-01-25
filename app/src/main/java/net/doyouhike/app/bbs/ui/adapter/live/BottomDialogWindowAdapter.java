/*
* -----------------------------------------------------------------
* Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
* -----------------------------------------------------------------
*
* File: BottomDialogWindowAdapter.java
* Author: ChenWeiZhen
* Version: 1.0
* Create: 2015-10-6
*
* Changes (from 2015-10-6)
* -----------------------------------------------------------------
* 2015-10-6 创建BottomDialogWindowAdapter.java (ChenWeiZhen);
* -----------------------------------------------------------------
*/
package net.doyouhike.app.bbs.ui.adapter.live;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;

import java.util.List;

public class BottomDialogWindowAdapter extends BaseAdapter {

    private Context context;
    private List<String> itemStrList;
    private List<Integer> itemColorList; // color在R的id

    public BottomDialogWindowAdapter(Context context, List<String> itemStrList, List<Integer> itemColorList) {
        this.context = context;
        this.itemStrList = itemStrList;
        this.itemColorList = itemColorList;
    }

    @Override
    public int getCount() {
        return itemStrList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemStrList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_bottom_popup, null);
        }
        TextView itemTv = (TextView) convertView.findViewById(R.id.tv_item);
        itemTv.setText(itemStrList.get(position));
        itemTv.setTextColor(context.getResources().getColor(itemColorList.get(position)));
        return convertView;
    }

}
