package net.doyouhike.app.bbs.biz.newnetwork.model.bean;

/**
 * Created by luochangdong on 15-11-30.
 */
public class ServerUrl {


    /**
     * serverUrl : https://jhapi-testing.zaitu.cn/
     */

    private String serverUrl;

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    @Override
    public String toString() {
        return "ServerUrl{" +
                "serverUrl='" + serverUrl + '\'' +
                '}';
    }
}
