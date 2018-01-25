/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: SettingPersonalMassageActivity.java
 * Author: ChenWeiZhen
 * Version: 1.0
 * Create: 2015-10-5
 *
 * Changes (from 2015-10-5)
 * -----------------------------------------------------------------
 * 2015-10-5 创建SettingPersonalMassageActivity.java (ChenWeiZhen);
 * -----------------------------------------------------------------
 * 2015-11-3 有关于“个人说明”的部分全部注释。另外：“个人说明”之后就是“签名” (ChenWeiZhen);
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.ui.activity.me;

import android.content.Intent;
import android.content.res.AssetManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.activity.BaseActivity;
import net.doyouhike.app.bbs.biz.entity.CitySelectInfo;
import net.doyouhike.app.bbs.biz.entity.CurrentUserDetails;
import net.doyouhike.app.bbs.biz.event.open.BindPhoneEvent;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.presenter.UsersHelper;
import net.doyouhike.app.bbs.biz.openapi.request.users.UserEditProfilePut;
import net.doyouhike.app.bbs.chat.helper.Constant;
import net.doyouhike.app.bbs.ui.activity.me.SettingNickNameActivity.NickNameInfo;
import net.doyouhike.app.bbs.ui.activity.me.SettingUserdescActivity.UserdescInfo;
import net.doyouhike.app.bbs.ui.widget.BottomDialogWindow;
import net.doyouhike.app.bbs.ui.widget.common.location.LocationSelectorDialog;
import net.doyouhike.app.bbs.util.GetCityIDUtils;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SettingPersonalMassageActivity extends BaseActivity implements
        OnClickListener {

    /**
     * 昵称项
     */
    private RelativeLayout nicknameRlyt;
    /**
     * 昵称text
     */
    private TextView nicknameTv;

    /**
     * 性别项
     */
    private RelativeLayout genderRlyt;
    /**
     * 性别text
     */
    private TextView genderTv;

    /**
     * 手机号项
     */
    private RelativeLayout phoneNumberRlyt;
    /**
     * 手机号text
     */
    private TextView phoneNumberTv;

    /**
     * 城市项
     */
    private RelativeLayout cityRlyt;
    /**
     * 城市text
     */
    private TextView cityTv;

    /**
     * 签名项
     */
    private RelativeLayout signatureRlyt;
    /**
     * 签名text
     */
    private TextView userdescTv;

    private BottomDialogWindow bottomPopupWindow;
    /**
     * 城市选择对话框
     */
    private LocationSelectorDialog citySelelctDialog;

    private CurrentUserDetails cuDetails;

    private String nickName;
    private String gender = Constant.UNKNOWN;
    private String phoneNumber;
    private int cityId;
    private String userdesc;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_setting_personal_message;
    }

    @Override
    protected void initViewsAndEvents() {
        bindControl();

        setListener();

        refreshDisplay();
    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    /**
     * 绑定控件
     */
    private void bindControl() {
        nicknameRlyt = (RelativeLayout) findViewById(R.id.rlyt_setting_nickname);
        nicknameTv = (TextView) findViewById(R.id.tv_setting_nickname);

        genderRlyt = (RelativeLayout) findViewById(R.id.rlyt_setting_gender);
        genderTv = (TextView) findViewById(R.id.tv_setting_gender);

        phoneNumberRlyt = (RelativeLayout) findViewById(R.id.rlyt_setting_phone_number);
        phoneNumberTv = (TextView) findViewById(R.id.tv_phone_number);

        cityRlyt = (RelativeLayout) findViewById(R.id.rlyt_setting_city);
        cityTv = (TextView) findViewById(R.id.tv_setting_city);

        signatureRlyt = (RelativeLayout) findViewById(R.id.rlyt_setting_signature);
        userdescTv = (TextView) findViewById(R.id.tv_setting_signature);
    }

    /**
     * 给控件添加监听者
     */
    private void setListener() {
        nicknameRlyt.setOnClickListener(this);
        genderRlyt.setOnClickListener(this);
        phoneNumberRlyt.setOnClickListener(this);
        cityRlyt.setOnClickListener(this);
        signatureRlyt.setOnClickListener(this);
    }

    /**
     * 设置显示数据
     */
    private void refreshDisplay() {

        cuDetails = SharedPreferencesManager.getUserDetailsInfo(this);

        // 昵称
        nickName = cuDetails.getNick_name();
        if (nickName != null && !nickName.equals("")) {
            nicknameTv.setText(nickName);
        }

        // 性别
        if (cuDetails.getSex().equals(Constant.MALE)) {
            genderTv.setText(getResources().getString(R.string.gender_boy));
        } else if (cuDetails.getSex().equals(Constant.FEMALE)) {
            genderTv.setText(getResources().getString(R.string.gender_girl));
        } else {
            genderTv.setText(getResources().getString(R.string.gender_unkown));
        }
        gender = cuDetails.getSex();

        // 手机号
        phoneNumber = cuDetails.getMobile();
        if (phoneNumber != null && !phoneNumber.equals("")) {
            phoneNumberTv.setText(phoneNumber);
            phoneNumberTv.setTextColor(getResources().getColor(R.color.dark_grey_word));
        } else {
            phoneNumberTv.setText(R.string.bind_number);
            phoneNumberTv.setTextColor(getResources().getColor(R.color.clickable));
        }

        // 城市
        String city_id = cuDetails.getCity_id()+"";
        if (cuDetails.getCity_name() != null
                && !cuDetails.getCity_name().equals("")) {
            try {
                SAXReader reader = new SAXReader();
                AssetManager assetManager = getAssets();
                Document document = reader.read(assetManager.open("city.xml"));
                Element root = document.getRootElement();
                @SuppressWarnings("unchecked")
                Iterator<Element> rowElements = root.elementIterator("row");

                while (rowElements.hasNext()) {
                    Element rowElement = rowElements.next();

                    if (city_id.equals(
                            rowElement.elementText("CityID"))) {
                        String provinceName = rowElement
                                .elementText("Province");
                        String cityName = rowElement.elementText("Name");
                        setCityTv(provinceName, cityName);
                        cityId = GetCityIDUtils.getCityID(this, cityName);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 签名
        if (cuDetails.getUser_desc() != null) {
            userdescTv.setText(cuDetails.getUser_desc());
            userdesc = cuDetails.getUser_desc();
        } else {
            userdescTv.setText("");
            userdesc = null;
        }
    }

    private void setCityTv(String provinceName, String cityName) {

        String content;
        content = provinceName + cityName;

        if (!TextUtils.isEmpty(provinceName) && !TextUtils.isEmpty(cityName)) {
            if (provinceName.equals(cityName)) {
                content = provinceName;
            }
        }

        cityTv.setText(content);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        List<String> itemStrList = new ArrayList<String>();
        List<Integer> itemColorList = new ArrayList<Integer>();

        switch (v.getId()) {
            case R.id.rlyt_setting_nickname:
                intent.setClass(SettingPersonalMassageActivity.this,
                        SettingNickNameActivity.class);
                startActivity(intent);
                break;
            case R.id.rlyt_setting_gender:
                if (bottomPopupWindow == null) {

                    itemStrList.add(getResources().getString(R.string.gender_boy));
                    itemStrList.add(getResources().getString(R.string.gender_girl));
                    itemColorList.add(R.color.black_word);
                    itemColorList.add(R.color.black_word);

                    bottomPopupWindow = new BottomDialogWindow(this, itemStrList,
                            itemColorList);

                    bottomPopupWindow
                            .setOnItemClickListener(new OnItemClickListener() {

                                @Override
                                public void onItemClick(AdapterView<?> parent,
                                                        View view, int position, long id) {
                                    switch (position) {
                                        case 0:
                                            genderTv.setText(getResources().getString(
                                                    R.string.gender_boy));
                                            gender = Constant.MALE;
                                            break;
                                        case 1:
                                            genderTv.setText(getResources().getString(
                                                    R.string.gender_girl));
                                            gender = Constant.FEMALE;
                                            break;

                                        default:
                                            break;
                                    }
                                    saveData(UserEditProfilePut.SEX, gender);
                                    bottomPopupWindow.dismiss();
                                }
                            });
                }
                bottomPopupWindow.show();

                break;
            case R.id.rlyt_setting_phone_number:
                intent.setClass(SettingPersonalMassageActivity.this,
                        SettingBindPhoneActivity.class);
                startActivity(intent);

                break;
            case R.id.rlyt_setting_city:
                if (citySelelctDialog == null) {
                    citySelelctDialog = new LocationSelectorDialog(this);

                }
                citySelelctDialog.show();
                break;
            case R.id.rlyt_setting_signature:
                intent.setClass(SettingPersonalMassageActivity.this,
                        SettingUserdescActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


    IOnResponseListener<Response<CurrentUserDetails>> listener = new IOnResponseListener<Response<CurrentUserDetails>>() {
        @Override
        public void onSuccess(Response<CurrentUserDetails> response) {
            if (response.getData() == null)
                return;
            SharedPreferencesManager.setUserDetailsInfo(mContext, response.getData());
            showToast("修改成功");
        }

        @Override
        public void onError(Response response) {
            showToast(response.getMsg());
            refreshDisplay();
        }
    };

    /**
     * 每当某项数据被修改就马上调用修改接口
     */
    private void saveData(String key, String value) {
        UsersHelper.getSingleTon().editUserProfile(this, key, value, listener);
    }

    // -----------------------------EventBus 接收----------------------------------//

    /**
     * 修改昵称的响应
     *
     * @param nnInfo
     */
    public void onEventMainThread(NickNameInfo nnInfo) {
        nicknameTv.setText(nnInfo.nickName);
    }


    /**
     * 选择城市对话框的响应
     *
     * @param csInfo
     */
    public void onEventMainThread(CitySelectInfo csInfo) {

        setCityTv(csInfo.getProvinceName(), csInfo.getCityName());
        cityId = csInfo.getCityId();
        saveData(UserEditProfilePut.CITY_ID, cityId + "");
    }

    /**
     * 修改个人说明的响应
     *
     * @param stInfo
     */
    public void onEventMainThread(UserdescInfo stInfo) {
        userdescTv.setText(stInfo.userdesc);
        userdesc = stInfo.userdesc;
        saveData(UserEditProfilePut.USER_DESC, userdesc);
    }

    public void onEventMainThread(BindPhoneEvent event) {
        phoneNumberTv.setText(event.getMobile());
    }


}
