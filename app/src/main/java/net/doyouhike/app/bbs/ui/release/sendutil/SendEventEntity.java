package net.doyouhike.app.bbs.ui.release.sendutil;

import android.text.TextUtils;

import com.google.gson.Gson;

import net.doyouhike.app.bbs.base.application.MyApplication;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.BaseTag;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.GetDestByKeywordResp;
import net.doyouhike.app.bbs.biz.openapi.request.nodes.timelines.EventPost;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventDetailResp;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.NodeTimeline;
import net.doyouhike.app.bbs.util.LogUtil;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;
import net.doyouhike.app.bbs.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能： 发送约伴活动
 *
 * @author：曾江 日期：16-3-17.
 */
public class SendEventEntity extends BaseSendEntity {


    @Override
    protected void updateLocalState() {
        if (StringUtil.isEmpty(getTimeline().getTempNoteId())) {
            SharedPreferencesManager.setPostEvent(getTimeline());

        } else {
            SharedPreferencesManager.setPostEventEdit(getTimeline());
        }
    }

    @Override
    protected void sendSuccess() {
        if (StringUtil.isEmpty(getTimeline().getTempNoteId())) {
            SharedPreferencesManager.setPostEvent(null);
        } else {
            SharedPreferencesManager.setPostEventEdit(null);
        }
        SharedPreferencesManager.clearToEventLiveInfo(MyApplication.getInstance().getApplicationContext());
    }


    @Override
    public EventPost getSendLiveParam() {


        EventPost param = new EventPost();


        //标签
        param.setTag_ids(getTagId(getTimeline().getTags()));

        param.setNode_id(getTimeline().getTempNoteId());

        //内容处理
        List<EventPost.ContentsBean> contentsBeen = new ArrayList<>();
        if (!isEmptyImage()) {
            for (EventDetailResp.ContentBean origin : getImgs()) {
                EventPost.ContentsBean
                        bean = new EventPost.ContentsBean();
                if (StringUtil.isNotEmpty(getTimeline().getTempNoteId())
                        && StringUtil.isNotEmpty(origin.getPhoto_id())) {
                    bean.setIs_new(origin.getIs_new());
                }
                // 填充图片id
                if (!TextUtils.isEmpty(origin.getPhoto_id())) {
                    bean.setPhoto_id(origin.getPhoto_id());
                }
                //填充输入文本
                if (origin.getContent() != null)
                    bean.setText(origin.getContent());


                contentsBeen.add(bean);
            }
        }
        param.setContents(contentsBeen);
        //位置处理
        param.setLocation(getTimeline().getLocation());

        EventPost.EventInfoBean event = new EventPost.EventInfoBean();
        //约伴信息
        if (null != getTimeline().getEvent()) {
            //标题
            event.setTitle(getTimeline().getEvent().getTitle());
            //出发地的城市ID
            event.setDeparture_id(getTimeline().getEvent().getDeparture_id());

            event.setDests(getDest(getTimeline().getEvent().getDests()));
            //出发时间
            if (!TextUtils.isEmpty(getTimeline().getEvent().getBegin_date())) {
                event.setBegin_date(getTimeline().getEvent().getBegin_date());
            }
            if (StringUtil.isNotEmpty(getTimeline().getEvent().getGather_date())) {
                event.setGather_date(getTimeline().getEvent().getGather_date());
            }
            event.setDays(getTimeline().getEvent().getDays());
            event.setMember_limit(Integer.parseInt(getTimeline().getEvent().getMember_limit()));

            event.setFee_type(getTimeline().getEvent().getFee_type());
        }

        param.setEvent_info(event);
        LogUtil.d("EventPost:", new Gson().toJson(param));

        return param;
    }


    private List<EventPost.EventInfoBean.DestsBean> getDest(List<GetDestByKeywordResp> dests) {

        ArrayList<EventPost.EventInfoBean.DestsBean> destList = new ArrayList<>();
        EventPost.EventInfoBean.DestsBean bean;
        if (null != dests) {
            for (GetDestByKeywordResp dest : dests) {
                bean = new EventPost.EventInfoBean.DestsBean();
                bean.setId(dest.getNode_id());
                bean.setName(dest.getNode_name());
                destList.add(bean);
            }

        }


        return destList;
    }


    private List<Integer> getTagId(List<BaseTag> tags) {

        List<Integer> tag_ids = new ArrayList<>();
        if (tags != null) {
            for (BaseTag tag : tags) {
                tag_ids.add(Integer.parseInt(tag.getTag_id()));
            }
        }
        return tag_ids;

    }
}
