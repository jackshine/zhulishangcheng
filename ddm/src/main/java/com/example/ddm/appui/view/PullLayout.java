package com.example.ddm.appui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ScrollView;

import com.example.ddm.R;
import com.nineoldandroids.animation.ObjectAnimator;


public class PullLayout extends ScrollView {

    private View rl_top;
    private ObjectAnimator oa;
    private float lastY = -1;
    private float detalY = -1;
    private int range;
    private boolean isTouchOrRunning;
    private boolean isActionCancel;

    public PullLayout(Context context) {
        super(context);
    }

    public PullLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setVerticalScrollBarEnabled(false);
        rl_top = findViewById(R.id.rl_top);
        rl_top.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                rl_top.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                range = rl_top.getHeight();
                rl_top.getLayoutParams().height = range;
            }
        });

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isActionCancel = false;
                isTouchOrRunning = true;
                lastY = ev.getY();
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                isTouchOrRunning = true;
                if (getScrollY() != 0) {
                    detalY = 0;
                    //上拉滚动
                } else {
                	//下拉变大
                    detalY = ev.getY() - lastY;
                    if (detalY > 0) {
                        setT((int) -detalY / 5);
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                isTouchOrRunning = false;
                if (getScrollY() ==0 ) {
                        reset();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (t > range) {
            return;
        } 
    }
    
    /**
     * 滚动到下拉的位置
     * @param t
     */
    public void setT(int t) {
        scrollTo(0, t);
        if (t < 0) {
            animatePull(t);
        }
    }
    /**
     * 下拉动画
     * @param t
     */
    private void animatePull(int t) {
        rl_top.getLayoutParams().height = range - t;
        rl_top.requestLayout();
    }
    /**
     * 拉动下拉时恢复初始状态
     */
    private void reset() {
        if (oa != null && oa.isRunning()) {
            return;
        }
        oa = ObjectAnimator.ofInt(this, "t", (int) -detalY / 5, 0);
        oa.setDuration(150);
        oa.start();
    }
}
