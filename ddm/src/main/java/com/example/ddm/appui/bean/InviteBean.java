package com.example.ddm.appui.bean;

/**
 * Created by Zhouziwen .
 * 邮箱：1916973618@qq.com
 */

public class InviteBean {

    /**
     * msg : success
     * code : 1
     * datas : {"upperName":"樊飞龙","upperPhone":"18838998708"}
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
        /**
         * upperName : 樊飞龙
         * upperPhone : 18838998708
         */

        private String upperName;
        private String upperPhone;

        public String getUpperName() {
            return upperName;
        }

        public void setUpperName(String upperName) {
            this.upperName = upperName;
        }

        public String getUpperPhone() {
            return upperPhone;
        }

        public void setUpperPhone(String upperPhone) {
            this.upperPhone = upperPhone;
        }
    }
}
