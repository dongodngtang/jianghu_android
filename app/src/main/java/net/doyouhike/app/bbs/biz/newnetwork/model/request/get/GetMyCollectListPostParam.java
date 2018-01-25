package net.doyouhike.app.bbs.biz.newnetwork.model.request.get;


import com.google.gson.reflect.TypeToken;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.Timeline;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;
import net.doyouhike.app.bbs.biz.openapi.request.BaseListGet;

import java.util.List;

/**
 * Created by zaitu on 15-12-1.
 * 我的收藏
 */
@RequestUrlAnnotation(Content.REQ_GET_MY_COLLECT_LIST)
public class GetMyCollectListPostParam extends BaseListGet {
    @Override
    public IResponseProcess getProcess() {
        return  new DataJsonParser<List<Timeline>>(new TypeToken<List<Timeline>>() {
        }.getType());
    }
}
