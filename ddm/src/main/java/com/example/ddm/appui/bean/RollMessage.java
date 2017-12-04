package com.example.ddm.appui.bean;

import java.util.List;

/**
 * Created by Zhouziwen .
 * 邮箱：1916973618@qq.com
 */

public class RollMessage {

    /**
     * msg : success
     * code : 1
     * data : [{"phone":"李","jiangPinLevel":"八等奖","time":"5分钟前","message":"在商城抽到"},{"phone":"李","jiangPinLevel":"八等奖","time":"5分钟前","message":"在商城抽到"},{"phone":"李","jiangPinLevel":"八等奖","time":"5分钟前","message":"在商城抽到"},{"phone":"李","jiangPinLevel":"八等奖","time":"5分钟前","message":"在商城抽到"},{"phone":"李","jiangPinLevel":"八等奖","time":"5分钟前","message":"在商城抽到"},{"phone":"李","jiangPinLevel":"八等奖","time":"5分钟前","message":"在商城抽到"},{"phone":"李","jiangPinLevel":"八等奖","time":"5分钟前","message":"在商城抽到"},{"phone":"李","jiangPinLevel":"八等奖","time":"5分钟前","message":"在商城抽到"},{"phone":"李","jiangPinLevel":"八等奖","time":"5分钟前","message":"在商城抽到"},{"phone":"李","jiangPinLevel":"七等奖","time":"5分钟前","message":"在商城抽到"},{"phone":"樊飞龙","jiangPinLevel":"八等奖","time":"5分钟前","message":"在商城抽到"},{"phone":"132****7766","jiangPinLevel":"七等奖","time":"5分钟前","message":"在商城抽到"},{"phone":"130****8727","jiangPinLevel":"五等奖","time":"5分钟前","message":"在商城抽到"},{"phone":"136****0057","jiangPinLevel":"七等奖","time":"5分钟前","message":"在商城抽到"},{"phone":"137****3546","jiangPinLevel":"五等奖","time":"5分钟前","message":"在商城抽到"},{"phone":"135****2584","jiangPinLevel":"六等奖","time":"5分钟前","message":"在商城抽到"},{"phone":"136****4137","jiangPinLevel":"六等奖","time":"5分钟前","message":"在商城抽到"},{"phone":"137****4677","jiangPinLevel":"七等奖","time":"5分钟前","message":"在商城抽到"},{"phone":"132****3114","jiangPinLevel":"五等奖","time":"5分钟前","message":"在商城抽到"},{"phone":"130****4176","jiangPinLevel":"七等奖","time":"5分钟前","message":"在商城抽到"}]
     */

    private String msg;
    private int code;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * phone : 李
         * jiangPinLevel : 八等奖
         * time : 5分钟前
         * message : 在商城抽到
         */

        private String phone;
        private String jiangPinLevel;
        private String time;
        private String message;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getJiangPinLevel() {
            return jiangPinLevel;
        }

        public void setJiangPinLevel(String jiangPinLevel) {
            this.jiangPinLevel = jiangPinLevel;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
