package net.doyouhike.app.bbs.ui.fragment.start;

import android.content.Intent;
import android.net.Uri;

import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;

import net.doyouhike.app.bbs.BuildConfig;
import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.fragment.BaseFragment;
import net.doyouhike.app.bbs.biz.event.start.CheckoutEvent;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.presenter.AccountHelper;
import net.doyouhike.app.bbs.biz.openapi.response.AppVersionResp;
import net.doyouhike.app.bbs.biz.openapi.response.events.StartAdBannerResp;
import net.doyouhike.app.bbs.ui.activity.MainActivity;
import net.doyouhike.app.bbs.ui.activity.login.LoginActivity;
import net.doyouhike.app.bbs.ui.util.TipUtil;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;
import net.doyouhike.app.bbs.util.UserInfoUtil;

import de.greenrobot.event.EventBus;

/**
 * 启动页
 * Created by zengjiang on 16/8/26.
 */

public class StartFragment extends BaseFragment {


    private boolean hasAd = false;

    @Override
    protected void initViewsAndEvents() {

        AccountHelper.getInstance().appVersionGet(mContext, BuildConfig.APP_CODE, appVersionListener);
    }


    IOnResponseListener<Response<AppVersionResp>> appVersionListener = new IOnResponseListener<Response<AppVersionResp>>() {
        @Override
        public void onSuccess(final Response<AppVersionResp> response) {
            if (response.getData() == null) {
                AccountHelper.getInstance().getAdLoading(mContext, adListener);
            } else {
                SharedPreferencesManager.saveAppUpdateInfo(response.getData());
                switch (response.getData().getUpgrade()) {
                    case 0:
                        AccountHelper.getInstance().getAdLoading(mContext, adListener);
                        break;
                    case 1:
                        String[] str = {"取消", "升级"};
                        TipUtil.alert(mContext, "升级提示", response.getData().getTip(), str, new OnBtnClickL() {
                            @Override
                            public void onBtnClick() {
                                goNextPage();
                            }
                        }, new OnBtnClickL() {
                            @Override
                            public void onBtnClick() {
                                goBroser(response.getData().getUrl());
                            }
                        }).setCanceledOnTouchOutside(false);
                        break;
                    case 2:

                        MaterialDialog dialog = new MaterialDialog(mContext);
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.content(response.getData().getTip())
                                .title("升级提示")
                                .btnText("升级")
                                .setOnBtnClickL(new OnBtnClickL() {
                                    @Override
                                    public void onBtnClick() {
                                        goBroser(response.getData().getUrl());
                                    }
                                });
                        dialog.show();
                        break;
                }

            }

        }

        @Override
        public void onError(Response response) {
            goNextPage();
        }
    };


    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_start;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    protected void onFirstUserVisible() {

    }


    private void onStartApp() {

        if (hasAd) {
            EventBus.getDefault().post(new CheckoutEvent());
        } else {
            goNextPage();
        }

    }

    private void goNextPage() {
        if (UserInfoUtil.getInstance().isLogin()) {
            readyGo(MainActivity.class);
        } else {
            //启动登陆页
            readyGo(LoginActivity.class);
        }
        getActivity().finish();
    }


    IOnResponseListener<Response<StartAdBannerResp>> adListener = new IOnResponseListener<Response<StartAdBannerResp>>() {
        @Override
        public void onSuccess(Response<StartAdBannerResp> response) {

            if (response.getData() != null
                    && response.getData().getAd_result() != null
                    && response.getData().getAd_result().size() > 0) {
                //有缓存,跳转广告页
                hasAd = true;
            }
            onStartApp();

        }

        @Override
        public void onError(Response response) {
            goNextPage();
        }
    };

    private void goBroser(String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        startActivity(intent);
        getActivity().finish();
        System.exit(0);
    }

}
