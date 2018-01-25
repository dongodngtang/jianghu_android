package net.doyouhike.app.bbs.biz.newnetwork.model.request.get;


import com.google.gson.reflect.TypeToken;

import net.doyouhike.app.bbs.biz.entity.SelectCityModel;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;

import java.util.List;

/**
 * Created by zaitu on 15-12-1.
 * 获取热门城市
 */
@RequestUrlAnnotation(Content.REQ_GET_HOT_CITIES)
public class GetHotCitiesParam extends BaseGetRequest {
    @Override
    protected void setMapValue() {

    }


    @Override
    public IResponseProcess getProcess() {
        return  new DataJsonParser<List<SelectCityModel>>(new TypeToken<List<SelectCityModel>>() {
        }.getType());
    }
}
