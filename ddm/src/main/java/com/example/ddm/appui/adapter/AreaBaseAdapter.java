package com.example.ddm.appui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.ddm.appui.model.CountyModel;

import java.util.List;

/**
 *区
 */

public class AreaBaseAdapter extends CommonAdapter<CountyModel> {

    public AreaBaseAdapter(List<CountyModel> datas, Context context) {
        super(context, datas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv = new TextView(context);
        tv.setPadding(20, 20, 20, 20);
        tv.setTextSize(18);
        tv.setText(mDataList.get(position).getCounty());
        return tv;
    }

    @Override
    public void setViewData(ViewHolder holder, CountyModel item, int position) {

    }
}
