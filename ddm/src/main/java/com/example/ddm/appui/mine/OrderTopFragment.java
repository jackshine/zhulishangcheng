package com.example.ddm.appui.mine;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.adapter.FragAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * 线上做单（普通用户）
 */
public class OrderTopFragment extends BaseFragment {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<String> mTitles = new ArrayList<>();//标题集合
    private List<BaseFragment> mfragments = new ArrayList<>();//视图集合
    private MyOrderAllFragment mMyOrderAllFragment;
    private MyOrderWaitGoodsFragment mMyOrderWaitGoodsFragment;
    private MyOrderTakeGoodsFragment mMyOrderTakeGoodsFragment;
    private MyOrderWaitFragment mMyOrderWaitFragment;
    private MyOrderEvaluateFragment mMyOrderEvaluateFragment;
    private MyOrderCancelFragment mMyOrderCancelFragment;
    public OrderTopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_top, container, false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mMyOrderAllFragment = new MyOrderAllFragment();
        mMyOrderWaitGoodsFragment = new MyOrderWaitGoodsFragment();
        mMyOrderTakeGoodsFragment = new MyOrderTakeGoodsFragment();
        mMyOrderWaitFragment = new MyOrderWaitFragment();
        mMyOrderEvaluateFragment = new MyOrderEvaluateFragment();
        mMyOrderCancelFragment = new MyOrderCancelFragment();
        mfragments.add(mMyOrderAllFragment);
        mfragments.add(mMyOrderWaitGoodsFragment);
        mfragments.add(mMyOrderTakeGoodsFragment);
        mfragments.add(mMyOrderWaitFragment);
        mfragments.add(mMyOrderEvaluateFragment);
        mfragments.add(mMyOrderCancelFragment);
        mTitles.add("全部");
        mTitles.add("待发货");
        mTitles.add("待收货");
        mTitles.add("待做单");
        mTitles.add("待评价");
        mTitles.add("已取消");
        mTabLayout =  mFindViewUtils.findViewById(R.id.tab_title);
        mViewPager = mFindViewUtils.findViewById(R.id.viewpager);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitles.get(0)));
        //初始化适配器
        FragAdapter adapter = new FragAdapter(getChildFragmentManager(), mfragments,mTitles);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);//让TabLayout随着ViewPager的变换而变换
    }

    @Override
    protected void setData() {
        super.setData();
    }
}
