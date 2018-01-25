package net.doyouhike.app.bbs.ui.home.topic;

/**
 * 作者：luochangdong on 16/10/19
 * 描述：
 */
public class LoadPageMore {

    private int page;
    private int loadMore;

    public LoadPageMore(int page, int loadMore) {
        this.page = page;
        this.loadMore = loadMore;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLoadMore() {
        return loadMore;
    }

    public void setLoadMore(int loadMore) {
        this.loadMore = loadMore;
    }
}
