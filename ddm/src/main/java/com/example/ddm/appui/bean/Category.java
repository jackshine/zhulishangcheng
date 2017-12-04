package com.example.ddm.appui.bean;

import java.util.List;

/**
 * Created by WangYetong on 2017/7/25.
 * email : wytaper495@qq.com
 * mark:首页分类
 */

public class Category {
    private String msg;
    private int code;
    private List<DataBeans> datas;

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

    public List<DataBeans> getDatas() {
        return datas;
    }

    public void setDatas(List<DataBeans> datas) {
        this.datas = datas;
    }

    public static class DataBeans{
        private String Img;
        private int ID;
        private DataBeansInner CategoryState;
        private String Name;

        public String getImg() {
            return Img;
        }

        public void setImg(String img) {
            Img = img;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public DataBeansInner getCategoryState() {
            return CategoryState;
        }

        public void setCategoryState(DataBeansInner categoryState) {
            CategoryState = categoryState;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public static class DataBeansInner{
            private int initValue;

            public int getInitValue() {
                return initValue;
            }

            public void setInitValue(int initValue) {
                this.initValue = initValue;
            }
        }
    }

}
