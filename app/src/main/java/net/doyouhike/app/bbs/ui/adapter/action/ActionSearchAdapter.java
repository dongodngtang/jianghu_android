/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info., All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: ActionAdapter.java
 * Author: ZouWenJie
 * Version: 1.0
 * Create: 2015-10-15
 *
 */
package net.doyouhike.app.bbs.ui.adapter.action;

import android.content.Context;

import net.doyouhike.app.bbs.biz.openapi.response.events.EventsListResp;

import java.util.List;

/**
 * @author ZouWenJie 活动Fragment中ListView的适配器
 */
public class ActionSearchAdapter extends BaseActionAdapter {

    public static boolean haveSelect = true;

    public ActionSearchAdapter(Context context, List<EventsListResp.ItemsBean> list) {
        super(context, list);
    }

    /**
     * @return the list
     */
    public List<EventsListResp.ItemsBean> getList() {
        return mDatas;
    }

    /**
     * @param list the list to set
     */
    public void setList(List<EventsListResp.ItemsBean> list) {
        this.mDatas = list;
    }
}
