package net.doyouhike.app.bbs.biz.openapi.presenter;

import android.content.Context;

import com.yolanda.nohttp.rest.CacheMode;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.service.ApiReq;
import net.doyouhike.app.bbs.biz.openapi.request.nodes.NodeAccusationListGet;
import net.doyouhike.app.bbs.biz.openapi.request.nodes.NodeAccusationPost;
import net.doyouhike.app.bbs.biz.openapi.request.nodes.NodeCommentDelete;
import net.doyouhike.app.bbs.biz.openapi.request.nodes.NodeDelete;
import net.doyouhike.app.bbs.biz.openapi.request.events.EventCancelPost;
import net.doyouhike.app.bbs.biz.openapi.request.nodes.NodeForwardPost;
import net.doyouhike.app.bbs.biz.openapi.request.nodes.NodeShareUrlGet;
import net.doyouhike.app.bbs.biz.openapi.request.routes.BaseMfDestGet;
import net.doyouhike.app.bbs.biz.openapi.request.tags.MinilogTagGet;
import net.doyouhike.app.bbs.biz.openapi.request.nodes.timelines.TimelineFollowsGet;
import net.doyouhike.app.bbs.biz.openapi.request.nodes.timelines.TimelineHotsGet;
import net.doyouhike.app.bbs.biz.openapi.request.nodes.timelines.TimelineTagsGet;
import net.doyouhike.app.bbs.biz.openapi.request.tags.BaseMfTypesGet;
import net.doyouhike.app.bbs.biz.openapi.request.tags.UserSubscriptionsGet;

/**
 * 作者：luochangdong on 16/9/28
 * 描述：
 */
public class NodesHelper {
    private static NodesHelper instance;

    public static NodesHelper getInstance() {
        if (instance == null)
            instance = new NodesHelper();
        return instance;
    }

    /**
     * 删除评论接口
     * @param context
     * @param node_id
     * @param comment_id
     * @param listener
     */
    public void deleteNodeComments(Context context, String node_id, String comment_id, IOnResponseListener listener) {
        NodeCommentDelete delete = new NodeCommentDelete(node_id, comment_id);
        delete.setCancelSign(context);
        ApiReq.doPost(delete, listener);

    }

    /**
     * 取消约伴活动
     *
     * @param context
     * @param node_id
     * @param listener
     */
    public void postNodeEventCancel(Context context, String node_id, IOnResponseListener listener) {
        EventCancelPost post = new EventCancelPost(node_id);
        post.setCancelSign(context);
        ApiReq.doPost(post, listener);
    }

    /**
     * 转发Node
     *
     * @param context
     * @param node_id
     * @param content
     * @param listener
     */
    public void postNodeForward(Context context, String node_id,
                                String content, IOnResponseListener listener) {
        NodeForwardPost post = new NodeForwardPost(node_id);
        post.setCancelSign(context);
        post.setContent(content);
        ApiReq.doPost(post, listener);
    }

    /**
     * 举报
     *
     * @param context
     * @param node_id
     * @param accusation_type
     * @param listener
     */
    public void postNodeAccusation(Context context, String node_id,
                                   int accusation_type, IOnResponseListener listener) {
        NodeAccusationPost post = new NodeAccusationPost(node_id);
        post.setCancelSign(context);
        post.setAccusation_type(accusation_type);
        ApiReq.doPost(post, listener);
    }

    /**
     * 获取举报类型
     *
     * @param context
     * @param listener
     */
    public void getNodeAccusationList(Context context, IOnResponseListener listener) {
        NodeAccusationListGet get = new NodeAccusationListGet();
        get.setCancelSign(context);
        ApiReq.doGet(get, listener);
    }

    /**
     * 删除一个node
     *
     * @param context
     * @param node_id
     * @param listener
     */
    public void deleteNode(Context context, String node_id, IOnResponseListener listener) {
        NodeDelete delete = new NodeDelete(node_id);
        delete.setCancelSign(context);
        ApiReq.doPost(delete, listener);
    }


    /**
     * 获取发布时用于选择的标签
     *
     * @param context
     * @param listener
     */
    public void getMinilogTag(Context context, IOnResponseListener listener) {
        MinilogTagGet get = new MinilogTagGet();
        get.setCancelSign(context);
        ApiReq.doGet(get, listener);
    }


    /**
     * 目的地搜索
     *
     * @param context
     * @param key_word
     * @param listener
     */
    public void searchBaseMfDest(Context context, String key_word, IOnResponseListener listener) {
        BaseMfDestGet get = new BaseMfDestGet(key_word);
        get.setCancelSign(context);
        ApiReq.doGet(get, listener);
    }


    /**
     * 获得系统标签的接口
     *
     * @param context
     * @param listener
     */
    public void getBaseMfTypes(Context context, IOnResponseListener listener) {
        BaseMfTypesGet get = new BaseMfTypesGet();
        get.setCancelSign(context);
        ApiReq.doGet(get, listener);
    }

    /**
     * 订阅标签
     *
     * @param context
     * @param page_index
     * @param page_size
     * @param last_id
     * @param tag_id
     * @param listener
     */
    public void getTimelineTags(Context context, int page_index, int page_size, String last_id,
                                String tag_id, IOnResponseListener listener) {
        TimelineTagsGet get = new TimelineTagsGet(tag_id);
        get.setCancelSign(context);
        get.setPage_index(page_index);
        get.setPage_size(page_size);
        get.setLast_id(last_id);

        ApiReq.doGet(get, listener);
    }

    /**
     * 获取当前用户订阅的标签
     *
     * @param context
     * @param user_id
     * @param listener
     */
    public void getUserSubscriptions(Context context, String user_id, IOnResponseListener listener) {
        UserSubscriptionsGet get = new UserSubscriptionsGet(user_id);
        get.setCancelSign(context);
        ApiReq.doGet(get, listener);
    }


    /**
     * 获取关注
     *
     * @param context
     * @param page_index
     * @param page_size
     * @param listener
     */
    public void getTimelineFollows(Context context, int page_index, int page_size, IOnResponseListener listener) {
        TimelineFollowsGet get = new TimelineFollowsGet();
        get.setCancelSign(context);
        get.setPage_index(page_index);
        get.setPage_size(page_size);
        ApiReq.doGet(get, listener);
    }


    /**
     * 获取热门
     *
     * @param context
     * @param page_index
     * @param page_size
     * @param listener
     */
    public void getTimelineHots(Context context, int page_index, int page_size, IOnResponseListener listener) {
        TimelineHotsGet get = new TimelineHotsGet();
        get.setCancelSign(context);
        get.setPage_index(page_index);
        get.setPage_size(page_size);
        ApiReq.doGet(get, listener);
    }

    /**
     * 获取node分享地址
     *
     * @param context
     * @param node_id
     * @param listener
     */
    public void nodeShareUrl(Context context, String node_id, IOnResponseListener listener) {
        NodeShareUrlGet get = new NodeShareUrlGet(node_id);
        get.setCacheMode(CacheMode.NONE_CACHE_REQUEST_NETWORK);
        get.setCancelSign(context);
        ApiReq.doGet(get, listener);
    }

    /**
     * 标签
     *
     * @param context
     * @param tag_id
     * @param listener
     */
    public void timelineTags(Context context, String tag_id, IOnResponseListener listener) {
        TimelineTagsGet get = new TimelineTagsGet(tag_id);
        get.setCancelSign(context);
        ApiReq.doGet(get, listener);
    }

    /**
     * 关注
     *
     * @param context
     * @param listener
     */
    public void timelineFollows(Context context, IOnResponseListener listener) {
        TimelineFollowsGet get = new TimelineFollowsGet();
        get.setCancelSign(context);
        ApiReq.doGet(get, listener);
    }


    /**
     * 热门
     *
     * @param context
     * @param listener
     */
    public void timelineHots(Context context, IOnResponseListener listener) {
        TimelineHotsGet get = new TimelineHotsGet();
        get.setCancelSign(context);
        ApiReq.doGet(get, listener);
    }


}
