package net.doyouhike.app.bbs.biz.newnetwork.model.response.message;

import java.util.List;

/**
 * 作者：luochangdong on 16/8/31
 * 描述：
 */
public class GetLikeMeListResp {

    /**
     * msgBean : [{"Title":"","NodeID":"6007171","NodeType":"minilog","UserName":"aas1900000","NickName":"z1947","Face":"http://static.test.doyouhike.net/files/faces/1/a/1a0b6ac4d.jpg","uuid":"265d5e7549c2bbfb26da56d00996c9a6","UserID":1508772,"IsRead":"2","Created":"1472546957","MinilogType":"txt","refContent":"就送水电话河山大好😓"}]
     * numNoRead : 0
     */

    private String numNoRead;
    /**
     * Title :
     * NodeID : 6007171
     * NodeType : minilog
     * UserName : aas1900000
     * NickName : z1947
     * Face : http://static.test.doyouhike.net/files/faces/1/a/1a0b6ac4d.jpg
     * uuid : 265d5e7549c2bbfb26da56d00996c9a6
     * UserID : 1508772
     * IsRead : 2
     * Created : 1472546957
     * MinilogType : txt
     * refContent : 就送水电话河山大好😓
     */

    private List<UsersBean> users;

    public String getNumNoRead() {
        return numNoRead;
    }

    public void setNumNoRead(String numNoRead) {
        this.numNoRead = numNoRead;
    }

    public List<UsersBean> getUsers() {
        return users;
    }

    public void setUsers(List<UsersBean> users) {
        this.users = users;
    }

    public static class UsersBean {
        private String Title;
        private String NodeID;
        private String NodeType;
        private String UserName;
        private String NickName;
        private String Face;
        private String uuid;
        private String UserID;
        private int IsRead;
        private long Created;
        private String MinilogType;
        private String refContent;
        private String firstPhoto;

        public String getFirstPhoto() {
            return firstPhoto;
        }

        public void setFirstPhoto(String firstPhoto) {
            this.firstPhoto = firstPhoto;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getNodeID() {
            return NodeID;
        }

        public void setNodeID(String NodeID) {
            this.NodeID = NodeID;
        }

        public String getNodeType() {
            return NodeType;
        }

        public void setNodeType(String NodeType) {
            this.NodeType = NodeType;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public String getNickName() {
            return NickName;
        }

        public void setNickName(String NickName) {
            this.NickName = NickName;
        }

        public String getFace() {
            return Face;
        }

        public void setFace(String Face) {
            this.Face = Face;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getUserID() {
            return UserID;
        }

        public void setUserID(String UserID) {
            this.UserID = UserID;
        }

        public int getIsRead() {
            return IsRead;
        }

        public void setIsRead(int IsRead) {
            this.IsRead = IsRead;
        }

        public long getCreated() {
            return Created;
        }

        public void setCreated(long Created) {
            this.Created = Created;
        }

        public String getMinilogType() {
            return MinilogType;
        }

        public void setMinilogType(String MinilogType) {
            this.MinilogType = MinilogType;
        }

        public String getRefContent() {
            return refContent;
        }

        public void setRefContent(String refContent) {
            this.refContent = refContent;
        }
    }
}
