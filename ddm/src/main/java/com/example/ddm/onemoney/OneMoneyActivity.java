package com.example.ddm.onemoney;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;

import com.example.ddm.R;
import com.example.ddm.application.PokonyanApplication;
import com.example.ddm.appui.BaseActivity;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.bean.eventbus.LoginSuccess;
import com.example.ddm.appui.mine.MineFragment;
import com.example.ddm.appui.order.OrderFragment;
import com.example.ddm.appui.summary.SummaryFragment;
import com.example.ddm.appui.utils.FindViewUtils;
import com.example.ddm.onemoney.one.OneHomeFragment;
import com.example.ddm.onemoney.three.ThreePersonFragment;
import com.example.ddm.onemoney.two.TwoOrderFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class OneMoneyActivity extends BaseActivity {
    private OneHomeFragment mOneHomeFragment;
    private TwoOrderFragment mTwoOrderFragment;
    private ThreePersonFragment mThreePersonFragment;
    private List<BaseFragment> mFragmentList = new ArrayList<>();
    private FragmentManager mFragmentManager;
    public RadioButton mTabHome, mTabFind,mTabMine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_one_money);
        View view = getLayoutInflater().inflate(R.layout.activity_one_money, null);
        //初始化找Id的工具类
        mFindViewUtils = new FindViewUtils(view);
        //        //防止底部菜单栏被软键盘顶上去
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(view);
        PokonyanApplication.getInstance().addActivity(this);
    }
    @Override
    protected void initView() {
        super.initView();
        mTabHome = mFindViewUtils.findViewById(R.id.radioBtn_choiceness);
        mTabFind = mFindViewUtils.findViewById(R.id.radioBtn_buy_car);
        mTabMine = mFindViewUtils.findViewById(R.id.radioBtn_mine);
        mOneHomeFragment = new OneHomeFragment();
        mFragmentList.add(mOneHomeFragment);
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.frame_layout, mOneHomeFragment);
        transaction.commit();
    }
    @Override
    protected void setListener() {
        super.setListener();
        mTabHome.setOnClickListener(this);
        mTabFind.setOnClickListener(this);
        mTabMine.setOnClickListener(this);
    }
    private void hideAllFragment(FragmentTransaction transaction) {
        transaction.hide(mOneHomeFragment);
        if (mTwoOrderFragment != null) {
            transaction.hide(mTwoOrderFragment);
        }
        if (mThreePersonFragment != null) {
            transaction.hide(mThreePersonFragment);
        }
        mTabHome.setChecked(false);
        mTabFind.setChecked(false);
        mTabMine.setChecked(false);
    }
    @Override
    protected void setData() {
        super.setData();
    }
    public void clickTab(int checkId) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        hideAllFragment(transaction);
        //点击某个按钮的时候显示对应fragment
        switch (checkId) {
            case R.id.radioBtn_choiceness:
                transaction.show(mOneHomeFragment);
                mTabHome.setChecked(true);
                break;
            case R.id.radioBtn_buy_car:
                if (mTwoOrderFragment == null) {
                    mTwoOrderFragment = new TwoOrderFragment();
                    transaction.add(R.id.frame_layout, mTwoOrderFragment);
                } else {
                    transaction.show(mTwoOrderFragment);
                }
                mTabFind.setChecked(true);
                break;
            case R.id.radioBtn_mine:
                    if (mThreePersonFragment == null) {
                        mThreePersonFragment = new ThreePersonFragment();
                        transaction.add(R.id.frame_layout, mThreePersonFragment);
                    } else {
                        transaction.show(mThreePersonFragment);
                    }
                mTabMine.setChecked(true);
                break;
        }
        transaction.commit();
    }
    @Override
    public void onClick(View v) {
        clickTab(v.getId());
    }
}
