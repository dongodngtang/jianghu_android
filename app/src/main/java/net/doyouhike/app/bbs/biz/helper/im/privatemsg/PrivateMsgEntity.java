package net.doyouhike.app.bbs.biz.helper.im.privatemsg;

import net.doyouhike.app.bbs.biz.db.dao.UserInfoDbUtil;
import net.doyouhike.app.bbs.biz.db.green.entities.ChatUserInfo;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.service.ApiReq;
import net.doyouhike.app.bbs.biz.openapi.request.users.ims.MessagePmsPost;
import net.doyouhike.app.bbs.util.UserInfoUtil;
import net.doyouhike.app.library.ui.utils.DateUtils;

/**
 * 悄悄话
 * Created by zengjiang on 16/8/15.
 */

public class PrivateMsgEntity implements IOnResponseListener{
    String receiverId;
    MessagePmsPost request;
    PrivateMsgSender sender;

    public PrivateMsgEntity(String receiverId,PrivateMsgSender sender) {
        this.receiverId = receiverId;
        this.sender=sender;
        request=new MessagePmsPost();

        request.setFrom_id(UserInfoUtil.getInstance().getUserId());
        request.setTo_id(receiverId);
        request.setBody("");
        request.setSubject("");

    }

    public void sendMsg(){


        ChatUserInfo userInfo= UserInfoDbUtil.getInstance().getUser(receiverId);

        if (null!=userInfo&&DateUtils.isToday(userInfo.getLastMsgTime())){
            //是否在二十四小时内发送过悄悄话
            sender.removeList(receiverId);
            return;
        }


        ApiReq.doPost(request,this);

    }


    @Override
    public void onSuccess(Response response) {
        //更新发送时间
        ChatUserInfo userInfo= UserInfoDbUtil.getInstance().getUser(receiverId);

        if (null!=userInfo){
            userInfo.setLastMsgTime(System.currentTimeMillis());
            UserInfoDbUtil.getInstance().saveUser(userInfo);
        }

        //移除发送列表
        sender.removeList(receiverId);
    }

    @Override
    public void onError(Response response) {
        sender.removeList(receiverId);
    }
}
