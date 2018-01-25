package net.doyouhike.app.bbs.ui.release;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.activity.BaseActivity;
import net.doyouhike.app.bbs.biz.entity.LocationInfo;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.BaseTag;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.SendingTimeline;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.Timeline;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.NodeTimeline;
import net.doyouhike.app.bbs.ui.release.tags.ReleaseTagActivity;
import net.doyouhike.app.bbs.util.LogUtil;
import net.doyouhike.app.bbs.util.draghelper.OnStartDragListener;
import net.doyouhike.app.library.ui.eventbus.EventCenter;

import java.util.List;

import butterknife.InjectView;

/**
 * 作者:luochangdong on 16/7/5 14:38
 * 描述:
 */
public class NewEditLiveActivity extends BaseActivity implements View.OnClickListener, OnStartDragListener {
    @InjectView(R.id.tv_send)
    TextView tvSend;
    @InjectView(R.id.tv_cancel)
    TextView tvCancel;
    @InjectView(R.id.tv_text_count)
    TextView tvTextCount;
    @InjectView(R.id.rlyt_top_bar)
    RelativeLayout rlytTopBar;
    @InjectView(R.id.et_input)
    EditText etInput;
    @InjectView(R.id.dragGridView)
    RecyclerView mDragGridView;
    @InjectView(R.id.iv_icon_tag)
    ImageView ivIconTag;
    @InjectView(R.id.tv_tag1)
    TextView tvTag1;
    @InjectView(R.id.tv_tag2)
    TextView tvTag2;
    @InjectView(R.id.tv_tag3)
    TextView tvTag3;
    @InjectView(R.id.iv_remove_tags)
    ImageView ivRemoveTags;
    @InjectView(R.id.llyt_tags)
    LinearLayout llytTags;
    @InjectView(R.id.tv_location)
    TextView tvLocation;
    @InjectView(R.id.tv_act_edit_live_dest_tip)
    TextView tvActEditLiveDestTip;
    @InjectView(R.id.iv_remove_location)
    ImageView ivRemoveLocation;
    @InjectView(R.id.llyt_location)
    RelativeLayout llytLocation;
    @InjectView(R.id.llyt_add_tags_and_location)
    LinearLayout llytAddTagsAndLocation;

    private PresenterEditLive presenter;
    NodeTimeline.ItemsBean.NodeBean timelineDraft;
    /**
     * 有内容的颜色
     */
    int hasTextColor;
    /**
     * 无内容的颜色
     */
    int noTextColor;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_edit_live;
    }

    @Override
    protected void initViewsAndEvents() {
        hasTextColor = getResources().getColor(R.color.txt_title_dark_87);
        noTextColor = getResources().getColor(R.color.txt_content);
        presenter = new PresenterEditLive(this);
        presenter.setEditTimeLine(timelineDraft);
        presenter.initData();
        tvCancel.setOnClickListener(this);
        llytTags.setOnClickListener(this);
        ivRemoveTags.setOnClickListener(this);
        tvSend.setOnClickListener(this);
        llytLocation.setOnClickListener(this);
        ivRemoveLocation.setOnClickListener(this);
        updateIsCanSend();
    }


    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Override
    protected void onEvent(EventCenter eventCenter) {
        super.onEvent(eventCenter);
        switch (eventCenter.getEventCode()) {
            case Content.RELEASE_SELECT_TAGS:
                presenter.setTags((List<BaseTag>) eventCenter.getData());
                break;
        }
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        String gson = extras.getString(Content.TIMELINE);
        LogUtil.d("直播重编辑:" + gson);
        timelineDraft = new Gson().fromJson(gson, SendingTimeline.class);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                presenter.goBack();
                break;
            case R.id.llyt_tags:
                startActivity(new Intent(NewEditLiveActivity.this, ReleaseTagActivity.class));
                break;
            case R.id.iv_remove_tags:
                presenter.setTags(null);
                break;
            case R.id.tv_send:
                presenter.sendLive();
                break;
            case R.id.llyt_location:
                presenter.clickLocation();
                break;
            case R.id.iv_remove_location:
                presenter.setLocation(null);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        presenter.destory();
        super.onDestroy();
        System.gc();
    }

    public void lookBigPic(int position) {
        presenter.lookBigPic(position);
    }

    /**
     * 添加图片弹窗
     */
    public void showGetImageDialog() {
        presenter.showAddImageDialog();
    }

    public void updateIsCanSend() {
        presenter.updateIsCanSend();
    }

    public void updatePics() {
        presenter.updatePics();
    }

    /**
     * 物理键回退
     */
    @Override
    public void onBackPressed() {
        presenter.goBack();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        presenter.onStartDrag(viewHolder);
    }

    /**
     * 直播-添加地点 响应
     *
     * @param info
     */
    public void onEvent(LocationInfo info) {
        presenter.respLocationInfo(info);
    }
}
