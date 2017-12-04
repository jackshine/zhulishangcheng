package com.example.ddm.appui.bean.dbbean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by WangYetong on 2017/7/18.
 * email : wytaper495@qq.com
 * mark:最近城市
 */
@Entity
public class RecentCity {
    @Id()
    private Long id;
    private String cityName;
    @Generated(hash = 1186393930)
    public RecentCity(Long id, String cityName) {
        this.id = id;
        this.cityName = cityName;
    }
    @Generated(hash = 885102994)
    public RecentCity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCityName() {
        return this.cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
