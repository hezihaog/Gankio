package me.wally.gankio.worker;

import android.content.Context;
import android.content.res.Configuration;

import me.wally.gankio.AppDelegate;
import me.wally.gankio.db.DatabaseManager;

/**
 * Package: me.wally.gankio.worker
 * FileName: DatabaseWorker
 * Date: on 2018/11/1  下午1:14
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class DatabaseWorker implements AppDelegate.IWorker {
    private DatabaseManager mDatabaseManager;
    private Context mContext;

    @Override
    public void onCreate(Context context, Boolean isDebug) {
        this.mContext = context.getApplicationContext();
        this.mDatabaseManager = DatabaseManager.shareInstance();
    }

    @Override
    public void onTerminate() {
    }

    @Override
    public void onExecute() {
        mDatabaseManager.initialize(mContext);
    }

    @Override
    public Integer getId() {
        return 2;
    }

    @Override
    public void onLowMemory() {

    }

    @Override
    public void onTrimMemory(int level) {

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

    }
}
