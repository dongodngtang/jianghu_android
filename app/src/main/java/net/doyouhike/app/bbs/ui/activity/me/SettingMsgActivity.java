package net.doyouhike.app.bbs.ui.activity.me;

import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.activity.BaseActivity;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.presenter.UserSettingHelper;
import net.doyouhike.app.bbs.biz.openapi.request.users.UsersSettingPut;
import net.doyouhike.app.bbs.biz.openapi.response.users.UserSettingResp;
import net.doyouhike.app.bbs.util.UserInfoUtil;

import butterknife.InjectView;

public class SettingMsgActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    @InjectView(R.id.llyt_this)
    LinearLayout viParent;
    @InjectView(R.id.sc_comment_bell_switch)
    SwitchCompat scCommentBellSwitch;
    @InjectView(R.id.sc_like_bell_switch)
    SwitchCompat scLikeBellSwitch;
    @InjectView(R.id.sc_action_bell_switch)
    SwitchCompat scActionBellSwitch;

    @InjectView(R.id.sc_follow_bell_switch)
    SwitchCompat mFollowSwitch;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_setting_bell;
    }

    @Override
    protected View getLoadingTargetView() {
        return viParent;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Override
    protected void initViewsAndEvents() {

        // 判断是否已经登录,未登录就关闭本页面，然后提示登录
        if (!UserInfoUtil.getInstance().isLogin()) {
            showToast(getString(R.string.please_to_login));
            finish();
        }
        UserSettingResp.UserSettingBean setting = UserSettingHelper.getInstance().getUserSettingBean();
        checkoutState(scCommentBellSwitch, setting.isPush_comment_msg());
        checkoutState(scActionBellSwitch, setting.isPush_event_msg());
        checkoutState(scLikeBellSwitch, setting.isPush_like_msg());
        checkoutState(mFollowSwitch, setting.isPush_fans_msg());
        setListener();
    }


    /**
     * 给控件添加监听者
     */
    private void setListener() {
        scCommentBellSwitch.setOnCheckedChangeListener(this);
        scActionBellSwitch.setOnCheckedChangeListener(this);
        scLikeBellSwitch.setOnCheckedChangeListener(this);
        mFollowSwitch.setOnCheckedChangeListener(this);

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()) {
            case R.id.sc_comment_bell_switch:
                UserSettingHelper.getInstance().pushComment(this, listener, false);
                break;
            case R.id.sc_like_bell_switch:
                UserSettingHelper.getInstance().pushLike(this, listener, false);
                break;
            case R.id.sc_action_bell_switch:
                UserSettingHelper.getInstance().pushEvent(this, listener, false);
                break;
            case R.id.sc_follow_bell_switch:
                UserSettingHelper.getInstance().pushFans(this, listener, false);
                break;

        }

    }

    IOnResponseListener listener = new IOnResponseListener() {
        @Override
        public void onSuccess(Response response) {
            UsersSettingPut put = (UsersSettingPut) response.getExtraTag();
            if (put.isPush_event_msg() != null)
                checkoutState(scActionBellSwitch, put.isPush_event_msg());
            if (put.isPush_like_msg() != null)
                checkoutState(scLikeBellSwitch, put.isPush_like_msg());
            if (put.isPush_comment_msg() != null)
                checkoutState(scCommentBellSwitch, put.isPush_comment_msg());
            if (put.isPush_fans_msg() != null)
                checkoutState(mFollowSwitch, put.isPush_fans_msg());
        }

        @Override
        public void onError(Response response) {

        }
    };


    private void checkoutState(SwitchCompat switchCompat, boolean state) {
        switchCompat.setChecked(state);
    }


}
