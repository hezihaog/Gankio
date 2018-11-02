package me.wally.gankio;

import android.content.Context;
import android.content.res.Configuration;

import java.util.HashMap;
import java.util.Map;

import me.wally.gankio.worker.DatabaseWorker;
import me.wally.gankio.worker.LogWorker;
import me.wally.gankio.worker.RouterWorker;

/**
 * Package: me.wally.gankio
 * FileName: AppDelegate
 * Date: on 2018/10/30  下午3:17
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class AppDelegate {
    private HashMap<Integer, IWorker> mWorkerList;
    private Context mContext;

    public AppDelegate(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public Context getContext() {
        return mContext;
    }

    private void addWorker(AppDelegate.IWorker worker) {
        mWorkerList.put(worker.getId(), worker);
    }

    public void dispatchCreate(Context context, Boolean isDebug) {
        mWorkerList = new HashMap<>(16);
        addWorker(new LogWorker());
        addWorker(new DatabaseWorker());
        addWorker(new RouterWorker());
        //这里添加自定义的Worker类
        for (Map.Entry<Integer, IWorker> entry : mWorkerList.entrySet()) {
            entry.getValue().onCreate(context, isDebug);
        }
        for (Map.Entry<Integer, IWorker> entry : mWorkerList.entrySet()) {
            entry.getValue().onExecute();
        }
    }

    public void dispatchTerminate() {
        for (Map.Entry<Integer, IWorker> entry : mWorkerList.entrySet()) {
            entry.getValue().onTerminate();
        }
    }

    public void dispatchLowMemory() {
        for (Map.Entry<Integer, IWorker> entry : mWorkerList.entrySet()) {
            entry.getValue().onLowMemory();
        }
    }

    public void dispatchTrimMemory(int level) {
        for (Map.Entry<Integer, IWorker> entry : mWorkerList.entrySet()) {
            entry.getValue().onTrimMemory(level);
        }
    }

    public void dispatchConfigurationChanged(Configuration newConfig) {
        for (Map.Entry<Integer, IWorker> entry : mWorkerList.entrySet()) {
            entry.getValue().onConfigurationChanged(newConfig);
        }
    }

    public interface IWorker {
        void onCreate(Context context, Boolean isDebug);

        void onTerminate();

        void onExecute();

        Integer getId();

        void onLowMemory();

        void onTrimMemory(int level);

        void onConfigurationChanged(Configuration newConfig);
    }
}