package net.doyouhike.app.bbs.ui.widget.road;

import android.content.Context;

import net.doyouhike.app.bbs.base.util.BaseEventBus;
import net.doyouhike.app.bbs.biz.entity.road.RoadListType;
import net.doyouhike.app.bbs.biz.event.road.RoadTypeSelectedEvent;
import net.doyouhike.app.bbs.ui.widget.common.dialog.BottomPopupDialog;

import java.util.List;


/**
 * 作者:zhulin
 * 描述:线路列表  类型 dailog
 */
public class RoadLitTypeDialog extends BottomPopupDialog<RoadListType> implements BottomPopupDialog.BottomDialogListener <RoadListType> {



    public RoadLitTypeDialog(Context context) {
        super(context);
        setListener(this);
        setSelectItem(RoadListType.getDefaultItem());
    }



    public void setTypeList(List<RoadListType> typeList) {
        upDataItem(typeList);
    }



    @Override
    public void onDismiss() {

    }

    @Override
    public void setSelectTopic(RoadListType roadListType) {

        RoadTypeSelectedEvent event = new RoadTypeSelectedEvent();
        event.setRoadListType(roadListType);
        BaseEventBus.postEvent(event);
        RoadLitTypeDialog.this.dismiss();
    }




}
