package net.doyouhike.app.bbs.ui.home.topic;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.BaseTag;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.NodeTimeline;
import net.doyouhike.app.bbs.ui.adapter.NodeTimelineAdapter;
import net.doyouhike.app.bbs.ui.widget.LoadMoreListView;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;
import net.doyouhike.app.library.ui.uistate.UiState;
import net.doyouhike.app.library.ui.uistate.UiStateController;
import net.doyouhike.app.library.ui.widgets.XSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Filework:
 * Author: luochangdong
 * Date:16-1-30
 */
public class TimelineView implements SwipeRefreshLayout.OnRefreshListener, LoadMoreListView.OnLoadMoreListener {
    XSwipeRefreshLayout mSwipeRefreshLayout;
    LoadMoreListView mListView;
    private Context mContext;
    public static final int REFRESH = 1101;
    public static final int LOADMORE = 1102;
    TimelineHelper timelineHelper;
    /**
     * ui状态控制
     */
    protected UiStateController uiStateController;

    /**
     * 20 datas per page
     */
    public static final int PAGE_LIMIT = 20;
    /**
     * the page number
     */
    NodeTimelineAdapter liveAdapter;
    BaseTag mTag;

    private final String mCommonMsgTip;

    public TimelineView(Context context, XSwipeRefreshLayout mSwipeRefreshLayout,
                        LoadMoreListView mListView, BaseTag tag) {
        this.mSwipeRefreshLayout = mSwipeRefreshLayout;
        this.mListView = mListView;
        mContext = context;
        mTag = tag;

        mCommonMsgTip = context.getString(R.string.common_error_msg);
        timelineHelper = new TimelineHelper(this);
        initView();
    }


    public Context getContext() {
        return mContext;
    }

    @Override
    public void onLoadMore() {
        switch (mTag.getLiveType()) {
            case ATTEND:
                timelineHelper.onlineFollowRequest(getPageSize(), PAGE_LIMIT, LOADMORE);
                break;
            case HOT:
                timelineHelper.onlineHotRequest(getPageSize(), PAGE_LIMIT, LOADMORE);
                break;
            case TOPIC:
                timelineHelper.onlineLiveRequest(getPageSize(), LOADMORE, getLastId(), mTag);
                break;
        }
    }

    @Override
    public void onRefresh() {
        switch (mTag.getLiveType()) {
            case ATTEND:

                if (isReleasing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                } else {
                    //发送中不能刷新
                    timelineHelper.onlineFollowRequest(1, PAGE_LIMIT, REFRESH);
                }

                break;
            case HOT:
                timelineHelper.onlineHotRequest(1, PAGE_LIMIT, REFRESH);
                break;
            case TOPIC:
                timelineHelper.onlineLiveRequest(1, REFRESH, getLastId(), mTag);
                break;

        }

    }

    public void refreshListData(List<NodeTimeline.ItemsBean> timelines) {
        if (null != liveAdapter && null != timelines) {

            if (timelines.size() <= 0) {
                //没有内容

                if (mTag.getLiveType() != TimelineRequestType.TOPIC)
                    updateView(UiState.EMPTY.setMsg(mContext.getString(R.string.common_no_content)));
            }

            if (isReleasing()) {
                return;
            }

            liveAdapter.getDatas().clear();
            notifyDataSetChanged(timelines);
        }

    }


    public void addMoreListData(List<NodeTimeline.ItemsBean> timelines) {
        if (null != liveAdapter && null != timelines) {
//            if (timelines.size() <= 0) {
//                //没有内容
//                return;
//            }

//            mListView.setCanLoadMore(timelines.size() > 0);

            notifyDataSetChanged(timelines);
        }

    }

    public void loadDataError(String error) {
        loadComplete();
        if (null != liveAdapter && liveAdapter.getDatas().isEmpty()) {
            //错误提示界面 点击刷新

            updateView(UiState.ERROR.setMsg(mCommonMsgTip, error), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateView(UiState.LOADING);
                    initData();
                }
            });
        } else {
//            TipUtil.alert(mContext,"网络好像有点问题");
        }
    }

    public void hideLoading() {
        updateView(UiState.NORMAL);
        loadComplete();
    }

    public void addTempTimeline(NodeTimeline.ItemsBean.NodeBean nodeBean) {

        NodeTimeline.ItemsBean timeline = new NodeTimeline.ItemsBean();
        timeline.setNode(nodeBean);
        if (null != timeline && null != liveAdapter) {

            if (!liveAdapter.getDatas().isEmpty()) {

                if (liveAdapter.getDatas().contains(timeline)) {
                    return;
                }
            }

            liveAdapter.getDatas().add(0, timeline);
            liveAdapter.notifyDataSetChanged();
            mListView.scrollTo(0, 0);
            hideLoading();
        }
    }

    public List<NodeTimeline.ItemsBean> getItems() {
        return liveAdapter.getDatas();
    }

    public ListView getListView() {
        return mListView;
    }

    private int getPageSize() {

        return timelineHelper.getmCurrentPage() + 1;
    }


    public NodeTimelineAdapter getLiveAdapter() {
        return liveAdapter;
    }


    public void setTag(BaseTag mTag) {
        this.mTag = mTag;
    }

    public void resetData() {
        liveAdapter.getDatas().clear();
    }

    public int getLastId() {

        List<NodeTimeline.ItemsBean> items = liveAdapter.getDatas();


        if (null != items && !items.isEmpty()) {
            String lastId = items.get(items.size() - 1).getNode().getNode_id();

            if (!TextUtils.isEmpty(lastId)) {
                return Integer.valueOf(lastId);
            }
        }

        return 0;

    }


    public boolean isReleasing() {

        if (mTag.getLiveType() == TimelineRequestType.ATTEND) {

            return SharedPreferencesManager.getPostLive() != null || SharedPreferencesManager.getPostEvent() != null;

        }

        return false;
    }

    public void initData() {
        //获取离线数据
        //500mms 获取最新数据
        switch (mTag.getLiveType()) {
            case ATTEND:

                if (isReleasing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    updateView(UiState.NORMAL);
                } else {
                    //发送中不能刷新
                    timelineHelper.onlineFollowRequest(1, PAGE_LIMIT, REFRESH);
                }
                break;
            case NEARBY:
                updateView(UiState.NORMAL);
                break;
            case HOT:
                timelineHelper.onlineHotRequest(1, PAGE_LIMIT, REFRESH);
                break;
            case TOPIC:
                timelineHelper.onlineLiveRequest(1, REFRESH, getLastId(), mTag);
                break;

        }

    }

    private void initView() {
        mSwipeRefreshLayout.setColorSchemeColors(
                mContext.getResources().getColor(R.color.color_theme));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        liveAdapter = new NodeTimelineAdapter(mContext, new ArrayList<NodeTimeline.ItemsBean>());
        liveAdapter.setType(mTag.getLiveType());
        mListView.setOnLoadMoreListener(this);
        mListView.setAdapter(liveAdapter);
//        mListView.setOnScrollListener(new LvScrollEvent());

    }


    public void refreshData() {
        mSwipeRefreshLayout.setRefreshing(true);
        onRefresh();

    }

    private void loadComplete() {
        if (null != mSwipeRefreshLayout && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        } else {
            if (null != mListView) {
                mListView.onLoadMoreComplete();
            }

        }
    }

    private void notifyDataSetChanged(List<NodeTimeline.ItemsBean> timelines) {
        //历史去重
        timelines.removeAll(liveAdapter.getDatas());
        liveAdapter.getDatas().addAll(timelines);
        liveAdapter.notifyDataSetChanged();
        if (liveAdapter.getDatas().size() == 0) {
            updateView(UiState.EMPTY.setMsg(new String[]{
                    mContext.getString(R.string.common_no_content),
                    mContext.getString(R.string.try_to_click_refresh)}), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateView(UiState.LOADING);
                    initData();
                }
            });
        }

    }

    public void setUiStateController(UiStateController uiStateController) {
        this.uiStateController = uiStateController;
    }

    protected void updateView(UiState state) {
        updateView(state, null);
    }

    protected void updateView(UiState state, View.OnClickListener onClickListener) {
        if (null == uiStateController) {
            throw new IllegalArgumentException("You must return a right target view for ui state");
        }

        uiStateController.updateView(state, onClickListener);
    }

    public void onDestroyView() {
        getLiveAdapter().onDestory();
    }


}