package com.example.ddm.appui.bean.eventbus;

/**
 * Created by Bbacr on 2017/7/21.
 *
 */

public class UpdateCardList {
    String msg;

    public UpdateCardList(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
