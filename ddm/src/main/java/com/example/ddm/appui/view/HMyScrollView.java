package com.example.ddm.appui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by Zhouziwen .
 * 邮箱：1916973618@qq.com
 */

public class HMyScrollView extends ScrollView {
    private OnScrollListener onScrollListener;

    public HMyScrollView(Context context) {
        this(context, null);
    }

    public HMyScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HMyScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    /**
     *
     * @param onScrollListener
     */
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(onScrollListener != null){
            onScrollListener.onScroll(t);
        }
    }
    /**
     *
     *
     *
     * @author xiaanming
     *
     */
    public interface OnScrollListener{

        public void onScroll(int scrollY);
    }
}
