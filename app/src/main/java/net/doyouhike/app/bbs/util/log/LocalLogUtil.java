package net.doyouhike.app.bbs.util.log;


import org.apache.log4j.Logger;

/**
 * 功能：
 *
 * @author：曾江 日期：16-4-5.
 */
public class LocalLogUtil {

    static Logger mNetLog;
    static  Logger mNormalLog;
    /**
     * log打印开关
     */
    private static boolean DEBUG_MODEL =true;

    /**
     * 打印网络相关log,对应log_net.log文件
     * @param msgs log文本
     */
    public static void netLog_d(Object ...msgs){

        if (DEBUG_MODEL){
            return;
        }


        if (null==msgs){
            return;
        }



        StringBuilder stringBuffer=new StringBuilder();
        for (Object msg:msgs){
            stringBuffer.append(msg);
        }

        if (mNetLog==null){
            LocalLogConfiurator.configNormalLog();
            mNetLog = Logger.getLogger(LocalLogConfiurator.LOG_NET);
        }

        mNetLog.debug(stringBuffer.toString());
    }
    public static void normalLog_d(String ...msgs){

        if (DEBUG_MODEL){
            return;
        }

        if (null==msgs){
            return;
        }

        StringBuilder stringBuffer=new StringBuilder();
        for (String msg:msgs){
            stringBuffer.append(msg);
        }

        if (mNormalLog==null){
            LocalLogConfiurator.configNetLog();
            mNormalLog = Logger.getLogger(LocalLogConfiurator.LOG_NORMAL);
        }
        mNormalLog.debug(stringBuffer.toString());
    }

    public static boolean isDebugModel() {
        return DEBUG_MODEL;
    }

    public static void resetDebugModel() {
        DEBUG_MODEL = !DEBUG_MODEL;
    }

}
