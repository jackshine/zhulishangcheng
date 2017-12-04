package com.example.ddm.appui.mine.coupon;
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
 * 优惠券
 */
public class CouponFragment extends BaseFragment {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private List<String> mList = new ArrayList<>();
    private List<BaseFragment> mFragments=new ArrayList<>();
    public CouponFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_coupon, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mViewPager = mFindViewUtils.findViewById(R.id.viewpager);
        mTabLayout = mFindViewUtils.findViewById(R.id.tab_title);
        mList.add("满减优惠券");
        mList.add("大转盘优惠券");
        mFragments.add(new MoneyOffCouponFragment());
        mFragments.add(new AdventuresCouponFragment());
    }
    @Override
    protected void setListener() {
        super.setListener();
        mTabLayout.addTab(mTabLayout.newTab().setText(mList.get(0)));
        //初始化适配器
        FragAdapter adapter = new FragAdapter(getChildFragmentManager(), mFragments,mList);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);//让TabLayout随着ViewPager的变换而变换
    }
    @Override
    protected void setData() {
        super.setData();
    }
}
