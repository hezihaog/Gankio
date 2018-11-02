package me.wally.gankio.worker;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.alibaba.android.arouter.launcher.ARouter;

import me.wally.gankio.AppDelegate;

/**
 * Package: me.wally.gankio.worker
 * FileName: RouterWorker
 * Date: on 2018/11/1  下午3:44
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class RouterWorker implements AppDelegate.IWorker {
    @Override
    public void onCreate(Context context, Boolean isDebug) {
        if (isDebug) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init((Application) context.getApplicationContext());
    }

    @Override
    public void onTerminate() {
        ARouter.getInstance().destroy();
    }

    @Override
    public void onExecute() {
    }

    @Override
    public Integer getId() {
        return 3;
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
