package net.doyouhike.app.library.ui.widgets.linkify;

import android.text.style.URLSpan;
import android.view.View;

/**
 * 作者：luochangdong on 16/8/18
 * 描述：
 */
public class SmartClickSpan extends URLSpan {

    private SmartTextCallback mSmartTextCallback;

    public void setSmartTextCallback(SmartTextCallback mSmartTextCallback) {
        this.mSmartTextCallback = mSmartTextCallback;
    }

    public SmartClickSpan(String url) {
        super(url);
    }

    @Override
    public void onClick(View widget) {
        if (getURL().contains("tel:")) {
            if (mSmartTextCallback != null)
                mSmartTextCallback.phoneNumberClick(getURL());
        } else if (getURL().matches("(http:\\/\\/|https:\\/\\/|www.).{3,}")) {
            if (mSmartTextCallback != null)
                mSmartTextCallback.webUrlClick(getURL());
        }
    }
}
