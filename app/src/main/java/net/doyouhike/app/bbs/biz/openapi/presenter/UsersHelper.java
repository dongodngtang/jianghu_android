package net.doyouhike.app.bbs.biz.openapi.presenter;

import android.content.Context;

import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.CacheMode;

import net.doyouhike.app.bbs.base.application.MyApplication;
import net.doyouhike.app.bbs.biz.db.green.GreenDaoUtils;
import net.doyouhike.app.bbs.biz.db.green.entities.Follow;
import net.doyouhike.app.bbs.biz.db.green.entities.UserFans;
import net.doyouhike.app.bbs.biz.db.green.help.FollowDao;
import net.doyouhike.app.bbs.biz.db.green.help.UserFansDao;
import net.doyouhike.app.bbs.biz.entity.PhoneContactInfo;
import net.doyouhike.app.bbs.biz.event.open.AccountFollowsListEvent;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.BaseTag;
import net.doyouhike.app.bbs.biz.newnetwork.service.ApiReq;
import net.doyouhike.app.bbs.biz.openapi.request.nodes.NodesFavoritesPost;
import net.doyouhike.app.bbs.biz.openapi.request.nodes.NodesLikePost;
import net.doyouhike.app.bbs.biz.openapi.request.searches.SearchUsersGet;
import net.doyouhike.app.bbs.biz.openapi.request.users.BaseRecommendUsersGet;
import net.doyouhike.app.bbs.biz.openapi.request.users.UserEditProfilePut;
import net.doyouhike.app.bbs.biz.openapi.request.users.UserInfoGet;
import net.doyouhike.app.bbs.biz.openapi.request.users.UserProfileGet;
import net.doyouhike.app.bbs.biz.openapi.request.users.UsersContactsPost;
import net.doyouhike.app.bbs.biz.openapi.request.users.UsersFansGet;
import net.doyouhike.app.bbs.biz.openapi.request.users.UsersFavoritesGet;
import net.doyouhike.app.bbs.biz.openapi.request.users.UsersFollowPost;
import net.doyouhike.app.bbs.biz.openapi.request.users.UsersFollowsGet;
import net.doyouhike.app.bbs.biz.openapi.request.users.UsersNodesStateGet;
import net.doyouhike.app.bbs.biz.openapi.request.users.UsersSettingGet;
import net.doyouhike.app.bbs.biz.openapi.request.users.UsersSettingPut;
import net.doyouhike.app.bbs.biz.openapi.request.users.messages.MessagesCommentsGet;
import net.doyouhike.app.bbs.biz.openapi.request.users.messages.MessagesCountsGet;
import net.doyouhike.app.bbs.biz.openapi.request.users.messages.MessagesEventsGet;
import net.doyouhike.app.bbs.biz.openapi.request.users.messages.MessagesLikesGet;
import net.doyouhike.app.bbs.biz.openapi.request.tags.UserTagsPut;
import net.doyouhike.app.bbs.biz.openapi.response.account.UsersFansResp;
import net.doyouhike.app.bbs.biz.openapi.response.account.UsersFollowsResp;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.UserInfoUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * 作者：luochangdong on 16/9/13
 * 描述：用户信息管理
 */
public class UsersHelper {

    private static UsersHelper instance;
    private UserFansDao fansDao;
    private FollowDao followDao;
    private static HashMap<String, Follow> follows = new HashMap<>();

    public static UsersHelper getSingleTon() {
        if (instance == null)
            instance = new UsersHelper();
        return instance;
    }

    private UsersHelper() {
        fansDao = GreenDaoUtils.getSingleTon().getmDaoSession().getUserFansDao();
        followDao = GreenDaoUtils.getSingleTon().getmDaoSession().getFollowDao();
    }

    /**
     * 未读消息活动数
     *
     * @param context
     * @param user_id
     * @param listener
     */
    public void getMsgEvent(Context context, String user_id, IOnResponseListener listener) {
        MessagesEventsGet get = new MessagesEventsGet(user_id);
        get.setPage_size(1);
        get.setPage_index(1);
        get.setCancelSign(context);
        ApiReq.doGet(get, listener);
    }

    /**
     * 未读消息评论数
     *
     * @param context
     * @param user_id
     * @param listener
     */
    public void getMsgComment(Context context, String user_id, IOnResponseListener listener) {
        MessagesCommentsGet get = new MessagesCommentsGet(user_id);
        get.setPage_size(1);
        get.setPage_index(1);
        get.setCancelSign(context);
        ApiReq.doGet(get, listener);
    }

    /**
     * 未读消息点赞数
     *
     * @param context
     * @param user_id
     * @param listener
     */
    public void getMsgLike(Context context, String user_id, IOnResponseListener listener) {
        MessagesLikesGet get = new MessagesLikesGet(user_id);
        get.setPage_size(1);
        get.setPage_index(1);
        get.setCancelSign(context);
        ApiReq.doGet(get, listener);
    }

    /**
     * 获取未读的消息
     *
     * @param context
     * @param user_id
     * @param listener
     */
    public void getMessageCount(Context context, String user_id, IOnResponseListener listener) {
        MessagesCountsGet get = new MessagesCountsGet(user_id);
        get.setCancelSign(context);
        ApiReq.doGet(get, listener);
    }

    /**
     * 批量订阅标签接口
     *
     * @param context
     * @param user_id
     * @param tags
     * @param listener
     */
    public void putUserTags(Context context, String user_id, List<BaseTag> tags, IOnResponseListener listener) {
        UserTagsPut put = new UserTagsPut(user_id);
        put.setCancelSign(context);
        put.setSubscriptions(tags);
        ApiReq.doPost(put, listener);
    }

    /**
     * @param context
     * @param user_id
     * @param listener
     */
    public void getMessagesEvent(Context context, String user_id, IOnResponseListener listener) {
        MessagesEventsGet eventsGet = new MessagesEventsGet(user_id);

        ApiReq.doGet(eventsGet, listener);

    }

    /**
     * 时间线 获取Node的 点赞 评论 关注
     *
     * @param context
     * @param node
     * @param listener
     */
    public void getNodeStates(Context context, String node, IOnResponseListener listener) {

        UsersNodesStateGet get = new UsersNodesStateGet(UserInfoUtil.getInstance().getUserId(), node);
        get.setCancelSign(context);
        ApiReq.doGet(get, listener);

    }

    /**
     * 时间线 获取Node的 点赞 评论 关注
     *
     * @param context
     * @param nodes
     * @param listener
     */
    public void getNodeStates(Context context, List<String> nodes, IOnResponseListener listener) {
        if (nodes != null && nodes.size() > 0 && StringUtil.isNotEmpty(UserInfoUtil.getInstance().getUserId())) {
            StringBuilder strNodes = new StringBuilder();
            for (String node : nodes) {
                strNodes.append(node)
                        .append(",");

            }
            UsersNodesStateGet get = new UsersNodesStateGet(UserInfoUtil.getInstance().getUserId(), strNodes.toString());
            get.setCancelSign(context);
            ApiReq.doGet(get, listener);
        }
    }

    /**
     * 获取用户接收推送隐私设置
     *
     * @param context
     * @param user_id
     * @param listener
     */
    public void getUserSetting(Context context, String user_id, IOnResponseListener listener) {
        UsersSettingGet get = new UsersSettingGet(user_id);
        get.setCancelSign(context);
        ApiReq.doGet(get, listener);
    }


    /**
     * 设置是否接收推送隐私设置
     *
     * @param context
     * @param user_id
     * @param key
     * @param value
     * @param listener
     */
    public void putUserSetting(Context context, String user_id, String key, boolean value, IOnResponseListener listener) {
        UsersSettingPut put = new UsersSettingPut(user_id);
        put.setCancelSign(context);
        if (key.equals(UsersSettingPut.FIND_BY_NEARLY)) {
            put.setFind_by_nearly(value);
        }
        if (key.equals(UsersSettingPut.FIND_BY_PHONE)) {
            put.setFind_by_phone(value);
        }
        if (key.equals(UsersSettingPut.PUSH_COMMENT_MSG)) {
            put.setPush_comment_msg(value);
        }
        if (key.equals(UsersSettingPut.PUSH_EVENT_MSG)) {
            put.setPush_event_msg(value);
        }
        if (key.equals(UsersSettingPut.PUSH_FANS_MSG)) {
            put.setPush_fans_msg(value);
        }
        if (key.equals(UsersSettingPut.PUSH_LIKE_MSG)) {
            put.setPush_like_msg(value);
        }
        ApiReq.doPost(put, listener);

    }

    /**
     * 根据通讯录获取好友关系
     *
     * @param context
     * @param user_id
     * @param contacts
     * @param listener
     */
    public void userContacts(Context context, String user_id, List<PhoneContactInfo> contacts, IOnResponseListener listener) {

        List<String> mobiles = new ArrayList<>();
        for (PhoneContactInfo item : contacts) {
            mobiles.add(item.phoneNum);
        }

        UsersContactsPost post = new UsersContactsPost(user_id);
        post.setCancelSign(context);
        post.setMobiles(mobiles);
        post.setExtraInfo(contacts);

        ApiReq.doPost(post, listener);

    }

    /**
     * 搜索用户
     *
     * @param context
     * @param keyword
     * @param listener
     */
    public void searchUsers(Context context, String keyword, IOnResponseListener listener) {
        SearchUsersGet get = new SearchUsersGet(keyword);
        get.setCancelSign(context);

        ApiReq.doGet(get, listener);
    }


    /**
     * 获取系统推荐用户接口
     *
     * @param context
     * @param listener
     */
    public void getRecommendUsers(Context context, IOnResponseListener listener) {
        BaseRecommendUsersGet get = new BaseRecommendUsersGet();
        get.setCancelSign(context);

        ApiReq.doGet(get, listener);
    }


    /**
     * 修改用户信息
     *
     * @param context
     * @param key
     * @param value
     * @param listener
     */
    public void editUserProfile(Context context, String key, String value, IOnResponseListener listener) {
        UserEditProfilePut put = new UserEditProfilePut(UserInfoUtil.getInstance().getUserId());
        put.setCancelSign(context);
        put.setRequestMethod(RequestMethod.PUT);

        if (key.equals(UserEditProfilePut.NICK_NAME)) {
            put.setNick_name(value);
        }
        if (key.equals(UserEditProfilePut.SEX)) {
            put.setSex(value);
        }

        if (key.equals(UserEditProfilePut.CITY_ID)) {
            put.setCity_id(value);
        }

        if (key.equals(UserEditProfilePut.USER_DESC)) {
            put.setUser_desc(value);
        }

        ApiReq.doPost(put, listener);

    }


    /**
     * 获取用户基本信息
     *
     * @param user_id
     * @param listener
     */
    public void getUserInfo(String user_id, IOnResponseListener listener) {
        UserInfoGet get = new UserInfoGet(user_id);
        ApiReq.doGet(get, listener);
    }

    /**
     * 获取用户详细信息
     *
     * @param user_id
     */
    public void getUserProfile(String user_id, IOnResponseListener listener) {
        UserProfileGet get = new UserProfileGet(user_id);
        ApiReq.doGet(get, listener);
    }

    /**
     * 用户删除收藏
     *
     * @param context
     * @param node_id
     * @param listener
     */
    public void deleteFavorite(Context context, String node_id, IOnResponseListener listener) {

        NodesFavoritesPost post = new NodesFavoritesPost(node_id);
        post.setCancelSign(context);
        post.setRequestMethod(RequestMethod.DELETE);
        ApiReq.doPost(post, listener);
    }

    /**
     * 用户添加收藏
     *
     * @param context
     * @param node_id
     * @param listener
     */
    public void addFavorite(Context context, String node_id, IOnResponseListener listener) {

        NodesFavoritesPost post = new NodesFavoritesPost(node_id);
        post.setCancelSign(context);
        ApiReq.doPost(post, listener);
    }

    /**
     * 获取用户收藏列表接口
     */
    public void getUsersFavorites() {
        MyApplication.getInstance().executorService.execute(new Runnable() {
            @Override
            public void run() {
                UsersFavoritesGet get = new UsersFavoritesGet(UserInfoUtil.getInstance().getUserId());
                ApiReq.doGet(get);
            }
        });
    }


    /**
     * 更新用户关注列表
     */
    public void getNetUserFollows() {
        MyApplication.getInstance().executorService.execute(new Runnable() {
            @Override
            public void run() {
                UsersFollowsGet param = new UsersFollowsGet(UserInfoUtil.getInstance().getUserId());
                param.setPage_size(0);
                param.setPage_index(0);
                ApiReq.doGet(param, followsListener);
            }
        });


    }

    public void getNetUserFans() {

        MyApplication.getInstance().executorService.execute(new Runnable() {
            @Override
            public void run() {
                UsersFansGet get = new UsersFansGet(UserInfoUtil.getInstance().getUserId());
                ApiReq.doGet(get, fansListener);
            }
        });

    }

    public void getNetUserBookMarks() {

    }

    /**
     * 获取用户关注列表
     *
     * @return
     */
    public List<Follow> getDbUserFollows() {
        return followDao.loadAll();
    }

    /**
     * 根据user_id获取Follow
     *
     * @param user_id
     * @return
     */
    public Follow getFollowByUserId(String user_id) {
        return followDao.queryBuilder().where(FollowDao.Properties.User_id.eq(user_id)).unique();
    }

    public int getFollowStateByUserId(String user_id) {
        int followState;
        Follow follow = follows.get(user_id);
        if (follow == null) {
            follow = getFollowByUserId(user_id);
            follows.put(user_id, follow);
        }

        followState = getFollowState(follow);
        return followState;
    }

    public int getFollowState(Follow follow) {
        int followState;
        if (follow == null) {
            followState = AttendState.NOT_ATTEND;
        } else if (follow.getFollow_each()) {
            followState = AttendState.ATTENTION_EACH_OTHER;
        } else {
            followState = AttendState.ATTENDING;
        }
        return followState;
    }

    public int getFollowStateBySocial(String social) {
        int followState;
        if (social.equals("not_follow")) {
            followState = AttendState.NOT_ATTEND;
        } else if (social.equals("follow")) {
            followState = AttendState.ATTENDING;
        } else {
            followState = AttendState.ATTENDING;
        }
        return followState;
    }

    /**
     * 新增一个关注用户
     *
     * @param follow
     * @return
     */
    public long addFollow(Follow follow) {
        return followDao.insertOrReplace(follow);
    }

    /**
     * 移除一个关注用户
     *
     * @param user_id
     */
    public void deleteFollow(String user_id) {
        follows.remove(user_id);
        followDao.deleteByKey(user_id);
    }


    public List<UserFans> getDbUserFans() {

        return fansDao.loadAll();
    }

    public void getDbUserBookMarks() {

    }

    /**
     * 点赞 or 取消点赞
     *
     * @param node_id
     */
    public void like(String node_id, Context context, IOnResponseListener listener) {
        NodesLikePost param = new NodesLikePost(node_id);
        param.setCancelSign(context);
        ApiReq.doPost(param, listener);
    }

    /**
     * 撤销点赞
     *
     * @param node_id
     */
    public void unLike(String node_id, Context context, IOnResponseListener listener) {
        NodesLikePost param = new NodesLikePost(node_id);
        param.setRequestMethod(RequestMethod.DELETE);
        param.setCancelSign(context);
        ApiReq.doPost(param, listener);
    }

    /**
     * 关注
     *
     * @param user_id
     * @param listener
     */
    public void follow(String user_id, IOnResponseListener listener) {
        UsersFollowPost param = new UsersFollowPost(user_id);

        ApiReq.doPost(param, listener);
    }

    /**
     * 取消关注
     *
     * @param user_id
     * @param listener
     */
    public void unFollow(String user_id, IOnResponseListener listener) {
        UsersFollowPost param = new UsersFollowPost(user_id);
        param.setRequestMethod(RequestMethod.DELETE);
        ApiReq.doPost(param, listener);
    }

    IOnResponseListener<Response<UsersFollowsResp>> followsListener = new IOnResponseListener<Response<UsersFollowsResp>>() {
        @Override
        public void onSuccess(Response<UsersFollowsResp> response) {
            if (response.getData().getFollows() != null
                    && response.getData().getFollows().size() > 0) {
                //先清表在插入
                followDao.deleteAll();
                followDao.insertOrReplaceInTx(response.getData().getFollows());
            }
        }

        @Override
        public void onError(Response response) {
            EventBus.getDefault().post(new AccountFollowsListEvent());
        }
    };

    IOnResponseListener<Response<UsersFansResp>> fansListener = new IOnResponseListener<Response<UsersFansResp>>() {
        @Override
        public void onSuccess(Response<UsersFansResp> response) {
            if (response.getData().getFans() != null &&
                    response.getData().getFans().size() > 0) {
                fansDao.deleteAll();
                fansDao.insertOrReplaceInTx(response.getData().getFans());
            }
        }

        @Override
        public void onError(Response response) {

        }
    };
}
