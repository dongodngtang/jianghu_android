package net.doyouhike.app.bbs.biz.helper.list_helper;

import android.view.View;
import android.widget.BaseAdapter;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.request.BaseListGet;
import net.doyouhike.app.bbs.biz.openapi.presenter.page.base.IPage;
import net.doyouhike.app.bbs.ui.widget.common.IUpdateView;
import net.doyouhike.app.library.ui.uistate.UiState;
import net.doyouhike.app.library.ui.widgets.XSwipeRefreshLayout;

import java.util.List;

/**
 * 功能：
 *
 * @author：曾江 日期：16-5-4.
 */
public class SimpleListHelper <T>extends BaseListHelper {

    IPage<T> mPage;
    private IUpdateView uiListener;

    public SimpleListHelper(XSwipeRefreshLayout refreshLayout, PullToRefreshListView listView,
                            IUpdateView uiListener, IPage<T> page) {
        this.uiListener = uiListener;
        mPage = page;
        initialize(refreshLayout, listView);
        setAdapter();
    }

    public SimpleListHelper(PullToRefreshListView listView, IUpdateView uiListener, IPage<T> page) {
        this.uiListener = uiListener;
        mPage = page;
        initialize(null, listView);
        setAdapter();
    }

    public SimpleListHelper(PullToRefreshListView listView, IUpdateView uiListener) {
        this.uiListener = uiListener;
        initialize(null, listView);
    }


    public IPage getPage() {
        return mPage;
    }

    public void checkoutPage(IPage page) {

        this.mPage = page;

        switch (mPage.getState()) {
            case EMPTY:
                //空提示
                showEmptyView();
                break;
            case NULL:
                //刷新数据
                updateView((UiState.LOADING));
                setAdapter();
                getData(true);
                break;
            case NORMAL:
                //正常显示
                onListViewComplete();
                updateView((UiState.NORMAL));
                setAdapter();
                setHasMoreView(mPage.hasMore());
                break;
            case ERROR:
                //显示错误页
                showErrorView();
                break;
            case LOADING:
                if (getItems().isEmpty()){
                    updateView(UiState.LOADING);
                }
                break;
        }
    }


    @Override
    public void handleResponse(Response response, boolean isRefresh) {
            mPage.handleResponse(response,isRefresh);
            onResponse(mPage.hasMore(), isRefresh);
    }


    @Override
    public void handleError(Response error, boolean isRefresh) {
            mPage.handleError(error, isRefresh);
            onError(error,isRefresh);
    }


    @Override
    public List<T> getItems() {
        return mPage.getItems();
    }

    @Override
    public BaseAdapter getAdapter() {
        return mPage.getAdapter();
    }

    @Override
    public BaseListGet getParams(boolean isRefresh) {
        return mPage.getRequestParam(isRefresh);
    }

    @Override
    public BaseListGet getParams() {
        return mPage.getRequestParam();
    }

    @Override
    public void updateView(UiState uiState) {
        if (null != uiListener) {
            uiListener.updateView(uiState);
        }
    }

    @Override
    public void updateView(UiState uiState, View.OnClickListener listener) {

        if (null != uiListener) {
            uiListener.updateView(uiState, listener);
        }
    }

    @Override
    public boolean hasMore() {
        return mPage.hasMore();
    }

    /**
     * @return 空列表是否需要刷新
     */

    @Override
    public boolean needEmptyRefresh(){
        return mPage.needEmptyRefresh();
    }

    /**
     * @return 是否需要下拉刷新
     */

    @Override
    public PullToRefreshBase.Mode needPullDownRefresh(){
        return mPage.needPullDownRefresh();
    }


    @Override
    public String[] getErrTip() {
        return mPage.getErrTip();
    }

    @Override
    public String[] getEmptyTip() {
        return mPage.getEmptyTip();
    }
}
