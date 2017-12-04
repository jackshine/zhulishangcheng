package com.example.ddm.appui.bean;

/**
 * Created by Bbacr on 2017/7/17.
 *
 */

public class Address {

    /**
     * msg : success
     * code : 1
     * datas : {"address":{"countyId":1102,"id":1,"cityId":647,"detail":"南三环淮南街郑飞小区","userId":38,"provinceId":3212}}
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
         * address : {"countyId":1102,"id":1,"cityId":647,"detail":"南三环淮南街郑飞小区","userId":38,"provinceId":3212}
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
             * countyId : 1102
             * id : 1
             * cityId : 647
             * detail : 南三环淮南街郑飞小区
             * userId : 38
             * provinceId : 3212
             */

            private int countyId;
            private int id;
            private int cityId;
            private String detail;
            private int userId;
            private int provinceId;

            public int getCountyId() {
                return countyId;
            }

            public void setCountyId(int countyId) {
                this.countyId = countyId;
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
