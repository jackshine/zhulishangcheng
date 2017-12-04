package com.example.ddm.appui.order;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.ddm.ProvinceActivity;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.bean.SendCodeBean;
import com.example.ddm.appui.bean.eventbus.AddressTwo;
import com.example.ddm.appui.bean.eventbus.AddressTwoId;
import com.example.ddm.appui.bean.eventbus.LoginSuccess;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * 编辑用户地址
 */
public class AddressUpdateFragment extends BaseFragment {
    private EditText mEditTextName;
    private EditText mEditTextPhone;
    private EditText mEditTextAddress;
    private int check;
    private LinearLayout mLayout;
    private EditText mEditTextDetail;
    private CheckBox mCheckBox;
    private Button mButton;
    private String provinceId,cityId, townId;
    public AddressUpdateFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_address_update, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mLayout = mFindViewUtils.findViewById(R.id.ll_select_place);
        mEditTextName = mFindViewUtils.findViewById(R.id.et_shouhuo_name);
        mEditTextPhone = mFindViewUtils.findViewById(R.id.et_phone_address);
        mEditTextAddress = mFindViewUtils.findViewById(R.id.et_address_place);
        mEditTextDetail = mFindViewUtils.findViewById(R.id.et_detail_address);
        mCheckBox = mFindViewUtils.findViewById(R.id.iv_select_nomal_address);
        mButton = mFindViewUtils.findViewById(R.id.btn_submit_save_address);
    }
    @Subscribe
    public void getAddress(AddressTwo addresstwo) {
        LogUtils.d("收到地址::::::");
        mEditTextAddress.setText(addresstwo.getProvince()+addresstwo.getCity()+addresstwo.getTown());//接受地址消息
    }
    @Subscribe
    public void getaddressid(AddressTwoId addresstwoid) {
        LogUtils.d("收到地址::::::");
        provinceId = addresstwoid.getProvinceId();
        cityId = addresstwoid.getCityId();
        townId = addresstwoid.getTownId();
    }
    @Override
    protected void setListener() {
        super.setListener();
        if (mCheckBox.isChecked()) {
            check = 1;
        } else {
            check = 0;
        }
        mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBaseActivity.showActivity(ProvinceActivity.class,null);
            }
        });
        provinceId = mArguments.getString("provinceId");
        cityId = mArguments.getString("cityId");
        townId = mArguments.getString("countyId");
        mEditTextName.setText(mArguments.getString("name"));
        mEditTextPhone.setText(mArguments.getString("phone"));
        mEditTextDetail.setText(mArguments.getString("detail"));
        mEditTextAddress.setText(mArguments.getString("address"));
    }
    @Override
    protected void setData() {
        super.setData();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserAddress(mArguments.getString("addressId"),provinceId,cityId,townId,String.valueOf(check),mEditTextName.getText().toString(),mEditTextPhone.getText().toString(),mEditTextDetail.getText().toString());
            }
        });
    }
    private void addUserAddress(String addressId,String provinceId,String cityId,String townId,String ifDefault,String name,String phone,String detail){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        hashMap.put("addressId",addressId);
        hashMap.put("provinceId",provinceId);
        hashMap.put("cityId",cityId);
        hashMap.put("countyId",townId);
        hashMap.put("ifDefault",ifDefault);
        hashMap.put(IAppKey.NAME,name);
        hashMap.put("phone",phone);
        hashMap.put("detail",detail);
        HttpHelper.getInstance().post(Url.editUserAddress, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                SendCodeBean sendCodeBean = JsonUtils.parse(response, SendCodeBean.class);
                if (sendCodeBean.getCode()==1) {
                    EventBus.getDefault().post(new LoginSuccess());
                    StringUtils.showToast(getActivity(),"添加成功");
                    popSelf();
                }
                if (sendCodeBean.getCode()==0) {
                    StringUtils.showToast(getActivity(),sendCodeBean.getMsg());
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                StringUtils.showToast(getActivity(),error_msg);
            }
        });
    }
}
