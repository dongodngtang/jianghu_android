package net.doyouhike.app.bbs.biz.newnetwork.model.response.login;

/**
 * 作者：luochangdong on 16/8/10
 * 邮箱：2270333671@qq.com
 * 描述：响应 GetServerParam 请求
 */
public class GetServerResponse {

    /**
     * serverUrl : http://dev.jh-api.zaitu.cn/
     * ipUrl : http://192.168.10.241/
     * android_ver_code : 25
     * ios_ver_code : 25
     * download_url : http://www.doyouhike.net/appdownload/mofangapp/upgrade
     */

    private String serverUrl;
    private String ipUrl;
    private int android_ver_code;
    private int ios_ver_code;
    private String download_url;

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getIpUrl() {
        return ipUrl;
    }

    public void setIpUrl(String ipUrl) {
        this.ipUrl = ipUrl;
    }

    public int getAndroid_ver_code() {
        return android_ver_code;
    }

    public void setAndroid_ver_code(int android_ver_code) {
        this.android_ver_code = android_ver_code;
    }

    public int getIos_ver_code() {
        return ios_ver_code;
    }

    public void setIos_ver_code(int ios_ver_code) {
        this.ios_ver_code = ios_ver_code;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }
}
