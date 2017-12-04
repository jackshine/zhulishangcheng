package com.example.ddm.appui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.ddm.R;

import java.util.List;


/**
 * Created by Administrator on 2017/3/15.
 *
 */

public class PopBaseAdapter extends CommonAdapter<String> {


    public PopBaseAdapter(Context context, List<String> dataList) {
        super(context, dataList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=View.inflate(context, R.layout.item_listview_pop,null);
        TextView tv= (TextView) view.findViewById(R.id.tv_text_pop);
        tv.setText(mDataList.get(position));
        return view;
    }

    @Override
    public void setViewData(ViewHolder holder, String item, int position) {

    }
}
