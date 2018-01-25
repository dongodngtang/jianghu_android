package net.doyouhike.app.bbs.biz.openapi.presenter.page.user;

import android.content.Context;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

import net.doyouhike.app.bbs.biz.db.green.entities.Follow;
import net.doyouhike.app.bbs.biz.db.green.entities.UserFans;
import net.doyouhike.app.bbs.biz.openapi.presenter.page.base.PageBase;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.request.users.UsersFansGet;
import net.doyouhike.app.bbs.biz.openapi.response.account.UsersFansResp;
import net.doyouhike.app.bbs.ui.adapter.me.FollowFansAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：luochangdong on 16/9/26
 * 描述：
 */
public class FansPage extends PageBase<Follow> {

    public FansPage(Context context, String user_id) {
        items = new ArrayList<>();
        adapter = new FollowFansAdapter(context, items, "fans");
        mRequest = new UsersFansGet(user_id);
    }

    @Override
    public UsersFansGet getRequestParam() {
        return (UsersFansGet) mRequest;
    }

    @Override
    public PullToRefreshBase.Mode needPullDownRefresh() {
        return PullToRefreshBase.Mode.PULL_FROM_END;
    }

    @Override
    public void handleResponse(Response response, boolean isRefresh) {
        List<UserFans> fans = ((Response<UsersFansResp>) response).getData().getFans();
        List<Follow> follows = new ArrayList<>();
        Follow bean;
        for (UserFans item : fans) {
            bean = new Follow();
            bean.setUser_id(item.getUser_id());
            bean.setAvatar(item.getAvatar());
            bean.setFollow_each(item.getFollow_each());
            bean.setNick_name(item.getNick_name());
            bean.setSex(item.getSex());
            bean.setUser_desc(item.getUser_desc());
            follows.add(bean);
        }

        updateItem(follows, isRefresh);
    }
}
