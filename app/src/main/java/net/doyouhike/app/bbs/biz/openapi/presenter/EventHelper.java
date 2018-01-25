package net.doyouhike.app.bbs.biz.openapi.presenter;

import android.content.Context;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.service.ApiReq;
import net.doyouhike.app.bbs.biz.openapi.request.events.EventBannersGet;
import net.doyouhike.app.bbs.biz.openapi.request.events.EventCommentsListGet;
import net.doyouhike.app.bbs.biz.openapi.request.events.EventDetailGet;
import net.doyouhike.app.bbs.biz.openapi.request.events.EventMembersGet;
import net.doyouhike.app.bbs.biz.openapi.request.events.EventQuitPost;
import net.doyouhike.app.bbs.biz.openapi.request.tags.EventTagsGet;
import net.doyouhike.app.bbs.biz.openapi.request.nodes.NodesLikesCountGet;
import net.doyouhike.app.bbs.chat.helper.Constant;

/**
 * 作者：luochangdong on 16/10/13
 * 描述：
 */
public class EventHelper {
    private static EventHelper instance;

    public static EventHelper getInstance() {
        if (instance == null)
            instance = new EventHelper();
        return instance;
    }

    /**
     * 获取活动详情
     *
     * @param context
     * @param node_id
     * @param listener
     */
    public void getEventDetail(Context context, String node_id, IOnResponseListener listener) {
        EventDetailGet get = new EventDetailGet(node_id);
        get.setCancelSign(context);
        ApiReq.doGet(get, listener);
    }

    /**
     * 获取活动类型
     *
     * @param context
     * @param listener
     */
    public void getEventTypes(Context context, IOnResponseListener listener) {
        EventTagsGet get = new EventTagsGet();
        get.setCancelSign(context);
        ApiReq.doGet(get, listener);
    }

    /**
     * 获取活动参加成员
     *
     * @param context
     * @param node_id
     * @param b
     * @param listener
     */
    public void getEventMenbers(Context context,
                                String node_id, boolean b, IOnResponseListener listener) {
        EventMembersGet membersGet = new EventMembersGet(node_id, false);
        membersGet.setCancelSign(context);
        ApiReq.doGet(membersGet, listener);
    }

    /**
     * 获取活动的点赞数
     *
     * @param context
     * @param node_id
     * @param listener
     */
    public void getEventLikeCount(Context context, String node_id, IOnResponseListener listener) {
        NodesLikesCountGet get = new NodesLikesCountGet(node_id);
        get.setCancelSign(context);
        ApiReq.doGet(get, listener);
    }

    public void getEVentComments(Context context, String node_id, IOnResponseListener listener) {
        EventCommentsListGet get = new EventCommentsListGet(node_id);
        get.setCancelSign(context);
        ApiReq.doGet(get, listener);
    }

    public void postEventQuit(Context context, String node_id, IOnResponseListener listener) {
        EventQuitPost post = new EventQuitPost(node_id);
        post.setCancelSign(context);
        ApiReq.doPost(post, listener);
    }

    /**
     * 活动页的广告
     *
     * @param context
     * @param listener
     */
    public void getEventBanner(Context context, IOnResponseListener listener) {
        EventBannersGet get = new EventBannersGet();
        get.setCancelSign(context);
        ApiReq.doGet(get, listener);
    }

    /**
     * 活动状态设置
     *
     * @param tv_live_action_status
     * @param status
     */
    public void setEventStatus(TextView tv_live_action_status, String status) {
        if (status != null)
            if (status.equals(Constant.EVENT_RECRUTING)) {
                tv_live_action_status.setText(R.string.event_recruiting);
            } else if (status.equals(Constant.EVENT_FULL)) {
                tv_live_action_status.setText(R.string.event_full);
            } else if (status.equals(Constant.EVENT_EXPIRATION)) {
                tv_live_action_status.setText(R.string.event_expiration);
            } else if (status.equals(Constant.EVENT_FINISH)) {
                tv_live_action_status.setText(R.string.event_finish);
            } else if (status.equals(Constant.EVENT_CANCEL)) {
                tv_live_action_status.setText(R.string.event_cancel);
            } else if (status.equals(Constant.EVENT_CLOSE))
                tv_live_action_status.setText(R.string.event_close);
    }
}
