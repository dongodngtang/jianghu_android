package net.doyouhike.app.bbs.ui.home;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.model.request.get.DoLikeTopicGetParam;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.Timeline;
import net.doyouhike.app.bbs.biz.newnetwork.service.ApiReq;
import net.doyouhike.app.bbs.ui.activity.action.ActionDetailActivity;
import net.doyouhike.app.bbs.ui.activity.login.LoginActivity;
import net.doyouhike.app.bbs.ui.home.topic.TimelineRequestType;
import net.doyouhike.app.bbs.ui.widget.common.webview.BaseWebViewActivity;
import net.doyouhike.app.bbs.util.ActivityRouter;
import net.doyouhike.app.bbs.util.StatisticsEventUtil;
import net.doyouhike.app.bbs.util.StrUtils;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.UserInfoUtil;

/**
 * Filework:
 * Author: luochangdong
 * Date:16-1-8
 */
public class PresenterLiveAdapterImp implements IPresenterLiveAdapter {
    private Context context;
    private TimelineRequestType isHot;
    public static final int UPLOAD_OK = 1;
    public static final int UPLOADING = 2;
    public static final int UPLOAD_FAIL = 0;
    private ILiveAdapterView iLiveAdapterView;

    public PresenterLiveAdapterImp(Context context, ILiveAdapterView iLiveAdapterView) {
        this.context = context;
        this.iLiveAdapterView = iLiveAdapterView;
    }

    @Override
    public void isHot(TimelineRequestType isHot) {
        this.isHot = isHot;
    }

    @Override
    public void clickLike(final ImageView likeImage, final TextView likeNum, final Timeline timeline) {
        if (isLogin() || timeline == null) return;
        likeImage.setClickable(false);

        if (getIsLike(timeline) == 0) {
            likeImage.setImageResource(R.drawable.icon_card_liked_default_3x);
            setIsLike(timeline, 1);
            DoLikeTopicGetParam doLikeTopicGetParam = new DoLikeTopicGetParam();
            doLikeTopicGetParam.setToken(UserInfoUtil.getInstance().getToken());
            doLikeTopicGetParam.setTopicId(getNodeId(timeline));

            ApiReq.doGet(doLikeTopicGetParam, new IOnResponseListener() {
                @Override
                public void onSuccess(Response response) {
                    if (response.getCode() == 0)
                        doLikeAdd(likeImage, likeNum, timeline);
                    likeImage.setClickable(true);
                }

                @Override
                public void onError(Response response) {
                    if (response.getCode() == 0)
                        setIsLike(timeline, 0);
                    likeImage.setClickable(true);

                }
            });
        } else if (getIsLike(timeline) == 1) {

//            likeImage.setImageResource(R.drawable.icon_card_like_default_3x);
//            setIsLike(timeline, 0);
//            UnLikeTopicReq unLikeTopicGetParam = new UnLikeTopicReq();
//            unLikeTopicGetParam.setToken(UserInfoUtil.getInstance().getToken());
//            unLikeTopicGetParam.setTopicId(getNodeId(timeline));
//            ApiReq.doPost(unLikeTopicGetParam, new IOnResponseListener() {
//                @Override
//                public void onSuccess(Response response) {
//                    if (response.getCode() == 0)
//                        doLikeLess(likeImage, likeNum, timeline);
//                    likeImage.setClickable(true);
//
//                }
//
//                @Override
//                public void onError(Response response) {
//                    if (response.getCode() == 0)
//                        setIsLike(timeline, 1);
//                    likeImage.setClickable(true);
//
//                }
//            });

        }


    }

    public String getLikeNum(Timeline timeline) {
        return timeline.getForward_node_detail() == null ?
                timeline.getLike_num() :
                timeline.getForward_node_detail().getLike_num();
    }

    public void setLikeNum(Timeline timeline, int num) {
        if (timeline.getForward_node_detail() == null)
            timeline.setLike_num(num + "");
        else
            timeline.getForward_node_detail().setLike_num(num + "");
    }

    public int getIsLike(Timeline timeline) {
        return timeline.getForward_node_detail() == null ?
                timeline.getIs_like() :
                timeline.getForward_node_detail().getIs_like();
    }

    public void setIsLike(Timeline timeline, int tag) {
        if (timeline.getForward_node_detail() == null)
            timeline.setIs_like(tag);
        else
            timeline.getForward_node_detail().setIs_like(tag);
    }

    public String getPostNum(Timeline timeline) {
        return timeline.getForward_node_detail() == null ?
                timeline.getPost_num() :
                timeline.getForward_node_detail().getPost_num();
    }

    public String getNodeId(Timeline timeline) {
        return timeline.getForward_node_detail() == null ?
                timeline.getNode_id() :
                timeline.getForward_node_detail().getNode_id();
    }


    private void doLikeLess(ImageView likeImage, TextView likeNum, Timeline timeline) {
        likeNum.setTextColor(context.getResources().getColor(R.color.txt_live_tip));
        if (StringUtil.isNotEmpty(getLikeNum(timeline)) && !("0").equals(getLikeNum(timeline))) {
            int num = Integer.parseInt(getLikeNum(timeline)) - 1;
            setLikeNum(timeline, num, likeNum);
        } else {
            setLikeNum(timeline, 0, likeNum);
        }

    }

    private void doLikeAdd(ImageView likeImage, TextView likeNum, Timeline timeline) {
        StatisticsEventUtil.Like(context);
        likeNum.setTextColor(context.getResources().getColor(R.color.orange_word));
        if (StringUtil.isNotEmpty(getLikeNum(timeline)) && !("0").equals(getLikeNum(timeline))) {
            int num = Integer.parseInt(getLikeNum(timeline)) + 1;
            setLikeNum(timeline, num, likeNum);
        } else {
            setLikeNum(timeline, 1, likeNum);
        }

    }

    public void setLikeNum(Timeline timeline, int num, TextView tv) {

        setLikeNum(timeline, num);

        if (num == 0)
            tv.setText("赞");
        else
            tv.setText(num + "");

    }


    private boolean isLogin() {
        if (UserInfoUtil.getInstance().getToken().isEmpty()) {
            context.startActivity(new Intent(context,
                    LoginActivity.class));
            String toastMsg = StrUtils.getResourcesStr(context,
                    R.string.please_to_login);
            StringUtil.showSnack(context, toastMsg);
            return true;
        }
        return false;
    }

    @Override
    public void clickComment(final Timeline timeline) {

        if (isLogin() || timeline == null) return;

        if (Integer.parseInt(getPostNum(timeline)) > 0) {
            if (timeline.getMinilog_type() == NewLiveAdapter.LIVE_TYPE_ACTION ||
                    (timeline.getForward_node_detail() != null &&
                            timeline.getForward_node_detail().getMinilog_type() == NewLiveAdapter.LIVE_TYPE_ACTION)) {
                //活动
                toActionDetailActivity(getNodeId(timeline));
            } else {
                //跳转评论页
                ActivityRouter.openLiveCommentActivity(context, getNodeId(timeline), false);
            }

        } else {
            //跳转编辑评论页
            ActivityRouter.openAddCommentActivity(context, getNodeId(timeline));
        }

    }


    /**
     * 跳转到详情界面
     * 活动 帖子
     *
     * @param timeline
     */
    protected void toLiveDetailsActivity(Timeline timeline) {
        if (timeline.getMinilog_type() == NewLiveAdapter.LIVE_TYPE_ACTION ||
                (timeline.getForward_node_detail() != null &&
                        timeline.getForward_node_detail().getMinilog_type() == NewLiveAdapter.LIVE_TYPE_ACTION)) {
            //活动
            toActionDetailActivity(getNodeId(timeline));
        } else if (timeline.getMinilog_type() == NewLiveAdapter.LIVE_TYPE_WEB_POST) {
            //帖子
            Timeline.TopicEntity topicEntity = timeline.getTopic();
            if (topicEntity != null && StringUtil.isNotEmpty(topicEntity.getUrl()))
                toBaseWebViewActivity(topicEntity.getUrl());
        } else if (timeline.getMinilog_type() == NewLiveAdapter.LIVE_TYPE_FORWARDING) {

            //转发
            Timeline.ForwardNodeDetailEntity forward = timeline.getForward_node_detail();
            if (forward != null) {
                int type = forward.getMinilog_type();
                if (type == NewLiveAdapter.LIVE_TYPE_ACTION) {
                    //活动
                    toActionDetailActivity(forward.getNode_id());
                } else if (type == NewLiveAdapter.LIVE_TYPE_WEB_POST) {
                    if (forward.getTopic() != null &&
                            StringUtil.isNotEmpty(forward.getTopic().getUrl())) {

                        toBaseWebViewActivity(forward.getTopic().getUrl());
                    }

                }
            }

        }

    }

    private void toBaseWebViewActivity(String url) {

        Intent intent = new Intent(context, BaseWebViewActivity.class);
        intent.putExtra(BaseWebViewActivity.I_URL, url);
        context.startActivity(intent);
    }


    private void toActionDetailActivity(String nodeId) {
        if (StringUtil.isNotEmpty(nodeId)) {
            Intent intent = new Intent(context,
                    ActionDetailActivity.class);
            intent.putExtra("nodeId", nodeId);
            context.startActivity(intent);
        }
    }

    @Override
    public void clickMore(Timeline timeline) {
//        if (timeline != null) {
//            LoginUser user = UserInfoUtil.getInstance().getCurrentUser();
//            if (user != null && user.getUser().getUser_id() != null) {
//                boolean isMyself = user.getUser().getUser_id().equals(
//                        timeline.getUser_info().getUser_id());
//                LiveMoreDialog moreDialog = new LiveMoreDialog(
//                        context, timeline, isMyself);
//                moreDialog.show();
//            } else {
//                LiveMoreDialog moreDialog = new LiveMoreDialog(
//                        context, timeline);
//                moreDialog.show();
//            }
//        }
    }

    @Override
    public void clickAuthor(String userId) {

        ActivityRouter.openOtherPageActivity(context, userId);


    }


    @Override
    public void lookDetail(Timeline timeline) {
        if (timeline != null) {
            if (timeline.getMinilog_type() != NewLiveAdapter.LIVE_TYPE_BE_DELETED) {

                if (UserInfoUtil.getInstance().getCurrentUser() != null || isHot == TimelineRequestType.HOT) {
                    toLiveDetailsActivity(timeline);
                } else {
                    context.startActivity(new Intent(context,
                            LoginActivity.class));
                    String toast = StrUtils.getResourcesStr(
                            context, R.string.please_to_login);
                    StringUtil.showSnack(context, toast);
                }
            } else {
                String toastMsg = StrUtils.getResourcesStr(context,
                        R.string.content_has_been_deleted);
                StringUtil.showSnack(context, toastMsg);
            }
        }
    }


}
