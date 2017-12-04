package com.example.ddm.appui;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
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
import com.example.ddm.LocationActivity;
import com.example.ddm.LoginActivity;
import com.example.ddm.MainActivity;
import com.example.ddm.R;
import com.example.ddm.application.PokonyanApplication;
import com.example.ddm.appui.bean.eventbus.LoginSuccess;
import com.example.ddm.appui.utils.AppMainTabLoading;
import com.example.ddm.appui.utils.DisplayUtils;
import com.example.ddm.appui.utils.FindViewUtils;
import com.example.ddm.appui.utils.NetUtils;
import com.example.ddm.manager.PreferenceManager;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
/**
 * Created by zzw .
 * Emile:1916973618@qq.com
 */
public class BaseFragment extends Fragment implements View.OnClickListener,AMapLocationListener {
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private Context mContext;
    /**
     * 找控件的工具类
     */
    protected FindViewUtils mFindViewUtils;
    /**
     * 界面传值
     */
    protected int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636};
    protected BaseActivity mBaseActivity;
    protected Bundle mArguments;
    protected Handler mHandler = new Handler(Looper.getMainLooper());
    protected InputMethodManager inputMethodManager;
    double latitude;
    double longitude;
    @Subscribe
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        mArguments = getArguments();
        if (mArguments == null) {
            mArguments = new Bundle();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mBaseActivity = (BaseActivity) getActivity();
        view.setClickable(true);
        initView(view);
        setListener();
        setData();
        Net();
    }
    protected void initView(View view) {
        mFindViewUtils = new FindViewUtils(view);
        //在这里给继承BaseFragment的Fragment设置背景色
        int fragmentBg = getResources().getColor(R.color.gray_background);
        view.setBackgroundColor(fragmentBg);
        //点击穿透事件
        view.setClickable(true);
//        mAppTitleBar = (AppTitleBar) view.findViewById(R.id.app_title_bar);
    }
    protected void setListener() {
    }
    @Subscribe
    public void shuaxin(LoginSuccess loginSuccess){
        /*刷新数据和ui*/
        setData();
    }
    protected void setData() {
    }
    //===================loading 相关=====================
    //===================loading 相关=====================
    public void showLoading() {
        hiddenAppMainTabLoading();
        mBaseActivity.showLoading();
    }
    public void showLoading(int textResId) {
        hiddenAppMainTabLoading();
        mBaseActivity.showLoading(textResId);
    }
    public void showLoading(String textResId) {
        hiddenAppMainTabLoading();
        mBaseActivity.showLoading(textResId);
    }
    public void hiddenLoading() {
        mBaseActivity.hiddenLoading();
    }
    private AppMainTabLoading mMainTabLoading;
    public void showAppMainTabLoading(int textId) {
        hiddenLoading();
        if (mMainTabLoading == null) {
            mMainTabLoading = new AppMainTabLoading(mBaseActivity);
        }
        mMainTabLoading.open(textId);
    }
    public void hiddenAppMainTabLoading() {
        if (mMainTabLoading != null) {
            mMainTabLoading.close();
        }
    }
    /**点击返回键的监听
     * @return : false ，不处理返回事件， true 处理返回事件
     */
    public boolean onBackPressed() {
        return false;
    }
    /**
     * 关闭Framgment
     */
    public void popSelf() {
        popBackStack();
        hideSoftKeyboard();
    }
    public void popSelf(String tag) {
        try {
            if (isDetached() || isRemoving() || getFragmentManager() == null) {
                return;
            }
            if (isResumed()) {
                getFragmentManager().popBackStackImmediate(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
            hideSoftKeyboard();
        } catch (Exception e) {
        }
    }
    protected void hideSoftKeyboard() {
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
        }
    }
    private void popBackStack() {
        try {
            if (isDetached() || isRemoving() || getFragmentManager() == null) {
                return;
            }
            if (isResumed()) {
                getFragmentManager().popBackStackImmediate();
            }
        } catch (Exception e) {
        }
    }
    @Override
    public void onClick(View v) {
    }
    //判断网络状态
    protected boolean Net(){
        if (!NetUtils.isNetworkAvalible(getContext())) {
            NetUtils.checkNetwork(getActivity());
            return false;
        } else {
            return true;
        }
    }
    //判断是否登录
    protected boolean isLogin(){
        if (TextUtils.isEmpty(PreferenceManager.instance().getToken())) {
            mBaseActivity.showActivity(LoginActivity.class, null);
        }
        return true;
    }
    @Override
    public void onPause() {
        super.onPause();
    }
    @Subscribe
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null
                && aMapLocation.getErrorCode() == 0) {
            latitude = aMapLocation.getLatitude();
            longitude = aMapLocation.getLongitude();
        }
    }
    /**
     * @param bgAlpha 透明度的参数 0.5f代表半透明
     */
    protected void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        getActivity().getWindow().setAttributes(lp);
    }

    protected float pxTodp(float value){
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float valueDP= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value,metrics);
        return valueDP;
    }
}
