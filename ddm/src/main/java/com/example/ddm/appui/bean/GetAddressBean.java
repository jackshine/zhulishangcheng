package com.example.ddm.appui.bean;

/**
 * Created by Bbacr on 2017/7/22.
 * 得到地址
 */

public class GetAddressBean {

    /**
     * msg : success
     * code : 1
     * datas : {"address":{"province":"辽宁省","city":"阜新市","countyId":492,"county":"新邱区","id":9,"cityId":45,"detail":"562366","userName":"李周末","userId":15,"provinceId":6}}
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
         * address : {"province":"辽宁省","city":"阜新市","countyId":492,"county":"新邱区","id":9,"cityId":45,"detail":"562366","userName":"李周末","userId":15,"provinceId":6}
         */

        private AddressBean address;

        public AddressBean getAddress() {
            return address;
        }

        public void setAddress(AddressBean address) {
            this.address = address;
        }

        public static class AddressBean {
            /**
             * province : 辽宁省
             * city : 阜新市
             * countyId : 492
             * county : 新邱区
             * id : 9
             * cityId : 45
             * detail : 562366
             * userName : 李周末
             * userId : 15
             * provinceId : 6
             */

            private String province;
            private String city;
            private int countyId;
            private String county;
            private int id;
            private int cityId;
            private String detail;
            private String userName;
            private int userId;
            private int provinceId;

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
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

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public int getProvinceId() {
                return provinceId;
            }

            public void setProvinceId(int provinceId) {
                this.provinceId = provinceId;
            }
        }
    }
}
