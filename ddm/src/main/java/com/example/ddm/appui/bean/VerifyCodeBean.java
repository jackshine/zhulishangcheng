package com.example.ddm.appui.bean;

/**
 * Created by Zhouziwen .
 * 邮箱：1916973618@qq.com
 */

public class VerifyCodeBean {

    /**
     * msg : success
     * verifyCode : false
     * code : 1
     */

    private String msg;
    private boolean verifyCode;
    private int code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(boolean verifyCode) {
        this.verifyCode = verifyCode;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
