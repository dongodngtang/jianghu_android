package net.doyouhike.app.bbs.biz.event.live;

/**
 * 首页切换
 * Created by zengjiang on 16/8/11.
 */

public class CheckoutMainPageEvent {
    /**
     * 哪个页面
     *
     * @see net.doyouhike.app.bbs.ui.activity.MainActivity#PAGE_MESSAGE
     */
    private int page;
    private int code;

    public CheckoutMainPageEvent(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
