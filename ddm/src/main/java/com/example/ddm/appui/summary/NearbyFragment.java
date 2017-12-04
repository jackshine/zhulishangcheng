package com.example.ddm.appui.summary;


import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.adapter.CommonAdapter;
import com.example.ddm.appui.adapter.FragAdapter;
import com.example.ddm.appui.bean.Category;
import com.example.ddm.appui.bean.eventbus.Address;
import com.example.ddm.appui.bean.eventbus.ListViewHeight;
import com.example.ddm.appui.constant.FragmentTag;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.summary.nearbylist.AllFragment;
import com.example.ddm.appui.summary.nearbylist.CarFragment;
import com.example.ddm.appui.summary.nearbylist.CateFragment;
import com.example.ddm.appui.summary.nearbylist.ElectronicFragment;
import com.example.ddm.appui.summary.nearbylist.HomeDecorationFragment;
import com.example.ddm.appui.summary.nearbylist.HotelFragment;
import com.example.ddm.appui.summary.nearbylist.RelaxationFragment;
import com.example.ddm.appui.summary.nearbylist.ServiceFragment;
import com.example.ddm.appui.summary.nearbylist.TravelFragment;
import com.example.ddm.appui.summary.nearbylist.WineTeaFragment;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.ShowFragmentUtils;
import com.example.ddm.appui.view.MyScrollView;
import com.example.ddm.appui.view.ObservableScrollView;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * 附近界面
 */
public class NearbyFragment extends BaseFragment implements AMap.OnMyLocationChangeListener, LocationSource,
        AMapLocationListener,MyScrollView.OnScrollListener {
    private ViewPager mViewPager;
    private TabLayout mTabLayout,mTabLayout1;
    private List<String> mTitles = new ArrayList<>();//标题集合
    private List<BaseFragment> mfragments = new ArrayList<>();//视图集合
    private ElectronicFragment mElectronicFragment;
    private AllFragment mAllFragment;
    private CarFragment mCarFragment;
    private CateFragment mCateFragment;
    private HomeDecorationFragment mHomeDecorationFragment;
    private HotelFragment mHotelFragment;
    private RelaxationFragment mRelaxationFragment;
    private ServiceFragment mServiceFragment;
    private TravelFragment mTravelFragment;
    private WineTeaFragment mWineTeaFragment;
    private MapView mMapView;
    private AMap mAMap;
    private MyScrollView mScrollView;
    private List<String> mList = new ArrayList<>();//店铺类型的集合
    private CommonAdapter<Category.DataBeans> mCategoryAdapter;
    private List<Category.DataBeans> mCategoryList = new ArrayList<>();//分类的集合
    private OnLocationChangedListener mListener;
    private MyLocationStyle myLocationStyle;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private String mName;
    private TextView mTextView;
    private ImageView mSearch;
    double latitude;
    double longitude;
    private String mLocation;
    private View mapLayout;
    public NearbyFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mapLayout == null) {
            Log.i("sys", "MF onCreateView() null");
            mapLayout = inflater.inflate(R.layout.fragment_nearby, null);
            mMapView = (MapView) mapLayout.findViewById(R.id.map);
            mMapView.onCreate(savedInstanceState);
            if (mAMap == null) {
                mAMap = mMapView.getMap();
                setUpMap();
            }
        }else {
            if (mapLayout.getParent() != null) {
                ((ViewGroup) mapLayout.getParent()).removeView(mapLayout);
            }
    }
        return mapLayout;
    }
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }
    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        // 如果要设置定位的默认状态，可以在此处进行设置
        myLocationStyle = new MyLocationStyle();
        mAMap.setMyLocationStyle(myLocationStyle);
//        mAMap.setOnMapClickListener(this);// 对amap添加单击地图事件监听器
        mAMap.setLocationSource(this);// 设置定位监听
        mAMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        mAMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        mAMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE));
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mAllFragment = new AllFragment();
        mCarFragment = new CarFragment();
        mCateFragment = new CateFragment();
        mHomeDecorationFragment = new HomeDecorationFragment();
        mHotelFragment = new HotelFragment();
        mRelaxationFragment = new RelaxationFragment();
        mElectronicFragment = new ElectronicFragment();
        mWineTeaFragment = new WineTeaFragment();
        mTravelFragment = new TravelFragment();
        mServiceFragment = new ServiceFragment();

        mfragments.add(mElectronicFragment);
        mfragments.add(mWineTeaFragment);
        mfragments.add(mCateFragment);
        mfragments.add(mCarFragment);
        mfragments.add(mHotelFragment);
        mfragments.add(mRelaxationFragment);
        mfragments.add(mHomeDecorationFragment);
        mfragments.add(mServiceFragment);
        mfragments.add(mTravelFragment);
        mfragments.add(mAllFragment);
        mSearch = mFindViewUtils.findViewById(R.id.search);
        mScrollView = mFindViewUtils.findViewById(R.id.scrollView);
        mScrollView.setOnScrollListener(this);
        mTabLayout = mFindViewUtils.findViewById(R.id.tab_title);
        mTabLayout1 = mFindViewUtils.findViewById(R.id.tab_title1);
        mViewPager = mFindViewUtils.findViewById(R.id.viewpager);
        mTextView = mFindViewUtils.findViewById(R.id.mainactivity_title_positioncity_txt);
    }
    @Override
    protected void setListener() {
        super.setListener();
        mFindViewUtils.findViewById(R.id.parent_layout).getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                onScroll(mScrollView.getScrollY());
                System.out.println(mScrollView.getScrollY());
            }
        });
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentUtils.showFragment(getActivity(),NearbySearchFragment.class, FragmentTag.NEARBYSEARCHFRAGMENT,null,true);
            }
        });
    }
    @Override
    protected void setData() {
        super.setData();
        getCategory();
    }
    @Subscribe
    public void onEventMainThread(ListViewHeight listViewHeight) {
        LogUtils.d("收到距离"+listViewHeight.getInt());
        int height = listViewHeight.getInt();
        ViewGroup.LayoutParams layoutParams = mViewPager.getLayoutParams();
        layoutParams.height = height;
        mViewPager.setLayoutParams(layoutParams);
    }
    private void getCategory() {
        Map<String, String> param = new HashMap<>();
        HttpHelper.getInstance().post(Url.GET_CATEGORY, param, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.d("wyt", "获取首页分类成功：" + response);
                Category category = JsonUtils.parse(response, Category.class);
                if (category.getCode()==1&&category.getDatas().size()>0) {
                    if (mCategoryList!=null) {
                        mCategoryList.clear();
                        mCategoryList.addAll(category.getDatas());
                        for (int i = 0; i <category.getDatas().size(); i++) {
                            mList.add(category.getDatas().get(i).getName());
                        }
                        mTitles.addAll(mList);
                        mTabLayout.addTab(mTabLayout.newTab().setText(mTitles.get(0)));
                        mTabLayout1.addTab(mTabLayout1.newTab().setText(mTitles.get(0)));
                        FragAdapter adapter = new FragAdapter(getChildFragmentManager(), mfragments,mTitles);
                        mViewPager.setAdapter(adapter);
                        mViewPager.setOffscreenPageLimit(9);
                        mTabLayout.setupWithViewPager(mViewPager);//让TabLayout随着ViewPager的变换而变换
                        mTabLayout1.setupWithViewPager(mViewPager);//让TabLayout随着ViewPager的变换而变换
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                Log.d("wyt", "获取首页分类失败：" + error_msg);
            }
        });
    }
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    /*开启定位时*/
                    && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                latitude = amapLocation.getLatitude();
                longitude = amapLocation.getLongitude();
                mLocation = amapLocation.getPoiName();
                mTextView.setText(mLocation);
                mlocationClient.stopLocation();
            }
        }
    }
//    @Override
//    public void onMapClick(LatLng latLng) {
//        mAMap.clear();
//        mName = latLng.toString();
//        latitude = latLng.latitude;
//        longitude = latLng.longitude;
//        MarkerOptions otMarkerOptions = new MarkerOptions();
//        otMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
//        otMarkerOptions.position(latLng);
//        mAMap.addMarker(otMarkerOptions);
//        mAMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
//    }
    @Override
    public void onMyLocationChange(Location location) {
// 定位回调监听
        if(location != null) {
            Toast.makeText(getContext(), "定位成功" + location.getLatitude() + " lon: " + location.getLongitude(), Toast.LENGTH_SHORT).show();
            LogUtils.d("定位成功" + location.getLatitude() + " lon: " + location.getLongitude());
            Bundle bundle = location.getExtras();
            if(bundle != null) {
                int errorCode = bundle.getInt(MyLocationStyle.ERROR_CODE);
                String errorInfo = bundle.getString(MyLocationStyle.ERROR_INFO);
                // 定位类型，可能为GPS WIFI等，具体可以参考官网的定位SDK介绍
                int locationType = bundle.getInt(MyLocationStyle.LOCATION_TYPE);
                /*
                errorCode
                errorInfo
                locationType
                */
                Log.e("amap", "定位信息， code: " + errorCode + " errorInfo: " + errorInfo + " locationType: " + locationType );
            } else {
                Log.e("amap", "定位信息， bundle is null ");
            }
        } else {
            Log.e("amap", "定位失败");
        }
    }
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(getContext());
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }
    @Override
    public void onScroll(int scrollY) {
        int mBuyLayout2ParentTop = Math.max(scrollY, mTabLayout.getTop());
        mTabLayout1.layout(0, mBuyLayout2ParentTop, mTabLayout1.getWidth(), mBuyLayout2ParentTop + mTabLayout1.getHeight());
    }
    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y>100) {
            mTextView.getBackground().setAlpha(255);
        }
    }
}
