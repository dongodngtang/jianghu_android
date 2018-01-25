package net.doyouhike.app.bbs.ui.adapter.message;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.abslistview.CommonAdapter;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.entity.MsgMyActionInfo;
import net.doyouhike.app.bbs.biz.openapi.presenter.EventRole;
import net.doyouhike.app.bbs.biz.openapi.response.users.UserMsgEventResp;
import net.doyouhike.app.bbs.chat.helper.Constant;
import net.doyouhike.app.bbs.ui.util.UserHeadNickClickHelper;
import net.doyouhike.app.bbs.util.ActivityRouter;
import net.doyouhike.app.bbs.util.UIUtils;
import net.doyouhike.app.bbs.util.UserInfoUtil;
import net.doyouhike.app.bbs.util.glide.GlideHelper;
import net.doyouhike.app.library.ui.utils.DateUtils;

import java.util.List;

/**
 * 作者：luochangdong on 16/10/17
 * 描述：
 */
public class MsgEventAdapter extends CommonAdapter<UserMsgEventResp.MsgsBean> {
    public MsgEventAdapter(Context context, List<UserMsgEventResp.MsgsBean> datas) {
        super(context, datas, R.layout.item_msg_action);
    }

    @Override
    public void convert(ViewHolder holder, final UserMsgEventResp.MsgsBean aInfo) {

        ImageView actionPortraitIv = holder.getView(R.id.iv_itemt_action_portrait);
        TextView actionNameTv = holder.getView(R.id.tv_itemt_action_name);
        TextView actionJoinTv = holder.getView(R.id.tv_itemt_action_join);

        TextView actionElapsedTimeTv = holder.getView(R.id.tv_itemt_action_elapsed_time);
        TextView actionTitleTv = holder.getView(R.id.tv_itemt_action_title);
        final TextView actionConfirmTv = holder.getView(R.id.tv_itemt_action_confirm);
        TextView actionVerificationtTv = holder.getView(R.id.tv_itemt_action_verification_text);
        RelativeLayout rl_item_msg_action = holder.getView(R.id.rl_item_msg_action);

        View v_item_divider = holder.getView(R.id.v_item_msg_action_divider);
        UIUtils.showView(v_item_divider, holder.getmPosition() == 0);

        String faceUrl = Constant.PHOTO_DOMAIN_PATH + aInfo.getFace();
        // 显示
        GlideHelper.displayHeader(mContext, actionPortraitIv, faceUrl);// 头像
        // 点击头像,打开其他人的个人页面
        UserHeadNickClickHelper.getInstance().setClickListener(actionPortraitIv,
                aInfo.getNick_name(),
                aInfo.getUser_id(),
                aInfo.getUser_id(),
                aInfo.getFace());

        // 点击昵称，他人主页
        actionNameTv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 打开其他人的个人页面
                ActivityRouter.openOtherPageActivity(mContext, aInfo.getUser_id());
            }
        });

        actionNameTv.setText(aInfo.getNick_name()); // 参加者名
        if (UserInfoUtil.getInstance().isSameUser(UserInfoUtil.getInstance().getUserId(), aInfo.getUser_id())) {
            actionNameTv.setText("我"); // 参加者名
        }

        actionJoinTv.setText(mContext.getResources().getString(
                R.string.join_the_action));

        // TODO 申请时间
        String creatTimeStr = DateUtils.getStrDate(mContext,
                aInfo.getCreated_time() * 1000);
        actionElapsedTimeTv.setText(creatTimeStr);

        actionTitleTv.setText(aInfo.getEvent_title()); // 活动标题


        // 留言
        if (aInfo.getMemo() != null && !aInfo.getMemo().equals("")) {
            actionVerificationtTv.setVisibility(View.VISIBLE);
            // 修改actionVerificationtTv的内容
            actionVerificationtTv.setText(aInfo.getMemo());

        } else {
            actionVerificationtTv.setVisibility(View.GONE);
        }

        // 是否已读
        if (aInfo.getIs_read() == 2) {
            rl_item_msg_action.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        } else {
            rl_item_msg_action.setBackgroundColor(mContext.getResources().getColor(R.color.bg_message_itme_unread));
        }

        if (aInfo.getRole() != 9 && aInfo.getIs_organizer() == 0) {
            actionConfirmTv.setVisibility(View.VISIBLE);
            actionConfirmTv.setText(mContext.getResources().getString(
                    R.string.action_confirmed));
        } else {
            actionConfirmTv.setVisibility(View.INVISIBLE);
        }

        if (aInfo.getNode_type().equals(MsgMyActionInfo.minilog_del)) {
            //活动取消
            actionTitleTv.setOnClickListener(null);
            actionConfirmTv.setText(mContext.getResources().getString(
                    R.string.action_cancel));
        } else {

            //点击标题进入活动详情
            actionTitleTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityRouter.openActionDetailActivity(v.getContext(), aInfo.getNode_id());
                }
            });

            rl_item_msg_action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (aInfo.getRole() == EventRole.CONVENER ||
                            aInfo.getRole() == EventRole.COOPERATE) {
                        ActivityRouter.openActionManagerActivity(mContext,
                                aInfo.getUser_id(),
                                aInfo.getNode_id(),
                                aInfo.getRole(),//活动角色
                                true,//管理活动,
                                true,//显示确认列表
                                "");//活动状态
                    } else {
                        ActivityRouter.openActionDetailActivity(v.getContext(), aInfo.getNode_id());
                    }

                }
            });
        }

    }
}
