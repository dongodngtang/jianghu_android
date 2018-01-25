package net.doyouhike.app.bbs.biz.event;


import net.doyouhike.app.bbs.biz.newnetwork.model.bean.BaseTag;

/**
 * 功能：
 *
 * @author：曾江 日期：16-3-3.
 */
public class CheckoutEvent {
    BaseTag tag;

    public CheckoutEvent(BaseTag tag) {
        this.tag = tag;
    }

    public BaseTag getTag() {
        return tag;
    }

    public void setTag(BaseTag tag) {
        this.tag = tag;
    }
}
