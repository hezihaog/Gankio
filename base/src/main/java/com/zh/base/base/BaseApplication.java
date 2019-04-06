package com.zh.base.base;

import android.app.Application;

/**
 * <b>Package:</b> com.zh.base.base <br>
 * <b>Create Date:</b> 2019/4/6  4:33 PM <br>
 * <b>@author:</b> zihe <br>
 * <b>Description:</b>  <br>
 */
public class BaseApplication extends Application {
    private static BaseApplication mSelf;

    @Override
    public void onCreate() {
        super.onCreate();
        mSelf = this;
    }

    public static BaseApplication shareInstance() {
        return mSelf;
    }
}