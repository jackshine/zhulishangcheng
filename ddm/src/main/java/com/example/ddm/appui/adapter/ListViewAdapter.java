package com.example.ddm.appui.adapter;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.ddm.LocationActivity;
import com.example.ddm.MainActivity;
import com.example.ddm.R;
import com.example.ddm.application.PokonyanApplication;
import com.example.ddm.appui.adapter.clickback.ICityClickBack;
import com.example.ddm.appui.bean.AllCity;
import com.example.ddm.appui.bean.CityDetail;
import com.example.ddm.appui.bean.HotCity;
import com.example.ddm.appui.bean.RecentCityO;
import com.example.ddm.appui.bean.eventbus.Location;
import com.example.ddm.appui.gridview.MyGridView;
import com.example.ddm.appui.utils.DisplayUtils;
import com.example.ddm.manager.PreferenceManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *
 */

public class ListViewAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private String currentCity;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    protected android.os.Handler mHandler = new android.os.Handler(Looper.getMainLooper());
    private boolean isNeedRefresh;
    private List<CityDetail.DatasBean> mDetailCityList = new ArrayList<>();//定位的城市详情
    private List<HotCity.DatasBean> mHotCityList = new ArrayList<>();//热门城市
    private List<RecentCityO.DatasBean> mRecentCityList = new ArrayList<>();//最近访问城市
    private List<AllCity.DatasBean.DatasBeanTow> mAllCityList = new ArrayList<>();//全部城市
    private ICityClickBack mCityClickBack;
    private Map<String, Integer> mSortSignMap = new HashMap<>();
    public ListViewAdapter(Context context, List<CityDetail.DatasBean> detailCityList,
                           List<HotCity.DatasBean> hotCityList,
                           List<RecentCityO.DatasBean> recentCityList,
                           List<AllCity.DatasBean.DatasBeanTow> allCityList,
                           ICityClickBack cityClickBack) {
        mContext = context;
        if (detailCityList != null) {
            mDetailCityList.addAll(detailCityList);
        }
        if (hotCityList != null) {
            mHotCityList.addAll(hotCityList);
        }
        if (recentCityList != null) {
            mRecentCityList.addAll(recentCityList);
        }
        if (allCityList != null) {
            mAllCityList.addAll(allCityList);
        }
        mInflater = LayoutInflater.from(context);
        mCityClickBack = cityClickBack;
        isNeedRefresh=true;
        initLocation();
    }
    @Override
    public int getCount() {
        return mAllCityList.size();
    }
    @Override
    public Object getItem(int i) {
        return mAllCityList == null ? null : mAllCityList.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public int getViewTypeCount() {
        return 5;
    }
    @Override
    public int getItemViewType(int position) {
        return position < 4 ? position : 4;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        int type = getItemViewType(i);
        switch (type) {
            case 0:
                view = typeDetail(view);
                break;
            case 1:
                view = typeRecent(view);
                break;
            case 2:
                view = typeHot(view);
                break;
            case 3:
                view = typeAll(view);
                break;
            default:
                view = typeThis(i, view);
                break;
        }
        return view;
    }
    private View typeDetail(View convertView) {
        ViewHolderDetail holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.city_item_location_city, null);
            holder = new ViewHolderDetail(convertView);
            holder.tvLocation = (TextView) convertView.findViewById(R.id.tv_locate);
            holder.tvCurrentLocationCity = (TextView) convertView.findViewById(R.id.tv_current_locate_city);
            holder.tvExpandePop = (TextView) convertView.findViewById(R.id.tv_expand_pop);
            holder.tvClosePop = (TextView) convertView.findViewById(R.id.tv_close_pop);
            holder.viewLine = convertView.findViewById(R.id.view_detail_line);
        } else {
            holder = (ViewHolderDetail) convertView.getTag();
        }
        holder.tvLocation.setText("当前定位城市：");
        if (!TextUtils.isEmpty(currentCity)) {
            holder.tvCurrentLocationCity.setText(currentCity);
        }
        holder.tvExpandePop.setText("选择区县");
        //showPopwindow
        final ViewHolderDetail finalHolder = holder;
        View popView = mInflater.inflate(R.layout.item_choose_area_pop, null);
        final MyGridView gvDetail = (MyGridView) popView.findViewById(R.id.gv_detail_city);
        gvDetail.setAdapter(new CommonAdapter<CityDetail.DatasBean>(mContext,
                mDetailCityList, R.layout.city_item_city) {
            @Override
            public void setViewData(ViewHolder holder, CityDetail.DatasBean item, int position) {
                holder.setText(item.getAreaName(), R.id.tv_city_name);
            }
        });
        gvDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mCityClickBack.onCityDetailClickBack(mDetailCityList.get(i).getAreaName());
            }
        });

        final PopupWindow pop = new PopupWindow(popView, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        pop.setTouchable(true);
        holder.tvExpandePop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //展开一个popwindow
                pop.showAsDropDown(finalHolder.viewLine,0, DisplayUtils.dip2px(5));
                view.setVisibility(View.GONE);
                finalHolder.tvClosePop.setVisibility(View.VISIBLE);
            }
        });
        holder.tvClosePop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //关闭popwindow
                pop.dismiss();
                view.setVisibility(View.GONE);
                finalHolder.tvExpandePop.setVisibility(View.VISIBLE);
            }
        });

//        holder.detailGrid.setAdapter(new CommonAdapter<CityDetail.DatasBean>(mContext,
//                mDetailCityList, R.layout.city_item_city) {
//            @Override
//            public void setViewData(com.example.ddm.appui.adapter.ViewHolder holder, CityDetail.DatasBean item, int position) {
//                holder.setText(item.getAreaName(), R.id.tv_city_name);
//            }
//        });
//        holder.detailGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                mCityClickBack.onCityDetailClickBack();
//            }
//        });
        return convertView;
    }

    private View typeRecent(View convertView) {
        ViewHolderRecent holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.city_item_recent_visit_city, null);
            holder = new ViewHolderRecent(convertView);
            holder.gvRecent = (MyGridView) convertView.findViewById(R.id.gv_recent_visit_city);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_recent_visit_city);
        } else {
            holder = (ViewHolderRecent) convertView.getTag();
        }
        holder.tvTitle.setText("最近访问城市");
        holder.gvRecent.setAdapter(new CommonAdapter<RecentCityO.DatasBean>(mContext,
                mRecentCityList, R.layout.city_item_city) {
            @Override
            public void setViewData(ViewHolder holder, RecentCityO.DatasBean item, int position) {
                holder.setText(item.getCity(), R.id.tv_city_name);
            }
        });
        holder.gvRecent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mCityClickBack.onRecentCityClickBack(mRecentCityList.get(i).getCity());
            }
        });
        return convertView;
    }
    private View typeHot(View convertView) {
        ViewHolderHot holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.city_item_recent_visit_city, null);
            holder = new ViewHolderHot(convertView);
            holder.gvHot = (MyGridView) convertView.findViewById(R.id.gv_recent_visit_city);
        } else {
            holder = (ViewHolderHot) convertView.getTag();
        }
        holder.gvHot.setAdapter(new CommonAdapter<HotCity.DatasBean>(mContext,
                mHotCityList, R.layout.city_item_city) {
            @Override
            public void setViewData(ViewHolder holder, HotCity.DatasBean item, int position) {
                holder.setText(item.getCity(), R.id.tv_city_name);
            }
        });
        holder.gvHot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mCityClickBack.onHotCityClickBack(mHotCityList.get(i).getCity());
            }
        });
        return convertView;
    }
    private View typeAll(View convertView) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.city_item_all_city_textview, null);
        }
        return convertView;
    }
    private View typeThis(final int position, View convertView) {
        ViewHolderAll holderAll = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.city_item_city_list, null);
            holderAll = new ViewHolderAll(convertView);
            holderAll.tvAlpha = (TextView) convertView.findViewById(R.id.tv_alpha);
            holderAll.tvCityName = (TextView) convertView.findViewById(R.id.tv_city_name);
            holderAll.llItem = (LinearLayout) convertView.findViewById(R.id.ll_main);
        } else {
            holderAll = (ViewHolderAll) convertView.getTag();
        }
        holderAll.tvCityName.setText(mAllCityList.get(position).getAreaName());
        String currentAlpha = getAlpha(mAllCityList.get(position).getSort());
        if (position != 4) {//不等0，如果当前alpha与上一个alpha相等，显示
            String previewAlpha = (position - 1) >= 0 ?
                    getAlpha(mAllCityList.get(position - 1).getSort()) : " ";
            if (!previewAlpha.equals(currentAlpha)) {
                holderAll.tvAlpha.setVisibility(View.VISIBLE);
                holderAll.tvAlpha.setText(currentAlpha);
            } else {
                holderAll.tvAlpha.setVisibility(View.GONE);
            }
        } else {//等0,直接显示
            holderAll.tvAlpha.setVisibility(View.VISIBLE);
            holderAll.tvAlpha.setText(currentAlpha);
        }
        holderAll.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCityClickBack.onAllCityClickBack(mAllCityList.get(position).getAreaName());
            }
        });
        return convertView;
    }



    private class ViewHolderDetail {
        private TextView tvLocation;//正在定位
        private TextView tvCurrentLocationCity;//当前定位到的城市
        private TextView tvExpandePop;
        private TextView tvClosePop;
        private View viewLine;
//        private MyGridView detailGrid;
        public ViewHolderDetail(View convertView) {
            convertView.setTag(this);
        }
    }
    private class ViewHolderRecent {
        private TextView tvTitle;
        private MyGridView gvRecent;
        public ViewHolderRecent(View convertView) {
            convertView.setTag(this);
        }
    }
    private class ViewHolderHot {
        private MyGridView gvHot;

        public ViewHolderHot(View convertView) {
            convertView.setTag(this);
        }
    }
    private class ViewHolderAll {
        private TextView tvAlpha;
        private TextView tvCityName;
        private LinearLayout llItem;
        public ViewHolderAll(View convertView) {
            convertView.setTag(this);
        }
    }
    private String getAlpha(String str) {
        if (TextUtils.isEmpty(str)) {
            return "#";
        }
        if (str.equals("0")) {
            return "定位";
        }
        if (str.equals("1")) {
            return "最近";
        }
        if (str.equals("2")) {
            return "热门";
        }
        if (str.equals("3")) {
            return "全部";
        } else {
            return str;
        }
    }
    public void updateDetail(List<CityDetail.DatasBean> detailCityList) {
        if (detailCityList != null) {
            mDetailCityList.clear();
            mDetailCityList.addAll(detailCityList);
        }
        notifyDataSetChanged();
    }
    public void updateRecent(List<RecentCityO.DatasBean> recentCityList) {
        if (recentCityList != null) {
            mRecentCityList.clear();
            mRecentCityList.addAll(recentCityList);
        }
        notifyDataSetChanged();
    }
    public void updateHot(List<HotCity.DatasBean> hotCityList) {
        if (hotCityList != null) {
            mHotCityList.clear();
            mHotCityList.addAll(hotCityList);
        }
        notifyDataSetChanged();
    }
    public void updateAll(List<AllCity.DatasBean.DatasBeanTow> allCityList) {
        if (allCityList != null) {
            mAllCityList.clear();
            mAllCityList.addAll(allCityList);
            for (int i = 0; i < mAllCityList.size(); i++) {
                mSortSignMap.put(getAlpha(mAllCityList.get(i).getSort()), i);
            }
        }
        notifyDataSetChanged();
    }
    public int getPositonBySort(String s) {
        if (mSortSignMap.get(s) != null) {
            return mSortSignMap.get(s);
        }
        return 0;
    }
    public void initLocation() {
        locationClient = new AMapLocationClient(mContext);
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
        // 启动定位
        locationClient.startLocation();
    }
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(final AMapLocation location) {
            if (null != location) {
                StringBuffer sb = new StringBuffer();
                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                if(location.getErrorCode() == 0){
                    PreferenceManager.instance().savebankid(location.getCity());
                    currentCity = location.getCity().substring(0,location.getCity().length()-1);
                    locationClient.stopLocation();
                    if (location.getCity().equals(PreferenceManager.instance().getLocation())) {
                    } else {
                        final Dialog dialog = new Dialog(mContext, R.style.translate_dialog);
                        View contentView = mInflater.inflate(R.layout.dialog_change_city, null);
                        TextView tvTitle = (TextView) contentView.findViewById(R.id.tv_dialog_title);
                        TextView tvContent = (TextView) contentView.findViewById(R.id.tv_dialog_content);
                        Button btnCancel = (Button) contentView.findViewById(R.id.btn_dialog_cancel);
                        Button btnOk = (Button) contentView.findViewById(R.id.btn_dialog_ok);
                        tvTitle.setText("提示");
                        tvContent.setText("检测到您目前的所在城市是"+location.getCity()+"，是否切换？");
                        btnCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        btnOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                PreferenceManager.instance().saveLocation(location.getCity());
                                dialog.dismiss();
                                if (mContext instanceof LocationActivity) {
                                    LocationActivity activity = (LocationActivity) mContext;
                                    activity.finish();
                                    Intent intent = new Intent(PokonyanApplication.getInstance(), MainActivity.class);
                                    intent.putExtra("location", currentCity);
                                    mContext.startActivity(intent);
                                }
                            }
                        });
                        dialog.setContentView(contentView);
                        Window window = dialog.getWindow();
                        WindowManager.LayoutParams params = window.getAttributes();
                        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                        params.width = DisplayUtils.getWidthPx() / 4 * 3;
                        window.setAttributes(params);
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.show();
                    }
                }
            }
            }};
    private AMapLocationClientOption getDefaultOption(){
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }
}
