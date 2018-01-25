package net.doyouhike.app.bbs.biz.helper.im;

import net.doyouhike.app.bbs.base.application.MyApplication;
import net.doyouhike.app.bbs.biz.helper.im.action.IHxIdAction;
import net.doyouhike.app.bbs.biz.helper.im.base.BaseAction;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;

/**
 * Created by zengjiang on 16/7/30.
 */

public class HxidStore implements GetHxIdTask.HxIdTaskListener {

    private final String TAG = "HxidStore";

    private HxidDispatcher dispatcher;

    public HxidStore(HxidDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void handleAction(BaseAction action) {


        switch (action.getAction()) {
            case IHxIdAction.REQUEST_ID:
                //请求环信ID

                boolean isAdd = dispatcher.addList(String.valueOf(action.getParams()));
                if (isAdd) {
                    processRequest(action);
                }

                break;
            case IHxIdAction.GROUP_MSG:
                //群发消息
                processRequest(action);
                break;
            case IHxIdAction.REQUEST_IDS:
                //批量请求环信ID
                processRequest(action);
                break;
            case IHxIdAction.GET_FROM_NET:
                //从网络获取环信ID
                processGetFromNetAction(action);
                break;
            case IHxIdAction.REQUEST_BY_HX_ID:
                processRequest(action);
                break;
            case IHxIdAction.REQUEST_ERROR:
                break;
            case IHxIdAction.REQUEST_COMPLETE:
                //请求结束
                break;
        }
    }

    /**
     * 群发活动请求
     *
     * @param action
     */
    private void processRequest(BaseAction action) {

        //初步处理, 如先从本地获取
        action.processRequest(dispatcher);
        //进一步处理
        handleAction(action);
    }


    /**
     * 从网络获取数据
     *
     * @param action
     */
    private void processGetFromNetAction(final BaseAction action) {

        MyApplication.getInstance().executorService.submit(new Runnable() {
            @Override
            public void run() {
                GetHxIdTask task = new GetHxIdTask(action);
                task.setListener(HxidStore.this);
                task.getData();
            }
        });

    }


    @Override
    public void onSuccess(BaseAction action) {
        action.callBackSuccess(dispatcher);
        handleAction(action);
    }

    @Override
    public void onError(BaseAction action, Response response) {
        action.onCallBackError(dispatcher,response);
        handleAction(action);
    }
}
