package com.example.ddm.appui.bean;

/**
 * Created by Bbacr on 2017/7/6.
 *发送验证码
 */

public class SendCodeBean {

    /**
     * msg : success
     * code : 1
     * datas : {}
     */

    private String msg;
    private int code;
    private DatasBean datas;

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

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public static class DatasBean {

    }
}
