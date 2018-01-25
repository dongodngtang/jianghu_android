package net.doyouhike.app.bbs.biz.event.open;

/**
 * 删除评论后,刷新消息评论数量
 * Created by zengjiang on 16/5/26.
 */
public class DeleteCommentEvent {
    String nodeId;

    public DeleteCommentEvent() {
    }
    public DeleteCommentEvent(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
}
