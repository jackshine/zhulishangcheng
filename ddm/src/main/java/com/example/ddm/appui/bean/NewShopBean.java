package com.example.ddm.appui.bean;

import java.util.List;

/**
 * Created by Bbacr on 2017/7/6.
 *最新商城
 */
public class NewShopBean {


    /**
     * msg : success
     * code : 1
     * datas : [{"isNewShop":0,"ShopName":"闺蜜","ShopAddress":"焦作市沁阳市沁阳市怀府中路玫瑰城购物中心","Phone":"18625877301","ID":145,"RealName":"郭金宽","MinImg":"http://image.ddmzl.com/400eee24dc884131a8593eeebd06342020170728.png"},{"isNewShop":0,"ShopName":"迪可","ShopAddress":"焦作市沁阳市沁阳市怀府中路","Phone":"13323896461","ID":144,"RealName":"赵甜","MinImg":"http://image.ddmzl.com/15af8fe9022c4660b23e83ef0187ed1820170728.png"},{"isNewShop":0,"ShopName":"首尔站","ShopAddress":"焦作市沁阳市沁阳市怀府中路","Phone":"15539126720","ID":143,"RealName":"梁敬佩","MinImg":"http://image.ddmzl.com/e82e0f1bdc434b0f875876341cda0efa20170728.png"},{"isNewShop":0,"ShopName":"bobo美甲","ShopAddress":"焦作市沁阳市沁阳市怀府中路","Phone":"13782883023","ID":142,"RealName":"闪波","MinImg":"http://image.ddmzl.com/47f86a024c354c49998cfb4911c7d79c20170728.png"},{"isNewShop":0,"ShopName":"GAGA","ShopAddress":"焦作市沁阳市沁阳市玫瑰城","Phone":"13903899408","ID":141,"RealName":"杨女士","MinImg":"http://image.ddmzl.com/a408f9c0d5ba44ec9a90f6fc5fb962e820170728.png"},{"isNewShop":0,"ShopName":"利平通讯","ShopAddress":"焦作市沁阳市百姓乐购生活购物广场(崇义店)","Phone":"13298463888","ID":140,"RealName":"杨超","MinImg":"http://image.ddmzl.com/18434c8108ea46d481340df54914222b20170727.png"},{"isNewShop":0,"ShopName":"梓铭淅实木沙发","ShopAddress":"郑州市惠济区中澳家具体验中心A1-57","Phone":"13011589609","ID":139,"RealName":"张生龙","MinImg":"http://image.ddmzl.com/bb0d58163670486a866ae81e5d19727f20170727.png"},{"isNewShop":0,"ShopName":"恒富盛家具有限公司","ShopAddress":"郑州市惠济区万福隆家具城展销中心二十二厅北门","Phone":"15981866150","ID":138,"RealName":"胡玉丽","MinImg":"http://image.ddmzl.com/f3260c3a2d084ef080a605a0a845d7e820170727.png"},{"isNewShop":0,"ShopName":"婴乐","ShopAddress":"郑州市惠济区京水社区（大河路南）","Phone":"13071007979","ID":137,"RealName":"马宁","MinImg":"http://image.ddmzl.com/908eb89e859049a2bf8cc3b287f227bc20170727.png"},{"isNewShop":0,"ShopName":"徐州松木批发","ShopAddress":"郑州市惠济区万客隆家具城22厅北门","Phone":"13526469815","ID":136,"RealName":"胡焕红","MinImg":"http://image.ddmzl.com/27ed4c946e5744fa94a2af4beba0255d20170727.png"},{"isNewShop":0,"ShopName":"安好--金玫瑰家私","ShopAddress":"郑州市惠济区万客隆家具城22厅北门","Phone":"15890088076","ID":135,"RealName":"安保勤","MinImg":"http://image.ddmzl.com/f02517b7ad9e47bbbbc7217ea82fede920170727.png"},{"isNewShop":0,"ShopName":"子都男士精致spa会所","ShopAddress":"郑州市金水区农业南路七里河南路西侧50米","Phone":"18738967665","ID":134,"RealName":"何蕊","MinImg":"http://ddm-image.oss-cn-beijing.aliyuncs.com/main.jpg"},{"isNewShop":0,"ShopName":"晟视眼镜","ShopAddress":"郑州市金水区天裕小区（东风渠滨河公园西170米）","Phone":"13460301838","ID":133,"RealName":"杨海光","MinImg":"http://image.ddmzl.com/767995247f9a4c65aac42eca66a29b1e20170723.png"},{"isNewShop":0,"ShopName":"晟视眼镜--园田花园店","ShopAddress":"郑州市金水区园田花园18号楼6号商铺附2号","Phone":"15324925774","ID":132,"RealName":"刘凤林","MinImg":"http://image.ddmzl.com/897d31e2f0ec41ad86e25f37baf9eeef20170723.png"},{"isNewShop":0,"ShopName":"晟视眼镜--城东路","ShopAddress":"郑州市二七区航海路与城东路东100米路北","Phone":"13586468934","ID":131,"RealName":"张惠","MinImg":"http://image.ddmzl.com/dde8315a83e34dd5856c549245390a2820170723.png"},{"isNewShop":0,"ShopName":"晟视眼镜--柳林路店","ShopAddress":"郑州市金水区三全路与柳林路口向西30米路北","Phone":"13783571687","ID":130,"RealName":"姜豪强","MinImg":"http://image.ddmzl.com/22bbf0739f8144d38a02da195e599e7020170723.png"},{"isNewShop":0,"ShopName":"晟视眼镜--渑池店","ShopAddress":"三门峡市渑池县汇金广场大张一楼","Phone":"15037933594","ID":129,"RealName":"贾斌斌","MinImg":"http://image.ddmzl.com/d1ed7e05ea594671bf4c1330a831253520170723.png"},{"isNewShop":0,"ShopName":"天艺摄影工作室","ShopAddress":"洛阳市宜阳县红旗中路大张对面锦城名都3号楼3单元10楼102室","Phone":"18739089869","ID":128,"RealName":"李冰新","MinImg":"http://image.ddmzl.com/e415c40d76714a57a0fb06c746dfb62720170723.png"},{"isNewShop":0,"ShopName":"阿诗丹顿-龙保水暖","ShopAddress":"焦作市武陟县大封镇中心大街","Phone":"15939166778","ID":127,"RealName":"孙佳新","MinImg":"http://image.ddmzl.com/9ed7a9e2eacc4dfaa8fc94bdc2e48ed920170723.png"},{"isNewShop":0,"ShopName":"晟视眼镜--文化路店","ShopAddress":"新乡市封丘县城关镇文化路宏盛商厦附近","Phone":"15837156999","ID":126,"RealName":"吕德生","MinImg":"http://image.ddmzl.com/1d406b9e5ca841d08576bca33fb8829020170722.png"}]
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
         * isNewShop : 0
         * ShopName : 闺蜜
         * ShopAddress : 焦作市沁阳市沁阳市怀府中路玫瑰城购物中心
         * Phone : 18625877301
         * ID : 145
         * RealName : 郭金宽
         * MinImg : http://image.ddmzl.com/400eee24dc884131a8593eeebd06342020170728.png
         */

        private int isNewShop;
        private String ShopName;
        private String ShopAddress;
        private String Phone;
        private int ID;
        private String RealName;
        private String MinImg;

        public int getIsNewShop() {
            return isNewShop;
        }

        public void setIsNewShop(int isNewShop) {
            this.isNewShop = isNewShop;
        }

        public String getShopName() {
            return ShopName;
        }

        public void setShopName(String ShopName) {
            this.ShopName = ShopName;
        }

        public String getShopAddress() {
            return ShopAddress;
        }

        public void setShopAddress(String ShopAddress) {
            this.ShopAddress = ShopAddress;
        }

        public String getPhone() {
            return Phone;
        }

        public void setPhone(String Phone) {
            this.Phone = Phone;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getRealName() {
            return RealName;
        }

        public void setRealName(String RealName) {
            this.RealName = RealName;
        }

        public String getMinImg() {
            return MinImg;
        }

        public void setMinImg(String MinImg) {
            this.MinImg = MinImg;
        }
    }
}
