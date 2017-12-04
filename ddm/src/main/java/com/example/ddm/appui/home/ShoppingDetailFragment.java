package com.example.ddm.appui.home;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bumptech.glide.Glide;
import com.example.ddm.LoginActivity;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.bean.GetUserIdBean;
import com.example.ddm.appui.bean.ShopDetailBean;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.share.Defaultcontent;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.utils.ShareBoardlistener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.ref.WeakReference;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * A simple {@link Fragment} subclass.
 * 商品详情
 */
public class ShoppingDetailFragment extends BaseFragment {
    private WebView mWebView;
    private LocationManager mLocationManager;
    private LocationClient mLocationClient;
    private BDLocationListener mBDLocationListener;
    private String provider,mStringLatitude;
    private TextView number,mName,mShopName,mBack,mShare;
    private ShareAction mShareAction;
    private UMShareListener mShareListener;
    private ImageView mImageView;
    private int userId;
    final public static int REQUEST_CODE_ASK_CALL_PHONE=123;
    private LinearLayout mLocation, mPhone;
    private double mLatitude, mLongitude;
    private List<ShopDetailBean.DatasBean> mDatasBeen = new ArrayList<>();
    private String mNum,mShopUrl,mAddress,mWeiZhi,mCity;
    public ShoppingDetailFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopping_detail, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mLocationClient = new LocationClient(getContext());
        mBDLocationListener = new MyBDLocationListener();
        // 注册监听
        mLocationClient.registerLocationListener(mBDLocationListener);
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");
        option.setScanSpan(10000);
        option.setAddrType("all");
        option.setProdName("通过GPS定位我当前的位置");
        option.disableCache(true);
        option.setPriority(LocationClientOption.GpsFirst);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
        mWebView = mFindViewUtils.findViewById(R.id.webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mBack = mFindViewUtils.findViewById(R.id.app_title_back);
        mShare = mFindViewUtils.findViewById(R.id.app_title_action);
        mImageView = mFindViewUtils.findViewById(R.id.pic_sp);
        mName = mFindViewUtils.findViewById(R.id.address);
        mShopName = mFindViewUtils.findViewById(R.id.txt_sp);
        mLocation = mFindViewUtils.findViewById(R.id.location);
        mPhone = mFindViewUtils.findViewById(R.id.phone);
        number = mFindViewUtils.findViewById(R.id.num);
        list();
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        //获取当前可用的位置控制器
        List<String> list = mLocationManager.getProviders(true);
        if (list.contains(LocationManager.GPS_PROVIDER)) {
            //是否为GPS位置控制器
            provider = LocationManager.GPS_PROVIDER;
        }else
        if (list.contains(LocationManager.NETWORK_PROVIDER)) {
            //是否为网络位置控制器
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            Toast.makeText(getContext(), "请检查网络或GPS是否打开",
                    Toast.LENGTH_LONG).show();
            return;
        }
        Location location = mLocationManager.getLastKnownLocation(provider);
        if (location != null) {
            //获取当前位置，这里只用到了经纬度
            mLatitude = location.getLatitude();
            mLongitude = location.getLongitude();
//            String string = "纬度为：" + location.getLatitude() + ",经度为："
//                    + location.getLongitude();
//            Toast.makeText(getContext(),string,Toast.LENGTH_LONG).show();
        }
        if (mLocationClient.isStarted()) {
            // 获得位置之后停止定位
            mLocationClient.stop();
        }
    }
    private class MyBDLocationListener implements BDLocationListener{
    @Override
    public void onReceiveLocation(BDLocation location) {
        if (location != null) {
            // 根据BDLocation 对象获得经纬度以及详细地址信息
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
          mWeiZhi = location.getAddrStr();
            mCity=location.getCity();
            LogUtils.d("我的位置"+mWeiZhi);
            if (mLocationClient.isStarted()) {
                // 获得位置之后停止定位
                mLocationClient.stop();
            }
        }
    }
    @Override
    public void onConnectHotSpotMessage(String s, int i) {
    }
}
    @Override
    protected void setListener() {
        super.setListener();
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popSelf();
            }
        });
        mLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(),"暂未完成",Toast.LENGTH_SHORT).show();
                    GPS();
            }
        });
        mPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.CALL_PHONE);
                    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[] {
                                Manifest.permission.CALL_PHONE
                        }, REQUEST_CODE_ASK_CALL_PHONE);
                        return;
                    } else {
                        // 上面已经写好的拨号方法 callDirectly(mobile);
                        Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + mNum));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                } else {
                    // 上面已经写好的拨号方法 callDirectly(mobile);
                    Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + mNum));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
        mShareListener = new CustomShareListener(this);
        mShareAction = new ShareAction(getActivity()).setDisplayList(
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
                SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.MORE)
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                        if (share_media == SHARE_MEDIA.SMS) {
                            new ShareAction(getActivity()).withText("来自分享面板标题")
                                    .setPlatform(share_media)
                                    .setCallback(mShareListener)
                                    .share();
                        } else {
//                            UMWeb web = new UMWeb(Defaultcontent.url);
                            UMWeb web = new UMWeb(Url.SHARE+PreferenceManager.instance().getShopId()+"&useId="+userId);
                            web.setTitle("分享一个产品，代购整座城市"+"\n"+"消费商—"+PreferenceManager.instance().getName()+"的店");
                            web.setDescription("同城优选，助利商城");
                            web.setThumb(new UMImage(getContext(), R.mipmap.logo_share));
                            new ShareAction(getActivity()).withMedia(web)
                                    .setPlatform(share_media)
                                    .setCallback(mShareListener)
                                    .share();
                        }
                    }
                });
        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(PreferenceManager.instance().getToken())) {
                    mShareAction.open();
                } else {
                    mBaseActivity.showActivity(LoginActivity.class,null);
                }
            }
        });
    }
    @Override
    protected void setData() {
        super.setData();
        GetId();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_ASK_CALL_PHONE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted callDirectly(mobile);
                }else {
                    // Permission Denied Toast.makeText(MainActivity.this,"CALL_PHONE Denied", Toast.LENGTH_SHORT) .show();
                }break;
            default:super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    /**
     * 商品详情
     */
    private void list(){
        showLoading();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.SHOPID, PreferenceManager.instance().getShopId());
        HttpHelper.getInstance().post(Url.SHOP_DETAIL, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final ShopDetailBean datasBean = JsonUtils.parse(response, ShopDetailBean.class);
                hiddenLoading();
                if (datasBean.getCode()==1) {
                    mShopName.setText( datasBean.getDatas().getShopName());
                    mName.setText(datasBean.getDatas().getShopAddress());
                    mNum = datasBean.getDatas().getPhone();
                    mStringLatitude = datasBean.getDatas().getAddress();
                    mAddress = datasBean.getDatas().getShopAddress();
                    number.setText(mNum);
                    mShopUrl = datasBean.getDatas().getRealName();
                    Glide.with(getContext()).load(datasBean.getDatas().getMainImg()).placeholder(R.drawable.ic_launcher).crossFade().into(mImageView);
                    Document doc= Jsoup.parse(datasBean.getDatas().getDetail());
                    Elements elements=doc.getElementsByTag("img");
                    for (Element element : elements) {
                        element.attr("style","width:100%").attr("height","auto");
                    }
                    LogUtils.d("aaaaaaaaaaaaa"+doc.toString());
                    mWebView.loadDataWithBaseURL(null,doc.toString(),"text/html","utf-8", null);
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                hiddenLoading();
            }
        });
    }
    /**
     * 调用百度地图
     */
    private void GPS(){
        Intent intent = null;
        try {// 如果有安装百度地图 就启动百度地图
            StringBuffer sbs = new StringBuffer();
            sbs.append("intent://map/direction?origin=latlng:")
                    // 我的位置
                    .append(mLatitude)
                    .append(",")
                    .append(mLongitude)
                    .append("|name:")
                    .append(mWeiZhi)
                    // 去的位置
                    .append("&destination=latlng:")
                    .append(mStringLatitude)
                    .append("|name:")
                    .append(mAddress)
                    // 城市
                    .append("&mode=driving")
                    .append("&coord_type=gcj02")
                    .append("&referer=com.bjypt.vipcard|vipcard#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
            try {
                intent = Intent.getIntent(sbs.toString());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
           getContext(). startActivity(intent);
        } catch (Exception e) {// 没有百度地图则弹出网页端
//            String rul = "http://api.map.baidu.com/direction?origin=latlng:"+mLatitude+","+mLongitude+"|name:我家&destination=latlng:"+mStringLatitude+"|name:目的&mode=driving&origin_region="+"当前位置"+"&destination_region="+mName+"&output=html&coord_type=gcj02&src=yourCompanyName|yourAppName";
//            Uri uri = Uri.parse(rul.toString());
//            intent = new Intent(Intent.ACTION_VIEW, uri);
//            getContext().startActivity(intent);
            StringBuffer sb = new StringBuffer();
            sb.append("http://api.map.baidu.com/direction?origin=latlng:")
                    // 我的位置
                    .append(mLatitude)
                    .append(",")
                    .append(mLongitude)
                    .append("|name:")
                    .append(mWeiZhi)
                    // 去的位置
                    .append("&destination=latlng:")
                    .append(mStringLatitude)
                    .append("|name:")
                    .append(mAddress)
                    // 城市
                    .append("&mode=driving®ion=").append(mCity)
                    .append("&output=html");
            Uri uri = Uri.parse(sb.toString());
            intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }
    private static class CustomShareListener implements UMShareListener {
        private WeakReference<ShoppingDetailFragment> mActivity;
        private CustomShareListener(ShoppingDetailFragment activity) {
            mActivity = new WeakReference(activity);
        }
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }
        @Override
        public void onResult(SHARE_MEDIA platform) {
            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(mActivity.get().getContext(), " 收藏成功啦", Toast.LENGTH_SHORT).show();
            } else {
                if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                        && platform != SHARE_MEDIA.EMAIL
                        && platform != SHARE_MEDIA.FLICKR
                        && platform != SHARE_MEDIA.FOURSQUARE
                        && platform != SHARE_MEDIA.TUMBLR
                        && platform != SHARE_MEDIA.POCKET
                        && platform != SHARE_MEDIA.PINTEREST
                        && platform != SHARE_MEDIA.INSTAGRAM
                        && platform != SHARE_MEDIA.GOOGLEPLUS
                        && platform != SHARE_MEDIA.YNOTE
                        && platform != SHARE_MEDIA.EVERNOTE) {
                    Toast.makeText(mActivity.get().getContext(), " 分享成功啦", Toast.LENGTH_SHORT).show();
                }
            }
        }
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                    && platform != SHARE_MEDIA.EMAIL
                    && platform != SHARE_MEDIA.FLICKR
                    && platform != SHARE_MEDIA.FOURSQUARE
                    && platform != SHARE_MEDIA.TUMBLR
                    && platform != SHARE_MEDIA.POCKET
                    && platform != SHARE_MEDIA.PINTEREST
                    && platform != SHARE_MEDIA.INSTAGRAM
                    && platform != SHARE_MEDIA.GOOGLEPLUS
                    && platform != SHARE_MEDIA.YNOTE
                    && platform != SHARE_MEDIA.EVERNOTE) {
                Toast.makeText(mActivity.get().getContext(), " 分享失败啦", Toast.LENGTH_SHORT).show();
                if (t != null) {
                    Log.d("throw", "throw:" + t.getMessage());
                }
            }
        }
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(mActivity.get().getContext(), " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(getContext()).onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mShareAction.close();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    private void GetId(){
        showLoading("正在加载");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        HttpHelper.getInstance().post(Url.GETUSERID, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                hiddenLoading();
                final GetUserIdBean bean = JsonUtils.parse(response, GetUserIdBean.class);
                if (bean.getCode()==1) {
                    userId = bean.getDatas().getUserId();
                } else if (bean.getCode()==0) {
                    StringUtils.showToast(getActivity(),bean.getMsg());
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                hiddenLoading();
                StringUtils.showToast(getActivity(),error_msg);
            }
        });
    }
}
