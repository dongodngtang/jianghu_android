package net.doyouhike.app.bbs.ui.fragment.action;

import android.os.Bundle;

import net.doyouhike.app.bbs.biz.openapi.presenter.page.action.ActionManageConfirmPage;
import net.doyouhike.app.bbs.biz.openapi.presenter.page.base.IPage;
import net.doyouhike.app.bbs.biz.event.action.ActionManageOperationEvent;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventMembersResp;
import net.doyouhike.app.bbs.ui.adapter.action.ActionManageConfirmAdapter;
import net.doyouhike.app.bbs.ui.util.SnackUtil;

/**
 * 活动成员 未确认
 * Created by zengjiang on 16/7/19.
 */
public class ActionManageConfirmFragment extends BaseActionManageFragment {


    /**
     * 活动管理
     */
    public static final String I_IS_MANAGER="param1";
    /**
     * 当前角色
     */
    public static final String I_CURRENT_ROLE="param2";
    /**
     * 切换标识,切换到已确认还是未确认
     */
    public static final String I_CHECKOUT_CONFIRMED ="param3";
    /**
     * nodeId
     */
    public static final String I_NODE_ID ="param4";
    /**
     * 状态
     */
    public static final String I_STATUS ="param5";


    private String mNodeId;
    private String mRole;
    private String mStatus;


    public ActionManageConfirmFragment() {
    }

    public static ActionManageConfirmFragment getInstance(String nodeId, String status, String role){

        ActionManageConfirmFragment fragment=new ActionManageConfirmFragment();
        Bundle bundle=new Bundle();
        bundle.putString(I_NODE_ID,nodeId);
        bundle.putString(I_STATUS,status);
        bundle.putString(I_CURRENT_ROLE,role);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle=getArguments();

        if (null!=bundle){
            mNodeId=bundle.getString(I_NODE_ID);
            mStatus=bundle.getString(I_STATUS);
            mRole=bundle.getString(I_CURRENT_ROLE);
        }

    }


    @Override
    protected IPage<EventMembersResp.ItemsBean> getPage() {
        return new ActionManageConfirmPage(getContext(),mNodeId,mStatus);
    }



    /**
     * @param response 确认参与人员返回数据
     */
    public void onEventMainThread(ActionManageOperationEvent response) {

        if (response.getCode() == 0) {// 确认成功
            ((ActionManageConfirmAdapter) simpleListHelper.getAdapter()).resetUnConfirmCount(false);
        } else if (response.getCode() == 1401016) {//确认人数已经超过活动人数上限
            SnackUtil.showSnack(getActivity(), "确认人数已经超过活动人数上限");
        } else {
            SnackUtil.showSnack(getActivity(), "确认失败");
        }
        if (null!=simpleListHelper){
            simpleListHelper.getAdapter().notifyDataSetChanged();
        }
    }
}
