package net.doyouhike.app.bbs.biz.openapi.request;


import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;

/**
 * Created by zaitu on 15-11-26.
 */
public class BaseListGet extends BaseGetRequest {


    /**
     * 注释:
     * token: 'xxx'//令牌 （必选）
     * page_index: 1 //页码 默认1
     * page_size: xxx //返回条数,默认20,最大50
     */

    private String token;
    private int page_index = 1;
    private int page_size = 20;
    private String last_id;//当前浏览的最后一个node_id

    @Override
    protected void setMapValue() {

        if (page_index != 0)
            map.put("page_index", page_index + "");
        if (page_size != 0)
            map.put("page_size", page_size + "");

        if (page_index == 1) {
            last_id = null;
        }
        if (last_id != null) {
            map.put("last_id", last_id);
            map.remove("page_index");
        }


    }

    public String getLast_id() {
        return last_id;
    }

    public void setLast_id(String last_id) {
        this.last_id = last_id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setPage_index(int page_index) {
        this.page_index = page_index;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public String getToken() {
        return token;
    }

    public int getPage_index() {
        return page_index;
    }

    public int getPage_size() {
        return page_size;
    }
}
