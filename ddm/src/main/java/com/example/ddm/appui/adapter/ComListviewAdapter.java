package com.example.ddm.appui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.ddm.R;
import com.example.ddm.appui.bean.GetSpecification;
import com.example.ddm.appui.view.ZFlowLayout;

import java.util.ArrayList;
import java.util.List;
/**
 * 作者：Yzp on 2017-04-13 16:04
 * 邮箱：15111424807@163.com
 * QQ: 486492302
 */
public class ComListviewAdapter extends BaseAdapter {
    private Context context;
    private Handler mHandler;
    private ZFlowLayout mFlowLayout;
    private GetSpecification dateModle;
    private List<GetSpecification.DatasBean.DataBean> lables;
    private Bundle bundle;
    private int SELCT_SUCCESS =0x00123;
    private String mOne, mTwo;
    public ComListviewAdapter(GetSpecification dateModle, Context context, Handler mHandler) {
        this.dateModle = dateModle;
        this.context = context;
        this.mHandler = mHandler;

    }
    @Override
    public int getCount() {
        return dateModle.getDatas().size();
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.item_specification, null);
        if (view != null){
            TextView type_txt = (TextView) view.findViewById(R.id.specification);
            String type = dateModle.getDatas().get(position).getName()+" :";
            String typeId = String.valueOf(dateModle.getDatas().get(position).getId());
            type_txt.setText(type);
            type_txt.setTextColor(Color.parseColor("#a1a1a1"));
            mFlowLayout = (ZFlowLayout) view.findViewById(R.id.flowLayout);
            lables = dateModle.getDatas().get(position).getData();
            if (position==0) {
                if (mFlowLayout.getChildCount() == 0){
                    //HashMap<String,TextView[]> mapView=new HashMap<String, TextView[]>();
                    TextView[]  textViews = new TextView[lables.size()];
                    String[] type_url = new String[lables.size()];
                    for (int i = 0; i < lables.size(); i++) {
                        TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.news_top_title_search_, mFlowLayout, false);
                        textViews[i] = tv;
                        textViews[i].setBackgroundResource(R.drawable.def_white);
                        textViews[i].setTextColor(Color.parseColor("#535353"));
                        textViews[i].setText(lables.get(i).getContent());
                        textViews[i].setTag(i);
                        type_url[i] =String.valueOf(lables.get(i).getId());   //对应的小id
                        mFlowLayout.addView(textViews[i]);//添加到父View
                    }
                    //绑定标签的Click事件
                    for(int j = 0 ; j < textViews.length;j++){
                        textViews[j].setTag(textViews);
                        mOne = String.valueOf(lables.get(j).getId());
                        textViews[j].setOnClickListener(new LableClickListener(type,mOne,""));
                    }
                }
            } else if (position==1) {
                if (mFlowLayout.getChildCount() == 0){
                    //HashMap<String,TextView[]> mapView=new HashMap<String, TextView[]>();
                    TextView[]  textViews = new TextView[lables.size()];
                    String[] type_url = new String[lables.size()];
                    for (int i = 0; i < lables.size(); i++) {
                        TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.news_top_title_search_, mFlowLayout, false);
                        textViews[i] = tv;
                        textViews[i].setBackgroundResource(R.drawable.def_white);
                        textViews[i].setTextColor(Color.parseColor("#535353"));
                        textViews[i].setText(lables.get(i).getContent());
                        textViews[i].setTag(i);
                        type_url[i] =String.valueOf(lables.get(i).getId());   //对应的小id
                        mFlowLayout.addView(textViews[i]);//添加到父View
                    }
                    //绑定标签的Click事件
                    for(int j = 0 ; j < textViews.length;j++){
                        textViews[j].setTag(textViews);
                        mTwo = String.valueOf(lables.get(j).getId());
                        textViews[j].setOnClickListener(new LableClickListener(type,"",mTwo));
                    }
                }
            }
        }
        return view;
    }
    class LableClickListener implements View.OnClickListener {
        private String type;
        private String type_url;
        private String type_two;
        public LableClickListener( String type,String one,String two){
            this.type = type;
            this.type_url = one;
            this.type_two = two;
        }
        @Override
        public void onClick(View v) {
            TextView[] textViews = (TextView[]) v.getTag();
            TextView tv = (TextView) v;
            for (int i = 0; i < textViews.length; i++) {
                //让点击的标签背景变成橙色，字体颜色变为白色
                if (tv.equals(textViews[i])) {
                    textViews[i].setBackgroundResource(R.drawable.def_green_bom);
                    textViews[i].setTextColor(Color.parseColor("#FFFFFF"));
                    //传递属性选择后的商品数据
                    Message message = new Message();
                    bundle = new Bundle();
                    bundle.putString("type",type);
                    bundle.putString("type_name",tv.getText().toString());
                    bundle.putString("type_url",type_url);
                    bundle.putString("type_two",type_two);
                    message.setData(bundle);
                    message.what = SELCT_SUCCESS;
                    mHandler.sendMessage(message);
                } else {
                    //其他标签背景变成白色，字体颜色为黑色
                    textViews[i].setBackgroundResource(R.drawable.def_white);
                    textViews[i].setTextColor(Color.parseColor("#535353"));
                }
            }
        }
    }
}
