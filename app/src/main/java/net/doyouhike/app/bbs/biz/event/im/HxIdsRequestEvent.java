package net.doyouhike.app.bbs.biz.event.im;

import java.util.List;

/**
 * 批量获取im id 事件
 * Created by zengjiang on 16/8/1.
 */

public class HxIdsRequestEvent {


    List<String> uuIds;

    public HxIdsRequestEvent(List<String> uuIds) {
        this.uuIds = uuIds;
    }

    public List<String> getUuIds() {
        return uuIds;
    }

    public void setUuIds(List<String> uuIds) {
        this.uuIds = uuIds;
    }
}
