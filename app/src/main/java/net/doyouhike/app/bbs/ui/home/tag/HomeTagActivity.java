package net.doyouhike.app.bbs.ui.home.tag;

import android.content.Intent;
import android.view.View;

import com.flyco.dialog.listener.OnBtnClickL;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.activity.BaseActivity;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.BaseTag;
import net.doyouhike.app.bbs.ui.home.tag.activity.IPresenterHomeTag;
import net.doyouhike.app.bbs.ui.home.tag.activity.IViewHomeTag;
import net.doyouhike.app.bbs.ui.home.tag.activity.PresenterHomeTag;
import net.doyouhike.app.bbs.ui.home.tag.activity.SectionAdapter;
import net.doyouhike.app.bbs.ui.home.tag.activity.SectionController;
import net.doyouhike.app.bbs.ui.util.TipUtil;
import net.doyouhike.app.bbs.ui.widget.common.TitleView;
import net.doyouhike.app.bbs.ui.widget.common.dslv.DragSortListView;
import net.doyouhike.app.library.ui.uistate.UiState;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class HomeTagActivity extends BaseActivity implements IViewHomeTag {

    public static final String I_SUBSCRIB_ITEMS = "param1";


    @InjectView(R.id.lv_act_home_tag_list)
    DragSortListView lvActHomeTagList;
    @InjectView(R.id.activity_home_tag_title_bar)
    TitleView activityHomeTagTitleBar;

    private ArrayList<BaseTag> mSubcribeItems;
    private List<BaseTag> mTempSubcribeItems;
    private List<BaseTag> mTagItems;
    boolean isGetSubscribItems = false;
    boolean isGetTagItems = false;

    SectionAdapter<BaseTag> mAdapter;
    IPresenterHomeTag mPresenter;




    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_home_tag;
    }

    @Override
    protected View getLoadingTargetView() {
        return lvActHomeTagList;
    }

    @Override
    protected void initViewsAndEvents() {


        mTempSubcribeItems = new ArrayList<>();
        mSubcribeItems = new ArrayList<>();
        ArrayList<BaseTag> tempTag = (ArrayList<BaseTag>) getIntent().getSerializableExtra(I_SUBSCRIB_ITEMS);
        if (null != tempTag) {
            mTempSubcribeItems.addAll(tempTag);
            mSubcribeItems.addAll(tempTag);
            isGetSubscribItems = true;
        }

        mTagItems = new ArrayList<>();
        mAdapter = new SectionAdapter<>(this, mSubcribeItems, mTagItems);
        lvActHomeTagList.setDropListener(mAdapter);

        // make and set controller on dslv
        SectionController c = new SectionController(lvActHomeTagList, mAdapter);
        lvActHomeTagList.setFloatViewManager(c);
        lvActHomeTagList.setOnTouchListener(c);

        // pass it to the ListActivity
        lvActHomeTagList.setAdapter(mAdapter);


        mPresenter = new PresenterHomeTag(this,mContext);

        updateView(UiState.LOADING);
        mPresenter.getTagData();

        if (!isGetSubscribItems) {
            mPresenter.getSubscribTag();
        }

        activityHomeTagTitleBar.setListener(new TitleView.ClickListener() {
            @Override
            public void clickLeft() {
                if (notEdit()) {
                    finishActivity(isGetSubscribItems);
                } else {

                    showExitDialog();


                }
            }

            @Override
            public void clickRight() {
                if (notEdit()) {
                    finishActivity(isGetSubscribItems);
                    return;
                }
                activityHomeTagTitleBar.getRight_text().setEnabled(false);
                updateView(UiState.SHOW_DIALOG);
                saveTags();
            }
        });

    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestory();
        super.onDestroy();
    }


    @Override
    public void onGetTags(List<BaseTag> tags) {
        mTagItems.clear();
        mTagItems.addAll(tags);
        mAdapter.notifyDataSetChanged();
        isGetTagItems = true;
        resetTags();
        mAdapter.notifyDataSetChanged();
        setNormalView();
    }

    @Override
    public void onGetTagErr(Response response) {
        if (!isGetTagItems)
            setErrView(response);
    }

    @Override
    public void onGetSubcribeTags(List<BaseTag> tags) {

        if (!isGetSubscribItems) {
            mTempSubcribeItems.clear();
            mTempSubcribeItems.addAll(tags);
        }

        mSubcribeItems.clear();
        mSubcribeItems.addAll(tags);
        isGetSubscribItems = true;
        resetTags();
        mAdapter.notifyDataSetChanged();
        setNormalView();
    }

    @Override
    public void onGetSubcribeTagsErr(Response response) {
        if (!isGetSubscribItems)
            setErrView(response);
    }

    @Override
    public void onSaveTagErr(Response response) {
        activityHomeTagTitleBar.getRight_text().setEnabled(true);
        updateView(UiState.NORMAL);
        showToast(response.getMsg());
    }

    @Override
    public void onSaveTagSuc() {
        updateView(UiState.NORMAL);
        finishActivity(true);
    }



    private void showExitDialog() {
        TipUtil.alert(HomeTagActivity.this
                ,null
                ,"放弃保存？"
                ,new String[]{"继续", "放弃"}
                ,new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                    }
                }
                ,
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        finish();
                    }
                }
        );
    }

    private boolean notEdit() {

        if (mTempSubcribeItems.size() == mSubcribeItems.size()) {
            for (int i = 0; i < mTempSubcribeItems.size(); i++) {
                if (!mTempSubcribeItems.get(i).equals(mSubcribeItems.get(i))) {
                    return false;
                }
            }
        }


        return mTempSubcribeItems.size() == mSubcribeItems.size()
                && mSubcribeItems.containsAll(mTempSubcribeItems);

    }

    private void saveTags() {
        mPresenter.onSubscribeTag(mSubcribeItems);
    }

    private void finishActivity(boolean haveExtra) {

        if (haveExtra) {
            Intent intent = new Intent();
            intent.putExtra(I_SUBSCRIB_ITEMS, mSubcribeItems);
            setResult(RESULT_OK, intent);
        }
        this.finish();
    }

    private void resetTags() {

        for (BaseTag tag : mSubcribeItems) {
            removedTag(tag);
        }
    }

    private void removedTag(BaseTag tag) {
        synchronized (mTagItems) {
            if (mTagItems.contains(tag)) {
                mTagItems.remove(tag);
            }
        }
    }

    private void setNormalView() {
        if (isGetSubscribItems && isGetTagItems) {
            updateView(UiState.NORMAL);
            activityHomeTagTitleBar.getRight_text().setEnabled(true);
        }
    }

    private void setErrView(Response response) {

        updateView(UiState.ERROR.setMsg(getString(R.string.common_error_msg), response.getMsg()), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isGetTagItems) {
                    updateView(UiState.LOADING);
                    mPresenter.getTagData();
                }

                if (!isGetSubscribItems) {
                    updateView(UiState.LOADING);
                    mPresenter.getSubscribTag();
                }
            }
        });
    }

}
