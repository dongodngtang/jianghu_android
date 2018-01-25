package net.doyouhike.app.bbs.biz.entity.common;

/**
 * 分页返回参数
 * Created by terry on 5/8/16.
 */
public class PageOutPut {

    private String page_index;

    private String page_size;

    private String page_count;

    private String total_records;



    public String getPage_count() {
        return page_count;
    }

    public void setPage_count(String page_count) {
        this.page_count = page_count;
    }

    public String getPage_index() {
        return page_index;
    }

    public void setPage_index(String page_index) {
        this.page_index = page_index;
    }

    public String getPage_size() {
        return page_size;
    }

    public void setPage_size(String page_size) {
        this.page_size = page_size;
    }

    public String getTotal_records() {
        return total_records;
    }

    public void setTotal_records(String total_records) {
        this.total_records = total_records;
    }




}
