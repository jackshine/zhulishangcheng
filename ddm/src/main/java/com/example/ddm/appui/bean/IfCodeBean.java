package com.example.ddm.appui.bean;

/**
 * Created by Zhouziwen .
 * 邮箱：1916973618@qq.com
 */

public class IfCodeBean {

    /**
     * ifCode : false
     * msg : success
     * code : 1
     */

    private boolean ifCode;
    private String msg;
    private int code;

    public boolean isIfCode() {
        return ifCode;
    }

    public void setIfCode(boolean ifCode) {
        this.ifCode = ifCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
