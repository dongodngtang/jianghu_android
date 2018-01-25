package net.doyouhike.app.bbs.biz.helper.im.action;

import net.doyouhike.app.bbs.biz.db.dao.UserInfoDbUtil;
import net.doyouhike.app.bbs.biz.entity.user.SearchEmIdResult;
import net.doyouhike.app.bbs.biz.helper.im.HxidDispatcher;
import net.doyouhike.app.bbs.biz.helper.im.base.BaseAction;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.HxUserInfo;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.GetUserImResponse;
import net.doyouhike.app.bbs.biz.openapi.request.users.ims.UsersImsGet;

import java.util.List;

/**
 * 批量请求环信ID
 * Created by zengjiang on 16/7/30.
 */

public class RequestIdsAction extends BaseAction<List<String>,GetUserImResponse> {


    private String msg;

    @Override
    public void processResponse(Response<GetUserImResponse> response) {
        UserInfoDbUtil.getInstance().updateImId(response.getData().getItems());
        setAction(IHxIdAction.REQUEST_COMPLETE);
    }

    /**
     * 初步处理本地获取的数据
     *
     * @param dispatcher 分发器,用于成功回调
     */
    @Override
    public void processRequest(HxidDispatcher dispatcher) {
        //从本地获取数据
        SearchEmIdResult result = UserInfoDbUtil.getInstance().getImId(getParams());
        //处理结果
        handleLocalSearchResult(result);
    }

    @Override
    public UsersImsGet getRequestParam() {


        UsersImsGet userImReqParam = new UsersImsGet();

        userImReqParam.setUser_ids(params);
        return userImReqParam;
    }

    /**
     * 处理本地搜索
     * @param result 本地搜索结果
     */
    private void handleLocalSearchResult(SearchEmIdResult result) {


        List<HxUserInfo> items = result.getExistEmIdList();

        removedExist(items);

        if (params.isEmpty()) {
            setAction(IHxIdAction.REQUEST_COMPLETE);
        } else {
            setAction(IHxIdAction.GET_FROM_NET);
        }

    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    /**
     * 移除已经获取到环信ID的用户
     * @param items
     */
    private void removedExist(List<HxUserInfo> items) {

        if (null == items || items.isEmpty()) {
            return;
        }

        synchronized (params) {

            for (HxUserInfo userInfo : items) {

                if (params.contains(userInfo.getUser_uuid())) {
                
                    //移除已经获取到环信ID的用户
                    params.remove(userInfo.getUser_uuid());

                }

            }


        }
    }

}
