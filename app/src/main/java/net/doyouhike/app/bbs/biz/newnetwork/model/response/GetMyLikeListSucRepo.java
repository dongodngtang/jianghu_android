package net.doyouhike.app.bbs.biz.newnetwork.model.response;


import java.util.List;

/**
 * Created by zaitu on 15-12-1.
 */
public class GetMyLikeListSucRepo {

    /**
     * {
     * "msgBean": [
     * {
     * "UserID": "1297852",
     * "UserName": "体验公司",
     * "Face": "http://static.test.doyouhike.net/files/faces/e/0/e0dbb1339.jpg",
     * "Created": "1446455228",
     * "Title": "1",
     * "refContent": "",  //被点赞的主帖内容
     * "MinilogType": "txt",
     * "NodeID": "1470701",
     * "IsRead": "0"  //   0和1未读， 2已读
     * }
     * ],
     * "numNoRead": 27  //未读的数量
     * }
     */

    private int numNoRead;
    /**
     * UserID : 1297852
     * UserName : 体验公司
     * Face : http://static.test.doyouhike.net/files/faces/e/0/e0dbb1339.jpg
     * Created : 1446455228
     * Title : 1
     * refContent : 被点赞的主帖内容
     * MinilogType : txt
     * NodeID : 1470701
     * IsRead : 0和1未读， 2已读
     */

    private List<LoginSucRepo> users;

    public void setNumNoRead(int numNoRead) {
        this.numNoRead = numNoRead;
    }

    public void setUsers(List<LoginSucRepo> users) {
        this.users = users;
    }

    public int getNumNoRead() {
        return numNoRead;
    }

    public List<LoginSucRepo> getUsers() {
        return users;
    }

    @Override
    public String toString() {
        return "GetMyLikeListSucRepo{" +
                "numNoRead=" + numNoRead +
                ", msgBean=" + users +
                '}';
    }
}
