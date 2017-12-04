package com.example.ddm.appui.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.ddm.appui.widget.ImgTextView;

import java.util.List;

/**
 * Created by zzw .
 * Emile:1916973618@qq.com
 */
public class SliderAdapter extends PagerAdapter {
    private List<ImgTextView> mImgTextViews;
    private ISliderAdapter mISliderAdapter;
    public SliderAdapter(List<ImgTextView> imgTextViews,ISliderAdapter iSliderAdapter) {
        this.mImgTextViews = imgTextViews;
        mISliderAdapter = iSliderAdapter;

    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImgTextView view = mImgTextViews.get(position % mImgTextViews.size());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mISliderAdapter.onClickItem(position % mImgTextViews.size());
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


}
