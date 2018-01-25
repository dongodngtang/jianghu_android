package net.doyouhike.app.library.ui.widgets.linkify;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.URLSpan;

import net.doyouhike.app.library.ui.R;

/**
 * 作者：luochangdong on 16/8/31
 * 描述：
 */
public class URLSpanNoUnderline extends URLSpan {
    public URLSpanNoUnderline(String url) {
        super(url);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setUnderlineText(false);
        ds.setColor(Color.parseColor("#0076FF"));
    }
}
