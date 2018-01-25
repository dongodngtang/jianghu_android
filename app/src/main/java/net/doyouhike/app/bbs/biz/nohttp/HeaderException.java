package net.doyouhike.app.bbs.biz.nohttp;

/**
 * 作者：luochangdong on 16/11/1
 * q q:2270333671
 * 描述：
 */
public class HeaderException extends Exception {
    private int responseCode;
    public HeaderException(String detailMessage,int responseCode) {
        super(detailMessage);
        this.responseCode = responseCode;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }
}
