package net.doyouhike.app.bbs.biz.event.im;

/**
 * 获取用户环信ID
 * Created by zengjiang on 16/8/5.
 */

public class GetOtherHxIdEvent {
    String userId;

    public GetOtherHxIdEvent(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
