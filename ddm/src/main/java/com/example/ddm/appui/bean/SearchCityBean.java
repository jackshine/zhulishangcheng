package com.example.ddm.appui.bean;

import java.util.List;

/**
 * Created by Bbacr on 2017/8/1.
 */

public class SearchCityBean {

    /**
     * datas : [{"id":"102","index_name":"zhuLiGou1","city_name":"郑州市","province_name":"河南省","main_img":"http://image.ddmzl.com/8da56219b068453f85466047f1adda3420170720.png","area_name":"金水区","real_name":"刘磊","name":"苹果零售店--地震局","state":"1","shop_address":"经三路10号院附1号","linkman":"刘磊","shop_phone":"18638199955"},{"id":"92","index_name":"zhuLiGou1","city_name":"石家庄市","province_name":"河北省","main_img":"http://image.ddmzl.com/5fb4dd8450eb4ecc9d367de79ec4f96d20170719.png","area_name":"桥西区","real_name":"陈海月","name":"苹果零售店--解放广场","state":"1","shop_address":"轨道交通1号线解放广场(地铁站)","linkman":"陈海月","shop_phone":"15033115992"},{"id":"91","index_name":"zhuLiGou1","city_name":"石家庄市","province_name":"河北省","main_img":"http://image.ddmzl.com/0067798822d4412d830edc3ff994052e20170719.png","area_name":"长安区","real_name":"王彩霞","name":"苹果零售店--燕华大厦","state":"1","shop_address":"中山路与建设大街交叉口北行50米路西国美电器底商苹果专卖店","linkman":"王彩霞","shop_phone":"15032168991"},{"id":"90","index_name":"zhuLiGou1","city_name":"石家庄市","province_name":"河北省","main_img":"http://image.ddmzl.com/570944bfbe994449abbde160d2e4c99f20170719.png","area_name":"桥西区","real_name":"郝增周","name":"苹果零售店--纵横科技","state":"1","shop_address":"红旗大街469号","linkman":"郝增周","shop_phone":"18631140666"},{"id":"56","index_name":"zhuLiGou1","city_name":"郑州市","province_name":"河南省","main_img":"http://image.ddmzl.com/8a370a4d6d7c42f5826fe6300e22580f20170714.png","area_name":"金水区","real_name":"18530073771","name":"苹果零售店--晨旭路","state":"1","shop_address":"经三路晨旭路向南50米路西苹果专卖","linkman":"","shop_phone":"18530073771"},{"id":"55","index_name":"zhuLiGou1","city_name":"郑州市","province_name":"河南省","main_img":"http://image.ddmzl.com/83aa1990b1774f41aa84a36ee19482bf20170714.png","area_name":"金水区","real_name":"聂倩倩","name":"苹果零售店--经三路","state":"1","shop_address":"经三路晨旭路向南50米路西苹果专卖","linkman":"","shop_phone":"13676960370"},{"id":"41","index_name":"zhuLiGou1","city_name":"许昌市","province_name":"河南省","main_img":"http://image.ddmzl.com/c50cb7b355714c6db7cbf866e0cc027520170711.png","area_name":"长葛市","real_name":"朱春旭","name":"原一峰苹果体验店","state":"1","shop_address":"长社路(澳门大酒店西北)","linkman":"","shop_phone":"18637477888"},{"id":"36","index_name":"zhuLiGou1","city_name":"郑州市","province_name":"河南省","main_img":"http://image.ddmzl.com/dc5051b373534248a7be31b068102f8e20170710.png","area_name":"金水区","real_name":"来振友","name":"苹果零售店-文化路","state":"1","shop_address":"文化路68号创新大厦广场入口处","linkman":"","shop_phone":"13525525209"},{"id":"34","index_name":"zhuLiGou1","city_name":"郑州市","province_name":"河南省","main_img":"http://image.ddmzl.com/26bbda208375495f84e5fbf55fb0d07920170708.png","area_name":"管城回族区","real_name":"李慧娇","name":"苹果零售-航海路店","state":"1","shop_address":"航海路与未来路交叉口向北500米路西","linkman":"","shop_phone":"15093399338"},{"id":"33","index_name":"zhuLiGou1","city_name":"西安市","province_name":"陕西省","main_img":"http://image.ddmzl.com/3c64e7d36a6740cbaaffcd3f6f2c725620170708.png","area_name":"未央区","real_name":"陶丹","name":"苹果零售店--园湖曲","state":"1","shop_address":"未央宫街办园湖曲小区11幢1单元1层","linkman":"","shop_phone":"18691498719"},{"id":"32","index_name":"zhuLiGou1","city_name":"西安市","province_name":"陕西省","main_img":"http://image.ddmzl.com/1a0a0fae4d804bc68ade32ede6fcae8020170708.png","area_name":"未央区","real_name":"吴召雷","name":"苹果零售店--凤城六路","state":"1","shop_address":"凤城六路双威迎宾广场一层苹果专卖店","linkman":"","shop_phone":"18602906338"},{"id":"31","index_name":"zhuLiGou1","city_name":"洛阳市","province_name":"河南省","main_img":"http://image.ddmzl.com/1ad21226472846dfba0ac901da0f1ad620170708.png","area_name":"涧西区","real_name":"王利娟","name":"苹果零售店--涧西区","state":"1","shop_address":"世纪电脑城一楼C区15号A","linkman":"","shop_phone":"13837962170"},{"id":"29","index_name":"zhuLiGou1","city_name":"西安市","province_name":"陕西省","main_img":"http://image.ddmzl.com/23bab65469284f90878bb649c902a22520170708.png","area_name":"未央区","real_name":"柳家禄","name":"苹果零售店--凤城五路","state":"1","shop_address":"凤城五路紫薇希望城东区9号楼一层商铺","linkman":"","shop_phone":"13259966392"},{"id":"28","index_name":"zhuLiGou1","city_name":"西安市","province_name":"陕西省","main_img":"http://image.ddmzl.com/3d67a4661d8e47458f171a6c0328d7dd20170708.png","area_name":"雁塔区","real_name":"刘首果","name":"苹果零售店--科技路店","state":"1","shop_address":"雁塔区科技路209号(旺座国际城北)","linkman":"","shop_phone":"18133963011"},{"id":"27","index_name":"zhuLiGou1","city_name":"西安市","province_name":"陕西省","main_img":"http://image.ddmzl.com/63347d5fb60248e3b617c4d95c8d2db920170708.png","area_name":"未央区","real_name":"18591989999","name":"苹果零售店--太华路店","state":"1","shop_address":"太华北路228号(立德酒店一楼)苹果专卖店","linkman":"","shop_phone":"18629307609"},{"id":"26","index_name":"zhuLiGou1","city_name":"西安市","province_name":"陕西省","main_img":"http://image.ddmzl.com/194cb71943b948f7bf7d988c36f6915620170708.png","area_name":"莲湖区","real_name":"吴宁","name":"苹果零售店--西大街店","state":"1","shop_address":"西大街安定广场4号楼一层苹果专卖","linkman":"","shop_phone":"18509203168"},{"id":"25","index_name":"zhuLiGou1","city_name":"郑州市","province_name":"河南省","main_img":"http://image.ddmzl.com/a628c2a613134485acd16fe964b69b4920170708.png","area_name":"管城回族区","real_name":"杨浩","name":"苹果零售店--东大街店","state":"1","shop_address":"管城回族区东大街与紫金山150米路南 东大街80号 苹果体验店","linkman":"","shop_phone":"18625771989"},{"id":"21","index_name":"zhuLiGou1","city_name":"洛阳市","province_name":"河南省","main_img":"http://ddm-image.oss-cn-beijing.aliyuncs.com/91f8136e8c8b44e7b12742a2f53ab9b220170707.png","area_name":"西工区","real_name":"段非","name":"苹果零售-爱酷数码店","state":"1","shop_address":"王城公园西门对面赛易博数码广场2楼A207","linkman":"","shop_phone":"18638882211"},{"id":"19","index_name":"zhuLiGou1","city_name":"濮阳市","province_name":"河南省","main_img":"http://ddm-image.oss-cn-beijing.aliyuncs.com/9e7b3537066c414f8cd28022640d016d20170706.png","area_name":"高新区","real_name":"高庆","name":"苹果零售-百邦店","state":"1","shop_address":"黄河路振兴路口西南角 百邦苹果授权店","linkman":"","shop_phone":"17737868848"},{"id":"18","index_name":"zhuLiGou1","city_name":"商丘市","province_name":"河南省","main_img":"http://ddm-image.oss-cn-beijing.aliyuncs.com/344fefd7377146f3bfd15d5a65febe5b20170706.png","area_name":"永城市","real_name":"杜亚男","name":"苹果零售-天成科技店","state":"1","shop_address":"永兴街于雪峰路交叉口向西200米天成科技","linkman":"","shop_phone":"15303970739"}]
     * num : 20
     * pageNo : 1
     * code : 1
     * recordsTotal : 26
     * msg : success
     * totalPage : 1
     */

    private int num;
    private String pageNo;
    private String code;
    private int recordsTotal;
    private String msg;
    private int totalPage;
    private List<DatasBean> datas;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * id : 102
         * index_name : zhuLiGou1
         * city_name : 郑州市
         * province_name : 河南省
         * main_img : http://image.ddmzl.com/8da56219b068453f85466047f1adda3420170720.png
         * area_name : 金水区
         * real_name : 刘磊
         * name : 苹果零售店--地震局
         * state : 1
         * shop_address : 经三路10号院附1号
         * linkman : 刘磊
         * shop_phone : 18638199955
         */

        private String id;
        private String index_name;
        private String city_name;
        private String province_name;
        private String main_img;
        private String area_name;
        private String real_name;
        private String name;
        private String state;
        private String shop_address;
        private String linkman;
        private String shop_phone;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIndex_name() {
            return index_name;
        }

        public void setIndex_name(String index_name) {
            this.index_name = index_name;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getProvince_name() {
            return province_name;
        }

        public void setProvince_name(String province_name) {
            this.province_name = province_name;
        }

        public String getMain_img() {
            return main_img;
        }

        public void setMain_img(String main_img) {
            this.main_img = main_img;
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getShop_address() {
            return shop_address;
        }

        public void setShop_address(String shop_address) {
            this.shop_address = shop_address;
        }

        public String getLinkman() {
            return linkman;
        }

        public void setLinkman(String linkman) {
            this.linkman = linkman;
        }

        public String getShop_phone() {
            return shop_phone;
        }

        public void setShop_phone(String shop_phone) {
            this.shop_phone = shop_phone;
        }
    }
}
