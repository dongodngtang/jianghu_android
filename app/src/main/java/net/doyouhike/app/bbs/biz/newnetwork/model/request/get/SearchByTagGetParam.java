package net.doyouhike.app.bbs.biz.newnetwork.model.request.get;


import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.Timeline;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;
import net.doyouhike.app.bbs.biz.openapi.request.BaseListGet;

import java.util.List;

/**
 * Filework:根据标签查询话题
 * Author: luochangdong
 * Date:16-1-29
 *
 */
@RequestUrlAnnotation(Content.REQ_POST_SEARCH_BY_TAG)
public class SearchByTagGetParam extends BaseListGet {

    @Override
    protected void setMapValue() {
        super.setMapValue();

        map.put("tagID",tagID+"");
        map.put("lastID", lastID + "");

    }

    @Override
    public IResponseProcess getProcess() {
        return  new DataJsonParser<List<Timeline>>(new TypeToken<List<Timeline>>() {
        }.getType());
    }
    /**
     * token : xxx//可选，有token则传递
     * tagID : 1
     * page : 1
     * limit : 20
     * lastID : 0
     */
    @Expose
    private int tagID;
    @Expose
    private int lastID;

    public void setTagID(int tagID) {
        this.tagID = tagID;
    }

    public void setLastID(int lastID) {
        this.lastID = lastID;
    }

    public int getTagID() {
        return tagID;
    }
    public int getLastID() {
        return lastID;
    }
}
