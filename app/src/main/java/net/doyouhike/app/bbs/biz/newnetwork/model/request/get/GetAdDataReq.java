package net.doyouhike.app.bbs.biz.newnetwork.model.request.get;


import com.google.gson.reflect.TypeToken;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.action.AdDataResp;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;
import net.doyouhike.app.bbs.util.UserInfoUtil;

import java.util.List;

/**
 * 功能：获取广告请求
 * 作者：曾江
 * 日期：15-12-30.
 */
@RequestUrlAnnotation(Content.REQ_SERVER_GET_ADDATA)
public class GetAdDataReq extends BaseTokenGetParams {


    @Override
    protected void setMapValue() {
        super.setMapValue();
        putValue("data_type", String.valueOf(data_type));
    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<List<AdDataResp>>(new TypeToken<List<AdDataResp>>() {
        }.getType());
    }


    int data_type = 1;//如果为2则为主引导页，为1或空为活动轮播图 (空为兼容以前旧版)

    public GetAdDataReq getIndexRequest() {
        setToken(UserInfoUtil.getInstance().getToken());
        setData_type(2);//如果为2则为主引导页
        return this;
    }

    public void setData_type(int data_type) {
        this.data_type = data_type;
    }
}
