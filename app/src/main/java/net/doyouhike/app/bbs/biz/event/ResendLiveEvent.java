package net.doyouhike.app.bbs.biz.event;

import net.doyouhike.app.bbs.biz.newnetwork.model.bean.SendingTimeline;

/**
 * 功能： 重新发送直播
 *
 * @author：曾江 日期：16-3-10.
 */
public class ResendLiveEvent {
    SendingTimeline timeline;

    public ResendLiveEvent(SendingTimeline timeline) {
        this.timeline = timeline;
    }

    public SendingTimeline getTimeline() {
        return timeline;
    }

    public void setTimeline(SendingTimeline timeline) {
        this.timeline = timeline;
    }
}
