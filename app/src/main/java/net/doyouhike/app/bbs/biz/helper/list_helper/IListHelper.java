package net.doyouhike.app.bbs.biz.helper.list_helper;

import android.view.View;
import android.widget.BaseAdapter;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.request.BaseListGet;
import net.doyouhike.app.library.ui.uistate.UiState;

import java.util.List;

/**
 * 功能：列表
 *
 * @author：曾江 日期：16-5-3.
 */
public interface IListHelper {


    /**
     * @param hasMore 有无更多数据
     * @param isRefresh 是否刷新
     */
    void onResponse(boolean hasMore,boolean isRefresh);

    /**
     * @param response 网络请求结果
     * @param isRefresh
     * @return 将网络请求结果取出列表结果
     */
    void handleResponse(Response response, boolean isRefresh);
    /**
     * @param isRefresh 是否刷新
     */
    void getData(boolean isRefresh);

    /**
     * @param error 加载列表错误回调
     * @param isRefresh
     */
    void onError(Response error, boolean isRefresh);

    /**
     * @param error
     * @param isRefresh
     */
    void handleError(Response error, boolean isRefresh);


    /**
     * 刷新视图
     */
    void setRefreshView();

    /**
     * 加载更多视图
     */
    void setLoadMoreView();


    /**
     * 加载完成视图
     * @param isRefresh
     * @param isError
     */
    void setLoadCompleteView(boolean isRefresh, boolean isError);


    /**
     * 下拉刷新时调用此方法
     */
    void onRefresh();

    /**
     * 设置适配器
     */
    void setAdapter();


    /**
     * @return 列表数据
     */
    List getItems();

    /**
     * @return 不同的适配器
     */
    BaseAdapter getAdapter();

    /**
     * @param isRefresh 是否刷新
     * @return 获取请求数据参数
     */
    BaseListGet getParams(boolean isRefresh);

    /**
     * @return 获取请求数据参数
     */
    BaseListGet getParams();

    /**
     * @return 网络请求tag
     */
    String getRequestTag();
    /**
     * @param uiState 更新ui ui状态
     */
    void updateView(UiState uiState);
    /**
     * @param uiState 更新ui ui状态
     */
    void updateView(UiState uiState, View.OnClickListener listener);

    /**
     * @return 错误提示信息
     */
    String[] getErrTip();

    /**
     * @return 列表为空时的提示信息
     */
    String[] getEmptyTip();

    /**
     * @return 有无更多数据
     */
    boolean hasMore();

    /**
     * @return 空列表是否需要刷新
     */
    boolean needEmptyRefresh();
    /**
     * @return 是否需要下拉刷新
     */
    PullToRefreshBase.Mode needPullDownRefresh();

    /**
     * 页码销毁调用
     */
    void onDestroy();
}
