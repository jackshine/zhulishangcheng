package com.example.ddm.appui.home;

import android.util.Log;

import com.example.ddm.appui.constant.Url;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by WangYetong on 2017/7/24.
 * email : wytaper495@qq.com
 * mark:
 */

public class CommonMethod {
    /**
     * 添加用户访问城市
     */

    public static void addRecentCity(final String cityName) {
        Map<String, String> param = new HashMap<>();
        param.put("token", PreferenceManager.instance().getToken());
        param.put("city", cityName);
        HttpHelper.getInstance().post(Url.ADD_RECENT, param, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.d("wyt", "添加用户访问城市成功：" + cityName + response);
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                Log.d("wyt", "添加用户访问城市失败：" + error_msg);
            }
        });
    }

}
