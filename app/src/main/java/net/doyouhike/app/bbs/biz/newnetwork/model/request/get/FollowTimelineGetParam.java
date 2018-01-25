package net.doyouhike.app.bbs.biz.newnetwork.model.request.get;


import com.google.gson.reflect.TypeToken;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.Timeline;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;

import java.util.List;

/**
 * Filework:
 * Author: luochangdong
 * Date:16-1-11
 */
public class FollowTimelineGetParam extends BaseGetRequest {

    @Override
    protected void setMapValue() {
        if (null != page) {
            map.put("page", page);
        }
        if (null != limit) {
            map.put("limit", limit);
        }
        if (null != token) {
            map.put("token", token);
        }
    }

    @Override
    public String getSubUrl() {
        String userId = SharedPreferencesManager.getUserId() == "-1" ? "0" : SharedPreferencesManager.getUserId();
        return Content.REQ_TIMELINE + "/" + userId;
    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<List<Timeline>>(new TypeToken<List<Timeline>>() {
        }.getType());
    }

    private String page;
    private String limit;
    private String token;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
