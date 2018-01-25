package net.doyouhike.app.bbs.biz.presenter.message;

import android.view.View;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.doyouhike.app.bbs.biz.event.live.CheckoutMainPageEvent;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.model.request.get.message.GetCommentMeListParam;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.message.GetCommentMeListResp;
import net.doyouhike.app.bbs.biz.newnetwork.service.ApiReq;
import net.doyouhike.app.bbs.ui.activity.MainActivity;
import net.doyouhike.app.bbs.ui.activity.message.MsgCommentMeActivity;
import net.doyouhike.app.bbs.ui.adapter.message.MsgCommentMeAdapter;
import net.doyouhike.app.bbs.util.LogUtil;
import net.doyouhike.app.bbs.util.PullToRefreshLVUtil;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.UserInfoUtil;
import net.doyouhike.app.library.ui.uistate.UiState;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 作者:luochangdong on 16/6/14 15:19
 * 描述:
 */
public class MsgCommoneMePresenter {

    private int PAGE = 1;
    private int LIMIT = 10;
    PullToRefreshListView pullToRefreshListView;
    MsgCommentMeAdapter adapter;
    List<GetCommentMeListResp.UsersBean> datas;
    MsgCommentMeActivity baseActivity;

    public MsgCommoneMePresenter(MsgCommentMeActivity activty) {
        datas = new ArrayList<>();
        baseActivity = activty;
//        adapter = new MsgCommentMeAdapter(activty, datas);
        pullToRefreshListView = baseActivity.getLvMsgCommentList();
        PullToRefreshLVUtil.setRefreshAndLoadLabel(pullToRefreshListView);
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                refresh();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getMore();
            }
        });
        pullToRefreshListView.setAdapter(adapter);
    }

    public void getCommentMeList() {
        String token = UserInfoUtil.getInstance().getToken();
        if (StringUtil.isEmpty(token))
            return;
        GetCommentMeListParam param = new GetCommentMeListParam();
        param.setToken(token);
        param.setPage_size(LIMIT);
        param.setPage_index(getPageSize());
        param.setExtraInfo(true);

        ApiReq.doGet(param, listener);

    }

    public static void getCommentListFrag() {
        String token = UserInfoUtil.getInstance().getToken();
        if (StringUtil.isEmpty(token))
            return;
        GetCommentMeListParam param = new GetCommentMeListParam();
        param.setToken(token);
        param.setPage_size(1);
        param.setPage_index(1);
        param.setExtraInfo(true);

        ApiReq.doGet(param, listenerFrag);
    }

    static IOnResponseListener<Response<GetCommentMeListResp>> listenerFrag = new IOnResponseListener<Response<GetCommentMeListResp>>() {
        @Override
        public void onSuccess(Response<GetCommentMeListResp> response) {
            EventBus.getDefault().post(response.getData().getUsers());
        }

        @Override
        public void onError(Response response) {

        }
    };

    IOnResponseListener<Response<GetCommentMeListResp>> listener = new IOnResponseListener<Response<GetCommentMeListResp>>() {
        @Override
        public void onSuccess(Response<GetCommentMeListResp> response) {

            if (response.getData() == null) {
                baseActivity.updateView(UiState.ERROR.setMsg("出错啦!", "可尝试点按刷新"), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        baseActivity.updateView(UiState.LOADING);
                        refresh();
                    }
                });
            } else {
                baseActivity.updateView(UiState.NORMAL);
                datas.addAll(response.getData().getUsers());
                if (datas.size() <= 0) {
                    baseActivity.updateView(UiState.EMPTY.setMsg("暂无消息", "先去评论别人", "前往热门"), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            baseActivity.finish();
                            EventBus.getDefault().post(new CheckoutMainPageEvent(MainActivity.PAGE_PIECES));
                        }
                    });
                } else {
                    adapter.notifyDataSetChanged();
                }
            }
            pullToRefreshListView.onRefreshComplete();
        }

        @Override
        public void onError(Response response) {
            baseActivity.updateView(UiState.ERROR.setMsg("出错啦!", "可尝试点按刷新"), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    baseActivity.updateView(UiState.LOADING);
                    refresh();
                }
            });
        }
    };


    public void refresh() {
        PAGE = 1;
        getCommentMeList();
    }
    private int getPageSize() {
        double size = datas.size();
        double limit = LIMIT;
        int page = (int) Math.ceil(size/ limit);
        LogUtil.d("page: "+page+" size: "+datas.size());
        return page + 1;
    }
    public void getMore() {

        getCommentMeList();
    }
}
