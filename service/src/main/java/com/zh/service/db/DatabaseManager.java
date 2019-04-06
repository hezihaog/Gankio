package com.zh.service.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.zh.service.db.gen.DaoMaster;
import com.zh.service.db.gen.DaoSession;

/**
 * Package: me.wally.gankio.db
 * FileName: DatabaseManager
 * Date: on 2018/11/1  上午11:38
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class DatabaseManager {
    private static final String DB_NAME = "gank_db";
    private Context mContext;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private DatabaseManager() {
    }

    private static final class SingleHolder {
        private static final DatabaseManager INSTANCE = new DatabaseManager();
    }

    public static DatabaseManager shareInstance() {
        return SingleHolder.INSTANCE;
    }

    /**
     * 初始化
     */
    public void initialize(Context context) {
        this.mContext = context.getApplicationContext();
        DatabaseOpenHelper openHelper = new DatabaseOpenHelper(context, DB_NAME);
        SQLiteDatabase db = openHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public Context getContext() {
        return mContext;
    }

    public DaoMaster getDaoMaster() {
        return mDaoMaster;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}