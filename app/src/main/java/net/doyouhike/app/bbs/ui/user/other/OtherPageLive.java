package net.doyouhike.app.bbs.ui.user.other;

import android.content.Context;

import net.doyouhike.app.bbs.biz.openapi.presenter.page.user.PageLive;

/**
 * 功能：
 *
 * @author：曾江 日期：16-3-23.
 */
public class OtherPageLive extends PageLive {

    public OtherPageLive(Context context,String otherId) {
        super(context,otherId);
        getAdapter().setOtherId(otherId);
    }

    @Override
    public String[] getEmptyTip() {
        return new String[]{"","还未留下任何痕迹"};
    }
}
