package com.example.ddm.appui.bean;

/**
 * Created by Zhouziwen .
 * 邮箱：1916973618@qq.com
 */

public class PushBean {


    /**
     * data : {"code":"067856340208446","creatTime":"2017-08-15 17:34:46","money":"100","userNmae":"15738961968","userPhone":"15738961968","toUserPhone":"15528196521","toUserName":"阔咯哈","state":"已取消"}
     * type : transfer_out
     */

    private String data;
    private String type;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
