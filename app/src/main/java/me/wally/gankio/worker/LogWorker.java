package me.wally.gankio.worker;

import android.content.Context;
import android.content.res.Configuration;

import com.apkfuns.logutils.LogUtils;

import me.wally.gankio.AppDelegate;

/**
 * Package: me.wally.gankio.worker
 * FileName: LogWorker
 * Date: on 2018/10/30  下午3:42
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class LogWorker implements AppDelegate.IWorker {

    @Override
    public void onCreate(Context context, Boolean isDebug) {
        LogUtils.getLogConfig().configAllowLog(isDebug).configShowBorders(false);
    }

    @Override
    public void onTerminate() {
        LogUtils.d("dispatchTerminate...");
    }

    @Override
    public void onExecute() {
        LogUtils.d("onExecute...");
    }

    @Override
    public Integer getId() {
        LogUtils.d("getId...");
        return 1;
    }

    @Override
    public void onLowMemory() {
        LogUtils.d("dispatchLowMemory...");
    }

    @Override
    public void onTrimMemory(int level) {
        LogUtils.d("dispatchTrimMemory...");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        LogUtils.d("dispatchConfigurationChanged...");
    }
}
