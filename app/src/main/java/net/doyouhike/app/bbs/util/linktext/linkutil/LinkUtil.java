package net.doyouhike.app.bbs.util.linktext.linkutil;

import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.widget.TextView;

import net.doyouhike.app.bbs.ui.widget.common.webview.BaseWebViewActivity;
import net.doyouhike.app.library.ui.widgets.linkify.LinkifyHelper;
import net.doyouhike.app.library.ui.widgets.linkify.SmartTextCallback;

/**
 * 作者：luochangdong on 16/9/6
 * 描述：
 */
public class LinkUtil {
    private static final Spannable.Factory spannableFactory = Spannable.Factory
            .getInstance();

    public static void setWebTextView(String content, final TextView textView) {
        Spannable spannable = spannableFactory.newSpannable(content);
        SmartTextCallback smartTextCallback = new SmartTextCallback() {
            @Override
            public void hashTagClick(String hashTag) {

            }

            @Override
            public void mentionClick(String mention) {

            }

            @Override
            public void emailClick(String email) {

            }

            @Override
            public void phoneNumberClick(String phoneNumber) {

            }

            @Override
            public void webUrlClick(String webUrl) {
                Context context = textView.getContext();
                Intent intent = new Intent();
                intent.putExtra(BaseWebViewActivity.I_URL, webUrl);
                intent.setClass(context, BaseWebViewActivity.class);
                context.startActivity(intent);
            }
        };
        LinkifyHelper.addLinks(spannable, smartTextCallback, LinkifyHelper.WEB_URLS);
        textView.setText(spannable);
    }
}
