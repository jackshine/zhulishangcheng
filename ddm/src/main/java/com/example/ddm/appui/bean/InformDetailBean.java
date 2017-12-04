package com.example.ddm.appui.bean;

/**
 * Created by Bbacr on 2017/7/18.
 *通知详情
 */

public class InformDetailBean {
    /**
     * msg : success
     * code : 1
     * datas : {"imgUrl":"http://ddm-image.oss-cn-beijing.aliyuncs.com/banner3ruzhu.jpg","Content":"政七街苹果店入驻助利商城，该店铺位于郑州市金水区东风路22号。店铺提供计算机系统集成，计算机的技术服务，计算机软件开发，技术咨询；批发兼零售：计算机及配件、电子产品、机械设备、通讯设备、仪器仪表等。店长联系电话：13838378247。欢迎大家前来参观！","CreateTime":"2017-07-05","Title":"政七街苹果店入驻本商城","Id":2}
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
         * imgUrl : http://ddm-image.oss-cn-beijing.aliyuncs.com/banner3ruzhu.jpg
         * Content : 政七街苹果店入驻助利商城，该店铺位于郑州市金水区东风路22号。店铺提供计算机系统集成，计算机的技术服务，计算机软件开发，技术咨询；批发兼零售：计算机及配件、电子产品、机械设备、通讯设备、仪器仪表等。店长联系电话：13838378247。欢迎大家前来参观！
         * CreateTime : 2017-07-05
         * Title : 政七街苹果店入驻本商城
         * Id : 2
         */

        private String imgUrl;
        private String Content;
        private String CreateTime;
        private String Title;
        private int Id;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String Content) {
            this.Content = Content;
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
