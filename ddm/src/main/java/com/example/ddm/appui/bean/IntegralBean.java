package com.example.ddm.appui.bean;

/**
 * Created by Bbacr on 2017/8/4.
 * 积分数量
 */

public class IntegralBean {


    /**
     * msg : success
     * code : 1
     * datas : {"val":"14.97:money:0.21:0.20:0.22:0.24:0.32:0.18:0.21:0.21:0.22:0.25:0.26:0.23:0.25:0.23:0.22:0.26:0.22:0.21:0.18:0.17:0.18:0.19:0.28:0.20:0.17:0.20:0.20:0.21:0.21:0.20:0.19:0.28:0.18:0.26:0.22:0.23:0.19:0.22:0.19:0.22:0.20:0.21:0.15:0.19:0.19:0.22:0.21:0.22:0.21:0.21:0.20:0.22:0.27:0.24:0.22:0.20:0.19:0.21:0.21:0.25:0.20:0.26:0.22:0.26:0.18:0.24:0.21:0.25:0.22","avotor":"","integral":"14.97","share":false,"time":"2017-09-11","userName":"188****8899"}
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
         * val : 14.97:money:0.21:0.20:0.22:0.24:0.32:0.18:0.21:0.21:0.22:0.25:0.26:0.23:0.25:0.23:0.22:0.26:0.22:0.21:0.18:0.17:0.18:0.19:0.28:0.20:0.17:0.20:0.20:0.21:0.21:0.20:0.19:0.28:0.18:0.26:0.22:0.23:0.19:0.22:0.19:0.22:0.20:0.21:0.15:0.19:0.19:0.22:0.21:0.22:0.21:0.21:0.20:0.22:0.27:0.24:0.22:0.20:0.19:0.21:0.21:0.25:0.20:0.26:0.22:0.26:0.18:0.24:0.21:0.25:0.22
         * avotor :
         * integral : 14.97
         * share : false
         * time : 2017-09-11
         * userName : 188****8899
         */

        private String val;
        private String avotor;
        private String integral;
        private boolean share;
        private String time;
        private String userName;

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }

        public String getAvotor() {
            return avotor;
        }

        public void setAvotor(String avotor) {
            this.avotor = avotor;
        }

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

        public boolean isShare() {
            return share;
        }

        public void setShare(boolean share) {
            this.share = share;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
