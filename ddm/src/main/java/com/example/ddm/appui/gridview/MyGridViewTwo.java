package com.example.ddm.appui.gridview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

import com.example.ddm.appui.adapter.AdapterPopuoShopCarGridView;

import java.util.List;

/**
 * Created by Zhouziwen .
 * 邮箱：1916973618@qq.com
 */

public class MyGridViewTwo extends GridView {
    public List list;

    public AdapterPopuoShopCarGridView adapter;

    public int tag;

    public MyGridViewTwo(Context context) {
        super(context);
    }

    public MyGridViewTwo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGridViewTwo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
