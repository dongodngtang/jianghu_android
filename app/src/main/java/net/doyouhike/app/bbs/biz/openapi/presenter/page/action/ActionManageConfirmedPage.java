package net.doyouhike.app.bbs.biz.openapi.presenter.page.action;

import android.content.Context;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

import net.doyouhike.app.bbs.biz.openapi.presenter.page.base.PageBase;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.request.events.EventMembersGet;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventMembersResp;
import net.doyouhike.app.bbs.biz.openapi.presenter.UsersHelper;
import net.doyouhike.app.bbs.ui.adapter.action.ActionManageConfirmedAdapter;
import net.doyouhike.app.bbs.biz.openapi.presenter.EventRole;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动成员未确认
 * Created by zengjiang on 16/7/19.
 */
public class ActionManageConfirmedPage extends PageBase<EventMembersResp.ItemsBean>{



    public ActionManageConfirmedPage(Context context, String nodeID, String status, String currentRole) {
        items = new ArrayList<>();
        adapter = new ActionManageConfirmedAdapter(context, items,nodeID,status,currentRole);
        mRequest = new EventMembersGet(nodeID,true);



    }

    @Override
    public void handleResponse(Response response, boolean isRefresh) {

        EventMembersResp data = ((Response<EventMembersResp>) response).getData();
        List<EventMembersResp.ItemsBean> confirmedList = new ArrayList<>();
        for (EventMembersResp.ItemsBean item : data.getItems()) {
            int follow = UsersHelper.getSingleTon().getFollowStateByUserId(item.getUser().getUser_id());
            item.setIsFollow(follow);

            if (item.getRole() != EventRole.UNPASS) {
                confirmedList.add(item);
            }
        }

        getAdapter().setAttendSize(confirmedList.size());
        updateItem(confirmedList,isRefresh);

    }

    @Override
    public EventMembersGet getRequestParam() {
        return (EventMembersGet)mRequest;
    }


    @Override
    public ActionManageConfirmedAdapter getAdapter() {
        return (ActionManageConfirmedAdapter)adapter;
    }

    @Override
    public PullToRefreshBase.Mode needPullDownRefresh() {
        return PullToRefreshBase.Mode.DISABLED;
    }
}
