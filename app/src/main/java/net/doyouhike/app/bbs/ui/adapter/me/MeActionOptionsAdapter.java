/*
* -----------------------------------------------------------------
* Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
* -----------------------------------------------------------------
*
* File: MeActionOptionsAdapter.java
* Author: ChenWeiZhen
* Version: 1.0
* Create: 2015-10-6
*
* Changes (from 2015-10-7)
* -----------------------------------------------------------------
* 2015-10-7 创建MeActivityOptionsAdapter.java (ChenWeiZhen);
* -----------------------------------------------------------------
* 2015-10-10 修改类名为：MeActionOptionsAdapter.java (ChenWeiZhen);
* -----------------------------------------------------------------
*/
package net.doyouhike.app.bbs.ui.adapter.me;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;

public class MeActionOptionsAdapter extends BaseAdapter {

    private Context context;
    private String[] optionsArr = new String[3];
    private int selectPosition = 0;

    public MeActionOptionsAdapter(Context context) {
        this.context = context;
        optionsArr[0] = context.getResources().getString(R.string.all);
        optionsArr[1] = context.getResources().getString(R.string.only_spinsor);
        optionsArr[2] = context.getResources().getString(R.string.only_join);
    }

    @Override
    public int getCount() {
        return optionsArr.length;
    }

    @Override
    public Object getItem(int position) {
        return optionsArr[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_user_event_options, parent, false);
        }

        TextView optionsTextTv = (TextView) convertView.findViewById(R.id.tv_me_activity_text);


        optionsTextTv.setText(optionsArr[position]);
        optionsTextTv.setSelected(position == selectPosition);

        return convertView;
    }

    /**
     * 选中方法
     *
     * @param position
     */
    public void select(int position) {
        selectPosition = position;
        notifyDataSetChanged();
    }

}
