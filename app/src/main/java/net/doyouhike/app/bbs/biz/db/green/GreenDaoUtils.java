package net.doyouhike.app.bbs.biz.db.green;

import android.database.sqlite.SQLiteDatabase;

import net.doyouhike.app.bbs.base.application.MyApplication;
import net.doyouhike.app.bbs.biz.db.green.help.DaoMaster;
import net.doyouhike.app.bbs.biz.db.green.help.DaoSession;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.UserInfoUtil;

/**
 * 作者：luochangdong on 16/9/13
 * 描述：
 */
public class GreenDaoUtils {

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private static GreenDaoUtils greenDaoUtils;



    public static GreenDaoUtils getSingleTon() {
        if (greenDaoUtils == null) {
            greenDaoUtils = new GreenDaoUtils();
        }
        return greenDaoUtils;
    }

    private void initGreenDao() {
        String user_id = UserInfoUtil.getInstance().getUserId();
        if (StringUtil.isEmpty(user_id))
            user_id = "green";

        mHelper = new DaoMaster.DevOpenHelper(MyApplication.getInstance(), user_id, null);
        db = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    //第一次发布app不需要升级数据库正常使用DevOpenHelper获取daosession
    public DaoSession getmDaoSession() {
        if (mDaoMaster == null) {
            initGreenDao();
        }
        return mDaoSession;
    }
//
//    //再次app需要升级数据库时 获取daosession
//    public DaoSession getUpdateSession(Context context) {
//        DBHelper dbHelper = new DBHelper(context);
//        DaoMaster daoMaster = new DaoMaster(dbHelper.getWritableDb());
//        return daoMaster.newSession();
//    }

    public SQLiteDatabase getDb() {
        if (db == null) {
            initGreenDao();
        }
        return db;
    }

}
