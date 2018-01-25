package net.doyouhike.app.bbs.biz.openapi;

/**
 * 作者：luochangdong on 16/9/7
 * 描述：OpenAPI 2.0 接口地址
 */
public class OpenApiUrl {


    /**
     * 登陆
     * <p>
     * POST
     */
    public final static String ACCOUNT_LOGIN = "account/login";

    /**
     * 获取短信验证码接口
     * <p>
     * POST
     */
    public final static String ACCOUNT_VCODE = "account/v_codes";

    /**
     * 内部应用获取短信验证码接口
     * <p>
     * POST
     */
    public final static String ACCOUNT_INTERNAL_VCODE = "account/internal_v_codes";

    /**
     * 用户忘记密码，重置密码
     * <p>
     * POST
     */
    public final static String ACCOUNT_PASSWORD_RESET = "account/password_reset";

    /**
     * 获取线路列表
     * <p>
     * GET
     */
    public final static String DESTS_ROUTES = "dests/routes";

    /**
     * 获取城市列表
     * <p>
     * GET
     */
    public final static String DESTS_CITIES = "dests/dest_cities";

    /**
     * 获取线路类型列表
     * <p>
     * GET
     */
    public final static String DESTS_ROUTE_TYPES = "dests/route_types";

    /**
     * 获取轨迹列表
     * <p>
     * GET
     */
    public final static String DESTS_ROUTE_MAPS = "dests/route_maps/";

    /**
     * 获取轨迹列表
     * <p>
     * 获取目的地详情
     * <p>
     * GET
     */
    public final static String DESTS_NODES = "dests/dest_nodes/";

    /**
     * 获取活动列表-多条件交差查询
     * <p>
     * GET
     */
    public final static String EVENTS = "events";

    /**
     * 获取活动banner
     * <p>
     * GET
     */
    public final static String EVENT_BANNERS = "ad/event";

    /**
     * 获取活动类型列表
     * <p>
     * GET
     */
    public final static String EVENT_TAGS = "tags/event_tags";


    /**
     * 碎片信息
     * <p>
     * GET
     */
    public final static String NODES = "nodes/";

    /**
     * 获取用户im信息接口
     * <p>
     * GET
     */
    public final static String ACCOUNT_USER_IMS = "users/user_ims/";

    /**
     * 注册用户的接口
     * <p>
     * POST
     */
    public final static String ACCOUNT_REGISTER = "account/users";

    /**
     * 检查验证码是否通过的接口
     * <p>
     * POST
     */
    public final static String ACCOUNT_VERIFY_VCODE = "account/verify_vcode";

    /**
     * 绑定手机号
     * <p>
     * POST
     */
    public final static String ACCOUNT_BIND_MOBILE = "account/bind_mobile";

    /**
     * 获取绑定手机号的验证码
     * <p>
     * POST
     */
    public final static String ACCOUNT_MOBILE_AUTH_VCODES = "account/mobile_auth_vcodes";

    /**
     * 用户信息
     */
    public final static String USERS = "users/";

    /**
     * 热门
     * <p>
     * GET
     */
    public final static String TIMELINE_HOTS = "timelines/hots";

    /**
     * 关注
     * <p>
     * GET
     */
    public final static String TIMELINE_FOLLOWS = "timelines/follows";

    /**
     * 标签
     * <p>
     * GET
     */
    public final static String TIMELINE_TAGS = "subscriptions/tags/";

    /**
     * 上传图片
     * <p>
     * POST
     */
    public final static String UPLOAD_PHOTO = "uploaders/photo";


    /**
     * 获取系统推荐用户接口
     * <p>
     * GET
     */
    public final static String USERS_RECOMMEND = "base/recommend_users";

    /**
     * 获得系统标签的接口
     * <p>
     * GET
     */
    public final static String BASE_MF_TYPES = "base/mf_types";

    /**
     * 目的地搜索
     * <p>
     * GET
     */
    public final static String BASE_MFDEST_NODES = "nodes/mfdest_nodes/";


    /**
     * 搜索用户
     * <p>
     * GET
     */
    public final static String SEARCH_USERS = "searches/users/";

    /**
     * 上传用户头像
     * <p>
     * POST
     */
    public final static String UPLOADER_AVATAR = "uploaders/avatar";

    /**
     * 获取某个用户的活动
     * <p>
     * GET
     */
    public final static String USER_EVENTS = "user_events/";

    /**
     * 获取发布时用于选择的标签
     * <p>
     * GET
     */
    public final static String MINILOG_TAGS = "tags/minilog_tags";

    /**
     * 发布碎片minglog
     * <p>
     * POST
     */
    public final static String MINILOGS = "minilogs";

    /**
     * 获取举报类型
     * GET
     */
    public final static String NODE_ACCUSATION_LIST = "tags/accusation_types";

    /**
     * 获取登录广告数据
     * <p>
     * GET
     */
    public final static String AD_LOADING = "ad/loading";

    /**
     * 用户账户
     */
    public static final String ACCOUNT_USERS = "account/users/";

    /**
     * 软件更新
     * <p>
     * GET
     */
    public static final String APP_VERSION = "app_version";

    /**
     * APP发送悄悄话
     * <p>
     * GET
     */
    public static final String MESSAGE_PMS = "messages/pms";
}
