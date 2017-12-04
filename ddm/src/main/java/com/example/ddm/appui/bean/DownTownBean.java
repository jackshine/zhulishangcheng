package com.example.ddm.appui.bean;

import java.util.List;

/**
 * Created by Bbacr on 2017/7/29.
 * 市接口
 */

public class DownTownBean {

    /**
     * msg : success
     * code : 1
     * datas : [{"zipCode":"016000","cityName":"阿拉善盟","id":36,"hasUsed":false,"provinceId":5},{"zipCode":"011100","cityName":"锡林郭勒盟","id":35,"hasUsed":false,"provinceId":5},{"zipCode":"137500","cityName":"兴安盟","id":34,"hasUsed":false,"provinceId":5},{"zipCode":"011800","cityName":"乌兰察布市","id":33,"hasUsed":false,"provinceId":5},{"zipCode":"014400","cityName":"巴彦淖尔市","id":32,"hasUsed":false,"provinceId":5},{"zipCode":"021000","cityName":"呼伦贝尔市","id":31,"hasUsed":false,"provinceId":5},{"zipCode":"010300","cityName":"鄂尔多斯市","id":30,"hasUsed":false,"provinceId":5},{"zipCode":"028000","cityName":"通辽市","id":29,"hasUsed":false,"provinceId":5},{"zipCode":"024000","cityName":"赤峰市","id":28,"hasUsed":false,"provinceId":5},{"zipCode":"016000","cityName":"乌海市","id":27,"hasUsed":false,"provinceId":5},{"zipCode":"014000","cityName":"包头市","id":26,"hasUsed":false,"provinceId":5},{"zipCode":"010000","cityName":"呼和浩特市","id":25,"hasUsed":false,"provinceId":5}]
     */

    private String msg;
    private int code;
    private List<DatasBean> datas;

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

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * zipCode : 016000
         * cityName : 阿拉善盟
         * id : 36
         * hasUsed : false
         * provinceId : 5
         */

        private String zipCode;
        private String cityName;
        private int id;
        private boolean hasUsed;
        private int provinceId;

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isHasUsed() {
            return hasUsed;
        }

        public void setHasUsed(boolean hasUsed) {
            this.hasUsed = hasUsed;
        }

        public int getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(int provinceId) {
            this.provinceId = provinceId;
        }
    }
}
