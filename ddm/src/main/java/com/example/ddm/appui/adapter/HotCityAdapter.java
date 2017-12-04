package com.example.ddm.appui.adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ddm.MainActivity;
import com.example.ddm.R;
import com.example.ddm.application.PokonyanApplication;
import com.example.ddm.appui.bean.City;
import com.example.ddm.appui.bean.HotCity;
import com.example.ddm.appui.home.CommonMethod;
import com.example.ddm.manager.PreferenceManager;

import java.util.List;
/**
 *
 */
public class HotCityAdapter extends BaseAdapter {
	private List<HotCity.DatasBean> mHotCityList;
	private LayoutInflater mInflater;
	private Context mContext;

	public HotCityAdapter(Context context, List<HotCity.DatasBean> hotCityList) {
		this.mHotCityList = hotCityList;
		this.mContext = context;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mHotCityList.size();
	}
	@Override
	public Object getItem(int position) {
		return mHotCityList.get(position);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.city_item_city, null);
			viewHolder.tvCityName = (TextView) convertView
					.findViewById(R.id.tv_city_name);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tvCityName.setText(mHotCityList.get(position).getCity());
		viewHolder.tvCityName.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				CommonMethod.addRecentCity(mHotCityList.get(position).getCity());
//				PreferenceManager.instance().saveLocation(mHotCityList.get(position).getCity());
				mContext.startActivity(new Intent(PokonyanApplication.getInstance(), MainActivity.class));
//				Toast.makeText(mContext,mHotCityList.get(position).getName() + "hhhhh", 0).show();
			}
		});
		return convertView;
	}
	class ViewHolder {
		TextView tvCityName;
	}
}
