package com.example.ddm.appui.bean;

import com.example.ddm.appui.gridview.GridViewDataTwo;

import java.util.List;

/**
 * Created by WangYetong on 2017/7/24.
 * email : wytaper495@qq.com
 * mark:
 */

public class AllCity {
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

    public static class DatasBean{

        private List<DatasBeanTow> cityPostion;

        public List<DatasBeanTow> getCityPosition() {
            return cityPostion;
        }

        public void setCityPosition(List<DatasBeanTow> cityPosition) {
            this.cityPostion = cityPosition;
        }

        public static class DatasBeanTow{
            private String areaName;
            private int id;
            private String sort;

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

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }
        }
    }
}
