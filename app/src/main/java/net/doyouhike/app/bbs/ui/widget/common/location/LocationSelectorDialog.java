package net.doyouhike.app.bbs.ui.widget.common.location;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.entity.CitySelectInfo;
import net.doyouhike.app.bbs.ui.release.yueban.EditEventActivity;
import net.doyouhike.app.bbs.util.LogUtil;
import net.doyouhike.app.bbs.util.UIUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;

/**
 * Filework:
 * Author: luochangdong
 * Date:16-3-11
 */
public class LocationSelectorDialog extends Dialog {


    private final static String TAG = LocationSelectorDialog.class.getSimpleName();
    @InjectView(R.id.tv_comfirm)
    TextView tvComfirm;
    @InjectView(R.id.aw_location_selector_wheel)
    AreasWheel areasWheel;

    public LocationSelectorDialog(EditEventActivity context) {
        super(context, R.style.dialog_full_screen_buttom_up);
    }

    public LocationSelectorDialog(Context context) {
        super(context, R.style.dialog_full_screen_buttom_up);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_location_select, null);
        ButterKnife.inject(this, contentView);
        setContentView(contentView);
        this.setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = UIUtils.getValidWidth(getContext()); //设置宽度
        lp.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(lp);
        tvComfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.d(TAG, areasWheel.getArea() + "getProvinceId:" + areasWheel.getProvinceId() + "getCityId" + areasWheel.getCityId());

                CitySelectInfo citySelectInfo = new CitySelectInfo(
                        areasWheel.getProvinceId(), areasWheel.getProvinceNm(),
                        areasWheel.getCityId(), areasWheel.getCityNm());
                EventBus.getDefault().post(citySelectInfo);


                dismiss();
            }
        });

    }


}
