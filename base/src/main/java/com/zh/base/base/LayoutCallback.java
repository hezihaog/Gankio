package com.zh.base.base;

/**
 * Package: me.wally.gankio.base
 * FileName: LayoutCallback
 * Date: on 2018/10/31  上午11:45
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public interface LayoutCallback {
    void onLayoutBefore();

    int onLayoutId();

    void onLayoutAfter();
}
