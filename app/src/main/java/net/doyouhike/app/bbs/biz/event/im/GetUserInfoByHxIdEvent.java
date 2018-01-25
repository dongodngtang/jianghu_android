package net.doyouhike.app.bbs.biz.event.im;

/**
 * 作者：luochangdong on 16/8/16
 * 描述：
 */
public class GetUserInfoByHxIdEvent {

    /**
     * 环信 ID
     */
    String HxId;

    public GetUserInfoByHxIdEvent(String hxId) {
        HxId = hxId;
    }

    public String getHxId() {
        return HxId;
    }

    public void setHxId(String hxId) {
        HxId = hxId;
    }
}
