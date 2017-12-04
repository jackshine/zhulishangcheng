package com.example.ddm.okthhp;

import okhttp3.OkHttpClient;


/**
 * Created by Hcw on 2016/12/26 0026.
 *
 */

public enum  NetClient {
    instance;
    public OkHttpClient mHttpClient;

    NetClient() {
        mHttpClient = new OkHttpClient();
    }
}
