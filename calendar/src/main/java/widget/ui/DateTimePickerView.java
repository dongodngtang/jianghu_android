package widget.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.timessquare.R;

import java.util.Calendar;

import widget.DatePicker;
import widget.TimePicker;

public class DateTimePickerView extends RelativeLayout {

	private View view_Parent;
	private TextView tv_datetime;
	private Context mcontext;

	private Calendar calendar;
	private DatePicker dp_test;
	private TimePicker tp_test;
	private TextView tv_ok, tv_cancel; // ȷ����ȡ��button	
	private PopupWindow pw;
	private String selectDate, selectTime;
	// ѡ��ʱ���뵱ǰʱ�䣬�����ж��û�ѡ����Ƿ�����ǰ��ʱ��
	public int currentYear,currentMonth,currentHour, currentMinute, currentDay,
	selectYear,selectMonth,selectHour,selectMinute, selectDay;	
	
	public void setParentView(View view) {
		view_Parent=view;
	}

	private void initView(Context context) {
		View.inflate(context, R.layout.datetimepicker_view, this);
		this.mcontext=context;
		tv_datetime = (TextView) this.findViewById(R.id.tv_datetimepicker);
		initDateTime();
		tv_datetime.setText(selectDate+" "+selectTime);		
		
		tv_datetime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				View view = View.inflate(mcontext,R.layout.dialog_select_time, null);				
				dp_test = (DatePicker) view.findViewById(R.id.dp_test);
				tp_test = (TimePicker) view.findViewById(R.id.tp_test);
				tv_ok = (TextView) view.findViewById(R.id.tv_ok);
				tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
				dp_test.setOnChangeListener(dp_onchanghelistener);
				tp_test.setOnChangeListener(tp_onchanghelistener);
				pw = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
//				pw.setOutsideTouchable(true);
//				pw.setBackgroundDrawable(new BitmapDrawable());
				
				pw.showAtLocation(view_Parent, 0, 0,  Gravity.END);
				
				tv_ok.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						tv_datetime.setText(selectDate+selectTime);						
						pw.dismiss();
					}
				});
				
				tv_cancel.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						pw.dismiss();
					}
				});
				
			}
		});

	}
	
	private void initDateTime() {
		calendar = Calendar.getInstance();
		currentYear=calendar.get(Calendar.YEAR);
		currentMonth=calendar.get(Calendar.MONTH);
		selectDate = calendar.get(Calendar.YEAR) + "年"
				+ calendar.get(Calendar.MONTH) + "月"
				+ calendar.get(Calendar.DAY_OF_MONTH) + "日"
				+ DatePicker.getDayOfWeekCN(calendar.get(Calendar.DAY_OF_WEEK));
		// ѡ��ʱ���뵱ǰʱ��ĳ�ʼ���������ж��û�ѡ����Ƿ�����ǰ��ʱ�䣬����ǣ�����toss��ʾ����ѡ���ȥ��ʱ��
		selectDay = currentDay = calendar.get(Calendar.DAY_OF_MONTH);
		selectMinute = currentMinute = calendar.get(Calendar.MINUTE);
		selectHour = currentHour = calendar.get(Calendar.HOUR_OF_DAY);

		selectTime = currentHour
				+ "点"
				+ ((currentMinute < 10) ? ("0" + currentMinute) : currentMinute)
				+ "分";
	}

	

	// listeners
	DatePicker.OnChangeListener dp_onchanghelistener = new DatePicker.OnChangeListener() {
		@Override
		public void onChange(int year, int month, int day, int day_of_week) {
			selectYear=year;
			selectMonth=month;
			selectDay = day;
			selectDate = year + "年" + month + "月" + day + "日"
					+ DatePicker.getDayOfWeekCN(day_of_week);
		}
	};
	TimePicker.OnChangeListener tp_onchanghelistener = new TimePicker.OnChangeListener() {
		@Override
		public void onChange(int hour, int minute) {
			selectTime = hour + "时" + ((minute < 10) ? ("0" + minute) : minute)
					+ "分";
			selectHour = hour;
			selectMinute = minute;
		}
	};

	

	public DateTimePickerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	public DateTimePickerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public DateTimePickerView(Context context) {
		super(context);
		initView(context);
	}

}
