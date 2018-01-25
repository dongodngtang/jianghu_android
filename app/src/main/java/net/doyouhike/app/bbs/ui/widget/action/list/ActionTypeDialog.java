package net.doyouhike.app.bbs.ui.widget.action.list;

import net.doyouhike.app.bbs.biz.newnetwork.model.bean.BaseTag;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.GetEventTypeSucRepo;
import net.doyouhike.app.bbs.ui.widget.action.ViActionContent;
import net.doyouhike.app.bbs.ui.widget.common.dialog.BottomPopupDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动列表, 活动类型 条件筛选
 * Created by zengjiang on 16/5/27.
 */
public class ActionTypeDialog extends BottomPopupDialog<BaseTag>
        implements BottomPopupDialog.BottomDialogListener<BaseTag> {


    /**
     * 默认数据,全部类型
     */
    private final String DEFAULT_ALL_TYPE_ID = "0";
    private final String DEFAULT_ALL_TYPE_NAME = "全部类型";

    /**
     *活动类型列表
     */
    private ArrayList<BaseTag> eventTypes = new ArrayList<>();
    private ViActionContent viActionContent;

    public ActionTypeDialog(ViActionContent viActionContent) {
        super(viActionContent.getContext());
        this.viActionContent = viActionContent;
        setListener(this);
        //设置默认选中项
        setSelectItem(new BaseTag(DEFAULT_ALL_TYPE_ID));
        //绑定数据,null 绑定默认项 全部
        updateEventData(null);
    }


    @Override
    public void onDismiss() {
        //是否选中第一项
        boolean isFirst=getSelectItem().getTag_id() == DEFAULT_ALL_TYPE_ID;
        viActionContent.onActionTypeDialogDismiss(isFirst);
    }

    @Override
    public void setSelectTopic(BaseTag type) {

        int id = Integer.parseInt(type.getTag_id());
        String name = type.getTag_name();

        viActionContent.setActionTypeSelected(name, id);
    }

    /**
     * @param items 更新活动类型数据
     */
    public void updateEventData(List<BaseTag> items) {
        eventTypes.clear();

        /**
         * 重新填充数据,第一项添加全部
         */
        BaseTag firstItem = new BaseTag();
        firstItem.setTag_id(DEFAULT_ALL_TYPE_ID);
        firstItem.setTag_name(DEFAULT_ALL_TYPE_NAME);
        eventTypes.add(firstItem);

        if (null != items)
            eventTypes.addAll(items);


        upDataItem(eventTypes);
    }

    public boolean isEmpty() {
        //默认项为空
        return eventTypes.size() <= 1;
    }
}
