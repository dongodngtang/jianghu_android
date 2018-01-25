package net.doyouhike.app.bbs.ui.activity.message;

import android.content.Context;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

import net.doyouhike.app.bbs.biz.openapi.presenter.page.base.PageBase;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.model.request.get.message.GetLikeMeListParam;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.message.GetLikeMeListResp;
import net.doyouhike.app.bbs.biz.openapi.request.users.messages.MessagesLikesGet;
import net.doyouhike.app.bbs.biz.openapi.response.users.UserMsgLikeResp;
import net.doyouhike.app.bbs.ui.adapter.message.MsgLikedAdapter;
import net.doyouhike.app.bbs.util.UserInfoUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：luochangdong on 16/9/1
 * 描述：
 */
public class MessageLikedPage extends PageBase<UserMsgLikeResp.MsgsBean> {

    public MessageLikedPage(Context context) {
        items = new ArrayList<>();
        adapter = new MsgLikedAdapter(context, items);
        mRequest = new MessagesLikesGet(UserInfoUtil.getInstance().getUserId());
    }

    @Override
    public MessagesLikesGet getRequestParam() {
        mRequest.setToken(UserInfoUtil.getInstance().getToken());
        return (MessagesLikesGet) mRequest;
    }

    @Override
    public void handleResponse(Response response, boolean isRefresh) {
        List<UserMsgLikeResp.MsgsBean> item = ((Response<UserMsgLikeResp>) response).getData().getMsgs();
        updateItem(item, isRefresh);
    }

    @Override
    public String[] getEmptyTip() {
        return new String[]{"暂无点赞", "先去点赞别人"};
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
