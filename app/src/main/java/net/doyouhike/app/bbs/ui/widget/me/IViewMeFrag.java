package net.doyouhike.app.bbs.ui.widget.me;

import net.doyouhike.app.bbs.biz.newnetwork.model.response.Timeline;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.NodeTimeline;
import net.doyouhike.app.bbs.ui.activity.me.IViewUser;

import java.util.List;

/**
 * 功能：
 * 作者：曾江
 * 日期：16-1-8.
 */
public interface IViewMeFrag extends IViewUser {
    /**
     * @param items      收藏列表
     * @param isRefreash 是否刷新，true 为刷新，false为加载更多
     */
    void updateCollects(List<NodeTimeline.ItemsBean> items, boolean isRefreash);


}
