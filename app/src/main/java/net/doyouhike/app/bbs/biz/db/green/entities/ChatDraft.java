package net.doyouhike.app.bbs.biz.db.green.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者：luochangdong on 16/9/20
 * 描述：
 */
@Entity
public class ChatDraft {

    @Id(autoincrement = true)
    private long id;
    private String uu_ids;
    private String msg;
    private int retry_count;
    private long send_time;
    private String sender_id;//发送者的user_id
    @Generated(hash = 932754725)
    public ChatDraft(long id, String uu_ids, String msg, int retry_count,
            long send_time, String sender_id) {
        this.id = id;
        this.uu_ids = uu_ids;
        this.msg = msg;
        this.retry_count = retry_count;
        this.send_time = send_time;
        this.sender_id = sender_id;
    }
    @Generated(hash = 1239601873)
    public ChatDraft() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getUu_ids() {
        return this.uu_ids;
    }
    public void setUu_ids(String uu_ids) {
        this.uu_ids = uu_ids;
    }
    public String getMsg() {
        return this.msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public int getRetry_count() {
        return this.retry_count;
    }
    public void setRetry_count(int retry_count) {
        this.retry_count = retry_count;
    }
    public long getSend_time() {
        return this.send_time;
    }
    public void setSend_time(long send_time) {
        this.send_time = send_time;
    }
    public String getSender_id() {
        return this.sender_id;
    }
    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

}
