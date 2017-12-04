package com.example.ddm;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.ddm.application.PokonyanApplication;
import com.example.ddm.appui.BaseActivity;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.bean.eventbus.LoginSuccess;
import com.example.ddm.appui.bean.eventbus.UpdateCardList;
import com.example.ddm.appui.home.HomeFragment;
import com.example.ddm.appui.mine.MineFragment;
import com.example.ddm.appui.mine.MineTwoFragment;
import com.example.ddm.appui.order.OrderFragment;
import com.example.ddm.appui.order.ShoppingCarFragment;
import com.example.ddm.appui.summary.NearbyFragment;
import com.example.ddm.appui.summary.SummaryFragment;
import com.example.ddm.appui.utils.FindViewUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.manager.PreferenceManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
public class MainActivity extends BaseActivity implements View.OnClickListener{
    private HomeFragment mHomeFragment;
    private SummaryFragment mSummaryFragment;
    private NearbyFragment mNearbyFragment;
    private OrderFragment mOrderFragment;
    private ShoppingCarFragment mShoppingCarFragment;
    private MineFragment mMineFragment;
    private MineTwoFragment mMineTwoFragment;
    private List<BaseFragment> mFragmentList = new ArrayList<>();
    private FragmentManager mFragmentManager;
    public RadioButton mTabHome, mTabFind, mTabOrder, mTabMine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.activity_main, null);
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
        mTabOrder = mFindViewUtils.findViewById(R.id.radioBtn_find);
        mTabMine = mFindViewUtils.findViewById(R.id.radioBtn_mine);
        mHomeFragment = new HomeFragment();
        mFragmentList.add(mHomeFragment);
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.frame_layout, mHomeFragment);
        transaction.commit();
    }
    public  void receverEventPost() {
        //显示第二个fragmet
        mSummaryFragment = new SummaryFragment();
        mFragmentList.add(mSummaryFragment);
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        hideAllFragment(transaction);
        transaction.add(R.id.frame_layout, mSummaryFragment);
        transaction.show(mSummaryFragment);
        transaction.commit();
        mTabFind.setChecked(true);
    }
    @Override
    protected void setListener() {
        super.setListener();
        mTabHome.setOnClickListener(this);
        mTabFind.setOnClickListener(this);
        mTabOrder.setOnClickListener(this);
        mTabMine.setOnClickListener(this);
    }
    @Override
    protected void setData() {
        super.setData();
    }
    private void hideAllFragment(FragmentTransaction transaction) {
        transaction.hide(mHomeFragment);
        if (mSummaryFragment != null) {
            transaction.hide(mSummaryFragment);
        }
        if (mShoppingCarFragment != null) {
            transaction.hide(mShoppingCarFragment);
        }
        if (mMineTwoFragment != null) {
            transaction.hide(mMineTwoFragment);
        }
        mTabHome.setChecked(false);
        mTabFind.setChecked(false);
        mTabOrder.setChecked(false);
        mTabMine.setChecked(false);
    }
    public void clickTab(int checkId) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        hideAllFragment(transaction);
        //点击某个按钮的时候显示对应fragment
        switch (checkId) {
            case R.id.radioBtn_choiceness:
                transaction.show(mHomeFragment);
                mTabHome.setChecked(true);
                EventBus.getDefault().post(new LoginSuccess());
                break;
            case R.id.radioBtn_buy_car:
                if (mSummaryFragment == null) {
                    mSummaryFragment = new SummaryFragment();
                    transaction.add(R.id.frame_layout, mSummaryFragment);
                } else {
                    transaction.show(mSummaryFragment);
                }
                mTabFind.setChecked(true);
                break;
            case R.id.radioBtn_find:
                    if (mShoppingCarFragment == null) {
                        mShoppingCarFragment = new ShoppingCarFragment();
                        transaction.add(R.id.frame_layout, mShoppingCarFragment);
                    } else {
                        transaction.show(mShoppingCarFragment);
                    }
                mTabOrder.setChecked(true);
//                EventBus.getDefault().post(new LoginSuccess());
                break;
            case R.id.radioBtn_mine:

                    if (mMineTwoFragment == null) {
                        mMineTwoFragment = new MineTwoFragment();
                        transaction.add(R.id.frame_layout, mMineTwoFragment);
                    } else {
                        transaction.show(mMineTwoFragment);
                    }
                mTabMine.setChecked(true);
                EventBus.getDefault().post(new LoginSuccess());
                break;
        }
        transaction.commit();
    }
    @Override
    public void onClick(View v) {
        clickTab(v.getId());
    }

}
