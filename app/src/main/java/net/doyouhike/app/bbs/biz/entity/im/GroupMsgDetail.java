package net.doyouhike.app.bbs.biz.entity.im;

import android.text.TextUtils;

import net.doyouhike.app.bbs.util.UserInfoUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 群发草稿箱
 * Created by zengjiang on 16/8/16.
 */

public class GroupMsgDetail {
    /**
     * 待发送用户的uuid
     */
    private List<String> uuids;
    /**
     * 消息
     */
    private String msg;
    /**
     * 首次发送时间
     */
    private long firstSendTime = 0;
    /**
     * 重试次数
     */
    private int retryCount;

    private String senderId;

    public static GroupMsgDetail getNewMsg(String content, List<String> uuIds) {

        GroupMsgDetail detail = new GroupMsgDetail();
        detail.setMsg(content);
        detail.setUuids(uuIds);
        detail.setRetryCount(0);
        detail.setSenderId(UserInfoUtil.getInstance().getUserId());
        detail.setFirstSendTime(System.currentTimeMillis());

        return detail;
    }

    public List<String> getUuids() {
        return uuids;
    }

    public void setUuids(String strUUids) {


        List<String> uuids = new ArrayList<>();


        if (!TextUtils.isEmpty(strUUids)) {

            String[] arrayUuids = strUUids.split(",");
            uuids.addAll(Arrays.asList(arrayUuids));

        }

        setUuids(uuids);
    }

    public void setUuids(List<String> uuids) {
        this.uuids = uuids;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getFirstSendTime() {
        return firstSendTime;
    }

    public void setFirstSendTime(long firstSendTime) {
        this.firstSendTime = firstSendTime;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    @Override
    public String toString() {
        return "GroupMsgDetail{" +
                "uuids=" + uuids +
                ", msg='" + msg + '\'' +
                '}';
    }
}
