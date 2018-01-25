package net.doyouhike.app.bbs.biz.newnetwork.model.bean;

/**
 * 功能：目的地子菜单
 * 作者：曾江
 * 日期：15-12-29.
 */
public class Cat {

    private int fid;
    private int cat_id;
    private String cat_name;



    public void setFid(int fid) {
        this.fid = fid;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public int getFid() {
        return fid;
    }

    public int getCat_id() {
        return cat_id;
    }

    public String getCat_name() {
        return cat_name;
    }
}
