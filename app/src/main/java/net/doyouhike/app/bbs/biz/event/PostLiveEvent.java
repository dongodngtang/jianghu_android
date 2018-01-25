package net.doyouhike.app.bbs.biz.event;


import net.doyouhike.app.bbs.biz.newnetwork.model.bean.SendingTimeline;

/**
 * 功能：
 *
 * @author：曾江 日期：16-3-4.
 */
public class PostLiveEvent  {

    /**
     * reserved data
     */
    private SendingTimeline data;

    /**
     * this code distinguish between different events
     */
    private int eventCode = -1;

    public PostLiveEvent(int eventCode) {
        this(eventCode, null);
    }

    public PostLiveEvent(int eventCode, SendingTimeline data) {
        this.eventCode = eventCode;
        this.data = data;
    }

    /**
     * get event code
     *
     * @return
     */
    public int getEventCode() {
        return this.eventCode;
    }

    /**
     * get event reserved data
     *
     * @return
     */
    public SendingTimeline getData() {
        return this.data;
    }
}
