/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info., All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: ToSearchUserActivity.java
 * Author: wu-yoline(伍建鹏)
 * Version: 1.0
 * Create: 2015-11-03
 *
 * Changes (from 2015-11-03)
 * -----------------------------------------------------------------
 * 2015-11-03 : 1、创建本类，实现父类onCreate()方法;(wu-yoline)
 * 				2、设置ContentView;(wu-yoline)
 * 				3、实现进入界面弹出输入法;(wu-yoline)
 * 				4、把输入法的完成按钮改为搜索;(wu-yoline)
 * 				5、监听输入法中的搜索按钮;(wu-yoline)
 * 				6、实现搜索用户功能;(wu-yoline)
 * -----------------------------------------------------------------
 * 2015-11-04 : 1、添加监听关注接口的方法，实现关注按钮的更新
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.ui.activity.live;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.amap.api.services.core.PoiItem;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.entity.LocationInfo;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.presenter.UsersHelper;
import net.doyouhike.app.bbs.biz.openapi.response.LoginUser;
import net.doyouhike.app.bbs.biz.openapi.response.users.SearchUserResp;
import net.doyouhike.app.bbs.ui.activity.BaseActivity;
import net.doyouhike.app.bbs.ui.activity.login.LoginActivity;
import net.doyouhike.app.bbs.ui.adapter.action.SearchNearResultAdapter;
import net.doyouhike.app.bbs.ui.adapter.action.SearchNearResultAdapter.OnClickItemListener;
import net.doyouhike.app.bbs.ui.adapter.me.SearchUserAdapter;
import net.doyouhike.app.bbs.ui.listener.SearchNearManager;
import net.doyouhike.app.bbs.ui.listener.SearchNearResultListener;
import net.doyouhike.app.bbs.ui.listener.SearchNearResultListener.OnSearchResultListener;
import net.doyouhike.app.bbs.ui.widget.action.XEditText;
import net.doyouhike.app.bbs.util.CommonUtil;
import net.doyouhike.app.bbs.util.StrUtils;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.UserInfoUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索用户界面点击搜索按钮后跳转的界面
 *
 * @author wu-yoline
 */
public class ToSearchActivity extends BaseActivity {

    /**
     * 该activity结束的时候使用setResult(...)方法时的Intent的key，
     * 对应的值是用户选择了的附近地点LocationInfo对象的json字符串
     */
    public static final String INTENT_KEY_SELECTED_RESULT = SearchNearActivity.INTENT_KEY_SELECTED_RESULT;

    /**
     * 该activity结束的时候使用setResult(...)方法时的Intent的key，
     * 对应的值是用户定位到的城市的LocationInfo对象的json字符串
     */
    public static final String INTENT_KEY_CITY_LOCATIONINFO = "city_LocationInfo";

    /**
     * 使用setResult(...)方法时的Intent的结果码
     */
    public static final int RESULT_CODE_SELECTED_NEAR = 1815;

    /**
     * 指定搜索类型（getIntent()方法中的key），搜索类型有SEARCH_USER和SEARCH_NEAR;
     */
    public static final String INTENT_EXTRA_NAME_SEARCH_TYPE_INT = "search_what";

    /**
     * 搜索用户
     */
    public static final int SEARCH_USER = 1145;
    /**
     * 搜索附近地点
     */
    public static final int SEARCH_NEAR = 1146;

    private static final int STATUS_NEVER_SEARCH = 0;
    private static final int STATUS_NO_RESULT = 1;
    private static final int STATUS_HAS_RESULT = 2;
    private static final int STATUS_LOADING = 3;
    private static final int STATUS_ERROR = 4;

    private int searchStatus = STATUS_NEVER_SEARCH;

    /**
     * 搜索框
     */
    private XEditText etSearch;
    private LinearLayout llytResult; // 搜索结果
    private LinearLayout llytNotResult; // 没有结果
    private ListView lvResult; // 搜索结果列表
    private SearchUserAdapter userAdapter; // 搜索结果适配器
    private List<SearchUserResp.ItemsBean> searchs;
    private View vLoading;
    private RelativeLayout rlytLoading;
    private LinearLayout llytErrorUI;
    private AnimationDrawable animation;

    /**
     * 搜索附近地点结果列表的adapter
     */
    private SearchNearResultAdapter nearsAdapter;
    private TextView tvNoResultTip;

    private boolean isSearching = false;
    private String keyword = "";
    private int searchType = SEARCH_USER;
    private LocationInfo cityInfo;
    private SearchNearManager searchManager;
    private SearchNearResultListener locationListener;
    private LinearLayout llytThis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutId(R.layout.activity_to_search);
        super.onCreate(savedInstanceState);

        // cwz 虚拟按键适配需要改变背景颜色

        readIntentDate();

        findView();

        initView();

        initLocationDate();
    }

    /**
     * 获取定位和搜索附近成功和失败的监听类
     *
     * @return
     */
    private SearchNearResultListener getLocationListener() {
        if (locationListener == null) {
            locationListener = new SearchNearResultListener(this,
                    new OnSearchResultListener() {
                        @Override
                        public void onSearchNearSucceed(List<PoiItem> pois) {
                            CommonUtil.testLog("onSearchNearSucceed");
                            isSearching = false;
                            if (pois != null && pois.size() > 0) {
                                showResultForSearchNear(pois);
                            } else {
                                showNoResult();
                            }
                        }

                        @Override
                        public void onSearchNearFailed() {
                            isSearching = false;
                            showErrorUI("请尝试重新搜索", null, null); // TODO
                        }

                        @Override
                        public void onLocationSucceed(LocationInfo info) {
                        }

                        @Override
                        public void onLocationFailed(String erreMsg) {
                        }
                    });
        }
        return locationListener;
    }

    private void initLocationDate() {
        if (searchType == SEARCH_NEAR) {
            searchManager = new SearchNearManager(this, getLocationListener());
        }
    }

    /**
     * 读取intent中的数据
     */
    private void readIntentDate() {
        Intent intent = getIntent();
        if (intent != null) {
            searchType = intent.getIntExtra(INTENT_EXTRA_NAME_SEARCH_TYPE_INT,
                    SEARCH_USER);
            String cityStr = intent
                    .getStringExtra(INTENT_KEY_CITY_LOCATIONINFO);
            try {
                cityInfo = new Gson().fromJson(cityStr, LocationInfo.class);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 初始化组件
     */
    private void initView() {
        initEtSearch();
        initResultList();
        initNoResultTip();
        initCancel();
        initLoadingUI();
        initErrorUI();
        setClickCloseInput();
    }

    private void setClickCloseInput() {
        llytThis.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeInputSoft();
            }
        });

        lvResult.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                closeInputSoft();
                return false;
            }
        });
    }

    private void initErrorUI() {
        llytErrorUI.setVisibility(View.GONE);
    }

    private void initLoadingUI() {
        rlytLoading.setVisibility(View.GONE);
        vLoading.setBackgroundResource(R.drawable.anim_loading);
    }

    /**
     * 初始化没有搜索出结果时提示的文字
     */
    private void initNoResultTip() {
        llytNotResult.setVisibility(View.GONE);
        if (searchType == SEARCH_NEAR) {
            tvNoResultTip.setText(StrUtils.getResourcesStr(
                    ToSearchActivity.this, R.string.no_find_this_result));
        } else if (searchType == SEARCH_USER) {
            tvNoResultTip.setText(StrUtils.getResourcesStr(
                    ToSearchActivity.this, R.string.no_these_user));
        }
    }

    private void initCancel() {
        TextView tvCancel = (TextView) this.findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ToSearchActivity.this.finish();
            }
        });
    }


    private void initResultList() {
        llytResult.setVisibility(View.GONE);
        if (searchType == SEARCH_USER) {
            searchs = new ArrayList<>();
            userAdapter = new SearchUserAdapter(ToSearchActivity.this,searchs);
            lvResult.setAdapter(userAdapter);
        } else if (searchType == SEARCH_NEAR) {
            nearsAdapter = new SearchNearResultAdapter(ToSearchActivity.this,
                    new ArrayList<PoiItem>(), new OnClickItemListener() {
                @Override
                public void onclickItem(LocationInfo info) {
                    Intent intent = new Intent();
                    String json = new Gson().toJson(info);
                    intent.putExtra(INTENT_KEY_SELECTED_RESULT, json);
                    ToSearchActivity.this.setResult(
                            RESULT_CODE_SELECTED_NEAR, intent);
                    ToSearchActivity.this.finish();
                }
            }, cityInfo);
            lvResult.setAdapter(nearsAdapter);
        }
    }

    private void updateUserResultList() {
        if (searchType == SEARCH_USER) {
            userAdapter.notifyDataSetChanged();
        }
    }

    private void updateNearResultList(List<PoiItem> pois) {
        if (searchType == SEARCH_NEAR) {
            nearsAdapter.setLocations(pois);
            userAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 初始化搜索框
     */
    private void initEtSearch() {
        etSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        if (searchType == SEARCH_USER) {
            etSearch.setHint(R.string.search_user);
        } else if (searchType == SEARCH_NEAR) {
            etSearch.setHint(R.string.search_near);
        }
        etSearch.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (etSearch != null && etSearch.getText() != null) {
                        String keyword = etSearch.getText().toString().trim();
                        Search(keyword);
                    }
                }
                return false;
            }
        });
        etSearch.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etSearch.setHint("");
                } else {
                    if (searchType == SEARCH_USER) {
                        etSearch.setHint(R.string.search_user);
                    } else if (searchType == SEARCH_NEAR) {
                        etSearch.setHint(R.string.search_near);
                    }
                }
            }
        });
    }

    /**
     * 搜索
     *
     * @param keyword 关键词
     */
    private void Search(String keyword) {
        if (!isSearching) {
            if (StrUtils.hasContent(keyword)) {
                requestSearch(keyword);
            } else {
                makeToast(StrUtils.getResourcesStr(ToSearchActivity.this,
                        R.string.search_content_can_t_null)); // TODO
            }
        } else {
            makeToast("正在搜索中，请等待搜索完成后再操作"); // TODO
        }
    }

    /**
     * 发出搜索请求
     *
     * @param keyword
     */
    private void requestSearch(String keyword) {
        if (etSearch != null && StrUtils.hasContent(keyword)) {
            if (searchType == SEARCH_USER) {
                LoginUser user = UserInfoUtil.getInstance().getCurrentUser();
                if (user != null) {
                    UsersHelper.getSingleTon().searchUsers(this,keyword,searchListener);
                    isSearching = true;
                    showLoadingView();
                } else {
                    toLoginActivity();
                }
            } else if (searchType == SEARCH_NEAR) {
                searchManager.queryNearList(keyword, cityInfo.getLatitude(),
                        cityInfo.getLongitude(), cityInfo.getCityCode());
                isSearching = true;
                showLoadingView();
            }
            updateSearchText(keyword);
        }
    }

    IOnResponseListener<Response<SearchUserResp>> searchListener = new IOnResponseListener<Response<SearchUserResp>>() {
        @Override
        public void onSuccess(Response<SearchUserResp> response) {
            isSearching = false;
            searchs.clear();
            searchs.addAll(response.getData().getItems());

            if (searchs.size() > 0) {
                showResultForSearchUser();
            } else {
                showNoResult();
            }
        }

        @Override
        public void onError(Response response) {
            isSearching = false;
            showErrorUI("尝试重新搜索", "刷新", new OnClickListener() {
                @Override
                public void onClick(View v) {
                    String keyword = etSearch.getText().toString().trim();
                    Search(keyword);
                }
            });
        }
    };

    private void toLoginActivity() {
        ToSearchActivity.this.startActivity(new Intent(ToSearchActivity.this,
                LoginActivity.class));
        StringUtil.showSnack(ToSearchActivity.this, R.string.please_to_login);
    }

    /**
     * 更新搜索框里的内容
     *
     * @param keyword 更新后要显示的文字
     */
    public void updateSearchText(String keyword) {
        if (keyword != null) {
            etSearch.setText(keyword);
            etSearch.setSelection(keyword.length());
            this.keyword = keyword;
        }
    }

    /**
     * 绑定组件
     */
    private void findView() {
        etSearch = (XEditText) this.findViewById(R.id.et_search_user);
        llytNotResult = (LinearLayout) this.findViewById(R.id.llyt_not_result);
        llytResult = (LinearLayout) this.findViewById(R.id.llyt_search_result);
        lvResult = (ListView) this.findViewById(R.id.lv_search_result);
        tvNoResultTip = (TextView) this.findViewById(R.id.tv_no_result_tip);
        rlytLoading = (RelativeLayout) this.findViewById(R.id.rlyt_loading);
        vLoading = this.findViewById(R.id.v_loading);
        llytErrorUI = (LinearLayout) this
                .findViewById(R.id.llyt_network_anomaly);
        llytThis = (LinearLayout) this.findViewById(R.id.llyt_this);
    }

    private void showNoResult() {
        if (animation != null && animation.isRunning()) {
            animation.stop();
        }

        llytNotResult.setVisibility(View.VISIBLE);
        llytResult.setVisibility(View.GONE);
        rlytLoading.setVisibility(View.GONE);
        llytErrorUI.setVisibility(View.GONE);
        searchStatus = STATUS_NO_RESULT;
    }

    private void showResultForSearchUser() {
        if (searchType == SEARCH_USER) {
            if (animation != null && animation.isRunning()) {
                animation.stop();
            }

            llytResult.setVisibility(View.VISIBLE);
            llytNotResult.setVisibility(View.GONE);
            rlytLoading.setVisibility(View.GONE);
            llytErrorUI.setVisibility(View.GONE);
            searchStatus = STATUS_HAS_RESULT;
            updateUserResultList();
        }
    }

    private void showResultForSearchNear(List<PoiItem> pois) {
        if (searchType == SEARCH_NEAR) {
            if (animation != null && animation.isRunning()) {
                animation.stop();
            }

            llytResult.setVisibility(View.VISIBLE);
            llytNotResult.setVisibility(View.GONE);
            rlytLoading.setVisibility(View.GONE);
            llytErrorUI.setVisibility(View.GONE);
            searchStatus = STATUS_HAS_RESULT;
            updateNearResultList(pois);
        }
    }

    private void showLoadingView() {
        rlytLoading.setVisibility(View.VISIBLE);
        llytNotResult.setVisibility(View.GONE);
        llytResult.setVisibility(View.GONE);
        llytErrorUI.setVisibility(View.GONE);
        animation = (AnimationDrawable) vLoading.getBackground();
        animation.start();
        searchStatus = STATUS_LOADING;
    }

    private void showErrorUI(String tip, String btnText,
                             View.OnClickListener onclickListener) {
        if (animation != null && animation.isRunning()) {
            animation.stop();
        }
        llytErrorUI.setVisibility(View.VISIBLE);
        llytNotResult.setVisibility(View.GONE);
        llytResult.setVisibility(View.GONE);
        rlytLoading.setVisibility(View.GONE);
        setErrorUI(tip, btnText, onclickListener);
        searchStatus = STATUS_ERROR;
    }

    private void setErrorUI(String tip, String btnText,
                            View.OnClickListener onclickListener) {
        TextView btnRefresh = (TextView) llytErrorUI
                .findViewById(R.id.btn_network_anomaly);
        TextView tvTip = (TextView) llytErrorUI
                .findViewById(R.id.tv_error_ui_tip);
        tvTip.setText(tip);
        if (StrUtils.hasContent(btnText)) {
            btnRefresh.setVisibility(View.VISIBLE);
            btnRefresh.setText(btnText);
            btnRefresh.setOnClickListener(onclickListener);
        } else {
            btnRefresh.setVisibility(View.GONE);
        }
    }

}
