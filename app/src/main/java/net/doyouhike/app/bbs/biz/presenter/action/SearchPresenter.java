package net.doyouhike.app.bbs.biz.presenter.action;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.helper.list_helper.SimpleListHelper;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.service.ApiReq;
import net.doyouhike.app.bbs.biz.nohttp.CallServer;
import net.doyouhike.app.bbs.biz.openapi.presenter.page.base.IPage;
import net.doyouhike.app.bbs.biz.openapi.presenter.page.base.State;
import net.doyouhike.app.bbs.biz.openapi.presenter.page.search.ActionSearchPage;
import net.doyouhike.app.bbs.biz.openapi.presenter.page.search.RoadSearchPage;
import net.doyouhike.app.bbs.biz.openapi.request.BaseListGet;
import net.doyouhike.app.bbs.ui.activity.action.ActionAndRoadSearchActivity;
import net.doyouhike.app.bbs.util.UIUtils;
import net.doyouhike.app.library.ui.uistate.UiState;

/**
 * 功能：搜索活动线路辅助控制器
 *
 * @author：曾江 日期：16-5-9.
 */
public class SearchPresenter {

    /**
     * 宿主activity
     */
    ActionAndRoadSearchActivity activity;
    /**
     * 活动标签页
     */
    ActionSearchPage mActionPage;
    /**
     * 线路标签页
     */
    RoadSearchPage mRoadPage;
    /**
     * 用于标记当前标签页
     */
    IPage mCurrentPage;

    /**
     * 列表加载助手
     */
    SimpleListHelper mListHelper;

    public SearchPresenter(ActionAndRoadSearchActivity activity) {
        this.activity = activity;
        //搜索按钮监听
        activity.getEtActivityKeyWord().setOnEditorActionListener(editorActionListener);

        mActionPage = new ActionSearchPage(activity);
        mRoadPage = new RoadSearchPage(activity);

        mListHelper = new SimpleListHelper(activity.getmDataListView(), activity, mActionPage) {

            /**
             * 从网络请求数据
             * @param param     request params
             * @param isRefresh true is refresh,false is get more data
             */
            @Override
            protected void getDataFromNet(BaseListGet param, boolean isRefresh) {

                getPage().setState(State.LOADING);

                if (mCurrentPage == mActionPage) {
                    //当前页为活动页,则搜索活动数据
                    getActionData(param, isRefresh);
                } else {
                    //当前页为线路页,则搜索线路数据
                    getRoadData(param, isRefresh);
                }
            }


            /**
             * 覆盖updateView方法
             * @param uiState
             * @param listener
             */
            @Override
            public void updateView(UiState uiState, View.OnClickListener listener) {


                if (uiState==UiState.EMPTY){
                    //更换空提示图片,不重试按钮
                    super.updateView(UiState.CUSTOM.setImgId(R.drawable.icon_search_empty).setMsg(uiState.getMsg()),null);
                }else {
                    super.updateView(uiState,listener);
                }
            }

        };
    }

    /**
     * activity销毁调用
     */
    public void onDestroy() {
        mListHelper.onDestroy();
        activity = null;
    }

    /**
     * @param isAction 切换页面,是否活动标签
     */
    public void checkoutPage(boolean isAction) {
        mCurrentPage = isAction ? mActionPage : mRoadPage;
        mListHelper.checkoutPage(mCurrentPage);


        if (mCurrentPage == mActionPage) {
            //设置搜索框关键词
            activity.getEtActivityKeyWord().setText(mActionPage.getRequestParam().getKey_word());
            activity.getEtActivityKeyWord().setHint("活动标题、目的地、线路");
        } else {
            //设置搜索框关键词
            activity.getEtActivityKeyWord().setText(mRoadPage.getRequestParam().getKeyword());
            activity.getEtActivityKeyWord().setHint("线路信息、编号");
        }
        //请求焦点,光标设置最后
        activity.getEtActivityKeyWord().setSelection(activity.getEtActivityKeyWord().length());



        if (mCurrentPage.getItems().isEmpty()&&mCurrentPage.getState()==State.NORMAL){
            //列表为空且正常状态切换显示键盘
                UIUtils.showSoftInput(activity.getEtActivityKeyWord(),true);

        }else {
            //所在页面列表不为空,错误或加载中页面不显示键盘
            UIUtils.showSoftInput(activity.getEtActivityKeyWord(),false);
        }
    }



    /**
     * editText搜索监听器
     */
    private final EditText.OnEditorActionListener editorActionListener = new TextView.OnEditorActionListener() {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == KeyEvent.ACTION_DOWN
                    || actionId == EditorInfo.IME_ACTION_SEARCH) {
                // 没有输入关键字，搜索框失去焦点，收起键盘
                UIUtils.showSoftInput(activity.getEtActivityKeyWord(),false);
                // 输入的关键字
                String keyWord = activity.getEtActivityKeyWord().getText().toString();
                if (TextUtils.isEmpty(keyWord)) {
                    activity.getEtActivityKeyWord().clearFocus();
                    return true;
                }

                setKeyWord(keyWord);
                //不为空打开对话框
                if (!mListHelper.getItems().isEmpty()){
                    mListHelper.getItems().clear();
                }
                // 开始联网获取数据
                mListHelper.getData(true);

            }
            return true;
        }
    };

    /**
     * 设置搜索关键词
     * @param keyWord 搜索关键词
     */
    private void setKeyWord(String keyWord) {
        if (mListHelper.getPage() instanceof ActionSearchPage) {
            ((ActionSearchPage) mListHelper.getPage()).getRequestParam().setKey_word(keyWord);
        } else {
            mRoadPage.getRequestParam().setKeyword(keyWord);
            mRoadPage.getAdapter().setKeyword(keyWord);
        }
    }

    /**
     * 搜索活动
     *
     * @param param     请求参数
     * @param isRefresh 是否刷新
     */
    private void getActionData(BaseListGet param, final boolean isRefresh) {

        if (!TextUtils.isEmpty(mActionPage.getRequestTag())) {
            CallServer.getRequestInstance().cancelBySign(mActionPage.getRequestTag());
        }

        ApiReq.doGet(param, new IOnResponseListener<Response>() {
            @Override
            public void onSuccess(Response response) {


                if (mCurrentPage == mActionPage) {
                    //线路界面处理
                    mListHelper.handleResponse(response, isRefresh);
                }else {

                    //处理响应
                    mActionPage.handleResponse(response, isRefresh);
                }
            }

            @Override
            public void onError(Response response) {

                if (mCurrentPage == mActionPage) {
                    //线路界面处理
                    mListHelper.handleError(response, isRefresh);
                }else {
                    //处理错误响应
                    mActionPage.handleError(response, isRefresh);
                }
            }
        });
    }

    /**
     * 搜索线路
     * @param param     请求参数
     * @param isRefresh 是否刷新
     */
    private void getRoadData(BaseListGet param, final boolean isRefresh) {

        if (!TextUtils.isEmpty(mRoadPage.getRequestTag())) {
            //停止上次请求
            CallServer.getRequestInstance().cancelBySign(mRoadPage.getRequestTag());
        }

        ApiReq.doGet(param, new IOnResponseListener<Response>() {
            @Override
            public void onSuccess(Response response) {

                if (mCurrentPage == mRoadPage) {
                    //线路界面处理
                    mListHelper.handleResponse(response, isRefresh);
                }else {
                    //处理响应
                    mRoadPage.handleResponse(response, isRefresh);
                }
            }

            @Override
            public void onError(Response response) {
                if (mCurrentPage == mRoadPage) {
                    //线路界面处理
                    mListHelper.handleError(response, isRefresh);
                }else {
                    //处理错误响应
                    mRoadPage.handleError(response, isRefresh);
                }
            }
        });
    }
}
