package net.doyouhike.app.bbs.biz.openapi.request.users;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.openapi.request.BaseListGet;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventsListResp;

/**
 * 作者：luochangdong on 16/10/9
 * 描述：
 */
public class UserEventsGet extends BaseListGet {

    public static String getParticipateType(int index) {
        String type = "all";
        switch (index) {
            case 0:
                type = "all";
                break;
            case 1:
                type = "creator";
                break;
            case 2:
                type = "joined";
                break;

        }
        return type;
    }

    @Override
    protected void setMapValue() {
        super.setMapValue();
        if (participate_type != null)
            map.put("participate_type", participate_type);
    }

    private String user_id;
    /**
     * 参与的类型(all/关于我所有的活动，
     * joined/我加入的活动，即除了我是活动创建者的活动，
     * creator/我是创建者的活动，
     * collaborator/我是协作的活动，
     * accountant/我是财务的活动，
     * ordinary/我是普通成员的活动，
     * guard/我是后守人员的活动)
     */
    private String participate_type;

    public String getParticipate_type() {
        return participate_type;
    }

    public void setParticipate_type(String participate_type) {
        this.participate_type = participate_type;
    }

    public UserEventsGet(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String getSubUrl() {
        return OpenApiUrl.USER_EVENTS + user_id;
    }


    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<EventsListResp>(EventsListResp.class);
    }
}
