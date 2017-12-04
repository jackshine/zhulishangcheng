package com.example.ddm.appui.bean;

import java.util.List;

/**
 * Created by Zhouziwen .
 * 邮箱：1916973618@qq.com
 */

public class GetZongJiangRecord {


    /**
     * msg : success
     * code : 1
     * datas : [{"obtainTime":"","img":"http://ddm-image.oss-cn-beijing.aliyuncs.com/dzp/4.png","code":"SAUD15C962277","jiangPinLevelValue":4,"createTime":"2017-09-28 15:32","jiangPinLevel":"四等奖","jiangPinName":"价值698充电宝一个","jiangPinCode":"JP299044","logisticsCode":"","obtainState":"未领取","id":1236,"obtainStateValue":1},{"obtainTime":"","img":"http://ddm-image.oss-cn-beijing.aliyuncs.com/dzp/2.png","code":"SAUD15C810754","jiangPinLevelValue":2,"createTime":"2017-09-28 15:30","jiangPinLevel":"二等奖","jiangPinName":"iwalk平衡车一台 ","jiangPinCode":"JP299042","logisticsCode":"","obtainState":"未领取","id":1231,"obtainStateValue":1},{"obtainTime":"","img":"http://ddm-image.oss-cn-beijing.aliyuncs.com/dzp/3.png","code":"SAUD15C249857","jiangPinLevelValue":3,"createTime":"2017-09-27 20:44","jiangPinLevel":"三等奖","jiangPinName":"1000元中石油 加油卡 ","jiangPinCode":"JP299043","logisticsCode":"","obtainState":"未领取","id":1225,"obtainStateValue":1},{"obtainTime":"","img":"http://ddm-image.oss-cn-beijing.aliyuncs.com/dzp/6.png","code":"SAUD15C787666","jiangPinLevelValue":6,"createTime":"2017-09-27 20:36","jiangPinLevel":"六等奖","jiangPinName":"200元 中石油加油卡  ","jiangPinCode":"JP299046","logisticsCode":"","obtainState":"未领取","id":1212,"obtainStateValue":1},{"obtainTime":"","img":"http://ddm-image.oss-cn-beijing.aliyuncs.com/dzp/4.png","code":"SAUD15C438522","jiangPinLevelValue":4,"createTime":"2017-09-27 20:30","jiangPinLevel":"四等奖","jiangPinName":"价值698充电宝一个","jiangPinCode":"JP299044","logisticsCode":"","obtainState":"未领取","id":1207,"obtainStateValue":1},{"obtainTime":"","img":"http://ddm-image.oss-cn-beijing.aliyuncs.com/dzp/5.png","code":"SAUD15C239753","jiangPinLevelValue":5,"createTime":"2017-09-26 17:47","jiangPinLevel":"五等奖","jiangPinName":"盖世小鸡 王者荣耀游戏手柄 ","jiangPinCode":"JP299045","logisticsCode":"","obtainState":"未领取","id":1191,"obtainStateValue":1},{"obtainTime":"","img":"http://ddm-image.oss-cn-beijing.aliyuncs.com/dzp/1.png","code":"SAUD15C101305","jiangPinLevelValue":1,"createTime":"2017-09-26 17:45","jiangPinLevel":"一等奖","jiangPinName":"iphone8 64G一台  ","jiangPinCode":"JP299041","logisticsCode":"","obtainState":"未领取","id":1182,"obtainStateValue":1},{"obtainTime":"","img":"http://ddm-image.oss-cn-beijing.aliyuncs.com/dzp/6.png","code":"SAUD15C069479","jiangPinLevelValue":6,"createTime":"2017-09-26 17:44","jiangPinLevel":"六等奖","jiangPinName":"200元 中石油加油卡  ","jiangPinCode":"JP299046","logisticsCode":"","obtainState":"未领取","id":1180,"obtainStateValue":1},{"obtainTime":"","img":"http://ddm-image.oss-cn-beijing.aliyuncs.com/dzp/1.png","code":"SAUD15C445588","jiangPinLevelValue":1,"createTime":"2017-09-26 16:27","jiangPinLevel":"一等奖","jiangPinName":"iphone8 64G一台  ","jiangPinCode":"JP299041","logisticsCode":"","obtainState":"未领取","id":1152,"obtainStateValue":1}]
     * totalPage : 1
     * recordsTotal : 9
     * pageNow : 1
     */

    private String msg;
    private int code;
    private int totalPage;
    private int recordsTotal;
    private int pageNow;
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

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getPageNow() {
        return pageNow;
    }

    public void setPageNow(int pageNow) {
        this.pageNow = pageNow;
    }

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }
    public static class DatasBean {
        /**
         * obtainTime :
         * img : http://ddm-image.oss-cn-beijing.aliyuncs.com/dzp/4.png
         * code : SAUD15C962277
         * jiangPinLevelValue : 4
         * createTime : 2017-09-28 15:32
         * jiangPinLevel : 四等奖
         * jiangPinName : 价值698充电宝一个
         * jiangPinCode : JP299044
         * logisticsCode :
         * obtainState : 未领取
         * id : 1236
         * obtainStateValue : 1
         */

        private String obtainTime;
        private String img;
        private String code;
        private int jiangPinLevelValue;
        private String createTime;
        private String jiangPinLevel;
        private String jiangPinName;
        private String jiangPinCode;
        private String logisticsCode;
        private String obtainState;
        private int id;
        private int obtainStateValue;

        public String getObtainTime() {
            return obtainTime;
        }

        public void setObtainTime(String obtainTime) {
            this.obtainTime = obtainTime;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getJiangPinLevelValue() {
            return jiangPinLevelValue;
        }

        public void setJiangPinLevelValue(int jiangPinLevelValue) {
            this.jiangPinLevelValue = jiangPinLevelValue;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getJiangPinLevel() {
            return jiangPinLevel;
        }

        public void setJiangPinLevel(String jiangPinLevel) {
            this.jiangPinLevel = jiangPinLevel;
        }

        public String getJiangPinName() {
            return jiangPinName;
        }

        public void setJiangPinName(String jiangPinName) {
            this.jiangPinName = jiangPinName;
        }

        public String getJiangPinCode() {
            return jiangPinCode;
        }

        public void setJiangPinCode(String jiangPinCode) {
            this.jiangPinCode = jiangPinCode;
        }

        public String getLogisticsCode() {
            return logisticsCode;
        }

        public void setLogisticsCode(String logisticsCode) {
            this.logisticsCode = logisticsCode;
        }

        public String getObtainState() {
            return obtainState;
        }

        public void setObtainState(String obtainState) {
            this.obtainState = obtainState;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getObtainStateValue() {
            return obtainStateValue;
        }

        public void setObtainStateValue(int obtainStateValue) {
            this.obtainStateValue = obtainStateValue;
        }
    }
}
