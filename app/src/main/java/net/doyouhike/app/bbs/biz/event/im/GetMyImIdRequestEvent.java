package net.doyouhike.app.bbs.biz.event.im;

/**
 * 获取自己的环信ID请求
 * Created by zengjiang on 16/8/8.
 */

public class GetMyImIdRequestEvent {
    public String uuid;

    public GetMyImIdRequestEvent(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
