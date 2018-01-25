package net.doyouhike.app.bbs.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.google.gson.Gson;
import com.yolanda.nohttp.RequestMethod;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.abslistview.CommonAdapter;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.application.MyApplication;
import net.doyouhike.app.bbs.biz.event.CommentEvent;
import net.doyouhike.app.bbs.biz.event.open.AccountUserFollowEvent;
import net.doyouhike.app.bbs.biz.event.open.DeleteCommentEvent;
import net.doyouhike.app.bbs.biz.event.open.DeleteNodeEvent;
import net.doyouhike.app.bbs.biz.event.open.NodesFavoriteEvent;
import net.doyouhike.app.bbs.biz.event.open.NodesLikeEvent;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.BaseTag;
import net.doyouhike.app.bbs.biz.openapi.presenter.AttendState;
import net.doyouhike.app.bbs.biz.openapi.presenter.EventHelper;
import net.doyouhike.app.bbs.biz.openapi.presenter.UsersHelper;
import net.doyouhike.app.bbs.biz.openapi.request.nodes.NodesFavoritesPost;
import net.doyouhike.app.bbs.biz.openapi.request.nodes.NodesLikePost;
import net.doyouhike.app.bbs.biz.openapi.response.LoginUser;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.NodeTimeline;
import net.doyouhike.app.bbs.chat.helper.Constant;
import net.doyouhike.app.bbs.ui.activity.MainActivity;
import net.doyouhike.app.bbs.ui.activity.login.LoginActivity;
import net.doyouhike.app.bbs.ui.home.GalleryAdapter;
import net.doyouhike.app.bbs.ui.home.LiveMoreDialog;
import net.doyouhike.app.bbs.ui.home.topic.TimelineRequestType;
import net.doyouhike.app.bbs.ui.release.NewEditLiveActivity;
import net.doyouhike.app.bbs.ui.release.yueban.EditEventActivity;
import net.doyouhike.app.bbs.ui.util.FollowButtonHelper;
import net.doyouhike.app.bbs.ui.util.TipUtil;
import net.doyouhike.app.bbs.ui.util.UserHeadNickClickHelper;
import net.doyouhike.app.bbs.util.ActivityRouter;
import net.doyouhike.app.bbs.util.LogUtil;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;
import net.doyouhike.app.bbs.util.StatisticsEventUtil;
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

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 作者：luochangdong on 16/10/10
 * 描述：
 */
public class NodeTimelineAdapter extends CommonAdapter<NodeTimeline.ItemsBean> {
    //直播类型 minilog_type

    private final static String TAG = NodeTimelineAdapter.class.getSimpleName();

    /**
     * 图文直播
     */
    public final static String NODE_TEXT_PHOTO = "text_photo";

    /**
     * 活动直播
     */
    public final static String NODE_EVENT = "event";

    /**
     * 转发贴直播
     */

    public final static String NODE_FORWARD = "forward";
    /**
     * 网站贴子
     */
    public final static String NODE_DISCUSSION = "discussion";

    /**
     * 当前登录用户id
     */
    private String currentUserId = "";
    NormalListDialog listDialog;

    /**
     * 他人主页的ID
     */
    private String otherId = "";
    /**
     * 是否在他人,浏览他自己发布的内容,目的,在他人主页浏览他自己的直播item时,不显示关注按钮,和点击头像昵称不跳转
     */
    private boolean isOtherPageItem = false;

    /**
     * 动态请求类型
     */
    private TimelineRequestType type;

    private final static int HANDLER_REFRESH = 100;

    public final static long TIME_SS = 1000L;


    public NodeTimelineAdapter(Context context, List<NodeTimeline.ItemsBean> datas) {
        super(context, datas, R.layout.item_live_main);
        //避免每次getView读取SharedPreferences值,降低性能
        currentUserId = UserInfoUtil.getInstance().getUserId();

        String[] itemMenu = new String[]{"复制"};
        listDialog = new NormalListDialog(mContext, itemMenu);
        listDialog.isTitleShow(false);
        MyApplication.getInstance().registerEventBus(this);
    }

    /**
     * 添加评论响应
     *
     * @param response
     */
    public void onEventMainThread(CommentEvent response) {
        if (response.getCode() == 0) {
            final String node_id = (String) response.getExtraTag();
            Observable.from(getDatas())
                    .filter(new Func1<NodeTimeline.ItemsBean, Boolean>() {
                        @Override
                        public Boolean call(NodeTimeline.ItemsBean itemsBean) {
                            if (itemsBean.getNode().getForward() != null &&
                                    itemsBean.getNode().getForward().getNode() != null
                                    && StringUtil.isNotEmpty(itemsBean.getNode().getForward().getNode().getNode_id())) {
                                return itemsBean.getNode().getForward().getNode().getNode_id().equals(node_id);
                            } else
                                return itemsBean.getNode().getNode_id().equals(node_id);
                        }
                    }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<NodeTimeline.ItemsBean>() {
                        @Override
                        public void call(NodeTimeline.ItemsBean itemsBean) {
                            if (itemsBean.getNode_counter() == null)
                                return;
                            LogUtil.d(TAG, this.hashCode() + " CommentEvent:"
                                    + node_id + " adapter:"
                                    + itemsBean.getNode().getNode_id());
                            itemsBean.getNode_counter()
                                    .setComments_num(itemsBean.getNode_counter().getComments_num() + 1);
                            notifyDataSetChanged();
                        }
                    });
        }
    }

    /**
     * 删除评论响应
     *
     * @param response
     */
    public void onEventMainThread(DeleteCommentEvent response) {
        final String node_id = response.getNodeId();
        Observable.from(getDatas())
                .filter(new Func1<NodeTimeline.ItemsBean, Boolean>() {
                    @Override
                    public Boolean call(NodeTimeline.ItemsBean itemsBean) {
                        if (itemsBean.getNode().getForward() != null &&
                                itemsBean.getNode().getForward().getNode() != null
                                && StringUtil.isNotEmpty(itemsBean.getNode().getForward().getNode().getNode_id())) {
                            return itemsBean.getNode().getForward().getNode().getNode_id().equals(node_id);
                        } else
                            return itemsBean.getNode().getNode_id().equals(node_id);
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NodeTimeline.ItemsBean>() {
                    @Override
                    public void call(NodeTimeline.ItemsBean itemsBean) {
                        if (itemsBean.getNode_counter() == null)
                            return;
                        LogUtil.d(TAG, this.hashCode() + " CommentEvent:"
                                + node_id + " adapter:"
                                + itemsBean.getNode().getNode_id());

                        if (itemsBean.getNode_counter().getComments_num() != 0)
                            itemsBean.getNode_counter()
                                    .setComments_num(itemsBean.getNode_counter().getComments_num() - 1);
                        notifyDataSetChanged();
                    }
                });

    }

    /**
     * 关注响应
     *
     * @param response
     */
    public void onEventMainThread(AccountUserFollowEvent response) {
        LogUtil.d(TAG, "AccountUserFollowEvent");
        if (response.getCode() == 0) {
            if (this != null)
                notifyDataSetChanged();
        }
    }

    /**
     * 点赞响应
     *
     * @param response
     */
    public void onEventMainThread(NodesLikeEvent response) {
        if (this == null)
            return;
        if (response.getCode() == 0) {
            LogUtil.d(TAG, "NodesLikeEvent");
            final NodesLikePost.LikeMode likeMode = (NodesLikePost.LikeMode) response.getExtraTag();
            Observable.from(getDatas())
                    .filter(new Func1<NodeTimeline.ItemsBean, Boolean>() {
                        @Override
                        public Boolean call(NodeTimeline.ItemsBean itemsBean) {
                            if (itemsBean.getNode().getForward() != null &&
                                    itemsBean.getNode().getForward().getNode() != null
                                    && StringUtil.isNotEmpty(itemsBean.getNode().getForward().getNode().getNode_id())) {
                                return itemsBean.getNode().getForward().getNode().getNode_id().equals(likeMode.getNode_id());
                            } else
                                return itemsBean.getNode().getNode_id().equals(likeMode.getNode_id());
                        }
                    }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<NodeTimeline.ItemsBean>() {

                        @Override
                        public void call(NodeTimeline.ItemsBean itemsBean) {
                            LogUtil.d(TAG, this.hashCode() +
                                    " likeMode:" + likeMode.getNode_id()
                                    + "   adapter:" + itemsBean.getNode().getNode_id());
                            itemsBean.getNode_counter().setLiked(likeMode.isLike());
                            if (likeMode.isLike()) {
                                //设置点击数量+1
                                itemsBean.getNode_counter()
                                        .setLike_num(itemsBean.getNode_counter().getLike_num() + 1);
                            } else {
                                //设置点击数量 -1
                                if (itemsBean.getNode_counter().getLike_num() != 0)
                                    itemsBean.getNode_counter()
                                            .setLike_num(itemsBean.getNode_counter().getLike_num() - 1);
                            }
                            notifyDataSetChanged();

                        }
                    });

        }

    }

    /**
     * 收藏
     *
     * @param response
     */
    public void onEventMainThread(NodesFavoriteEvent response) {
        if (response.getCode() == 0 && this != null) {
            try {
                LogUtil.d(TAG, this.hashCode() + " NodesFavoriteEvent");
                final NodesFavoritesPost post = (NodesFavoritesPost) response.getExtraTag();
                Observable.from(getDatas())
                        .filter(new Func1<NodeTimeline.ItemsBean, Boolean>() {
                            @Override
                            public Boolean call(NodeTimeline.ItemsBean itemsBean) {
                                if (itemsBean.getNode().getForward() != null &&
                                        itemsBean.getNode().getForward().getNode() != null
                                        && StringUtil.isNotEmpty(itemsBean.getNode().getForward().getNode().getNode_id())) {
                                    return itemsBean.getNode().getForward().getNode().getNode_id().equals(post.getNode_id());
                                } else
                                    return itemsBean.getNode().getNode_id().equals(post.getNode_id());
                            }
                        }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<NodeTimeline.ItemsBean>() {

                            @Override
                            public void call(NodeTimeline.ItemsBean itemsBean) {
                                LogUtil.d(TAG, this.hashCode() +
                                        " Favorited:" + post.getNode_id()
                                        + "   adapter:" + itemsBean.getNode().getNode_id());

                                itemsBean.getNode_counter()
                                        .setFavorited(post.getRequestMethod() == RequestMethod.POST);

                                notifyDataSetChanged();

                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 删除Node响应
     *
     * @param response
     */
    public void onEventMainThread(DeleteNodeEvent response) {
        if (response.getCode() == 0 && this != null) {
            try {
                LogUtil.d(TAG, this.hashCode() + " DeleteNodeEvent");
                String nodeId = (String) response.getExtraTag();
                remove(nodeId);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void remove(String nodeId) {
        NodeTimeline.ItemsBean deleteNode = new NodeTimeline.ItemsBean();
        deleteNode.setNode(new NodeTimeline.ItemsBean.NodeBean(nodeId));
        getDatas().remove(deleteNode);
        notifyDataSetChanged();

    }

    @Override
    public void convert(ViewHolder holder, final NodeTimeline.ItemsBean timeline) {

        if (timeline.getNode() == null) {
            holder.setVisible(R.id.rl_item_live_container, false);
            return;
        } else {
            holder.setVisible(R.id.rl_item_live_container, true);
        }

        //图片 活动 转发
        LinearLayout ll_live_action = holder.getView(R.id.ll_live_action);
        RecyclerView rv_live_images = holder.getView(R.id.rv_live_images);
        LinearLayout ll_live_forwarding = holder.getView(R.id.ll_live_forwarding);
        //文本块
        LinearLayout ll_live_content = holder.getView(R.id.ll_live_content);
        final TextView tv_time = holder.getView(R.id.tv_live_time);
        TextView tv_live_action_status = holder.getView(R.id.tv_live_action_status);

        ll_live_action.setVisibility(View.GONE);
        rv_live_images.setVisibility(View.GONE);
        ll_live_forwarding.setVisibility(View.GONE);
        ll_live_content.setVisibility(View.GONE);
        tv_live_action_status.setVisibility(View.GONE);    //活动状态

        //位置信息
        setLocation(holder, timeline);


        //直播发送状态
        handReleaseAndTime(holder, timeline, tv_time);
        //点赞 评论 分享
        setLikeCommentShare(holder, timeline);

        if (timeline.getNode().getMinilog_type().equals(NODE_FORWARD)) {//转发
            if (timeline.getNode().getForward() == null ||
                    timeline.getNode().getForward().getNode() == null) {
                holder.setVisible(R.id.rl_item_live_container, false);
                return;
            }
            setNodeData(holder, timeline.getNode().getForward().getNode());
            ll_live_forwarding.setVisibility(View.VISIBLE);
            TextView tv_forwarding_content = holder.getView(R.id.tv_forwarding_content);
            tv_forwarding_content.setVisibility(View.GONE);
            TextView tv_live_nickname_forwarding = holder.getView(R.id.tv_live_nickname_forwarding);
            if (timeline.getNode().getContent() != null &&
                    StringUtil.isNotEmpty(timeline.getNode().getContent().getText()))
                tv_forwarding_content.setVisibility(View.VISIBLE);
            tv_forwarding_content.setText(timeline.getNode().getContent().getText());

            if (StringUtil.isNotEmpty(timeline.getNode().getUser().getNick_name()))
                holder.setText(R.id.tv_live_nickname_forwarding, timeline.getNode().getUser().getNick_name());

            tv_live_nickname_forwarding.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //他人主页点击自己的昵称,不跳转
                    if (isOtherPageItem) {
                        return;
                    }
                    ActivityRouter.openOtherPageActivity(v.getContext(), timeline.getNode().getUser().getUser_id());
                }
            });
        } else
            setNodeData(holder, timeline.getNode());

        //是否关注
        if (null != timeline.getNode().getUser() &&
                !timeline.getNode().equals(NODE_FORWARD)) {
            setIsFollowState(holder, timeline);
        }
    }

    /**
     * 查看详细
     *
     * @param timeline
     */
    private void lookDetail(NodeTimeline.ItemsBean.NodeBean timeline) {

        if (UserInfoUtil.getInstance().getCurrentUser() != null || type == TimelineRequestType.HOT) {
            toLiveDetailsActivity(timeline);
        } else {
            mContext.startActivity(new Intent(mContext,
                    LoginActivity.class));

        }


    }

    /**
     * 跳转到详情界面
     * 活动 帖子
     *
     * @param node
     */
    protected void toLiveDetailsActivity(NodeTimeline.ItemsBean.NodeBean node) {
        if (node.getMinilog_type().equals(NODE_EVENT)) {
            //活动
            ActivityRouter.openActionDetailActivity(mContext, node.getNode_id());
        } else if (node.getMinilog_type().equals(NODE_DISCUSSION)) {
            //帖子
            NodeTimeline.ItemsBean.NodeBean.TopicBean topicEntity = node.getTopic();
            if (topicEntity != null && StringUtil.isNotEmpty(topicEntity.getUrl())) {
                String url = topicEntity.getUrl();
                if (url.substring(0, 1).equals("/")) {
                    url = url.substring(1);
                }
                ActivityRouter.openWebActivity(mContext,
                        Constant.DOYOUHIKE_DOMAIN_PATH + url);

            }

        }

    }

    /**
     * 设置Node内容
     *
     * @param holder
     */
    private void setNodeData(ViewHolder holder, final NodeTimeline.ItemsBean.NodeBean node) {
        //标签信息
        setTagData(holder, node);
        //查看详情
        holder.setOnClickListener(R.id.ll_live_action, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lookDetail(node);
            }
        });

        //作者信息
        if (node.getUser() != null) {
            isOtherPageItem = otherId.equals(node.getUser().getUser_id());
            setUserData(holder, node.getUser());
        }

        if (node.getMinilog_type().equals(NODE_TEXT_PHOTO)) {//图文

            NodeTimeline.ItemsBean.NodeBean.ContentBean contentBean = node.getContent();
            setContentText(holder, contentBean);


            List<NodeTimeline.ItemsBean.NodeBean.ContentBean.PhotosBean> photos = node.getContent().getPhotos();
            if (photos != null
                    && photos.size() > 0) {
                List<String> images = new ArrayList<>();
                for (NodeTimeline.ItemsBean.NodeBean.ContentBean.PhotosBean image : photos) {
                    images.add(image.getPhotoUrl());
                }
                RecyclerView rv_live_images = holder.getView(R.id.rv_live_images);
                setImageData(rv_live_images, images);
            }

        } else if (node.getMinilog_type().equals(NODE_EVENT)) {//活动
            holder.setVisible(R.id.ll_live_action, true);
            NodeTimeline.ItemsBean.NodeBean.EventBean eventBean = node.getEvent();
            setActiveData(holder, eventBean);

        } else if (node.getMinilog_type().equals(NODE_DISCUSSION)) {//帖子
            NodeTimeline.ItemsBean.NodeBean.TopicBean topic = node.getTopic();
            if (topic != null) {
                holder.setVisible(R.id.ll_live_action, true);
                setTopicData(holder, topic);
            }
        }

    }

    /**
     * 点赞 评论 分享
     *
     * @param holder
     * @param timeline
     */
    private void setLikeCommentShare(ViewHolder holder, final NodeTimeline.ItemsBean timeline) {

        final ImageView iv_live_like = holder.getView(R.id.iv_live_like);
        final TextView tv_like_num = holder.getView(R.id.tv_like_num);
        LinearLayout ll_live_comment = holder.getView(R.id.ll_live_comment);
        TextView tv_card_commnet_num = holder.getView(R.id.tv_card_commnet_num);
        ImageView iv_live_share = holder.getView(R.id.iv_live_share);
        if (timeline.getNode_counter() == null) {
            tv_like_num.setText(R.string.like);
            tv_card_commnet_num.setText(R.string.comment);
            iv_live_like.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_card_like_default_3x));
            tv_like_num.setTextColor(mContext.getResources().getColor(R.color.text_action_detail_zan));
            return;
        }


        tv_like_num.setText(timeline.getNode_counter().getLike_num() == 0 ? "赞" :
                timeline.getNode_counter().getLike_num() + "");
        tv_card_commnet_num.setText(timeline.getNode_counter().getComments_num() == 0 ? "评论" :
                timeline.getNode_counter().getComments_num() + "");

        if (timeline.getNode_counter().isLiked()) {
            iv_live_like.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_card_liked_default_3x));
            tv_like_num.setTextColor(mContext.getResources().getColor(R.color.orange_bg));
        } else {
            iv_live_like.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_card_like_default_3x));
            tv_like_num.setTextColor(mContext.getResources().getColor(R.color.text_action_detail_zan));
        }


        iv_live_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser user = UserInfoUtil.getInstance().getCurrentUser();
                if (user != null && user.getUser().getUser_id() != null) {
                    boolean isMyself = user.getUser().getUser_id().equals(
                            timeline.getNode().getUser().getUser_id());
                    LiveMoreDialog moreDialog = new LiveMoreDialog(
                            mContext, timeline, isMyself);
                    moreDialog.show();
                } else {
                    LiveMoreDialog moreDialog = new LiveMoreDialog(
                            mContext, timeline);
                    moreDialog.show();
                }
            }
        });


        ll_live_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isLogin()) return;
                String commentNodeId = timeline.getNode().getNode_id();
                String nodeType = timeline.getNode().getMinilog_type();
                String user_id = timeline.getNode().getUser().getUser_id();
                if (timeline.getNode().getForward() != null &&
                        timeline.getNode().getForward().getNode() != null
                        && StringUtil.isNotEmpty(timeline.getNode().getForward().getNode().getNode_id())) {
                    commentNodeId = timeline.getNode().getForward().getNode().getNode_id();
                    nodeType = timeline.getNode().getForward().getNode().getMinilog_type();
                    user_id = timeline.getNode().getForward().getNode().getUser().getUser_id();
                }

                if (timeline.getNode_counter().getComments_num() > 0) {
                    if (timeline.getNode().getMinilog_type().equals(NODE_EVENT) ||
                            (timeline.getNode().getForward() != null &&
                                    timeline.getNode().getForward().getNode().getMinilog_type().equals(NODE_EVENT))) {
                        //活动
                        ActivityRouter.openActionDetailActivity(mContext, commentNodeId);
                    } else {
                        //跳转评论页
                        boolean isCanDel = (nodeType != NODE_EVENT) && UserInfoUtil.getInstance().isCurrentUser(user_id);
                        ActivityRouter.openLiveCommentActivity(mContext, commentNodeId, isCanDel);
                    }

                } else {
                    //跳转编辑评论页
                    ActivityRouter.openAddCommentActivity(mContext, commentNodeId);
                }
            }
        });

        iv_live_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin()) return;

                iv_live_like.setClickable(false);

                if (timeline.getNode_counter().isLiked()) {
                    iv_live_like.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_card_like_default_3x));
                    if (timeline.getNode().getForward() != null &&
                            timeline.getNode().getForward().getNode() != null
                            && StringUtil.isNotEmpty(timeline.getNode().getForward().getNode().getNode_id())) {
                        UsersHelper.getSingleTon().unLike(timeline.getNode().getForward().getNode().getNode_id(), mContext, null);
                    } else
                        UsersHelper.getSingleTon().unLike(timeline.getNode().getNode_id(), mContext, null);
                    IOnResponseListener unLike = new IOnResponseListener() {
                        @Override
                        public void onSuccess(Response response) {
                            NodesLikePost.LikeMode likeMode = (NodesLikePost.LikeMode) response.getExtraTag();
                            likeClickSuccess(likeMode, timeline, tv_like_num);
                            iv_live_like.setClickable(true);
                        }

                        @Override
                        public void onError(Response response) {
                            iv_live_like.setClickable(true);

                        }
                    };

                } else {
                    iv_live_like.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_card_liked_default_3x));
                    if (timeline.getNode().getForward() != null &&
                            timeline.getNode().getForward().getNode() != null
                            && StringUtil.isNotEmpty(timeline.getNode().getForward().getNode().getNode_id())) {
                        UsersHelper.getSingleTon().like(timeline.getNode().getForward().getNode().getNode_id(), mContext, null);
                    } else
                        UsersHelper.getSingleTon().like(timeline.getNode().getNode_id(), mContext, null);
                    IOnResponseListener like = new IOnResponseListener() {
                        @Override
                        public void onSuccess(Response response) {
                            iv_live_like.setClickable(true);
                            NodesLikePost.LikeMode likeMode = (NodesLikePost.LikeMode) response.getExtraTag();
                            likeClickSuccess(likeMode, timeline, tv_like_num);
                        }

                        @Override
                        public void onError(Response response) {
                            iv_live_like.setClickable(true);
                        }
                    };
                }
            }
        });

    }

    private boolean isLogin() {
        if (!UserInfoUtil.getInstance().isLogin()) {
            //还没登陆了
            ActivityRouter.openLoginActivity(mContext);
            return true;
        }
        return false;
    }

    /**
     * 点赞处理
     *
     * @param likeMode
     * @param timeline
     * @param tv_like_num
     */
    private void likeClickSuccess(NodesLikePost.LikeMode likeMode, NodeTimeline.ItemsBean timeline, TextView tv_like_num) {
        if (likeMode.isLike()) {
            timeline.getNode_counter().setLiked(true);
            tv_like_num.setTextColor(mContext.getResources().getColor(R.color.orange_bg));
            //设置点击数量+1
            timeline.getNode_counter().setLike_num(timeline.getNode_counter().getLike_num() + 1);
            StatisticsEventUtil.Like(mContext);

        } else {
            timeline.getNode_counter().setLiked(false);

            tv_like_num.setTextColor(mContext.getResources().getColor(R.color.text_action_detail_zan));
            //设置点击数量 -1
            if (timeline.getNode_counter().getLike_num() != 0)
                timeline.getNode_counter().setLike_num(timeline.getNode_counter().getLike_num() - 1);
        }
        tv_like_num.setText(timeline.getNode_counter().getLike_num() == 0 ? "赞" :
                timeline.getNode_counter().getLike_num() + "");
    }


    /**
     * 发送直播的状态
     *
     * @param holder
     * @param timeline
     * @param tv_time
     */
    private void handReleaseAndTime(ViewHolder holder, NodeTimeline.ItemsBean timeline, TextView tv_time) {

        tv_time.setText(getNormalTime(timeline.getNode().getTime()));
        tv_time.setTextColor(mContext.getResources().getColor(R.color.text_gray));


        if (timeline.getNode().getReleaseStatus() == 0) {
            releaseState(holder, true);
            return;
        } else {
            releaseState(holder, false);
        }

        switch (timeline.getNode().getReleaseStatus()) {
            case Content.WAIT:
                tv_time.setTextColor(mContext.getResources().getColor(R.color.orange_word));
                tv_time.setText(StrUtils.getResourcesStr(mContext,
                        R.string.sending));
                holder.getView(R.id.ll_live_user_info).setClickable(false);
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

    private void setReleaseFailStyle(ViewHolder holder, final NodeTimeline.ItemsBean timeline, TextView tv_time) {
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
     * 时间转换
     *
     * @param untime
     * @return
     */
    private String getNormalTime(long untime) {
        long time = untime * TIME_SS;
        return DateUtils.getStrDate(mContext, time);
    }

    /**
     * 发送直播失败的弹窗
     *
     * @param timeline
     */
    private void showReleaseFailDialog(final NodeTimeline.ItemsBean timeline) {

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
                        if (timeline.getNode().getMinilog_type().equals(NODE_EVENT)) {
                            goEditEventActivity();
                        } else {
                            goEditLiveActivity();
                        }
                    }
                });

    }

    private void goEditLiveActivity() {
        if (mContext instanceof MainActivity) {
            Intent intent = new Intent(mContext, NewEditLiveActivity.class);
            String json = new Gson().toJson(SharedPreferencesManager.getPostLive());
            intent.putExtra(Content.TIMELINE, json);
            mContext.startActivity(intent);
        }

    }

    private void goEditEventActivity() {
        Intent intent = new Intent(mContext, EditEventActivity.class);
        String gson = new Gson().toJson(SharedPreferencesManager.getPostEvent());
        intent.putExtra(Content.TIMELINE, gson);
        mContext.startActivity(intent);
    }

    private void setIsFollowState(ViewHolder holder, final NodeTimeline.ItemsBean timeline) {
        final TextView tv_live_is_follow = holder.getView(R.id.tv_live_is_follow);
        final String user_id = timeline.getNode().getUser().getUser_id();

        if (type == TimelineRequestType.ATTEND) {
            tv_live_is_follow.setVisibility(View.GONE);
        } else {
            if (isOtherPageItem) {
                tv_live_is_follow.setVisibility(View.GONE);
                return;
            }
            if (currentUserId.equals(user_id)) {
                tv_live_is_follow.setVisibility(View.GONE);
                return;
            }

            Boolean isFollow = true;
            int followType = UsersHelper.getSingleTon()
                    .getFollowStateByUserId(timeline.getNode().getUser().getUser_id());
            timeline.getNode_counter().setAttend(followType);


            if (timeline.getNode_counter().getSocial() != null &&
                    timeline.getNode_counter().getSocial().equals(AttendState.SOCIAL_NOT_FOLLOW)
                    && followType == AttendState.NOT_ATTEND) {//未关注
                isFollow = false;
            }
            if (isFollow) {//未关注
                tv_live_is_follow.setVisibility(View.GONE);

            } else {//已关注
                tv_live_is_follow.setVisibility(View.VISIBLE);
                FollowButtonHelper.setTextState(tv_live_is_follow, AttendState.NOT_ATTEND, user_id, null);
            }
        }
    }


    /**
     * 设置标签
     *
     * @param holder
     * @param timeline
     */
    private void setTagData(ViewHolder holder, NodeTimeline.ItemsBean.NodeBean timeline) {
        //直播标签
        LinearLayout rl_live_tag = holder.getView(R.id.ll_live_tag);
        TextView tv_live_tag1 = holder.getView(R.id.tv_live_type_1);
        TextView tv_live_tag2 = holder.getView(R.id.tv_live_type_2);
        TextView tv_live_tag3 = holder.getView(R.id.tv_live_type_3);

        rl_live_tag.setVisibility(View.GONE);
        tv_live_tag1.setVisibility(View.GONE);
        tv_live_tag2.setVisibility(View.GONE);
        tv_live_tag3.setVisibility(View.GONE);
        NodeTimeline.ItemsBean.NodeBean.ForwardBean forward = timeline.getForward();
        List<BaseTag> tags;
        if (forward == null)
            tags = timeline.getTags();
        else
            tags = forward.getNode().getTags();

        if (null != tags) {

            switch (tags.size()) {
                case 1:
                    rl_live_tag.setVisibility(View.VISIBLE);
                    tv_live_tag1.setVisibility(View.VISIBLE);
                    tv_live_tag1.setText(tags.get(0).getTag_name());
                    break;
                case 2:
                    rl_live_tag.setVisibility(View.VISIBLE);
                    tv_live_tag1.setVisibility(View.VISIBLE);
                    tv_live_tag2.setVisibility(View.VISIBLE);
                    tv_live_tag1.setText(tags.get(0).getTag_name());
                    tv_live_tag2.setText(tags.get(1).getTag_name());
                    break;
                case 3:
                    rl_live_tag.setVisibility(View.VISIBLE);
                    tv_live_tag1.setVisibility(View.VISIBLE);
                    tv_live_tag2.setVisibility(View.VISIBLE);
                    tv_live_tag3.setVisibility(View.VISIBLE);
                    tv_live_tag1.setText(tags.get(0).getTag_name());
                    tv_live_tag2.setText(tags.get(1).getTag_name());
                    tv_live_tag3.setText(tags.get(2).getTag_name());
                    break;
                default:
                    rl_live_tag.setVisibility(View.GONE);
                    break;
            }
        }

    }

    /**
     * 头像 昵称
     *
     * @param holder
     * @param user
     */
    private void setUserData(ViewHolder holder, final NodeTimeline.ItemsBean.NodeBean.UserBean user) {
        ImageView iv_live_header = holder.getView(R.id.iv_live_header);
        TextView tv_live_nickname = holder.getView(R.id.tv_live_nickname);
        if (StringUtil.isNotEmpty(user.getNick_name()))
            tv_live_nickname.setText(user.getNick_name());


        tv_live_nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //他人主页点击自己的昵称,不跳转
                if (isOtherPageItem) {
                    return;
                }

                ActivityRouter.openOtherPageActivity(v.getContext(), user.getUser_id());
            }
        });
        String avatarUrl = Constant.PHOTO_DOMAIN_PATH + user.getAvatar();
        GlideHelper.displayHeader(mContext, iv_live_header, avatarUrl);
        //他人主页点击自己的头像,不弹窗
        if (isOtherPageItem) {
            iv_live_header.setOnClickListener(null);

        } else {

            // 点击头像，他人主页
            UserHeadNickClickHelper.getInstance().setClickListener(iv_live_header,
                    user.getNick_name(),
                    user.getUser_id(),
                    user.getUser_id(),
                    user.getAvatar());
        }


        if (currentUserId.equals(user.getUser_id())) {
            iv_live_header.setClickable(false);
        } else {
            iv_live_header.setClickable(true);
        }
    }

    /**
     * 设置位置信息
     *
     * @param holder
     * @param timeline
     */
    private void setLocation(ViewHolder holder, NodeTimeline.ItemsBean timeline) {
        TextView tv_live_location = holder.getView(R.id.tv_live_location);
        View line_live_location = holder.getView(R.id.line_live_location);
        line_live_location.setVisibility(View.GONE);
        tv_live_location.setVisibility(View.GONE);
        NodeTimeline.ItemsBean.NodeBean.LocationBean location;
        if (timeline.getNode().getForward() != null)
            location = timeline.getNode().getForward().getNode().getLocation();
        else
            location = timeline.getNode().getLocation();
        if (location != null
                && !timeline.getNode().getMinilog_type().equals(NODE_EVENT)) {


            if (StringUtil.isNotEmpty(location.getLocationName())) {
                line_live_location.setVisibility(View.VISIBLE);
                tv_live_location.setVisibility(View.VISIBLE);
                tv_live_location.setText(location.getLocationName());

                final NodeTimeline.ItemsBean.NodeBean.LocationBean.Dest dest = location.getDest();

                if (dest != null && StringUtil.isNotEmpty(dest.getNode_slug())) {
                    tv_live_location.setTextColor(mContext.getResources()
                            .getColor(R.color.txt_dark_live_location));
                    tv_live_location.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (dest.getNode_cat().equals(Content.ROUTE)) {
                                ActivityRouter.openRoadDetailActivity(mContext,
                                        dest.getNode_slug());
                            } else {
                                ActivityRouter.openDesAndRoadListActivity(mContext,
                                        dest.getNode_slug());
                            }

                        }
                    });
                } else {
                    tv_live_location.setTextColor(mContext.getResources()
                            .getColor(R.color.txt_light_remark));
                    tv_live_location.setOnClickListener(null);
                }
            }


        }
    }

    private void setTopicData(ViewHolder holder, NodeTimeline.ItemsBean.NodeBean.TopicBean topic) {
        holder.setText(R.id.tv_live_action_title, topic.getTitle());
        ImageView iv_live_action_image = holder.getView(R.id.iv_live_action_image);
        if (StringUtil.isEmpty(topic.getBanner()))
            iv_live_action_image.setImageResource(R.drawable.ic_home_repost_link_3x);
        else
            GlideHelper.displayNetForwardImage(mContext, iv_live_action_image, topic.getBanner());
    }


    /**
     * 活动召集
     *
     * @param active
     */
    private void setActiveData(ViewHolder holder, NodeTimeline.ItemsBean.NodeBean.EventBean active) {
        if (active == null) {
            return;
        }
        TextView tv_live_action_status = holder.getView(R.id.tv_live_action_status);
        ImageView iv_live_action_image = holder.getView(R.id.iv_live_action_image);
        TextView tv_live_action_title = holder.getView(R.id.tv_live_action_title);

        String actionUrl = Constant.PHOTO_DOMAIN_PATH + active.getImageUrl();
        GlideHelper.displayNetActionImage(mContext, iv_live_action_image, actionUrl);
        setActivityStatus(tv_live_action_status, active.getEvent_status());
        tv_live_action_title.setText(active.getTitle());


    }

    /**
     * 活动状态
     *
     * @param tv_live_action_status
     * @param status
     */
    private void setActivityStatus(TextView tv_live_action_status, String status) {
        if (StringUtil.isNotEmpty(status)) {
            tv_live_action_status.setVisibility(View.VISIBLE);
            if (status.equals(Constant.EVENT_RECRUTING) ||
                    status.equals(Constant.EVENT_FULL)) {
                tv_live_action_status.setBackgroundResource(R.color.color_embellish);
            } else {
                tv_live_action_status.setBackgroundResource(R.color.color_alarm);
            }

            EventHelper.getInstance().setEventStatus(tv_live_action_status, status);
        }
    }

    /**
     * 图片 贴
     *
     * @param rv_live_images
     * @param mImages
     */
    private void setImageData(RecyclerView rv_live_images, List<String> mImages) {
        rv_live_images.setVisibility(View.VISIBLE);
        GalleryAdapter mAdapter;
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

    /**
     * 设置文本
     *
     * @param holder
     * @param contentBean
     */
    private void setContentText(ViewHolder holder, final NodeTimeline.ItemsBean.NodeBean.ContentBean contentBean) {
        if (contentBean != null && StringUtil.isNotEmpty(contentBean.getText())) {
            holder.setVisible(R.id.ll_live_content, true);

            final String content = contentBean.getText();
            final TextView tv_live_content = holder.getView(R.id.tv_live_content);
            final TextView tv_live_content_more = holder.getView(R.id.tv_live_content_more);
            final TextView tv_live_content_all = holder.getView(R.id.tv_live_content_all);
            LinkUtil.setWebTextView(content, tv_live_content);
            LinkUtil.setWebTextView(content, tv_live_content_all);

            UIUtils.showView(tv_live_content, !contentBean.isExpand());
            UIUtils.showView(tv_live_content_all, contentBean.isExpand());

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
                    contentBean.setExpand(true);
                }
            });
            tv_live_content.setMovementMethod(smartTextClickMovement);
            tv_live_content_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_live_content.setVisibility(View.GONE);
                    tv_live_content_more.setVisibility(View.GONE);
                    tv_live_content_all.setVisibility(View.VISIBLE);
                    contentBean.setExpand(true);
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
            holder.setVisible(R.id.ll_live_content, false);
        }
    }

    /**
     * 文本复制
     *
     * @param content
     */
    private void liveContentCopy(final String content) {
        listDialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        listDialog.dismiss();
                        android.content.ClipboardManager clip = (android.content.ClipboardManager) mContext
                                .getSystemService(Context.CLIPBOARD_SERVICE);
                        clip.setText(content); // 复制
                        break;
                }
            }
        });
    }

    /**
     * 请求类型
     *
     * @param type
     */
    public void setType(TimelineRequestType type) {
        this.type = type;
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


    public List getDatas() {
        return mDatas;
    }

    public void onDestory() {
        MyApplication.getInstance().unregisterEventBus(this);
    }
}
