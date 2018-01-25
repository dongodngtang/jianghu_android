package net.doyouhike.app.bbs.biz.openapi.presenter.page.user;

import android.content.Context;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

import net.doyouhike.app.bbs.biz.db.green.entities.Follow;
import net.doyouhike.app.bbs.biz.openapi.presenter.page.base.PageBase;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.request.users.UsersFollowsGet;
import net.doyouhike.app.bbs.biz.openapi.response.account.UsersFollowsResp;
import net.doyouhike.app.bbs.ui.adapter.me.FollowFansAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：luochangdong on 16/9/26
 * 描述：
 */
public class FollowPage extends PageBase<Follow> {

    public FollowPage(Context context, String user_id) {
        items = new ArrayList<>();
        adapter = new FollowFansAdapter(context, items, "follow");
        mRequest = new UsersFollowsGet(user_id);
    }

    @Override
    public UsersFollowsGet getRequestParam() {
        return (UsersFollowsGet) mRequest;
    }

    @Override
    public PullToRefreshBase.Mode needPullDownRefresh() {
        return PullToRefreshBase.Mode.DISABLED;
    }


    @Override
    public void handleResponse(Response response, boolean isRefresh) {
        List<Follow> follows = ((Response<UsersFollowsResp>) response).getData().getFollows();
        updateItem(follows, isRefresh);
    }
}
