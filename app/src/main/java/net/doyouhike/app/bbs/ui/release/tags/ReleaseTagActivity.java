package net.doyouhike.app.bbs.ui.release.tags;

import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.activity.BaseActivity;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.BaseTag;
import net.doyouhike.app.bbs.biz.openapi.presenter.NodesHelper;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.NodeTimeline;
import net.doyouhike.app.bbs.biz.openapi.response.users.MinilogTagResp;
import net.doyouhike.app.bbs.ui.release.PresenterEditLive;
import net.doyouhike.app.library.ui.eventbus.EventCenter;
import net.doyouhike.app.library.ui.uistate.UiState;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import de.greenrobot.event.EventBus;

/**
 * Filework:
 * Author: luochangdong
 * Date:16-1-27
 */
public class ReleaseTagActivity extends BaseActivity {
    @InjectView(R.id.tv_complete)
    TextView tvComplete;
    /**
     * 常规标签
     */
    @InjectView(R.id.gv_tags)
    GridView gvTags;
    /**
     * 热门标签
     */
    @InjectView(R.id.gv_s_tags)
    GridView gvSTags;
    @InjectView(R.id.ll_tip_view)
    LinearLayout ll_tip_view;

    public final static int RESULT_CODE_FINISH_ADD_TAGS = 1823;
    public final static String INTENT_EXTRA_RELEASE_TAG = "release_tag";

    TagsAdapter sTagsAdapter;
    TagsAdapter tagsAdapter;
    public List<BaseTag> tagInfos = new ArrayList<>();
    @InjectView(R.id.line_add_tag)
    View lineAddTag;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_release_tags;
    }

    @Override
    protected View getLoadingTargetView() {
        return ll_tip_view;
    }

    @Override
    protected void initViewsAndEvents() {


        tvComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new EventCenter<>(Content.RELEASE_SELECT_TAGS, tagInfos));
                finish();
            }
        });
        refresh();
    }

    private void refresh() {
        updateView(UiState.LOADING);
        NodesHelper.getInstance().getMinilogTag(this,listener);
        tvComplete();
    }

    IOnResponseListener<Response<MinilogTagResp>> listener = new IOnResponseListener<Response<MinilogTagResp>>() {
        @Override
        public void onSuccess(Response<MinilogTagResp> response) {
            initData(response.getData());
        }

        @Override
        public void onError(Response response) {
            getNetDataError(response.getMsg());
        }
    };


    public void initData(final MinilogTagResp tagResp) {
        if (null == tagResp && null == tagResp.getTags()) {
            uiStateController.updateView(UiState.ERROR, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   refresh();
                }
            });
            return;
        }
        tagInfos.clear();
        updateView(UiState.NORMAL);
        initSelected(tagResp);

        if (tagResp.getS_tags() == null || tagResp.getS_tags().isEmpty()) {
            lineAddTag.setVisibility(View.GONE);
        } else {
            lineAddTag.setVisibility(View.VISIBLE);
        }

        if (sTagsAdapter == null) {
            sTagsAdapter = new TagsAdapter(this, tagResp.getS_tags());
            gvSTags.setAdapter(sTagsAdapter);

        } else {
            sTagsAdapter.notifyDataSetChanged();
        }
        if (tagsAdapter == null) {
            tagsAdapter = new TagsAdapter(this, tagResp.getTags());
            gvTags.setAdapter(tagsAdapter);
        } else {
            tagsAdapter.notifyDataSetChanged();
        }

        tvComplete();

    }

    private void initSelected(MinilogTagResp getMinilogTagResp) {

        for (BaseTag selected : PresenterEditLive.tags) {

            for (BaseTag stag : getMinilogTagResp.getS_tags()) {

                if (selected.getTag_id().equals(stag.getTag_id())) {
                    stag.setSelected(true);
                }
            }

            for (BaseTag tag : getMinilogTagResp.getTags()) {
                if (selected.getTag_id().equals(tag.getTag_id())) {
                    tag.setSelected(true);
                }
            }
        }

    }

    public void getNetDataError(String errorMsg) {
        if (sTagsAdapter == null) {

            updateView(UiState.CUSTOM.setMsg(getString(R.string.common_error_msg),
                    getString(R.string.try_to_click_refresh)),
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            updateView(UiState.LOADING);
                            refresh();
                        }
                    });

        }
    }

    public void tvComplete() {
        if (tvComplete == null)
            return;
        if (tagInfos.size() > 0) {
            tvComplete.setAlpha(1);
            tvComplete.setClickable(true);
        } else {
            tvComplete.setAlpha(0.5f);
            tvComplete.setClickable(false);
        }
    }

    public void tipExceeding() {
        showToast("最多可选3个标签");
    }

    public void rollBack(View view) {
        finish();
    }



}
