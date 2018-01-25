package net.doyouhike.app.bbs.biz.db.provider;

import android.support.annotation.NonNull;

import net.doyouhike.app.bbs.biz.db.green.GreenDaoUtils;
import net.doyouhike.app.bbs.biz.db.green.entities.ChatDraft;
import net.doyouhike.app.bbs.biz.db.green.help.ChatDraftDao;
import net.doyouhike.app.bbs.biz.entity.im.GroupMsgDetail;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.UserInfoUtil;
import net.doyouhike.app.library.ui.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 群发草稿箱 基本操作 增删改查
 * Created by zengjiang on 16/8/16.
 */

public class DraftManage {



    static private DraftManage instance;
    private ChatDraftDao chatDraftDao;

    private DraftManage() {
        chatDraftDao = GreenDaoUtils.getSingleTon().getmDaoSession().getChatDraftDao();

    }

    public static synchronized DraftManage getInstance() {
        if (instance == null) {
            instance = new DraftManage();
        }
        return instance;
    }



    /**
     * 保存草稿列表
     *
     * @param detail 草稿信息
     */
    public void saveDraft(@NonNull GroupMsgDetail detail) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String uuid : detail.getUuids()) {
            stringBuilder.append(uuid).append(",");
        }


        String strUuIds = StringUtil.removeEnd(stringBuilder.toString(), ",");

        ChatDraft entity = new ChatDraft();

        entity.setMsg(detail.getMsg());
        entity.setRetry_count(0);
        entity.setSend_time(System.currentTimeMillis());
        entity.setSender_id(detail.getSenderId());
        entity.setUu_ids(strUuIds);

        chatDraftDao.insertOrReplace(entity);

    }

    /**
     * @return 获取发送失败列表 获取后清空数据库
     */
    public List<GroupMsgDetail> getDrafts() {

        List<GroupMsgDetail> details = new ArrayList<>();

        List<ChatDraft> entities = chatDraftDao.loadAll();
        for (ChatDraft item : entities) {
            //  群发超过了5次,不处理了
            if (item.getRetry_count() > 5) {
                continue;
            }

            //超过了今天,时间过期,不处理
            if (!DateUtils.isToday(item.getSend_time())) {
                continue;
            }
            //不是当前用户要群发的消息
            if (!UserInfoUtil.getInstance().isCurrentUser(item.getSender_id())) {
                continue;
            }

            GroupMsgDetail detail = new GroupMsgDetail();
            detail.setUuids(item.getUu_ids());
            detail.setMsg(item.getMsg());
            detail.setFirstSendTime(item.getSend_time());
            detail.setSenderId(item.getSender_id());
            detail.setRetryCount(item.getRetry_count());
            details.add(detail);
        }
        chatDraftDao.deleteAll();


        return details;
    }


}
