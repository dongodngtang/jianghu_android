package net.doyouhike.app.bbs.ui.release.yueban.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.util.BaseDialog;
import net.doyouhike.app.bbs.ui.widget.common.location.NumPickerWheel;
import net.doyouhike.app.bbs.util.UIUtils;

import java.util.ArrayList;

import butterknife.InjectView;

/**
 * 作者:luochangdong on 16/4/18 13:43
 * 描述:
 */
public class LimitDialog extends BaseDialog {
    @InjectView(R.id.tv_calendar_select)
    TextView tvCalendarSelect;
    @InjectView(R.id.tv_complete)
    TextView tvComplete;


    CallbackLimit callbackLimit;

    ArrayList<String> numDatas;
    @InjectView(R.id.wheel_num_picker)
    NumPickerWheel wheelNumPicker;


    public LimitDialog(Context context) {
        super(context, R.style.dialog_full_screen_buttom_up);
    }

    public void setCallbackLimit(CallbackLimit callbackLimit) {
        this.callbackLimit = callbackLimit;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.dialog_number_limit;
    }

    @Override
    protected void initViewsAndEvents() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = UIUtils.getValidWidth(getContext()); //设置宽度
        this.setCanceledOnTouchOutside(true);
        lp.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(lp);

        numDatas = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            numDatas.add(2 + i + "");
        }

        tvComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callbackLimit != null)
                    callbackLimit.setLimit(wheelNumPicker.getCurrentValue());
                dismiss();
            }
        });
    }

    @Override
    protected void onFirstUserVisible() {

    }

    public interface CallbackLimit {
        void setLimit(String limit);
    }

}
