package com.example.ddm.appui.mine;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.order.OrderFragment;

import java.util.ArrayList;
import java.util.List;
/**
 * A simple {@link Fragment} subclass.
 *
 */
public class MyOrderFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener{
    private RadioGroup mTabGroup;
    private ViewPager mViewPager;
    private List<BaseFragment> mFragments = new ArrayList<>();
    public MyOrderFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_order2, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mViewPager = (ViewPager) view.findViewById(R.id.friend_viewPager);
        mTabGroup = (RadioGroup) view.findViewById(R.id.friend_radio_group);
        mTabGroup.setOnCheckedChangeListener(this);
        onCheckedChanged(mTabGroup, R.id.friend_tab);
        mFragments.add(new OrderTopFragment());
        mFragments.add(new OrderFragment());
        mViewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager()));
    }
    @Override
    protected void setListener() {
        super.setListener();
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mTabGroup.check(R.id.friend_tab);
                        break;
                    case 1:
                        mTabGroup.check(R.id.friend_group_tab);
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    @Override
    protected void setData() {
        super.setData();
    }
    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.friend_tab:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.friend_group_tab:
                mViewPager.setCurrentItem(1);
                break;
        }
    }
    private class ViewPagerAdapter extends FragmentPagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
        @Override
        public int getCount() {
            return mFragments.size();
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }
    }
}
