package me.wally.gankio.worker;

import android.content.Context;
import android.content.res.Configuration;

import com.apkfuns.logutils.LogUtils;
import com.tencent.smtt.sdk.QbSdk;

import me.wally.gankio.AppDelegate;

public class X5WebWorker implements AppDelegate.IWorker {
    @Override
    public void onCreate(Context context, Boolean isDebug) {
        QbSdk.PreInitCallback callback = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean result) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                LogUtils.d(" onViewInitFinished: " + result);
            }

            @Override
            public void onCoreInitFinished() {
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(context.getApplicationContext(), callback);
    }

    @Override
    public void onTerminate() {

    }

    @Override
    public void onExecute() {

    }

    @Override
    public Integer getId() {
        return 4;
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