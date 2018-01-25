package net.doyouhike.app.bbs.biz.newnetwork.model.request.get;


import com.google.gson.reflect.TypeToken;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.GetEventTypeSucRepo;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;

import java.util.List;

/**
 * Created by zaitu on 15-12-1.
 * 获取活动类型列表
 */
@RequestUrlAnnotation(Content.REQ_GET_EVENT_TYPES)
public class GetEventTypesParam extends BaseGetRequest {
    @Override
    protected void setMapValue() {

    }


    @Override
    public IResponseProcess getProcess() {
        return  new DataJsonParser<List<GetEventTypeSucRepo>>(new TypeToken<List<GetEventTypeSucRepo>>() {
        }.getType());
    }
}
