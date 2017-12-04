package com.example.ddm.appui.bean;

import java.util.List;

/**
 * Created by Zhouziwen .
 * 邮箱：1916973618@qq.com
 */

public class NearbyBean {

    /**
     * count : 312
     * info : OK
     * infocode : 10000
     * status : 1
     */

    private String count;
    private String info;
    private String infocode;
    private int status;
    private List<DatasBean> datas;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfocode() {
        return infocode;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * _id : 1204
         * _location : 113.687978,34.795569
         * _name : 上海滩
         * _address : 东风路22号
         * id : 232
         * hat_city_id : 152
         * hat_area_id : 1366
         * hat_province_id : 16
         * main_img : http://image.ddmzl.com/792ed65572414a24a70533481999d80320170824.png
         * sales : 0
         * keyword : ccccc
         * state : 1
         * create_time : 2017-08-24 14:46:30
         * category_id : 105
         * linkman : 刘德华
         * is_delivery : 1
         * graded : 5
         * shop_phone : 18037673875
         * _createtime : 2017-09-06 15:17:48
         * _updatetime : 2017-09-06 15:22:46
         * _province : 河南省
         * _city : 郑州市
         * _district : 金水区
         * _distance : 122
         * _image : []
         */

        private String _id;
        private String _location;
        private String _name;
        private String _address;
        private int id;
        private String hat_city_id;
        private String hat_area_id;
        private String hat_province_id;
        private String main_img;
        private String sales;
        private String keyword;
        private String state;
        private String create_time;
        private String category_id;
        private String linkman;
        private String is_delivery;
        private String graded;
        private String shop_phone;
        private String _createtime;
        private String _updatetime;
        private String _province;
        private String _city;
        private String _district;
        private String _distance;
        private List<?> _image;
        public String get_id() {
            return _id;
        }
        public void set_id(String _id) {
            this._id = _id;
        }
        public String get_location() {
            return _location;
        }

        public void set_location(String _location) {
            this._location = _location;
        }

        public String get_name() {
            return _name;
        }

        public void set_name(String _name) {
            this._name = _name;
        }

        public String get_address() {
            return _address;
        }

        public void set_address(String _address) {
            this._address = _address;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getHat_city_id() {
            return hat_city_id;
        }

        public void setHat_city_id(String hat_city_id) {
            this.hat_city_id = hat_city_id;
        }

        public String getHat_area_id() {
            return hat_area_id;
        }

        public void setHat_area_id(String hat_area_id) {
            this.hat_area_id = hat_area_id;
        }

        public String getHat_province_id() {
            return hat_province_id;
        }

        public void setHat_province_id(String hat_province_id) {
            this.hat_province_id = hat_province_id;
        }

        public String getMain_img() {
            return main_img;
        }

        public void setMain_img(String main_img) {
            this.main_img = main_img;
        }

        public String getSales() {
            return sales;
        }

        public void setSales(String sales) {
            this.sales = sales;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getLinkman() {
            return linkman;
        }

        public void setLinkman(String linkman) {
            this.linkman = linkman;
        }

        public String getIs_delivery() {
            return is_delivery;
        }

        public void setIs_delivery(String is_delivery) {
            this.is_delivery = is_delivery;
        }

        public String getGraded() {
            return graded;
        }

        public void setGraded(String graded) {
            this.graded = graded;
        }

        public String getShop_phone() {
            return shop_phone;
        }

        public void setShop_phone(String shop_phone) {
            this.shop_phone = shop_phone;
        }

        public String get_createtime() {
            return _createtime;
        }

        public void set_createtime(String _createtime) {
            this._createtime = _createtime;
        }

        public String get_updatetime() {
            return _updatetime;
        }

        public void set_updatetime(String _updatetime) {
            this._updatetime = _updatetime;
        }

        public String get_province() {
            return _province;
        }

        public void set_province(String _province) {
            this._province = _province;
        }

        public String get_city() {
            return _city;
        }

        public void set_city(String _city) {
            this._city = _city;
        }

        public String get_district() {
            return _district;
        }

        public void set_district(String _district) {
            this._district = _district;
        }

        public String get_distance() {
            return _distance;
        }

        public void set_distance(String _distance) {
            this._distance = _distance;
        }

        public List<?> get_image() {
            return _image;
        }

        public void set_image(List<?> _image) {
            this._image = _image;
        }
    }
}
