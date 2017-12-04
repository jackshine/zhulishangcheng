package com.example.ddm.appui.utils;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import com.example.ddm.application.PokonyanApplication;

/**
 * Created by hongchuanwei .
 * on 2017/2/27
 * 获取系统资源的类
 */

public class ResUtils {
    private static Resources getResource() {
        return PokonyanApplication.getInstance().getResources();
    }

    public static Drawable getDrawable(int id) {
        return getResource().getDrawable(id);
    }

    public static int getColor(int id) {
        return getResource().getColor(id);
    }

    public static String getString(int id) {
        if (id < 0) {
            return "";
        }
        return getResource().getString(id);
    }


    public static String[] getStringArray(int id) {
        return getResource().getStringArray(id);
    }
}
