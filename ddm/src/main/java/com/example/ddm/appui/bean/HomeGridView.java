package com.example.ddm.appui.bean;

/**
 * Created by Bbacr on 2017/7/4.
 *
 */

public class HomeGridView {
    private int icon,mPic;
    private String text;

    public HomeGridView(int icon, int pic, String text) {
        this.icon = icon;
        mPic = pic;
        this.text = text;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getPic() {
        return mPic;
    }

    public void setPic(int pic) {
        mPic = pic;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
