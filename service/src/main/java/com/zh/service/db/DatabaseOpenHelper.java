package com.zh.service.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.github.yuweiguocn.library.greendao.MigrationHelper;

import org.greenrobot.greendao.database.Database;

import com.zh.service.db.gen.DaoMaster;
import com.zh.service.db.gen.GankCollectionEntityDao;

/**
 * Package: me.wally.gankio.db
 * FileName: DatabaseOpenHelper
 * Date: on 2018/11/2  下午7:22
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class DatabaseOpenHelper extends DaoMaster.OpenHelper {
    private static final String TAG = DatabaseOpenHelper.class.getSimpleName();

    public DatabaseOpenHelper(Context context, String name) {
        super(context, name);
    }

    public DatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        if (oldVersion == newVersion) {
            Log.i(TAG, "数据库无需升级");
            return;
        }
        Log.i(TAG, "数据库从" + oldVersion + "升级到 ::: " + newVersion + "版本");
        //使用GreenDaoUpgradeHelper辅助类进行升级
        MigrationHelper.migrate(db,
                GankCollectionEntityDao.class
        );
    }
}
