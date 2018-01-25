package net.doyouhike.app.bbs.biz.openapi.presenter.page.base;

import android.widget.BaseAdapter;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

import net.doyouhike.app.bbs.biz.openapi.presenter.page.user.PageManager;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.request.BaseListGet;

import java.util.List;

/**
 * 功能：状态行为
 * 作者：曾江
 * 日期：16-1-12.
 */
public interface IPage<T> {

    /**
     * @return 返回列表
     */
    List<T> getItems();

    /**
     * @param item      列表内容
     * @param isRefresh 是否更新
     */
    void updateItem(List<T> item, boolean isRefresh);

    /**
     * @param manager 状态管理器
     */
    void updateView(PageManager manager);

    /**
     * @return 列表是否为空
     */
    boolean isEmpty();

    /**
     * @return 是否拥有更多
     */
    boolean hasMore();

    /**
     * @param hasMore 是否拥有更多
     */
    void sethasMore(boolean hasMore);

    /**
     * @return 获取适配器
     */
    BaseAdapter getAdapter();

    /**
     * @return 获取网络请求参数
     */
    BaseListGet getRequestParam();

    BaseListGet getRequestParam(boolean isRefresh);

    /**
     * @param response  网络请求结果
     * @param isRefresh 是否刷新
     */
    void handleResponse(Response response, boolean isRefresh);

    /**
     * @return 下一页
     */
    void setRequestPage();

    /**
     * @return 获取相关状态
     */
    State getState();

    void setState(State state);

    /**
     * @param msgs 获取列表失败信息
     */
    void getDataErr(String... msgs);

    void notifyDataChange();


    public String[] getEmptyTip();

    public void setEmptyTip(String... emptyTip);

    public String[] getErrTip();

    public void setErrTip(String[] errTip);

    /**
     * @return 网络请求的tag
     */
    String getRequestTag();

    void handleError(Response error, boolean isRefresh);

    /**
     * @return 空列表是否需要刷新
     */
    boolean needEmptyRefresh();

    /**
     * @return 是否需要下拉刷新
     */
    PullToRefreshBase.Mode needPullDownRefresh();
}
