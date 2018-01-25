package net.doyouhike.app.bbs.biz.event.im;

import net.doyouhike.app.bbs.biz.entity.im.GroupMsgDetail;

/**
 * 群发消息请求事件
 * Created by zengjiang on 16/8/1.
 */

public class SendGroupMsgEvent {

    GroupMsgDetail msgDetail;

    public SendGroupMsgEvent(GroupMsgDetail msgDetail) {
        this.msgDetail = msgDetail;
    }

    public GroupMsgDetail getMsgDetail() {
        return msgDetail;
    }

    public void setMsgDetail(GroupMsgDetail msgDetail) {
        this.msgDetail = msgDetail;
    }
}
