package com.example.ddm.appui.mine;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.adapter.FragAdapter;

import java.util.ArrayList;
import java.util.List;
/**
 * A simple {@link Fragment} subclass.
 * 中奖记录
 */
public class AwardFragment extends BaseFragment {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private TextView mBack;
    private GetAwardFragment mGetAwardFragment;
    private NoneAwardFragment mNoneAwardFragment;
    private List<String> mList = new ArrayList<>();
    private List<BaseFragment> mFragments = new ArrayList<>();
    public AwardFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_award, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mBack = mFindViewUtils.findViewById(R.id.app_title_back);
        mTabLayout =mFindViewUtils.findViewById(R.id.tab_title);
        mViewPager =mFindViewUtils.findViewById(R.id.viewpager);
        mGetAwardFragment = new GetAwardFragment();
        mNoneAwardFragment = new NoneAwardFragment();
        mList.add("未领取");
        mList.add("已领取");
        mFragments.add(mNoneAwardFragment);
        mFragments.add(mGetAwardFragment);}
    @Override
    protected void setListener() {
        super.setListener();
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popSelf();
            }
        });
        mTabLayout.addTab(mTabLayout.newTab().setText(mList.get(0)));
        FragAdapter adapter = new FragAdapter(getChildFragmentManager(), mFragments,mList);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(1);
        mViewPager.setOffscreenPageLimit(1);
        mTabLayout.setupWithViewPager(mViewPager);//让TabLayout随着ViewPager的变换而变换
    }
    @Override
    protected void setData() {
        super.setData();
    }
}
