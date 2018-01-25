package net.doyouhike.app.bbs.ui.activity.message;

import android.content.Context;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.presenter.page.base.PageBase;
import net.doyouhike.app.bbs.biz.openapi.request.users.messages.MessagesEventsGet;
import net.doyouhike.app.bbs.biz.openapi.response.users.UserMsgEventResp;
import net.doyouhike.app.bbs.ui.adapter.message.MsgEventAdapter;
import net.doyouhike.app.bbs.util.UserInfoUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：luochangdong on 16/10/17
 * 描述：
 */
public class MessageEventPage extends PageBase<UserMsgEventResp.MsgsBean> {

    public MessageEventPage(Context context) {
        items = new ArrayList<>();
        adapter = new MsgEventAdapter(context, items);
        mRequest = new MessagesEventsGet(UserInfoUtil.getInstance().getUserId());
    }

    @Override
    public MessagesEventsGet getRequestParam() {
        return (MessagesEventsGet) mRequest;
    }

    @Override
    public void handleResponse(Response response, boolean isRefresh) {
        //数据转换
        List<UserMsgEventResp.MsgsBean> items = ((Response<UserMsgEventResp>) response).getData().getMsgs();
        updateItem(items, isRefresh);
    }

    @Override
    public String[] getEmptyTip() {
        return new String[]{"暂无活动", "先去参加活动"};
    }

    @Override
    public boolean needEmptyRefresh() {
        return false;
    }

    @Override
    public PullToRefreshBase.Mode needPullDownRefresh() {
        return PullToRefreshBase.Mode.PULL_FROM_END;
    }
}
