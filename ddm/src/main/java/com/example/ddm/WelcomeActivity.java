package com.example.ddm;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Bundle;
import android.widget.Toast;
import com.example.ddm.application.PokonyanApplication;
import com.example.ddm.appui.BaseActivity;
import com.example.ddm.appui.bean.VersionBean;
import com.example.ddm.appui.constant.FragmentTag;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.dialog.UpdateDialog;
import com.example.ddm.appui.mine.QqServiceFragment;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.ShowFragmentUtils;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;
import java.util.HashMap;
public class WelcomeActivity extends BaseActivity {
    boolean isFirstIn;
    private String mVersion;
    public static String sMallId = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getLocalVersion(this);
        PokonyanApplication.getInstance().addActivity(this);
        // 获取SharedPreferences对象
        SharedPreferences sp = WelcomeActivity.this.getSharedPreferences("SP", MODE_PRIVATE);
        isFirstIn = sp.getBoolean("isFirstIn", true);
        if (isFirstIn) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    goGuide();
                }
            }, 2000);
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("isFirstIn", false);
            editor.commit();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkVersion();
//                    goHome();
                }
            }, 2000);
        }
    }
    @Override
    protected void initView() {
        super.initView();
    }
    @Override
    protected void setListener() {
        super.setListener();
    }
    private void goHome() {
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        WelcomeActivity.this.startActivity(intent);
        WelcomeActivity.this.finish();
    }
    private void goGuide() {
        Intent intent = new Intent(WelcomeActivity.this, GuideViewPagerActivity.class);
        WelcomeActivity.this.startActivity(intent);
        WelcomeActivity.this.finish();
    }
    private void checkVersion(){
        HashMap<String, String> hashMap = new HashMap<>();
        HttpHelper.getInstance().post(Url.VERSION, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                LogUtils.d("ddddd"+Integer.valueOf(mVersion.replace(".","")));
                LogUtils.d("22222="+response);
                final VersionBean bean = JsonUtils.parse(response, VersionBean.class);
                LogUtils.d("ggggggggg="+bean.getDatas().getAndroidVersionCode().replace(".",""));
//                Integer.valueOf(mVersion.replace(".",""))
                if (bean.getCode()==1) {
                    if (Integer.valueOf(bean.getDatas().getAndroidVersionCode().replace(".",""))<=Integer.valueOf(mVersion.replace(".",""))) {
                                goHome();
                    } else {
                        UpdateDialog my = new UpdateDialog(WelcomeActivity.this, bean);
                        my.show();
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                LogUtils.d("gggggg"+error_msg);
                if (Net()) {
                    ShowFragmentUtils.showFragment(WelcomeActivity.this, QqServiceFragment.class, FragmentTag.QQSERVICEFRAGMENT, null, true);
                } else {
                    Toast.makeText(WelcomeActivity.this, "已与服务器断开 ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void getLocalVersion(Context context) {
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            mVersion = "0.0";
        }
        LogUtils.d("版本号"+info.versionName);
        mVersion = info.versionName;
    }
}
