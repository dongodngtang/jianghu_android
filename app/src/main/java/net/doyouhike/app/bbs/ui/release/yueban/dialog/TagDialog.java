package net.doyouhike.app.bbs.ui.release.yueban.dialog;

import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.util.BaseDialog;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.BaseTag;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.GetEventTypeSucRepo;
import net.doyouhike.app.bbs.biz.openapi.presenter.EventHelper;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventTypesResp;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.NodeTimeline;
import net.doyouhike.app.bbs.ui.home.MyGridView;
import net.doyouhike.app.bbs.ui.release.yueban.PresenterEvent;
import net.doyouhike.app.bbs.util.UIUtils;
import net.doyouhike.app.library.ui.uistate.UiState;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Filework:
 * Author: luochangdong
 * Date:16-3-15
 */
public class TagDialog extends BaseDialog {

    @InjectView(R.id.gv_tags)
    MyGridView gvTags;
    @InjectView(R.id.ll_tip_view)
    ScrollView llTipView;
    @InjectView(R.id.tv_complete)
    TextView tvComplete;

    EventTypeAdapter adapter;
    List<BaseTag> tagInfos = new ArrayList<>();
    List<BaseTag> typeSelectEntities = new ArrayList<>();

    /**
     * 已选的
     */
    List<BaseTag> selected;

    @InjectView(R.id.tv_tag_cancel)
    TextView tvTagCancel;
    @InjectView(R.id.tv_tag_tip)
    TextView tvTagTip;
    @InjectView(R.id.iv_dialog_share_progress)
    ImageView ivDialogShareProgress;

    private PresenterEvent presenterEvent;

    public TagDialog(PresenterEvent presenterEvent) {
        super(presenterEvent.getContext(), R.style.dialog_full_screen_buttom_up);
        this.presenterEvent = presenterEvent;
    }


    @Override
    protected View getLoadingTargetView() {
        return llTipView;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.dialog_tag;
    }

    @Override
    protected void initViewsAndEvents() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = UIUtils.getValidWidth(getContext()); //设置宽度
        this.setCanceledOnTouchOutside(true);
        lp.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(lp);

        adapter = new EventTypeAdapter(getContext(), typeSelectEntities, this);
        adapter.setTvTagTip(tvTagTip);
        gvTags.setAdapter(adapter);
    }

    @Override
    protected void onFirstUserVisible() {

        tvComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenterEvent.setTagData(tagInfos);
                dismiss();
            }
        });
        tvTagCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        refresh();
    }


    private void refresh() {
        //从网络获取活动类型

        EventHelper.getInstance().getEventTypes(getContext(), getEventTypeListener);
    }


    private void getNetDataError(String errorMsg) {
        if (typeSelectEntities.size() <= 0)
            updateView(UiState.CUSTOM.setMsg(getContext().getString(R.string.common_error_msg),
                    getContext().getString(R.string.try_to_click_refresh)),
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            updateView(UiState.NORMAL);
                            refresh();
                        }
                    });


    }

    public void setSelected(List<BaseTag> entities) {
        selected = entities;
    }


    IOnResponseListener getEventTypeListener = new IOnResponseListener<Response<EventTypesResp>>() {

        @Override
        public void onSuccess(Response<EventTypesResp> response) {

            if (response.getData() != null && response.getData().getItems().size() > 0) {

                typeSelectEntities.clear();
                for (BaseTag item : response.getData().getItems()) {
                    if (selected != null) {
                        for (BaseTag temp : selected) {
                            if (temp.getTag_id().equals(item.getTag_id())) {
                                item.setSelected(true);
                            }
                        }
                    }

                    typeSelectEntities.add(item);
                }
                adapter.notifyDataSetChanged();

            }

        }

        @Override
        public void onError(Response response) {

            getNetDataError(response.getMsg());

        }
    };

}
