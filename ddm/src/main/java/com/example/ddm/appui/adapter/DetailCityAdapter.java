package com.example.ddm.appui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ddm.R;
import com.example.ddm.appui.bean.CityDetail;
import com.example.ddm.appui.home.CommonMethod;

import java.util.List;

/**
 *
 */
public class DetailCityAdapter extends BaseAdapter {

	private List<CityDetail.DatasBean> mHotCityList;
	private LayoutInflater mInflater;
	private Context mContext;

	public DetailCityAdapter(Context context, List<CityDetail.DatasBean> hotCityList) {
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
		viewHolder.tvCityName.setText(mHotCityList.get(position).getAreaName());
		viewHolder.tvCityName.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CommonMethod.addRecentCity(mHotCityList.get(position).getAreaName());
				Toast.makeText(mContext,mHotCityList.get(position).getAreaName() + "", Toast.LENGTH_LONG).show();
			}
		});
		return convertView;
	}

	class ViewHolder {
		TextView tvCityName;
	}

}
