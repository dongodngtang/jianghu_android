package net.doyouhike.app.bbs.biz.presenter.action;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yolanda.nohttp.RequestMethod;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.db.green.entities.Follow;
import net.doyouhike.app.bbs.biz.event.open.AccountUserFollowEvent;
import net.doyouhike.app.bbs.biz.event.open.NodesLikeEvent;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.BaseTag;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.GetDestByKeywordResp;
import net.doyouhike.app.bbs.biz.openapi.presenter.AttendState;
import net.doyouhike.app.bbs.biz.openapi.presenter.EventHelper;
import net.doyouhike.app.bbs.biz.openapi.presenter.EventRole;
import net.doyouhike.app.bbs.biz.openapi.presenter.NodesHelper;
import net.doyouhike.app.bbs.biz.openapi.presenter.UsersHelper;
import net.doyouhike.app.bbs.biz.openapi.request.nodes.NodesFavoritesPost;
import net.doyouhike.app.bbs.biz.openapi.request.nodes.NodesLikePost;
import net.doyouhike.app.bbs.biz.openapi.response.LoginUser;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventCommentListResp;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventDetailResp;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventMembersResp;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.NodeShareUrlResp;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.NodeTimeline;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.NodesLikesCountResp;
import net.doyouhike.app.bbs.biz.openapi.response.users.UserNodeStateResp;
import net.doyouhike.app.bbs.chat.helper.Constant;
import net.doyouhike.app.bbs.ui.activity.action.ActionAttendActivity;
import net.doyouhike.app.bbs.ui.activity.action.ActionDetailActivity;
import net.doyouhike.app.bbs.ui.activity.action.IActionDetailView;
import net.doyouhike.app.bbs.ui.activity.login.LoginActivity;
import net.doyouhike.app.bbs.ui.home.dialog.ForwardingDialog;
import net.doyouhike.app.bbs.ui.release.yueban.EditEventActivity;
import net.doyouhike.app.bbs.ui.util.FollowButtonHelper;
import net.doyouhike.app.bbs.ui.util.UserHeadNickClickHelper;
import net.doyouhike.app.bbs.ui.widget.BottomDialogWindow;
import net.doyouhike.app.bbs.ui.widget.action.AutoRelativeLayout;
import net.doyouhike.app.bbs.ui.widget.action.RichTextView;
import net.doyouhike.app.bbs.util.ActivityRouter;
import net.doyouhike.app.bbs.util.CheckAppExistUtil;
import net.doyouhike.app.bbs.util.DensityUtil;
import net.doyouhike.app.bbs.util.ShareUtil;
import net.doyouhike.app.bbs.util.StatisticsEventUtil;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.UIUtils;
import net.doyouhike.app.bbs.util.UserInfoUtil;
import net.doyouhike.app.bbs.util.glide.GlideHelper;
import net.doyouhike.app.library.ui.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 活动详情
 * Created by terry on 4/6/16.
 */
public class ActionDetailPresenter {

    private final String TAG = ActionDetailPresenter.class.getSimpleName();

    private final String FORMAT = DateUtils.FORMAT;// 时间的格式

    private Context context;

    private ActionDetailActivity baseActivity;


    private IActionDetailView actionDetailView;

    public static final String MYUSERID = "myUserID";

    public static final int INTENT_EXTRA_NAME_EDIT = 2319;
    // 请求码
    public static final int REQUEST_CODE_TO_COMMENT = 2318;

    private View header;

    /**
     * 活动详情Id
     */
    private String actionDetailId;

    private String userId = "";


    /**
     * 分享地址
     */
    public String address;


    private BottomDialogWindow bottomPopupWindow;// 底部弹出框的类


    // 活动详情界面数据集合
    private EventDetailResp info;

    List<String> itemStrList = new ArrayList<String>();// 底部弹出对话框的字符串集合
    List<Integer> itemColorList = new ArrayList<Integer>();// 底部弹出对话框的字体颜色集合

    //是否关注
    private boolean isFollow = false;

    //关注用户 图标
    private TextView ib_followUser;

    //分享 dialog
    private AlertDialog shareDialog;

    //参见成员 正行
    private RelativeLayout rl_action_detail_member_line;

    //点赞
    private RelativeLayout rl_Like;
    private TextView tv_like;
    private ImageView iv_like;

    private boolean isLike = false;

    //分享 标题
    private String shareTitle;

    // 是否收藏
    private boolean isToFavorite;

    //是否已分享


    //底部菜单 相关
    //底部 布局
    private View ll_buttom;

    //评论
    private TextView tv_postNum;
    View rl_discuss;

    //活动详情 暂时 没确定格式
    private RichTextView mRtvActionDetail;

    /**
     * 活动参加成员
     */
    List<EventMembersResp.ItemsBean> eventMembers;

    /**
     * 活动点赞人数
     */
    NodesLikesCountResp likesCountResp;

    /**
     * 讨论总数
     */
    int commentsTotal;

    /**
     * 本人在活动中的角色
     */
    int meRole;

    public ActionDetailPresenter(Context context, ActionDetailActivity baseActivity, Intent intent) {
        this.context = context;
        this.actionDetailView = this.baseActivity = baseActivity;
        header = actionDetailView.getHeader();
        if (null != intent) {
            actionDetailId = intent.getStringExtra("nodeId");
        }

        initView();

        // 改为活动的标题
        shareTitle = context.getString(R.string.doyouhike_share_title);
        actionDetailView.onLoadStart();
    }

    //标题
    TextView title;
    //活动组织者 信息
    View userInfo;
    //头像
    ImageView iv_head;
    //昵称
    TextView nickname;
    //性别
    ImageView iv_sex;
    //发起活动次数
    TextView tv_action_num;
    //出发地
    TextView tv_location_start;
    //时间
    TextView tv_action_detail_date;
    //天数
    TextView tv_action_detail_totalDay;
    //参加人数
    TextView tv_action_detail_jionedNum;
    //人数 限制
    TextView tv_action_detail_limitation;
    //费用
    TextView tv_cost;
    //报名活动
    TextView tv_attend;
    //管理
    View rl_manage;
    //编辑
    View rl_edit;
    //退出
    View rl_exit;

    private void initView() {

        //标题
        title = (TextView) header.findViewById(R.id.tv_action_detail_title);

        //活动组织者 信息
        userInfo = header.findViewById(R.id.rl_action_detail_user_info);
        //头像
        iv_head = (ImageView) header.findViewById(R.id.action_detail_avator);
        //昵称
        nickname = (TextView) header.findViewById(R.id.tv_nickname);
        //性别
        iv_sex = (ImageView) header.findViewById(R.id.iv_action_detail_sex);
        //发起活动次数
        tv_action_num = (TextView) header.findViewById(R.id.tv_action_detail_actionNum);
        //关注 //0 未关注 1 已经关注 2 互相关注
        ib_followUser = (TextView) header.findViewById(R.id.ib_action_detail_attention);

        //出发地
        tv_location_start = (TextView) header.findViewById(R.id.tv_location_start_selected);
        //时间
        tv_action_detail_date = (TextView) header.findViewById(R.id.tv_action_detail_date);
        //天数
        tv_action_detail_totalDay = (TextView) header.findViewById(R.id.tv_action_detail_totalDay);
        //参加人数
        tv_action_detail_jionedNum = (TextView) header.findViewById(R.id.tv_action_detail_jionedNum);
        //人数 限制
        tv_action_detail_limitation = (TextView) header.findViewById(R.id.tv_action_detail_limitation);
        //费用
        tv_cost = (TextView) header.findViewById(R.id.ll_action_detail_cost);
        //活动详情 暂时 没确定格式
        mRtvActionDetail = (RichTextView) header.findViewById(R.id.rt_action_detail_content);

        ll_buttom = actionDetailView.getBottomView();

        tv_like = (TextView) ll_buttom.findViewById(R.id.tv_action_detail_like_num);
        iv_like = (ImageView) ll_buttom.findViewById(R.id.iv_action_detail_zan_unclick);
        //活动 赞  有点赞的 数量
        rl_Like = (RelativeLayout) ll_buttom.findViewById(R.id.rl_action_detail_like);


        //评论总数
        tv_postNum = (TextView) ll_buttom.findViewById(R.id.tv_action_detail_postNum);
        rl_discuss = ll_buttom.findViewById(R.id.rl_action_detail_discuss);


        tv_attend = (TextView) ll_buttom.findViewById(R.id.tv_action_detail_attend);
        rl_manage = ll_buttom.findViewById(R.id.rl_action_detail_manage);
        rl_edit = ll_buttom.findViewById(R.id.rl_action_detail_bottom_edit);
        rl_exit = ll_buttom.findViewById(R.id.rl_action_detail_bottom_exit);
    }

    /**
     * 初始化数据
     *
     * @param
     */
    public void requestData() {

        if (StringUtil.isNotEmpty(actionDetailId)) {
            //获取分享地址
            if (StringUtil.isEmpty(address))
                getShareAddress();
            //获取本人与活动的关系
            UsersHelper.getSingleTon()
                    .getNodeStates(baseActivity, actionDetailId, nodeStateListener);

            getDataFromNet();
        }
    }


    /**
     * 填充数据
     *
     * @param info
     */
    public void initContentData(EventDetailResp info) {
        try {
            this.info = info;

            //设置标题
            EventHelper.getInstance()
                    .setEventStatus(baseActivity.getTar_title().getTitle_text(), info.getEvent_status());

            initViewHeadData();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 填充头部UI数据
     */
    private void initViewHeadData() {
        //标题
        title.setText(info.getTitle());
        //
        userInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityRouter.openOtherPageActivity(context, info.getLeader_user().getUser_id());
            }
        });


        String leaderAvator = Constant.PHOTO_DOMAIN_PATH + info.getLeader_user().getAvatar();
        GlideHelper.displayHeader(context, iv_head, leaderAvator);


        UserHeadNickClickHelper.getInstance().setClickListener(iv_head,
                info.getLeader_user().getNick_name(),
                info.getLeader_user().getUser_id(),
                info.getLeader_user().getUser_id(),
                info.getLeader_user().getAvatar());


        nickname.setText(info.getLeader_user().getNick_name());
        nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityRouter.openOtherPageActivity(context, info.getLeader_user().getUser_id());
            }
        });


        if (StringUtil.isNotEmpty(info.getLeader_user().getSex()) && !info.getLeader_user().getSex().equals(Constant.UNKNOWN)) {
            //男
            if (info.getLeader_user().getSex().equals(Constant.MALE))
                iv_sex.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_me_male_3x));
                //女
            else if (info.getLeader_user().getSex().equals(Constant.FEMALE))
                iv_sex.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_me_female_3x));
        } else
            iv_sex.setVisibility(View.GONE);


        if (StringUtil.isNotEmpty(info.getLeader_user().getEvent_num())) {
            tv_action_num.setVisibility(View.VISIBLE);
            tv_action_num.setText("共发起" + info.getLeader_user().getEvent_num() + "次活动");
        } else
            tv_action_num.setVisibility(View.GONE);


        if (!info.getLeader_user().getUser_id().equals(userId)) {
            Follow follow = UsersHelper.getSingleTon().getFollowByUserId(info.getLeader_user().getUser_id());

            int followState = AttendState.NOT_ATTEND;
            if (follow != null)
                followState = follow.getFollow_each() ? AttendState.ATTENTION_EACH_OTHER : AttendState.ATTENDING;

            FollowButtonHelper.setTextState(ib_followUser, followState, info.getLeader_user().getUser_id());
            //已关注 或者相互关注
            if (followState == AttendState.ATTENDING || followState == AttendState.ATTENTION_EACH_OTHER) {
                ib_followUser.setVisibility(View.GONE);
                isFollow = true;
            } else {
                isFollow = false;
            }


        } else {
            ib_followUser.setVisibility(View.GONE);
        }

        //出发地
        tv_location_start.setText(info.getFrom().getName());
        //目的地
        addActionDes(info.getTo());

        StringBuffer sb = new StringBuffer();
        String begin_date = DateUtils.getStrDate(Long.parseLong(info.getBegin_date()) * 1000L, FORMAT);

        //由开始时间转 结束时间

        int iDays = info.getDays() - 1;
        long days = iDays * 86400L;
        long endDate = Long.parseLong(info.getBegin_date()) + days;

        info.setEnd_date(String.valueOf(endDate));

        String end_date_long = DateUtils.getStrDate(endDate * 1000L, FORMAT);

        sb.append(begin_date);
        sb.append("-");
        sb.append(end_date_long);

        tv_action_detail_date.setText(sb.toString());
        //天数
        tv_action_detail_totalDay.setText("共" + info.getDays() + "天");
        //集合时间
        TextView tvGather = (TextView) header.findViewById(R.id.tv_action_detail_time);
        View viGater = header.findViewById(R.id.rl_time);
        if (StringUtil.isNotEmpty(info.getGather_date())
                && !info.getGather_date().equals("0")) {
            UIUtils.showView(viGater, true);
            String gather_date = DateUtils.getStrDate(Long.parseLong(info.getGather_date()) * 1000L, "HH:mm");
            tvGather.setText(gather_date);

            String year_moth = DateUtils.getStrDate(Long.parseLong(info.getGather_date()) * 1000L, FORMAT);
            TextView tv_year_moth = (TextView) header.findViewById(R.id.tv_act_action_detail_time_date);
            tv_year_moth.setText(year_moth);
        } else {
            tvGather.setText("");
            UIUtils.showView(viGater, false);
        }


        //参加人数
        if (StringUtil.isNotEmpty(info.getMember_num() + "")) {
            tv_action_detail_jionedNum.setText(info.getMember_num() + "");
        }

        tv_action_detail_limitation.setText("/" + info.getMember_limit());

        //添加标签
        addTags(info.getTags());

        //费用
        if (StringUtil.isNotEmpty(info.getFee_type())) {

            UIUtils.showView(tv_cost, true);
            if (info.getFee_type().equals("AA")) {
                tv_cost.setTextColor(Color.parseColor("#FF4A90E2"));
                tv_cost.setBackgroundResource(R.drawable.shape_action_detail_cost_aa);
                tv_cost.setText("AA活动，费用按人均摊");
            } else if (info.getFee_type().equals("free")) {
                tv_cost.setTextColor(Color.parseColor("#FF48ADA0"));
                tv_cost.setBackgroundResource(R.drawable.shape_action_detail_cost_free);
                tv_cost.setText("免费活动，无须活动经费");
            } else {
                UIUtils.showView(tv_cost, false);
            }
        } else {
            UIUtils.showView(tv_cost, false);
        }

        //活动详情 暂时 没确定格式
        if (info.getContent() != null) {
            mRtvActionDetail.initData(info.getContent());
            mRtvActionDetail.setVisibility(View.VISIBLE);
        } else
            mRtvActionDetail.setVisibility(View.GONE);

        mRtvActionDetail.requestLayout();
    }


    /**
     * 获取分享地址
     */
    private void getShareAddress() {
        NodesHelper.getInstance().nodeShareUrl(baseActivity, actionDetailId, shareListener);
    }

    IOnResponseListener<Response<NodeShareUrlResp>> shareListener = new IOnResponseListener<Response<NodeShareUrlResp>>() {
        @Override
        public void onSuccess(Response<NodeShareUrlResp> response) {
            address = response.getData().getUrl();
            String title = "";
            if (info != null && info.getTitle() != null)
                title = info.getTitle();
        }

        @Override
        public void onError(Response response) {

        }
    };

    /**
     * 请求详情 数据
     */
    public void getDataFromNet() {
        LoginUser user = UserInfoUtil.getInstance().getCurrentUser();
        if (user != null) {
            userId = user.getUser().getUser_id();
        }

        //获取活动内容
        EventHelper.getInstance()
                .getEventDetail(baseActivity, actionDetailId, contentResponseListener);

    }

    /**
     * 获取本人与活动的关系
     */
    IOnResponseListener<Response<UserNodeStateResp>> nodeStateListener = new IOnResponseListener<Response<UserNodeStateResp>>() {
        @Override
        public void onSuccess(Response<UserNodeStateResp> response) {
            if (response.getData() == null || actionDetailView == null
                    || response.getData().getItems() == null
                    || response.getData().getItems().get(0) == null)
                return;
            isToFavorite = response.getData().getItems().get(0).isFavorited();
        }

        @Override
        public void onError(Response response) {

        }
    };

    /**
     * 添加活动目的地
     *
     * @param list
     */
    private void addActionDes(List<EventDetailResp.ToBean> list) {
        AutoRelativeLayout ag = (AutoRelativeLayout) header.findViewById(R.id.arl_action_detail_location_des);
        ag.removeAllViews();
        for (final EventDetailResp.ToBean resp : list) {
            View destView = LayoutInflater.from(context).inflate(R.layout.item_action_detail_des_location, null);
            LinearLayout linearLayout = (LinearLayout) destView.findViewById(R.id.ll_action_detail_to);
            TextView tv_destination = (TextView) destView.findViewById(R.id.tv_destination);

            tv_destination.setText(resp.getDest_name());
            tv_destination.setEnabled(StringUtil.isNotEmpty(resp.getNode_slug()));

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (StringUtil.isNotEmpty(resp.getNode_slug())) {

                        if (resp.getDest_cat().contains("route")) {
                            ActivityRouter.openRoadDetailActivity(v.getContext(), resp.getNode_slug());
                        } else {
                            ActivityRouter.openDesAndRoadListActivity(v.getContext(), resp.getNode_slug());
                        }
                    }
                }
            });

            RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            ag.addView(destView, rlp);
        }
        ag.requestLayout();
    }

    /**
     * 参加活动成员头像
     *
     * @param memberList
     */
    private void addMemberHead(List<EventMembersResp.ItemsBean> memberList) {


        rl_action_detail_member_line = (RelativeLayout) header
                .findViewById(R.id.rl_action_detail_member_line);


        //  点击查看活动成员列表
        rl_action_detail_member_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityRouter.openActionManagerActivity(context,
                        userId,
                        actionDetailId,
                        meRole,
                        false,
                        true,//显示确认列表
                        info.getEvent_status());

            }
        });


        RecyclerView rlMember = (RecyclerView) header
                .findViewById(R.id.rl_action_detail_join);


        List<EventMembersResp.ItemsBean> temList = memberList;
        if (null == temList) {
            UIUtils.showView(rlMember, false);
            return;
        }


        UIUtils.showView(rlMember, true);

        EventMembersResp.ItemsBean leaderMember = new EventMembersResp.ItemsBean();
        EventMembersResp.ItemsBean.UserBean userBean = new EventMembersResp.ItemsBean.UserBean();
        leaderMember.setUser(userBean);

        leaderMember.getUser().setUser_id(info.getLeader_user().getUser_id());


        if (temList.contains(leaderMember)) {
            //剔除发起人
            temList.remove(leaderMember);
        }


        if (temList.isEmpty()) {
            UIUtils.showView(rlMember, false);
            return;
        }


        if (memberList.size() > 16) {

            temList = temList.subList(0, 16);
        }

        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rlMember.setLayoutManager(linearLayoutManager);

        rlMember.setAdapter(new CommonAdapter<EventMembersResp.ItemsBean>(context, R.layout.item_action_detail_member, temList) {
            @Override
            public void convert(ViewHolder holder, final EventMembersResp.ItemsBean actionMember) {

                ImageView image = holder.getView(R.id.iv_action_detail_member);
                //加载头像
                String avator = Constant.PHOTO_DOMAIN_PATH + actionMember.getUser().getAvatar();
                GlideHelper.displayHeader(context, image, avator);
                UserHeadNickClickHelper.getInstance().setClickListener(image
                        , actionMember.getUser().getNick_name()
                        , actionMember.getUser().getUser_id()
                        , actionMember.getUser().getUser_id()
                        , actionMember.getUser().getAvatar());

            }

        });


    }

    /**
     * 添加类型标签
     */
    private void addTags(List<BaseTag> tags) {
        AutoRelativeLayout ag = (AutoRelativeLayout) header.findViewById(R.id.ll_action_detail_tags);
//        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        rlp.addRule(RelativeLayout.CENTER_VERTICAL);//addRule参数对应RelativeLayout XML布局的属性
//        rlp.setMargins(DensityUtil.dip2px(context, 116f), 0, DensityUtil.dip2px(context, 15f), 0);
        ag.removeAllViews();


        if (null != tags && !tags.isEmpty()) {
            TextView tv = getTagTextView("#");
            ag.addView(tv);

            for (BaseTag info : tags) {
                TextView contentTv = getTagTextView(info.getTag_name());
                ag.addView(contentTv);
            }

        }


//        ag.setLayoutParams(rlp);
        ag.requestLayout();
    }


    private TextView getTagTextView(String content) {
        TextView tv = new TextView(context);
        tv.setTextColor(context.getResources().getColor(R.color.txt_light_tip_26));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12); //12SP
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, DensityUtil.dip2px(context, 5), 0);
        tv.setGravity(Gravity.CENTER);
        tv.setLayoutParams(lp);
        tv.setText(content);

        return tv;
    }

    /**
     * 活动详情 回调
     */
    IOnResponseListener contentResponseListener = new IOnResponseListener<Response<EventDetailResp>>() {

        @Override
        public void onSuccess(Response<EventDetailResp> response) {
            actionDetailView.onLoadSuccess();
            initContentData(response.getData());
            //获取活动参加成员
            EventHelper.getInstance().getEventMenbers(baseActivity, actionDetailId, false,
                    eventMembersListener);
        }

        @Override
        public void onError(Response response) {
            actionDetailView.onLoadError(response.getCode());
        }

    };


    /**
     * 活动参与人员回调
     */
    IOnResponseListener<Response<EventMembersResp>> eventMembersListener = new IOnResponseListener<Response<EventMembersResp>>() {
        @Override
        public void onSuccess(Response<EventMembersResp> response) {
            eventMembers = response.getData().getItems();
            //获取自己的角色
            meRole = EventUtil.getMeRole(eventMembers);

            List<EventMembersResp.ItemsBean> jionMembers = EventUtil.getJionMembers(eventMembers);
            //成员头像
            addMemberHead(jionMembers);

            //底部管理菜单
            initBottomView();
            //活动点赞数
            EventHelper.getInstance().getEventLikeCount(baseActivity, actionDetailId, likesListener);

            //评论数
            setCommentCount();
        }

        @Override
        public void onError(Response response) {

        }
    };

    /**
     * 赞
     */
    IOnResponseListener<Response<NodesLikesCountResp>> likesListener = new IOnResponseListener<Response<NodesLikesCountResp>>() {
        @Override
        public void onSuccess(Response<NodesLikesCountResp> response) {
            likesCountResp = response.getData();
            //初始化赞
            initLikeBtn();

        }

        @Override
        public void onError(Response response) {

        }
    };

    /**
     * 讨论
     */
    IOnResponseListener<Response<EventCommentListResp>> commentsListener = new IOnResponseListener<Response<EventCommentListResp>>() {
        @Override
        public void onSuccess(Response<EventCommentListResp> response) {


            commentsTotal = response.getData().getPage().getTotal_records();
            //初始化讨论
            initCommentBtn();
        }

        @Override
        public void onError(Response response) {

        }
    };


    /**
     * 处理  关注请求 返回
     *
     * @param response
     */
    public void setFollowUserResponse(AccountUserFollowEvent response) {
        String follow_id = (String) response.getExtraTag();
        if (response.getCode() == 0 && follow_id.equals(info.getLeader_user().getUser_id())) {
            ib_followUser.setVisibility(View.GONE);
        }
    }


    IOnResponseListener favoriteListener = new IOnResponseListener() {
        @Override
        public void onSuccess(Response response) {
            NodesFavoritesPost post = (NodesFavoritesPost) response.getExtraTag();
            if (post.getRequestMethod() == RequestMethod.POST) {
                StringUtil.showSnack(context, "收藏成功");
                StatisticsEventUtil.collect(context);
            }
            isToFavorite = !isToFavorite;

        }

        @Override
        public void onError(Response response) {

        }
    };


    /**
     * 点赞 回调
     */
    public void setLikeResponse(NodesLikeEvent event) {
        rl_Like.setClickable(true);

        if (event.getCode() == 0) {
            NodesLikePost.LikeMode likeMode = (NodesLikePost.LikeMode) event.getExtraTag();
            if (likeMode.isLike()) {
                isLike = true;
                tv_like.setTextColor(context.getResources().getColor(R.color.orange_bg));
                //设置点击数量+1
                likesCountResp.setLike_num(likesCountResp.getLike_num() + 1);
                StatisticsEventUtil.Like(context);

            } else {
                isLike = false;

                tv_like.setTextColor(context.getResources().getColor(R.color.text_action_detail_zan));
                //设置点击数量 -1
                if (likesCountResp.getLike_num() != 0)
                    likesCountResp.setLike_num(likesCountResp.getLike_num() - 1);
            }
            tv_like.setText(likesCountResp.getLike_num() == 0 ? "赞" : likesCountResp.getLike_num() + "");

        }
        //失败后 数据回退
        else {
            if (isLike) {
                iv_like.setImageDrawable(context.getResources().getDrawable(R.drawable.activity_icon_like_click));
            } else {
                iv_like.setImageDrawable(context.getResources().getDrawable(R.drawable.activity_icon_like));
            }
        }

    }

    /**
     * 弹出分享对话框
     */
    public void showShareDialog() {
        shareDialog = new AlertDialog.Builder(context, R.style.dialog_full_screen_buttom_up).create();
        shareDialog.getWindow().setGravity(Gravity.BOTTOM);


        View shareView = View.inflate(context,
                R.layout.dialog_action_share, null);

        LinearLayout ll_share_weixin = (LinearLayout) shareView
                .findViewById(R.id.ll_share_weixin);
        LinearLayout ll_share_pyq = (LinearLayout) shareView
                .findViewById(R.id.ll_share_pyq);
        LinearLayout ll_share_weibo = (LinearLayout) shareView
                .findViewById(R.id.ll_share_weibo);
        LinearLayout ll_copy_link = (LinearLayout) shareView
                .findViewById(R.id.ll_copy_link);
        LinearLayout ll_share_qq = (LinearLayout) shareView
                .findViewById(R.id.ll_share_qq);
        LinearLayout ll_share_qzone = (LinearLayout) shareView
                .findViewById(R.id.ll_share_qzone);

        ll_share_weixin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //判断微信客户端是否存在
                String packageName = context.getString(R.string.wechat_packagename);
                if (!CheckAppExistUtil.checkAppExist(context, packageName)) {
                    CheckAppExistUtil.showAlertDialog(context);
                    shareDialog.dismiss();
                    return;
                }
                // 改为活动图片的链接
                int shareImgUrl = R.drawable.ic_launcher;

                // 改为活动介绍的内容
                if (null != info) {


                    String shareContent = ShareUtil.getActionShareContent(ShareUtil.SHARE_TO_WEIXIN,
                            info.getTitle());
                    shareTitle = ShareUtil.getActionShareTitle(ShareUtil.SHARE_TO_WEIXIN,
                            info.getTitle());

                    // 改为活动的链接
                    String targetUrl = address;

                    ShareUtil.shareToWeixin(context, shareImgUrl,
                            shareContent, shareTitle, targetUrl);

                }
                shareDialog.dismiss();
            }
        });

        ll_share_pyq.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //判断微信客户端是否存在
                String packageName = context.getString(R.string.wechat_packagename);
                if (!CheckAppExistUtil.checkAppExist(context, packageName)) {
                    CheckAppExistUtil.showAlertDialog(context);
                    shareDialog.dismiss();
                    return;
                }
                // 改为活动图片的链接
                int shareImgUrl = R.drawable.ic_share_img;

                // 改为活动介绍的内容
                if (null != info) {


                    String shareContent = ShareUtil.getActionShareContent(ShareUtil.SHARE_TO_PYQ,
                            info.getTitle());
                    shareTitle = ShareUtil.getActionShareTitle(ShareUtil.SHARE_TO_PYQ,
                            info.getTitle());

                    // 改为活动的链接
                    String targetUrl = address;

                    ShareUtil.shareToPyq(context, shareImgUrl,
                            shareContent, shareTitle, targetUrl);

                }
                shareDialog.dismiss();
            }
        });

        ll_share_weibo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 改为活动图片的链接
                int shareImgUrl = R.drawable.ic_launcher;

                // 改为活动介绍的内容
                if (null != info) {


                    String shareContent = ShareUtil.getActionShareContent(ShareUtil.SHARE_TO_WEIBO,
                            info.getTitle());
                    shareTitle = ShareUtil.getActionShareTitle(ShareUtil.SHARE_TO_WEIBO,
                            info.getTitle());

                    // 改为活动的链接
                    String targetUrl = address;

                    ShareUtil.shareToWeibo(context, shareImgUrl,
                            shareContent, shareTitle, targetUrl);
                }

                shareDialog.dismiss();
            }
        });

        ll_copy_link.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 改为要复制的内容(文字或者网址)
                String content = address;
                ShareUtil.copyLink(context, content);
                shareDialog.dismiss();
            }
        });

        ll_share_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 改为活动图片的链接
                int shareImgUrl = R.drawable.ic_launcher;

                // 改为活动介绍的内容
                if (null != info) {
                    String shareContent = ShareUtil.getActionShareContent(ShareUtil.SHARE_TO_QQ,
                            info.getTitle());
                    shareTitle = ShareUtil.getActionShareTitle(ShareUtil.SHARE_TO_QQ,
                            info.getTitle());

                    // 改为活动的链接
                    String targetUrl = address;

                    ShareUtil.shareQQ(context, shareImgUrl,
                            shareContent, shareTitle, targetUrl);
                }

                shareDialog.dismiss();
            }
        });
        ll_share_qzone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 改为活动图片的链接
                int shareImgUrl = R.drawable.ic_launcher;

                // 改为活动介绍的内容
                if (null != info) {
                    String shareContent = ShareUtil.getActionShareContent(ShareUtil.SHARE_TO_QZONE,
                            info.getTitle());
                    shareTitle = ShareUtil.getActionShareTitle(ShareUtil.SHARE_TO_QZONE,
                            info.getTitle());


                    // 改为活动的链接
                    String targetUrl = address;

                    ShareUtil.shareQZone(context, shareImgUrl,
                            shareContent, shareTitle, targetUrl);
                }

                shareDialog.dismiss();
            }
        });


        TextView favorite = (TextView) shareView.findViewById(R.id.tv_favorite);
        TextView repost = (TextView) shareView.findViewById(R.id.tv_repost);
        TextView forward = (TextView) shareView.findViewById(R.id.tv_forwarding_item);
        final TextView cancel = (TextView) shareView.findViewById(R.id.tv_cancel);
        if (isToFavorite) {
            favorite.setText("取消收藏");
        } else {
            favorite.setText("收藏");
        }
        favorite.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!UserInfoUtil.getInstance().isLogin()) {//未登录，跳转到登陆界面
                    ActivityRouter.openLoginActivity(context);
                    return;
                }
                if (isToFavorite) {
                    UsersHelper.getSingleTon().deleteFavorite(context, actionDetailId, favoriteListener);
                } else {
                    UsersHelper.getSingleTon().addFavorite(context, actionDetailId, favoriteListener);

                }

                shareDialog.dismiss();
            }
        });
        repost.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ActivityRouter.openReportActivity(context, actionDetailId, true);
                shareDialog.dismiss();
            }
        });
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareDialog.dismiss();
                if (!UserInfoUtil.getInstance().isLogin()) {//未登录，跳转到登陆界面
                    ActivityRouter.openLoginActivity(context);
                    return;
                }
                new ForwardingDialog(context, actionDetailId).show();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                shareDialog.dismiss();
            }
        });
        shareDialog.show();
        //
        //Display display = shareDialog.getWindow().getWindowManager().getDefaultDisplay();
        shareDialog.setContentView(shareView);
        shareDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);


    }

    /**
     * 初始化 底部 菜单
     */
    public void initBottomView() {
        if (info == null || info.getEvent_status() == null)
            return;
        //设置 显示
        ll_buttom.setVisibility(View.VISIBLE);
        tv_attend.setEnabled(true);
        setScrollHideMenu();
        if (info.getEvent_status().equals(Constant.EVENT_RECRUTING) ||
                info.getEvent_status().equals(Constant.EVENT_FULL)) {

            //活动未结束
            recruiting();

        } else {

            //活动已结束
            exiration();
        }


    }

    /**
     * 活动已结束
     */
    private void exiration() {
        //活动结束活动发起人仍然可以编辑
        //显示 编辑 布局

        if (meRole == EventRole.CONVENER) {
            //发起人

            //活动状态为已结束，发起人亦可进入活动管理；隐藏确认/删除成员、修改角色等操作
            UIUtils.showView(tv_attend, false);
            UIUtils.showView(rl_manage, true);
            UIUtils.showView(rl_edit, true);
            UIUtils.showView(rl_exit, false);
            setBottomViewClickEvent(rl_manage, rl_edit, rl_exit);

        } else {

            UIUtils.showView(tv_attend, true);
            UIUtils.showView(rl_manage, false);
            UIUtils.showView(rl_edit, false);
            UIUtils.showView(rl_exit, false);

            tv_attend.setText("报名结束");
            tv_attend.setTextColor(context.getResources().getColor(
                    R.color.white_word));
            tv_attend.setBackgroundColor(context.getResources().getColor(
                    R.color.me_navi_title_parent));
            tv_attend.setEnabled(false);


        }
    }

    /**
     * 活动未结束
     * 只有发起人和协作才能管理活动
     */
    private void recruiting() {
        if (meRole == EventRole.VISITOR) {
            // 未参加
            tv_attend.setText("报名");

            tv_attend.setTextColor(context.getResources().getColor(
                    R.color.white_word));
            tv_attend.setBackgroundResource(R.drawable.selector_attend);

            UIUtils.showView(tv_attend, true);
            UIUtils.showView(rl_manage, false);
            UIUtils.showView(rl_edit, false);
            UIUtils.showView(rl_exit, false);

            tv_attend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!UserInfoUtil.getInstance().isLogin()) {
                        //还没登陆了
                        openLoginActivity();
                        return;
                    }


                    //参加活动 召集中\进行中可以参加
                    if (Constant.EVENT_RECRUTING.equals(info.getEvent_status()) || Constant.EVENT_FULL.equals(info.getEvent_status())) {
                        Intent attendIntent = new Intent(context, ActionAttendActivity.class);
                        attendIntent.putExtra("nodeId", actionDetailId);
                        baseActivity.startActivityForResult(attendIntent, 0);
                    }
                }
            });

        } else if (meRole == EventRole.CONVENER) {
            //发起人
            UIUtils.showView(tv_attend, false);
            UIUtils.showView(rl_manage, true);
            UIUtils.showView(rl_edit, true);
            UIUtils.showView(rl_exit, false);

            setBottomViewClickEvent(rl_manage, rl_edit, rl_exit);

        } else if (meRole == EventRole.COOPERATE) {
            // 协作
            UIUtils.showView(tv_attend, false);
            UIUtils.showView(rl_manage, true);
            UIUtils.showView(rl_edit, false);
            UIUtils.showView(rl_exit, true);

            setBottomViewClickEvent(rl_manage, rl_edit, rl_exit);

        } else {
            //已参加
            UIUtils.showView(tv_attend, false);
            UIUtils.showView(rl_manage, false);
            UIUtils.showView(rl_edit, false);
            UIUtils.showView(rl_exit, true);

            setBottomViewClickEvent(rl_manage, rl_edit, rl_exit);
        }
    }

    private void initLikeBtn() {
        if (likesCountResp.getLike_num() != 0)
            tv_like.setText(likesCountResp.getLike_num() + "");
        //用户点击了 赞
        if (likesCountResp.isLiked()) {
            tv_like.setTextColor(context.getResources().getColor(R.color.orange_bg));
            iv_like.setImageDrawable(context.getResources().getDrawable(R.drawable.activity_icon_like_click));
            isLike = true;
        }
        rl_Like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!UserInfoUtil.getInstance().isLogin()) {
                    //还没登陆了
                    openLoginActivity();
                    return;
                }


                if (isLike) {
                    iv_like.setImageDrawable(context.getResources().getDrawable(R.drawable.activity_icon_like));
                    UsersHelper.getSingleTon().unLike(actionDetailId, baseActivity, null);

                } else {
                    iv_like.setImageDrawable(context.getResources().getDrawable(R.drawable.activity_icon_like_click));
                    UsersHelper.getSingleTon().like(actionDetailId, baseActivity, null);
                }
                rl_Like.setClickable(false);
            }
        });
    }

    /**
     * 设置评论数量
     */
    private void initCommentBtn() {

        if (commentsTotal != 0) {
            rl_discuss.setOnClickListener(null);
            tv_postNum.setText(commentsTotal + "");
            rl_discuss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityRouter.openLiveCommentActivity(context, info.getNode_id(), false);
                }
            });
        } else {
            tv_postNum.setText("讨论 ");
            rl_discuss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!UserInfoUtil.getInstance().isLogin()) {
                        //还没登陆了
                        openLoginActivity();
                        return;
                    }
                    ActivityRouter.openAddCommentActivity(context, info.getNode_id());
                }
            });
        }
    }

    private void setBottomViewClickEvent(View rl_manage, View rl_edit, final View rl_exit) {
        //跳转活动管理
        rl_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityRouter.openActionManagerActivity(context,
                        userId,
                        actionDetailId,
                        meRole,//活动角色
                        true,//管理活动,
                        true,//显示确认列表
                        info.getEvent_status());
            }
        });

        rl_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editAction();
            }
        });
        rl_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCancelDialog();
            }
        });

    }

    /**
     * 滑动隐藏
     */
    private void setScrollHideMenu() {
        if (actionDetailView != null && actionDetailView.getContentView() != null)
            actionDetailView.getContentView().setAnimationView(ll_buttom);
    }

    /**
     * 是否退出活动
     */
    private void showCancelDialog() {
        itemStrList.clear();
        itemColorList.clear();
        itemStrList.add("确认退出");
        itemColorList.add(R.color.orange_word);
        bottomPopupWindow = new BottomDialogWindow(context, itemStrList,
                itemColorList);
        bottomPopupWindow.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                EventHelper.getInstance().postEventQuit(baseActivity, actionDetailId, new IOnResponseListener() {
                    @Override
                    public void onSuccess(Response response) {
                        requestData();

                    }

                    @Override
                    public void onError(Response response) {
                        StringUtil.showSnack(context, "操作失败");
                    }
                });

                bottomPopupWindow.dismiss();
            }
        });
        bottomPopupWindow.show();
    }

    /**
     * 是否增加评论
     */
    public void setCommentCount() {
        //评论数
        EventHelper.getInstance().getEVentComments(baseActivity, actionDetailId, commentsListener);
    }


    public EventDetailResp getInfo() {
        return info;
    }


    private final int SHOW_TOP = 0;
    private final int SHOW_LAST = 1;


    private NodeTimeline.ItemsBean.NodeBean getTimeLine() {
        NodeTimeline.ItemsBean.NodeBean nodeBean = new NodeTimeline.ItemsBean.NodeBean();

        nodeBean.setNode_id(info.getNode_id());
        nodeBean.setMinilog_type(info.getNode_type());
        NodeTimeline.ItemsBean.NodeBean.EventBean eventBean = new NodeTimeline.ItemsBean.NodeBean.EventBean();
        //标题
        eventBean.setTitle(info.getTitle());
        eventBean.setFee_type(info.getFee_type());
        eventBean.setBegin_date(info.getBegin_date());
        eventBean.setEnd_date(info.getEnd_date());
        eventBean.setDays(info.getDays() + "");
        eventBean.setEvent_state(info.getEvent_status());
        eventBean.setMember_limit(info.getMember_limit() + "");
        eventBean.setJoined(info.getMember_num() + "");
        eventBean.setEvent_contents(info.getContent());
        eventBean.setGather_date(info.getGather_date());

        //出发地
        if (info.getFrom() != null) {
            eventBean.setDeparture_id(info.getFrom().getCity_id() + "");
            eventBean.setDeparture_name(info.getFrom().getName());
        }
        //目的地
        if (info.getTo() != null) {
            List<GetDestByKeywordResp> dests = new ArrayList<>();
            GetDestByKeywordResp dest;
            for (EventDetailResp.ToBean toBean : info.getTo()) {
                dest = new GetDestByKeywordResp();
                dest.setNode_id(toBean.getDest_id());
                dest.setNode_name(toBean.getDest_name());
                dest.setNode_cat(toBean.getDest_cat());
                dests.add(dest);
            }
            eventBean.setDests(dests);
        }
        //标签
        if (info.getTags() != null) {
            nodeBean.setTags(info.getTags());
        }
        nodeBean.setEvent(eventBean);

        return nodeBean;
    }

    public void editAction() {
        Observable.create(new Observable.OnSubscribe<NodeTimeline.ItemsBean.NodeBean>() {
            @Override
            public void call(Subscriber<? super NodeTimeline.ItemsBean.NodeBean> subscriber) {
                subscriber.onNext(getTimeLine());
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NodeTimeline.ItemsBean.NodeBean>() {
                    @Override
                    public void call(NodeTimeline.ItemsBean.NodeBean nodeBean) {
                        Intent intent = new Intent(baseActivity, EditEventActivity.class);
                        String json = new Gson().toJson(nodeBean);
                        intent.putExtra(Content.TIMELINE, json);
                        baseActivity.startActivity(intent);
                    }
                });

    }

    /**
     * 特定的时机，未登录时调用
     */
    private void openLoginActivity() {
        Intent intent = new Intent();
        intent.setClass(context, LoginActivity.class);
        context.startActivity(intent);

    }

}
