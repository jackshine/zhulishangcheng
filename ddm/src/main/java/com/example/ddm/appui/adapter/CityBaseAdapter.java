package com.example.ddm.appui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.ddm.appui.model.CityModel;

import java.util.List;

/**
 *s市
 */

public class CityBaseAdapter extends CommonAdapter<CityModel> {


    public CityBaseAdapter(List<CityModel> datas, Context context) {
        super(context, datas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv = new TextView(context);
        tv.setPadding(20, 20, 20, 20);
        tv.setTextSize(18);
        tv.setText(mDataList.get(position).getCity());
        return tv;
    }

    @Override
    public void setViewData(ViewHolder holder, CityModel item, int position) {

    }
}
