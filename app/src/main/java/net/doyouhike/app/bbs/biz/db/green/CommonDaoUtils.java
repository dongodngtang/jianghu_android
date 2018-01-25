package net.doyouhike.app.bbs.biz.db.green;

import android.database.sqlite.SQLiteDatabase;

import net.doyouhike.app.bbs.base.application.MyApplication;
import net.doyouhike.app.bbs.biz.db.green.help.DaoMaster;
import net.doyouhike.app.bbs.biz.db.green.help.DaoSession;

/**
 * 作者：luochangdong on 16/9/20
 * 描述：
 */
public class CommonDaoUtils {
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private static CommonDaoUtils greenDaoUtils;

    private CommonDaoUtils() {
    }

    public static CommonDaoUtils getSingleTon() {
        if (greenDaoUtils == null) {
            greenDaoUtils = new CommonDaoUtils();
        }
        return greenDaoUtils;
    }

    private void initGreenDao() {

        mHelper = new DaoMaster.DevOpenHelper(MyApplication.getInstance(), "jianghu", null);
        db = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }


    public DaoSession getmDaoSession() {
        if (mDaoMaster == null) {
            initGreenDao();
        }
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        if (db == null) {
            initGreenDao();
        }
        return db;
    }
}
