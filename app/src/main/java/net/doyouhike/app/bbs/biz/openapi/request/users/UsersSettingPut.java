package net.doyouhike.app.bbs.biz.openapi.request.users;

import com.google.gson.annotations.Expose;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.CacheMode;

import net.doyouhike.app.bbs.biz.event.open.UserSettingEvent;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BasePostRequest;
import net.doyouhike.app.bbs.biz.newnetwork.net.ResponseEventAnnotation;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;

/**
 * 作者：luochangdong on 16/10/9
 * 描述：
 */
@ResponseEventAnnotation(UserSettingEvent.class)
public class UsersSettingPut extends BasePostRequest {

    public final static String FIND_BY_PHONE = "find_by_phone";
    public final static String FIND_BY_NEARLY = "find_by_nearly";
    public final static String PUSH_COMMENT_MSG = "push_comment_msg";
    public final static String PUSH_EVENT_MSG = "push_event_msg";
    public final static String PUSH_LIKE_MSG = "push_like_msg";
    public final static String PUSH_FANS_MSG = "push_fans_msg";

    /**
     * find_by_phone : true
     * find_by_nearly : false
     * push_comment_msg : true
     * push_event_msg : true
     * push_like_msg : true
     * push_fans_msg : true
     */

    private String user_id;

    @Expose
    private Boolean find_by_phone;
    @Expose
    private Boolean find_by_nearly;
    @Expose
    private Boolean push_comment_msg;
    @Expose
    private Boolean push_event_msg;
    @Expose
    private Boolean push_like_msg;
    @Expose
    private Boolean push_fans_msg;

    @Override
    public String getSubUrl() {
        return OpenApiUrl.USERS + user_id + "/user_setting";
    }

    public UsersSettingPut(String user_id) {
        setRequestMethod(RequestMethod.PUT);
        setCacheMode(CacheMode.DEFAULT);
        this.user_id = user_id;
        setExtraInfo(this);
    }

    public Boolean isFind_by_phone() {
        return find_by_phone;
    }

    public void setFind_by_phone(boolean find_by_phone) {
        this.find_by_phone = find_by_phone;
    }

    public Boolean isFind_by_nearly() {
        return find_by_nearly;
    }

    public void setFind_by_nearly(boolean find_by_nearly) {
        this.find_by_nearly = find_by_nearly;
    }

    public Boolean isPush_comment_msg() {
        return push_comment_msg;
    }

    public void setPush_comment_msg(boolean push_comment_msg) {
        this.push_comment_msg = push_comment_msg;
    }

    public Boolean isPush_event_msg() {
        return push_event_msg;
    }

    public void setPush_event_msg(boolean push_event_msg) {
        this.push_event_msg = push_event_msg;
    }

    public Boolean isPush_like_msg() {
        return push_like_msg;
    }

    public void setPush_like_msg(boolean push_like_msg) {
        this.push_like_msg = push_like_msg;
    }

    public Boolean isPush_fans_msg() {
        return push_fans_msg;
    }

    public void setPush_fans_msg(boolean push_fans_msg) {
        this.push_fans_msg = push_fans_msg;
    }
}
