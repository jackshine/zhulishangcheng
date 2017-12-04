package com.example.ddm.appui.bean.eventbus;

/**
 * Created by Bbacr on 2017/7/29.
 *
 */

public class AddressId {
    String provinceId;
    String cityId;
    String townId;

    public AddressId(String provinceId, String cityId, String townId) {
        this.provinceId = provinceId;
        this.cityId = cityId;
        this.townId = townId;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getTownId() {
        return townId;
    }

    public void setTownId(String townId) {
        this.townId = townId;
    }
}
