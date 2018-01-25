package net.doyouhike.app.bbs.ui.widget.action.list;

import android.view.Gravity;
import android.view.WindowManager;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.ui.widget.action.ViActionContent;
import net.doyouhike.app.bbs.ui.widget.common.dialog.BottomPopupDialog;
import net.doyouhike.app.bbs.util.DensityUtil;
import net.doyouhike.app.bbs.util.UIUtils;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 活动列表, 即将出发 离我最近 条件筛选
 * Created by zengjiang on 16/5/27.
 */
public class ActionMidConditionDialog extends BottomPopupDialog<String> implements BottomPopupDialog.BottomDialogListener<String> {


    private ArrayList<String> items = new ArrayList<>();
    private ViActionContent viActionContent;

    public ActionMidConditionDialog(ViActionContent viActionContent) {
        super(viActionContent.getContext());
        this.viActionContent = viActionContent;
        setListener(this);
        updateData();
    }


    @Override
    public void onDismiss() {

        int index = items.indexOf(getSelectItem());
        //是否选中第一项
        viActionContent.onMidConditionDismiss(index == 0);
    }

    @Override
    public void setSelectTopic(String s) {

        //"search_type": 1/2 , 1、即将出发 2、离我最近（需传经纬度）
        viActionContent.setMidConditionSelected(s, items.indexOf(s)+1);
    }


    /**
     * 绑定数据
     */
    private void updateData() {

        String[] strings = getContext().getResources().getStringArray(R.array.EventTimeData);
        Collections.addAll(items, strings);
        //设置默认选中项
        setSelectItem(items.get(0));
        upDataItem(items);
    }

    public boolean isEmpty() {
        return items.size() <= 1;
    }
}
