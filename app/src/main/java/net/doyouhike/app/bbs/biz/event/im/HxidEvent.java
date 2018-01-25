package net.doyouhike.app.bbs.biz.event.im;

import net.doyouhike.app.bbs.biz.db.green.entities.ChatUserInfo;

/**
 * 获取环信ID后的响应
 * Created by zengjiang on 16/8/5.
 */

public class HxidEvent {
    private boolean success;
    private String msg;
    private int code;
    private ChatUserInfo info;
    private String userId;
    private String action;

    public static HxidEvent getSuccessEvent(ChatUserInfo userInfo,String userId,String action){
        HxidEvent event=new HxidEvent();
        event.setSuccess(true);
        event.setInfo(userInfo);
        event.setUserId(userId);
        event.setAction(action);
        return event;
    }

    public static HxidEvent getFaileEvent(String msg,int code,String userId,String action){
        HxidEvent event=new HxidEvent();
        event.setSuccess(false);
        event.setCode(code);
        event.setMsg(msg);
        event.setUserId(userId);
        event.setAction(action);
        return event;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ChatUserInfo getInfo() {
        return info;
    }

    public void setInfo(ChatUserInfo info) {
        this.info = info;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
