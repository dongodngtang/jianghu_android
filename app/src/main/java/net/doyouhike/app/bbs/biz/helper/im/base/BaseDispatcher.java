package net.doyouhike.app.bbs.biz.helper.im.base;

import de.greenrobot.event.EventBus;

/**
 * Created by zengjiang on 16/7/30.
 */

public abstract class BaseDispatcher  {

    public BaseDispatcher() {
    }

    public abstract void dispatch(BaseAction action) ;

    public void onCallBack(Object event){
        EventBus.getDefault().post(event);
    }
}
