package net.doyouhike.app.bbs.ui.widget.live.tag;

import android.content.Intent;

import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.BaseTag;
import net.doyouhike.app.bbs.biz.presenter.home.PresenterHomePopup;
import net.doyouhike.app.bbs.ui.home.HomeFragment;
import net.doyouhike.app.bbs.ui.home.popupmenu.fixedtag.TagFactory;
import net.doyouhike.app.bbs.ui.home.tag.HomeTagActivity;
import net.doyouhike.app.bbs.ui.home.topic.TimelineRequestType;
import net.doyouhike.app.bbs.ui.widget.common.dialog.BottomPopupDialog;
import net.doyouhike.app.bbs.util.UIUtils;
import net.doyouhike.app.bbs.util.UserInfoUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述:动态  标签 dailog
 */
public class HomeTagDialog extends BottomPopupDialog<BaseTag> {

    boolean hasGetSubscripTag = false;

    PresenterHomePopup mPresenter;

    private HomeFragment fragment;

    /**
     * 订阅的标签
     */
    private ArrayList<BaseTag> subscriptionTags = new ArrayList<>();
    /**
     * 全部标签
     */
    private List<BaseTag> tagList = new ArrayList<>();


    public HomeTagDialog(HomeFragment fragment) {
        super(fragment.getContext());
        this.fragment = fragment;
        initialize();
    }


    public void onDestroy() {
        mPresenter.onDestroy();
        fragment = null;
    }



    public void showDialog() {
        if (!hasGetSubscripTag) {
            //没获取到数据,请求数据
            mPresenter.initData();
        }
        show();
        setLoginView();
    }


    /**
     * 更新标签数据
     * @param tags 订阅标签
     * @param isOffline 是否离线数据
     */
    public void onGetSubcribeTags(List<BaseTag> tags, boolean isOffline) {

        if (isOffline && hasGetSubscripTag) {
            return;
        }

        subscriptionTags.clear();
        subscriptionTags.addAll(tags);
        setAllItems();

        hasGetSubscripTag = !isOffline;

    }

    public void onGetSubscribeTagErr(Response response) {

    }

    private void setAllItems() {

        tagList.clear();

        if (UserInfoUtil.getInstance().isLogin()) {
            tagList.add(TagFactory.createTag(TimelineRequestType.ATTEND));
        }

        tagList.add(TagFactory.createTag(TimelineRequestType.HOT));
//        fixedTags.add(TagFactory.createTag(TimelineRequestType.NEARBY));

        tagList.addAll(subscriptionTags);

        upDataItem(tagList);
    }

    /**
     * 设置登陆的ui 没登陆不能编辑
     */
    private void setLoginView() {

        boolean isLogin = UserInfoUtil.getInstance().isLogin();
        UIUtils.showView(getTvRight(), isLogin);
    }

    private void initialize() {
        initView();
        initData();

    }

    private void initView() {
        UIUtils.showView(getTvRight(), true);
        UIUtils.showView(getTvLeft(), false);
    }


    private void initData() {

        setAllItems();

        mPresenter = new PresenterHomePopup(this);
        mPresenter.initData();

        notifyDataSetChanged();
    }


    @Override
    public String getRightText() {
        return "编辑订阅";
    }

    @Override
    public void onRightClick() {
        //编辑
        Intent intent = new Intent(fragment.getContext(), HomeTagActivity.class);
        //如果已经获取到了最新的订阅标签,则传过去
        if (hasGetSubscripTag)
            intent.putExtra(HomeTagActivity.I_SUBSCRIB_ITEMS, subscriptionTags);
        (fragment).startActivityForResult(intent, HomeFragment.REQ_CODE_TAG);
    }

}
