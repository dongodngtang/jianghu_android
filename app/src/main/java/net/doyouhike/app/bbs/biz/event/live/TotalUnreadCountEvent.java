package net.doyouhike.app.bbs.biz.event.live;

/**
 * 未读消息总数,更新
 * Created by zengjiang on 16/6/16.
 */
public class TotalUnreadCountEvent {

    private int totalCount;//未设置屏蔽消息的,未读消息数量
    private boolean hasUnreadMsg;//是否有未读消息,用于显示小红点

    public TotalUnreadCountEvent(int totalCount, boolean hasUnreadMsg) {
        this.totalCount = totalCount;
        this.hasUnreadMsg = hasUnreadMsg;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isHasUnreadMsg() {
        return hasUnreadMsg;
    }

    public void setHasUnreadMsg(boolean hasUnreadMsg) {
        this.hasUnreadMsg = hasUnreadMsg;
    }
}
