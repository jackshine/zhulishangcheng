package com.example.ddm.appui.bean;

/**
 * Created by WangYetong on 2017/7/25.
 * email : wytaper495@qq.com
 * mark:热门搜索
 */

public class OpenSearch {
    private int code;
    private String msg;
    private String [] datas;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String[] getDatas() {
        return datas;
    }

    public void setDatas(String[] datas) {
        this.datas = datas;
    }
}
