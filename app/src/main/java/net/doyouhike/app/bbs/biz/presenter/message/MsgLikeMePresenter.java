package net.doyouhike.app.bbs.biz.presenter.message;

import android.view.View;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.doyouhike.app.bbs.biz.event.live.CheckoutMainPageEvent;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.model.request.get.message.GetLikeMeListParam;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.message.GetCommentMeListResp;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.message.GetLikeMeListResp;
import net.doyouhike.app.bbs.biz.newnetwork.service.ApiReq;
import net.doyouhike.app.bbs.ui.activity.MainActivity;
import net.doyouhike.app.bbs.ui.activity.message.MsgLikedActivity;
import net.doyouhike.app.bbs.ui.adapter.message.MsgLikedAdapter;
import net.doyouhike.app.bbs.util.LogUtil;
import net.doyouhike.app.bbs.util.PullToRefreshLVUtil;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.UserInfoUtil;
import net.doyouhike.app.library.ui.uistate.UiState;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 作者：luochangdong on 16/8/31
 * 描述：
 */
public class MsgLikeMePresenter {
    private int PAGE = 1;
    private int LIMIT = 10;
    PullToRefreshListView pullToRefreshListView;
    MsgLikedAdapter adapter;
    List<GetLikeMeListResp.UsersBean> datas;
    MsgLikedActivity baseActivity;

    public MsgLikeMePresenter(MsgLikedActivity activty) {
        datas = new ArrayList<>();
        baseActivity = activty;
//        adapter = new MsgLikedAdapter(activty,datas);
        pullToRefreshListView = baseActivity.getLvMsgLikeList();
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

    public void getLikeMeList(){
        String token = UserInfoUtil.getInstance().getToken();
        if(StringUtil.isEmpty(token))
            return;
        GetLikeMeListParam param = new GetLikeMeListParam();
        param.setToken(token);
        param.setPage_size(LIMIT);
        param.setPage_index(getPageSize());
        param.setExtraInfo(true);//从列表获取标志 当点击列表后, 全部消息视为已读,数据清零

        ApiReq.doGet(param,listener);

    }

    public static void getLikeListFrag(){
        String token = UserInfoUtil.getInstance().getToken();
        if(StringUtil.isEmpty(token))
            return;
        GetLikeMeListParam param = new GetLikeMeListParam();
        param.setToken(token);
        param.setPage_size(1);
        param.setPage_index(1);

        ApiReq.doGet(param,listenerFrag);
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

    IOnResponseListener<Response<GetLikeMeListResp>> listener = new IOnResponseListener<Response<GetLikeMeListResp>>() {
        @Override
        public void onSuccess(Response<GetLikeMeListResp> response) {
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
                }else{
                    adapter.notifyDataSetChanged();
                }
            }
            pullToRefreshListView.onRefreshComplete();
        }

        @Override
        public void onError(Response response) {
            baseActivity.updateView(UiState.ERROR.setMsg("出错啦!","可尝试点按刷新"), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    baseActivity.updateView(UiState.LOADING);
                    refresh();
                }
            });
        }
    };


    public void refresh(){
        PAGE = 1;
        getLikeMeList();
    }
    private int getPageSize() {
        double size = datas.size();
        double limit = LIMIT;
        int page = (int) Math.ceil(size/ limit);
        LogUtil.d("page: "+page+" size: "+datas.size());
        return page + 1;
    }
    public void getMore(){
        getLikeMeList();
    }
}
