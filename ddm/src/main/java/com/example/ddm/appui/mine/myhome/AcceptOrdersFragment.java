package com.example.ddm.appui.mine.myhome;
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
import com.example.ddm.appui.mine.merchant.DealFailFragment;
import com.example.ddm.appui.mine.merchant.DealSuccessFragment;
import java.util.ArrayList;
import java.util.List;
/**
 * A simple {@link Fragment} subclass.
 * 做单明细
 */
public class AcceptOrdersFragment extends BaseFragment {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ImageView mImageView;
    private List<String> mTitles = new ArrayList<>();//标题集合
    private List<BaseFragment> mfragments = new ArrayList<>();//视图集合
    private DealFailFragment mDealFailFragment;
    private DealSuccessFragment mDealSuccessFragment;
    public AcceptOrdersFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_accept_orders, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mImageView = mFindViewUtils.findViewById(R.id.pic);
        mDealFailFragment = new DealFailFragment();
        mDealSuccessFragment = new DealSuccessFragment();
        mfragments.add(mDealSuccessFragment);
        mfragments.add(mDealFailFragment);
        mTitles.add("交易成功");
        mTitles.add("交易失败");
        mTabLayout = mFindViewUtils.findViewById(R.id.tab_title);
        mViewPager = mFindViewUtils.findViewById(R.id.viewpager);
    }
    @Override
    protected void setListener() {
        super.setListener();
        //给TabLayout添加内容
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
    @Override
    protected void setData() {
        super.setData();
    }
}
