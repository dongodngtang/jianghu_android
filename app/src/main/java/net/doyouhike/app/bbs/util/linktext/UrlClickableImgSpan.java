package net.doyouhike.app.bbs.util.linktext;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.Browser;
import android.util.Log;
import android.view.View;

/**
 * 功能：
 * 作者：曾江
 * 日期：16-1-25.
 */
public class UrlClickableImgSpan extends ClickableImgSpan {

    private final String mURL;

    public UrlClickableImgSpan(Drawable d, int verticalAlignment, String mURL) {
        super(d, verticalAlignment);
        this.mURL = mURL;
    }

    public void onClick(View widget) {
        Uri uri = Uri.parse(getURL());
        Context context = widget.getContext();
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.putExtra(Browser.EXTRA_APPLICATION_ID, context.getPackageName());
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.w("URLSpan", "Actvity was not found for intent, " + intent.toString());
        }
    }

    public String getURL() {
        return mURL;
    }
}
