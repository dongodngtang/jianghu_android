package net.doyouhike.app.bbs.biz.openapi.presenter.page.action;

import android.content.Context;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

import net.doyouhike.app.bbs.biz.openapi.presenter.page.base.PageBase;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.request.events.EventMembersGet;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventMembersResp;
import net.doyouhike.app.bbs.biz.openapi.presenter.UsersHelper;
import net.doyouhike.app.bbs.ui.adapter.action.ActionMemberAdapter;
import net.doyouhike.app.bbs.biz.openapi.presenter.EventRole;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动成员未确认
 * Created by zengjiang on 16/7/19.
 */
public class ActionMemberConfirmPage extends PageBase<EventMembersResp.ItemsBean> {


    public ActionMemberConfirmPage(Context context, String nodeID) {
        items = new ArrayList<>();
        adapter = new ActionMemberAdapter(context, items, false);
        mRequest = new EventMembersGet(nodeID, false);
    }

    @Override
    public void handleResponse(Response response, boolean isRefresh) {

        EventMembersResp data = ((Response<EventMembersResp>) response).getData();
        List<EventMembersResp.ItemsBean> unConfirmList = new ArrayList<>();
        for (EventMembersResp.ItemsBean item : data.getItems()) {
            int follow = UsersHelper.getSingleTon().getFollowStateByUserId(item.getUser().getUser_id());
            item.setIsFollow(follow);

            if (item.getRole() == EventRole.UNPASS) {
                unConfirmList.add(item);
            }
        }

        getAdapter().setCount(unConfirmList.size());
        updateItem(unConfirmList, isRefresh);

    }

    @Override
    public EventMembersGet getRequestParam() {
        return (EventMembersGet) mRequest;
    }


    @Override
    public ActionMemberAdapter getAdapter() {
        return (ActionMemberAdapter) adapter;
    }

    @Override
    public PullToRefreshBase.Mode needPullDownRefresh() {
        return PullToRefreshBase.Mode.DISABLED;
    }
}
