package net.doyouhike.app.bbs.util.linktext;

import android.content.Context;
import android.content.Intent;
import android.text.style.URLSpan;
import android.view.View;

import net.doyouhike.app.bbs.ui.widget.common.webview.BaseWebViewActivity;

/**
 * 功能：
 *
 * @author：曾江 日期：16-2-27.
 */
public class UrlClickableSpan extends URLSpan {


    public UrlClickableSpan(String url) {
        super(url);
    }

    @Override
    public void onClick(View widget) {
        Context context = widget.getContext();
        Intent intent=new Intent();
        intent.putExtra(BaseWebViewActivity.I_URL,getURL());
        intent.setClass(context, BaseWebViewActivity.class);
        context.startActivity(intent);

    }
}
