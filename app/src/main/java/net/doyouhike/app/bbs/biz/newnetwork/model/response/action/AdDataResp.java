package net.doyouhike.app.bbs.biz.newnetwork.model.response.action;

/**
 * 功能：活动页的广告内容
 * 作者：曾江
 * 日期：15-12-30.
 */
public class AdDataResp {

    /**
     * ID : 4
     * LinkType : mf_link
     * ImageUrl : http://c1.zdb.io/files/2015/12/09/7/7a8f7acbd766d6aa3e9e44aef5ec4180_b.jpg
     * LinkUrl : http://www.doyouhike.net/forum/backpacking/2332820,0,0,1.html?jhsign=20228ffadbdb5ec478704ffec04ff767&uid=3000463
     * NodeType : 1
     */

    private String ID;
    private String LinkType;
    private String ImageUrl;
    private String LinkUrl;
    private String NodeType;

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setLinkType(String LinkType) {
        this.LinkType = LinkType;
    }

    public void setImageUrl(String ImageUrl) {
        this.ImageUrl = ImageUrl;
    }

    public void setLinkUrl(String LinkUrl) {
        this.LinkUrl = LinkUrl;
    }

    public void setNodeType(String NodeType) {
        this.NodeType = NodeType;
    }

    public String getID() {
        return ID;
    }

    public String getLinkType() {
        return LinkType;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public String getLinkUrl() {
        return LinkUrl;
    }

    public String getNodeType() {
        return NodeType;
    }
}
