/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info., All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: EventTimeAdapter.java
 * Author: ZouWenJie
 * Version: 1.0
 * Create: 2015-11-5
 *
 */
package net.doyouhike.app.bbs.ui.adapter.action;

import android.content.Context;
import android.widget.TextView;

import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.abslistview.CommonAdapter;

import net.doyouhike.app.bbs.R;

import java.util.List;

public class SelectAdapter extends CommonAdapter<String> {

    private Integer selectPosition = 0;

    public SelectAdapter(Context context, List<String> datas) {
        super(context, datas, R.layout.item_action_select);
    }

    @Override
    public void convert(ViewHolder holder, String s) {
        int position = holder.getmPosition();


        TextView tv = holder.getView(R.id.item_action_select);
        tv.setText(s);

        if (position == selectPosition) {
            tv.setTextColor(mContext.getResources().getColor(
                    R.color.blue_word));
        } else {
            tv.setTextColor(mContext.getResources().getColor(
                    R.color.black_word));
        }
    }

    public void setSelectPosition(Integer selectPosition) {
        this.selectPosition = selectPosition;
    }
}
