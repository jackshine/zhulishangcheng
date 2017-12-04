package com.example.ddm.appui.bean;

import java.util.List;

/**
 * Created by WangYetong on 2017/7/25.
 * email : wytaper495@qq.com
 * mark:搜索提示
 */

public class ShopSuggest {

    /**
     * result : ["苹果专卖店","苹果零售店地震局","苹果零售店太华路店","苹果零售店政七街店","苹果零售航海路店","苹果零售天成科技店","苹果零售店东大街店","苹果零售店经三路","苹果零售店西大街店","苹果零售爱酷数码店"]
     * code : 1
     * msg : success
     */

    private int code;
    private String msg;
    private List<String> result;

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

    public List<String> getResult() {
        return result;
    }

    public void setResult(List<String> result) {
        this.result = result;
    }
}
