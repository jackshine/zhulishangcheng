package com.example.ddm.appui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ddm.R;
import com.example.ddm.appui.adapter.clickback.Callback;
import com.example.ddm.appui.bean.GetUserAddress;
import com.example.ddm.appui.bean.SendCodeBean;
import com.example.ddm.appui.bean.eventbus.LoginSuccess;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Zhouziwen .
 * 邮箱：1916973618@qq.com
 */

public class ShouhuoAddressBaseAdapter extends CommonBaseAdapter<GetUserAddress.DatasBean.AddressLestBean>implements View.OnClickListener{
    private boolean isSelect;
    private int flag;//0:删除1：选择默认地址
    private int oldPosition;
    private FragmentActivity mActivity;
    private Callback mCallback;
    public ShouhuoAddressBaseAdapter(Context context, List<GetUserAddress.DatasBean.AddressLestBean> dataList,Callback callback) {
        super(context, dataList);
        mCallback = callback;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = convertView.inflate(context, R.layout.item_listview_shouhuo_address, null);
            vh.tv_shouhuo_address_name = (TextView) convertView.findViewById(R.id.tv_shouhuo_address_name);
            vh.address_phone = (TextView) convertView.findViewById(R.id.tv_shouhuo_address_phone);
            vh.tv_detail_address = (TextView) convertView.findViewById(R.id.tv_detail_address);
            vh.ll_select_nomal_address = (LinearLayout) convertView.findViewById(R.id.ll_select_nomal_address);
            vh.ll_editor_address = (LinearLayout) convertView.findViewById(R.id.ll_editor_address);
            vh.ll_delete_address = (LinearLayout) convertView.findViewById(R.id.ll_delete_address);
            vh.iv_default = (ImageView) convertView.findViewById(R.id.iv_item_select_default_address);
            vh.tv_nomal_address = (TextView) convertView.findViewById(R.id.tv_nomal_address);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        final GetUserAddress.DatasBean.AddressLestBean addressBean = mDataList.get(position);
        vh.tv_detail_address.setText(addressBean.getDetail());
        vh.tv_shouhuo_address_name.setText(addressBean.getName());
        vh.address_phone.setText(addressBean.getPhone());
        if (addressBean.isIfDefault()) {
            vh.iv_default.setBackgroundResource(R.mipmap.dh);
            vh.tv_nomal_address.setText("默认地址");
//            vh.tv_nomal_address.setTextColor(context.getResources().getColor(R.color.title_bar));
        } else {
            vh.iv_default.setBackgroundResource(R.mipmap.bty);
            vh.tv_nomal_address.setText("设为默认");
//            vh.tv_nomal_address.setTextColor(context.getResources().getColor(R.color.black));
        }
        //编辑地址
        vh.ll_editor_address.setOnClickListener(this);

        vh.ll_delete_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context).setTitle("确认").setMessage("确定删除吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
                                hashMap.put("addressId", String.valueOf(mDataList.get(position).getId()));
                                HttpHelper.getInstance().post(Url.deleteUserAddress, hashMap, new RawResponseHandler() {
                                    @Override
                                    public void onSuccess(int statusCode, String response) {
                                        final SendCodeBean userAddress = JsonUtils.parse(response, SendCodeBean.class);
                                        if (userAddress.getCode()==1) {
                                            removeOne(mDataList.get(position));
                                            EventBus.getDefault().post(new LoginSuccess());
                                            Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    @Override
                                    public void onFailure(int statusCode, String error_msg) {

                                    }
                                });

                            }
                        })
                        .setNegativeButton("取消", null).show();
            }
        });
        //选择默认地址
        vh.ll_select_nomal_address.setOnClickListener(this);
        vh.ll_select_nomal_address.setTag(position);
//        vh.ll_select_nomal_address.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                oldPosition = position;
//                HashMap<String, String> hashMap = new HashMap<>();
//                hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
//                hashMap.put("addressId", String.valueOf(mDataList.get(position).getId()));
//                HttpHelper.getInstance().post(Url.userAddressToBeDefault, hashMap, new RawResponseHandler() {
//                    @Override
//                    public void onSuccess(int statusCode, String response) {
//                        final SendCodeBean userAddress = JsonUtils.parse(response, SendCodeBean.class);
//                        if (userAddress.getCode()==1) {
//                            vh.iv_default.setBackgroundResource(R.mipmap.dh);
//                            vh.tv_nomal_address.setText("默认地址");
////                            notifyDataSetChanged();
//                            EventBus.getDefault().post(new LoginSuccess());
//
//                        }
//                    }
//                    @Override
//                    public void onFailure(int statusCode, String error_msg) {
//
//                    }
//                });
//
//            }
//        });
        if (oldPosition == position) {
            vh.iv_default.setBackgroundResource(R.mipmap.dh);
            vh.tv_nomal_address.setText("默认地址");
        } else {
            vh.iv_default.setBackgroundResource(R.mipmap.bty);
            vh.tv_nomal_address.setText("设为默认");
        }
        return convertView;
    }

    @Override
    public void onClick(View v) {
        mCallback.click(v);
    }


    class ViewHolder {
        public TextView tv_shouhuo_address_name;//收货人
        public TextView address_phone;//收货人手机号
        public TextView tv_detail_address;//详细地址
        public LinearLayout ll_select_nomal_address;//选择默认地址
        public LinearLayout ll_editor_address;//编辑地址
        public LinearLayout ll_delete_address;//删除地址
        public ImageView iv_default;
        public TextView tv_nomal_address;//默认地址
    }
}
