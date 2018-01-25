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
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.abslistview.CommonAdapter;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.service.ApiReq;
import net.doyouhike.app.bbs.biz.openapi.request.events.EventMemberOperationPost;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventMembersResp;
import net.doyouhike.app.bbs.biz.openapi.presenter.EventOperation;
import net.doyouhike.app.bbs.chat.helper.Constant;
import net.doyouhike.app.bbs.ui.util.SnackUtil;
import net.doyouhike.app.bbs.ui.util.TipUtil;
import net.doyouhike.app.bbs.ui.util.UserHeadNickClickHelper;
import net.doyouhike.app.bbs.biz.openapi.presenter.EventRole;
import net.doyouhike.app.bbs.util.StrUtils;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.UIUtils;
import net.doyouhike.app.bbs.util.UserInfoUtil;
import net.doyouhike.app.bbs.util.glide.GlideHelper;

import java.util.List;

/**
 * @author ZouWenJie 活动管理界面已确认的数据适配器
 */
public class ActionManageConfirmedAdapter extends CommonAdapter<EventMembersResp.ItemsBean> {


    private static final int VIEW_TYPE_HEAD = 0;
    private static final int VIEW_TYPE_CONTENT = 1;

    private Context context;
    private String nodeID;
    private String[] roles;
    private int role;
    private String status;
    private String currentRole;//当前角色,查看管理活动页面的用户角色,由上级页面传递过来
    private String myUserID;
    ActionSheetDialog mDelMemberDialog;
    ActionSheetDialog mMemberManageDialog;
    private int mAttendSize;


    public ActionManageConfirmedAdapter(Context context,
                                        List<EventMembersResp.ItemsBean> showList, String nodeID,
                                        String status, String currentRole) {
        super(context, showList, R.layout.item_action_manage);
        this.context = context;
        this.nodeID = nodeID;
        this.status = status;
        this.myUserID = UserInfoUtil.getInstance().getUserId();
        this.currentRole = currentRole;
        roles = new String[]{"", "发起人", "协作", "财务", "成员", "留守"};

        String[] delItems = {"删除"};
        mDelMemberDialog = new ActionSheetDialog(context, delItems, null);
        mDelMemberDialog.title("删除成员");


        String[] roleList = {"协作", "财务", "成员", "留守"};
        mMemberManageDialog = new ActionSheetDialog(context, roleList, null);
        mMemberManageDialog.title("角色分配");
        mMemberManageDialog.isTitleShow(false);
    }


    /**
     * @param attendSize 设置参加人数
     */
    public void setAttendSize(int attendSize) {

        mAttendSize = attendSize;
    }

    public int getAttendSize() {
        return mAttendSize;
    }

    public void setmAttendSize(int mAttendSize) {
        this.mAttendSize = mAttendSize;
    }

    public void resetAttendCount(boolean isAdd) {

        if (isAdd) {
            mAttendSize++;
        } else if (mAttendSize > 0) {
            mAttendSize--;
        }


    }


    @Override
    public void convert(ViewHolder holder, final EventMembersResp.ItemsBean members) {

        ImageView head = holder.getView(R.id.riv_action_manager_head);
        ImageView sex = holder.getView(R.id.iv_action_manager_sex);
        TextView sure = holder.getView(R.id.btn_action_manager_sure);
        TextView tvRole = holder.getView(R.id.btn_action_manager_role);
        TextView nickname = holder.getView(R.id.tv_action_manager_nickname);
        TextView phone = holder.getView(R.id.tv_phone);
        TextView mergencyPeople = holder.getView(R.id.tv_mergency_contact);
        TextView mergencyPhone = holder.getView(R.id.tv_mergency_phone);
        TextView insuranceNumber = holder.getView(R.id.tv_insurance_number);
        TextView member = holder.getView(R.id.tv_action_manager_member);
        TextView real_name = holder.getView(R.id.tv_real_name);

        /**
         * 设置数据
         */
        // 头像
        String headerUrl = Constant.PHOTO_DOMAIN_PATH + members.getUser().getAvatar();
        GlideHelper.displayHeader(context, head, headerUrl);
        //点击头部进入个人主页
        UserHeadNickClickHelper.getInstance().setClickListener(head,
                members.getUser().getNick_name(),
                members.getUser().getUser_id(),
                members.getUser().getUser_id(),
                members.getUser().getAvatar());


        //昵称
        nickname.setText(members.getUser().getNick_name());


        //性别
        if (Constant.MALE.equals(members.getUser().getSex())) {
            sex
                    .setImageResource(R.drawable.ic_me_male_3x);
        } else if (Constant.FEMALE.equals(members.getUser().getSex())) {
            sex
                    .setImageResource(R.drawable.ic_me_female_3x);
        } else {
            sex.setVisibility(View.GONE);
        }


        //资料卡内容
        setExtraInfo(members, phone, mergencyPeople, mergencyPhone, insuranceNumber, real_name);


        //角色
        final int roleIndex = members.getRole();
        member.setText(roles[roleIndex]);


        //设置确认按钮
        setActionManageSate(holder.getmPosition(), sure, members, roleIndex);

        // 设置角色属性
        setRoleState(tvRole, members);

    }

    /**
     * 资料卡内容
     */
    private void setExtraInfo(EventMembersResp.ItemsBean members, TextView phone, TextView mergencyPeople, TextView mergencyPhone, TextView insuranceNumber, TextView real_name) {
        if (null != members) {

            // 电话
            final String mobile = members.getUser().getMobile();
            phone.setText(mobile);
            //点击号码直接拨打电话
            if (StrUtils.hasContent(mobile)) {
                phone.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPhoneDialog(mobile);
                    }
                });
            }

            //真实姓名
            if (StringUtil.isNotEmpty(members.getUser().getReal_name()))
                real_name.setText(members.getUser().getReal_name());


            //紧急联系人
            if (!TextUtils.isEmpty(members.getContact_name())) {
                mergencyPeople.setText(members.getContact_name());
            }

            //紧急联系电话
            final String contactTel = members.getContact_tel();
            if (!TextUtils.isEmpty(contactTel)) {

                mergencyPhone.setTextColor(mergencyPhone.getResources().getColorStateList(R.color.txt_button_common_light));
                mergencyPhone.setText(contactTel);
                //点击号码直接拨打电话
                mergencyPhone.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        showPhoneDialog(contactTel);
                    }
                });
            } else {
                mergencyPhone.setText("无");
                mergencyPhone.setTextColor(mergencyPhone.getResources().getColor(R.color.txt_light_remark));
            }

        }


        //保险单号
        if (!TextUtils.isEmpty(members.getInsurance_number())) {
            insuranceNumber.setText(members
                    .getInsurance_number());
        }
    }


    /**
     * 设置确认按钮
     *
     * @param position
     * @param members
     * @param roleIndex
     */
    private void setActionManageSate(int position, final TextView del, final EventMembersResp.ItemsBean members, int roleIndex) {


        //当前用户不显示确认按钮
        String itemUserId = getItem(position).getUser().getUser_id();
        UIUtils.showView(del, !UserInfoUtil.getInstance().isCurrentUser(itemUserId));

        if (roleIndex == EventRole.CONVENER) {
            // 发起人,隐藏已确认图标
            del.setVisibility(View.GONE);
        }
        //协作人隐藏已确认图标,协作人不能对协作人进行删除操作
        if (roleIndex == EventRole.COOPERATE && currentRole.equals("2")) {
            del.setVisibility(View.GONE);
        }

        del.setTextColor(del.getResources().getColorStateList(R.color.txt_button_action_manage_del_member));
        del.setBackgroundResource(R.drawable.selector_action_manage_del_member);

        del.setText("删除");
        del.setSelected(true);
        del.setEnabled(true);

//        if (status.equals(Constant.EVENT_CANCEL)) {
//            //活动状态为已结束，发起人亦可进入活动管理；隐藏确认/删除成员、修改角色等操作
//            UIUtils.showView(del, false);
//        }

        // 点击删除成员
        del.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
//                if (status.equals(Constant.EVENT_CANCEL)) {
//                    SnackUtil.showSnack(context, "活动已结束");
//                    return;
//                }
                delMember(members, del);
            }
        });

    }


    /**
     * 设置角色信息
     *
     * @param tvRole
     * @param members
     * @return
     */
    private void setRoleState(final TextView tvRole, final EventMembersResp.ItemsBean members) {
        //   "tvRole": "1",  // 角色（1：召集人，2：协作，3：财务，4：普通成员，5：留守人员，9:未确认）
        final int currentItemRole = members.getRole();
        //只有管理者才能分配角色
        UIUtils.showView(tvRole, currentRole.equals("1"));

        if (currentRole.equals("1")) {
            UIUtils.showView(tvRole, !UserInfoUtil.getInstance().isSameUser(myUserID, members.getUser().getUser_id()));
        }

        tvRole.setEnabled(true);

        // 为item项中的控件设置点击事件
        tvRole.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {

                onRoleClick(currentItemRole, members, tvRole);
            }
        });

//        if (status.equals(Constant.EVENT_CANCEL)) {
//            //活动状态为已结束，发起人亦可进入活动管理；隐藏确认/删除成员、修改角色等操作
//            UIUtils.showView(tvRole, false);
//        }

    }

    /**
     * 更换角色
     *
     * @param currentItemRole
     * @param members
     * @param tvRole
     */
    private void onRoleClick(final int currentItemRole, final EventMembersResp.ItemsBean members, final TextView tvRole) {


//        if (status.equals(Constant.EVENT_CANCEL)) {
//            SnackUtil.showSnack(context, "活动已结束");
//            return;
//        }

        mMemberManageDialog.show();
        mMemberManageDialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {

                //position=0/协作,1/财务,2/成员,3/留守
                role = position + 2;
                //修改角色与当前角色一致不发送网络修改
                if (role == currentItemRole) {
                    mMemberManageDialog.dismiss();
                    return;
                }


                //协作人数不能超过已确认人数三分之一
                if (EventRole.COOPERATE == role) {
                    //统计协助者数量
                    int assistanceRoleSize = 0;
                    for (EventMembersResp.ItemsBean member : mDatas) {
                        if (member.getRole() == EventRole.COOPERATE) {
                            assistanceRoleSize++;
                        }
                    }

                    //再计算上刚刚的选择,即选为协作人员

                    if (mDatas.size() != 2) {
                        assistanceRoleSize++;
                    }

                    if (assistanceRoleSize * 3 > mDatas.size()) {
                        mMemberManageDialog.dismiss();
                        StringUtil.showSnack(view.getContext(),
                                "协作人数不能超过确认人数三分之一");
                        return;
                    }

                }
                tvRole.setEnabled(false);
                //发送网络请求
                EventMemberOperationPost rolePost = new EventMemberOperationPost(nodeID,
                        members.getUser().getUser_id(), EventRole.getOperStr(role));
                ApiReq.doPost(rolePost, new IOnResponseListener() {
                    @Override
                    public void onSuccess(Response response) {
                        members.setRole(role);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Response response) {

                    }
                });

                mMemberManageDialog.dismiss();

            }
        });

    }

    /**
     * @param mobile 显示拨打电话对话框
     */
    protected void showPhoneDialog(final String mobile) {

        String title;

        if (mobile.length() == 11) {
            String substring1 = mobile.substring(0, 3);
            String substring2 = mobile.substring(3, 7);
            String substring3 = mobile.substring(7);
            String tel = substring1 + "-" + substring2 + "-" + substring3;
            title = tel;
        } else {
            title = mobile;
        }


        TipUtil.alert(context
                , null
                , title
                , new String[]{"取消", "呼叫"}
                , new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                    }
                }
                , new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobile));
                        context.startActivity(intent);
                    }
                });

    }

    /**
     * 删除成员
     *
     * @param members 成员
     * @param del
     */
    protected void delMember(final EventMembersResp.ItemsBean members, final TextView del) {

        mDelMemberDialog.show();
        mDelMemberDialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                del.setEnabled(false);
                mDelMemberDialog.dismiss();
                EventMemberOperationPost operation = new EventMemberOperationPost(nodeID, members.getUser().getUser_id(), EventOperation.DELETE.getOperation());
                operation.setCancelSign(mContext);
                ApiReq.doPost(operation);

            }
        });

    }


    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_HEAD : VIEW_TYPE_CONTENT;
    }

    @Override
    public int getCount() {
        if (mDatas != null) {
            return mDatas.size() + 1;
        }
        return 1;
    }

    @Override
    public EventMembersResp.ItemsBean getItem(int position) {

        if (position == 0) {
            return null;
        }

        if (mDatas == null || mDatas.isEmpty()) {
            return null;
        }
        return mDatas.get(position - 1);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (getItemViewType(position) == VIEW_TYPE_HEAD) {// 设置参加人数标题栏
            convertView = View.inflate(mContext,
                    R.layout.item_action_listview_head, null);
            TextView tv = (TextView) convertView.findViewById(R.id.tv_number);
            tv.setText("已确认" + mAttendSize + "人");
            return convertView;
        } else {
            return super.getView(position, convertView, parent);
        }
    }

}
