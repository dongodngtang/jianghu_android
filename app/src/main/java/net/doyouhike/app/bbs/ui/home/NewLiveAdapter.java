package net.doyouhike.app.bbs.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.ClipboardManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.abslistview.CommonAdapter;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.entity.TimeLineUserInfoEntity;
import net.doyouhike.app.bbs.biz.event.open.DeleteCommentEvent;
import net.doyouhike.app.bbs.biz.newnetwork.Event.SendCommentEvent;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.Timeline;
import net.doyouhike.app.bbs.ui.activity.MainActivity;
import net.doyouhike.app.bbs.ui.home.topic.TimelineRequestType;
import net.doyouhike.app.bbs.ui.release.NewEditLiveActivity;
import net.doyouhike.app.bbs.ui.release.yueban.EditEventActivity;
import net.doyouhike.app.bbs.ui.util.FollowButtonHelper;
import net.doyouhike.app.bbs.ui.util.TipUtil;
import net.doyouhike.app.bbs.ui.util.UserHeadNickClickHelper;
import net.doyouhike.app.bbs.util.ActivityRouter;
import net.doyouhike.app.bbs.util.CommonUtil;
import net.doyouhike.app.bbs.util.LogUtil;
import net.doyouhike.app.bbs.util.StrUtils;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.UIUtils;
import net.doyouhike.app.bbs.util.UserInfoUtil;
import net.doyouhike.app.bbs.util.glide.GlideHelper;
import net.doyouhike.app.bbs.util.linktext.linkutil.LinkUtil;
import net.doyouhike.app.library.ui.utils.DateUtils;
import net.doyouhike.app.library.ui.widgets.linkify.OnTextClick;
import net.doyouhike.app.library.ui.widgets.linkify.SmartTextClickMovement;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 作者:luochangdong on 16/5/18 16:36
 * 描述:
 */
public class NewLiveAdapter extends CommonAdapter<Timeline> implements ILiveAdapterView {

    //直播类型 minilog_type

    private final static String TAG = NewLiveAdapter.class.getSimpleName();
    /**
     * 纯文字
     */
    public final static int LIVE_TYPE_TEXT = 1;
    /**
     * 图文直播
     */
    public final static int LIVE_TYPE_TEXT_IMAGE = 2;
    /**
     * 分享直播(帖子）
     */
    public final static int LIVE_TYPE_URL_SHARE = 3;
    /**
     * 活动直播
     */
    public final static int LIVE_TYPE_ACTION = 4;
    /**
     * 未知，服务器说可以忽略
     */
    public final static int LIVE_TYPE_TRACE = 5;
    /**
     * 转发贴直播
     */

    public final static int LIVE_TYPE_FORWARDING = 6;
    /**
     * 网站贴子
     */
    public final static int LIVE_TYPE_WEB_POST = 7;

    /**
     * 删除贴直播
     */
    public final static int LIVE_TYPE_BE_DELETED = -2;


    private final static int HANDLER_REFRESH = 100;

    public final static long TIME_SS = 1000L;
    /**
     * 动态请求类型
     */
    private TimelineRequestType type;

    /**
     * 他人主页的ID
     */
    private String otherId = "";
    /**
     * 是否在他人,浏览他自己发布的内容,目的,在他人主页浏览他自己的直播item时,不显示关注按钮,和点击头像昵称不跳转
     */
    private boolean isOtherPageItem = false;

    /**
     * 当前登录用户id
     */
    private String currentUserId = "";


    NormalListDialog listDialog;


    private PresenterLiveAdapterImp liveAdapterImp;
    private RelativeLayout.LayoutParams LayoutParams = new RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);

    public NewLiveAdapter(Context context, List<Timeline> datas) {
        super(context, datas, R.layout.item_live_main);
        liveAdapterImp = new PresenterLiveAdapterImp(context, this);
        EventBus.getDefault().register(this);

        String[] itemMenu = new String[]{"复制"};
        listDialog = new NormalListDialog(mContext, itemMenu);
        listDialog.isTitleShow(false);

        //避免每次getView读取SharedPreferences值,降低性能
        currentUserId = UserInfoUtil.getInstance().getUserId();
    }


    //核心方法 主要看这里
    @Override
    public void convert(final ViewHolder holder, final Timeline timeline) {


        if (null != timeline.getForward_node_detail() &&
                StringUtil.isNotEmpty(timeline.getForward_node_detail().getIsDeleted()) &&
                timeline.getForward_node_detail().getIsDeleted().equals("1")) {
            holder.setVisible(R.id.rl_item_live_container, false);
            return;
        } else {
            holder.setVisible(R.id.rl_item_live_container, true);
        }
        final TextView tv_time = holder.getView(R.id.tv_live_time);
        TextView tv_live_is_follow = holder.getView(R.id.tv_live_is_follow);
        //文本块
        LinearLayout ll_live_content = holder.getView(R.id.ll_live_content);
        TextView tv_live_content = holder.getView(R.id.tv_live_content);
        TextView tv_live_content_more = holder.getView(R.id.tv_live_content_more);
        TextView tv_live_content_all = holder.getView(R.id.tv_live_content_all);


        //图片 活动 帖子
        LinearLayout ll_live_action = holder.getView(R.id.ll_live_action);
        RecyclerView rv_live_images = holder.getView(R.id.rv_live_images);
        LinearLayout ll_live_forwarding = holder.getView(R.id.ll_live_forwarding);
        ll_live_action.setVisibility(View.GONE);
        rv_live_images.setVisibility(View.GONE);
        ll_live_forwarding.setVisibility(View.GONE);

        TextView tv_live_action_status = holder.getView(R.id.tv_live_action_status);
        TextView tv_live_action_title = holder.getView(R.id.tv_live_action_title);
        ImageView iv_live_action_image = holder.getView(R.id.iv_live_action_image);
        tv_live_action_status.setVisibility(View.GONE);

        //直播定位
        setLocationData(holder, timeline);
        //作者数据填充
        if (timeline.getUser_info() != null) {
            isOtherPageItem = otherId.equals(timeline.getUser_info().getUser_id());
            setAuthorData(holder, timeline.getUser_info());
        }
        //文本填充
        setTextData(timeline.getContent(), ll_live_content,
                tv_live_content, tv_live_content_more, tv_live_content_all, timeline);


        //查看详情
        lookDetail(ll_live_action, timeline);

        //TAG标签
        setTagData(timeline, holder);

        //更多 评论 点赞
        setLikeShareData(timeline, holder);

        //直播发送状态
        handReleaseAndTime(holder, timeline, tv_time);
        //关注
        tv_live_is_follow.setVisibility(View.GONE);
        if (null != timeline.getUser_info() &&
                timeline.getMinilog_type() != LIVE_TYPE_FORWARDING) {
            setIsFollowState(timeline.getIs_follow(), timeline.getUser_info().getUser_id(), tv_live_is_follow);
        }


        //变化区填充数据
        switch (timeline.getMinilog_type()) {
            case LIVE_TYPE_TEXT:
                //文字内容
                break;
            case LIVE_TYPE_TEXT_IMAGE:
                //图片


                List<Timeline.ImagesEntity> mImages = timeline.getImages();
                if (mImages != null && !mImages.isEmpty()) {
                    List<String> images = new ArrayList<>();
                    for (Timeline.ImagesEntity image : mImages) {
                        images.add(image.getReal_file());
                    }
                    setImageData(rv_live_images, images);
                }

                break;
            case LIVE_TYPE_URL_SHARE:

                break;
            case LIVE_TYPE_ACTION:
                //活动
                ll_live_action.setVisibility(View.VISIBLE);
                tv_live_action_status.setVisibility(View.VISIBLE);
                setActiveData(tv_live_action_status, tv_live_action_title, iv_live_action_image, timeline.getEvent());

                break;
            case LIVE_TYPE_TRACE:
                CommonUtil.errorLog("服务器返回的主页的json中minilog_type = 5的情况，json = "
                        + "，标识码201511271947");// TODO

                break;
            case LIVE_TYPE_FORWARDING:
                //转发

                ll_live_forwarding.setVisibility(View.VISIBLE);
                if (null != timeline.getForward_node_detail()) {
                    if (null != timeline.getForward_node_detail().getIsDeleted()
                            && timeline.getForward_node_detail().getIsDeleted().equals("1")) {
                        //原帖已删除

                    } else {

                        final Timeline.ForwardNodeDetailEntity forwardEntity = timeline.getForward_node_detail();
                        if (forwardEntity != null) {
                            //转发作者昵称
                            if (timeline.getUser_info() != null) {
                                holder.setText(R.id.tv_live_nickname_forwarding, timeline.getUser_info().getNick_name());
                                holder.setOnClickListener(R.id.tv_live_nickname_forwarding, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        //他人主页点击自己的昵称,不跳转
                                        if (isOtherPageItem) {
                                            return;
                                        }


                                        liveAdapterImp.clickAuthor(timeline.getUser_info().getUser_id());
                                    }
                                });

                            }
                            //转发作者写的内容
                            TextView tv_forwarding_content = holder.getView(R.id.tv_forwarding_content);
                            tv_forwarding_content.setText(timeline.getContent());
                            UIUtils.showView(tv_forwarding_content, StringUtil.isNotEmpty(timeline.getContent()));


                            if (StringUtil.isEmpty(forwardEntity.getContent()))
                                ll_live_content.setVisibility(View.GONE);
                            else {
                                ll_live_content.setVisibility(View.VISIBLE);
                                setTextData(forwardEntity.getContent(), ll_live_content,
                                        tv_live_content, tv_live_content_more, tv_live_content_all, timeline);

                            }

                            //原作者
                            if (forwardEntity.getUser_info() != null)
                                setAuthorData(holder, forwardEntity.getUser_info());


                            switch (forwardEntity.getMinilog_type()) {
                                case LIVE_TYPE_WEB_POST:
                                    Timeline.ForwardNodeDetailEntity.TopicBean topicBean = forwardEntity.getTopic();
                                    if (topicBean != null) {
                                        ll_live_action.setVisibility(View.VISIBLE);
                                        ll_live_content.setVisibility(View.GONE);
                                        setTopicData(iv_live_action_image, tv_live_action_title,
                                                topicBean.getTitle(), topicBean.getIcon());
                                    }
                                    break;
                                case LIVE_TYPE_ACTION:

                                    if (forwardEntity.getEvent() != null) {
                                        ll_live_action.setVisibility(View.VISIBLE);
                                        tv_live_action_status.setVisibility(View.VISIBLE);
                                        iv_live_action_image.setImageResource(R.drawable.activity_pic);
                                        tv_live_action_title.setText(forwardEntity.getEvent().getTitle());
                                        setActivityStatus(tv_live_action_status, forwardEntity.getEvent().getStatus());
                                    }

                                    break;
                                case LIVE_TYPE_TEXT:

                                    break;
                                case LIVE_TYPE_TEXT_IMAGE:
                                    List<Timeline.ForwardNodeDetailEntity.ImagesEntity> forwardImages = forwardEntity.getImages();
                                    if (forwardImages != null && !forwardImages.isEmpty()) {
                                        List<String> forwardItems = new ArrayList<>();
                                        for (Timeline.ForwardNodeDetailEntity.ImagesEntity item : forwardImages) {
                                            forwardItems.add(item.getReal_file());
                                        }
                                        setImageData(rv_live_images, forwardItems);
                                    }

                                    break;
                            }
                        }
                    }
                }
                break;
            case LIVE_TYPE_BE_DELETED:
                //删除
//                ll_live_action.setVisibility(View.VISIBLE);
//                tv_live_action_title.setText(R.string.sorry_this_is_deleted_by_author);
                break;
            case LIVE_TYPE_WEB_POST:
                //帖子
                Timeline.TopicEntity topic = timeline.getTopic();
                if (topic != null) {
                    ll_live_action.setVisibility(View.VISIBLE);
                    setTopicData(iv_live_action_image, tv_live_action_title,
                            topic.getTitle(), topic.getIcon());
                }

                break;
            default:
                break;
        }

    }

    /**
     * 设置他人主页ID
     *
     * @param otherId 他人主页ID
     */
    public void setOtherId(String otherId) {
        this.otherId = otherId;
    }

    /**
     * @param currentUserId 当前已经登录用户ID
     */
    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    private void setAuthorData(ViewHolder holder, final TimeLineUserInfoEntity userInfoEntity) {

        ImageView iv_live_header = holder.getView(R.id.iv_live_header);
        TextView tv_live_nickname = holder.getView(R.id.tv_live_nickname);
        tv_live_nickname.setText(userInfoEntity.getNick_name());
        if (StringUtil.isNotEmpty(userInfoEntity.getAvatar())) {
            GlideHelper.displayHeader(mContext, iv_live_header, userInfoEntity.getAvatar());
        }

        tv_live_nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //他人主页点击自己的昵称,不跳转
                if (isOtherPageItem) {
                    return;
                }

                ActivityRouter.openOtherPageActivity(v.getContext(), userInfoEntity.getUser_id());
            }
        });


        //他人主页点击自己的头像,不弹窗
        if (isOtherPageItem) {
            iv_live_header.setOnClickListener(null);
        } else {

            // 点击头像，他人主页
            UserHeadNickClickHelper.getInstance().setClickListener(iv_live_header,
                    userInfoEntity.getNick_name(),
                    userInfoEntity.getUser_id(),
                    userInfoEntity.getUuid(),
                    userInfoEntity.getAvatar());
        }


        if (currentUserId.equals(userInfoEntity.getUser_id())) {
            iv_live_header.setClickable(false);
        } else {
            iv_live_header.setClickable(true);
        }
    }


    /**
     * 处理发送直播
     *
     * @param holder
     * @param timeline
     * @param tv_time
     */
    private void handReleaseAndTime(ViewHolder holder, final Timeline timeline, TextView tv_time) {

//        if (timeline.getForward_node_detail() == null) {
//
//            if (StringUtil.isNotEmpty(timeline.getTime()))
//                tv_time.setText(getNormalTime(timeline.getTime()));
//
//        } else {
//            if (StringUtil.isNotEmpty(timeline.getForward_node_detail().getTime()))
//                tv_time.setText(getNormalTime(timeline.getForward_node_detail().getTime()));
//        }
        if (StringUtil.isNotEmpty(timeline.getTime())) {
            tv_time.setText(getNormalTime(timeline.getTime()));
            tv_time.setTextColor(mContext.getResources().getColor(R.color.text_gray));
        }


        if (timeline.getReleaseStatus() == 0) {
            releaseState(holder, true);
            return;
        } else {
            releaseState(holder, false);
        }

        switch (timeline.getReleaseStatus()) {
            case Content.WAIT:
                holder.getView(R.id.ll_live_user_info).setClickable(false);
//                ReleaseEvent(holder, timeline);
                break;
            case Content.SENDING:
                tv_time.setTextColor(mContext.getResources().getColor(R.color.orange_word));
                tv_time.setText(StrUtils.getResourcesStr(mContext,
                        R.string.sending));
                holder.getView(R.id.ll_live_user_info).setClickable(false);
                break;
            case Content.SEND_OK:
                releaseState(holder, true);
                holder.getView(R.id.ll_live_user_info).setClickable(true);

                holder.getView(R.id.ll_live_user_info).setOnClickListener(null);
                break;
            case Content.SEND_FAIL:
                setReleaseFailStyle(holder, timeline, tv_time);

                tv_time.setText(StrUtils.getResourcesStr(mContext,
                        R.string.send_live_fail_tip));
                break;
            case Content.RELEASE_USER_LIMIT:
                //用户被冻结
                setReleaseFailStyle(holder, timeline, tv_time);
                tv_time.setText(R.string.account_freeze);
                break;
            case Content.RELEASE_LIVE_OVER_COUNT:
                //用户被冻结
                setReleaseFailStyle(holder, timeline, tv_time);
                tv_time.setText(R.string.release_over_count);
                TipUtil.alert(mContext, "无法发布", "发布次数超限制，请稍后再试");
                break;
            default:
                //发布的时间

                break;

        }
    }

    private void setReleaseFailStyle(ViewHolder holder, final Timeline timeline, TextView tv_time) {
        holder.setVisible(R.id.tv_live_location, false);
        holder.setVisible(R.id.line_live_location, false);
        tv_time.setTextColor(mContext.getResources().getColor(R.color.orange_word));
        holder.getView(R.id.ll_live_user_info).setClickable(true);
        holder.getView(R.id.ll_live_user_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReleaseFailDialog(timeline);
            }
        });
    }

    private void releaseState(ViewHolder holder, Boolean clickable) {
        holder.getView(R.id.iv_live_share).setClickable(clickable);
        holder.getView(R.id.iv_live_like).setClickable(clickable);
        holder.getView(R.id.ll_live_comment).setClickable(clickable);
        holder.getView(R.id.ll_live_item).setClickable(clickable);
        holder.getView(R.id.ll_live_action).setClickable(clickable);
    }


    /**
     * 发送直播失败的弹窗
     *
     * @param timeline
     */
    private void showReleaseFailDialog(final Timeline timeline) {

        String title = mContext.getString(R.string.live_send_fail);
        String content = mContext.getString(R.string.try_again_or_delete);
        String left = mContext.getString(R.string.cancel);
        String right = mContext.getString(R.string.try_again);


        TipUtil.alert(mContext
                , title
                , content
                , new String[]{left, right}
                , new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                    }
                }
                , new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        if (timeline.getMinilog_type() == LIVE_TYPE_ACTION) {
                            goEditEventActivity(timeline);
                        } else {
                            goEditLiveActivity(timeline);
                        }
                    }
                });

    }

    private void goEditLiveActivity(Timeline timeline) {
        if (mContext instanceof MainActivity) {
            Intent intent = new Intent(mContext, NewEditLiveActivity.class);
            Bundle budle = new Bundle();
            budle.putSerializable(Content.TIMELINE, timeline);
            intent.putExtras(budle);
            MainActivity mainActivity = (MainActivity) mContext;
            mainActivity.startActivityForResult(intent,
                    MainActivity.REQUEST_CODE_TO_RELEASE_LIVE);
        }

    }

    private void goEditEventActivity(Timeline timeline) {
        Intent intent = new Intent(mContext, EditEventActivity.class);
        Bundle budle = new Bundle();
        budle.putSerializable(Content.TIMELINE, timeline);
        intent.putExtras(budle);
        mContext.startActivity(intent);
    }

    private void setTopicData(ImageView iv_live_action_image, TextView tv_live_action_title,
                              String title, String icon) {


        tv_live_action_title.setText(title);

        if (StringUtil.isEmpty(icon))
            iv_live_action_image.setImageResource(R.drawable.ic_home_repost_link_3x);
        else
            GlideHelper.displayNetForwardImage(mContext, iv_live_action_image, icon);


    }

    private void lookDetail(LinearLayout ll_live_action, final Timeline timeline) {

        ll_live_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liveAdapterImp.lookDetail(timeline);
            }
        });

    }


    /**
     * 活动召集
     *
     * @param tv_live_action_status
     * @param tv_live_action_title
     * @param iv_live_action_image
     * @param active
     */
    private void setActiveData(TextView tv_live_action_status, TextView tv_live_action_title,
                               ImageView iv_live_action_image, Timeline.EventEntity active) {
        if (active == null) {
            return;
        }

        iv_live_action_image.setImageResource(R.drawable.activity_pic);

        setActivityStatus(tv_live_action_status, active.getStatus());

        tv_live_action_title.setText(active.getTitle());


    }

    private void setActivityStatus(TextView tv_live_action_status, String status) {
        if (StringUtil.isNotEmpty(status)) {
            if (status.equals("1")) {
                tv_live_action_status.setText(mContext.getString(R.string.event_recruiting));
                tv_live_action_status.setBackgroundResource(R.color.color_embellish);
            } else if (status.equals("2")) {
                tv_live_action_status.setText(mContext.getString(R.string.event_full));
                tv_live_action_status.setBackgroundResource(R.color.color_embellish);
            } else if (status.equals("3")) {
                tv_live_action_status.setText(R.string.action_over);
                tv_live_action_status.setBackgroundResource(R.color.color_alarm);
            }
        }
    }


    /**
     * 是否关注
     *
     * @param isfollow
     * @param tv_live_is_follow
     */
    private void setIsFollowState(int isfollow, String userId, TextView tv_live_is_follow) {

        if (type == TimelineRequestType.ATTEND) {
            tv_live_is_follow.setVisibility(View.GONE);
        } else {
            if (isfollow == 0) {//未关注
                tv_live_is_follow.setVisibility(View.VISIBLE);
                FollowButtonHelper.setTextState(tv_live_is_follow, isfollow, userId);
            } else {
                tv_live_is_follow.setVisibility(View.GONE);

            }
            if (currentUserId.equals(userId)) {
                tv_live_is_follow.setVisibility(View.GONE);
            }


            if (isOtherPageItem) {
                tv_live_is_follow.setVisibility(View.GONE);
            }

        }


    }


    /**
     * 点赞　评论
     *
     * @param timeline
     * @param holder
     */
    private void setLikeShareData(final Timeline timeline, ViewHolder holder) {
        final ImageView iv_live_like = holder.getView(R.id.iv_live_like);
        ImageView iv_live_share = holder.getView(R.id.iv_live_share);
        LinearLayout ll_live_comment = holder.getView(R.id.ll_live_comment);

        final TextView tv_like_num = holder.getView(R.id.tv_like_num);

        TextView tv_card_commnet = holder.getView(R.id.tv_card_commnet_num);


        if (StringUtil.isNotEmpty(liveAdapterImp.getLikeNum(timeline))) {
            if (liveAdapterImp.getLikeNum(timeline).equals("0")) {
                tv_like_num.setText("赞");
            } else {
                tv_like_num.setText(liveAdapterImp.getLikeNum(timeline));
            }

            tv_like_num.setTextColor(liveAdapterImp.getIsLike(timeline) == 0 ? mContext.getResources().getColor(R.color.txt_live_tip) :
                    mContext.getResources().getColor(R.color.orange_word));
            if (liveAdapterImp.getIsLike(timeline) == 0) {
                iv_live_like.setImageResource(R.drawable.icon_card_like_default_3x);
            } else if (liveAdapterImp.getIsLike(timeline) == 1) {
                iv_live_like.setImageResource(R.drawable.icon_card_liked_default_3x);
            }

        }
        if (StringUtil.isNotEmpty(liveAdapterImp.getPostNum(timeline))) {
            if (liveAdapterImp.getPostNum(timeline).equals("0")) {
                tv_card_commnet.setText("评论");
            } else {
                tv_card_commnet.setText(liveAdapterImp.getPostNum(timeline));
            }
        }
        iv_live_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liveAdapterImp.clickMore(timeline);
            }
        });

        ll_live_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liveAdapterImp.clickComment(timeline);
            }
        });


        iv_live_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liveAdapterImp.clickLike(iv_live_like, tv_like_num, timeline);
            }
        });

    }


    /**
     * 文本信息
     *
     * @param content
     * @param ll_live_content
     * @param tv_live_content
     * @param tv_live_content_more
     */
    private void setTextData(final String content, LinearLayout ll_live_content,
                             final TextView tv_live_content, final TextView tv_live_content_more,
                             final TextView tv_live_content_all, final Timeline timeline) {


        if (StringUtil.isNotEmpty(content)) {
            ll_live_content.setVisibility(View.VISIBLE);

            LinkUtil.setWebTextView(content, tv_live_content);
            LinkUtil.setWebTextView(content, tv_live_content_all);

            UIUtils.showView(tv_live_content, !timeline.isExpand());
            UIUtils.showView(tv_live_content_all, timeline.isExpand());

            tv_live_content.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listDialog.show();
                    liveContentCopy(content);
                    return false;
                }
            });

            tv_live_content_all.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listDialog.show();
                    liveContentCopy(content);
                    return false;
                }
            });

            tv_live_content_all.setMovementMethod(SmartTextClickMovement.getInstance());

            SmartTextClickMovement smartTextClickMovement = SmartTextClickMovement.getInstance();
            smartTextClickMovement.setOnTextClick(new OnTextClick() {
                @Override
                public void onTextClicked() {
                    tv_live_content.setVisibility(View.GONE);
                    tv_live_content_more.setVisibility(View.GONE);
                    tv_live_content_all.setVisibility(View.VISIBLE);
                    timeline.setExpand(true);
                }
            });
            tv_live_content.setMovementMethod(smartTextClickMovement);
            tv_live_content_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_live_content.setVisibility(View.GONE);
                    tv_live_content_more.setVisibility(View.GONE);
                    tv_live_content_all.setVisibility(View.VISIBLE);
                    timeline.setExpand(true);
                }
            });
            tv_live_content.post(new Runnable() {
                @Override
                public void run() {
                    if (tv_live_content.getLineCount() > 3) {
                        tv_live_content_more.setVisibility(View.VISIBLE);
                    } else
                        tv_live_content_more.setVisibility(View.GONE);
                }
            });

        } else {
            ll_live_content.setVisibility(View.GONE);
        }
    }

    private void liveContentCopy(final String content) {
        listDialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        listDialog.dismiss();
                        ClipboardManager clip = (ClipboardManager) mContext
                                .getSystemService(Context.CLIPBOARD_SERVICE);
                        clip.setText(content); // 复制
//                        StringUtil.showSnack(mContext, "复制成功");
                        break;
                }
            }
        });
    }

    /**
     * 位置信息
     *
     * @param holder
     * @param timeline
     */
    private void setLocationData(ViewHolder holder, Timeline timeline) {

        TextView tv_live_location = holder.getView(R.id.tv_live_location);
        View line_live_location = holder.getView(R.id.line_live_location);

        if (timeline.getLocation() != null &&
                !timeline.getLocation().getLocation().isEmpty()
                && timeline.getMinilog_type() != LIVE_TYPE_ACTION) {

            line_live_location.setVisibility(View.VISIBLE);
            tv_live_location.setVisibility(View.VISIBLE);


            tv_live_location.setText(timeline.getLocation().getLocation());
            final Timeline.LocationEntity.LocationSlugBean locationSlug = timeline.getLocation().getLocation_slug();
            if (locationSlug != null && StringUtil.isNotEmpty(locationSlug.getNode_slug())) {
                tv_live_location.setTextColor(mContext.getResources().getColor(R.color.txt_dark_live_location));
                tv_live_location.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (locationSlug.getNode_cat().equals(Content.ROUTE)) {
                            ActivityRouter.openRoadDetailActivity(mContext, locationSlug.getNode_slug());
                        } else {
                            ActivityRouter.openDesAndRoadListActivity(mContext, locationSlug.getNode_slug());
                        }

                    }
                });
            } else {
                tv_live_location.setTextColor(mContext.getResources().getColor(R.color.txt_light_remark));
                tv_live_location.setOnClickListener(null);
            }


        } else {
            line_live_location.setVisibility(View.GONE);
            tv_live_location.setVisibility(View.GONE);
        }


    }

    /**
     * 标签
     *
     * @param timeline
     * @param holder
     */
    private void setTagData(Timeline timeline, ViewHolder holder) {
        //直播标签
        LinearLayout rl_live_tag = holder.getView(R.id.ll_live_tag);
        TextView tv_live_tag1 = holder.getView(R.id.tv_live_type_1);
        TextView tv_live_tag2 = holder.getView(R.id.tv_live_type_2);
        TextView tv_live_tag3 = holder.getView(R.id.tv_live_type_3);

        rl_live_tag.setVisibility(View.GONE);
        tv_live_tag1.setVisibility(View.GONE);
        tv_live_tag2.setVisibility(View.GONE);
        tv_live_tag3.setVisibility(View.GONE);
        Timeline.ForwardNodeDetailEntity forward = timeline.getForward_node_detail();
        List<Timeline.TagsEntity> tags;
        if (forward == null)
            tags = timeline.getTags();
        else
            tags = forward.getTags();

        if (null != tags) {

            switch (tags.size()) {
                case 1:
                    rl_live_tag.setVisibility(View.VISIBLE);
                    tv_live_tag1.setVisibility(View.VISIBLE);
                    tv_live_tag1.setText(tags.get(0).getDesc());
                    break;
                case 2:
                    rl_live_tag.setVisibility(View.VISIBLE);
                    tv_live_tag1.setVisibility(View.VISIBLE);
                    tv_live_tag2.setVisibility(View.VISIBLE);
                    tv_live_tag1.setText(tags.get(0).getDesc());
                    tv_live_tag2.setText(tags.get(1).getDesc());
                    break;
                case 3:
                    rl_live_tag.setVisibility(View.VISIBLE);
                    tv_live_tag1.setVisibility(View.VISIBLE);
                    tv_live_tag2.setVisibility(View.VISIBLE);
                    tv_live_tag3.setVisibility(View.VISIBLE);
                    tv_live_tag1.setText(tags.get(0).getDesc());
                    tv_live_tag2.setText(tags.get(1).getDesc());
                    tv_live_tag3.setText(tags.get(2).getDesc());
                    break;
                default:
                    rl_live_tag.setVisibility(View.GONE);
                    break;
            }
        }

    }

    private String getNormalTime(String untime) {
        long time = Long.parseLong(untime) * TIME_SS;
        return DateUtils.getStrDate(mContext, time);
    }


    /**
     * 图片 贴
     *
     * @param rv_live_images
     * @param mImages
     */
    private void setImageData(RecyclerView rv_live_images, List<String> mImages) {
        rv_live_images.setVisibility(View.VISIBLE);
        GalleryAdapter mAdapter = null;
        if (rv_live_images.getAdapter() != null) {
            mAdapter = (GalleryAdapter) rv_live_images.getAdapter();
            mAdapter.setmDatas(mImages);
        } else {
            //设置布局管理器
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            rv_live_images.setLayoutManager(linearLayoutManager);

            mAdapter = new GalleryAdapter(mContext);
            rv_live_images.setAdapter(mAdapter);
            mAdapter.setmDatas(mImages);

        }
    }


    public List<Timeline> getDatas() {
        return mDatas;
    }

    public void setType(TimelineRequestType type) {
        liveAdapterImp.isHot(type);
        this.type = type;
    }


    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        EventBus.getDefault().unregister(this);
    }






    @Override
    public void refreshFollow(final String userId, final int isFollow) {
        for (Timeline timeline : mDatas) {
            if (null == timeline || timeline.getUser_info() == null) {
                continue;
            }
            if (userId.equals(timeline.getUser_info().getUser_id())) {
                timeline.setIs_follow(isFollow);
            }
        }
        mHandler.sendEmptyMessage(HANDLER_REFRESH);
    }


    /**
     * 评论 响应
     *
     * @param event
     */
    public void onEventMainThread(SendCommentEvent event) {

        if (null == event || event.getResponse() == null) {
            return;
        }

        Response response = event.getResponse();

        for (Timeline timeline : mDatas) {
            String forwardNode = "";
            if (timeline.getForward_node_detail() != null)
                forwardNode = timeline.getForward_node_detail().getNode_id();

            String nodeId = (String) response.getExtraTag();

            if (null == nodeId) {
                return;
            }

            if (nodeId.equals(timeline.getNode_id()) ||
                    nodeId.equals(forwardNode)) {
                LogUtil.d("收到评论事件" + timeline.getNode_id());
                if (timeline.getForward_node_detail() != null) {
                    int postNum = Integer.parseInt(timeline.getForward_node_detail().getPost_num()) + 1;
                    timeline.getForward_node_detail().setPost_num(String.valueOf(postNum));
                } else {
                    int postNum = Integer.parseInt(timeline.getPost_num()) + 1;
                    timeline.setPost_num(String.valueOf(postNum));
                }
            }
        }
        mHandler.sendEmptyMessage(HANDLER_REFRESH);
    }



    /**
     * @param event 删除评论消息
     */
    public void onEventMainThread(DeleteCommentEvent event) {
        for (Timeline timeline : mDatas) {
            String forwardNode = "";
            if (timeline.getForward_node_detail() != null)
                forwardNode = timeline.getForward_node_detail().getNode_id();

            if (event.getNodeId().equals(timeline.getNode_id()) ||
                    event.getNodeId().equals(forwardNode)) {

                LogUtil.d("收到评论事件" + timeline.getNode_id());
                if (timeline.getForward_node_detail() != null) {
                    int postNum = Integer.parseInt(timeline.getForward_node_detail().getPost_num()) - 1;
                    timeline.getForward_node_detail().setPost_num(String.valueOf(postNum));
                } else {
                    int postNum = Integer.parseInt(timeline.getPost_num()) - 1;
                    timeline.setPost_num(String.valueOf(postNum));
                }
            }
        }
        mHandler.sendEmptyMessage(HANDLER_REFRESH);
    }




    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_REFRESH:
                    notifyDataSetChanged();
                    break;
            }
        }
    };

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }
}
