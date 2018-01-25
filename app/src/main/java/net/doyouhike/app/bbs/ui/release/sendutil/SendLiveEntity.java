package net.doyouhike.app.bbs.ui.release.sendutil;

import net.doyouhike.app.bbs.base.application.MyApplication;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.BaseTag;
import net.doyouhike.app.bbs.biz.openapi.request.nodes.timelines.MinilogPost;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventDetailResp;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.NodeTimeline;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能： 发送直播
 *
 * @author：曾江 日期：16-3-17.
 */
public class SendLiveEntity extends BaseSendEntity {


    @Override
    protected void updateLocalState() {
        SharedPreferencesManager.setPostLive(getTimeline());
    }

    @Override
    protected void sendSuccess() {
        SharedPreferencesManager.setPostLive(null);
        SharedPreferencesManager.clearToReleaseLiveInfo(MyApplication.getInstance().getApplicationContext());
    }


    @Override
    public MinilogPost getSendLiveParam() {

        MinilogPost param = new MinilogPost();

        if (getTimeline().getContent().getText() != null)
            param.setContent(getTimeline().getContent().getText());

        if (!isEmptyImage()) {
            List<MinilogPost.PhotosBean> photos = new ArrayList<>();
            MinilogPost.PhotosBean bean;
            for (EventDetailResp.ContentBean item : getImgs()) {
                if (item.getPhoto_id() != null) {
                    bean = new MinilogPost.PhotosBean();
                    bean.setTemp_photo_id(item.getPhoto_id());
                    photos.add(bean);
                }
            }
            param.setPhotos(photos);
        }

        //标签
        param.setTag_ids(getTagId(getTimeline().getTags()));
        //位置处理
        if (null != getTimeline().getLocation()) {
            MinilogPost.LocationBean location = new MinilogPost.LocationBean();
            location.setAltitude(getTimeline().getLocation().getAltitude());
            location.setLatitude(getTimeline().getLocation().getLatitude());
            location.setLongitude(getTimeline().getLocation().getLongitude());
            location.setCity_id(getTimeline().getLocation().getCity_id());
            location.setDest_id(getTimeline().getLocation().getDest_id());
            location.setLocation_name(getTimeline().getLocation().getLocationName());
            param.setLocation(location);
        }
        return param;
    }

    private List<String> getTagId(List<BaseTag> tags) {

        List<String> tag_ids = new ArrayList<>();
        if (tags != null) {
            for (BaseTag tag : tags) {
                tag_ids.add(tag.getTag_id());
            }
        }
        return tag_ids;

    }
}
