package com.example.ddm.appui.gridview;

import com.example.ddm.R;
import com.example.ddm.appui.bean.ViewPagerInfo;

import java.util.ArrayList;

public class GridViewDataTwo {
	private ArrayList<ViewPagerInfo> data = new ArrayList<ViewPagerInfo>();

	public ArrayList<ViewPagerInfo> getData() {
		return data;
	}

	public void setData(ArrayList<ViewPagerInfo> data) {
		this.data = data;
	}
	
	public ArrayList<ViewPagerInfo> getDataSource(){
		ViewPagerInfo info = new ViewPagerInfo();
		info.setIcon(R.drawable.sort11);
		info.setText("旅游出行");
		data.add(info);
		
		info = new ViewPagerInfo();
		info.setIcon(R.drawable.sort12);
		info.setText("结婚/摄影");
		data.add(info);
		
		info = new ViewPagerInfo();
		info.setIcon(R.drawable.sort13);
		info.setText("母婴亲子");
		data.add(info);
		
		info = new ViewPagerInfo();
		info.setIcon(R.drawable.sort14);
		info.setText("学习培训");
		data.add(info);
		
		info = new ViewPagerInfo();
		info.setIcon(R.drawable.sort15);
		info.setText("生活服务");
		data.add(info);
		
		info = new ViewPagerInfo();
		info.setIcon(R.drawable.sort16);
		info.setText("温泉");
		data.add(info);
		
		info = new ViewPagerInfo();
		info.setIcon(R.drawable.sort17);
		info.setText("景点");
		data.add(info);
		
		info = new ViewPagerInfo();
		info.setIcon(R.drawable.sort18);
		info.setText("家装");
		data.add(info);

		info = new ViewPagerInfo();
		info.setIcon(R.drawable.sort19);
		info.setText("运动健身");
		data.add(info);

		info = new ViewPagerInfo();
		info.setIcon(R.drawable.sort20);
		info.setText("全部分类");
		data.add(info);
		return data;
	}
}
