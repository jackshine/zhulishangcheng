package com.example.ddm.appui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ddm.R;

import java.util.List;
/**
 */
public class AdapterPopuoShopCarGridView extends BaseAdapter {
    private Context context;
    private List<String> list;
    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局
    private int clickTemp = -1;
    //标识选择的Item
    public void setSeclection(int position) {
        clickTemp = position;
    }
    public AdapterPopuoShopCarGridView(List<String> list, Context context) {
        this.context = context;
        this.list = list;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.gridview_item, null);
            viewHolder = new ViewHolder();
            /**得到各个控件的对象*/
            viewHolder.mTxtDes = (TextView) convertView.findViewById(R.id.gridview_item_detilas);
            convertView.setTag(viewHolder);//绑定ViewHolder对象
        } else {
            viewHolder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }
        viewHolder.mTxtDes.setText(list.get(position));
        if (clickTemp == position) {
            viewHolder.mTxtDes.setBackgroundResource(R.drawable.def_green_bom);
        } else {
            viewHolder.mTxtDes.setBackgroundResource(R.drawable.def_white);
        }
        return convertView;
    }

    class ViewHolder {
        public TextView mTxtDes;
    }
}

