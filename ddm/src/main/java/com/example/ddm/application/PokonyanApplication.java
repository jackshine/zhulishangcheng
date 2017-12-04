package com.example.ddm.application;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Vibrator;
import com.example.ddm.appui.utils.DisplayUtils;
import com.example.ddm.manager.PreferenceManager;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.common.QueuedWork;

import java.util.LinkedList;
import java.util.List;
import cn.jpush.android.api.JPushInterface;
/**
 * Created by zzw .
 * Emile:1916973618@qq.com
 */
public class PokonyanApplication extends Application {
    private List<Activity> mList = new LinkedList<Activity>();
    private static PokonyanApplication mInstance;
    private boolean mIsTokenExpired = false;
    public Vibrator mVibrator;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        DisplayUtils.init();
        PreferenceManager.instance().init(this);
        Config.DEBUG = true;
        QueuedWork.isUseThreadPool = false;
        UMShareAPI.get(this);
        Fresco.initialize(this);
    }
    public static PokonyanApplication getInstance(){
        return mInstance;
    }
    // add Activity
    public void addActivity(Activity activity) {
        mList.add(activity);
    }
    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
    @Override
    protected void attachBaseContext(Context base){
        super.attachBaseContext(base);
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }
    public boolean isTokenExpired(){
        return mIsTokenExpired;
    }
    public void setTokenExpired(boolean tokenExpired){
        mIsTokenExpired = tokenExpired;
    }

    //各个平台的配置，建议放在全局Application或者程序入口
    {
//        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        PlatformConfig.setWeixin("wx56f22c971e7e35c5", "dcc060298acf91885ac0e70492475222");
        //豆瓣RENREN平台目前只能在服务器端配置
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad","http://sns.whalecloud.com");
        PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
        PlatformConfig.setQQZone("1106250541", "4ouYbGL2J7TWs6J6");
        PlatformConfig.setTwitter("3aIN7fuF685MuZ7jtXkQxalyi", "MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO");
        PlatformConfig.setAlipay("2015111700822536");
        PlatformConfig.setLaiwang("laiwangd497e70d4", "d497e70d4c3e4efeab1381476bac4c5e");
        PlatformConfig.setPinterest("1439206");
        PlatformConfig.setKakao("e4f60e065048eb031e235c806b31c70f");
        PlatformConfig.setDing("dingoalmlnohc0wggfedpk");
        PlatformConfig.setVKontakte("5764965","5My6SNliAaLxEm3Lyd9J");
        PlatformConfig.setDropbox("oz8v5apet3arcdy","h7p2pjbzkkxt02a");
        PlatformConfig.setYnote("9c82bf470cba7bd2f1819b0ee26f86c6ce670e9b");
    }
}
