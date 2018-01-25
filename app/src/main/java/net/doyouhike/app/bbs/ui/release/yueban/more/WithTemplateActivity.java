package net.doyouhike.app.bbs.ui.release.yueban.more;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.activity.BaseActivity;
import net.doyouhike.app.bbs.ui.widget.common.TitleView;
import net.doyouhike.app.bbs.util.UIUtils;

import butterknife.InjectView;
import butterknife.OnClick;

public class WithTemplateActivity extends BaseActivity implements TitleView.ClickListener {


    public final static String R_TEMPLATE_ATTENTION = "attention";
    public final static String R_TEMPLATE_DISCLAIMER = "disclaimer";

    @InjectView(R.id.title_act_with_more)
    TitleView titleActWithMore;
    @InjectView(R.id.iv_act_with_temp_attention_indicate)
    ImageView ivActWithTempAttentionIndicate;
    @InjectView(R.id.tv_act_with_temp_attention_content)
    TextView tvActWithTempAttentionContent;
    @InjectView(R.id.vi_act_with_temp_attention_content)
    LinearLayout viActWithTempAttentionContent;
    @InjectView(R.id.iv_act_with_temp_disclaimer_indicate)
    ImageView ivActWithTempDisclaimerIndicate;
    @InjectView(R.id.tv_act_with_temp_disclaimer_content)
    TextView tvActWithTempDisclaimerContent;
    @InjectView(R.id.vi_act_with_temp_disclaimer_content)
    LinearLayout viActWithTempDisclaimerContent;
    @InjectView(R.id.vi_act_with_temp_attention_head)
    LinearLayout viActWithTempAttentionHead;
    @InjectView(R.id.vi_act_with_temp_disclaimer_head)
    LinearLayout viActWithTempDisclaimerHead;
    @InjectView(R.id.vi_act_with_temp_attention)
    LinearLayout viActWithTempAttention;
    @InjectView(R.id.vi_act_with_temp_disclaimer)
    LinearLayout viActWithTempDisclaimer;

    public String mTemplateAttention;
    public String mTemplateDisclaimer;


    /**
     * 注意事项
     */
    @OnClick(R.id.vi_act_with_temp_attention)
    public void selectedAttention() {
        boolean previewState=viActWithTempAttention.isSelected();
        setNomalView();
        setAttentionView(previewState);
    }

    /**
     * 免责声明
     */
    @OnClick(R.id.vi_act_with_temp_disclaimer)
    public void selectedDisclaimer() {
        boolean previewState=viActWithTempDisclaimer.isSelected();
        setNomalView();
        setDisclaimerView(previewState);
    }


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_with_template;
    }

    @Override
    protected void initViewsAndEvents() {
        titleActWithMore.setListener(this);
        mTemplateAttention = getString(R.string.with_template_attention);
        mTemplateDisclaimer = getString(R.string.with_template_disclaimer);
    }

    /**
     * 设置注意事项相关视图
     *
     * @param previewState 上次状态,是否被选
     */
    private void setAttentionView(boolean previewState) {

        viActWithTempAttention.setSelected(!previewState);
        UIUtils.showView(viActWithTempAttentionContent, !previewState);
        ivActWithTempAttentionIndicate.setImageResource(previewState ? R.drawable.icon_activity_main_down_3x : R.drawable.icon_activity_main_up_3x);
    }

    /**
     * 设置免责声明相关视图
     *
     * @param previewState 上次状态,是否被选
     */
    private void setDisclaimerView(boolean previewState) {

        viActWithTempDisclaimer.setSelected(!previewState);
        UIUtils.showView(viActWithTempDisclaimerContent, !previewState);
        ivActWithTempDisclaimerIndicate.setImageResource(previewState ? R.drawable.icon_activity_main_down_3x : R.drawable.icon_activity_main_up_3x);

    }


    /**
     * 用于只展开一个,但都是选中状态
     */
    private void setNomalView() {
        setDisclaimerView(true);
        setAttentionView(true);
        UIUtils.showView(viActWithTempDisclaimerContent, false);
        UIUtils.showView(viActWithTempAttentionContent, false);
    }

    @Override
    public void clickLeft() {
        finish();
    }

    @Override
    public void clickRight() {

        Intent intent = new Intent();

        if (viActWithTempAttention.isSelected())
            intent.putExtra(R_TEMPLATE_ATTENTION, mTemplateAttention);
        if (viActWithTempDisclaimer.isSelected())
            intent.putExtra(R_TEMPLATE_DISCLAIMER, mTemplateDisclaimer);

        setResult(RESULT_OK, intent);
        finish();
    }
}
