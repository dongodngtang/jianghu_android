package net.doyouhike.app.bbs.ui.home.popupmenu.fixedtag;


import net.doyouhike.app.bbs.biz.newnetwork.model.bean.BaseTag;
import net.doyouhike.app.bbs.ui.home.topic.TimelineRequestType;

/**
 * 功能：
 *
 * @author：曾江 日期：16-2-27.
 */
public class TagFactory {


    public static BaseTag createTag(TimelineRequestType type) {

        BaseTag tag = new BaseTag();
        switch (type) {
            case HOT:
                tag.setTag_name("热门");
                tag.setTag_id(BaseTag.TYPE_TAG_ID_HOT);
                tag.setLiveType(TimelineRequestType.HOT);
                tag.setS_type(BaseTag.TYPE_SUB_TAG_FIXED);
                break;
            case NEARBY:
                tag.setTag_name("附近");
                tag.setTag_id(BaseTag.TYPE_TAG_ID_NEARBY);
                tag.setLiveType(TimelineRequestType.NEARBY);
                tag.setS_type(BaseTag.TYPE_SUB_TAG_FIXED);
                break;
            case ATTEND:
                tag.setTag_name("关注");
                tag.setTag_id(BaseTag.TYPE_TAG_ID_ATTEND);
                tag.setLiveType(TimelineRequestType.ATTEND);
                tag.setS_type(BaseTag.TYPE_SUB_TAG_FIXED);
                break;
            default:
                tag.setTag_name("TOPIC");
                tag.setTag_id(BaseTag.TYPE_TAG_ID);
                tag.setLiveType(TimelineRequestType.TOPIC);
                tag.setS_type(BaseTag.TYPE_SUB_TAG_FIXED);
        }

        return tag;
    }
}
