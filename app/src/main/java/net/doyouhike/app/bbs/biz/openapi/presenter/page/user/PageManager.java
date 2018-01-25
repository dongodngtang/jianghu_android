package net.doyouhike.app.bbs.biz.openapi.presenter.page.user;

import android.widget.BaseAdapter;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.doyouhike.app.bbs.biz.openapi.presenter.page.base.IPage;
import net.doyouhike.app.library.ui.uistate.UiState;

/**
 * 功能：
 * 作者：曾江
 * 日期：16-1-12.
 */
public class PageManager {

    StateListener stateListener;
    PullToRefreshListView plvContent;
    IPage mStateImp;

    public PageManager(PullToRefreshListView plvContent) {
        this.plvContent = plvContent;
    }


    public void refreshData() {
        mStateImp.getRequestParam().setPage_index(1);
        mStateImp.sethasMore(true);
        stateListener.getListDate(mStateImp);
    }

    public void getMoreData() {
        mStateImp.setRequestPage();
        stateListener.getListDate(mStateImp);
    }


    public void setStateListener(StateListener stateListener) {
        this.stateListener = stateListener;
    }

    public void updateSate() {

        if (null==mStateImp){
            return;
        }

        switch (mStateImp.getState()) {
            case EMPTY:
                //空提示
                stateListener.setViewState(mStateImp, UiState.EMPTY.setMsg(mStateImp.getEmptyTip()));
                break;
            case NULL:
                //刷新数据
                stateListener.setViewState(mStateImp, UiState.LOADING);
                stateListener.getListDate(mStateImp);
                break;
            case NORMAL:
                //正常显示
                stateListener.setViewState(mStateImp, UiState.NORMAL);
                mStateImp.updateView(this);
//                if (mStateImp.hasMore()) {
//                    plvContent.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
//                } else {
//                    plvContent.setMode(PullToRefreshBase.Mode.DISABLED);
//                }
                break;
            case ERROR:
                //显示错误页
                stateListener.setViewState(mStateImp, UiState.ERROR.setMsg(mStateImp.getErrTip()));

                break;
        }

    }

    public void notifyDataChange() {
        //正常显示
        stateListener.setViewState(mStateImp, UiState.NORMAL);
        mStateImp.notifyDataChange();
        if (mStateImp.hasMore()) {
            plvContent.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        } else {
//            plvContent.setMode(PullToRefreshBase.Mode.DISABLED);
        }
    }

    public void updateAdapter(BaseAdapter adapter) {
        plvContent.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    public IPage getmStateImp() {
        return mStateImp;
    }

    public void checkoutSate(IPage state) {
        plvContent.onRefreshComplete();
        mStateImp = state;
    }

    public interface StateListener {

        /**
         * @param state 获取列表数据
         */
        void getListDate(IPage state);

        /**
         * @param state 更新UI状态
         */
        void setViewState(IPage page, UiState state);

    }
}
