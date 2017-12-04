package com.example.ddm.appui.gridview;
import com.example.ddm.R;
import com.example.ddm.appui.bean.ViewPagerInfo;

import java.util.ArrayList;
public class GridViewDataOne {
	private ArrayList<ViewPagerInfo> data = new ArrayList<ViewPagerInfo>();
	public ArrayList<ViewPagerInfo> getData() {
		return data;
	}
	public void setData(ArrayList<ViewPagerInfo> data) {
		this.data = data;
	}
	public ArrayList<ViewPagerInfo> getDataSource(){
		ViewPagerInfo info = new ViewPagerInfo();
		info.setIcon(R.drawable.sort1);
		info.setText("数码电子");
		data.add(info);
		info = new ViewPagerInfo();
		info.setIcon(R.drawable.sort2);
		info.setText("食品类");
		data.add(info);
		info = new ViewPagerInfo();
		info.setIcon(R.drawable.sort3);
		info.setText("电影");
		data.add(info);
		info = new ViewPagerInfo();
		info.setIcon(R.drawable.sort4);
		info.setText("酒店");
		data.add(info);
		info = new ViewPagerInfo();
		info.setIcon(R.drawable.sort5);
		info.setText("休闲娱乐");
		data.add(info);
		info = new ViewPagerInfo();
		info.setIcon(R.drawable.sort6);
		info.setText("机票/火车票");
		data.add(info);
		info = new ViewPagerInfo();
		info.setIcon(R.drawable.sort7);
		info.setText("外卖");
		data.add(info);
		info = new ViewPagerInfo();
		info.setIcon(R.drawable.sort8);
		info.setText("KTV");
		data.add(info);
		info = new ViewPagerInfo();
		info.setIcon(R.drawable.sort9);
		info.setText("周边游");
		data.add(info);
		info = new ViewPagerInfo();
		info.setIcon(R.drawable.sort10);
		info.setText("丽人");
		data.add(info);
		return data;
	}
}
