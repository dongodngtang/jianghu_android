package net.doyouhike.app.bbs.ui.widget.common.webview;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.provider.Browser;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.activity.BaseActivity;
import net.doyouhike.app.bbs.ui.widget.common.TitleView;
import net.doyouhike.app.bbs.util.LogUtil;
import net.doyouhike.app.bbs.util.UIUtils;

import butterknife.InjectView;

/**
 * 功能：内嵌网页浏览器
 *
 * @author：曾江 日期：16-3-8.
 */
public class BaseWebViewActivity extends BaseActivity implements TitleView.ClickListener {


    public static final String I_URL = "params1";

    private static final String TAG = BaseWebViewActivity.class.getSimpleName();
    private static final int FILECHOOSER_RESULTCODE = 1024;
    @InjectView(R.id.pb_act_web_index)
    ProgressBar mProgressBar;
    @InjectView(R.id.wv_act_web_content)
    WebView mWebView;
    @InjectView(R.id.vi_web_act_root)
    RelativeLayout mRootView;
    @InjectView(R.id.title_act_web_base)
    TitleView titleActWebBase;

    private String url;


    /**
     * 网页选择文件
     */
    ValueCallback<Uri> mUploadMessage;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_webview_base;
    }

    @Override
    protected void initViewsAndEvents() {

        initialize();


        titleActWebBase.setListener(this);
        //支持javascript
        mWebView.getSettings().setJavaScriptEnabled(true);
        // 设置出现缩放工具
        mWebView.getSettings().setBuiltInZoomControls(false);
        //自适应屏幕
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.setWebChromeClient(getChromeClient());

        mWebView.setWebViewClient(new InfoDetailWebViewClient());
        url = getIntent().getStringExtra(I_URL);
        LogUtil.d(TAG, "url===" + url);
        mWebView.loadUrl(url);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage) return;
            Uri result = data == null || resultCode != RESULT_OK ? null
                    : data.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;

        }
    }

    protected void initialize() {

    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            this.finish();
        }
    }

    @Override
    protected void onDestroy() {


        mRootView.removeView(mWebView);
        mWebView.destroy();
        mWebView = null;
        super.onDestroy();
    }

    @Override
    public void clickLeft() {
        this.finish();
    }

    @Override
    public void clickRight() {

        if (TextUtils.isEmpty(mWebView.getUrl())) {
            return;
        }

        Uri uri = Uri.parse(mWebView.getUrl());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.putExtra(Browser.EXTRA_APPLICATION_ID, this.getPackageName());
        try {
            this.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.w("URLSpan", "Actvity was not found for intent, " + intent.toString());
        }
    }

    class InfoDetailWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (url.startsWith("tel:")) {
                //用intent启动拨打电话
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                BaseWebViewActivity.this.startActivity(intent);
                return true;
            }
            return false;
//            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            //handler.cancel(); 默认的处理方式，WebView变成空白页
//                        //接受证书
            handler.proceed();
            //handleMessage(Message msg); 其他处理
        }


    }

    protected WebChromeClient getChromeClient() {
        return new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (null != mProgressBar) {
                    //存在被销毁时,发送回调,导致空指针
                    mProgressBar.setProgress(newProgress);
                    UIUtils.showView(mProgressBar, 100 != newProgress);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (null != titleActWebBase) {
                    titleActWebBase.setTitle(title);
                }
            }

            //文件选择工具

            // For Android 3.0+
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                if (mUploadMessage != null) return;
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                startActivityForResult(Intent.createChooser(i, "File Chooser"), BaseWebViewActivity.FILECHOOSER_RESULTCODE);
            }

            // For Android < 3.0
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                openFileChooser(uploadMsg, "");
            }

            // For Android  > 4.1.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                openFileChooser(uploadMsg, acceptType);
            }


        };
    }
}
