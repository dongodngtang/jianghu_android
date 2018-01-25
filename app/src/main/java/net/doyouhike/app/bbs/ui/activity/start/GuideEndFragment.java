package net.doyouhike.app.bbs.ui.activity.start;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.fragment.BaseFragment;
import net.doyouhike.app.bbs.ui.activity.MainActivity;
import net.doyouhike.app.bbs.util.CommonUtil;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;
import net.doyouhike.app.bbs.util.StringUtil;

import butterknife.InjectView;

/**
 * 作者：luochangdong on 16/8/25
 * 描述：
 */
public class GuideEndFragment extends BaseFragment implements View.OnClickListener {
    /**
     * 版本号
     */
    @InjectView(R.id.tv_user_guide_version)
    TextView tv_user_guide_version;

    /**
     * 进入磨房 按钮
     */
    @InjectView(R.id.rl_user_guide_goIn)
    RelativeLayout rl_user_guide_goIn;

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void initViewsAndEvents() {
        rl_user_guide_goIn.setOnClickListener(this);
        String version = CommonUtil.getAppVersionName(mContext);
        if (StringUtil.isNotEmpty(version)){
            String  strVersion="V" + version;
            tv_user_guide_version.setText(strVersion);
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_guide_2;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_user_guide_goIn://直接进入登陆界面
                //保存是否初次进入app的数据
                SharedPreferencesManager.setShowGuidePage(false);//标记已显示

                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            default:
                break;
        }
    }
}
