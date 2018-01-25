package net.doyouhike.app.bbs.ui.activity.me;

import android.view.View;
import android.widget.ImageView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.activity.BaseActivity;
import net.doyouhike.app.bbs.biz.db.green.entities.Follow;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.presenter.UserSettingHelper;
import net.doyouhike.app.bbs.biz.openapi.presenter.page.user.FansPage;
import net.doyouhike.app.bbs.biz.openapi.presenter.page.user.FollowPage;
import net.doyouhike.app.bbs.biz.helper.list_helper.SimpleListHelper;
import net.doyouhike.app.bbs.biz.openapi.request.users.UsersSettingPut;
import net.doyouhike.app.bbs.biz.openapi.response.users.UserSettingResp;
import net.doyouhike.app.bbs.ui.widget.common.IUpdateView;
import net.doyouhike.app.bbs.ui.widget.common.TitleView;

import butterknife.InjectView;

/**
 * 作者：luochangdong on 16/9/26
 * 描述：
 */
public class FollowFansActivity extends BaseActivity implements IUpdateView {

    @InjectView(R.id.navigation_title)
    TitleView navigationTitle;
    @InjectView(R.id.lv_follow_and_follower_list)
    PullToRefreshListView lvFollowAndFollowerList;

    /**
     * 消息提醒开关
     */
    ImageView ivBell;

    SimpleListHelper<Follow> simpleListHelper;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_follow_fans;
    }

    @Override
    protected View getLoadingTargetView() {
        return lvFollowAndFollowerList;
    }

    @Override
    protected void initViewsAndEvents() {
        ivBell = navigationTitle.getRight_image();
        UserSettingResp.UserSettingBean setting = UserSettingHelper.getInstance().getUserSettingBean();
        ivBell.setSelected(!setting.isPush_fans_msg());
        ivBell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserSettingHelper.getInstance().pushFans(mContext, listener, true);
            }
        });


        String user_id = getIntent().getStringExtra("user_id");
        String followOrfans = getIntent().getStringExtra("follow_fans");
        if (followOrfans.equals("follow")) {
            ivBell.setVisibility(View.GONE);
            FollowPage followPage = new FollowPage(this, user_id);
            simpleListHelper = new SimpleListHelper<Follow>(lvFollowAndFollowerList, this, followPage);
            navigationTitle.setTitle(getString(R.string.follow));
        } else {
            ivBell.setVisibility(View.VISIBLE);
            FansPage fansPage = new FansPage(this, user_id);
            simpleListHelper = new SimpleListHelper<Follow>(lvFollowAndFollowerList, this, fansPage);
            navigationTitle.setTitle(getString(R.string.follower));
        }

        simpleListHelper.getData(true);
    }

    IOnResponseListener listener = new IOnResponseListener() {
        @Override
        public void onSuccess(Response response) {
            UsersSettingPut put = (UsersSettingPut) response.getExtraTag();

            boolean on_off = put.isPush_fans_msg();
            ivBell.setSelected(!on_off);

        }

        @Override
        public void onError(Response response) {

        }
    };
}
