package com.example.ddm.appui.mine.Charge;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.adapter.FragAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class PayFragment extends BaseFragment {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ImageView mImageView;
    private List<String> mTitles = new ArrayList<>();//标题集合
    private List<BaseFragment> mfragments = new ArrayList<>();//视图集合
    private ChargeFragment mChargeFragment;
    private WaitPayFragment mWaitPayFragment;
    public PayFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pay, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mImageView = mFindViewUtils.findViewById(R.id.pic);
        mWaitPayFragment = new WaitPayFragment();
        mChargeFragment = new ChargeFragment();
        mfragments.add(mChargeFragment);
        mfragments.add(mWaitPayFragment);
        mTitles.add("在线充值");
        mTitles.add("待支付");
        mTabLayout = mFindViewUtils.findViewById(R.id.tab_title);
        mViewPager = mFindViewUtils.findViewById(R.id.viewpager);
    }
    @Override
    protected void setListener() {
        super.setListener();
        //给TabLayout添加内容
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitles.get(0)));
        //初始化适配器
        FragAdapter adapter = new FragAdapter(getChildFragmentManager(), mfragments,mTitles);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);//让TabLayout随着ViewPager的变换而变换
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popSelf();
            }
        });
    }
}
