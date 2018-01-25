package net.doyouhike.app.bbs.biz.newnetwork.model.request.get;


import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;

import net.doyouhike.app.bbs.biz.entity.ActionInfo;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;
import net.doyouhike.app.bbs.biz.openapi.request.BaseListGet;

import java.util.List;

/**
 * 查询用户活动
 * Created by luochangdong on 15-11-30.
 */
@RequestUrlAnnotation(Content.REQ_USER_EVENTS)
public class UserEventsReq extends BaseListGet {

    public static final int TYPE_QUERY_ALL = 1;
    public static final int TYPE_QUERY_SPINSOR = 2;
    public static final int TYPE_QUERY_JOIN = 3;

    @Override
    public IResponseProcess getProcess() {
        return  new DataJsonParser<List<ActionInfo>>(new TypeToken<List<ActionInfo>>() {
        }.getType());
    }

    @Override
    protected void setMapValue() {
        super.setMapValue();
        if (!TextUtils.isEmpty(userID)){
            map.put("userID",userID);
        }
        map.put("queryType",queryType+"");
    }



    /**
     * token : xxxx //可选
     * page : 1
     * limit : 20
     * userID : 12345
     * queryType : 1
     */



    @Expose
    private String userID;
    @Expose
    private int queryType=TYPE_QUERY_ALL;

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setQueryType(int queryType) {
        this.queryType = queryType;
    }

    public String getUserID() {
        return userID;
    }

    public int getQueryType() {
        return queryType;
    }
}
