package com.example.ddm.appui.bean;

import java.util.List;

/**
 * Created by Bbacr on 2017/7/6.
 * 轮播图
 */

public class SlideBean {

    /**
     * datas : [{"id":6,"sort":6,"img":"http://ddm-image.oss-cn-beijing.aliyuncs.com/bannerhongbao.jpg","type":1,"url":"http://www.ddmzl.com/m/html/news-detail.html?id=16"},
     * {"id":5,"sort":5,"img":"http://ddm-image.oss-cn-beijing.aliyuncs.com/androidBanner750.jpg","type":1,"url":"http://www.ddmzl.com/m/html/news-detail.html?id=17"},{"id":4,"sort":4,"img":"http://ddm-image.oss-cn-beijing.aliyuncs.com/IOSbanner750.jpg","type":1,"url":"http://www.ddmzl.com/m/html/news-detail.html?id=16"},{"id":3,"sort":3,"img":"http://ddm-image.oss-cn-beijing.aliyuncs.com/bannerhongbao.jpg","type":1,"url":"http://www.ddmzl.com/m/html/news-detail.html?id=16"},{"id":2,"sort":2,"img":"http://ddm-image.oss-cn-beijing.aliyuncs.com/banner2.jpg","type":1,"url":"http://www.ddmzl.com/m/html/shop-apply.html"},{"id":1,"sort":1,"img":"http://ddm-image.oss-cn-beijing.aliyuncs.com/banner3.jpg","type":1,"url":"http://www.ddmzl.com/m/html/pattern.html"}]
     * code : 1
     * msg : success
     */

    private int code;
    private String msg;
    private List<DatasBean> datas;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * id : 6
         * sort : 6
         * img : http://ddm-image.oss-cn-beijing.aliyuncs.com/bannerhongbao.jpg
         * type : 1
         * url : http://www.ddmzl.com/m/html/news-detail.html?id=16
         */

        private int id;
        private int sort;
        private String img;
        private int type;
        private String url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
