package net.doyouhike.app.bbs.biz.newnetwork.model.response.message;

import net.doyouhike.app.bbs.biz.entity.common.RichTextContent;
import net.doyouhike.app.bbs.util.StringUtil;

import java.util.List;

/**
 * 作者:luochangdong on 16/6/14 15:13
 * 描述:
 */
public class GetCommentMeListResp {



    /**
     * msgBean : [{"IsRead":"2","CommentID":"3441903","Created":"1465722589","reply_to":"1508715","reply_to_username":"凝固","reply_to_nickname":"凝固","Content":"好多好多好多话","RefrenceID":"3441844","Title":"北京奥林匹克公园跑步4天跑步","NodeID":"6004406","NodeType":"event","UserName":"wow","NickName":"Wowabc","Face":"http://dev.static.doyouhike.com/files/faces/8/f/8fe3e7d99.jpg","UserID":"10","MinilogType":"activity","refContent":"8曲回家里"},{"IsRead":"2","CommentID":"3441900","Created":"1465716305","reply_to":"13","reply_to_username":"root","reply_to_nickname":"root1155","Content":"[{\"type\":\"text\",\"content\":\"ttttttttt\"}]","RefrenceID":"3441859","Title":"","NodeID":"6003680","NodeType":"minilog","UserName":"wow","NickName":"Wowabc","Face":"http://dev.static.doyouhike.com/files/faces/8/f/8fe3e7d99.jpg","UserID":"10","MinilogType":"txt-photo","refContent":"[{\"type\":\"text\",\"content\":\"5曲岁了\"}]","firstPhoto":"http://dev.static.doyouhike.com/files/2016/03/16/2/21c770f221b25524f96685c5eff8e19f_s.jpg"},{"IsRead":"2","CommentID":"3441895","Created":"1465711641","reply_to":"1508715","reply_to_username":"凝固","reply_to_nickname":"凝固","Content":"咋滴啦，还凝固","RefrenceID":"3441844","Title":"北京奥林匹克公园跑步4天跑步","NodeID":"6004406","NodeType":"event","UserName":"wow","NickName":"Wowabc","Face":"http://dev.static.doyouhike.com/files/faces/8/f/8fe3e7d99.jpg","UserID":"10","MinilogType":"activity","refContent":"8曲回家里"},{"IsRead":"2","CommentID":"3441881","Created":"1465699204","reply_to":"13","reply_to_username":"root","reply_to_nickname":"root1155","Content":"[{\"type\":\"text\",\"content\":\"ttttttttt\"}]","RefrenceID":"3441859","Title":"","NodeID":"6003680","NodeType":"minilog","UserName":"wow","NickName":"Wowabc","Face":"http://dev.static.doyouhike.com/files/faces/8/f/8fe3e7d99.jpg","UserID":"10","MinilogType":"txt-photo","refContent":"[{\"type\":\"text\",\"content\":\"5曲岁了\"}]","firstPhoto":"http://dev.static.doyouhike.com/files/2016/03/16/2/21c770f221b25524f96685c5eff8e19f_s.jpg"},{"IsRead":"2","CommentID":"3441878","Created":"1465378390","reply_to":"13","reply_to_username":"root","reply_to_nickname":"root1155","Content":"ttttttttt","RefrenceID":"3441859","Title":"","NodeID":"6003680","NodeType":"minilog","UserName":"wow","NickName":"Wowabc","Face":"http://dev.static.doyouhike.com/files/faces/8/f/8fe3e7d99.jpg","UserID":"10","MinilogType":"txt-photo","refContent":"[{\"type\":\"text\",\"content\":\"5曲岁了\"}]","firstPhoto":"http://dev.static.doyouhike.com/files/2016/03/16/2/21c770f221b25524f96685c5eff8e19f_s.jpg"},{"IsRead":"2","CommentID":"3441877","Created":"1465377375","reply_to":"1508715","reply_to_username":"凝固","reply_to_nickname":"凝固","Content":"[{\"type\":\"text\",\"content\":\"顺带把不咸不淡好的表现手段卑鄙\"}]","RefrenceID":"3441859","Title":"","NodeID":"6003680","NodeType":"minilog","UserName":"wow","NickName":"Wowabc","Face":"http://dev.static.doyouhike.com/files/faces/8/f/8fe3e7d99.jpg","UserID":"10","MinilogType":"txt-photo","refContent":"[{\"type\":\"text\",\"content\":\"5曲岁了\"}]","firstPhoto":"http://dev.static.doyouhike.com/files/2016/03/16/2/21c770f221b25524f96685c5eff8e19f_s.jpg"},{"IsRead":"2","CommentID":"3441876","Created":"1465377138","reply_to":"1508715","reply_to_username":"凝固","reply_to_nickname":"凝固","Content":"[{\"type\":\"text\",\"content\":\"好多好多话好多好多话\"}]","RefrenceID":"3441859","Title":"","NodeID":"6003680","NodeType":"minilog","UserName":"wow","NickName":"Wowabc","Face":"http://dev.static.doyouhike.com/files/faces/8/f/8fe3e7d99.jpg","UserID":"10","MinilogType":"txt-photo","refContent":"[{\"type\":\"text\",\"content\":\"5曲岁了\"}]","firstPhoto":"http://dev.static.doyouhike.com/files/2016/03/16/2/21c770f221b25524f96685c5eff8e19f_s.jpg"},{"IsRead":"2","CommentID":"3441873","Created":"1465375917","reply_to":"0","Content":"回复@凝固: Apple very day.","RefrenceID":"3441843","Title":"北京奥林匹克公园跑步4天跑步","NodeID":"6004406","NodeType":"event","UserName":"Doyouhike","NickName":"Doyouhike","Face":"http://dev.static.doyouhike.com/files/faces/00/user2.gif","UserID":"2","MinilogType":"activity","refContent":"up土灰"},{"IsRead":"2","CommentID":"3441872","Created":"1465375984","reply_to":"0","Content":"回复 @凝固：切克闹，切克闹...","RefrenceID":"3441844","Title":"北京奥林匹克公园跑步4天跑步","NodeID":"6004406","NodeType":"event","UserName":"root","NickName":"root1155","Face":"http://dev.static.doyouhike.com/files/faces/0/d/0db6eebfa.jpg","UserID":"13","MinilogType":"activity","refContent":"8曲回家里"},{"IsRead":"2","CommentID":"3441871","Created":"1465373423","reply_to":"1508715","reply_to_username":"凝固","reply_to_nickname":"凝固","Content":"回复@凝固:hhhhhhhhhhhhhhhhhhhhhhh","RefrenceID":"3441792","Title":"20160216  快来报名","NodeID":"6002721","NodeType":"event","UserName":"wow","NickName":"Wowabc","Face":"http://dev.static.doyouhike.com/files/faces/8/f/8fe3e7d99.jpg","UserID":"10","MinilogType":"activity","refContent":"女权主义"}]
     * numNoRead : 0
     */

    private String numNoRead;
    /**
     * IsRead : 2
     * CommentID : 3441903
     * Created : 1465722589
     * reply_to : 1508715
     * reply_to_username : 凝固
     * reply_to_nickname : 凝固
     * Content : 好多好多好多话
     * RefrenceID : 3441844
     * Title : 北京奥林匹克公园跑步4天跑步
     * NodeID : 6004406
     * NodeType : event
     * UserName : wow
     * NickName : Wowabc
     * Face : http://dev.static.doyouhike.com/files/faces/8/f/8fe3e7d99.jpg
     * UserID : 10
     * MinilogType : activity
     * refContent : 8曲回家里
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
        private int IsRead;
        private String CommentID;
        private long Created;
        private String reply_to;
        private String reply_to_username;
        private String reply_to_nickname;
        private String RefrenceID;
        private String Title;
        private String NodeID;
        private String NodeType;
        private String UserName;
        private String NickName;
        private String Face;
        private String UserID;
        private String MinilogType;
        private String refContent;
        private String firstPhoto;
        private int is_show_content=1;   //0 评论已经删除不显示内容，提示删除的文案信息， 1显示评论内容

        private List<RichTextContent> Content;

        /**
         * 用于判断删除
         */
        private String NodeUserID;
        private String uuid;


        public int getIsRead() {
            return IsRead;
        }

        public void setIsRead(int IsRead) {
            this.IsRead = IsRead;
        }

        public String getCommentID() {
            return CommentID;
        }

        public void setCommentID(String CommentID) {
            this.CommentID = CommentID;
        }

        public long getCreated() {
            return Created;
        }

        public void setCreated(long Created) {
            this.Created = Created;
        }

        public String getReply_to() {
            return reply_to;
        }

        public void setReply_to(String reply_to) {
            this.reply_to = reply_to;
        }

        public String getReply_to_username() {
            return reply_to_username;
        }

        public void setReply_to_username(String reply_to_username) {
            this.reply_to_username = reply_to_username;
        }

        public String getReply_to_nickname() {
            return reply_to_nickname;
        }

        public void setReply_to_nickname(String reply_to_nickname) {
            this.reply_to_nickname = reply_to_nickname;
        }



        public String getRefrenceID() {
            return RefrenceID;
        }

        public void setRefrenceID(String RefrenceID) {
            this.RefrenceID = RefrenceID;
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

        public String getUserID() {
            return UserID;
        }

        public void setUserID(String UserID) {
            this.UserID = UserID;
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

        public String getFirstPhoto() {
            return firstPhoto;
        }

        public void setFirstPhoto(String firstPhoto) {
            this.firstPhoto = firstPhoto;
        }

        public String getStrContent() {

            if (null==Content){
                return "";
            }

            StringBuilder stringBuilder=new StringBuilder();

            for (RichTextContent richTextContent :Content){
                if (richTextContent.isTextType()){
                    stringBuilder.append(richTextContent.getContent()).append("\n");
                }
            }

            String strComment= StringUtil.removeEnd(stringBuilder.toString(),"\n");
            return strComment;
        }
        public String getSingleLineStrContent() {

            if (null==Content){
                return "";
            }

            StringBuilder stringBuilder=new StringBuilder();

            for (RichTextContent richTextContent :Content){
                if (richTextContent.isTextType()){
                    stringBuilder.append(richTextContent.getContent()).append(" ");
                }
            }

            String strComment= StringUtil.removeEnd(stringBuilder.toString()," ");
            return strComment;
        }


        public List<RichTextContent>  getContent() {
            return Content;
        }

        public void setContent(List<RichTextContent>  content) {
            Content = content;
        }


        public String getNodeUserID() {
            return NodeUserID;
        }

        public void setNodeUserID(String nodeUserID) {
            NodeUserID = nodeUserID;
        }

        public int getIs_show_content() {
            return is_show_content;
        }

        public void setIs_show_content(int is_show_content) {
            this.is_show_content = is_show_content;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }
    }

}
