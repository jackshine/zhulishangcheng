package com.example.ddm.appui.bean.picture;

import java.io.Serializable;

/**
 * Created by Bbacr on 2017/7/10.
 *
 */

public class Picture implements Serializable {
    /**
     * 图片路径
     */
    public String url;
    /**
     * 图片本身的宽 和 高,（清晰显示前的宽高），可以自己设定可以根据屏幕设定，可以根据图片大小设定
     */
    public float width;
    public float height;
}
