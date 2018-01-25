package net.doyouhike.app.bbs.ui.user.other;

import net.doyouhike.app.bbs.biz.db.dao.UserInfoDbUtil;
import net.doyouhike.app.bbs.biz.entity.CurrentUserDetails;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.presenter.UsersHelper;
import net.doyouhike.app.bbs.ui.activity.me.IViewUser;
import net.doyouhike.app.bbs.biz.presenter.me.PresenterUser;

/**
 * 功能：
 *
 * @author：曾江 日期：16-3-22.
 */
public class OtherNetPresenter extends PresenterUser {


    public OtherNetPresenter(IViewUser iView) {
        super(iView);
    }

    public void getProfile(String userId) {

        UsersHelper.getSingleTon().getUserInfo(userId, getUserInfoDetailListener);
    }


    //获取用户资料详情
    //#######start#########

    protected IOnResponseListener getUserInfoDetailListener = new IOnResponseListener<Response<CurrentUserDetails>>() {
        @Override
        public void onSuccess(Response<CurrentUserDetails> response) {

            if (null == response.getData()) {
                return;
            }
            UserInfoDbUtil.getInstance().saveUser(response.getData());

            if (null != iView)
                iView.updateProfile(response.getData());
        }

        @Override
        public void onError(Response response) {
            //获取请求标签，如果是从第1页开始，则是刷新
            if (null != iView)
                iView.onRequestErr(IViewUser.ERR_COLLECT, response.getMsg());
        }
    };
}
