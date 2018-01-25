/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File
 * Author: ZouWenJie
 * Version: 1.0
 * Create: 2015-10-19
 *
 */
package net.doyouhike.app.bbs.ui.adapter.action;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zhy.base.adapter.ViewHolder;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.service.ApiReq;
import net.doyouhike.app.bbs.biz.openapi.request.events.EventMemberOperationPost;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventMembersResp;
import net.doyouhike.app.bbs.biz.openapi.presenter.EventRole;
import net.doyouhike.app.bbs.chat.helper.Constant;
import net.doyouhike.app.bbs.ui.util.SnackUtil;

import java.util.List;

/**
 * 活动成员列表
 */
public class ActionManageConfirmAdapter extends BaseActionMemberAdapter {

    private String status;
    private String nodeID;


    public ActionManageConfirmAdapter(Context context, List<EventMembersResp.ItemsBean> datas, String status, String nodeID) {
        super(context, datas);
        this.status=status;
        this.nodeID=nodeID;
    }

    @Override
    public void convert(ViewHolder holder, EventMembersResp.ItemsBean members) {
        super.convert(holder, members);


        holder.setText(R.id.tv_action_manager_member,members.getMemo());

        TextView operate=holder.getView(R.id.btn_action_manager_sure);
        TextView info=holder.getView(R.id.tv_action_manager_member);

        info.setText(members.getMemo());
        actionManager(members,operate,info);

    }

    /**
     * 从活动管理入口进入
     *
     * 设置角色
     * 和管理按钮
     */
    private void actionManager(final EventMembersResp.ItemsBean member, final TextView sure, TextView role) {


        //   "role": "1",  // 角色（1：召集人，2：协作，3：财务，4：普通成员，5：留守人员，9:未确认）
        if (member.getRole() == EventRole.UNPASS) {

            sure.setEnabled(true);
            sure.setClickable(true);
            sure.setText("确认");


            //确认按钮
            sure.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (status.equals(Constant.EVENT_CANCEL)) {
                        SnackUtil.showSnack(mContext,"活动已开始或者已结束");
                        return;
                    }
                    sure.setEnabled(false);
                    sure.setClickable(false);


                    //确认活动
                    EventMemberOperationPost postParam = new EventMemberOperationPost(nodeID,
                            member.getUser().getUser_id(),EventRole.HANDLE_confirm);
                    postParam.setCancelSign(mContext);
                    ApiReq.doPost(postParam, new IOnResponseListener() {
                        @Override
                        public void onSuccess(Response response) {
                            mDatas.remove(member);
                            notifyDataSetChanged();
                        }

                        @Override
                        public void onError(Response response) {

                        }
                    });

                }
            });

        } else {

            sure.setEnabled(false);
            sure.setClickable(false);
            sure.setText("已确认");
            role.setVisibility(View.VISIBLE);
            role.setText("成员");
        }


        //已结束的活动  隐藏确认
        if (status.equals(Constant.EVENT_CANCEL)) {
            sure.setVisibility(View.GONE);
        }
    }
}
