package net.doyouhike.app.bbs.util.linktext;

import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;
import android.view.View;

/**
 * 功能：
 * 作者：曾江
 * 日期：16-1-25.
 */
public abstract class ClickableImgSpan extends ImageSpan {
    public ClickableImgSpan(Drawable d, int verticalAlignment) {
        super(d, verticalAlignment);
    }

    public abstract void onClick(View widget);
}
