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
import com.example.ddm.appui.home.CommonMethod;

import java.util.List;

/**
 *cc
 */
public class RecentVisitCityAdapter extends BaseAdapter {
	
	private List<String> mRecentVisitCityList;
	private LayoutInflater mInflater;
	private Context mContext;
	
	public RecentVisitCityAdapter(Context context, List<String> recentVisitCityList) {
		this.mRecentVisitCityList=recentVisitCityList;
		this.mContext=context;
		mInflater=LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mRecentVisitCityList.size();
	}

	@Override
	public Object getItem(int position) {
		return mRecentVisitCityList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder=null;
		if(convertView==null){
			viewHolder=new ViewHolder();
			convertView=mInflater.inflate(R.layout.city_item_city,null);
			viewHolder.tvCityName=(TextView) convertView.findViewById(R.id.tv_city_name);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		viewHolder.tvCityName.setText(mRecentVisitCityList.get(position));
		viewHolder.tvCityName.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CommonMethod.addRecentCity(mRecentVisitCityList.get(position));
				Toast.makeText(mContext,mRecentVisitCityList.get(position)+"",Toast.LENGTH_SHORT).show();
			}
		});
		return convertView;
	}
	
	class ViewHolder{
		TextView tvCityName;
	}

}
