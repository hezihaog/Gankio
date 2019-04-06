package me.wally.gankio.mvp.presenter.impl;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.apkfuns.logutils.LogUtils;
import com.zh.base.BuildConfig;
import com.zh.base.base.BaseApplication;
import com.zh.service.db.DatabaseManager;

/**
 * Package: me.wally.gankio
 * FileName: UIApplication
 * Date: on 2018/10/30  下午3:19
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class UIApplication extends BaseApplication {
    private static UIApplication mSelf;

    @Override
    public void onCreate() {
        super.onCreate();
        mSelf = this;
        boolean isDebug = BuildConfig.DEBUG;
        LogUtils.getLogConfig().configAllowLog(isDebug).configShowBorders(false);
        DatabaseManager.shareInstance().initialize(this);
        if (isDebug) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init((Application) this.getApplicationContext());
    }

    public static UIApplication shareInstance() {
        return mSelf;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouter.getInstance().destroy();
    }
}