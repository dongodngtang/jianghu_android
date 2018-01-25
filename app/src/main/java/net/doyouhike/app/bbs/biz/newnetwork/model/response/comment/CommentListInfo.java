package net.doyouhike.app.bbs.biz.newnetwork.model.response.comment;

/**
 * Created by zengjiang on 16/6/17.
 */
public class CommentListInfo {

    /**
     * NodeType : minilog
     * ChildNum : 17
     * UserID : 1273478
     */

    private String NodeType;
    private int ChildNum;
    private String UserID;

    public String getNodeType() {
        return NodeType;
    }

    public void setNodeType(String NodeType) {
        this.NodeType = NodeType;
    }

    public int getChildNum() {
        return ChildNum;
    }

    public void setChildNum(int ChildNum) {
        this.ChildNum = ChildNum;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }
}
