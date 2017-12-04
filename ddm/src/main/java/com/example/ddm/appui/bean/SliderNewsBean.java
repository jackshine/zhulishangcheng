package com.example.ddm.appui.bean;

/**
 * Created by Zhouziwen .
 * 邮箱：1916973618@qq.com
 */

public class SliderNewsBean {

    /**
     * msg : success
     * code : 1
     * datas : {"ifShow":true,"message":"188****8899 3秒前在商城领取红包","type":"积分","value":"77","url":""}
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
         * ifShow : true
         * message : 188****8899 3秒前在商城领取红包
         * type : 积分
         * value : 77
         * url :
         */

        private boolean ifShow;
        private String message;
        private String type;
        private String value;
        private String url;

        public boolean isIfShow() {
            return ifShow;
        }

        public void setIfShow(boolean ifShow) {
            this.ifShow = ifShow;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
