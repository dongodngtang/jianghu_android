package net.doyouhike.app.bbs.ui.activity.start;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;

import com.github.paolorotolo.appintro.AppIntro;
import com.umeng.analytics.MobclickAgent;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.application.MyApplication;
import net.doyouhike.app.bbs.util.ActivityRouter;

/**
 * 作者：luochangdong on 16/8/25
 * 描述：功能引导页
 */
public class NewGuideActivity extends AppIntro {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(this);

        GuideFragment page1 = GuideFragment.newInstance(R.layout.fragment_guide_1);
        page1.setNextListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               nextButton.callOnClick();
            }
        });
        addSlide(page1);
        addSlide(new GuideEndFragment());

        LinearLayout bottomBar = (LinearLayout) findViewById(com.github.paolorotolo.appintro.R.id.bottom);
        bottomBar.setVisibility(View.GONE);

    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        ActivityRouter.openLoginActivity(this);
        finish();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        ActivityRouter.openLoginActivity(this);
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        MyApplication.getInstance().removeActivity(this);
        super.onDestroy();
    }
}
