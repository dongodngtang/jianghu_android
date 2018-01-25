/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: HttpInterface.java
 * Author: ChenWeiZhen
 * Version: 1.0
 * Create: 2015-10-09
 *
 * Changes (from 2015-10-21)
 * -----------------------------------------------------------------
 * 2015-10-9 创建HttpInterface.java (ChenWeiZhen);
 * -----------------------------------------------------------------
 * 2015-10-21 添加 INTERFACE_TOPIC_DETAILS字段(wu-yoline);
 * -----------------------------------------------------------------
 * 2015-10-27 添加 INTERFACE_TOPIC_PUBLISH_COMMENT字段;(wu-yoline);
 * -----------------------------------------------------------------
 * 2015-10-28 1、添加 INTERFACE_COLLECT_ADDBOOKMARK字段;(wu-yoline)
 * 			  2、添加 INTERFACE_COLLECT_CANCELBOOKMARK字段;(wu-yoline)
 * -----------------------------------------------------------------
 * 2015-11-03 1、添加 INTERFACE_GET_RECOMMEND_USERS字段;(wu-yoline)
 * 			  2、添加INTERFACE_SEARCH字段;(wu-yoline)
 * -----------------------------------------------------------------
 * 2015-11-04 1、添加INTERFACE_CHECK_CONTACT字段;(wu-yoline)
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.biz.nohttp;

import net.doyouhike.app.bbs.BuildConfig;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;

/**
 * 存放所有接口的类
 * 
 */
public class HttpInterface {

	/** 获取请求数据地址 */
	public static String INTERFACE_GET_SERVER_URL = BuildConfig.API_URL;

	// 登录相关(包括个人资料管理)
	/** 登陆 */
	public static final String INTERFACE_LOGIN = "user/login";
	/** 注册 */
	public static final String INTERFACE_REGISTER = "user/reg";
	/** 获取忘记密码验证码 */
	public static final String INTERFACE_SEND_FINDPWD_VCODE = "user/sendFindPwdVcode";
	/** 验证忘记密码验证码 */
	public static final String INTERFACE_CHECK_FINDPWD_VCODE = "user/modifyPwd";

	/** 获取忘记密码验证码 */
	public static final String INTERFACE_SEND_BIND_PHONE_VCODE = "user/sendBindPhoneSMS";
	/** 验证忘记密码验证码 */
	public static final String INTERFACE_CHECK_BIND_PHONE_VCODE = "user/bindPhone";

	/** 获取注册验证码 */
	public static final String INTERFACE_SEND_REG_VCODE = "phone/sendRegVcode";
	/** 验证注册验证码 */
	public static final String INTERFACE_CHECK_REG_VCODE = "phone/checkRegVcode";

	// 以下，个人（他人）资料相关
	/** 获取自己详细资料 */
	public static final String INTERFACE_USER_MY_PROFILE = "user/myProfile";
	/** 修改个人信息 */
	public static final String INTERFACE_USER_MODIFY_MY_PROFILE = "user/modifyMyProfile";
	/** 其他人的资料 */
	public static final String INTERFACE_USER_OTHER_INFO = "user/get_user_infos_by_other_uid";

	/** 上传头像 */
	public static final String INTERFACE_USER_UPDPHOTO = "user/updPhoto";

	// 反馈相关
	/** 发送反馈到服务器 */
	public static final String INTERFACE_FEEDBACK_SEND = "feedback/send_feedback";
	/** 检查敏感词汇 */
	public static final String INTERFACE_CHECK_SENSITIVE_WORD = "topics/check_sensitive_word";

	// 以下，首页相关
	/** 直播列表：关注 */
	public static final String INTERFACE_TIMELINE = "timeline";
	/** 直播列表：热门 */
	public static final String INTERFACE_TIMELINE_HOT = "timeline/hotTimeline";

	// 以下，消息相关
	/** 获取用户是否接收推送消息 */
	public static final String INTERFACE_GET_RECEIVED_PUSH_MSG = "user/getReceivedPushMsg";
	/** 设置用户是否接收推送消息 */
	public static final String INTERFACE_SET_RECEIVED_PUSH_MSG = "user/setReceivedPushMsg";

	/** 未读消息总数 */
	public static final String INTERFACE_MSG_TOTAL = "user/get_user_msg_total";
	/** 获取评论我的人 */
	public static final String INTERFACE_MSG_COMMENT = "topics/getCommentMeList";
	/** 获取给我点赞的人 */
	public static final String INTERFACE_MSG_LIKELIST = "topics/getMyLikeList";
	/** 获取与我有关的活动 */
	public static final String INTERFACE_MSG_EVENT = "event/get_my_events";

	// 以下，me模块下半部分的三个“列表”接口
	/** 直播列表：我的直播 */
	public static final String INTERFACE_MY_TIMELINE = "timeline/myTimeline";
	/** 用户的活动 */
	public static final String INTERFACE_USER_EVENTS = "event/user_events";
	/** 直播列表：我的收藏 */
	public static final String INTERFACE_MY_FAVORITE = "timeline/getMyCollectList";

	// 以下，关注相关
	/** 关注一个用户 */
	public static final String INTERFACE_FOLLOW_A_USER = "user/doFollow";
	/** 取消关注某用户 */
	public static final String INTERFACE_UNDO_FOLLOW_THE_USER = "user/undoFollow";
	/** 我关注的人的列表 */
	public static final String INTERFACE_USER_MY_FOLLOW = "user/myFollow";
	/** 关注我的人的列表 */
	public static final String INTERFACE_UER_FOLLOW_ME = "user/followMe";
	/** 获得系统推荐的用户的接口 */
	public static final String INTERFACE_GET_RECOMMEND_USERS = "system/getRecommendUser";

	// 以下，点赞相关
	/** 给直播点赞 */
	public static final String INTERFACE_DO_LIKE_TOPIC = "topics/doLikeTopic";
	/** 给直播取消点赞 */
	public static final String INTERFACE_UNLIKE_TOPIC = "topics/unLikeTopic";

	// 以下，直播相关
	/** 获取某直播详情 */
	public static final String INTERFACE_TOPIC_DETAILS = "topics/details";
	/** 发布评论 */
	public static final String INTERFACE_TOPIC_PUBLISH_COMMENT = "topics/publishComment";
	/** 收藏直播 */
	public static final String INTERFACE_COLLECT_ADDBOOKMARK = "collect/addBookMark";
	/** 取消收藏直播 */
	public static final String INTERFACE_COLLECT_CANCELBOOKMARK = "collect/cancelBookMark";
	/** 发布直播接口 */
	public static final String INTERFACE_PUBLIC_TOPIC = "topics/publishTopic";
	/** 上传图片接口 */
	public static final String INTERFACE_UPD_PIC = "topics/updPic";
	/** 删除磨房信息 */
	public static final String INTERFACE_DELETE__NODE = "topics/deleteNode";
	/** 获取更多评论 */
	public static final String INTERFACE_GET_MORE_COMMENTS = "topics/getNodeCommentList";

	// 以下，活动相关
	/** 按时间获取活动列表 */
	public static final String INTERFACE_ACTION_SELECT_BY_TIME = "event/get_events_by_date";
	/** 按类型获取活动列表 */
	public static final String INTERFACE_ACTION_SELECT_BY_TAG = "event/get_events_by_tag";
	/** 按目的地获取活动列表 */
	public static final String INTERFACE_ACTION_SELECT_BY_CAT = "event/get_events_by_cat";
	/** 获取活动详情 */
	public static final String INTERFACE_ACTION_DETAILS = "event/get_event_detail";
	/** 获取热门城市 */
	public static final String INTERFACE_ACTION_HOT_CITY = "topics/get_hot_cities";
	/** 查询活动 */
	public static final String INTERFACE_ACTION_SEARCH = "event/search_events";
	/** 获取活动类型列表 */
	public static final String INTERFACE_ACTION_EVENT_TYPES = "event/get_event_types";
	/** 获取活动目的地列表 */
	public static final String INTERFACE_ACTION_EVENT_CATS = "event/get_event_cats";
	/** 参加活动 */
	public static final String INTERFACE_ACTION_ATTEND = "event/apply_join";
	/** 退出活动 */
	public static final String INTERFACE_ACTION_QUIT = "event/exit_event";
	/** 获取活动参与人员 */
	public static final String INTERFACE_ACTION_MEMBERS = "event/get_event_members";
	/** 确认活动参与人员 */
	public static final String INTERFACE_ACTION_CONFIRMED = "event/accept_member";
	/** 设置活动人员角色 */
	public static final String INTERFACE_SET_ROLE = "event/set_member_role";
	/** 删除活动参加人员 */
	public static final String INTERFACE_DELETE_MEMBER = "event/remove_member";
	/** 举报活动 */
	public static final String INTERFACE_REPORT = "collect/addAccusation";
	/** 获取举报类型 */
	public static final String INTERFACE_REPORT_TYPE = "collect/getAccusationTypeList";
	/** 获取分享地址 */
	public static final String INTERFACE_SHARE = "server/shareUrl";
	/** 获取广告地址 */
	public static final String INTERFACE_ADS = "server/getADData";

	// 以下，搜索相关
	/** 搜索接口 */
	public static final String INTERFACE_SEARCH = "search";
	/** 搜索接口 */
	public static final String INTERFACE_CHECK_CONTACT = "system/checkContact";
	/** 搜索接口 */
	public static final String INTERFACE_GET_TAGS = "system/getTags";

	// 服务器的请求地址
	public static String getInterface(String interfaceStr) {
		String spServerUrl = SharedPreferencesManager.getServerUrl();
		return spServerUrl + interfaceStr;
	}

}
