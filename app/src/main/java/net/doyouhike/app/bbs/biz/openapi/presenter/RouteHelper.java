package net.doyouhike.app.bbs.biz.openapi.presenter;

import android.content.Context;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.service.ApiReq;
import net.doyouhike.app.bbs.biz.openapi.request.routes.DestsCitiesGet;
import net.doyouhike.app.bbs.biz.openapi.request.routes.DestsNodesChildGet;
import net.doyouhike.app.bbs.biz.openapi.request.routes.DestsNodesDetailGet;
import net.doyouhike.app.bbs.biz.openapi.request.routes.DestsRouteMapsGet;
import net.doyouhike.app.bbs.biz.openapi.request.routes.DestsRouteTypesGet;
import net.doyouhike.app.bbs.biz.openapi.request.routes.DestsRoutesSlugDetailGet;

/**
 * 作者：luochangdong on 16/9/8
 * 描述：
 */
public class RouteHelper {
    private static RouteHelper instance;

    public static RouteHelper getInstance() {
        if (instance == null)
            instance = new RouteHelper();
        return instance;
    }


    /**
     * 获取目的地的子节点
     * @param context
     * @param node_slug
     * @param listener
     */
    public void destsRouteChild(Context context, String node_slug, IOnResponseListener listener) {
        DestsNodesChildGet get = new DestsNodesChildGet(node_slug);
        get.setCancelSign(context);
        ApiReq.doGet(get, listener);
    }

    /**
     * 获取线路的类型
     *
     * @param context
     * @param listener
     */
    public void destsRouteType(Context context, IOnResponseListener listener) {
        DestsRouteTypesGet get = new DestsRouteTypesGet();
        get.setCancelSign(context);
        ApiReq.doGet(get, listener);
    }


    /**
     * 获取目的地详情
     *
     * @param context
     * @param node_slug
     * @param listener
     */
    public void destsNodesDetail(Context context, String node_slug, IOnResponseListener listener) {
        DestsNodesDetailGet get = new DestsNodesDetailGet(node_slug);
        get.setCancelSign(context);
        ApiReq.doGet(get, listener);
    }

    /**
     * 获取轨迹列表
     *
     * @param context
     * @param route_slug
     * @param listener
     */
    public void destsRouteMaps(Context context, String route_slug, IOnResponseListener listener) {
        DestsRouteMapsGet get = new DestsRouteMapsGet(route_slug);
        get.setCancelSign(context);
        ApiReq.doGet(get, listener);
    }

    /**
     * 获取城市列表
     *
     * @param context
     * @param listener
     */
    public void destsCities(Context context, IOnResponseListener listener) {
        DestsCitiesGet get = new DestsCitiesGet();
        get.setCancelSign(context);
        ApiReq.doGet(get, listener);
    }

    /**
     * 获取线路详情
     *
     * @param context
     * @param route_slug
     * @param listener
     */
    public void destsRoutesSlugDetail(Context context, String route_slug, IOnResponseListener listener) {
        DestsRoutesSlugDetailGet get = new DestsRoutesSlugDetailGet(route_slug);
        get.setCancelSign(context);
        ApiReq.doGet(get, listener);
    }

}
