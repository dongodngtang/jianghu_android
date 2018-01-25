package net.doyouhike.app.bbs.biz.openapi.request.events;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.request.BaseListGet;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventsListResp;

/**
 * 作者：luochangdong on 16/9/9
 * 描述：
 */
@RequestUrlAnnotation(OpenApiUrl.EVENTS)
public class EventsListGet extends BaseListGet {
    /**
     * tag_id : 活动类型id
     * search_type : 1、即将出发 2、最新发布
     * city_id : 出发地的地市id
     * key_word : 搜索关键字 例：'梧桐山'
     * page_index : 当前页
     * page_size : 每页最大记录数
     * date : 出发日期 例：‘2016-05-01’ 或 1（本周末）/ 2（十天内）/3（三十天内）/ 4（今天内）
     * fee_type : none/不限制, AA/费用条件为AA的, free/ 费用条件为免费的
     * has_friend : 0、不做好友过滤 1、好友参加
     */

    private String tag_id;
    private String search_type = "1";
    private String city_id;
    private String keyword;
    private String date;
    private String fee_type;
    private String has_friend = "0";

    @Override
    protected void setMapValue() {
        super.setMapValue();
        if (null != tag_id)
            map.put("tag_id", tag_id);
        if (null != search_type)
            map.put("search_type", search_type);
        if (null != city_id)
            map.put("city_id", city_id);
        if (null != keyword)
            map.put("keyword", keyword);
        if (null != date)
            map.put("date", date);
        if (null != fee_type)
            map.put("fee_type", fee_type);
        if (null != has_friend)
            map.put("has_friend", has_friend);


    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<Response<EventsListResp>>(EventsListResp.class);
    }

    public String getTag_id() {
        return tag_id;
    }

    public void setTag_id(String tag_id) {
        this.tag_id = tag_id;
    }

    public String getSearch_type() {
        return search_type;
    }

    public void setSearch_type(String search_type) {
        this.search_type = search_type;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getKey_word() {
        return keyword;
    }

    public void setKey_word(String key_word) {
        this.keyword = key_word;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public String getHas_friend() {
        return has_friend;
    }

    public void setHas_friend(String has_friend) {
        this.has_friend = has_friend;
    }
}
