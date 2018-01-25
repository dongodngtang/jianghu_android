package net.doyouhike.app.bbs.biz.event.live;

import net.doyouhike.app.bbs.biz.entity.dynamic.DesAndRoadInfo;

/**
 *
 * Created by terry on 5/31/16.
 */
public class DesAndRoadListEvent {

    private boolean isSuccess =true;

    private DesAndRoadInfo desAndRoadInfo;



    public DesAndRoadInfo getDesAndRoadInfo() {
        return desAndRoadInfo;
    }

    public void setDesAndRoadInfo(DesAndRoadInfo desAndRoadInfo) {
        this.desAndRoadInfo = desAndRoadInfo;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }


}
