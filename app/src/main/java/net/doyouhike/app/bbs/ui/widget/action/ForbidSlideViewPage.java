package net.doyouhike.app.bbs.ui.widget.action;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

public class ForbidSlideViewPage extends ViewPager {

    private boolean isCanScroll = true;

    public ForbidSlideViewPage(Context context) {
        super(context);
    }

    public ForbidSlideViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void scrollTo(int x, int y) {
        if (isCanScroll) {
            super.scrollTo(x, y);
        }
    }

    public boolean isCanScroll() {
        return isCanScroll;
    }

    public void setCanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }

}
