package net.doyouhike.app.bbs.ui.fragment.action;

import android.os.Bundle;

import net.doyouhike.app.bbs.biz.openapi.presenter.page.action.ActionMemberConfirmPage;
import net.doyouhike.app.bbs.biz.openapi.presenter.page.base.IPage;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventMembersResp;

/**
 * 活动成员 未确认
 * Created by zengjiang on 16/7/19.
 */
public class ActionMemberConfirmFragment extends BaseActionManageFragment {

    /**
     * nodeId
     */
    public static final String I_NODE_ID ="param4";


    private String mNodeId;


    public ActionMemberConfirmFragment() {
    }

    public static ActionMemberConfirmFragment getInstance(String nodeId){

        ActionMemberConfirmFragment fragment=new ActionMemberConfirmFragment();
        Bundle bundle=new Bundle();
        bundle.putString(I_NODE_ID,nodeId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle=getArguments();

        if (null!=bundle){
            mNodeId=bundle.getString(I_NODE_ID);
        }

    }


    @Override
    protected IPage<EventMembersResp.ItemsBean> getPage() {
        return new ActionMemberConfirmPage(getContext(),mNodeId);
    }
}
