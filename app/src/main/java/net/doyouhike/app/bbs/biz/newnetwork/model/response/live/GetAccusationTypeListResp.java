package net.doyouhike.app.bbs.biz.newnetwork.model.response.live;

/**
 * 作者:luochangdong on 16/6/30 10:36
 * 描述:
 */
public class GetAccusationTypeListResp {

    /**
     * TypeID : 1
     * TypeName : 垃圾营销
     */

    private int TypeID;
    private String TypeName;

    public int getTypeID() {
        return TypeID;
    }

    public void setTypeID(int TypeID) {
        this.TypeID = TypeID;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String TypeName) {
        this.TypeName = TypeName;
    }
}
