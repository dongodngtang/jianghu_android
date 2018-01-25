package net.doyouhike.app.bbs.biz.newnetwork.model.request.get;


import com.google.gson.reflect.TypeToken;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.EventCats;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;

import java.util.List;

/**
 * Created by luochangdong on 15-12-1.
 * 获取活动目地地列表
 */
@RequestUrlAnnotation(Content.REQ_GET_EVENT_CATS)
public class GetEventCats extends BaseGetRequest {

    @Override
    public IResponseProcess getProcess() {
        return  new DataJsonParser<List<EventCats>>(new TypeToken<List<EventCats>>() {
        }.getType());
    }
    @Override
    protected void setMapValue() {

    }
}
