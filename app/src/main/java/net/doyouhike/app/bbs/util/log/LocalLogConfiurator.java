package net.doyouhike.app.bbs.util.log;

import android.os.Environment;

import net.doyouhike.app.bbs.base.application.MyApplication;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

import java.io.File;
import java.io.IOException;

/**
 * 功能：SD卡日志配置
 *
 * @author：曾江 日期：16-3-30.
 */
public class LocalLogConfiurator {


    public static final String LOG_NORMAL = "log_normal";
    public static final String LOG_NET = "log_net";

    /**
     * log文件输出格式
     */
    private final static String LOG_PATTERN="%d %-5p [%C{2}]-[%M-%L] %m%n";
    /**
     * log文件数量
     */
    private static final int MAX_BACKUP_INDEX = 5;
    /**
     * 每个log文件的大小512kb
     */
    private static final long MAXIMUM_FILE_SIZE = 512 * 1024;
    /**
     * .doyouhike.app.bbs目录下的路径名称
     */
    private static final String DIRETORY_NAME = "log";


    /**
     * log配置
     */
    public static void config(){
        configNetLog();
        configNormalLog();
    }

    /**
     * 配置网络打印的Log
     */
    public static void configNetLog(){
        configLog(LOG_NET);
    }

    /**
     * 配置日常log
     */
    public static void configNormalLog(){
        configLog(LOG_NORMAL);
    }

    /**
     * @param logName log的名字
     */
    private static void configLog(String logName){

       Logger logger=Logger.getLogger(logName);

        PatternLayout fileLayout = new PatternLayout(LOG_PATTERN);
        RollingFileAppender rollingFileAppender;
        try {
            rollingFileAppender = new RollingFileAppender(fileLayout, getLogFileName(logName));
        } catch (IOException var5) {
            throw new RuntimeException("Exception configuring log system", var5);
        }
        //滚动文件数量
        rollingFileAppender.setMaxBackupIndex(MAX_BACKUP_INDEX);
        //文件大小
        rollingFileAppender.setMaximumFileSize(MAXIMUM_FILE_SIZE);
        rollingFileAppender.setImmediateFlush(true);
        logger.addAppender(rollingFileAppender);
        //只有级别大于或等于level的日志记录消息才会得到处理。所有其他的消息都将被忽略。
        // 默认的级别是logging.NOTSET, 表示处理所有的日志消息。
        logger.setLevel(Level.DEBUG);
    }


    /**
     * @param subPath log文件的下路径
     * @return
     */
    private static String getLogFileName(String subPath){

        String parentPath;
//
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            parentPath = Environment.getExternalStorageDirectory().getPath()+
                    MyApplication.BASE_FILE_PATH;
        }else {
            parentPath=MyApplication.getInstance().getCacheDir().toString();
        }

        return parentPath+ File.separator+DIRETORY_NAME + File.separator+
                subPath + File.separator
                +subPath+".log";

    }
}
