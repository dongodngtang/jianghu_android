package net.doyouhike.app.bbs.ui.widget.common.location;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.ui.release.yueban.PresenterEvent;
import net.doyouhike.app.bbs.util.UIUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import widget.DatePicker;
import widget.TimePicker;

/**
 * Filework:
 * Author: luochangdong
 * Date:16-4-6
 */
public class HourSelectorDialog extends Dialog {
    @InjectView(R.id.tv_comfirm)
    TextView tvComfirm;


    PresenterEvent presenterEvent;
    public int selectYear, selectMonth, selectHour, selectMinute, selectDay;
    @InjectView(R.id.dp_date)
    DatePicker dpDate;
    @InjectView(R.id.tp_time)
    TimePicker tpTime;
    SimpleDateFormat ymdFormat = new SimpleDateFormat(Content.DATE_EVENT_YYYY_MM_DD);

    public HourSelectorDialog(PresenterEvent presenterEvent) {
        super(presenterEvent.getContext(), R.style.dialog_full_screen_buttom_up);
        this.presenterEvent = presenterEvent;
    }

    public void setDefuatTime(Date time) {
        dpDate.setNewDays(time);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_hour_minute_wheel, null);
        ButterKnife.inject(this, contentView);
        setContentView(contentView);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = UIUtils.getValidWidth(getContext()); //设置宽度
        this.setCanceledOnTouchOutside(true);
        lp.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(lp);

        tvComfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presenterEvent.setTvHourMinute(getTime());
                dismiss();
            }
        });
    }


    private String getTime() {

        Date date = dpDate.getCurrentDate();
        String dateStr = ymdFormat.format(date) + " ";
        selectHour = tpTime.getHourOfDay();
        selectMinute = tpTime.getMinute();
        String time = selectHour + ":" + ((selectMinute < 10) ? ("0" + selectMinute) : selectMinute);
        return dateStr + time;
    }
}
