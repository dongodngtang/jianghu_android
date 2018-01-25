package net.doyouhike.app.bbs.biz.event.open;

/**
 * 作者：luochangdong on 16/10/20
 * 描述：
 */
public class BindPhoneEvent {
    private String mobile;

    public BindPhoneEvent(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
