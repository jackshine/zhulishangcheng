package com.example.ddm.appui.adapter;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.ddm.LocationActivity;
import com.example.ddm.LoginActivity;
import com.example.ddm.MainActivity;
import com.example.ddm.R;
import com.example.ddm.application.PokonyanApplication;
import com.example.ddm.appui.bean.AllCity;
import com.example.ddm.appui.bean.City;
import com.example.ddm.appui.bean.CityDetail;
import com.example.ddm.appui.bean.HotCity;
import com.example.ddm.appui.bean.eventbus.Card;
import com.example.ddm.appui.bean.eventbus.NowLocation;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.gridview.MyGridView;
import com.example.ddm.appui.home.CommonMethod;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
public class CityListAdapter extends BaseAdapter {
	private Context mContext;
	private List<AllCity.DatasBean.DatasBeanTow> mAllCityList;
	private List<HotCity.DatasBean> mHotCityList;
	private LocationActivity mLocationActivity;
	private List<String> mRecentCityList;
	private List<CityDetail.DatasBean> mDetailCitis;
	public HashMap<String, Integer> alphaIndexer;
	private String[] sections;
	private LocationClient myLocationClient;
	private String currentCity;
	private MyLocationListener myLocationListener;
	private boolean isNeedRefresh;
	private TextView tvCurrentLocateCity;
	private ProgressBar pbLocate;
	private TextView tvLocate;
	private final int VIEW_TYPE = 5;
	private String cityname;
	protected android.os.Handler mHandler = new android.os.Handler(Looper.getMainLooper());
	private List<String> mycityList=new ArrayList<>();
	public IAdapterClickBack mBack;
	public String getCityName() {
		return cityname;
	}
	public void setCityName(String cityname) {
		this.cityname = cityname;
	}
	public CityListAdapter(Context context, List<AllCity.DatasBean.DatasBeanTow> allCityList,
						   List<HotCity.DatasBean> hotCityList, List<String> recentCityList, IAdapterClickBack back) {
		this.mContext = context;
		this.mAllCityList = allCityList;
		this.mHotCityList = hotCityList;
		this.mRecentCityList=recentCityList;
		this.mBack = back;
		mDetailCitis = new ArrayList<>();
		getCityDetail("郑州市");
		alphaIndexer = new HashMap<String, Integer>();
		sections = new String[allCityList.size()];
		for (int i = 0; i < mAllCityList.size(); i++) {
			String currentStr = getAlpha(mAllCityList.get(i).getSort());
			String previewStr = (i - 1) >= 0 ? getAlpha(mAllCityList.get(i - 1).getSort()) : "";
			if (!previewStr.equals(currentStr)) {
				String name = getAlpha(mAllCityList.get(i).getSort());
				alphaIndexer.put(name, i);
				sections[i] = name;
			}
		}
		isNeedRefresh=true;
		initLocation();
	}
	@Override
	public int getViewTypeCount() {
		return VIEW_TYPE;
	}
	@Override
	public int getItemViewType(int position) {
		return position < 4 ? position : 4;
	}
	@Override
	public int getCount() {
		return mAllCityList.size();
	}
	@Override
	public Object getItem(int position) {
		return mAllCityList.get(position);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		int viewType = getItemViewType(position);
		if (viewType == 0) {
			convertView = View.inflate(mContext, R.layout.city_item_location_city,
					null);
			tvLocate=(TextView) convertView.findViewById(R.id.tv_locate);
			tvCurrentLocateCity=(TextView) convertView.findViewById(R.id.tv_current_locate_city);
			pbLocate = (ProgressBar) convertView.findViewById(R.id.pb_loacte);
			if(!isNeedRefresh){
				tvLocate.setText("当前定位城市");
				tvCurrentLocateCity.setVisibility(View.VISIBLE);
				tvCurrentLocateCity.setText(currentCity);
				pbLocate.setVisibility(View.GONE);
//				MyGridView gvRecentVisitCity = (MyGridView) convertView.findViewById(R.id.gv_detail_city);
//				gvRecentVisitCity.setAdapter(new DetailCityAdapter(mContext, mDetailCitis));
			}else{
				myLocationClient.start();
				MyGridView gvRecentVisitCity = (MyGridView) convertView.findViewById(R.id.gv_detail_city);
				gvRecentVisitCity.setAdapter(new DetailCityAdapter(mContext, mDetailCitis));
			}
			tvCurrentLocateCity.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					pbLocate.setVisibility(View.VISIBLE);
					tvLocate.setText("正在定位");
					tvCurrentLocateCity.setVisibility(View.GONE);
					myLocationClient.start();
				}
			});
		}
		else if (viewType == 1) {
			convertView = View.inflate(mContext,R.layout.city_item_recent_visit_city, null);
			TextView tvRecentVisitCity=(TextView) convertView.findViewById(R.id.tv_recent_visit_city);
			tvRecentVisitCity.setText("最近访问城市");
			MyGridView gvRecentVisitCity = (MyGridView) convertView.findViewById(R.id.gv_recent_visit_city);
			if (mRecentCityList.size()>=3){
				gvRecentVisitCity.setAdapter(new RecentVisitCityAdapter(mContext,mycityList));
			}else{
				gvRecentVisitCity.setAdapter(new RecentVisitCityAdapter(mContext,mRecentCityList));
			}
		} else if (viewType == 2) {
			convertView = View.inflate(mContext,R.layout.city_item_recent_visit_city, null);
			TextView tvRecentVisitCity=(TextView) convertView.findViewById(R.id.tv_recent_visit_city);
			tvRecentVisitCity.setText("热门城市");
			MyGridView gvRecentVisitCity = (MyGridView) convertView.findViewById(R.id.gv_recent_visit_city);
			gvRecentVisitCity.setAdapter(new HotCityAdapter(mContext, mHotCityList));
		} else if (viewType == 3) {
			convertView = View.inflate(mContext,R.layout.city_item_all_city_textview, null);
		} else {
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = View.inflate(mContext, R.layout.city_item_city_list,null);
				viewHolder.tvAlpha = (TextView) convertView.findViewById(R.id.tv_alpha);
				viewHolder.tvCityName = (TextView) convertView.findViewById(R.id.tv_city_name);
				viewHolder.llMain=(LinearLayout) convertView.findViewById(R.id.ll_main);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			if (position >= 1) {
				viewHolder.tvCityName.setText(mAllCityList.get(position).getAreaName());
				viewHolder.llMain.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						//全部城市的点击事件
						cityname = mAllCityList.get(position).getAreaName();
						//添加到最近访问城市
//						CommonMethod.addRecentCity(mAllCityList.get(position).getAreaName());
						mycityList.add(cityname);
						mBack.onBack(cityname);

					}
				});
				String currentStr = getAlpha(mAllCityList.get(position).getSort());
				String previewStr = (position - 1) >= 0 ? getAlpha(mAllCityList.get(position - 1).getSort()) : " ";

				if (!previewStr.equals(currentStr)) {
					viewHolder.tvAlpha.setVisibility(View.VISIBLE);
					viewHolder.tvAlpha.setText(currentStr);
				} else {
					viewHolder.tvAlpha.setVisibility(View.GONE);
				}
			}
		}
		return convertView;
	}
	private String getAlpha(String str) {
		if (str == null) {
			return "#";
		}
		if (str.trim().length() == 0) {
			return "#";
		}
		char c = str.trim().substring(0, 1).charAt(0);

		Pattern pattern = Pattern.compile("^[A-Za-z]+$");
		if (pattern.matcher(c + "").matches()) {
			return (c + "").toUpperCase();
		} else if (str.equals("0")) {
			return "定位";
		} else if (str.equals("1")) {
			return "最近";
		} else if (str.equals("2")) {
			return "热门";
		} else if (str.equals("3")) {
			return "全部";
		} else {
			return "#";
		}
	}
	class ViewHolder {
		TextView tvAlpha;
		TextView tvCityName;
		LinearLayout llMain;
	}
	public void initLocation() {
		myLocationClient = new LocationClient(mContext);
		myLocationListener=new MyLocationListener();
		myLocationClient.registerLocationListener(myLocationListener);
		LocationClientOption option = new LocationClientOption();
		option.setCoorType("bd09ll");
		option.setScanSpan(10000);
		option.setAddrType("all");
		option.setProdName("通过GPS定位我当前的位置");
		option.disableCache(true);
		option.setPriority(LocationClientOption.GpsFirst);
		myLocationClient.setLocOption(option);
		myLocationClient.start();
	}
	public  class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(final BDLocation arg0) {
			isNeedRefresh = false;
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					if (arg0.getCity() == null) {
						//定位失败
						tvLocate.setText("未定位到城市,请选择");
						tvCurrentLocateCity.setVisibility(View.VISIBLE);
						tvCurrentLocateCity.setText("重新选择");
						pbLocate.setVisibility(View.GONE);
						return;
					} else {
						currentCity = arg0.getCity();
						tvLocate.setText("当前定位城市");
						tvCurrentLocateCity.setVisibility(View.VISIBLE);
						//定位成功
						tvCurrentLocateCity.setText(currentCity);
						myLocationClient.stop();
						pbLocate.setVisibility(View.GONE);
//						PreferenceManager.instance().saveLocation(currentCity.substring(0,currentCity.length()-1));
						AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
						builder.setMessage("是否要切换到当前城市吗?"+""+currentCity);
						builder.setTitle("提示");
						builder.setPositiveButton("确认",
								new android.content.DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										dialog.dismiss();
										mContext.startActivity(new Intent(PokonyanApplication.getInstance(), MainActivity.class));
									}
								});
						builder.setNegativeButton("取消",
								new android.content.DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										dialog.dismiss();
									}
								});
						builder.create().show();
//						//定位成功
//						currentCity = arg0.getCity().substring(0, arg0.getCity().length() - 1);
//						tvLocate.setText("当前定位城市");
//						tvCurrentLocateCity.setVisibility(View.VISIBLE);
//						tvCurrentLocateCity.setText(currentCity);
//						myLocationClient.stop();
//						pbLocate.setVisibility(View.GONE);
					}
				}
			});
		}
		@Override
		public void onConnectHotSpotMessage(String s, int i) {
		}
	}
	private void getCityDetail(String cityName) {
		Map<String, String> param = new HashMap<>();
		param.put("cityName", cityName);
		HttpHelper.getInstance().post(Url.GET_CITYDETAIL, param, new RawResponseHandler() {
			@Override
			public void onSuccess(int statusCode, String response) {
				Log.d("wyt", "获取城市详情成功：" + response);
				CityDetail cityDetail = JsonUtils.parse(response, CityDetail.class);
				JsonUtils.parse(response, CityDetail.DatasBean.class);
				mDetailCitis.addAll(cityDetail.getDatas());
			}
			@Override
			public void onFailure(int statusCode, String error_msg) {
				Log.d("wyt", "获取城市详情失败：" + error_msg);
			}
		});

	}
}
