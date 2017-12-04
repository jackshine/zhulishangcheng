package com.example.ddm.appui.adapter.clickback;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ddm.R;
import com.example.ddm.appui.adapter.CommentAdapter;
import com.example.ddm.appui.adapter.CommonBaseAdapter;
import com.example.ddm.appui.adapter.EvaluateAdapter;
import com.example.ddm.appui.adapter.FunnyGridAdapter;
import com.example.ddm.appui.bean.EvaluateBean;
import com.example.ddm.appui.bean.ShopDetail;
import com.example.ddm.appui.bean.StoreDetail;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.view.SodukuGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhouziwen .
 * 邮箱：1916973618@qq.com
 */

public class ShoppingAdapter extends CommonBaseAdapter<StoreDetail.DatasBean.CustomersdatasBean> {
    private FunnyGridAdapter funnyGridAdapter;
    private static final int TYPE_ONE=0;
    private static final  int TYPE_TWO=1;
    private List<String> mList = new ArrayList<>();
    private int type;
    public ShoppingAdapter(Context context, List<StoreDetail.DatasBean.CustomersdatasBean> dataList) {
        super(context, dataList);
    }
    //    public int getItemViewType(int position) {
//        type = super.getItemViewType(position);
//        if (mDataList.get(position).getAhead_img().size()==1){
//            type=TYPE_ONE;
//        }else{
//            type=TYPE_TWO;
//        }
//        return type;
//    }
    @Override
    public int getItemViewType(int position) {
        type = super.getItemViewType(position);
        LogUtils.d("dddaee"+mDataList.get(position).getImgUrl());
        if (TextUtils.isEmpty(mDataList.get(position).getImgUrl())){
            type=TYPE_ONE;
        }else{
            type=TYPE_TWO;
            LogUtils.d("我走了");
        }
        return type;

    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            if(type==TYPE_TWO){
                convertView = convertView.inflate(context,
                        R.layout.comment_pic_item, null);
                vh.mGridView = (SodukuGridView) convertView
                        .findViewById(R.id.grid_view);
                vh.mComment=(ImageView) convertView.findViewById(R.id.comment);
                vh.mShopName = (TextView) convertView.findViewById(R.id.shop_name);
                vh.mTime= (TextView) convertView.findViewById(R.id.time1);
                vh.mPic= (ImageView) convertView.findViewById(R.id.pic);
                vh.mContent= (TextView) convertView.findViewById(R.id.content);
                vh.mReply= (TextView) convertView.findViewById(R.id.reply);
                vh.mLayout = (LinearLayout) convertView.findViewById(R.id.layout);
//                   vh.tv_time= (TextView) convertView.findViewById(R.id.tv_funny_time);
            }if(type==TYPE_ONE){
                convertView = convertView.inflate(context,
                        R.layout.comment_txt_item, null);
                vh.mComment=(ImageView) convertView.findViewById(R.id.comment);
                vh.mShopName = (TextView) convertView.findViewById(R.id.shop_name);
                vh.mTime= (TextView) convertView.findViewById(R.id.time);
                vh.mPic= (ImageView) convertView.findViewById(R.id.pic);
                vh.mContent= (TextView) convertView.findViewById(R.id.content);
                vh.mReply= (TextView) convertView.findViewById(R.id.reply);
                vh.mLayout = (LinearLayout) convertView.findViewById(R.id.layout);
            }
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        StoreDetail.DatasBean.CustomersdatasBean funnyBean = mDataList.get(position);
        vh.mShopName.setText(funnyBean.getCustomerPhone());
        vh.mTime.setText(funnyBean.getCreateTime());
        Glide.with(context).load(funnyBean.getCustomerAvotorr()).placeholder(R.drawable.ic_launcher).crossFade().into(vh.mPic);
//        imageLoader.displayImage(funnyBean.getUimg(),vh.iv_photo,options);
        vh.mContent.setText("   "+funnyBean.getContent());
        if (TextUtils.isEmpty(funnyBean.getReplyMessage())) {
            vh.mLayout.setVisibility(View.GONE);
        } else {
            vh.mLayout.setVisibility(View.VISIBLE);
            vh.mReply.setText(funnyBean.getReplyMessage());
        }
//        funnyGridAdapter = new FunnyGridAdapter(funnyBean.getAhead_img(), context);
        if (!TextUtils.isEmpty(mDataList.get(position).getImgUrl())) {
            String d[] = funnyBean.getImgUrl().split(",");
            LogUtils.d("这是url"+funnyBean.getImgUrl());
            if (mList!=null) {
                mList.clear();
                for (int i = 0; i <d.length ; i++) {
                    mList.add(d[i]);
                    funnyGridAdapter = new FunnyGridAdapter(context,mList);
                    vh.mGridView.setAdapter(funnyGridAdapter);
                    funnyGridAdapter.notifyDataSetChanged();
                }
            }
        }
        LogUtils.d("图片集合"+mList);

        return convertView;
    }
    class ViewHolder {
        public SodukuGridView mGridView;
        public TextView mShopName;
        public TextView mTime;
        public ImageView mPic;
        public  TextView mContent;
        public TextView mReply;/*回复*/
        public ImageView mComment;/*星级评论*/
        private LinearLayout mLayout;
    }
}
