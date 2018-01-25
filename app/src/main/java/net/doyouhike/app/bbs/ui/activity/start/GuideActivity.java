package net.doyouhike.app.bbs.ui.activity.start;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.activity.BaseActivity;
import net.doyouhike.app.bbs.ui.activity.MainActivity;
import net.doyouhike.app.bbs.util.SpTools;
import net.doyouhike.app.bbs.util.StringUtil;

import butterknife.InjectView;

/**
 * @author ZouWenJie 初次进入app的引导页
 */
public class GuideActivity extends BaseActivity implements OnClickListener {

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
    protected void onCreate(Bundle savedInstanceState) {
        isSetSystemBar = false;
        super.onCreate(savedInstanceState);

        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_guides;
    }

    @Override
    protected void initViewsAndEvents() {

        rl_user_guide_goIn.setOnClickListener(this);
        String version = getVersion();
        if (StringUtil.isNotEmpty(version)){

            String  strVersion="V" + version;
            tv_user_guide_version.setText(strVersion);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_user_guide_goIn://直接进入登陆界面
                //保存是否初次进入app的数据
                SpTools.setBoolean(GuideActivity.this, StartActivity.FIRSTIN, false);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }

    private String getVersion() {
        // 获取版本号
        PackageInfo info = null;
        PackageManager manager = this.getPackageManager();
        try {
            info = manager.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (null != info)
            return info.versionName;
        return null;
    }

}
