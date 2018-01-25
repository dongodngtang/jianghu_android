package net.doyouhike.app.bbs.ui.activity.me;

import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.activity.BaseActivity;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.presenter.UserSettingHelper;
import net.doyouhike.app.bbs.biz.openapi.request.users.UsersSettingPut;
import net.doyouhike.app.bbs.biz.openapi.response.users.UserSettingResp;
import net.doyouhike.app.bbs.ui.widget.common.TitleView;

import butterknife.InjectView;

/**
 * Filework:
 * Author: luochangdong
 * Date:16-3-29
 */
public class SettingPrivacyActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {
    @InjectView(R.id.title_privacy)
    TitleView titlePrivacy;
    /**
     * 通过手机号找到我
     */
    @InjectView(R.id.sc_privacy_phone)
    SwitchCompat scPrivacyPhone;
    /**
     * 发布约伴在附近显示
     */
    @InjectView(R.id.sc_privacy_near)
    SwitchCompat scPrivacyNear;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_privacy_setting;
    }


    @Override
    protected void initViewsAndEvents() {
        UserSettingResp.UserSettingBean setting = UserSettingHelper.getInstance().getUserSettingBean();
        checkoutState(scPrivacyNear, setting.isFind_by_nearly());
        checkoutState(scPrivacyPhone, setting.isFind_by_phone());
        scPrivacyNear.setOnCheckedChangeListener(this);
        scPrivacyPhone.setOnCheckedChangeListener(this);
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()) {
            case R.id.sc_privacy_phone:
                UserSettingHelper.getInstance().pushFindByPhone(mContext, listener);

                break;
            case R.id.sc_privacy_near:
                UserSettingHelper.getInstance().pushNearly(mContext, listener);
                break;
        }
    }

    IOnResponseListener listener = new IOnResponseListener() {
        @Override
        public void onSuccess(Response response) {
            UsersSettingPut put = (UsersSettingPut) response.getExtraTag();
            if (put.isFind_by_nearly() != null)
                checkoutState(scPrivacyNear, put.isFind_by_nearly());
            if (put.isFind_by_phone() != null)
                checkoutState(scPrivacyPhone, put.isFind_by_phone());
        }

        @Override
        public void onError(Response response) {

        }
    };

    private void checkoutState(SwitchCompat switchCompat, boolean state) {
        switchCompat.setChecked(state);
    }
}
