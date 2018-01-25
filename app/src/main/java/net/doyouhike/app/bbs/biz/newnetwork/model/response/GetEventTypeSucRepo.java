package net.doyouhike.app.bbs.biz.newnetwork.model.response;

/**
 * 活动类型
 * Created by zaitu on 15-12-1.
 */
public class GetEventTypeSucRepo {

    /**
     * id : 100
     * sort_num : 1
     * type_name : 休闲户外
     */

    private int id;
    private String sort_num;
    private String type_name;

    public GetEventTypeSucRepo() {
    }

    public GetEventTypeSucRepo(int id) {
        this.id = id;
    }



    public void setId(int id) {
        this.id = id;
    }

    public void setSort_num(String sort_num) {
        this.sort_num = sort_num;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public int getId() {
        return id;
    }

    public String getSort_num() {
        return sort_num;
    }

    public String getType_name() {
        return type_name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GetEventTypeSucRepo that = (GetEventTypeSucRepo) o;

        if (id!=that.id) return false;
        return sort_num != null ? sort_num.equals(that.sort_num) : that.sort_num == null;

    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return type_name;
    }
}
