package net.doyouhike.app.bbs.ui.activity.action;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.activity.BaseActivity;
import net.doyouhike.app.bbs.ui.adapter.live.PageFragmentAdapter;
import net.doyouhike.app.bbs.ui.fragment.action.ActionManageConfirmFragment;
import net.doyouhike.app.bbs.ui.fragment.action.ActionManageConfirmedFragment;
import net.doyouhike.app.bbs.ui.fragment.action.ActionMemberConfirmFragment;
import net.doyouhike.app.bbs.ui.fragment.action.ActionMemberConfirmedFragment;
import net.doyouhike.app.library.ui.widgets.XViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class ActionManageActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 活动管理
     */
    public static final String I_IS_MANAGER = "param1";
    /**
     * 当前角色
     */
    public static final String I_CURRENT_ROLE = "param2";
    /**
     * 切换标识,切换到已确认还是未确认
     */
    public static final String I_CHECKOUT_CONFIRMED = "param3";
    /**
     * nodeId
     */
    public static final String I_NODE_ID = "param4";
    /**
     * 状态
     */
    public static final String I_STATUS = "param5";

    /**
     * 已确认
     */
    @InjectView(R.id.tv_activity_action_member_confirmed)
    TextView tvActivityActionMemberConfirmed;
    /**
     * 未确认
     */
    @InjectView(R.id.tv_activity_action_member_confirm)
    TextView tvActivityActionMemberConfirm;
    /**
     * 内容
     */
    @InjectView(R.id.vp_activity_action_member_content)
    XViewPager vpActivityActionMemberContent;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_action_member;
    }

    @Override
    protected void initViewsAndEvents() {
        tvActivityActionMemberConfirm.setOnClickListener(this);
        tvActivityActionMemberConfirmed.setOnClickListener(this);

        //活动id
        String nodeID = getIntent().getStringExtra(I_NODE_ID);
        //活动状态
        String status = getIntent().getStringExtra(I_STATUS);
        //是否活动管理入口
        boolean isManager = getIntent().getBooleanExtra(I_IS_MANAGER, false);
        //当前角色
        String currentRole = getIntent().getStringExtra(I_CURRENT_ROLE);


        List<Fragment> fragments = new ArrayList<>();

        if (isManager) {
            //活动管理

            //已确认 index=0
            fragments.add(ActionManageConfirmedFragment.getInstance(nodeID, status, currentRole));
            //未确认 index=1
            fragments.add(ActionManageConfirmFragment.getInstance(nodeID, status, currentRole));

        } else {
            //查看成员

            //已确认 index=0
            fragments.add(ActionMemberConfirmedFragment.getInstance(nodeID));
            //未确认 index=1
            fragments.add(ActionMemberConfirmFragment.getInstance(nodeID));
        }



        PageFragmentAdapter fragmentAdapter = new PageFragmentAdapter(getSupportFragmentManager(),
                fragments);
        vpActivityActionMemberContent.setAdapter(fragmentAdapter);
        vpActivityActionMemberContent.setEnableScroll(false);

        //显示已确认还是未确认页面
        boolean showConfirmedView=getIntent().getBooleanExtra(I_CHECKOUT_CONFIRMED,true);
        checkoutView(showConfirmedView);

    }

    /**
     * 标题栏左边回退按钮调用
     */
    public void rollBack(View view) {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_activity_action_member_confirmed:
                //已确认
                checkoutView(true);
                break;
            case R.id.tv_activity_action_member_confirm:
                //未确认
                checkoutView(false);
                break;
        }
    }


    /**
     * @param isConfirmed true为管理界面
     */
    private void checkoutView(boolean isConfirmed) {

        tvActivityActionMemberConfirmed.setSelected(isConfirmed);
        tvActivityActionMemberConfirm.setSelected(!isConfirmed);

        vpActivityActionMemberContent.setCurrentItem(isConfirmed ? 0 : 1, false);
    }
}
