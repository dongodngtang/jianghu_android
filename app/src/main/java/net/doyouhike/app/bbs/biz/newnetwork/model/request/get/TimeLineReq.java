package net.doyouhike.app.bbs.biz.newnetwork.model.request.get;


import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.Timeline;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;
import net.doyouhike.app.bbs.biz.openapi.request.BaseListGet;

import java.util.List;

/**
 * 获取用户直播
 * Created by luochangdong on 15-11-30.
 */
@RequestUrlAnnotation(Content.REQ_MY_TIME_LINE)
public class TimeLineReq extends BaseListGet {

    @Override
    protected void setMapValue() {
        super.setMapValue();

        if (!TextUtils.isEmpty(userID)){
            map.put("userID",userID);
        }
    }

    @Override
    public IResponseProcess getProcess() {
        return  new DataJsonParser<List<Timeline>>(new TypeToken<List<Timeline>>() {
        }.getType());
    }

    /**
     * token : 'xxx' //令牌  //查看自己的传toke,n ,查看其他用户不要传token,传userid.
     * page : 1
     * limit : 20
     * userID : xx//查看其他用户的的timeline
     */

    private String userID;

    public void setUserID(String userID) {
        this.userID = userID;
    }


    public String getUserID() {
        return userID;
    }
}
