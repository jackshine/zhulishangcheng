package com.example.ddm.appui.bean;

import java.util.List;

/**
 * Created by Bbacr on 2017/7/29.
 * 县
 */

public class CountyBean {

    /**
     * msg : success
     * code : 1
     * datas : [{"areaName":"丰镇市","id":407,"cityId":33,"hasUsed":false},{"areaName":"四子王旗","id":406,"cityId":33,"hasUsed":false},{"areaName":"察哈尔右翼后旗","id":405,"cityId":33,"hasUsed":false},{"areaName":"察哈尔右翼中旗","id":404,"cityId":33,"hasUsed":false},{"areaName":"察哈尔右翼前旗","id":403,"cityId":33,"hasUsed":false},{"areaName":"凉城县","id":402,"cityId":33,"hasUsed":false},{"areaName":"兴和县","id":401,"cityId":33,"hasUsed":false},{"areaName":"商都县","id":400,"cityId":33,"hasUsed":false},{"areaName":"化德县","id":399,"cityId":33,"hasUsed":false},{"areaName":"卓资县","id":398,"cityId":33,"hasUsed":false},{"areaName":"集宁区","id":397,"cityId":33,"hasUsed":false}]
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
         * areaName : 丰镇市
         * id : 407
         * cityId : 33
         * hasUsed : false
         */

        private String areaName;
        private int id;
        private int cityId;
        private boolean hasUsed;

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
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

        public boolean isHasUsed() {
            return hasUsed;
        }

        public void setHasUsed(boolean hasUsed) {
            this.hasUsed = hasUsed;
        }
    }
}
