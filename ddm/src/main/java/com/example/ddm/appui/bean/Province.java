package com.example.ddm.appui.bean;

import java.util.List;

/**
 * Created by Bbacr on 2017/7/22.
 * 省
 */
public class Province {
    /**
     * msg : success
     * code : 1
     * datas : [{"id":1,"provinceName":"北京市","hasUsed":false},{"id":2,"provinceName":"天津市","hasUsed":false},{"id":3,"provinceName":"河北省","hasUsed":false},{"id":4,"provinceName":"山西省","hasUsed":false},{"id":5,"provinceName":"内蒙古自治区","hasUsed":false},{"id":6,"provinceName":"辽宁省","hasUsed":false},{"id":7,"provinceName":"吉林省","hasUsed":false},{"id":8,"provinceName":"黑龙江省","hasUsed":false},{"id":9,"provinceName":"上海市","hasUsed":false},{"id":10,"provinceName":"江苏省","hasUsed":false},{"id":11,"provinceName":"浙江省","hasUsed":false},{"id":12,"provinceName":"安徽省","hasUsed":false},{"id":13,"provinceName":"福建省","hasUsed":false},{"id":14,"provinceName":"江西省","hasUsed":false},{"id":15,"provinceName":"山东省","hasUsed":false},{"id":16,"provinceName":"河南省","hasUsed":false},{"id":17,"provinceName":"湖北省","hasUsed":false},{"id":18,"provinceName":"湖南省","hasUsed":false},{"id":19,"provinceName":"广东省","hasUsed":false},{"id":20,"provinceName":"广西壮族自治区","hasUsed":false},{"id":21,"provinceName":"海南省","hasUsed":false},{"id":22,"provinceName":"重庆市","hasUsed":false},{"id":23,"provinceName":"四川省","hasUsed":false},{"id":24,"provinceName":"贵州省","hasUsed":false},{"id":25,"provinceName":"云南省","hasUsed":false},{"id":26,"provinceName":"西藏自治区","hasUsed":false},{"id":27,"provinceName":"陕西省","hasUsed":false},{"id":28,"provinceName":"甘肃省","hasUsed":false},{"id":29,"provinceName":"青海省","hasUsed":false},{"id":30,"provinceName":"宁夏回族自治区","hasUsed":false},{"id":31,"provinceName":"新疆维吾尔自治区","hasUsed":false},{"id":32,"provinceName":"香港特别行政区","hasUsed":false},{"id":33,"provinceName":"澳门特别行政区","hasUsed":false},{"id":34,"provinceName":"台湾省","hasUsed":false}]
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
         * id : 1
         * provinceName : 北京市
         * hasUsed : false
         */

        private int id;
        private String provinceName;
        private boolean hasUsed;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public boolean isHasUsed() {
            return hasUsed;
        }
        public void setHasUsed(boolean hasUsed) {
            this.hasUsed = hasUsed;
        }
    }
}
