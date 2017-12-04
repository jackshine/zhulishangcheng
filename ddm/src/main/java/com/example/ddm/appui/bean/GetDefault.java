package com.example.ddm.appui.bean;

/**
 * Created by Zhouziwen .
 * 邮箱：1916973618@qq.com
 */

public class GetDefault {

    /**
     * msg : success
     * code : 1
     * datas : {"ifDefault":true,"province":"河南省","phone":"18838998708","city":"郑州市","countyId":1366,"name":"kd","county":"金水区","id":74,"cityId":152,"detail":"dongfenlu35","provinceId":16}
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
         * ifDefault : true
         * province : 河南省
         * phone : 18838998708
         * city : 郑州市
         * countyId : 1366
         * name : kd
         * county : 金水区
         * id : 74
         * cityId : 152
         * detail : dongfenlu35
         * provinceId : 16
         */

        private boolean ifDefault;
        private String province;
        private String phone;
        private String city;
        private int countyId;
        private String name;
        private String county;
        private int id;
        private int cityId;
        private String detail;
        private int provinceId;

        public boolean isIfDefault() {
            return ifDefault;
        }

        public void setIfDefault(boolean ifDefault) {
            this.ifDefault = ifDefault;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getCountyId() {
            return countyId;
        }

        public void setCountyId(int countyId) {
            this.countyId = countyId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public int getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(int provinceId) {
            this.provinceId = provinceId;
        }
    }
}
