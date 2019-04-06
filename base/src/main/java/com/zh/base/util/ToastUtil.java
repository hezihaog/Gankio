package com.zh.base.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Package: me.wally.gankio.util
 * FileName: ToastUtil
 * Date: on 2018/11/1  下午1:25
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class ToastUtil {
    private ToastUtil() {
    }

    public static void toast(Context context, String msg) {
        Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void toastLong(Context context, String msg) {
        Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
}
