/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: MsgActionActivity.java
 * Author: ChenWeiZhen
 * Version: 1.0
 * Create: 2015-10-21
 *
 * Changes (from 2015-10-21)
 * -----------------------------------------------------------------
 * 2015-10-21 创建MsgActionActivity.java (ChenWeiZhen);
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.ui.activity.message;

import android.view.View;
import android.widget.ImageView;

import com.flyco.dialog.widget.ActionSheetDialog;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.activity.BaseActivity;
import net.doyouhike.app.bbs.biz.helper.list_helper.SimpleListHelper;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.presenter.UserSettingHelper;
import net.doyouhike.app.bbs.biz.openapi.presenter.UsersHelper;
import net.doyouhike.app.bbs.biz.openapi.request.users.UsersSettingPut;
import net.doyouhike.app.bbs.biz.openapi.response.users.UserNodeStateResp;
import net.doyouhike.app.bbs.biz.openapi.response.users.UserSettingResp;
import net.doyouhike.app.bbs.ui.widget.common.IUpdateView;
import net.doyouhike.app.bbs.util.UserInfoUtil;

import butterknife.InjectView;

public class MsgActionActivity extends BaseActivity implements IUpdateView {


    @InjectView(R.id.iv_bell)
    ImageView ivBell;

    /**
     * 信息选项列表
     */
    @InjectView(R.id.lv_msg_action_list)
    PullToRefreshListView actionLv;

    MessageEventPage page;
    SimpleListHelper simpleListHelper;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_msg_action;
    }

    @Override
    protected View getLoadingTargetView() {
        return actionLv;
    }

    @Override
    protected void initViewsAndEvents() {

        UserSettingResp.UserSettingBean setting = UserSettingHelper.getInstance().getUserSettingBean();
        ivBell.setSelected(!setting.isPush_event_msg());
        ivBell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserSettingHelper.getInstance().pushEvent(mContext, listener, true);
            }
        });

        page = new MessageEventPage(this);
        simpleListHelper = new SimpleListHelper(actionLv, this, page);
        simpleListHelper.getData(true);
    }

    IOnResponseListener listener = new IOnResponseListener() {
        @Override
        public void onSuccess(Response response) {
            UsersSettingPut put = (UsersSettingPut) response.getExtraTag();
            boolean on_off = put.isPush_event_msg();
            ivBell.setSelected(!on_off);

        }

        @Override
        public void onError(Response response) {

        }
    };

}
