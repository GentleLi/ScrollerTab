package com.ljt.scrollertab.utils;

import android.content.Context;
import android.view.WindowManager;

/**
 * Created by Duo Nuo on 2016/9/19.
 */
public class AppUtils {

    /**
     * 获取屏幕的宽度和高度
     *
     * @param context
     * @return
     */
    public static int[] getScreenWidthAndHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        int[] widthAndHeight = new int[2];
        widthAndHeight[0] = wm.getDefaultDisplay().getWidth();
        widthAndHeight[1] = wm.getDefaultDisplay().getHeight();
        return widthAndHeight;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
