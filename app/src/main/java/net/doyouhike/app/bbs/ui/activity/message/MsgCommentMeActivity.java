package net.doyouhike.app.bbs.ui.activity.message;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.activity.BaseActivity;
import net.doyouhike.app.bbs.biz.helper.list_helper.SimpleListHelper;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.presenter.UserSettingHelper;
import net.doyouhike.app.bbs.biz.openapi.presenter.UsersHelper;
import net.doyouhike.app.bbs.biz.openapi.request.users.UsersSettingPut;
import net.doyouhike.app.bbs.biz.openapi.response.users.UserSettingResp;
import net.doyouhike.app.bbs.biz.presenter.message.MsgCommoneMePresenter;
import net.doyouhike.app.bbs.ui.widget.common.IUpdateView;
import net.doyouhike.app.bbs.util.UserInfoUtil;
import net.doyouhike.app.library.ui.uistate.UiState;

import butterknife.InjectView;

/**
 * 作者:luochangdong on 16/6/14 15:18
 * 描述:
 */
public class MsgCommentMeActivity extends BaseActivity implements IUpdateView {


    @InjectView(R.id.title_left_content)
    LinearLayout titleLeftContent;
    @InjectView(R.id.iv_bell)
    ImageView ivBell;
    @InjectView(R.id.lv_msg_comment_list)
    PullToRefreshListView lvMsgCommentList;

    MessageCommentPage page;
    SimpleListHelper helper;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_msg_comment_me;
    }

    @Override
    protected void initViewsAndEvents() {
        UserSettingResp.UserSettingBean setting = UserSettingHelper.getInstance().getUserSettingBean();
        ivBell.setSelected(!setting.isPush_comment_msg());
        ivBell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserSettingHelper.getInstance().pushComment(mContext, listener, true);
            }
        });
        updateView(UiState.LOADING);
        page = new MessageCommentPage(this);
        helper = new SimpleListHelper(lvMsgCommentList, this, page);
        helper.getData(true);

    }

    @Override
    protected View getLoadingTargetView() {
        return lvMsgCommentList;
    }

    public PullToRefreshListView getLvMsgCommentList() {
        return lvMsgCommentList;
    }


    IOnResponseListener listener = new IOnResponseListener() {
        @Override
        public void onSuccess(Response response) {
            UsersSettingPut put = (UsersSettingPut) response.getExtraTag();

            boolean on_off = put.isPush_comment_msg();
            ivBell.setSelected(!on_off);

        }

        @Override
        public void onError(Response response) {

        }
    };

}
