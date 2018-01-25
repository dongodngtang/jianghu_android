package net.doyouhike.app.bbs.biz.event;

import com.google.gson.Gson;

import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.SendingTimeline;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.NodeTimeline;
import net.doyouhike.app.bbs.ui.adapter.NodeTimelineAdapter;
import net.doyouhike.app.bbs.ui.home.NewLiveAdapter;

/**
 * 功能：发送相关事件,如发送直播.发送活动
 *
 * @author：曾江 日期：16-3-10.
 */
public class SendEvent {
    String liveInfoStr;

    public SendEvent(String liveInfoStr) {
        this.liveInfoStr = liveInfoStr;
    }

    public String getLiveInfoStr() {
        return liveInfoStr;
    }

    public void setLiveInfoStr(String liveInfoStr) {
        this.liveInfoStr = liveInfoStr;
    }


    public SendingTimeline getTimeline() {

        SendingTimeline timeline = new Gson().fromJson(liveInfoStr,
                SendingTimeline.class);

        //将Node_id清除并转移,防止与直播列表数据混淆
        timeline.setTempNoteId(timeline.getNode_id());
        timeline.setNode_id(null);

        timeline.setMinilog_type(NodeTimelineAdapter.NODE_EVENT);
        timeline.setSendingId(SendingTimeline.ID_SENDING_EVENT);
        timeline.setReleaseStatus(Content.WAIT);

        return timeline;
    }


}
