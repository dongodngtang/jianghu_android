package net.doyouhike.app.bbs.ui.activity.message;

import android.content.Context;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.presenter.page.base.PageBase;
import net.doyouhike.app.bbs.biz.openapi.request.users.messages.MessagesCommentsGet;
import net.doyouhike.app.bbs.biz.openapi.response.users.UserMsgCommentsResp;
import net.doyouhike.app.bbs.ui.adapter.message.MsgCommentMeAdapter;
import net.doyouhike.app.bbs.util.UserInfoUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：luochangdong on 16/9/1
 * 描述：
 */
public class MessageCommentPage extends PageBase<UserMsgCommentsResp.MsgsBean> {
    public MessageCommentPage(Context context) {
        items = new ArrayList<>();
        adapter = new MsgCommentMeAdapter(context, items);
        mRequest = new MessagesCommentsGet(UserInfoUtil.getInstance().getUserId());
    }



    @Override
    public MessagesCommentsGet getRequestParam() {
        return (MessagesCommentsGet) mRequest;
    }

    @Override
    public String[] getEmptyTip() {
        return new String[]{"暂无消息", "先去评论别人"};
    }

    @Override
    public boolean needEmptyRefresh() {
        return false;
    }

    @Override
    public void handleResponse(Response response, boolean isRefresh) {
        //数据转换
        List<UserMsgCommentsResp.MsgsBean> items = ((Response<UserMsgCommentsResp>) response).getData().getMsgs();
        updateItem(items, isRefresh);
    }

    @Override
    public PullToRefreshBase.Mode needPullDownRefresh() {
        return PullToRefreshBase.Mode.PULL_FROM_END;
    }
}
