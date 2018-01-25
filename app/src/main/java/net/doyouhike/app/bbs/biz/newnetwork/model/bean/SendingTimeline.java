package net.doyouhike.app.bbs.biz.newnetwork.model.bean;


import net.doyouhike.app.bbs.biz.openapi.response.nodes.NodeTimeline;

/**
 * 功能：发送中的直播
 *
 * @author：曾江 日期：16-3-14.
 */
public class SendingTimeline extends NodeTimeline.ItemsBean.NodeBean {
    private static final String TAG="SendingTimeline";
    public static final int ID_SENDING_LIVE=1;
    public static final int ID_SENDING_EVENT=2;
    public static final int ID_SENDING_SUCCESS=0;

    private int sendingId=ID_SENDING_LIVE;
    private String tempNoteId;

    public int getSendingId() {
        return sendingId;
    }

    public void setSendingId(int sendingId) {
        this.sendingId = sendingId;
    }

    public String getTempNoteId() {
        return tempNoteId;
    }

    public void setTempNoteId(String tempNoteId) {
        this.tempNoteId = tempNoteId;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SendingTimeline that = (SendingTimeline) o;

        return sendingId == that.sendingId;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + sendingId;
        return result;
    }
}
