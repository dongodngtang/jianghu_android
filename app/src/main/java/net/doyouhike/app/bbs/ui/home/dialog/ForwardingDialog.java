package net.doyouhike.app.bbs.ui.home.dialog;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.flyco.animation.FlipEnter.FlipTopEnter;
import com.flyco.dialog.utils.CornerUtils;
import com.flyco.dialog.widget.internal.BaseAlertDialog;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.presenter.NodesHelper;
import net.doyouhike.app.bbs.util.StringUtil;

/**
 * 作者:luochangdong on 16/5/25 09:25
 * 描述:
 */
public class ForwardingDialog extends BaseAlertDialog<ForwardingDialog> {

    EditText et_forwarding_words;
    TextView tv_cancel, tv_release;
    String topicId;

    public ForwardingDialog(final Context context, String topicId) {
        super(context);
        this.topicId = topicId;
    }

    @Override
    public View onCreateView() {
        widthScale(0.85f);
        showAnim(new FlipTopEnter());
        setCanceledOnTouchOutside(false);
        // dismissAnim(this, new ZoomOutExit());
        View inflate = View.inflate(mContext, R.layout.forwarding_dialog, null);
        et_forwarding_words = (EditText) inflate.findViewById(R.id.et_forwarding_dialog_words);
        tv_cancel = (TextView) inflate.findViewById(R.id.tv_forwarding_dialog_cancel);
        tv_release = (TextView) inflate.findViewById(R.id.tv_forwarding_dialog_release);


        inflate.setBackgroundDrawable(
                CornerUtils.cornerDrawable(Color.parseColor("#ffffff"), dp2px(5)));
        return inflate;
    }

    private String getContent() {
        return et_forwarding_words.getText().toString();
    }

    IOnResponseListener postListener = new IOnResponseListener() {
        @Override
        public void onSuccess(Response response) {
            StringUtil.showSnack(mContext,R.string.forward_ok);
        }

        @Override
        public void onError(Response response) {
            StringUtil.showSnack(mContext,response.getMsg());
        }
    };

    @Override
    public void setUiBeforShow() {
        float radius = dp2px(mCornerRadius);
        tv_cancel.setBackgroundDrawable(CornerUtils.btnSelector(radius, mBgColor, mBtnPressColor, -2));
        tv_release.setBackgroundDrawable(CornerUtils.btnSelector(radius, mBgColor, mBtnPressColor, -2));
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tv_release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                NodesHelper.getInstance().postNodeForward(mContext,topicId,getContent(),postListener);
            }
        });

    }

}
