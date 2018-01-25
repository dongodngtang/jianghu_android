package net.doyouhike.app.bbs.biz.openapi.presenter.page.search;

import android.content.Context;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.presenter.page.base.PageBase;
import net.doyouhike.app.bbs.biz.openapi.presenter.page.base.State;
import net.doyouhike.app.bbs.biz.openapi.request.events.EventsListGet;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventsListResp;
import net.doyouhike.app.bbs.ui.adapter.action.ActionSearchAdapter;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;

import java.util.ArrayList;

/**
 * 功能：活动搜索页
 * @author：曾江 日期：16-5-9.
 */
public class ActionSearchPage extends PageBase<EventsListResp.ItemsBean> {



    public ActionSearchPage(Context context) {
        items = new ArrayList<>();
        adapter = new ActionSearchAdapter(context, items);
        mRequest = new EventsListGet();
        state= State.NORMAL;
    }


    @Override
    public void handleResponse(Response response, boolean isRefresh) {
        Response<EventsListResp> eventsResp = response;
        if (!eventsResp.getData().getPhoto_domain_path().equals(SharedPreferencesManager.getPhotoDomainPath())) {
            SharedPreferencesManager.setPhotoDomainPath(eventsResp.getData().getPhoto_domain_path());
        }
        updateItem(eventsResp.getData().getItems(), isRefresh);
    }
    @Override
    public EventsListGet getRequestParam() {

        return (EventsListGet)mRequest;
    }

    @Override
    public PullToRefreshBase.Mode needPullDownRefresh() {
        return PullToRefreshBase.Mode.PULL_FROM_END;
    }

    @Override
    public String[] getEmptyTip() {
        return new String[]{"无结果","没有找到相关活动"};
    }

}
