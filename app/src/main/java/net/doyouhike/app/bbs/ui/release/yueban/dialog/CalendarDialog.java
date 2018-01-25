package net.doyouhike.app.bbs.ui.release.yueban.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.squareup.timessquare.CalendarCellDecorator;
import com.squareup.timessquare.CalendarPickerView;
import com.squareup.timessquare.DefaultDayViewAdapter;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.ui.release.yueban.PresenterEvent;
import net.doyouhike.app.bbs.util.LogUtil;
import net.doyouhike.app.bbs.util.UIUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Filework:
 * Author: luochangdong
 * Date:16-3-14
 */
public class CalendarDialog extends Dialog {

    private final static String TAG = CalendarDialog.class.getSimpleName();
    @InjectView(R.id.calendar_view)
    CalendarPickerView calendar;
    @InjectView(R.id.tv_calendar_select)
    TextView tvCalendarSelect;
    @InjectView(R.id.tv_complete)
    TextView tvComplete;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
    private PresenterEvent presenterEvent;

    public CalendarDialog(PresenterEvent presenterEvent) {
        super(presenterEvent.getContext(), R.style.dialog_full_screen_buttom_up);
        this.presenterEvent = presenterEvent;


    }


    private void setTvComplete() {
        tvComplete.setEnabled(true);
        if (calendar.getSelectedDates().size() == 1) {

            tvCalendarSelect.setText("点按结束日期或完成");
        } else {
            if (calendar.getSelectedDates().size() > 1) {
                StringBuilder sb = new StringBuilder();
                String startDate = simpleDateFormat.format(calendar.getSelectedDates().get(0));
                String endDate = simpleDateFormat.format(calendar.getSelectedDates().get(calendar.getSelectedDates().size() - 1));
                sb.append(startDate)
                        .append("-")
                        .append(endDate)
                        .append(" 共")
                        .append(calendar.getSelectedDates().size())
                        .append("天");
                tvCalendarSelect.setText(sb.toString());

            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_calendar, null);
        ButterKnife.inject(this, contentView);
        setContentView(contentView);

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = UIUtils.getValidWidth(getContext()); //设置宽度
        this.setCanceledOnTouchOutside(true);
        lp.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(lp);

        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        calendar.setCustomDayView(new DefaultDayViewAdapter());

        calendar.setDecorators(Collections.<CalendarCellDecorator>emptyList());
        calendar.init(new Date(), nextYear.getTime()) //
                .inMode(CalendarPickerView.SelectionMode.RANGE);

        initEvent(calendar);
        tvComplete.setEnabled(false);

    }

    private void initEvent(final CalendarPickerView calendar) {
        calendar.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                LogUtil.d(TAG, "onDateSelected" + simpleDateFormat.format(date));
                LogUtil.d(TAG, "size" + calendar.getSelectedDates().size());
                setTvComplete();
//

            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });

        tvComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calendar.getSelectedDates().size() > 0) {
                    Date begin = calendar.getSelectedDates().get(0);
                    Date end = calendar.getSelectedDates().get(calendar.getSelectedDates().size() - 1);
                    String days = calendar.getSelectedDates().size() + "";
                    presenterEvent.setTvCalendarDate(begin, end, days);
                }
                dismiss();

            }
        });

    }


}
