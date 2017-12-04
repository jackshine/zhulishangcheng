package com.example.ddm.appui.adapter.clickback;

/**
 * Created by WangYetong on 2017/7/25.
 * email : wytaper495@qq.com
 * mark:
 */

public interface ICityClickBack {

    /**
     * 当前定位城市
     */
    void onCityDetailClickBack(String city);

    /**
     * 最近访问城市
     */
    void onRecentCityClickBack(String recent);

    /**
     * 热门城市
     */
    void onHotCityClickBack(String hot);

    /**
     * 全部城市
     */
    void onAllCityClickBack(String all);
}
