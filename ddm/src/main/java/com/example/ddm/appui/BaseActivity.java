package com.example.ddm.appui;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.example.ddm.LocationActivity;
import com.example.ddm.MainActivity;
import com.example.ddm.R;
import com.example.ddm.application.PokonyanApplication;
import com.example.ddm.appui.utils.AppLoading;
import com.example.ddm.appui.utils.DisplayUtils;
import com.example.ddm.appui.utils.FindViewUtils;
import com.example.ddm.appui.utils.NetUtils;
import com.example.ddm.manager.PreferenceManager;


public class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    protected FindViewUtils mFindViewUtils;
    private Context mContext;
    protected InputMethodManager mInputMethodManager;
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        //处理 息屏 之后不再重新加载
        View view = getLayoutInflater().inflate(layoutResID, null);
        setContentView(view);
    }
    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        initView();
        setListener();
        setData();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }
    /**
     * 初始化界面
     */
    protected void initView() {
    }
    /**
     * 设置监听
     */
    protected void setListener() {
    }
    /**
     * 加载数据
     */
    protected void setData() {
    }
    //判断网络状态
    protected boolean Net(){
        if (!NetUtils.isNetworkAvalible(this)) {
            NetUtils.checkNetwork(this);
            return false;
        } else {
            return true;
        }
    }
    /**
     * @param activity : 将要被显示的Activity
     * @param data     ： extraData
     */
    public void showActivity(Class<? extends BaseActivity> activity, Bundle data) {
        Intent intent = new Intent(this, activity);
        if (data != null) {
            intent.putExtras(data);
        }
        startActivity(intent);
    }
    @Override
    public void onClick(View v) {
    }
    public Fragment findFragmentByTag(String tag) {
        return getSupportFragmentManager().findFragmentByTag(tag);
    }
    public void hideSoftKeyboard() {
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN && getCurrentFocus() != null) {
            mInputMethodManager.hideSoftInputFromWindow(
                    getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    //=======================loading
    private AppLoading mAppLoading;
    public void showLoading() {
        if (mAppLoading == null) {
            mAppLoading = new AppLoading(this);
        }
        mAppLoading.open();
    }
    public void showLoading(int textResId) {
        if (mAppLoading == null) {
            mAppLoading = new AppLoading(this);
        }
        mAppLoading.open(textResId);
    }public void showLoading(String textResId) {
        if (mAppLoading == null) {
            mAppLoading = new AppLoading(this);
        }
        mAppLoading.open(textResId);
    }
    public void hiddenLoading() {
        if (mAppLoading != null) {
            mAppLoading.close();
        }
    }
}
