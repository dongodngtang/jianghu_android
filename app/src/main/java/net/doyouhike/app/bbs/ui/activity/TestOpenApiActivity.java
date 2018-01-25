package net.doyouhike.app.bbs.ui.activity;


import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.openapi.presenter.NodesHelper;

import butterknife.InjectView;

/**
 * 作者:luochangdong on 16/6/29 09:42
 * 描述:
 */
public class TestOpenApiActivity extends net.doyouhike.app.bbs.base.activity.BaseActivity {


    @InjectView(R.id.et_username)
    AppCompatEditText etUsername;
    @InjectView(R.id.et_password)
    AppCompatEditText etPassword;
    @InjectView(R.id.btn_login)
    AppCompatButton btnLogin;
    @InjectView(R.id.tv_user)
    AppCompatTextView tvUser;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_open_api;
    }

    @Override
    protected void initViewsAndEvents() {

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NodesHelper.getInstance().timelineFollows(mContext, null);
                NodesHelper.getInstance().timelineHots(mContext, null);
            }
        });


    }


}
