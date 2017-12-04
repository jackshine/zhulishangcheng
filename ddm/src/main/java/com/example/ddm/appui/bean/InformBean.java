package com.example.ddm.appui.bean;

import java.util.List;

/**
 * Created by Bbacr on 2017/7/18.
 *通知
 */
public class InformBean {
    /**
     * msg : success
     * code : 1
     * datas : [{"imgUrl":"http://ddm-image.oss-cn-beijing.aliyuncs.com/tongzhi.png","CreateTime":"2017-07-17","Title":"微信支付宝支付，让支付更便捷","Id":12},{"imgUrl":"http://ddm-image.oss-cn-beijing.aliyuncs.com/banner1%20tongzhi.jpg","CreateTime":"2017-07-05","Title":"助利商城正式上线啦","Id":5},{"imgUrl":"http://ddm-image.oss-cn-beijing.aliyuncs.com/banner3ruzhu.jpg","CreateTime":"2017-07-05","Title":"安妮美甲入驻本商城","Id":4},{"imgUrl":"http://ddm-image.oss-cn-beijing.aliyuncs.com/banner3ruzhu.jpg","CreateTime":"2017-07-05","Title":"小罐茶入驻本商城","Id":3},{"imgUrl":"http://ddm-image.oss-cn-beijing.aliyuncs.com/banner3ruzhu.jpg","CreateTime":"2017-07-05","Title":"政七街苹果店入驻本商城","Id":2}]
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
         * imgUrl : http://ddm-image.oss-cn-beijing.aliyuncs.com/tongzhi.png
         * CreateTime : 2017-07-17
         * Title : 微信支付宝支付，让支付更便捷
         * Id : 12
         */

        private String imgUrl;
        private String CreateTime;
        private String Title;
        private int Id;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }
    }
}
