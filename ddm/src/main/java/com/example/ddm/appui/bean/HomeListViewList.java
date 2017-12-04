package com.example.ddm.appui.bean;

/**
 * Created by Bbacr on 2017/7/4.
 *
 */

public class HomeListViewList {
    private int mIcon;
    private String mShopName,mName,mPhone, mPlace;

    public HomeListViewList(int icon, String shopName, String name, String phone, String place) {
        mIcon = icon;
        mShopName = shopName;
        mName = name;
        mPhone = phone;
        mPlace = place;
    }

    public int getIcon() {
        return mIcon;
    }

    public void setIcon(int icon) {
        mIcon = icon;
    }

    public String getShopName() {
        return mShopName;
    }

    public void setShopName(String shopName) {
        mShopName = shopName;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getPlace() {
        return mPlace;
    }

    public void setPlace(String place) {
        mPlace = place;
    }
}
