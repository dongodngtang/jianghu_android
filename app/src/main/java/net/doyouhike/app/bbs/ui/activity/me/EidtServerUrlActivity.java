package net.doyouhike.app.bbs.ui.activity.me;

import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import net.doyouhike.app.bbs.BuildConfig;
import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.activity.BaseActivity;
import net.doyouhike.app.bbs.ui.widget.common.TitleView;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;
import net.doyouhike.app.bbs.util.StringUtil;

import butterknife.InjectView;

/**
 * 作者:luochangdong on 16/5/3 10:42
 * 描述:
 */
public class EidtServerUrlActivity extends BaseActivity {

    @InjectView(R.id.edit_dev_server_url)
    EditText editDevServerUrl;
    @InjectView(R.id.tv_dev_server_url_comfirl)
    TextView tvDevServerUrlComfirl;
    @InjectView(R.id.title_edit_server)
    TitleView titleEditServer;

    @InjectView(R.id.gr_server_url_list)
    RadioGroup gr_server_url_list;

    private String serverEnvirment = BuildConfig.ENV_TYPE;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_edit_server_url;
    }

    @Override
    protected void initViewsAndEvents() {

        String serverUrl = SharedPreferencesManager.getServerUrl();
        editDevServerUrl.setText(serverUrl);
        tvDevServerUrlComfirl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editDevServerUrl.getText().toString();
                if (StringUtil.isEmpty(text)) {
                    showToast("内容不能为空");
                } else {
                    SharedPreferencesManager.setDevServerUrl(text);
                    SharedPreferencesManager.setServerEnvironment(serverEnvirment);
                    finish();
                }
            }
        });

        titleEditServer.setListener(new TitleView.ClickListener() {
            @Override
            public void clickLeft() {
                finish();
            }

            @Override
            public void clickRight() {

            }
        });

        gr_server_url_list.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //开发环境
                if(checkedId == R.id.rb_server_url_dev) {
                    editDevServerUrl.setText("http://192.168.1.231:9090/v20/");
                    serverEnvirment = "dev";
                }
                //测试环境
                else if(checkedId == R.id.rb_server_url_testing){
                    editDevServerUrl.setText("http://openapi.test.zaitu.cn/v20/");
                    serverEnvirment = "testing";
                }
                //正式环境
                else if(checkedId == R.id.rb_server_url_env) {
                    editDevServerUrl.setText("http://openapi.zaitu.cn/v20/");
                    serverEnvirment = "production";
                }

            }
        });

    }

}
