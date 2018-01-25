package net.doyouhike.app.bbs.biz.helper.im.privatemsg;

import java.util.Vector;

/**
 * 悄悄话发送工具
 * Created by zengjiang on 16/8/15.
 */

public class PrivateMsgSender {

    private static PrivateMsgSender instance;
    /**
     * 发送队列
     */
    private Vector<String> requestList = new Vector<>();

    public synchronized static PrivateMsgSender getInstance() {
        if (null == instance) {
            initInstance();
        }
        return instance;
    }

    private synchronized static void initInstance() {
        if (null == instance) {
            instance = new PrivateMsgSender();
        }
    }

    private PrivateMsgSender() {
    }

    /**
     * 发送悄悄话
     * @param receiverId 接受者ID
     */
    public synchronized void sendMsg(final String receiverId) {

        //是否在发送队列当中
        if (addList(receiverId)) {
            //执行发送
            new PrivateMsgEntity(receiverId, this).sendMsg();
        }


    }

    /**
     * 添加列表
     * @param receiverId 接受者ID
     * @return 是否添加成功
     */
    public boolean addList(String receiverId) {
        synchronized (requestList) {
            if (!requestList.contains(receiverId)) {
                requestList.add(receiverId);
                return true;
            }
        }
        return false;
    }

    /**
     * 移除请求列表
     * @param receiverId 接受者ID
     */
    public void removeList(String receiverId) {
        synchronized (requestList) {
            if (requestList.contains(receiverId)) {
                requestList.remove(receiverId);
            }
        }
    }

}
