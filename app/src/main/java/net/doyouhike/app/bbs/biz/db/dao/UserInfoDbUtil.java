package net.doyouhike.app.bbs.biz.db.dao;

import android.text.TextUtils;

import net.doyouhike.app.bbs.biz.db.green.CommonDaoUtils;
import net.doyouhike.app.bbs.biz.db.green.help.ChatUserInfoDao;
import net.doyouhike.app.bbs.biz.db.provider.DraftManage;
import net.doyouhike.app.bbs.biz.entity.CurrentUserDetails;
import net.doyouhike.app.bbs.biz.entity.im.GroupMsgDetail;
import net.doyouhike.app.bbs.biz.db.green.entities.ChatUserInfo;
import net.doyouhike.app.bbs.biz.entity.user.SearchEmIdResult;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.HxUserInfo;
import net.doyouhike.app.bbs.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zengjiang on 16/7/21.
 */
public class UserInfoDbUtil {


    public static UserInfoDbUtil instance;
    public static ChatUserInfoDao chatUserInfoDao;
    private static final String TAG = "UserInfoDbUtil";


    private final Object mDbLock = new Object();//数据库加锁,防止多线程操作的数据库的开关不一致

    public static UserInfoDbUtil getInstance() {
        if (instance == null)
            instance = new UserInfoDbUtil();
        return instance;
    }

    public UserInfoDbUtil() {
        chatUserInfoDao = CommonDaoUtils.getSingleTon().getmDaoSession().getChatUserInfoDao();
    }


    /**
     * 存储用户信息
     *
     * @param details 用户主页的用户信息
     */
    public synchronized void saveUser(CurrentUserDetails details) {

        saveUser(new ChatUserInfo(details));
    }


    /**
     * 批量存储用户数据
     *
     * @param userInfos 用户信息
     */
    public void saveUsers(List<ChatUserInfo> userInfos) {

        if (null == userInfos || userInfos.isEmpty()) {
            return;
        }
        chatUserInfoDao.insertOrReplaceInTx(userInfos);

    }

    /**
     * 存储用户信息
     *
     * @param userInfo 用户信息
     */
    public void saveUser(ChatUserInfo userInfo) {
        chatUserInfoDao.insertOrReplace(userInfo);
    }


    /**
     * @param userId 用户ID
     * @return 获取用户信息
     */
    public ChatUserInfo getUser(String userId) {

        return chatUserInfoDao.load(userId);
    }

    /**
     * @param imId 用户ImId
     * @return 获取用户信息
     */
    public ChatUserInfo getUserFromImId(String imId) {
        return chatUserInfoDao.queryBuilder().where(ChatUserInfoDao.Properties.Im_id.eq(imId)).unique();
    }


    /**
     * @param userId 用户ID
     * @return 是否存在环信ID
     */
    public boolean isEixtEmId(String userId) {
        return !TextUtils.isEmpty(getImId(userId));
    }

    /**
     * @param userId 用户ID
     * @return 环信ID
     */
    public String getImId(String userId) {
        ChatUserInfo userInfo = getUser(userId);

        if (null != userInfo) {
            return userInfo.getIm_id();
        }

        return "";
    }


    public void addOrUpdateUserInfo(ChatUserInfo chatUserInfo) {
        ChatUserInfo dbChatUserInfo = chatUserInfoDao.load(chatUserInfo.getUser_id());
        if (dbChatUserInfo == null) {
            chatUserInfoDao.insert(chatUserInfo);
            LogUtil.d(TAG, "addOrUpdateUserInfo insert");
        } else {
            chatUserInfo.setIm_id(dbChatUserInfo.getIm_id());
            chatUserInfo.setIm_enabled(dbChatUserInfo.getIm_enabled());
            chatUserInfoDao.update(chatUserInfo);
            LogUtil.d(TAG, "addOrUpdateUserInfo update");
        }

    }


    /**
     * 查询环信信息
     *
     * @param uuIds 用户uuId
     * @return 查询结果
     */
    public SearchEmIdResult getImId(List<String> uuIds) {

        synchronized (mDbLock) {
            SearchEmIdResult result = new SearchEmIdResult();

            List<HxUserInfo> validUsers = new ArrayList<>();
            List<String> emptyEmIdUsers = new ArrayList<>();

            result.setEmptyList(emptyEmIdUsers);
            result.setExistEmIdList(validUsers);


            for (String uuId : uuIds) {
                //通过uuid查询本地用户信息
                ChatUserInfo userInfo = chatUserInfoDao.load(uuId);
                //将本地用户信息格式转换,为了方便群发消息
                HxUserInfo hxUserInfo = toHxInfo(userInfo);

                if (null == hxUserInfo || TextUtils.isEmpty(hxUserInfo.getIm_id())) {
                    //环信用户信息为空,或无环信ID,则视为查询不到结果
                    emptyEmIdUsers.add(uuId);
                } else {
                    validUsers.add(hxUserInfo);
                }

            }

            return result;
        }

    }

    /**
     * @param userInfo 用户用户信息
     * @return 环信用户信息
     */
    private HxUserInfo toHxInfo(ChatUserInfo userInfo) {

        if (null != userInfo && !TextUtils.isEmpty(userInfo.getIm_id())) {
            HxUserInfo hxUserInfo = new HxUserInfo();

            hxUserInfo.setNickName(userInfo.getNick_name());
            hxUserInfo.setUser_uuid(userInfo.getUser_id());
            hxUserInfo.setIm_id(userInfo.getIm_id());
            hxUserInfo.setIm_enabled(userInfo.getIm_enabled());


            return hxUserInfo;
        }

        return null;
    }

    /**
     * 更新用户信息
     *
     * @param items 网络获取环信ID结果
     */
    public void updateImId(List<HxUserInfo> items) {


        if (null == items || items.isEmpty()) {
            return;
        }

        synchronized (mDbLock) {

            for (HxUserInfo hxUserInfo : items) {
                ChatUserInfo info = chatUserInfoDao.load(hxUserInfo.getUser_uuid());
                if (info == null) {
                    info = new ChatUserInfo();

                }
                info.setUser_id(hxUserInfo.getUser_uuid());
                info.setIm_id(hxUserInfo.getIm_id());
                info.setIm_enabled(hxUserInfo.isIm_enabled());

                chatUserInfoDao.update(info);

            }
        }
    }


    /**
     * @return 群发消息 保存草稿箱
     */
    public List<GroupMsgDetail> getDrafts() {


        synchronized (mDbLock) {

            return DraftManage.getInstance().getDrafts();
        }


    }

    /**
     * @param detail 草稿箱信息
     */
    public void saveDraft(GroupMsgDetail detail) {

        if (detail == null) {
            return;
        }

        if (null == detail.getUuids() || detail.getUuids().isEmpty()) {
            return;
        }


        if (TextUtils.isEmpty(detail.getMsg())) {
            return;
        }


        synchronized (mDbLock) {

            DraftManage.getInstance().saveDraft(detail);
        }


    }

}
