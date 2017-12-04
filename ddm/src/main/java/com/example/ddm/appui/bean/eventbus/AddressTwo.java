package com.example.ddm.appui.bean.eventbus;

/**
 * Created by Bbacr on 2017/7/29.
 *
 */

public class AddressTwo {
    String province;
    String city;
    String town;

    public AddressTwo(String province, String city, String town) {
        this.province = province;
        this.city = city;
        this.town = town;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
