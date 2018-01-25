/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info., All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: ActionAdsActivity.java
 * Author: ZouWenJie
 * Version: 1.0
 * Create: 2015-12-14
 *
 */
package net.doyouhike.app.bbs.ui.activity.action;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.ui.activity.BaseActivity;

public class ActionAdsActivity extends BaseActivity {
    public static final String INTENT_EXTRA_NAME_LINKURL = "LinkUrl";
    private WebView vw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutId(R.layout.activity_action_ads);
        super.onCreate(savedInstanceState);
        bindControl();
        initData();
    }

    private void bindControl() {
        vw = (WebView) findViewById(R.id.wv_action_ads);
        vw.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    private void initData() {
        String LinkUrl = getIntent().getStringExtra(INTENT_EXTRA_NAME_LINKURL);
        vw.loadUrl(LinkUrl);
    }

}
