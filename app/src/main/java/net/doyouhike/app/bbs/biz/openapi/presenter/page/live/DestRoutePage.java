package net.doyouhike.app.bbs.biz.openapi.presenter.page.live;

import android.content.Context;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

import net.doyouhike.app.bbs.biz.openapi.presenter.page.base.PageBase;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.DestRouteItem;
import net.doyouhike.app.bbs.biz.newnetwork.model.request.get.DestRouteListReq;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.DestRouteListResp;
import net.doyouhike.app.bbs.biz.openapi.request.routes.DestsNodesChildGet;
import net.doyouhike.app.bbs.biz.openapi.response.routes.DestsRouteChildResp;
import net.doyouhike.app.bbs.ui.adapter.live.DestRouteAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 通过关键字获取目的地线路列表
 * Created by zengjiang on 16/5/31.
 */
public class DestRoutePage extends PageBase<DestsRouteChildResp.ChildDestsBean> {

    public DestRoutePage(Context context, String node_slug) {
        items = new ArrayList<>();
        mRequest = new DestsNodesChildGet(node_slug);

        adapter = new DestRouteAdapter(context, items);
    }


    @Override
    public DestsNodesChildGet getRequestParam() {
        return (DestsNodesChildGet) mRequest;
    }


    /**
     * 网络数据转换
     *
     * @param response  网络响应
     * @param isRefresh 是否刷新
     */
    @Override
    public void handleResponse(Response response, boolean isRefresh) {
        //数据转换
        List<DestsRouteChildResp.ChildDestsBean> tempItems = ((Response<DestsRouteChildResp>) response).getData().getChild_dests();
//        更新数据
        updateItem(tempItems, isRefresh);

    }

    @Override
    public PullToRefreshBase.Mode needPullDownRefresh() {
        return PullToRefreshBase.Mode.PULL_FROM_END;
    }
}
