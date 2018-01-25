package net.doyouhike.app.bbs.biz.entity.road;

/**
 * 线路列表 线路类型
 * Created by terry on 5/8/16.
 */
public class RoadListType {

    private String id;

    private String route_type;

    private String type_desc;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private boolean isSelected = false;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoute_type() {
        return route_type;
    }

    public void setRoute_type(String route_type) {
        this.route_type = route_type;
    }

    public String getType_desc() {
        return type_desc;
    }

    public void setType_desc(String type_desc) {
        this.type_desc = type_desc;
    }


    public static RoadListType getDefaultItem(){
        RoadListType type = new RoadListType();
        type.setId("");
        type.setSelected(true);
        type.setRoute_type("全部类型");
        type.setType_desc("全部类型");
        return type;
    }

    @Override
    public String toString() {
        return null==route_type?"":route_type;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoadListType that = (RoadListType) o;

        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
