package com.zh.base.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Package: me.wally.gankio.util
 * FileName: FileUtil
 * Date: on 2018/11/5  下午1:29
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class FileUtil {
    private FileUtil() {
    }

    public static String getAppRootDirPath(Context context) {
        String packageName = context.getApplicationContext().getPackageName();
        File appDir = new File(Environment.getExternalStorageDirectory(), packageName);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        return appDir.getAbsolutePath();
    }
}