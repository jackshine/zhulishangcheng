package com.example.ddm.appui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.ddm.appui.model.ProvinceModel;

import java.util.List;

/**
 *省
 */

public class ProviceBaseAdapter extends CommonAdapter<ProvinceModel> {
    public ProviceBaseAdapter(List<ProvinceModel> datas, Context context) {
        super(context, datas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv = new TextView(context);
        tv.setPadding(20, 20, 20, 20);
        tv.setTextSize(18);
        tv.setText(mDataList.get(position).getProvince());
        return tv;
    }

    @Override
    public void setViewData(ViewHolder holder, ProvinceModel item, int position) {

    }
}
