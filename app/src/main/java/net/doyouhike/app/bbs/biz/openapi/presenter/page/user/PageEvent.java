package net.doyouhike.app.bbs.biz.openapi.presenter.page.user;

import android.content.Context;

import net.doyouhike.app.bbs.biz.openapi.presenter.page.base.PageBase;
import net.doyouhike.app.bbs.biz.newnetwork.model.request.get.UserEventsReq;
import net.doyouhike.app.bbs.biz.openapi.request.users.UserEventsGet;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventsListResp;
import net.doyouhike.app.bbs.ui.adapter.me.MeEventAdapter;

import java.util.ArrayList;

/**
 * 功能：
 * 作者：曾江
 * 日期：16-1-12.
 */
public class PageEvent extends PageBase<EventsListResp.ItemsBean> {


    public PageEvent(Context context,String user_id) {

        items = new ArrayList<>();
        adapter = new MeEventAdapter(context, items);
        mRequest = new UserEventsGet(user_id);

    }

    @Override
    public MeEventAdapter getAdapter() {
        return (MeEventAdapter) adapter;
    }

    @Override
    public UserEventsGet getRequestParam() {
        return (UserEventsGet) mRequest;
    }


    @Override
    public String[] getEmptyTip() {
        return new String[]{"","暂无活动"};
    }
}
