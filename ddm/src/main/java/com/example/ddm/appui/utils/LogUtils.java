package com.example.ddm.appui.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by hongchuanwei .
 * on 2017/3/6
 */

public class LogUtils {
    /**
     * 方法说明:
     * @param  str  要输出的信息
     */
    public static void d(String str){
        if(!TextUtils.isEmpty(str)){
            Log.d("log","是Log===="+str);
        }
    }
}
