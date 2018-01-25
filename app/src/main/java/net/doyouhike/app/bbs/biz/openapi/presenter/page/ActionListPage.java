package net.doyouhike.app.bbs.biz.openapi.presenter.page;

import android.content.Context;

import net.doyouhike.app.bbs.biz.openapi.presenter.page.base.PageBase;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;
import net.doyouhike.app.bbs.biz.openapi.request.events.EventsListGet;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventsListResp;
import net.doyouhike.app.bbs.ui.adapter.action.ActionAdapter;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;

import java.util.ArrayList;

/**
 * 活动列表页
 * Created by zengjiang on 16/5/19.
 */
public class ActionListPage extends PageBase<EventsListResp.ItemsBean> {


    public ActionListPage(Context context) {
        items = new ArrayList<>();
        adapter = new ActionAdapter(context, items);
        mRequest = new EventsListGet();

//        初始化参数
        getRequestParam().setHas_friend("0");//0、不做好友过滤  1、好友参加
        getRequestParam().setSearch_type("1");//1、即将出发 2、离我最近（需传经纬度）
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

        return (EventsListGet) mRequest;
    }

    @Override
    public String getRequestTag() {
        return OpenApiUrl.EVENTS;
    }

    @Override
    public String[] getEmptyTip() {
        return new String[]{"", "暂无相关活动"};
    }

    @Override
    public boolean needEmptyRefresh() {
        //列表为空时,不需要刷新提醒
        return false;
    }
}
