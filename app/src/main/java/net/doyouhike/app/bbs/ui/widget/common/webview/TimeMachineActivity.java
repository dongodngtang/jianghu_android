package net.doyouhike.app.bbs.ui.widget.common.webview;

import android.content.Intent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import net.doyouhike.app.bbs.util.timemachine.TimeMachineUtil;
import net.doyouhike.app.bbs.util.LogUtil;
import net.doyouhike.app.bbs.util.ShareUtil;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;
import net.doyouhike.app.bbs.util.UserInfoUtil;

public class TimeMachineActivity extends BaseWebViewActivity {

    private static final String TAG = TimeMachineActivity.class.getSimpleName();

    @Override
    protected void initialize() {
        super.initialize();
//				//设置已经第一次阅读时光机的值
        TimeMachineUtil.setHasRead(this);

        String userId = UserInfoUtil.getInstance().getUserId();
        SharedPreferencesManager.setReadedTimelineMachine(this, userId);

        titleActWebBase.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    @Override
    protected WebChromeClient getChromeClient() {
        return new InjectedChromeClient("HostApp", HostJsScope.class) {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                LogUtil.d(TAG, "newProgress:" + newProgress);
            }

        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        ShareUtil.onActivityResult(requestCode, resultCode, data);

    }


}
