package net.doyouhike.app.bbs.ui.home;

import android.widget.AbsListView;

import com.bumptech.glide.Glide;

import net.doyouhike.app.bbs.base.application.MyApplication;
import net.doyouhike.app.bbs.util.LogUtil;

/**
 * Filework: Listview 滑动时间监听
 * Author: luochangdong
 * Date:16-2-23
 */
public class LvScrollEvent implements AbsListView.OnScrollListener {

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // TODO Auto-generated method stub
        switch (scrollState) {
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE: //
                Glide.with(view.getContext()).resumeRequests();
                // mBusy = false;
                LogUtil.d("停止...");
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                Glide.with(view.getContext()).pauseRequests();
                // mBusy = true;
                LogUtil.d("正在滑动...");
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                // mBusy = true;
                Glide.with(view.getContext()).pauseRequests();
                LogUtil.d("开始滚动...");

                break;
        }
    }

}
