package me.wally.gankio;

import android.app.Application;
import android.content.res.Configuration;

/**
 * Package: me.wally.gankio
 * FileName: UIApplication
 * Date: on 2018/10/30  下午3:19
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class UIApplication extends Application {
    private AppDelegate mAppDelegate;
    private static UIApplication mSelf;

    @Override
    public void onCreate() {
        super.onCreate();
        mSelf = this;
        Boolean isDebug = BuildConfig.DEBUG;
        mAppDelegate = new AppDelegate(this.getApplicationContext());
        mAppDelegate.dispatchCreate(getApplicationContext(), isDebug);
    }

    public AppDelegate getAppDelegate() {
        return mAppDelegate;
    }

    public static UIApplication shareInstance() {
        return mSelf;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mAppDelegate != null) {
            mAppDelegate.dispatchTerminate();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mAppDelegate != null) {
            mAppDelegate.dispatchLowMemory();
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (mAppDelegate != null) {
            mAppDelegate.dispatchTrimMemory(level);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mAppDelegate != null) {
            mAppDelegate.dispatchConfigurationChanged(newConfig);
        }
    }
}