package com.example.ddm.appui.bean.eventbus;

/**
 * Created by Bbacr on 2017/7/22.
 */

public class Location {
    String mLocation;

    public String getName() {
        return mLocation;
    }

    public void setName(String name) {
        mLocation = name;
    }

    public Location(String name) {

        mLocation = name;
    }
}
