/*
* -----------------------------------------------------------------
* Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
* -----------------------------------------------------------------
*
* File: MsglikedActivity.java
* Author: ChenWeiZhen
* Version: 1.0
* Create: 2015-10-21
*
* Changes (from 2015-10-21)
* -----------------------------------------------------------------
* 2015-10-21 创建MsglikedActivity.java (ChenWeiZhen);
* -----------------------------------------------------------------
*/
package net.doyouhike.app.bbs.ui.activity.message;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.activity.BaseActivity;
import net.doyouhike.app.bbs.biz.helper.list_helper.SimpleListHelper;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.presenter.UserSettingHelper;
import net.doyouhike.app.bbs.biz.openapi.presenter.UsersHelper;
import net.doyouhike.app.bbs.biz.openapi.request.users.UsersSettingPut;
import net.doyouhike.app.bbs.biz.openapi.response.users.UserSettingResp;
import net.doyouhike.app.bbs.biz.presenter.message.MsgLikeMePresenter;
import net.doyouhike.app.bbs.ui.widget.common.IUpdateView;
import net.doyouhike.app.bbs.util.UserInfoUtil;
import net.doyouhike.app.library.ui.uistate.UiState;

import butterknife.InjectView;

public class MsgLikedActivity extends BaseActivity implements IUpdateView {

    @InjectView(R.id.title_left_content)
    LinearLayout titleLeftContent;
    @InjectView(R.id.iv_bell)
    ImageView ivBell;
    @InjectView(R.id.lv_msg_liked_list)
    PullToRefreshListView lvMsgLikedList;

    MessageLikedPage page;
    SimpleListHelper helper;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_msg_liked;
    }

    @Override
    protected void initViewsAndEvents() {
        UserSettingResp.UserSettingBean setting = UserSettingHelper.getInstance().getUserSettingBean();
        ivBell.setSelected(!setting.isPush_like_msg());
        ivBell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserSettingHelper.getInstance().pushLike(mContext, listener, true);
            }
        });
        page = new MessageLikedPage(this);
        helper = new SimpleListHelper(lvMsgLikedList, this, page);
        helper.getData(true);

    }

    @Override
    protected View getLoadingTargetView() {
        return lvMsgLikedList;
    }

    public PullToRefreshListView getLvMsgLikeList() {
        return lvMsgLikedList;
    }


    IOnResponseListener listener = new IOnResponseListener() {
        @Override
        public void onSuccess(Response response) {
            UsersSettingPut put = (UsersSettingPut) response.getExtraTag();
            boolean on_off = put.isPush_like_msg();
            ivBell.setSelected(!on_off);

        }

        @Override
        public void onError(Response response) {

        }
    };

}
