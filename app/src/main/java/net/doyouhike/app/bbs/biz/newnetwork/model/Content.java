package net.doyouhike.app.bbs.biz.newnetwork.model;


import net.doyouhike.app.bbs.BuildConfig;
import net.doyouhike.app.bbs.util.StringUtil;

/**
 * Created by zaitu on 15-11-9.
 */
public class Content {

    public static final String TOPIC_TAG = "topic_tag";

    /**
     * 时光机首次
     */
    public static final String TIME_MACHINE_FIRSR = "time_machine_first"; //首次观看时光机，true为首次
    public static final String TIME_MACHINE_REGISTER = "time_machine_register"; //首次观看时光机，true为首次

    public static final String TIME_MACHINE_URL_PAST = BuildConfig.DEBUG ?
            "http://www.test.doyouhike.net/mobile/shiguangji/data_service/access_past?isapp=1&access_token="
            : "http://www.doyouhike.net/mobile/shiguangji/data_service/access_past?isapp=1&access_token=";
    public static final String TIME_MACHINE_URL_FUTURE = BuildConfig.DEBUG ?
            "http://www.test.doyouhike.net/mobile/shiguangji/data_service/access_future?isapp=1&access_token="
            : "http://www.doyouhike.net/mobile/shiguangji/data_service/access_future?isapp=1&access_token=";

    /**
     * 直播 约伴 timeline 对象传递
     */
    public static final String TIMELINE = "TIMELINE";
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int TWO = 2;
    /**
     * 发布状态
     */
    public static final int WAIT = 1;
    public static final int SENDING = 2;
    public static final int SEND_OK = 3;
    public static final int SEND_FAIL = 4;
    public static final int SEND_POST_LIVE = 5;
    public static final int IS_RELEASE_TAG = 122;//首页发布按钮释放标志
    public static final int SEND_CANCEL = 123;//首页发布按钮释放标志
    public static final int RELEASE_TAG = 124;

    /**
     * EventBus 刷新“首页”界面
     */
    public static final int COMMENT_OK = 1205;
    public static final int RELEASE_LIVE_SUCCUSS = 1206;
    public static final int RELEASE_LIVE_FAIL = 1207;
    /**
     * 禁止用户发送约伴
     */
    public static final int RELEASE_LIVE_FORBEDING = 2000001;

    /**
     * 发布次数超限制
     */
    public static final int RELEASE_LIVE_OVER_MORE = 1401022;
    public static final int RELEASE_SELECT_TAGS = 1208;
    public static final int DO_LIKE = 1209;
    public static final int RELEASE_LIVE_DELETE = 1210;
    public static final int FOLLOW_MSG = 1211;
    public static final int RELEASE_SELECT_IMAGE = 1212;
    public static final int RELEASE_LIVE_SENDING = 1213;
    public static final int RELEASE_LIVE_REPOST = 1214;
    public static final int RELEASE_LIVE_CANCEL = 1215;
    public static final int NEW_FANS_TAG = 1216;
    /**
     * 发布约吧用户被禁
     */
    public static final int RELEASE_USER_LIMIT = 1217;
    /**
     * 发布次数超限制
     */
    public static final int RELEASE_LIVE_OVER_COUNT = 1218;


    public static final String EmptyStr = "";
    public static final String SP_TOKEN = "prams0";//token
    public static final String SP_DATA_VER = "prams1";//本地数据版本

    public static final String NET_HEADER_TOKEN = "X-ZAITU-TOKEN";
    public static final String NET_RESP_MSG = "msg";
    public static final String NET_RESP_CODE = "code";
    public static final String NET_RESP_DATA = "data";


    /**
     * 约伴 时间格式化
     */
    public static final String DATE_EVENT_MM_DD = "MM/dd";

    public static final String DATE_EVENT_YYYY_MM_DD_HH_MM = "yyyy/MM/dd HH:mm";


    public static final String DATE_EVENT_YYYY_MM_DD = "yyyy/MM/dd";

    public static boolean EDIT_LIVE_TIP = true;

    /**
     * 线路
     */
    public static final String ROUTE = "route";
    /**
     * 城市
     */
    public static final String CITY = "city";

    /**
     * 评论内容 文本
     */
    public static final String TEXT = "text";

    /**
     * 评论内容 图片
     */
    public static final String IMAGE = "image";


    /**
     * 2
     * 获取分享地址
     * 接口地址：server/shareUrl
     * 请求方式：POST均可
     */
    public static final String REQ_REQ_SHARE_URL = "server/shareUrl";


    /**
     * 4
     * 获取短信验证码接口
     * 请求地址：/phone/sendRegVcode
     * 请求方式：GET
     */
    public static final String REQ_SEND_REG_VCODE = "phone/sendRegVcode";
    /**
     * 6
     * 图片上传接口
     * 发布江湖信息图片上传功能
     * 地址：/topics/updPic
     */
    public static final String REQ_UPD_PIC = "topics/updPic";
    /**
     * 8
     * 忘记密码重置密码短信接口
     * 忘记密码后给自己的手机发送找回密码的短信
     * 请求地址：/user/sendFindPwdVcode 请求方式：GET
     */
    public static final String REQ_SEND_FIND_PWD_VCODE = "user/sendFindPwdVcode";
    /**
     * 10
     * 发送用户绑定手机验证码接口
     * 请求地址：/user/sendBindPhoneSMS 请求方式：GET
     */
    public static final String REQ_SEND_BIND_PHONE_SMS = "user/sendBindPhoneSMS";
    /**
     * 12
     * 获取江湖主页列表
     * 为用户获取他的江湖主页的信息
     * 请求地址  timeline/getTimeline/xxx 请求方式：GET 输入参数:
     */
    public static final String REQ_TIMELINE = "timeline/getTimeline";
    /**
     * 14
     * 获得帖子详情
     * 获得帖子详情接口
     * 请求地址：/topics/details 请求方式：GET
     */
    public static final String REQ_TOPICS_DETAILS = "topics/details";

    /**
     * 18
     * 发布江湖信息:图文
     * 地址：topics/publishTopic
     * POST
     */
    public static final String REQ_PUBLISH_TOPIC_IMG = "topics/publishTopic";
    /**
     * 20
     * 删除江湖信息
     * 地址：topics/deleteNode
     */
    public static final String REQ_DELETE_NODE = "topics/deleteNode";
    /**
     * 22
     * 点赞一个帖子
     * 地址：topics/doLikeTopic
     */
    public static final String REQ_DO_LIKE_TOPIC = "topics/doLikeTopic";
    /**
     * 24
     * 点赞一个评论
     * 地址：topics/doLikeComment
     */
    public static final String REQ_DO_LIKE_COMMENT = "topics/doLikeComment";
    /**
     * 26
     * 获得系统推荐的用户的接口
     * 请求地址：/system/getRecommendUser
     * 请求方式：POST
     */
    public static final String REQ_GET_RECOMMEND_USER = "system/getRecommendUser";
    /**
     * 28
     * 取消关注一个用户
     * 请求地址：/user/undoFollow
     * 请求方式：POST/GET
     */
    public static final String REQ_UNDO_FOLLOW = "user/undoFollow";
    /**
     * 30
     * 查看关注用户的粉丝
     * 请求地址：/user/followMe
     * 请求方式：POST
     */
    public static final String REQ_FOLLOW_ME = "user/followMe";
    /**
     * 32
     * 搜索用户
     * 客户端上传通讯录后服务器只返回已经在江湖注册的用户
     * 请求地址：/search/
     * 请求方式：POST
     */
    public static final String REQ_SEARCH = "search";
    /**
     * 34
     * 查看其他用户的资料
     * 请求地址：/user/get_user_infos_by_other_uid
     * 请求方式：POST
     */
    public static final String REQ_GET_USER_INFOS_BY_OTHER_UID = "user/get_user_infos_by_other_uid";
    /**
     * 36
     * 上传头像
     * 请求地址：/user/updPhoto
     * 请求方式：POST
     */
    public static final String REQ_UPD_PHOTO = "user/updPhoto";
    /**
     * 38
     * 收藏帖子
     * 请求地址：collect/addBookMark
     * 请求方式：POST
     */
    public static final String REQ_ADD_BOOKMARK = "collect/addBookMark";
    /**
     * 40
     * 消息获取对我点赞的人
     * 请求地址：topics/getMyLikeList
     * 请求方式：POST
     */
    public static final String REQ_GET_MY_LIKE_LIST = "topics/getMyLikeList";
    /**
     * 42
     * error 500
     * 消息我的活动
     * 请求地址：event/get_my_events
     * 请求方式：POST
     */
    public static final String REQ_GET_MY_EVENTS = "event/get_my_events";
    /**
     * 44
     * 我的收藏
     * 请求地址：timeline/getMyCollectList
     * 请求方式：POST
     */
    public static final String REQ_GET_MY_COLLECT_LIST = "timeline/getMyCollectList";
    /**
     * 46
     * 获取活动类型列表
     * 请求地址：event/get_event_types
     * 参数： 无
     */
    public static final String REQ_GET_EVENT_TYPES = "event/get_event_types";
    /**
     * 48
     * 查询活动
     * API地址: event/search_events
     * post
     */
    public static final String REQ_SEARCH_EVENTS = "event/search_events";
    /**
     * 50
     * 获取活动的参与人员
     * 请求地址：event/get_event_members
     * 请求方式：POST
     */
    public static final String REQ_GET_EVENT_MEMBERS = "event/get_event_members";
    /**
     * 52
     * 删除活动参加人员
     * API地址: event/remove_member
     * 输入:
     */
    public static final String REQ_REMOVE_MEMBER = "event/remove_member";
    /**
     * 54
     * 退出活动
     * API地址: event/exit_event
     * 输入:
     */
    public static final String REQ_EXIT_EVENT = "event/exit_event";
    /**
     * 56
     * 获取热门城市
     * API地址: topics/get_hot_cities
     */
    public static final String REQ_GET_HOT_CITIES = "topics/get_hot_cities";
    /**
     * 58
     * 添加举报
     * API地址: collect/addAccusation
     */
    public static final String REQ_ADD_ACCUSATION = "collect/addAccusation";
    /**
     * 60
     * 获取用户是否接收推送消息
     * API地址: user/getReceivedPushMsg
     */
    public static final String REQ_GET_RECEIVED_PUSH_MSG = "user/getReceivedPushMsg";
    /**
     * 62
     * 敏感词检查
     * API地址: topics/check_sensitive_word
     * 输入: {"content":"江贼民"}
     * 如果不是敏感词，返回code=0
     * 输出: { "code": "1700001" "msg": "敏感词" }
     */
    public static final String REQ_TOPICS_CHECK_SENSITIVE_WORD = "topics/check_sensitive_word";
    /**
     * 63
     * 获取广告数据
     * API地址: server/getADData
     * 输入: token:xxxx   如果已经登陆传token
     */
    public static final String REQ_SERVER_GET_ADDATA = "server/getADData";

    /**
     * 64 获取首页用于查询的标签
     * API地址: tagdata/get_search_tags
     * 输入: 无
     */
    public static final String REQ_TAGDATA_GET_SEARCH_TAGS = "tagdata/get_search_tags";
    /**
     * 66 获取当前用户订阅的标签
     * <p/>
     * API地址: tagdata/get_my_subscribes
     * 输入:
     * token:xxx   //必须
     */
    public static final String REQ_TAGDATA_GET_MY_SUBSCRIBES = "tagdata/get_my_subscribes";
    /**
     * 67 订阅标签
     * API地址: tagdata/subscribe_tag
     * 输入:
     * token:xxx //必须
     * tagID:1
     */
    public static final String REQ_TAGDATA_SUBSCRIBE_TAG = "tagdata/subscribe_tag";
    /**
     * 68 订阅版块
     * API地址: tagdata/subscribe_forum
     * 输入:
     * token:xxx   //必须
     * forumID:1
     */
    public static final String REQ_TAGDATA_SUBSCRIBE_FORUM = "tagdata/subscribe_forum";
    /**
     * 69 取消订阅的标签
     * API地址: tagdata/unsubscribe
     * 输入:
     * token:xxx   //必须
     * subID:1
     */
    public static final String REQ_TAGDATA_UNSUBSCRIBE = "tagdata/unsubscribe";

    /**
     * 72 批量订阅标签接口
     * API地址: tagdata/subscribe_tag_forum
     */
    public static final String REQ_SUBSCRIBE_TAG_FORUM = "tagdata/subscribe_tag_forum";
    /**
     * 74
     * 发布江湖信息:活动约伴
     * 地址：topics/publishTopic
     * post
     */
    public static final String REQ_PUBLISH_TOPIC_EVENT = "topics/publishTopic";

    /**
     * 75
     * 更新江湖信息:活动约伴
     * 地址：event/update_event
     * post
     */
    public static final String REQ_UPDATE_TOPIC_EVENT = "event/update_event";

    /**
     * 用于验证token是否可用，并且返回当前token的登录用户信息
     */
    public static final String REQ_CHECK_TOKEN = "user/checkToken";

    /**
     * 用户使用手机号码和密码登录并获得token
     * 接口地址：user/login
     * 请求方式：GET
     */
    public static final String REQ_Login = "user/login";


    /**
     * 发布纯文字的江湖信息
     * 接口地址：topics/publishTopic
     * 请求方式：POST
     */
    public static final String REQ_PUBLISH_TOPIC = "topics/publishTopic";


    /**
     * 用户更新自己的用户信息
     * 接口地址：user/updUserInfo
     * 提交方式：POST
     */
    public static final String REQ_UPD_USER_INFO = "user/updUserInfo";

    /**
     * 为手机号phoneNumber注册
     * API接口地址：/user/reg
     * 请求方式：GET
     */
    public static final String REQ_REGISTER = "user/reg";
    /**
     * 用户验证用户手机号码收到的验证码是否可用
     * 请求地址：/phone/checkRegVcode
     * 请求方式：GET
     */
    public static final String REQ_REQ_CHECK_REG_VCODE = "phone/checkRegVcode";
    /**
     * 用于验证手机号码是否已经被注册
     * 请求地址：/phone/status 、
     * 请求方法：GET
     */
    public static final String REQ_REQ_CHECK_PHONE_STATUS = "phone/status";

    /**
     * 当启用app时，获取到数据请求访问地址
     */
    public static final String REQ_SERVER = "server";


    /**
     * 用户登录后绑定手机的接口
     * 请求地址：/phone/status 、
     * 请求方法：GET
     */
    public static final String REQ_BIND_PHONE = "user/bindPhone";


    /**
     * 为匿名用户获取热门的江湖主页的信息
     * 请求地址：/timeline/hotTimeline
     * 请求方法：GET
     */
    public static final String REQ_HOT_TIME_LINE = "timeline/hotTimeline";


    /**
     * 获取帖子更多评论
     * 请求地址：/topics/getNodeCommentList
     * 请求方法：POST
     */
    public static final String REQ_GET_NODE_COMMENT_LIST = "topics/getNodeCommentList";


    /**
     * 发布评论
     * 请求地址：/topics/publishComment
     * 请求方法：POST
     */
    public static final String REQ_PUBLISH_COMMENT = "topics/publishComment";


    /**
     * 取消点赞
     * 请求地址：/topics/unLikeTopic
     * 请求方法：POST
     */
    public static final String REQ_UNLIKE_TOPIC = "topics/unLikeTopic";


    /**
     * 获得系统定义的全部tag
     * 请求地址：/system/getTags
     * 请求方法：POST
     */
    public static final String REQ_GET_TAGS = "system/getTags";


    /**
     * 关注一个用户
     * 请求地址：/user/doFollow
     * 请求方法：POST
     */
    public static final String REQ_GET_DOFOLLOW = "user/doFollow";


    /**
     * 查看用户关注的人
     * 请求地址：/user/myFollow
     * 请求方法：POST
     */
    public static final String REQ_MY_FOLLOW = "user/myFollow";


    /**
     * 客户端上传通讯录后服务器只返回已经在江湖注册的用户
     * 请求地址：/system/checkContact
     * 请求方法：POST
     */
    public static final String REQ_CHECK_CONTACT = "system/checkContact";


    /**
     * 查看自己的资料
     * 请求地址：/user/myProfile
     * 请求方法：POST
     */
    public static final String REQ_MY_PROFILE = "user/myProfile";


    /**
     * 修改自己的资料
     * 请求地址：/user/modifyMyProfile
     * 请求方法：POST
     */
    public static final String REQ_MODIFY_MYPROFILE = "user/modifyMyProfile";


    /**
     * 查看timeline
     * 请求地址：/timeline/myTimeline
     * 请求方法：POST
     */
    public static final String REQ_MY_TIME_LINE = "timeline/myTimeline";


    /**
     * 取消收藏帖子
     * 请求地址：/collect/cancelBookMark
     * 请求方法：POST
     */
    public static final String REQ_CANCEL_BOOKMARK = "collect/cancelBookMark";


    /**
     * 消息获取对评论的人
     * 请求地址：/topics/getCommentMeList
     * 请求方法：POST
     */
    public static final String REQ_GET_COMMENT_ME_LEST = "topics/getCommentMeList";


    /**
     * 获取未读消息总数
     * 请求地址：/user/get_user_msg_total
     * 请求方法：POST
     */
    public static final String REQ_GET_USER_MSG_TOTAL = "user/get_user_msg_total";


    /**
     * 获取用户的活动列表
     * 请求地址：/event/user_events
     * 请求方法：POST
     */
    public static final String REQ_USER_EVENTS = "event/user_events";

    /**
     * 获取活动目地地列表
     * 请求地址：/event/get_event_cats
     * 请求方法：GET
     */
    public static final String REQ_GET_EVENT_CATS = "event/get_event_cats";


    /**
     * 获取活动的详细信息
     * 请求地址：/event/get_event_detail
     * 请求方法：POST
     */
    public static final String REQ_GET_EVENT_DETAIL = "event/get_event_detail";


    /**
     * 确认活动参加人员
     * 请求地址：/event/accept_member
     * 请求方法：POST
     */
    public static final String REQ_ACCEPT_MEMBER = "event/accept_member";


    /**
     * 设置活动人员角色
     * 请求地址：/event/accept_member
     * 请求方法：POST
     */
    public static final String REQ_SET_MEMBER_ROLE = "event/set_member_role";


    /**
     * 报名参加活动
     * 请求地址：/event/apply_join
     * 请求方法：POST
     */
    public static final String REQ_APPLY_JOIN = "event/apply_join";

    /**
     * 获取举报类型
     * 请求地址：/collect/getAccusationTypeList
     * 请求方法：GET
     */
    public static final String REQ_GET_ACCUSATION_TYPE_LIST = "collect/getAccusationTypeList";

    /**
     * 设置用户是否接收推送消息
     * 请求地址：/user/setReceivedPushMsg
     * 请求方法：POST
     */
    public static final String REQ_SET_RECV_PUSH_MSG = "user/setReceivedPushMsg";

    /**
     * 获取用户是否接收推送消息
     * 请求地址：/user/getReceivedPushMsg
     * 请求方法：POST
     */
    public static final String REQ_GET_RECV_PUSH_MSG = "user/getReceivedPushMsg";

    /**
     * 修改密码接口
     */
    public static final String REQ_ModifyPwd = "user/modifyPwd";
    /**
     * 获取发布时用于选择的标签
     * 请求地址：tagdata/get_minilog_tags
     * 请求方法：GET
     */
    public static final String REQ_GET_MINILOG_TAGS = "tagdata/get_minilog_tags";

    /**
     * 根据版块查询话题
     * 请求地址：tagdata/search_by_forum
     * 请求方法：POST
     */
    public static final String REQ_POST_SEARCH_BY_FORUM = "tagdata/search_by_forum";
    /**
     * 根据标签查询话题
     * 请求地址： tagdata/search_by_tag
     * 请求方法：POST
     */
    public static final String REQ_POST_SEARCH_BY_TAG = "tagdata/search_by_tag";
    /**
     * 根据关键字获取目的地
     * 请求地址：destination/get_dest_by_keyword
     * 请求方法：POST
     */
    public static final String REQ_POST_SELECT_DEST_BY_KEY_WORD = "destination/get_dest_by_keyword";


    /**
     * 获取用户隐私设置
     * 请求地址：v2/user/get_user_privacy
     * 请求方法：GET
     */
    public static final String REQ_GET_GET_USER_PRIVACY = "user/get_user_privacy";
    /**
     * 设置用户隐私
     * 请求地址：v2/user/get_user_privacy
     * 请求方法：GET
     */
    public static final String REQ_GET_SET_USER_PRIVACY = "user/set_user_privacy";
    /**
     * 取消约伴活动
     * 请求地址：event/cancel_event
     * 请求方法：Post
     */
    public static final String REQ_GET_DELETE_EVENT = "event/cancel_event";

    /**
     * 查看线路列表
     * 请求地址：event/cancel_event
     * 请求方法：Post
     */
    public static final String REQ_POST_ROAD_LIST_EVENT = "dests/routes";

    /**
     * 查看线路列表 类型
     * 请求地址：
     * 请求方法：Post
     */
    public static final String REQ_POST_ROAD_LIST_TYPE = "dests/route_types";

    /**
     * 查看线路列表 目的城市
     * 请求地址：
     * 请求方法：Post
     */
    public static final String REQ_POST_ROAD_LIST_DES = "dests/dest_cities";

    /**
     * 查看线路详情
     * 请求地址：
     * 请求方法：Post
     */
    public static final String REQ_POST_ROAD_DETAIL_INFO = "dests/route_detail";

    /**
     * 查看线路详情  轨迹
     * 请求地址：
     * 请求方法：Post
     */
    public static final String REQ_POST_ROAD_DETAIL_MAP = "dests/route_maps";

    /**
     * 通过关键字获取目的地线路列表
     */
    public static final String REQ_GET_DEST_ROUTE_LIST = "dests/dest_route_list";
    /**
     * 查看目的地详情
     * 请求地址：
     * 请求方法：Post
     */
    public static final String REQ_POST_DES_AND_ROAD = "dests/dest_detail";

    /**
     * 删除评论
     * 请求地址：
     */
    public static final String REQ_GET_TOPICS_DELCOMMENT = "topics/del_comment";

    /**
     * 转发直播
     * 请求地址：
     * 请求方法：Post
     */
    public static final String REQ_POST_TOPICS_FORWARD_NODE = "topics/forward_node";

    /**
     * 获取给我评论的人
     * 请求地址：
     * 请求方法：get
     */
    public static final String REQ_GET_GET_COMMENT_ME_LIST = "topics/getCommentMeList";

    /**
     * 获取举报类型
     * 请求地址：
     * 请求方法：get
     */
    public static final String REQ_GET_COLLECT_GET_ACCUSATION_TYPELIST = "collect/getAccusationTypeList";


    /**
     * 获取其他用户im信息
     * 地址：user_im/get_users_ims
     * 参数：
     * token:
     * uuid:uuid1,uuid2,uuid3...   #uuid之间用逗号分隔，不能有空格
     */
    public static final String REQ_USER_IM_GET_USERS_IMS = "user_im/get_users_ims";


    /**
     * 发送悄悄话
     *
     * 地址：user_pm/send_users_pms
     * 参数：
     */
    public static final String REQ_USER_PM_SEND_USERS_PMS = "user_pm/send_users_pms";


    public static String mUserAgent = "android_JH : " + System.getProperty("http.agent");

    /**
     * 通过UUID 获取用户信息
     *
     * 地址：user_im/get_user_info
     * 参数：
     */
    public static final String REQ_GET_USER_INFO_BY_UUID = "user_im/get_user_info";


}
