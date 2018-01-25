package net.doyouhike.app.bbs.biz.event.im;

/**
 * 发送悄悄话
 * Created by zengjiang on 16/8/15.
 */

public class SendPrivateMsgEvent {
    /**
     * 接受者ID
     */
    private String receiverId;

    public SendPrivateMsgEvent(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }
}
