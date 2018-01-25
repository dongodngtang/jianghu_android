package net.doyouhike.app.bbs.biz.newnetwork.model.bean;

/**
 * 功能：活动页，目的地的内容
 * 作者：曾江
 * 日期：15-12-29.
 */
public class DestinationItem {
    boolean isTitle=false;
    String content;
    int catID;
    int forumID;

    public DestinationItem() {
    }

    public DestinationItem(boolean isTitle, String content) {
        this.isTitle = isTitle;
        this.content = content;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public void setIsTitle(boolean isTitle) {
        this.isTitle = isTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCatID() {
        return catID;
    }

    public void setCatID(int catID) {
        this.catID = catID;
    }

    public int getForumID() {
        return forumID;
    }

    public void setForumID(int forumID) {
        this.forumID = forumID;
    }
}
