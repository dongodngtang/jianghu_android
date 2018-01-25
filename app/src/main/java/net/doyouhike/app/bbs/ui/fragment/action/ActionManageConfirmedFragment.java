package net.doyouhike.app.bbs.ui.fragment.action;

import android.os.Bundle;
import android.widget.LinearLayout;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.db.dao.UserInfoDbUtil;
import net.doyouhike.app.bbs.biz.event.im.HxIdsRequestEvent;
import net.doyouhike.app.bbs.biz.openapi.presenter.page.action.ActionManageConfirmedPage;
import net.doyouhike.app.bbs.biz.openapi.presenter.page.base.IPage;
import net.doyouhike.app.bbs.biz.db.green.entities.ChatUserInfo;
import net.doyouhike.app.bbs.biz.event.action.ActionManageOperationEvent;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.request.events.EventMemberOperationPost;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventMembersResp;
import net.doyouhike.app.bbs.biz.openapi.presenter.EventRole;
import net.doyouhike.app.bbs.ui.adapter.action.ActionManageConfirmedAdapter;
import net.doyouhike.app.bbs.ui.util.SnackUtil;
import net.doyouhike.app.bbs.ui.util.TipUtil;
import net.doyouhike.app.bbs.ui.widget.action.GroupMsgDialog;
import net.doyouhike.app.bbs.util.StatisticsEventUtil;
import net.doyouhike.app.bbs.util.UIUtils;
import net.doyouhike.app.bbs.util.UserInfoUtil;
import net.doyouhike.app.library.ui.uistate.UiState;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 活动成员 确认 列表
 * Created by zengjiang on 16/7/19.
 */
public class ActionManageConfirmedFragment extends BaseActionManageFragment {

    /**
     * 当前角色
     */
    public static final String I_CURRENT_ROLE = "param2";
    /**
     * nodeId
     */
    public static final String I_NODE_ID = "param4";
    /**
     * 状态
     */
    public static final String I_STATUS = "param5";

    /**
     * 群发消息
     */
    @InjectView(R.id.ll_action_manage_comfirmed_group_msg)
    LinearLayout llActionManageComfirmedGroupMsg;

    /**
     * 群发消息
     */
    @OnClick(R.id.ll_action_manage_comfirmed_group_msg)
    public void onGroupMsg() {
        sendGroupMsg();
    }


    private String mNodeId;
    private String mRole;
    private String mStatus;


    public ActionManageConfirmedFragment() {
    }

    public static ActionManageConfirmedFragment getInstance(String nodeId, String status, String role) {

        ActionManageConfirmedFragment fragment = new ActionManageConfirmedFragment();
        Bundle bundle = new Bundle();
        bundle.putString(I_NODE_ID, nodeId);
        bundle.putString(I_STATUS, status);
        bundle.putString(I_CURRENT_ROLE, role);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();

        if (null != bundle) {
            mNodeId = bundle.getString(I_NODE_ID);
            mStatus = bundle.getString(I_STATUS);
            mRole = bundle.getString(I_CURRENT_ROLE);
        }

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_action_manage_comfirmed;
    }

    @Override
    protected IPage<EventMembersResp.ItemsBean> getPage() {
        return new ActionManageConfirmedPage(getContext(), mNodeId, mStatus, mRole) {


            @Override
            public void handleResponse(Response response, boolean isRefresh) {
                super.handleResponse(response, isRefresh);
                //网络获取数据后,显示底部群发按钮
                UIUtils.showView(llActionManageComfirmedGroupMsg, getItems().size() > 1);

            }

            @Override
            public void updateItem(List<EventMembersResp.ItemsBean> item, boolean isRefresh) {
                super.updateItem(item, isRefresh);


                saveUserInfoToDb(item);
            }
        };
    }

    /**
     * 保存用户信息到
     *
     * @param item
     */
    private void saveUserInfoToDb(List<EventMembersResp.ItemsBean> item) {

        if (null != item) {

            List<ChatUserInfo> userInfos = new ArrayList<>();

            List<String> msgBean = new ArrayList<>();

            for (EventMembersResp.ItemsBean members : simpleListHelper.getItems()) {

                EventMembersResp.ItemsBean.UserBean userBean = members.getUser();
                if (userBean != null) {
                    ChatUserInfo userInfo = new ChatUserInfo();

                    userInfo.setUser_id(userBean.getUser_id());
                    userInfo.setNick_name(userBean.getNick_name());
                    userInfo.setAvatar(userBean.getAvatar());
                    userInfo.setSex(userBean.getSex());
                    userInfos.add(userInfo);

                    msgBean.add(userBean.getUser_id());
                }

            }

            //更新或存储用户信息
            UserInfoDbUtil.getInstance().saveUsers(userInfos);

            if (!msgBean.isEmpty()) {
                //请求环信ID
                EventBus.getDefault().post(new HxIdsRequestEvent(msgBean));
            }


        }

    }


    /**
     * @param response 删除活动参与人员返回的数据
     */
    public void onEventMainThread(ActionManageOperationEvent response) {

        updateView(UiState.NORMAL);
        if (response.getCode() == 0) {

            EventMemberOperationPost postParam = (EventMemberOperationPost) response.getExtraTag();

            //删除
            if (postParam.getOperation().equals(EventRole.HANDLE_delete)) {
                EventMembersResp.ItemsBean itemBean = null;
                for (EventMembersResp.ItemsBean item : simpleListHelper.getItems()) {
                    if (item.getUser().getUser_id().equals(postParam.getUser_id())) {
                        itemBean = item;
                        break;
                    }
                }
                if (itemBean != null)
                    simpleListHelper.getItems().remove(itemBean);
                ((ActionManageConfirmedAdapter) simpleListHelper.getAdapter()).resetAttendCount(false);
                UIUtils.showView(llActionManageComfirmedGroupMsg, simpleListHelper.getItems().size() > 1);

            } else if (postParam.getOperation().equals(EventRole.HANDLE_confirm))//确认
            {
                simpleListHelper.getData(true);

                //确认成员统计
                StatisticsEventUtil.ConfirmMember(getActivity());
            }


        } else if (response.getCode() == 1401018) {
            //协作人数不能超过已确认人数三分之一
            SnackUtil.showSnack(getActivity(), "协作人数不能超过已确认人数三分之一");
        } else if (response.getCode() == 1401023) {
            //留守不能超过1人
            TipUtil.alert(getActivity(), "无法修改", "留守不能超过1人");
        } else {
            SnackUtil.showSnack(getActivity(), "操作失败");
        }
        if (null != simpleListHelper)
            simpleListHelper.getAdapter().notifyDataSetChanged();
    }


    private void sendGroupMsg() {
        int attendSize = ((ActionManageConfirmedAdapter) simpleListHelper.getAdapter()).getAttendSize();


        List<String> users = new ArrayList<>();


        for (EventMembersResp.ItemsBean itemsBean : simpleListHelper.getItems()) {

            if (UserInfoUtil.getInstance().isCurrentUser(itemsBean.getUser().getUser_id())) {
                continue;
            }
            users.add(itemsBean.getUser().getUser_id());

        }


        if (((ActionManageConfirmedAdapter) simpleListHelper.getAdapter()).getAttendSize() > 20) {
            //已确认人数超过20时
            TipUtil.alert(getContext(), "无法群发，人数过多");
        } else {
            new GroupMsgDialog(attendSize, users, mContext).show();
        }


    }
}
