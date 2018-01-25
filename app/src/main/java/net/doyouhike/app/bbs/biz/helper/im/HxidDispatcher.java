package net.doyouhike.app.bbs.biz.helper.im;

import net.doyouhike.app.bbs.biz.db.green.entities.ChatUserInfo;
import net.doyouhike.app.bbs.biz.event.im.HxidEvent;
import net.doyouhike.app.bbs.biz.helper.im.base.BaseAction;
import net.doyouhike.app.bbs.biz.helper.im.base.BaseDispatcher;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;

import java.util.Vector;

/**
 * Created by zengjiang on 16/7/30.
 */

public class HxidDispatcher extends BaseDispatcher {

    private static HxidDispatcher instance = new HxidDispatcher();

    private Vector<String> requestList = new Vector<>();


    HxidStore store;

    public static HxidDispatcher getInstance() {
        return instance;
    }

    public HxidDispatcher() {
        store = new HxidStore(this);
    }


    @Override
    public void dispatch(BaseAction action) {
        store.handleAction(action);
    }


    public void onCallBackError(String userId, Response response,String action) {
        removeList(userId);
        postEvent(HxidEvent.getFaileEvent(response.getMsg(), response.getCode(), userId,action));

    }

    public void callBackSuccess(String userId, ChatUserInfo info,String action) {
        removeList(userId);
        postEvent(HxidEvent.getSuccessEvent(info, userId,action));

    }


    public void postEvent(HxidEvent event) {

        onCallBack(event);

    }

    public boolean addList(String userId) {
        synchronized (requestList) {
            if (!requestList.contains(userId)) {
                requestList.add(userId);
                return true;
            }
        }
        return false;
    }

    public void removeList(String userId) {
        synchronized (requestList) {
            if (requestList.contains(userId)) {
                requestList.remove(userId);
            }
        }
    }

}
