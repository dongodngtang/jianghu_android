package net.doyouhike.app.bbs.biz.openapi.presenter.page.base;

import android.widget.BaseAdapter;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

import net.doyouhike.app.bbs.biz.openapi.presenter.page.user.PageManager;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.request.BaseListGet;
import net.doyouhike.app.bbs.util.LogUtil;

import java.util.List;

/**
 * 功能：
 * 作者：曾江
 * 日期：16-1-12.
 */
public abstract class PageBase<T> implements IPage<T> {

    protected BaseAdapter adapter;
    protected List<T> items;
    boolean hasMore = true;
    protected BaseListGet mRequest;
    protected State state = State.NULL;
    private int mCurrentPage;

    String[] emptyTip;
    String[] errTip;

    @Override
    public void updateItem(List<T> item, boolean isRefresh) {

        if (isRefresh) {
            items.clear();
        }


        if (null == item || item.size() <= 0) {
//            hasMore = false;

            if (isRefresh) {
                //刷新返回空
                state = State.EMPTY;
                setEmptyMsg();
            }
            return;
        }

        mCurrentPage = mRequest.getPage_index();
        items.addAll(item);

        if (items.isEmpty()) {
            state = State.EMPTY;
            setEmptyMsg();

        } else {
            state = State.NORMAL;
        }

        if (null != getAdapter()) {
            getAdapter().notifyDataSetChanged();
        }

    }

    @Override
    public BaseAdapter getAdapter() {
        return adapter;
    }

    @Override
    public List<T> getItems() {
        return items;
    }

    @Override
    public void handleResponse(Response response, boolean isRefresh) {
        updateItem(((Response<List<T>>) response).getData(), isRefresh);
    }

    @Override
    public void handleError(Response error, boolean isRefresh) {
        if (isRefresh && getItems().isEmpty()) {
            setState(State.ERROR);
        } else {
            setState(State.NORMAL);
        }
    }

    @Override
    public BaseListGet getRequestParam(boolean isRefresh) {
        setRequestPage();
        return getRequestParam();
    }

    @Override
    public void updateView(PageManager manager) {
        manager.updateAdapter(adapter);
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public boolean hasMore() {
        return hasMore;
    }

    @Override
    public boolean needEmptyRefresh() {
        return true;
    }

    @Override
    public void sethasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    @Override
    public void setRequestPage() {
        mRequest.setPage_index(getPageSize());
    }

    private int getPageSize() {
        return mCurrentPage + 1;
    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public void setState(State state) {
        this.state = state;
    }

    @Override
    public void getDataErr(String... msgs) {
        if (items.isEmpty()) {
            state = State.ERROR;
            setErrTip(msgs);
        }
    }

    @Override
    public void notifyDataChange() {
        adapter.notifyDataSetChanged();
    }

    protected void setEmptyMsg() {

    }

    @Override
    public String[] getEmptyTip() {
        return emptyTip;
    }

    @Override
    public void setEmptyTip(String... emptyTip) {
        this.emptyTip = emptyTip;
    }

    @Override
    public String[] getErrTip() {
        return errTip;
    }

    @Override
    public void setErrTip(String[] errTip) {
        this.errTip = errTip;
    }

    @Override
    public String getRequestTag() {
        return "";
    }

    @Override
    public PullToRefreshBase.Mode needPullDownRefresh() {
        return PullToRefreshBase.Mode.BOTH;
    }
}
