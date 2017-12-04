package com.example.ddm.onemoney.one;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.adapter.FragAdapter;
import com.example.ddm.appui.utils.BannerImageViewUtils;
import com.example.ddm.appui.viewpager.CycleViewPager;
import com.example.ddm.appui.widget.AppTitleBar;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * 一元夺宝1
 */
public class OneHomeFragment extends BaseFragment {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private List<String> mTitles = new ArrayList<>();//标题集合
    private List<BaseFragment> mfragments = new ArrayList<>();//视图集合
    private OneZoneFragment mOneZoneFragment;
    private TenZoneFragment mTenZoneFragment;
    private ViewFlipper mViewFlipper;
    private ImageView bannerImageView;
    private CycleViewPager mCycleViewPager;
    private List<ImageView> mImageViews = new ArrayList<>();
    public OneHomeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_one_home, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mOneZoneFragment = new OneZoneFragment();
        mTenZoneFragment = new TenZoneFragment();

        mViewFlipper = mFindViewUtils.findViewById(R.id.marquee_view);
        mCycleViewPager = (CycleViewPager) getChildFragmentManager().findFragmentById(R.id.school_fragment_banner);
    }
    @Override
    protected void setListener() {
        super.setListener();
        mfragments.add(mOneZoneFragment);
        mfragments.add(mTenZoneFragment);
        mTitles.add("一元专区");
        mTitles.add("十元专区");
        mTabLayout = mFindViewUtils.findViewById(R.id.tab_title);
        mViewPager = mFindViewUtils.findViewById(R.id.viewpager);
        //给TabLayout添加内容
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitles.get(0)));
        //初始化适配器
        FragAdapter adapter = new FragAdapter(getChildFragmentManager(), mfragments,mTitles);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);//让TabLayout随着ViewPager的变换而变换
        mViewFlipper.addView(View.inflate(getContext(), R.layout.noticelayout_three, null));
    }
    @Override
    protected void setData() {
        super.setData();
        bannerImageView = BannerImageViewUtils.getImageView(getContext());
        bannerImageView.setImageResource(R.drawable.banner_pager_1);
        mImageViews.add(bannerImageView);
        // 设置循环，在调用setData方法前调用
        mCycleViewPager.setCycle(false);
        // 在加载数据前设置是否循环
        mCycleViewPager.setData(mImageViews, null, null);
        //设置轮播
        mCycleViewPager.setWheel(false);
        mCycleViewPager.setTime(2000);
        //设置圆点指示图标组居中显示，默认靠右
        mCycleViewPager.setIndicatorCenter();
        mImageViews.clear();
    }
}
