package com.example.ddm.appui.mine.share;


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
 *
 */
public class ShareFragment extends BaseFragment {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private List<String> mList = new ArrayList<>();
    private List<BaseFragment> mFragments=new ArrayList<>();
    public ShareFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_share, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mViewPager = mFindViewUtils.findViewById(R.id.viewpager);
        mTabLayout = mFindViewUtils.findViewById(R.id.tab_title);
        mList.add("分享团队");
        mList.add("分享流水");
        mFragments.add(new ShareTeamFragment());
        mFragments.add(new ShareWaterFragment());
    }
    @Override
    protected void setListener() {
        super.setListener();
        mTabLayout.addTab(mTabLayout.newTab().setText(mList.get(0)));
        //初始化适配器
        FragAdapter adapter = new FragAdapter(getChildFragmentManager(), mFragments,mList);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(1);
        mTabLayout.setupWithViewPager(mViewPager);//让TabLayout随着ViewPager的变换而变换
    }
    @Override
    protected void setData() {
        super.setData();
    }
}
