package net.doyouhike.app.bbs.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.yolanda.nohttp.RequestMethod;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.service.BaseApiReq;
import net.doyouhike.app.bbs.biz.openapi.presenter.NodesHelper;
import net.doyouhike.app.bbs.biz.openapi.presenter.UsersHelper;
import net.doyouhike.app.bbs.biz.openapi.request.nodes.NodesFavoritesPost;
import net.doyouhike.app.bbs.biz.openapi.response.LoginUser;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.NodeShareUrlResp;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.NodeTimeline;
import net.doyouhike.app.bbs.ui.activity.login.LoginActivity;
import net.doyouhike.app.bbs.ui.adapter.NodeTimelineAdapter;
import net.doyouhike.app.bbs.ui.home.dialog.ForwardingDialog;
import net.doyouhike.app.bbs.ui.util.TipUtil;
import net.doyouhike.app.bbs.ui.widget.BottomDialogWindow;
import net.doyouhike.app.bbs.util.ActivityRouter;
import net.doyouhike.app.bbs.util.CommonUtil;
import net.doyouhike.app.bbs.util.ShareUtil;
import net.doyouhike.app.bbs.util.StatisticsEventUtil;
import net.doyouhike.app.bbs.util.StrUtils;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.UIUtils;
import net.doyouhike.app.bbs.util.UserInfoUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Filework:
 * Author: luochangdong
 * Date:16-1-11
 */
public class LiveMoreDialog extends AlertDialog {

    private boolean isMyself = false; // 是否点击自己的直播
    private boolean isLogin = false;

    private String nodeId;

    private NodeTimeline.ItemsBean timeline;
    private Context context;
    private TextView tvReport;
    private TextView tvFavorite;
    private TextView tvForwarding;
    private ImageView ivProgress;
    private String targetUrl;
    private int toShare = -1;
    //    SimpleProgressDialog progressDialog;
    private boolean isGetTargetUrlFail = false;
    /**
     * 是否正在获取分享链接
     */
    boolean isGettingShareUrl = false;


    /**
     * 主页直播更多按钮弹出的dialog
     *
     * @param context
     * @param timeline 直播是否已经收藏
     * @param isMyself 直播是否是自己的
     */
    public LiveMoreDialog(Context context, NodeTimeline.ItemsBean timeline, boolean isMyself) {
        this(context, timeline);
        this.isMyself = isMyself;
        this.isLogin = true;

    }


    /**
     * 主页直播更多按钮弹出的dialog
     *
     * @param context
     * @param info
     */
    public LiveMoreDialog(Context context, NodeTimeline.ItemsBean info) {
        super(context, R.style.dialog_full_screen_buttom_up);
        this.context = context;
        this.timeline = info;
        this.nodeId = timeline.getNode().getNode_id();
        if (timeline.getNode().getForward() != null) {
            this.nodeId = timeline.getNode().getForward().getNode().getNode_id();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_action_share);
        isGettingShareUrl = false;
        ShareUtil.init(context);
        findView();
        initDialog(); // 初始化dialog
        initView(); // 初始化组件

        //获取分享地址
        requestDate();
    }

    /**
     * 获取分享地址
     */
    private void requestDate() {
        if (timeline != null) {

            if (isGettingShareUrl) {
                return;
            }

            isGettingShareUrl = true;
            showProgress(isGettingShareUrl);
            NodesHelper.getInstance().nodeShareUrl(context, nodeId, shareUrlListener);
        }
    }

    /**
     * 获取分享地址响应
     */
    IOnResponseListener<Response<NodeShareUrlResp>> shareUrlListener = new IOnResponseListener<Response<NodeShareUrlResp>>() {
        @Override
        public void onSuccess(Response<NodeShareUrlResp> response) {
            isGettingShareUrl = false;
            showProgress(isGettingShareUrl);
            targetUrl = response.getData().getUrl();
            if (toShare != -1) {
                toShareActivity(toShare);
            }

        }

        @Override
        public void onError(Response response) {
            showProgress(false);
            isGetTargetUrlFail = true;
            if (toShare != -1) {
                TipUtil.alert(getContext(), BaseApiReq.ERR_MSG_COMMON);
            }
        }
    };

    private void findView() {
        tvForwarding = (TextView) this.findViewById(R.id.tv_forwarding_item);
        tvReport = (TextView) this.findViewById(R.id.tv_repost);
        tvFavorite = (TextView) this.findViewById(R.id.tv_favorite);
        ivProgress = (ImageView) this.findViewById(R.id.iv_dialog_share_progress);
    }

    /**
     * 初始化组件
     */
    private void initView() {
        initShareBtn(); // 初始化分享
        initFavoriteBtn(); // 初始化收藏按钮
        initReportBtn(); // 初始化举报或删除按钮
        initCancelBtn(); // 初始化取消按钮

        //转发
        clickForwarding();
    }

    /**
     * 转发
     */
    private void clickForwarding() {
        if (isMyself && timeline.getNode().getMinilog_type().equals(NodeTimelineAdapter.NODE_FORWARD)) {
            tvForwarding.setText("取消转发");
            tvForwarding.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if (isLogin) {
                        deleteLive(timeline.getNode().getNode_id());
                    } else {
                        ActivityRouter.openLoginActivity(context);
                    }
                }
            });
        } else {
            tvForwarding.setText("转发");
            tvForwarding.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if (isLogin) {
                        new ForwardingDialog(context, nodeId)
                                .show();

                    } else {
                        ActivityRouter.openLoginActivity(context);
                    }


                }
            });
        }
    }


    private void initShareBtn() {
        LinearLayout llytWeiXin = (LinearLayout) this
                .findViewById(R.id.ll_share_weixin);
        LinearLayout llytWeiBo = (LinearLayout) this
                .findViewById(R.id.ll_share_weibo);
        LinearLayout llytPyq = (LinearLayout) this
                .findViewById(R.id.ll_share_pyq);
        LinearLayout llytCopy = (LinearLayout) this
                .findViewById(R.id.ll_copy_link);
        LinearLayout llytQq = (LinearLayout) this
                .findViewById(R.id.ll_share_qq);
        LinearLayout llytQzone = (LinearLayout) this
                .findViewById(R.id.ll_share_qzone);


        clickShareBtn(llytWeiXin, ShareUtil.SHARE_TO_WEIXIN);
        clickShareBtn(llytWeiBo, ShareUtil.SHARE_TO_WEIBO);
        clickShareBtn(llytPyq, ShareUtil.SHARE_TO_PYQ);
        clickShareBtn(llytCopy, ShareUtil.COPY_LINK);
        clickShareBtn(llytQq, ShareUtil.SHARE_TO_QQ);
        clickShareBtn(llytQzone, ShareUtil.SHARE_TO_QZONE);
    }

    private void clickShareBtn(LinearLayout llytWeiXin, final int shareTo) {
        llytWeiXin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (targetUrl != null) {
                    toShareActivity(shareTo);
                } else if (isGetTargetUrlFail) {
//                    Toast.makeText(context, "分享失败,请重试", Toast.LENGTH_LONG)
//                            .show(); // TODO
                    toShare = shareTo;
                    requestDate();
                } else {
                    toShare = shareTo;
                }

            }

        });
    }

    private void toShareActivity(int toShare) {
        switch (toShare) {
            case ShareUtil.SHARE_TO_WEIXIN:
                ShareUtil.shareToWeixin(context, R.drawable.ic_share_img,
                        getShareContent(toShare), ShareUtil.getShareTitle(toShare, timeline), targetUrl);
                break;

            case ShareUtil.SHARE_TO_WEIBO:
                ShareUtil.shareToWeibo(context, R.drawable.ic_share_img,
                        getShareContent(toShare), ShareUtil.getShareTitle(toShare, timeline), targetUrl);
                break;

            case ShareUtil.SHARE_TO_PYQ:
                ShareUtil.shareToPyq(context, R.drawable.ic_share_img,
                        getShareContent(toShare), ShareUtil.getShareTitle(toShare, timeline), targetUrl);
                break;

            case ShareUtil.COPY_LINK:
                ShareUtil.copyLink(context, targetUrl);
                break;

            case ShareUtil.SHARE_TO_QQ:
                ShareUtil.shareQQ(context, R.drawable.ic_share_img,
                        getShareContent(toShare), ShareUtil.getShareTitle(toShare, timeline), targetUrl);
                break;

            case ShareUtil.SHARE_TO_QZONE:
                ShareUtil.shareQZone(context, R.drawable.ic_share_img,
                        getShareContent(toShare), ShareUtil.getShareTitle(toShare, timeline), targetUrl);
                break;

        }
        LiveMoreDialog.this.dismiss();
    }


    private String getShareContent(int toShare) {

        if (timeline != null) {

            return ShareUtil.getShareContent(toShare, timeline);

        } else {
            return "";
        }
    }

    private void initCancelBtn() {
        TextView tvCancel = (TextView) this.findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiveMoreDialog.this.dismiss();
            }
        });
    }

    private void initReportBtn() {
        String liveType = timeline.getNode().getMinilog_type();
        if ((liveType.equals(NodeTimelineAdapter.NODE_EVENT) || liveType.equals(NodeTimelineAdapter.NODE_DISCUSSION))
                && isMyself) {
            tvReport.setVisibility(View.GONE);
        } else {
            String text = "";
            if (timeline.getNode().getForward() != null) {
                NodeTimeline.ItemsBean.NodeBean.ForwardBean forwardBean = timeline.getNode().getForward();
                String type = timeline.getNode().getForward().getNode().getMinilog_type();
                if ((liveType.equals(NodeTimelineAdapter.NODE_EVENT) || liveType.equals(NodeTimelineAdapter.NODE_DISCUSSION))
                        && forwardBean.getNode().getUser().getUser_id().equals(
                        UserInfoUtil.getInstance().getUserId())) {
                    tvReport.setVisibility(View.GONE);
                    return;
                }
                if (forwardBean.getNode().getUser().getUser_id().equals(
                        UserInfoUtil.getInstance().getUserId())) {
                    text = context.getResources().getString(R.string.share_dialog_delete);
                    tvReport.setTextColor(context.getResources().getColor(R.color.orange_word));
                } else {
                    text = context.getResources().getString(R.string.repost);
                    tvReport.setTextColor(context.getResources().getColor(R.color.txt_title_dark_87));
                }
            } else {

                if (isMyself) {
                    text = context.getResources().getString(R.string.share_dialog_delete);
                    tvReport.setTextColor(context.getResources().getColor(R.color.orange_word));
                } else {
                    text = context.getResources().getString(R.string.repost);
                    tvReport.setTextColor(context.getResources().getColor(R.color.txt_title_dark_87));
                }
            }
            tvReport.setText(text);
            clickReportBtn();
        }
    }

    private void clickReportBtn() {
        tvReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timeline != null) {
                    dismiss();
                    if (timeline.getNode().getForward() != null) {
                        if (tvReport.getText().toString().equals(context.getResources().getString(R.string.share_dialog_delete))) {
                            if (isLogin) {
                                final ActionSheetDialog twoDeleteDialog = new ActionSheetDialog(context, new String[]{"删除"}, null);
                                twoDeleteDialog.title("是否删除?");
                                twoDeleteDialog.setOnOperItemClickL(new OnOperItemClickL() {
                                    @Override
                                    public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        deleteLive(timeline.getNode().getNode_id());
                                        deleteLive(timeline.getNode().getForward().getNode().getNode_id());
                                        twoDeleteDialog.dismiss();
                                    }
                                });
                                twoDeleteDialog.show();

                            } else {
                                ActivityRouter.openLoginActivity(context);
                            }
                        } else {
                            toReportActivity();
                        }
                    } else {
                        if (tvReport.getText().toString().equals(context.getResources().getString(R.string.share_dialog_delete))) {
                            showSureBottom();
                        } else {
                            toReportActivity();
                        }
                    }

                } else {
                    CommonUtil.errorLog(this.getClass().getName()
                            + "::点击举报或删除时info == null"); // TODO
                    StringUtil.showSnack(
                            context,
                            StrUtils.getResourcesStr(context,
                                    R.string.app_error));
                }
            }

        });
    }

    private synchronized void deleteLive(String node_id) {
        NodesHelper.getInstance().deleteNode(context, node_id, deleteNodeListener);
    }

    protected void showSureBottom() {
        // 提示：询问是否删除直播
        List<String> itemStrList = new ArrayList<String>();
        itemStrList.add(context.getResources().getString(R.string.share_dialog_delete));
        List<Integer> itecolorList = new ArrayList<Integer>();
        itecolorList.add(R.color.orange_word);
        final BottomDialogWindow mBottomPopupWindow = new BottomDialogWindow(
                context, itemStrList, itecolorList);

        mBottomPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // 点击“删除”后正常发送请求
                mBottomPopupWindow.dismiss();
                deleteLive(timeline.getNode().getNode_id());
            }
        });

        mBottomPopupWindow.show();
    }


    private void toReportActivity() {
        NodeTimeline.ItemsBean.NodeBean.ForwardBean forwardBean = timeline.getNode().getForward();
        if (forwardBean != null) {
            ActivityRouter.openReportActivity(context, nodeId,
                    forwardBean.getNode().getMinilog_type().equals(NodeTimelineAdapter.NODE_EVENT));
        } else {
            ActivityRouter.openReportActivity(context, nodeId,
                    timeline.getNode().getMinilog_type().equals(NodeTimelineAdapter.NODE_EVENT));
        }

        dismiss();
    }

    private void initFavoriteBtn() {
        if (isLogin) {
            String text = "";
            if (timeline.getNode_counter().isFavorited()) {
                text = context.getResources().getString(
                        R.string.cancel_favorite);
            } else {
                text = context.getResources().getString(R.string.favorite);
            }
            tvFavorite.setText(text);
            clickFavoriteBtn(tvFavorite);
        } else {
            tvFavorite.setVisibility(View.GONE);
        }
    }

    private void clickFavoriteBtn(final TextView tvFavorite) {
        tvFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser user = UserInfoUtil.getInstance().getCurrentUser();
                if (user == null) {
                    context.startActivity(new Intent(context,
                            LoginActivity.class));

                } else {

                    if (timeline.getNode_counter().isFavorited()) {
                        tvFavorite.setText(StrUtils.getResourcesStr(context,
                                R.string.canceling_favorite));
                        UsersHelper.getSingleTon().deleteFavorite(context, nodeId, favoriteListener);
                    } else {

                        tvFavorite.setText(StrUtils.getResourcesStr(context,
                                R.string.favouriting));
                        UsersHelper.getSingleTon().addFavorite(context, nodeId, favoriteListener);
                    }
                    tvFavorite.setTextColor(0xFFBFBFBF);
                    tvFavorite.setClickable(true);
                }
            }
        });
    }

    /**
     * 收藏响应
     */
    IOnResponseListener favoriteListener = new IOnResponseListener() {
        @Override
        public void onSuccess(Response response) {
            dismiss();
            NodesFavoritesPost post = (NodesFavoritesPost) response.getExtraTag();
            if (post.getRequestMethod() == RequestMethod.POST) {
                StringUtil.showSnack(context, "收藏成功");
                StatisticsEventUtil.collect(context);
            }
            if (timeline.getNode_counter().isFavorited()) {
                timeline.getNode_counter().setFavorited(false);
            } else {
                timeline.getNode_counter().setFavorited(true);
            }
        }

        @Override
        public void onError(Response response) {
            dismiss();
            StringUtil.showSnack(context, response.getMsg());
            if (timeline.getNode_counter().isFavorited()) {
                tvFavorite.setText(StrUtils.getResourcesStr(context,
                        R.string.cancel_favorite));
            } else {
                tvFavorite.setText(StrUtils.getResourcesStr(context,
                        R.string.favorite));
            }
            tvFavorite.setTextColor(0xFF000000);
            tvFavorite.setClickable(true);
        }
    };

    private void initDialog() {
        this.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        this.getWindow().setGravity(Gravity.BOTTOM);
    }


    IOnResponseListener deleteNodeListener = new IOnResponseListener() {
        @Override
        public void onSuccess(Response response) {
            dismiss();
            StringUtil.showSnack(context, R.string.delete_success);
        }

        @Override
        public void onError(Response response) {
            StringUtil.showSnack(context, response.getMsg());
        }
    };


    private void showProgress(boolean show) {
        UIUtils.showView(ivProgress, show);
        if (show) {
            Animation operatingAnim = AnimationUtils.loadAnimation(getContext(), R.anim.share_loading);
            LinearInterpolator lin = new LinearInterpolator();
            operatingAnim.setInterpolator(lin);
            ivProgress.clearAnimation();
            ivProgress.setAnimation(operatingAnim);
        } else {
            ivProgress.clearAnimation();
        }


    }

}
