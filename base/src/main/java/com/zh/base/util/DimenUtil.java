package com.zh.base.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Package: me.wally.gankio.util
 * FileName: DimenUtil
 * Date: on 2018/10/31  下午3:57
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class DimenUtil {
    private DimenUtil() {
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    private int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    public static int spToPixel(Context context, float spValue) {
        final float fontScale = getDisplayMetrics(context).scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics displaymetrics = context.getResources().getDisplayMetrics();
        return displaymetrics;
    }
}
