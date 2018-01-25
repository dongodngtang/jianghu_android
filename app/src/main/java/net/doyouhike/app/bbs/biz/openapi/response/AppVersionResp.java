package net.doyouhike.app.bbs.biz.openapi.response;

/**
 * 作者：luochangdong on 16/10/28
 * q q:2270333671
 * 描述：
 */
public class AppVersionResp {

    /**
     * upgrade : 0
     * tip : 请更新到最新版本
     * url : http://www.doyouhike.net/appdownload/mofangapp
     */

    private int upgrade;
    private String tip;
    private String url;

    public int getUpgrade() {
        return upgrade;
    }

    public void setUpgrade(int upgrade) {
        this.upgrade = upgrade;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
