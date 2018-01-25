package net.doyouhike.app.bbs.ui.activity.me;

import android.content.Context;

import net.doyouhike.app.bbs.biz.entity.CurrentUserDetails;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.Timeline;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventsListResp;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.NodeTimeline;

import java.util.List;

/**
 * 功能：
 *
 * @author：曾江 日期：16-3-22.
 */
public interface IViewUser {

    int ERR_LIVE = 0;
    int ERR_EVENT = 1;
    int ERR_COLLECT = 2;
    int ERR_PROFILE = 3;

    /**
     * @param events     活动内容
     * @param isRefreash 是否刷新，true 为刷新，false为加载更多
     */
    void updateUserEvent(List<EventsListResp.ItemsBean> events, boolean isRefreash);

    /**
     * @param items      直播列表
     * @param isRefreash 是否刷新，true 为刷新，false为加载更多
     */
    void updateTmLine(List<NodeTimeline.ItemsBean> items, boolean isRefreash);

    /**
     * @param profile 用户信息
     */
    void updateProfile(CurrentUserDetails profile);

    /**
     * 获取列表错误回调
     *
     * @param symbol     错误回调标志，用以区分接口
     * @param errCode    错误编码
     * @param msg        错误信息
     * @param isRefreash 是否刷新，true 为刷新，false为加载更多
     */
    void getItemsErr(int symbol, int errCode, String msg, boolean isRefreash);

    /**
     * @param symbol 错误回调标志，用以区分接口
     * @param msg    错误信息
     */
    void onRequestErr(int symbol, String msg);

    Context getContext();
}
