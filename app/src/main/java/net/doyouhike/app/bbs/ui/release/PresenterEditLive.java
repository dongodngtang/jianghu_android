package net.doyouhike.app.bbs.ui.release;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.google.gson.Gson;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.entity.LocationInfo;
import net.doyouhike.app.bbs.biz.event.PostLiveEvent;
import net.doyouhike.app.bbs.biz.event.SendLive;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.BaseTag;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.SendingTimeline;
import net.doyouhike.app.bbs.biz.nohttp.CallServer;
import net.doyouhike.app.bbs.biz.openapi.response.LoginUser;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventDetailResp;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.NodeTimeline;
import net.doyouhike.app.bbs.ui.activity.live.LookPicsForChooseActivity;
import net.doyouhike.app.bbs.ui.activity.login.LoginActivity;
import net.doyouhike.app.bbs.ui.activity.me.ChooseImageActivity;
import net.doyouhike.app.bbs.ui.adapter.NodeTimelineAdapter;
import net.doyouhike.app.bbs.ui.release.yueban.destination.SelectDestActivity;
import net.doyouhike.app.bbs.util.BitmapUtil;
import net.doyouhike.app.bbs.util.LogUtil;
import net.doyouhike.app.bbs.util.SaveFileUtil;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;
import net.doyouhike.app.bbs.util.StrUtils;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.UIUtils;
import net.doyouhike.app.bbs.util.UserInfoUtil;
import net.doyouhike.app.bbs.util.draghelper.GridWrapLayoutManager;
import net.doyouhike.app.bbs.util.draghelper.SimpleItemTouchHelperCallback;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 作者:luochangdong on 16/7/5 14:41
 * 描述:
 */
public class PresenterEditLive {

    public static final String TAG = "PresenterEditLive";

    private static final int REQUEST_CODE_TO_ADD_TAGS = 10001;

    private static final int REQUEST_CODE_ADD_PICS = 1550;

    private static final int REQUEST_CODE_TO_ADD_LOCATION = 1834;

    public static final int RESULT_CODE_RELEASE_LIVE = 2046;

    public static final String INTENT_EXTRA_LIVE_INFO = "liveInfo";
    public static final String ADD_IMG = "R.drawable.post_addingapicture_notclick";
    String savePicUri;
    public final static int TAKE_PHOTO_WITH_DATA = 21;
    /**
     * 拖拽辅助工具
     */
    private ItemTouchHelper mItemTouchHelper;

    /**
     * 发送失败的编辑数据
     */
    NodeTimeline.ItemsBean.NodeBean mEidtTimeline;

    NewEditLiveActivity baseActivity;

    /**
     * 编辑框是否有文字
     */
    private boolean hasContent = false;


    /**
     * 退出发布底部弹窗
     */
    ActionSheetDialog backSheetDialog;
    /**
     * 添加图片底部弹窗
     */
    ActionSheetDialog imageSheetDialog;
    /**
     * 定位信息
     */
    private NodeTimeline.ItemsBean.NodeBean.LocationBean location;
    /**
     * 标签
     */
    public static List<BaseTag> tags = new ArrayList<>();

    /**
     * 内容
     */
    public static NodeTimeline.ItemsBean.NodeBean.ContentBean contentBean = new NodeTimeline.ItemsBean.NodeBean.ContentBean();


    /**
     * 三个标签
     */
    private TextView[] tvTags = new TextView[3];
    /**
     * 九宫格图片适配器
     */
    private DragImgAdapter adapter;
    /**
     * 敏感字检测
     */
    private PresenterBlocklist checkBlock;


    public PresenterEditLive(NewEditLiveActivity activity) {
        baseActivity = activity;
    }


    public void setTags(List<BaseTag> tagsStr) {
        UIUtils.showView(baseActivity.ivRemoveTags, tagsStr != null && tagsStr.size() > 0);
        if (null == tagsStr) {
            setTagTextViewNomal();
            return;
        }
        tags.clear();
        setTagTextViewNomal();
        for (int i = 0; i < tagsStr.size(); i++) {
            tvTags[i].setText(tagsStr.get(i).getTag_name());
            tvTags[i].setTextColor(baseActivity.hasTextColor);
        }
        tags.addAll(tagsStr);
    }

    private void setTagTextViewNomal() {
        tags.clear();
        for (int j = 0; j < 3; j++) {
            tvTags[j].setText("");
            tvTags[j].setTextColor(baseActivity.noTextColor);
        }
        tvTags[0].setText("添加标签");
    }

    public void setEditTimeLine(NodeTimeline.ItemsBean.NodeBean timeline) {
        mEidtTimeline = timeline;
    }

    public void initData() {
        checkBlock = new PresenterBlocklist(blockCallback);
        initView();
        sheetDialog();
        readDate(); // 读取之前保存的数据
        //显示线路 地点提示
        UIUtils.showView(baseActivity.tvActEditLiveDestTip, Content.EDIT_LIVE_TIP);
    }

    private ArrayList<String> getPhotosStr(List<EventDetailResp.ContentBean> contents) {
        ArrayList<String> list = new ArrayList<>();
        for (EventDetailResp.ContentBean item : contents) {
            list.add(item.getWhole_photo_path());
        }
        return list;
    }


    private void readDate() {
        NodeTimeline.ItemsBean.NodeBean info;
        if (mEidtTimeline == null) {
            info = SharedPreferencesManager
                    .getToReleaseLiveInfo(baseActivity);
        } else {
            info = mEidtTimeline;
        }


        if (info != null && baseActivity.etInput != null
                && info.getContent() != null
                && info.getContent().getText() != null) {
            baseActivity.etInput.setText(info.getContent().getText()); // 设置输入框内容
            baseActivity.etInput.setSelection(info.getContent().getText().length()); // 设置光标位置

            // 设置选择的图片
            getImages().clear();
            if (info.getEvent() != null && info.getEvent().getEvent_contents() != null) {
                List<EventDetailResp.ContentBean> contentBeanList = info.getEvent().getEvent_contents();
                getImages().addAll(contentBeanList);
            }

            updatePics();
            // 设置标签内容
            List<BaseTag> tagsArr = info.getTags();
            if (tagsArr != null) {
                setTags(tagsArr);
            }

            setLocation(info.getLocation()); // 设置定位信息

        }
        updateIsCanSend();

    }

    private List<EventDetailResp.ContentBean> getImages(){
        return adapter.getmDatas();
    }

    private EventDetailResp.ContentBean getBean(String imagePath) {
        return new EventDetailResp.ContentBean(imagePath);
    }

    public void updatePics() {
        if (!getImages().contains(getBean(ADD_IMG)) && getImages().size() < 9) {
            getImages().add(getBean(ADD_IMG));
        }
        adapter.notifyDataSetChanged();
    }

    private void initView() {

        initEditView(); // 初始化编辑框
        baseActivity.ivRemoveTags.setVisibility(View.GONE);
        baseActivity.ivRemoveLocation.setVisibility(View.GONE);

        tvTags[0] = baseActivity.tvTag1;
        tvTags[1] = baseActivity.tvTag2;
        tvTags[2] = baseActivity.tvTag3;
        initPicsBtn(); // 初始化添加图片相关的组件
    }

    private void initPicsBtn() {
        List<EventDetailResp.ContentBean> datas = new ArrayList<>();
        datas.add(getBean(ADD_IMG));
        adapter = new DragImgAdapter(baseActivity, datas);
        baseActivity.mDragGridView.setHasFixedSize(true);

        //布局
        GridLayoutManager layoutManager = new GridWrapLayoutManager(baseActivity, 3);
        baseActivity.mDragGridView.setLayoutManager(layoutManager);
        //分割线
//        GridDividerDecoration divider = new GridDividerDecoration(this);
//        mDragGridView.addItemDecoration(divider);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(baseActivity.mDragGridView);

        baseActivity.mDragGridView.setAdapter(adapter);
    }


    private void initEditView() {
        baseActivity.etInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && baseActivity.tvTextCount != null) {
                    int len = s.toString().trim().length();
                    if (len <= 0) {
                        hasContent = false;
                        baseActivity.tvTextCount.setVisibility(View.GONE);
                    } else if (len > 0 && len < 490) {
                        hasContent = true;
                        baseActivity.tvTextCount.setVisibility(View.GONE);
                    } else if (len >= 490 && len <= 500) {
                        hasContent = true;
                        baseActivity.tvTextCount.setVisibility(View.VISIBLE);
                        baseActivity.tvTextCount.setEnabled(true);
                        baseActivity.tvTextCount.setText((500 - len) + "");
                    } else {
                        hasContent = false;
                        baseActivity.tvTextCount.setVisibility(View.VISIBLE);
                        baseActivity.tvTextCount.setEnabled(false);
                        baseActivity.tvTextCount.setText((len - 500) + "");
                    }
                }
                updateIsCanSend();
            }
        });
    }

    public boolean updateIsCanSend() {
        if (baseActivity.tvSend != null) {
            if (hasContent
                    || (getImages() != null && getImages().size() > 1)) {
                baseActivity.tvSend.setAlpha(1);
                baseActivity.tvSend.setClickable(true);
                return true;
            } else {
                baseActivity.tvSend.setAlpha(0.5f);
                baseActivity.tvSend.setClickable(false);
                return false;
            }
        }
        return false;
    }

    /**
     * 退出发布
     */
    public void goBack() {
        boolean isCanSend = updateIsCanSend();
        if (isCanSend) {
            bottomDialog();
        } else {
            SharedPreferencesManager.clearToReleaseLiveInfo(baseActivity);
            baseActivity.finish();
        }
    }

    /**
     * 退出发布底部弹窗
     */
    private void bottomDialog() {
        backSheetDialog.show();
        backSheetDialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:

                        if (mEidtTimeline != null)
                            EventBus.getDefault()
                                    .post(new PostLiveEvent(Content.RELEASE_LIVE_DELETE,
                                            (SendingTimeline) mEidtTimeline));
                        else
                            SharedPreferencesManager.clearToReleaseLiveInfo(baseActivity);
                        baseActivity.finish();
                        break;

                    case 1:
                        String liveJson = getLiveInfoStr();
                        SharedPreferencesManager.setToReleaseLiveInfo(
                                baseActivity, liveJson);
                        baseActivity.finish();
                        break;
                }
                backSheetDialog.superDismiss();
            }
        });
    }


    private List<NodeTimeline.ItemsBean.NodeBean.ContentBean.PhotosBean> getPhotos() {
        if (getImages() == null)
            return null;
        List<NodeTimeline.ItemsBean.NodeBean.ContentBean.PhotosBean> imgs = new ArrayList<>();
        if (getImages().contains(getBean(ADD_IMG))) {
            getImages().remove(getBean(ADD_IMG));
        }
        NodeTimeline.ItemsBean.NodeBean.ContentBean.PhotosBean img;
        for (EventDetailResp.ContentBean path : getImages()) {
            img = new NodeTimeline.ItemsBean.NodeBean.ContentBean.PhotosBean();
            img.setWhole_photo_path(path.getWhole_photo_path());
            imgs.add(img);
        }

        return imgs;
    }

    private NodeTimeline.ItemsBean.NodeBean getLiveInfo() {
        NodeTimeline.ItemsBean.NodeBean mpLInfo = new NodeTimeline.ItemsBean.NodeBean();
        NodeTimeline.ItemsBean.NodeBean.EventBean eventBean = new NodeTimeline.ItemsBean.NodeBean.EventBean();

        contentBean.setText(getText());
        contentBean.setPhotos(getPhotos());

        eventBean.setEvent_contents(getImages());
        mpLInfo.setEvent(eventBean);
        mpLInfo.setContent(contentBean);
        mpLInfo.setLocation(location);
        mpLInfo.setTags(tags);
        mpLInfo.setNode_type(NodeTimelineAdapter.NODE_TEXT_PHOTO);
        LoginUser user = UserInfoUtil.getInstance().getCurrentUser();
        if (null != user) {
            NodeTimeline.ItemsBean.NodeBean.UserBean userInfo = new NodeTimeline.ItemsBean.NodeBean.UserBean();
            userInfo.setAvatar(user.getUser().getAvatar());
            userInfo.setNick_name(user.getUser().getNick_name());
            userInfo.setUser_id(user.getUser().getUser_id());
            userInfo.setUser_name(user.getUser().getUser_name());

            mpLInfo.setUser(userInfo);
        }
        return mpLInfo;
    }


    /**
     * 初始化底部弹窗
     */
    private void sheetDialog() {
        String[] stringItems = {"退出", "临时保存"};
        backSheetDialog = new ActionSheetDialog(baseActivity, stringItems, null);
        backSheetDialog.title("退出发布");

        String[] imageItems = {"拍照", "从手机相册选择"};
        imageSheetDialog = new ActionSheetDialog(baseActivity, imageItems, null);
        imageSheetDialog.title("添加图片");
    }

    private String getPath(String uriPath) {
        try {
            URI uri = new URI(uriPath);
            return uri.getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getText() {
        return baseActivity.etInput.getText().toString();
    }

    public void sendLive() {
        baseActivity.tvSend.setClickable(false);
        final LoginUser user = UserInfoUtil.getInstance().getCurrentUser();
        if (user != null) {
            goSend();
//            if (getText() == null || getText().isEmpty()) {
//                goSend();
//            } else {
//                baseActivity.tvSend.setText("发送中");
//                checkBlock.checkSensitiveWord(getText());
//            }

        } else {
            baseActivity.startActivity(new Intent(baseActivity,
                    LoginActivity.class));
            baseActivity.tvSend.setClickable(true);
        }
    }

    /**
     * 开始发送
     */
    private void goSend() {
        String liveJson = getLiveInfoStr();
        SharedPreferencesManager.setToReleaseLiveInfo(baseActivity, liveJson);
        EventBus.getDefault().post(new SendLive(liveJson));
        baseActivity.finish();
    }

    private String getLiveInfoStr() {
        NodeTimeline.ItemsBean.NodeBean liveInfo = getLiveInfo();
        try {
            return new Gson().toJson(liveInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    PresenterBlocklist.BlockCallback blockCallback = new PresenterBlocklist.BlockCallback() {
        @Override
        public void haveBlock() {
            StringUtil.showSnack(baseActivity, "发布失败，可能包含违法信息！");
            baseActivity.tvSend.setClickable(true);
            baseActivity.tvSend.setText("发送");
        }

        @Override
        public void notHaveBlock() {
            goSend();
        }

        @Override
        public void netError() {
            StringUtil.showSnack(baseActivity, "发布失败,请检查网络后重试");
            baseActivity.tvSend.setClickable(true);
            baseActivity.tvSend.setText("发送");
        }
    };

    public void clickLocation() {
        Content.EDIT_LIVE_TIP = false;
        //显示线路 地点提示
        UIUtils.showView(baseActivity.tvActEditLiveDestTip, Content.EDIT_LIVE_TIP);
        Intent intent = new Intent(baseActivity, SelectDestActivity.class);
        intent.putExtra(TAG, TAG);
        baseActivity.startActivity(intent);
    }

    /**
     * 填充地点
     *
     * @param location
     */
    public void setLocation(NodeTimeline.ItemsBean.NodeBean.LocationBean location) {

        boolean hasLocation = location != null;

        baseActivity.tvLocation.setTextColor(hasLocation ? baseActivity.hasTextColor
                : baseActivity.noTextColor);
        //显示 删除按钮
        UIUtils.showView(baseActivity.ivRemoveLocation, hasLocation);

        if (hasLocation) {
            baseActivity.tvLocation.setText(location.getLocationName());
            this.location = location;
        } else {
            baseActivity.tvLocation.setText(StrUtils.getResourcesStr(baseActivity,
                    R.string.add_location));
            this.location = null;
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_ADD_PICS
                && resultCode == ChooseImageActivity.RESULT_CODE_SELECT_PICS) {
            if (data != null) {
                List<String> chooses = data
                        .getStringArrayListExtra(ChooseImageActivity.INTENT_EXTRA_SELECTED_PICS_STR_ARRAY);
                getImages().remove(getBean(ADD_IMG));
                for (String path : chooses) {
                    getImages().add(getBean(path));
                }
                updatePics();

            }

        } else if (requestCode == TAKE_PHOTO_WITH_DATA) {

            Bitmap bitmap = BitmapUtil.decodeFile(savePicUri);
            if (bitmap == null) {
                return;
            }
            getImages().remove(getBean(ADD_IMG));
            bitmap.recycle();
            getImages().add(getBean("file://" + savePicUri));
            updatePics();
        } else if (resultCode == LookPicsForChooseActivity.RESULT_CODE_COMPLETE) {
            if (data != null) {
                getImages().clear();

                List<String> selected = data
                        .getStringArrayListExtra(LookPicsForChooseActivity.INTENT_EXTRA_NAME_SELECT_PICS_STR_LIST);
                for (String select : selected) {
                    getImages().add(getBean(select));
                }
                updatePics();
            }
        } else if (resultCode == LookPicsForChooseActivity.RESULT_CODE_NOT_COMPLETE) {
            updatePics();
        }
        updateIsCanSend();
    }

    public void clickAddPicBtn() {
        Intent intent = new Intent(baseActivity, ChooseImageActivity.class);
        intent.putExtra(ChooseImageActivity.CHOOSE_TYPE,
                ChooseImageActivity.CHOOSE_TYPE_POST);
        intent.putExtra(ChooseImageActivity.INTENT_EXTRA_SELECTED_PICS_STR_ARRAY, getImages().size() - 1);
        baseActivity.startActivityForResult(intent, REQUEST_CODE_ADD_PICS);
    }

    public void lookBigPic(int position) {
        Intent intent = new Intent(baseActivity,
                LookPicsForChooseActivity.class);
        if (getImages() == null)
            return;
        getImages().remove(getBean(ADD_IMG));
        intent.putExtra(
                LookPicsForChooseActivity.INTENT_EXTRA_NAME_PICS_STR_ARRAY,
                getPhotosStr(getImages()).toArray(new String[getImages().size()]));
        intent.putExtra(LookPicsForChooseActivity.INTENT_EXTRA_NAME_AIM_INT,
                LookPicsForChooseActivity.OPEND_FOR_LIVE_LOOK);
        intent.putExtra(
                LookPicsForChooseActivity.INTENT_EXTRA_NAME_SHOW_INDEX_INT,
                position);

        intent.putStringArrayListExtra(
                LookPicsForChooseActivity.INTENT_EXTRA_NAME_SELECT_PICS_STR_LIST,
                getPhotosStr(getImages()));
        baseActivity.startActivityForResult(intent, REQUEST_CODE_ADD_PICS);
    }

    /**
     * 添加图片弹窗
     */
    public void showGetImageDialog() {
        imageSheetDialog.show();
        imageSheetDialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        savePicUri = SaveFileUtil.getSaveImagePath(baseActivity, false);

                        File out = new File(savePicUri);
                        Uri uri = Uri.fromFile(out);

                        LogUtil.d("uri.getPath() = " + uri.getPath());

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        baseActivity.startActivityForResult(intent, TAKE_PHOTO_WITH_DATA);
                        break;

                    case 1:

                        clickAddPicBtn();
                        break;
                }
                imageSheetDialog.dismiss();
            }
        });

    }

    public void showAddImageDialog() {
        imageSheetDialog.show();
        imageSheetDialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        savePicUri = SaveFileUtil.getSaveImagePath(baseActivity, false);

                        File out = new File(savePicUri);
                        Uri uri = Uri.fromFile(out);

                        LogUtil.d("uri.getPath() = " + uri.getPath());

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        baseActivity.startActivityForResult(intent, TAKE_PHOTO_WITH_DATA);
                        break;

                    case 1:

                        clickAddPicBtn();
                        break;
                }
                imageSheetDialog.dismiss();
            }
        });
    }

    public void destory() {
        CallServer.getRequestInstance().cancelBySign(Content.REQ_TOPICS_CHECK_SENSITIVE_WORD);
        tags.clear();
    }

    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    public void respLocationInfo(LocationInfo info) {
        NodeTimeline.ItemsBean.NodeBean.LocationBean bean = new NodeTimeline.ItemsBean.NodeBean.LocationBean();
        bean.setAltitude(String.valueOf(info.getAltitude()));
        bean.setCity_id(info.getCity_id());
        bean.setLatitude(String.valueOf(info.getLatitude()));
        bean.setLocationName(info.getLocation());
        bean.setLongitude(String.valueOf(info.getLongitude()));
        bean.setDest_id(info.getDest_id());
        if (bean != null) {
            setLocation(bean);
        }
    }
}
