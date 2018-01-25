/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: ReportAdapter.java
 * Author: ZouWenJie
 * Version: 1.0
 * Create: 2015-11-21
 *
 */
package net.doyouhike.app.bbs.ui.adapter.live;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.abslistview.CommonAdapter;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.live.GetAccusationTypeListResp;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.NodeAccusationResp;
import net.doyouhike.app.bbs.ui.activity.ReportActivity;
import net.doyouhike.app.bbs.util.UIUtils;

import java.util.List;

public class ReportAdapter extends CommonAdapter<NodeAccusationResp.ItemsBean> {
    ReportActivity baseActivity;

    public ReportAdapter(ReportActivity context, List<NodeAccusationResp.ItemsBean> datas) {
        super(context, datas, R.layout.item_report);
        baseActivity = context;
    }

    @Override
    public void convert(ViewHolder holder, final NodeAccusationResp.ItemsBean item) {

        RelativeLayout rl_item_repost = holder.getView(R.id.rl_item_repost);
        if(item.getType_id() == 7){
            UIUtils.showView(rl_item_repost,baseActivity.isEvent());
        }

        holder.setVisible(R.id.v_side, holder.getmPosition() != getCount() - 1);
        final TextView tv = holder.getView(R.id.tv_report_type);
        final ImageView iv_copying = holder.getView(R.id.iv_copying);
        tv.setText(item.getType_name());
        UIUtils.showView(iv_copying,item.getType_id() == baseActivity.getAccusationType());
        tv.setSelected(item.getType_id() == baseActivity.getAccusationType());
    }
}
