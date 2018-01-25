package net.doyouhike.app.bbs.ui.home.fragment;

import net.doyouhike.app.bbs.biz.newnetwork.model.bean.BaseTag;

/**
 * 功能：
 *
 * @author：曾江 日期：16-2-27.
 */
public class LiveFragmentFactory {

    public static BaseLiveFragment createLiveFragemtn(BaseTag tag) {

        switch (tag.getLiveType()) {
            case HOT:
                break;
            case NEARBY:
                break;
            case ATTEND:
                return AttendLiveFragment.newInstance(tag);
            default:
        }


        return BaseLiveFragment.newInstance(tag);
    }
}
