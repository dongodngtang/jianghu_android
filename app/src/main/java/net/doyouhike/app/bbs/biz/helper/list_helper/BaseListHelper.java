package net.doyouhike.app.bbs.biz.helper.list_helper;

import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.request.BaseListGet;
import net.doyouhike.app.bbs.biz.newnetwork.service.ApiReq;
import net.doyouhike.app.bbs.biz.nohttp.CallServer;
import net.doyouhike.app.library.ui.uistate.UiState;
import net.doyouhike.app.library.ui.widgets.XSwipeRefreshLayout;

/**
 * 功能：
 *
 * @author：曾江 日期：16-5-3.
 */
public abstract class BaseListHelper implements IListHelper {


    /**
     * 上拉刷新view
     */
    protected XSwipeRefreshLayout refreshLayout;
    protected PullToRefreshListView listView;
    private boolean onDestroy = false;

    public BaseListHelper() {
    }


    public void initialize(final XSwipeRefreshLayout refreshLayout, final PullToRefreshListView listView) {

        this.refreshLayout = refreshLayout;
        this.listView = listView;

        if (null != refreshLayout) {

            //下拉刷新
            refreshLayout.setColorSchemeColors(
                    refreshLayout.getResources().getColor(R.color.color_theme));
            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    getData(true);
                }
            });

            listView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        } else {

            listView.setMode(needPullDownRefresh());
        }

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                getData(true);
            }

            @Override
            public void onPullUpToRefresh(final PullToRefreshBase<ListView> refreshView) {

                if (hasMore()) {
                    //可以加载更多数据
                    getData(false);
                } else {
                    //没有更多数据了
                    refreshView.post(new Runnable() {
                        @Override
                        public void run() {
                            refreshView.onRefreshComplete();
                        }
                    });
//                    Toast.makeText(refreshView.getContext(),"已经是最后一页",Toast.LENGTH_SHORT).show();
                }

            }
        });

        setAdapter();

    }

    /**
     * 获取数据
     *
     * @param isRefresh 是否刷新
     */
    public void getData(final boolean isRefresh) {
        final BaseListGet param = getParams(isRefresh);

        if (isRefresh) {
            onRefresh();
            if (getItems().isEmpty()) {
                updateView(UiState.LOADING);
            } else {
                setRefreshView();
            }
            //从1页开始获取数据
            param.setPage_index(1);
        } else {
            setLoadMoreView();
            param.setPage_index(param.getPage_index());
        }
        //设置页码标志,用于响应时判断是刷新还是请求更多数据
        param.setExtraInfo(param.getPage_index());
        getDataFromNet(param, isRefresh);
    }

    /**
     * get data from net
     *
     * @param param     request params
     * @param isRefresh true is refresh,false is get more data
     */
    protected void getDataFromNet(BaseListGet param, final boolean isRefresh) {

        if (!TextUtils.isEmpty(getRequestTag())) {
            CallServer.getRequestInstance().cancelBySign(getRequestTag());
        }
        param.setCancelSign(listView);
        ApiReq.doGet(param, new IOnResponseListener<Response>() {
            @Override
            public void onSuccess(Response response) {

                if (onDestroy) {
                    return;
                }
                handleResponse(response, isRefresh);

            }

            @Override
            public void onError(Response response) {
                if (onDestroy) {
                    return;
                }
                handleError(response, isRefresh);
            }
        });

    }

    /**
     * @param hasMore   有无更多数据
     * @param isRefresh 是否刷新
     */
    @Override
    public void onResponse(boolean hasMore, boolean isRefresh) {


        setLoadCompleteView(isRefresh, false);

        //设置是否能够加载更多

        setHasMoreView(hasMore);

    }

    /**
     * 设置更多视图
     *
     * @param hasMore 是否拥有更多
     */
    protected void setHasMoreView(boolean hasMore) {
//        if (null == refreshLayout) {
//
//            listView.setMode(
//                    hasMore ? PullToRefreshBase.Mode.BOTH : PullToRefreshBase.Mode.PULL_FROM_START);
//        } else {
//
//            listView.setMode(
//                    hasMore ? PullToRefreshBase.Mode.PULL_FROM_END : PullToRefreshBase.Mode.DISABLED);
//        }
    }


    @Override
    public void onError(Response error, boolean isRefresh) {

        if (onDestroy) {
            return;
        }
        setLoadCompleteView(isRefresh, true);
    }


    @Override
    public void setRefreshView() {


        if (null != refreshLayout) {

            listView.onRefreshComplete();
            refreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    if (null != refreshLayout)
                        refreshLayout.setRefreshing(true);
                }
            });
        }
    }

    @Override
    public void setLoadMoreView() {

        if (null != refreshLayout) {
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void setLoadCompleteView(boolean isRefresh, boolean isError) {


        if (null != refreshLayout) {
            if (isRefresh) {
                refreshLayout.setRefreshing(false);
            }
        }

        onListViewComplete();

        if (isRefresh)
            updateView(UiState.NORMAL);


        if (getItems().isEmpty()) {

            if (isError) {
                //显示错误页面
                showErrorView();
            } else {
                //显示空页面
                showEmptyView();
            }

        } else {
            if (!isError)
                getAdapter().notifyDataSetChanged();
        }
    }

    /**
     * listview加载完成后调用,需要延时线程完成,否则可能界面不响应
     */
    protected void onListViewComplete() {
        listView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (null != listView) {
                    listView.onRefreshComplete();
                }
            }
        }, 500);
    }

    protected void showErrorView() {
        String[] tip;
        tip = getErrTip();

        if (null == tip) {
            tip = new String[]{
                    listView.getContext().getString(R.string.network_anomaly),
                    listView.getContext().getString(R.string.try_to_click_refresh)
            };
        }

        updateView(UiState.ERROR.setMsg(tip), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(true);
            }
        });
    }

    protected void showEmptyView() {
        String[] tip;
        tip = getEmptyTip();
        //设置empty视图


        if (null == tip) {

            tip = needEmptyRefresh() ?

                    new String[]{
                            listView.getContext().getString(R.string.common_no_content),
                            listView.getContext().getString(R.string.try_to_click_refresh)}

                    :

                    new String[]{
                            listView.getContext().getString(R.string.common_no_content)};
        }

        if (needEmptyRefresh()) {
            updateView(UiState.EMPTY.setMsg(tip), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getData(true);
                }
            });
        } else {

            updateView(UiState.EMPTY.setMsg(tip));
        }

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void setAdapter() {

        if (getAdapter() != null) {
            listView.getRefreshableView().setAdapter(getAdapter());
        }
    }

    @Override
    public String getRequestTag() {
        return null;
    }


    @Override
    public void onDestroy() {
        CallServer.getRequestInstance().cancelBySign(listView);
        onDestroy = true;
    }
}
