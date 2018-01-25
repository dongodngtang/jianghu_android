package net.doyouhike.app.bbs.biz.event;

import com.google.gson.Gson;

import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.SendingTimeline;
import net.doyouhike.app.bbs.ui.adapter.NodeTimelineAdapter;
import net.doyouhike.app.bbs.ui.home.NewLiveAdapter;
import net.doyouhike.app.bbs.util.LogUtil;

/**
 * 功能：发送直播对象
 *
 * @author：曾江 日期：16-3-10.
 */
public class SendLive {
    String liveInfoStr;

    public SendLive(String liveInfoStr) {
        this.liveInfoStr = liveInfoStr;
    }

    public String getLiveInfoStr() {
        return liveInfoStr;
    }

    public void setLiveInfoStr(String liveInfoStr) {
        this.liveInfoStr = liveInfoStr;
    }

    public SendingTimeline getTimeline() {

        SendingTimeline liveInfo = new Gson().fromJson(liveInfoStr,
                SendingTimeline.class);


        if (liveInfo == null) {
            return null;
        }


        liveInfo.setReleaseStatus(Content.WAIT);
        //文本内容
        //标签信息

        //用户信息
        //图片信息


//        timeline.setTime(String.valueOf(liveInfo.getTime()));
        //直播位置



        liveInfo.setMinilog_type(NodeTimelineAdapter.NODE_TEXT_PHOTO);


        LogUtil.d("SendReleaseUtil", "Content.RELEASE_TAG");
        return liveInfo;
    }


}
