package net.doyouhike.app.bbs.ui.adapter.message;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.abslistview.CommonAdapter;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.entity.MsgMyCommentInfo;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.message.GetCommentMeListResp;
import net.doyouhike.app.bbs.biz.openapi.response.users.UserMsgCommentsResp;
import net.doyouhike.app.bbs.chat.helper.Constant;
import net.doyouhike.app.bbs.ui.util.CommentContentHelper;
import net.doyouhike.app.bbs.ui.util.UserHeadNickClickHelper;
import net.doyouhike.app.bbs.util.ActivityRouter;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.UIUtils;
import net.doyouhike.app.bbs.util.UserInfoUtil;
import net.doyouhike.app.bbs.util.glide.GlideHelper;
import net.doyouhike.app.library.ui.utils.DateUtils;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 作者:luochangdong on 16/6/14 15:51
 * 描述:
 */
public class MsgCommentMeAdapter extends CommonAdapter<UserMsgCommentsResp.MsgsBean> {

    /**
     * 当前登录用户ID
     */
    private String myselfID;

    public MsgCommentMeAdapter(Context context, List<UserMsgCommentsResp.MsgsBean> datas) {
        super(context, datas, R.layout.item_msg_comment);
        myselfID = UserInfoUtil.getInstance().getUserId();
    }

    @Override
    public void convert(ViewHolder holder, final UserMsgCommentsResp.MsgsBean cInfo) {
        ImageView commentPortraitIv = holder.getView(R.id.iv_itemt_commenter_portrait);
        TextView commentNameTv = holder.getView(R.id.tv_itemt_commenter_name);
        TextView commentCreatTimeTv = holder.getView(R.id.tv_itemt_creat_time);
        final TextView commentContentTv = holder.getView(R.id.tv_itemt_comment_content);

        LinearLayout rl_item_comment = holder.getView(R.id.rl_item_comment);
        RelativeLayout commentOriginalRlyt = holder.getView(R.id.rlyt_item_commenter_original);
        TextView commentOriginalTextV = holder.getView(R.id.tv_itemt_commenter_original);
        ImageView commentOriginalImageV = holder.getView(R.id.iv_itemt_commenter_original);

        ImageView commentIsReadIv = holder.getView(R.id.iv_itemt_commenter_isread);
        View v_item_divider = holder.getView(R.id.v_item_msg_comment_divider);

        View gapV = holder.getView(R.id.v_gap);

        // 头像
        String faceUrl = Constant.PHOTO_DOMAIN_PATH + cInfo.getFace();
        GlideHelper.displayHeader(mContext, commentPortraitIv, faceUrl);
        // 点击头像，他人主页
        UserHeadNickClickHelper.getInstance().setClickListener(commentPortraitIv,
                cInfo.getNick_name(),
                cInfo.getUser_id(),
                cInfo.getUser_id(),
                cInfo.getFace());
        // 点击昵称，他人主页
        commentNameTv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 打开其他人的个人页面
                ActivityRouter.openOtherPageActivity(v.getContext(), cInfo.getUser_id());

            }
        });

        UIUtils.showView(v_item_divider, holder.getmPosition() == 0);
        // 评论者名
        commentNameTv.setText(cInfo.getNick_name());
        // TODO  评论内容
        if (cInfo.getContent() != null)
            Observable.create(new Observable.OnSubscribe<String>() {
                @Override
                public void call(Subscriber<? super String> subscriber) {
                    StringBuilder sb = new StringBuilder();
                    for (UserMsgCommentsResp.MsgsBean.ContentBean content : cInfo.getContent()) {
                        if (content.getType().equals(Constant.TXET))
                            sb.append(content.getContent());
                    }
                    subscriber.onNext(sb.toString());
                }
            }).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            CommentContentHelper.setReplyToContent(commentContentTv, s,
                                    cInfo.getReply_to_nickname(),
                                    cInfo.getReply_to_id());
                        }
                    });


        commentContentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCommentText(cInfo);
            }
        });

        // TODO 评论时间
        String creatTimeStr = DateUtils.getStrDate(mContext,
                cInfo.getCreated_time() * 1000);
        commentCreatTimeTv.setText(creatTimeStr);

        // 右边内容块的设置

        if (cInfo.getNode_type().equals("minilog")) { // 图文

            commentOriginalTextV.setVisibility(View.GONE);
            commentOriginalImageV.setVisibility(View.VISIBLE);
            if (StringUtil.isNotEmpty(cInfo.getFirst_photo()))
                GlideHelper.displayNetImage(mContext, commentOriginalImageV,
                        Constant.PHOTO_DOMAIN_PATH + cInfo.getFirst_photo(), R.drawable.home_big_pic_fail_white);
            else
                GlideHelper.displayNetImage(mContext, commentOriginalImageV, "", R.drawable.news_icon_text);

            // 点击打开正文
            setOnOriginalClickListener(rl_item_comment, cInfo.getNode_id(), cInfo.getNode_type(), true);

        } else if (cInfo.getNode_type().equals(MsgMyCommentInfo.NODE_TYPE_URL_SHARE)) {// 帖子
            commentOriginalTextV.setVisibility(View.GONE);
            commentOriginalImageV.setVisibility(View.VISIBLE);
            GlideHelper.displayNetImage(mContext, commentOriginalImageV, "", R.drawable.ic_home_repost_link_3x);

            // 点击打开正文
            setOnOriginalClickListener(rl_item_comment, cInfo.getNode_id(), cInfo.getNode_type(), true);
        } else if (cInfo.getNode_type().equals(MsgMyCommentInfo.NODE_TYPE_ACTION)) {// 活动
            commentOriginalTextV.setVisibility(View.VISIBLE);
            commentOriginalImageV.setVisibility(View.VISIBLE);
            commentOriginalTextV.setBackgroundResource(R.color.alpha_50_black);
            commentOriginalTextV.setTextColor(holder.getConvertView().getResources().getColor(R.color.txt_dark_content));
            commentOriginalTextV.setText(cInfo.getEvent_title());
            commentOriginalImageV.setImageResource(R.drawable.activity_pic);
            // 点击打开活动详情
            setOnOriginalClickListener(rl_item_comment, cInfo.getNode_id(), cInfo.getNode_type(), false);
        } else {// 已经删除
            commentOriginalImageV.setVisibility(View.GONE);
            commentOriginalTextV.setVisibility(View.VISIBLE);
            commentOriginalTextV.setText(mContext.getResources().getString(
                    R.string.content_is_delete));
            commentOriginalTextV.setTextColor(mContext.getResources().getColor(R.color.txt_live_tip));
            commentOriginalTextV.setBackgroundColor(0xFFF6F8F6);


            // 不用点击事件了
            rl_item_comment.setOnClickListener(null);
        }


        if (!cInfo.isIs_show_content()) {
            //评论内容已经删除 评论已经删除不显示内容，提示删除的文案信息， 1显示评论内容
            commentContentTv.setText(mContext.getResources().getString(
                    R.string.comment_is_delete));
            commentContentTv.setTextColor(mContext.getResources().getColor(R.color.txt_live_tip));

        }


        // 是否已读
        rl_item_comment.setSelected(cInfo.getIs_read() != 2);


        // 最后项填充列表处理
        UIUtils.showView(gapV, holder.getmPosition() < getCount() - 1);


    }

    private void clickCommentText(UserMsgCommentsResp.MsgsBean cInfo) {
        // 已经删除了得内容，就不能评论了
        if (!cInfo.isIs_show_content()) {
            return;
        }


        ActivityRouter.openReplyCommentActivity(mContext,
                cInfo.getComment_id(),
                cInfo.getNick_name(),
                cInfo.getUser_id(),
                cInfo.getNode_id(),
                cInfo.getUser_name());
    }


    /**
     * 给原内容块添加点击监听的方法
     */
    private void setOnOriginalClickListener(LinearLayout commentOriginalRlyt, final String nodeID,
                                            final String mini_log_type, final boolean isModerator) {
        commentOriginalRlyt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mini_log_type.equals("event")) {
                    // 点击跳转到活动详情
                    ActivityRouter.openActionDetailActivity(mContext, nodeID);
                } else {
                    //跳转评论页
                    ActivityRouter.openLiveCommentActivity(mContext, nodeID, isModerator);
                }
            }
        });
    }
}
