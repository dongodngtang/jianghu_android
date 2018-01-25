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
 * Created by luochangdong on 15-11-30.
 */
public class HotTimelineGetParam extends BaseGetRequest {
    /**
     * cityId : //城市ID
     * deviceId : //设备ID
     * page : //页码
     * limit : //获得的最新一条的latest_id
     */

    private String cityId;
    private String deviceId;
    private String page;
    private String limit;
    private String token;
    @Override
    protected void setMapValue() {

        if (null != cityId) {
            map.put("cityId", cityId);
        }
        if (null != deviceId) {
            map.put("deviceId", deviceId);
        }
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
        return Content.REQ_HOT_TIME_LINE + "/" + userId;
    }

    @Override
    public IResponseProcess getProcess() {
        return  new DataJsonParser<List<Timeline>>(new TypeToken<List<Timeline>>() {
        }.getType());
    }


    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getCityId() {
        return cityId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getPage() {
        return page;
    }

    public String getLimit() {
        return limit;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
