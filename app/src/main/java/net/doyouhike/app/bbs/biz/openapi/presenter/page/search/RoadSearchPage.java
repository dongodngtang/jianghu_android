package net.doyouhike.app.bbs.biz.openapi.presenter.page.search;

import android.content.Context;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

import net.doyouhike.app.bbs.biz.openapi.presenter.page.base.PageBase;
import net.doyouhike.app.bbs.biz.openapi.presenter.page.base.State;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.request.routes.DestsRoutesGet;
import net.doyouhike.app.bbs.biz.openapi.response.routes.DestsRoutesResp;
import net.doyouhike.app.bbs.ui.adapter.road.RoadSearchAdapter;

import java.util.ArrayList;

/**
 * 功能：活动搜索页
 * @author：曾江 日期：16-5-9.
 */
public class RoadSearchPage extends PageBase<DestsRoutesResp.ItemsBean> {



    public RoadSearchPage(Context context) {
        items = new ArrayList<>();
        adapter = new RoadSearchAdapter(context, items);
        mRequest = new DestsRoutesGet();
        state= State.NORMAL;
    }


    @Override
    public DestsRoutesGet getRequestParam() {
        return (DestsRoutesGet)mRequest;
    }

    @Override
    public RoadSearchAdapter getAdapter() {
        return (RoadSearchAdapter)adapter;
    }

    @Override
    public PullToRefreshBase.Mode needPullDownRefresh() {
        return PullToRefreshBase.Mode.PULL_FROM_END;
    }

    @Override
    public String[] getEmptyTip() {
        return new String[]{"无结果","没有找到相关线路"};
    }

    @Override
    public void handleResponse(Response response, boolean isRefresh) {
        updateItem (((Response<DestsRoutesResp>)response).getData().getItems(),isRefresh);
    }
}
