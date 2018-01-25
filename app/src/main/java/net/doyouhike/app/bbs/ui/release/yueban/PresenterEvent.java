package net.doyouhike.app.bbs.ui.release.yueban;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.google.gson.Gson;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.event.EventReleaseLocationEvent;
import net.doyouhike.app.bbs.biz.event.PostLiveEvent;
import net.doyouhike.app.bbs.biz.event.SendEvent;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.BaseTag;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.SendingTimeline;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.GetDestByKeywordResp;
import net.doyouhike.app.bbs.biz.openapi.presenter.NodesHelper;
import net.doyouhike.app.bbs.biz.openapi.response.LoginUser;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventDetailResp;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.NodeTimeline;
import net.doyouhike.app.bbs.biz.presenter.action.ActionDetailPresenter;
import net.doyouhike.app.bbs.chat.helper.Constant;
import net.doyouhike.app.bbs.ui.adapter.NodeTimelineAdapter;
import net.doyouhike.app.bbs.ui.release.yueban.destination.SelectDestAdapter;
import net.doyouhike.app.bbs.ui.release.yueban.dialog.CalendarDialog;
import net.doyouhike.app.bbs.ui.release.yueban.dialog.TagDialog;
import net.doyouhike.app.bbs.ui.release.yueban.more.WithMoreFragment;
import net.doyouhike.app.bbs.ui.util.TipUtil;
import net.doyouhike.app.bbs.ui.widget.action.EditData;
import net.doyouhike.app.bbs.ui.widget.common.location.HourSelectorDialog;
import net.doyouhike.app.bbs.ui.widget.common.location.LocationSelectorDialog;
import net.doyouhike.app.bbs.util.AmapLocationUtil;
import net.doyouhike.app.bbs.util.GetCityIDUtils;
import net.doyouhike.app.bbs.util.LogUtil;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;
import net.doyouhike.app.bbs.util.StrUtils;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.UIUtils;
import net.doyouhike.app.bbs.util.UserInfoUtil;
import net.doyouhike.app.library.ui.proccess.LoadingDialog;
import net.doyouhike.app.library.ui.utils.BaseBitmapUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Filework:
 * Author: luochangdong
 * Date:16-3-11
 */
public class PresenterEvent {


    public static final String EVENT_TITLE = "EVENT_TITLE";
    public static final String EVENT_CONTENT = "EVENT_content";
    public static final String EVENT_LIMIT = "EVENT_limit";
    public static final String EVENT_RELEASE = "EVENT_RELEASE";
    public static final String EVENT_JION = "Jioned";
    public static final String TIME_HOUT_SPIT = ":";


    LoadingDialog dialog;

    /**
     * 集合时间弹窗
     */
    HourSelectorDialog mHourSelectorDialog;
    /**
     * 出发地弹窗
     */
    LocationSelectorDialog mLocationSelectorDialog;
    /**
     * 行程日期弹窗
     */
    CalendarDialog mCalendar;
    /**
     * 类型弹窗
     */
    TagDialog mTagDialog;
    SimpleDateFormat beginDateFormat = new SimpleDateFormat(Content.DATE_EVENT_YYYY_MM_DD);
    EditEventActivity editEventActivity;
    /**
     * 高德地图定位工具类
     */
    private AmapLocationUtil mapLocationUtil;

    /**
     * Event title　活动标题
     */
    private String mTitle;
    /**
     * Event mLimit　人数限制
     */
    private String mLimit;

    private int mJioned;

    /**
     * Event content 内容正文
     */
    private EditData mContent = new EditData();

    /**
     * 发布标签
     */
    public List<BaseTag> mTags = new ArrayList<>();
    /**
     * 目的地
     */
    private List<GetDestByKeywordResp> dests = new ArrayList<>();
    /**
     * 出发地
     */
    private String mDepartureName;

    private String mDepartureId;
    /**
     * 发布的Timeline
     */
    private NodeTimeline.ItemsBean.NodeBean mEventTimeline;
    /**
     * Event参数
     */
    private NodeTimeline.ItemsBean.NodeBean.EventBean mEventEntity = new NodeTimeline.ItemsBean.NodeBean.EventBean();

    /**
     * 开始日期
     */
    private Date mBeginDate;
    /**
     * 结束日期
     */
    private Date mEndDate;

    /**
     * 活动天数
     */
    private String mDays;


    /**
     * 活动费用
     */
    private String fee_type = "free";

    /**
     * 集合时间 时:分
     */
//    private String mHourMinute = beginDateFormat.format(Calendar.getInstance().getTime()) + " 08:00";
    private String mHourMinute;
    /**
     * 是否为约伴编辑
     */
    private Boolean isEdit = false;

    /**
     * 编辑发送失败
     */
    private Boolean isSendFailEdit = false;

    private Context mContext;

    /**
     * 下一步编辑页面
     */
    WithMoreFragment withMoreFragment;

    /**
     * 是否为第一页编辑界面
     */
    private boolean isFistEditPage = true;


    public PresenterEvent(EditEventActivity editEventActivity) {
        this.editEventActivity = editEventActivity;
        mContext = editEventActivity;
        mTagDialog = new TagDialog(this);
        mLocationSelectorDialog = new LocationSelectorDialog(editEventActivity);
        mCalendar = new CalendarDialog(this);
        mHourSelectorDialog = new HourSelectorDialog(this);
        dialog = new LoadingDialog(editEventActivity);

    }

    public Context getContext() {
        return mContext;
    }

    public void startLocation() {
        goStartLocation();
    }


    public void travelDay() {
        goCalendarActivity();
    }

    public void travelType() {
        goTravelTyep();
    }

    /**
     * 取消约伴
     *
     * @param node_id
     */
    public void cancelEvent(String node_id) {
        String token = UserInfoUtil.getInstance().getToken();
        if (token.isEmpty() || node_id.isEmpty())
            return;

        dialog.show();

        NodesHelper.getInstance().postNodeEventCancel(mContext, node_id, cancelListener);


    }

    IOnResponseListener cancelListener = new IOnResponseListener() {
        @Override
        public void onSuccess(Response response) {
            cancelEvent(true);
        }

        @Override
        public void onError(Response response) {
            cancelEvent(false);
        }
    };

    public void getBundleExtras(Bundle extras) {

        if (extras == null) {
            initLocation();
            return;
        }
        String gson = extras.getString(Content.TIMELINE);

        mEventTimeline = new Gson().fromJson(gson, SendingTimeline.class);
        isSendFailEdit = true;
        if (!StringUtil.isEmpty(mEventTimeline.getNode_id())) {
            isEdit = true;
        }
        LogUtil.d("编辑约伴原始数据:", gson);

    }

    public void initData() {
        editEventActivity.tvCancel.setText(R.string.cancel);
        editEventActivity.tvSend.setText(R.string.next_step);
        UIUtils.showView(editEventActivity.back, !isFistEditPage);
        initHistory();
        updateIsCanNext();

    }

    public void clickCancel() {
        if (isFistEditPage) {
            if (!isEdit && dests.size() > 0) {
                bottomDialog();
            } else {

                if (isEdit) {
                    EditCancelDialog();
                } else {
                    editEventActivity.finish();
                }
            }
        } else {
            withMoreFragment.goback();
            editEventActivity.getSupportFragmentManager().beginTransaction().remove(withMoreFragment);
            withMoreFragment = null;
            editEventActivity.svEditEvent.setVisibility(View.VISIBLE);
            editEventActivity.editEventContainer.setVisibility(View.GONE);
            editEventActivity.tvCancel.setText(R.string.cancel);
            editEventActivity.tvSend.setText("下一步");
            isFistEditPage = true;
        }
        UIUtils.showView(editEventActivity.back, !isFistEditPage);
        updateIsCanNext();
    }

    /**
     * 放弃修改弹窗
     */
    private void EditCancelDialog() {
        TipUtil.alert(editEventActivity
                , "放弃修改?"
                , "放弃修改不会保存你的更改"
                , new String[]{"继续编辑", "退出"}
                , new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                    }
                }
                ,
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        editEventActivity.finish();
                    }
                }
        );
    }

    public void clickSend() {
        if (isFistEditPage)
            clickEventMore();
        else {
            withMoreFragment.goback();
            releaseClick();
        }
    }

    public void clickAAMoney() {
        nomalAA_Free();
        selectedAA();

        fee_type = "AA";
    }

    public void clickFree() {
        nomalAA_Free();
        selectedFree();
        fee_type = "free";
    }

    public void clickMeettingTime() {
        showHourDialog();

    }

    public void clickDeleteEvent() {
        if (mEventTimeline.getNode_id() == null)
            return;
        deleteDialog();
    }

    public void clickEventMore() {
        Bundle bundle = new Bundle();
        if (StringUtil.isEmpty(mTitle)) {
            bundle.putString(EVENT_TITLE, getEventTitle());
        } else {
            bundle.putString(EVENT_TITLE, mTitle);
        }

        bundle.putSerializable(EVENT_CONTENT, mContent);
        bundle.putString(EVENT_LIMIT, mLimit);
        bundle.putInt(EVENT_JION, mJioned);
        if (withMoreFragment == null)
            withMoreFragment = new WithMoreFragment();
        withMoreFragment.setArguments(bundle);
        withMoreFragment.setEditEventCall(new IEditEventCall() {
            @Override
            public void setImageTextTitle(String title, String limit, EditData content) {
                callImageTextTitle(title, limit, content);
            }

            @Override
            public void updateTitle(String title) {
                mTitle = title;
                updateIsCanSend();
            }
        });
        editEventActivity.getSupportFragmentManager().beginTransaction().add(R.id.edit_event_container, withMoreFragment).commit();
        editEventActivity.tvCancel.setText(R.string.back);
        editEventActivity.tvSend.setText("发布");
        editEventActivity.svEditEvent.setVisibility(View.GONE);
        editEventActivity.editEventContainer.setVisibility(View.VISIBLE);
        isFistEditPage = false;
        UIUtils.showView(editEventActivity.back, !isFistEditPage);
    }

    private void callImageTextTitle(String title, String limit, EditData content) {
        if (StringUtil.isNotEmpty(title) && title.equals(getEventTitle())) {
            mTitle = null;
        } else {
            mTitle = title;
        }

        mLimit = limit;
        mContent = content;
        String json = new Gson().toJson(mContent);
        LogUtil.d("编辑图文回传的数据:" + json);
    }

    public void toonActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == EditEventActivity.REQUEST_CODE_MORE) {
            if (data == null)
                return;
            String title = data.getStringExtra(EVENT_TITLE);
            if (StringUtil.isNotEmpty(title) && title.equals(getEventTitle())) {
                mTitle = null;
            } else {
                mTitle = title;
            }

            mLimit = data.getStringExtra(EVENT_LIMIT);
            mContent = (EditData) data.getSerializableExtra(EVENT_CONTENT);
            String json = new Gson().toJson(mContent);
            LogUtil.d("编辑图文回传的数据:" + json);
        }
    }


    /**
     * 初始历史记录
     */
    private void initHistory() {
        editEventActivity.rlDeleteEvent.setVisibility(View.GONE);
        if (mEventTimeline == null) {
            mEventTimeline = SharedPreferencesManager.getToReleaseEvent(mContext);
        }
        if (mEventTimeline != null) {

            if (mEventTimeline.getTags() != null) {

                mTagDialog.setSelected(mEventTimeline.getTags());
                //类型
                setTagData(mEventTimeline.getTags());
            }

            if (mEventTimeline.getEvent() != null) {
                //出发地
                setTvLocationStartSelected(mEventTimeline.getEvent().getDeparture_name(), mEventTimeline.getEvent().getDeparture_id());
                //行程日期
                if (!StringUtil.isEmpty(mEventTimeline.getEvent().getBegin_date()) &&
                        !StringUtil.isEmpty(mEventTimeline.getEvent().getEnd_date()) &&
                        !StringUtil.isEmpty(mEventTimeline.getEvent().getDays())) {

                    StringBuilder sb = new StringBuilder();
                    String begin = StrUtils.timeStamp2Date(mEventTimeline.getEvent().getBegin_date(), Content.DATE_EVENT_YYYY_MM_DD);
                    String end = StrUtils.timeStamp2Date(mEventTimeline.getEvent().getEnd_date(), Content.DATE_EVENT_MM_DD);
                    sb.append(begin)
//                            .append("-")
//                            .append(end)
                            .append(" 共")
                            .append(mEventTimeline.getEvent().getDays())
                            .append("天");

                    editEventActivity.tvCalendarDate.setText(sb.toString());
                    mDays = mEventTimeline.getEvent().getDays();

                    //集合时间
                    if (StringUtil.isNotEmpty(mEventTimeline.getEvent().getGather_date())
                            && !mEventTimeline.getEvent().getGather_date().equals("0")) {
                        String hourMime = StrUtils.timeStamp2Date(mEventTimeline.getEvent().getGather_date(), Content.DATE_EVENT_YYYY_MM_DD_HH_MM);
                        setTvHourMinute(hourMime);
                    }

                    updateIsCanNext();
                }
                if (mEventTimeline.getEvent().getDests() != null) {
                    for (GetDestByKeywordResp dest : mEventTimeline.getEvent().getDests()) {
                        //目的地
                        dests.add(dest);
                        setDest(dest);
                    }
                }
                //人数限制
                mLimit = mEventTimeline.getEvent().getMember_limit();

                //确认参加人数
                if (StringUtil.isNotEmpty(mEventTimeline.getEvent().getJoined()))
                    mJioned = Integer.parseInt(mEventTimeline.getEvent().getJoined());

                //活动费用
                fee_type = mEventTimeline.getEvent().getFee_type();
                if (fee_type.equals("AA")) {
                    nomalAA_Free();
                    selectedAA();
                } else {
                    nomalAA_Free();
                    selectedFree();
                }
                if (isEdit) {
                    if (StringUtil.isNotEmpty(mEventTimeline.getEvent().getEvent_state())
                            && (mEventTimeline.getEvent().getEvent_state().equals(Constant.EVENT_CLOSE)
                            || mEventTimeline.getEvent().getEvent_state().equals(Constant.EVENT_CANCEL))) {
                        //活动已结束
                        editEventActivity.rlDeleteEvent.setVisibility(View.GONE);
                    } else {
                        editEventActivity.rlDeleteEvent.setVisibility(View.VISIBLE);
                    }
                } else {
                    editEventActivity.rlDeleteEvent.setVisibility(View.GONE);
                }


                //标题
                if (StringUtil.isNotEmpty(getEventTitle())
                        && !mEventTimeline.getEvent().getTitle().equals(getEventTitle())) {
                    mTitle = mEventTimeline.getEvent().getTitle();

                }

            }


            //图文
            if (mEventTimeline.getEvent().getEvent_contents() != null) {
                EditData.ImgText imgText;
                ArrayList<EditData.ImgText> imgTexts = new ArrayList<>();
                ArrayList<String> del_photo_ids = new ArrayList<>();

                for (EventDetailResp.ContentBean content : mEventTimeline.getEvent().getEvent_contents()) {
                    imgText = new EditData.ImgText();
                    imgText.setInputStr(content.getContent());
                    imgText.setImagePath(content.getWhole_photo_path());
                    imgText.setPhotoId(content.getPhoto_id());
                    imgText.setIsNew(content.getIs_new());

                    if (content.getIs_new() != null
                            && content.getIs_new().equals("2")) {
                        del_photo_ids.add(content.getPhoto_id());
                    }
                    imgTexts.add(imgText);
                }
                mContent.setImgTexts(imgTexts);
                //删除PhotoId
                mContent.setDel_photoId(del_photo_ids);
            }

            //重置为可发送状态
            mEventTimeline.setReleaseStatus(0);


        } else {
            mEventTimeline = new NodeTimeline.ItemsBean.NodeBean();
        }


    }

    /**
     * 获取标题
     *
     * @return
     */
    private String getEventTitle() {


        StringBuilder title = new StringBuilder();
//        if (mDepartureName != null) {
//            title.append(mDepartureName);//出发地
//            title.append("-");
//        }
        //目的地
        for (int i = 0; i < dests.size(); i++) {
            title.append(dests.get(i).getNode_name());
            if (i == dests.size() - 1)
                break;
            title.append("-");
        }
        //天数
        if (!StringUtil.isEmpty(mDays)) {
            title.append(mDays).append("天");
        }
        //类型
        if (mTags.size() > 0) {
            title.append(mTags.get(0).getTag_name());
        }

        return title.toString();
    }


    /**
     * 更新是否可以发送的状态
     *
     * @return 返回修改后的状态
     */
    private boolean updateIsCanNext() {
        if (editEventActivity.tvSend != null) {
            if (dests.size() > 0 && mDepartureName != null
                    && mTags.size() > 0 && StringUtil.isNotEmpty(mDays)
                    && StringUtil.isNotEmpty(mHourMinute)) {
                editEventActivity.tvSend.setAlpha(1f);
                editEventActivity.tvSend.setClickable(true);
                return true;
            } else {
                editEventActivity.tvSend.setAlpha(0.54f);
                editEventActivity.tvSend.setClickable(false);
                return false;
            }
        }
        return false;
    }

    private boolean updateIsCanSend() {
        if (editEventActivity.tvSend != null) {
            if (dests.size() > 0 && mDepartureName != null
                    && mTags.size() > 0 && StringUtil.isNotEmpty(mDays)
                    && StringUtil.isNotEmpty(mHourMinute)
                    && StringUtil.isNotEmpty(mTitle)) {
                editEventActivity.tvSend.setAlpha(1f);
                editEventActivity.tvSend.setClickable(true);
                return true;
            } else {
                editEventActivity.tvSend.setAlpha(0.54f);
                editEventActivity.tvSend.setClickable(false);
                return false;
            }
        }
        return false;
    }


    /**
     * 约伴发布　参
     */
    private void releaseClick() {
        String jsonEvent = draft();
        SendEvent sendEvent = new SendEvent(jsonEvent);
        editEventActivity.tvSend.setEnabled(false);
        EventBus.getDefault().post(sendEvent);
        editEventActivity.tvSend.postDelayed(new Runnable() {
            @Override
            public void run() {
                editEventActivity.finish();
            }
        }, 2000);

    }

    /**
     * 保存草稿
     *
     * @return json
     */
    private String draft() {
        List<GetDestByKeywordResp> to = new ArrayList<>();
        GetDestByKeywordResp toBean;
        for (int i = 0; i < dests.size(); i++) {
            toBean = new GetDestByKeywordResp();
            toBean.setNode_cat(dests.get(i).getNode_cat());
            toBean.setNode_id(dests.get(i).getNode_id());
            toBean.setNode_name(dests.get(i).getNode_name());
            to.add(toBean);
        }
        if (StringUtil.isEmpty(mTitle)) {
            mEventEntity.setTitle(getEventTitle());
        } else {

            mEventEntity.setTitle(mTitle);
        }
        if (mLimit == null || mLimit.isEmpty()) {
            mEventEntity.setMember_limit("16");
        } else {
            mEventEntity.setMember_limit(mLimit);
        }
        if (StringUtil.isEmpty(mEventTimeline.getNode_id()))
            mEventEntity.setJoined("1");//发布默认

        mEventEntity.setDeparture_id(mDepartureId);
        mEventEntity.setDeparture_name(mDepartureName);
        mEventEntity.setDests(to);


        //发布时间
        if (!StringUtil.isEmptyObj(mBeginDate)) {
            mEventEntity.setBegin_date(getBeginTime(mBeginDate) + "");
            mEventEntity.setDays(mDays);
            mEventEntity.setEnd_date(getEndTime(mEndDate) + "");

        } else if (mEventTimeline.getEvent() != null && !StringUtil.isEmpty(mEventTimeline.getEvent().getBegin_date())) {
            mEventEntity.setBegin_date(mEventTimeline.getEvent().getBegin_date());
            mEventEntity.setEnd_date(mEventTimeline.getEvent().getEnd_date());
            mEventEntity.setDays(mEventTimeline.getEvent().getDays());
        }

        //集合时间

        if (StringUtil.isNotEmpty(mHourMinute)) {
            mEventEntity.setGather_date(StrUtils.date2TimeStamp(mHourMinute, Content.DATE_EVENT_YYYY_MM_DD_HH_MM) + "");
        } else if (mEventTimeline.getEvent() != null && StringUtil.isNotEmpty(mEventTimeline.getEvent().getGather_date())) {
            mEventEntity.setGather_date(mEventTimeline.getEvent().getGather_date());
        }

        //活动状态
        if (mEventTimeline.getEvent() == null)
            mEventEntity.setEvent_state(Constant.EVENT_RECRUTING);//召集中
        else
            mEventEntity.setEvent_state(mEventTimeline.getEvent().getEvent_state());

        //确认参加人数
        if (mEventTimeline.getEvent() != null
                && mEventTimeline.getEvent().getJoined() != null) {
            mEventEntity.setJoined(mEventTimeline.getEvent().getJoined());
        }

        mEventEntity.setDests(dests);//目的地


        List<EventDetailResp.ContentBean> event_contents = new ArrayList<>();

        EventDetailResp.ContentBean event_content;
        List<EditData.ImgText> imgTextList = mContent.getImgTexts();
        ArrayList<String> del_photo_ids = new ArrayList<>();

        if (imgTextList != null) {
            for (EditData.ImgText entity : imgTextList) {
                event_content = new EventDetailResp.ContentBean();
                if (StringUtil.isNotEmpty(entity.getImagePath())) {
                    event_content.setType(Constant.IMAGE);
                } else
                    event_content.setType(Constant.TXET);
                event_content.setWhole_photo_path(entity.getImagePath());
                event_content.setContent(entity.getInputStr());
                event_content.setIs_new(entity.getIsNew());
                event_content.setPhoto_id(entity.getPhotoId());
                if (StringUtil.isNotEmpty(entity.getPhotoId()))
                    event_content.setStatus(EventDetailResp.ContentBean.UPLOAD_OK);

                event_contents.add(event_content);
                if (entity.getIsNew() != null &&
                        entity.getIsNew().equals("2")) {
                    del_photo_ids.add(entity.getPhotoId());
                }

            }
            mEventEntity.setDel_photo_ids(del_photo_ids);
            if (event_contents.size() > 0) {
                mEventEntity.setEvent_contents(event_contents);//图片
            }

        }


        mEventEntity.setFee_type(fee_type);


        LoginUser currentUser = UserInfoUtil.getInstance().getCurrentUser();
        NodeTimeline.ItemsBean.NodeBean.UserBean userBean = new NodeTimeline.ItemsBean.NodeBean.UserBean();
        userBean.setAvatar(currentUser.getUser().getAvatar());
        userBean.setNick_name(currentUser.getUser().getNick_name());
        userBean.setUser_desc(currentUser.getUser().getUser_desc());
        userBean.setUser_id(currentUser.getUser().getUser_id());

        mEventTimeline.setUser(userBean);

        mEventTimeline.setEvent(mEventEntity);//活动
        mEventTimeline.setTags(mTags);//标签
        mEventTimeline.setTime(System.currentTimeMillis());
        mEventTimeline.setNode_type(NodeTimelineAdapter.NODE_EVENT);

        String jsonEvent = new Gson().toJson(mEventTimeline);
        if (StringUtil.isEmpty(mEventTimeline.getNode_id())) {
            SharedPreferencesManager.setToEventLiveInfo(mContext, jsonEvent);
        }
        LogUtil.d(jsonEvent);
        return jsonEvent;
    }


    public void showHourDialog() {
        mHourSelectorDialog.show();
        if (mBeginDate != null)
            mHourSelectorDialog.setDefuatTime(mBeginDate);
        editEventActivity.dialogMatch(mHourSelectorDialog);

    }

    public void goTravelTyep() {
        mTagDialog.show();
        editEventActivity.dialogMatch(mTagDialog);
    }


    public void goCalendarActivity() {
        mCalendar.show();
        editEventActivity.dialogMatch(mCalendar);

    }

    /**
     * 出发地
     */
    public void goStartLocation() {
        mLocationSelectorDialog.show();
    }

    /**
     * 响应 LocationSelectorDialog
     *
     * @param location
     */
    public void setTvLocationStartSelected(String location, String city_id) {
        editEventActivity.tvLocationStartSelected.setText(location);
        mDepartureName = location;
        mDepartureId = city_id;
        updateIsCanNext();
    }

    /**
     * 停止定位
     */
    public void stopLocation() {
        mTagDialog.onDialogDestory();
        try {
            if (null != mapLocationUtil) {
                //暂停定位
                mapLocationUtil.stopLocation();
                mapLocationUtil.onDestory();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 定位数据响应处理
     *
     * @param event
     */
    public void setLocationEvent(EventReleaseLocationEvent event) {
        LogUtil.d("定位数据响应");
        if (event == null)
            return;
        NodeTimeline.ItemsBean.NodeBean.LocationBean locationEntity = new NodeTimeline.ItemsBean.NodeBean.LocationBean();
        if (StringUtil.isNotEmptyObj(event.getaMapLocation())) {
            locationEntity.setLatitude(event.getaMapLocation().getLatitude() + "");
            locationEntity.setLongitude(event.getaMapLocation().getLongitude() + "");
            locationEntity.setAltitude(event.getaMapLocation().getAltitude() + "");
            locationEntity.setLocationName(event.getaMapLocation().getPoiName());
            String cityName = event.getaMapLocation().getCity();
            if (!TextUtils.isEmpty(cityName) && cityName.endsWith("市")) {
                cityName = cityName.replace("市", "");
            }
            String city_id = GetCityIDUtils.getCityID(mContext, cityName) + "";
            locationEntity.setCity_id(city_id);
            mEventTimeline.setLocation(locationEntity);
            if (StringUtil.isEmpty(mDepartureName))
                setTvLocationStartSelected(cityName, city_id);
            stopLocation();//关闭定位服务
        }


    }

    /**
     * 响应 SelectDestActivity
     *
     * @param dest
     */
    public void handleDestResult(GetDestByKeywordResp dest) {
        LogUtil.d("目的地结果");
        if (dest == null)
            return;
        dests.add(dest);
        setDest(dest);
        updateIsCanNext();
    }

    /**
     * 目的地
     *
     * @param dest
     */
    private void setDest(GetDestByKeywordResp dest) {
        if (dests.size() == 1) {//一个目的地的显示样式
            editEventActivity.llDestOne.setVisibility(View.VISIBLE);
            if (!StringUtil.isEmpty(dest.getNode_name()))
                editEventActivity.tvDestination.setText(dest.getNode_name());
            if (!StringUtil.isEmpty(dest.getNode_cat())) {
                if (dest.getNode_cat().equals(SelectDestAdapter.TYPE_CITY)) {
                    editEventActivity.ivCat.setImageResource(R.drawable.road_cat_city);
                } else if (dest.getNode_cat().equals(SelectDestAdapter.TYPE_ROUTE)) {
                    editEventActivity.ivCat.setImageResource(R.drawable.road_cat_route_dark);
                }
            }


            editEventActivity.ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editEventActivity.llDestOne.setVisibility(View.GONE);
                    dests.remove(0);
                    updateIsCanNext();

                }
            });
        } else {//多个目的地的显示样式
            if (dests.size() == 2) {
                editEventActivity.llDestOne.setVisibility(View.GONE);
                addLocationView(dests.get(0));
            }
            addLocationView(dest);
        }
        if (dests.size() > 0) {
            editEventActivity.ivAddLocation.setVisibility(View.VISIBLE);
        } else {
            editEventActivity.ivAddLocation.setVisibility(View.GONE);
        }
    }

    /**
     * 显示 动态添加 目的地
     *
     * @param dest
     */
    public void addLocationView(final GetDestByKeywordResp dest) {
        final View destView = LayoutInflater.from(mContext).inflate(R.layout.item_location, null);
        TextView tv_destination = (TextView) destView.findViewById(R.id.tv_destination);
        ImageView iv_cat = (ImageView) destView.findViewById(R.id.iv_cat);
        ImageView iv_close = (ImageView) destView.findViewById(R.id.iv_close);
        if (!StringUtil.isEmpty(dest.getNode_name())) {
            if (dest.getNode_name().length() > 4) {
                tv_destination.setTextSize(12);
            } else {
                tv_destination.setTextSize(14);
            }
            tv_destination.setText(dest.getNode_name());
        }

        if (!StringUtil.isEmpty(dest.getNode_cat())) {
            if (dest.getNode_cat().equals(SelectDestAdapter.TYPE_CITY)) {
                iv_cat.setImageResource(R.drawable.road_cat_city);
            } else if (dest.getNode_cat().equals(SelectDestAdapter.TYPE_ROUTE)) {
                iv_cat.setImageResource(R.drawable.road_cat_route_dark);
            }
        }


        editEventActivity.rlLocationAdd.addView(destView);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editEventActivity.rlLocationAdd.removeView(destView);
                dests.remove(dest);
                if (dests.size() == 0) {
                    editEventActivity.llDestOne.setVisibility(View.GONE);
                }
                updateIsCanNext();
            }
        });


    }

    /**
     * 显示 标签
     *
     * @param tagInfos
     */
    public void setTagData(List<BaseTag> tagInfos) {
        if (tagInfos == null)
            return;
        mTags.clear();

        StringBuilder sb = new StringBuilder();
        for (BaseTag tag : tagInfos) {
            sb.append("#");
            sb.append(tag.getTag_name())
                    .append(" ");
            mTags.add(tag);
        }
        editEventActivity.tvTagType.setText(sb.toString());
        updateIsCanNext();
    }

    /**
     * 显示 日期
     *
     * @param begin
     * @param end
     * @param days
     */
    public void setTvCalendarDate(Date begin, Date end, String days) {
        if (StringUtil.isEmptyObj(begin) || StringUtil.isEmptyObj(end) || StringUtil.isEmpty(days))
            return;

        mBeginDate = begin;
        mEndDate = end;
        mDays = days;
        StringBuilder sb = new StringBuilder();
        sb.append(beginDateFormat.format(begin))
                .append(" ")
//                .append(beginDateFormat.format(end))
                .append(" 共")
                .append(days)
                .append("天");
        editEventActivity.tvCalendarDate.setText(sb.toString());

        updateIsCanNext();

    }

    /**
     * 显示 时分
     *
     * @param hourMinute
     */
    public void setTvHourMinute(String hourMinute) {
        editEventActivity.tvHourMinute.setText(hourMinute);
        mHourMinute = hourMinute;
        LogUtil.d(mHourMinute);
        updateIsCanNext();
    }


    /**
     * 选择 AA
     */
    private void selectedAA() {
        editEventActivity.ivAaMoney.setImageDrawable(BaseBitmapUtil.getDrawableFromRes(mContext, R.drawable.post_yue_list_cost_icon_aa_click));
        editEventActivity.tvAaMoney.setTextColor(mContext.getResources().getColor(R.color.color_theme));
        editEventActivity.rlAaMoney.setBackgroundDrawable(BaseBitmapUtil.getDrawable(mContext, R.drawable.shape_post_yue_money_selected));
        editEventActivity.tvAaPs.setVisibility(View.VISIBLE);
        editEventActivity.tvFeePs.setVisibility(View.GONE);
    }

    /**
     * 重置
     */
    private void nomalAA_Free() {
        editEventActivity.ivAaMoney.setImageDrawable(BaseBitmapUtil.getDrawableFromRes(mContext, R.drawable.post_yue_list_cost_icon_aa));
        editEventActivity.tvAaMoney.setTextColor(mContext.getResources().getColor(R.color.gray_word));
        editEventActivity.rlAaMoney.setBackgroundDrawable(BaseBitmapUtil.getDrawable(mContext, R.drawable.shape_post_yue_money_nomal));
        editEventActivity.ivFreeMoney.setImageDrawable(BaseBitmapUtil.getDrawableFromRes(mContext, R.drawable.post_yue_list_cost_icon_free));
        editEventActivity.tvFreeMoney.setTextColor(mContext.getResources().getColor(R.color.gray_bg));
        editEventActivity.rlFreeMoney.setBackgroundDrawable(BaseBitmapUtil.getDrawable(mContext, R.drawable.shape_post_yue_money_nomal));
    }

    /**
     * 选择Free
     */
    private void selectedFree() {
        editEventActivity.ivFreeMoney.setImageDrawable(BaseBitmapUtil.getDrawableFromRes(mContext, R.drawable.post_yue_list_cost_icon_free_click));
        editEventActivity.tvFreeMoney.setTextColor(mContext.getResources().getColor(R.color.color_theme));
        editEventActivity.rlFreeMoney.setBackgroundDrawable(BaseBitmapUtil.getDrawable(mContext, R.drawable.shape_post_yue_money_selected));
        editEventActivity.tvAaPs.setVisibility(View.GONE);
        editEventActivity.tvFeePs.setVisibility(View.VISIBLE);

    }

    /**
     * 获取格式化的开始日期
     *
     * @param date
     * @return
     */
    private long getBeginTime(Date date) {

        if (null == date) {
            return 0;
        }
        String strTimestamp = new SimpleDateFormat(Content.DATE_EVENT_YYYY_MM_DD).format(date);
        return StrUtils.date2TimeStamp(strTimestamp, Content.DATE_EVENT_YYYY_MM_DD);

    }

    /**
     * 获取格式化的结束日期
     *
     * @param endDate
     * @return
     */
    private long getEndTime(Date endDate) {
        String strTimestamp = new SimpleDateFormat(Content.DATE_EVENT_YYYY_MM_DD).format(endDate);

        return StrUtils.date2TimeStamp(strTimestamp, Content.DATE_EVENT_YYYY_MM_DD);
    }


    /**
     * 取消约伴响应处理
     *
     * @param isDelete
     */
    public void cancelEvent(Boolean isDelete) {
        dialog.closeDialog();
        if (isDelete) {
            SharedPreferencesManager.clearToEventLiveInfo(mContext);
            editEventActivity.toShowToast("取消约伴成功");
            editEventActivity.tvCancel.postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent it = new Intent();
                    it.putExtra("delete", true);
                    editEventActivity.setResult(ActionDetailPresenter.INTENT_EXTRA_NAME_EDIT, it);
//            EventBus.getDefault().post(new DeleteEvent());
                    editEventActivity.finish();
                }
            }, 1000);

        } else {
            editEventActivity.toShowToast("取消失败");
        }

    }

    /**
     * 启动定位  每次只需定位一次
     */
    public void initLocation() {
        if (null == mapLocationUtil)
            mapLocationUtil = new AmapLocationUtil(new EventReleaseLocationEvent());
        mapLocationUtil.startLocation(true, 0l);
        LogUtil.d("初始化定位");
    }


    /**
     * 取消底部躺出框
     */
    private void bottomDialog() {
        final String[] stringItems = {"退出", "临时保存"};
        final ActionSheetDialog sheetDialog = new ActionSheetDialog(mContext, stringItems, null);
        sheetDialog.title("退出编辑").show();

        sheetDialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if (isSendFailEdit) {
                            if (mEventTimeline instanceof SendingTimeline) {
                                EventBus.getDefault()
                                        .post(new PostLiveEvent(Content.RELEASE_LIVE_DELETE, (SendingTimeline) mEventTimeline));
                            }
                        }

                        SharedPreferencesManager.clearToEventLiveInfo(mContext);
                        editEventActivity.finish();
                        break;

                    case 1:
                        draft();
                        editEventActivity.finish();
                        break;
                }
                sheetDialog.dismiss();
            }
        });
    }

    /**
     * 删除弹窗
     */
    private void deleteDialog() {
        final String[] stringItems = {"确认"};
        final ActionSheetDialog sheetDialog = new ActionSheetDialog(mContext, stringItems, null);
        sheetDialog.title("取消约伴");

        sheetDialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        cancelEvent(mEventTimeline.getNode_id());
                        break;

                }
                sheetDialog.dismiss();
            }
        });
        sheetDialog.show();

    }


}
