package net.doyouhike.app.bbs.ui.release.yueban;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.activity.BaseActivity;
import net.doyouhike.app.bbs.biz.entity.CitySelectInfo;
import net.doyouhike.app.bbs.biz.event.EventReleaseLocationEvent;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.GetDestByKeywordResp;
import net.doyouhike.app.bbs.biz.nohttp.CallServer;
import net.doyouhike.app.bbs.ui.release.yueban.destination.SelectDestActivity;

import butterknife.InjectView;

/**
 * Filework:
 * Author: luochangdong
 * Date:16-3-11
 */
public class EditEventActivity extends BaseActivity implements View.OnClickListener {

    public static final String TAG = EditEventActivity.class.getSimpleName();
    public static final int REQUEST_CODE_MORE = 25;
    Bundle extras;
    PresenterEvent mIPresenterEvent;

    @InjectView(R.id.iv_aa_money)
    ImageView ivAaMoney;
    @InjectView(R.id.tv_aa_money)
    TextView tvAaMoney;
    @InjectView(R.id.iv_free_money)
    ImageView ivFreeMoney;
    @InjectView(R.id.tv_free_money)
    TextView tvFreeMoney;
    @InjectView(R.id.rl_aa_money)
    RelativeLayout rlAaMoney;
    @InjectView(R.id.rl_free_money)
    RelativeLayout rlFreeMoney;
    @InjectView(R.id.rl_meetting_time)
    RelativeLayout rlMeettingTime;
    @InjectView(R.id.tv_hour_minute)
    TextView tvHourMinute;
    @InjectView(R.id.rl_delete_event)
    RelativeLayout rlDeleteEvent;
    @InjectView(R.id.tv_send)
    TextView tvSend;
    @InjectView(R.id.tv_cancel)
    TextView tvCancel;
    @InjectView(R.id.iv_cat)
    ImageView ivCat;
    @InjectView(R.id.tv_destination)
    TextView tvDestination;
    @InjectView(R.id.iv_close)
    ImageView ivClose;
    @InjectView(R.id.ll_dest_one)
    LinearLayout llDestOne;
    @InjectView(R.id.tv_calendar_date)
    TextView tvCalendarDate;
    @InjectView(R.id.tv_location_start_selected)
    TextView tvLocationStartSelected;
    @InjectView(R.id.rl_location_start)
    RelativeLayout rlLocationStart;
    @InjectView(R.id.iv_add_location)
    ImageView ivAddLocation;
    @InjectView(R.id.rl_location_end)
    RelativeLayout rlLocationEnd;
    @InjectView(R.id.rl_location_add)
    GridLayout rlLocationAdd;
    @InjectView(R.id.rl_days)
    RelativeLayout rlDays;
    @InjectView(R.id.tv_tag_type)
    TextView tvTagType;
    @InjectView(R.id.rl_types)
    RelativeLayout rlTypes;
    @InjectView(R.id.vi_act_with_more)
    RelativeLayout viActWithMore;
    @InjectView(R.id.tv_fee_ps)
    TextView tvFeePs;
    @InjectView(R.id.tv_aa_ps)
    TextView tvAaPs;
    @InjectView(R.id.sv_edit_event)
    ScrollView svEditEvent;
    @InjectView(R.id.edit_event_container)
    FrameLayout editEventContainer;
    @InjectView(R.id.back)
    ImageView back;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_with_release;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Override
    protected void initViewsAndEvents() {
        mIPresenterEvent = new PresenterEvent(this);
        tvCancel.setOnClickListener(this);
        rlLocationStart.setOnClickListener(this);
        rlLocationEnd.setOnClickListener(this);
        rlDays.setOnClickListener(this);
        rlTypes.setOnClickListener(this);
        tvSend.setOnClickListener(this);
        rlFreeMoney.setOnClickListener(this);
        rlAaMoney.setOnClickListener(this);
        rlMeettingTime.setOnClickListener(this);
        rlDeleteEvent.setOnClickListener(this);
        viActWithMore.setOnClickListener(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        this.extras = extras;

    }

    @Override
    protected void onFirstUserVisible() {
        mIPresenterEvent.getBundleExtras(extras);
        mIPresenterEvent.initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_location_start:
                //出发地
                mIPresenterEvent.startLocation();
                break;
            case R.id.rl_location_end:
                //目的地
                readyGo(SelectDestActivity.class);
                break;
            case R.id.rl_days:
                //行程日期
                mIPresenterEvent.travelDay();
                break;
            case R.id.rl_types:
                //类型
                mIPresenterEvent.travelType();
                break;
            case R.id.tv_cancel:
                //取消
                mIPresenterEvent.clickCancel();
                break;
            case R.id.tv_send:
                //发送

                mIPresenterEvent.clickSend();
                break;
            case R.id.rl_aa_money:
                //AA活动
                mIPresenterEvent.clickAAMoney();

                break;
            case R.id.rl_free_money:
                //免费活动
                mIPresenterEvent.clickFree();
                break;
            case R.id.rl_meetting_time:
                //集合时间
                mIPresenterEvent.clickMeettingTime();
                break;
            case R.id.rl_delete_event:
                //删除约伴
                mIPresenterEvent.clickDeleteEvent();

                break;
            case R.id.vi_act_with_more:
                mIPresenterEvent.clickEventMore();
                break;
            default:
                break;

        }
    }


    public void toSetResult(int resultCode, Intent data) {
        setResult(resultCode, data);
        finish();
    }

    public void dialogMatch(Dialog dialog) {
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = display.getWidth(); //设置宽度
        dialog.getWindow().setAttributes(lp);
    }

    public void toShowToast(String msg) {
        showToast(msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mIPresenterEvent.toonActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        CallServer.getRequestInstance().cancelBySign(TAG);
        mIPresenterEvent.stopLocation();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        mIPresenterEvent.clickCancel();
    }

    /**
     * 选择城市对话框的响应
     *
     * @param csInfo
     */
    public void onEventMainThread(CitySelectInfo csInfo) {
        mIPresenterEvent.setTvLocationStartSelected(csInfo.getCityName(), csInfo.getCityId() + "");
    }

    /**
     * 事件: 响应 SelectDestActivity
     *
     * @param destEntity
     */
    public void onEvent(GetDestByKeywordResp destEntity) {
        mIPresenterEvent.handleDestResult(destEntity);
    }

    /**
     * 地图定位 回调
     *
     * @param event
     */
    public void onEvent(EventReleaseLocationEvent event) {
        mIPresenterEvent.setLocationEvent(event);
    }

}