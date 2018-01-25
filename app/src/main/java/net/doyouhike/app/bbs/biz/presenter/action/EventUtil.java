package net.doyouhike.app.bbs.biz.presenter.action;

import net.doyouhike.app.bbs.biz.openapi.response.events.EventMembersResp;
import net.doyouhike.app.bbs.biz.openapi.presenter.EventRole;
import net.doyouhike.app.bbs.util.UserInfoUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：luochangdong on 16/9/13
 * 描述：
 */
public class EventUtil {

    /**
     * 获取自己在活动中的角色
     *
     * @param members
     * @return
     */
    public static int getMeRole(List<EventMembersResp.ItemsBean> members) {
        int role = EventRole.VISITOR;
        for (EventMembersResp.ItemsBean itemsBean : members) {
            if (itemsBean.getUser().getUser_id().equals(UserInfoUtil.getInstance().getUserId()))
                role = itemsBean.getRole();
        }

        return role;

    }

    /**
     * 去除发起人
     *
     * @param eventMembers
     * @return
     */
    public static List<EventMembersResp.ItemsBean> getJionMembers(List<EventMembersResp.ItemsBean> eventMembers) {
        List<EventMembersResp.ItemsBean> jions = new ArrayList<>();
        for (EventMembersResp.ItemsBean item : eventMembers) {
            if (item.getRole() == EventRole.CONVENER || item.getRole() == EventRole.UNPASS)
                continue;
            jions.add(item);
        }
        return jions;
    }
}
