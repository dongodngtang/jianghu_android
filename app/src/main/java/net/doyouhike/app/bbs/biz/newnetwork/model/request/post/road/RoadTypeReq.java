package net.doyouhike.app.bbs.biz.newnetwork.model.request.post.road;

import com.google.gson.reflect.TypeToken;

import net.doyouhike.app.bbs.biz.entity.road.RoadListType;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BasePostRequest;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;

import java.util.List;

/**
 * 线路类型 查看线路列表  类型
 * Created by terry on 5/8/16.
 */
@RequestUrlAnnotation(Content.REQ_POST_ROAD_LIST_TYPE)
public class RoadTypeReq extends BaseGetRequest {



    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<List<RoadListType>>(new TypeToken<List<RoadListType>>() {
        }.getType());
    }

    @Override
    protected void setMapValue() {

    }
}
