package com.example.ddm.appui.mine.myhome;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ddm.LoginActivity;
import com.example.ddm.ProvinceActivity;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.bean.SendCodeBean;
import com.example.ddm.appui.bean.ShengFengBean;
import com.example.ddm.appui.bean.eventbus.Address;
import com.example.ddm.appui.bean.eventbus.AddressId;
import com.example.ddm.appui.bean.eventbus.Card;
import com.example.ddm.appui.bean.eventbus.UpdateCardList;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * 编辑地址
 */
public class CompileAddressFragment extends BaseFragment {
    private LinearLayout mLayout;
    private TextView mBack;
    private EditText mEditText1,mEditText2;
    private Button mKeep;
    private String provinceId,cityId, townId;
    public CompileAddressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compile_address, container, false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mBack = mFindViewUtils.findViewById(R.id.app_title_back);
        mLayout = mFindViewUtils.findViewById(R.id.ll_select_place);
        mEditText1 = mFindViewUtils.findViewById(R.id.et_address_place);
        mEditText2 = mFindViewUtils.findViewById(R.id.et_address_place_detail);
        mKeep = mFindViewUtils.findViewById(R.id.loginInBtn);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popSelf();
            }
        });
        mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBaseActivity.showActivity(ProvinceActivity.class,null);
            }
        });
        mKeep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNull()) {
                 addAddress(provinceId,cityId,townId);
                    popSelf();
                }

            }
        });
    }

    @Override
    protected void setData() {
        super.setData();
    }
    private void addAddress(String provinceId,String cityId,String countyId){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        hashMap.put("provinceId",provinceId );
        hashMap.put("cityId",cityId );
        hashMap.put("countyId",countyId );
        hashMap.put("detail",mEditText2.getText().toString().trim());
        HttpHelper.getInstance().post(Url.Add_Address, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final SendCodeBean bean = JsonUtils.parse(response, SendCodeBean.class);
                if (bean.getCode() == 1) {
                    EventBus.getDefault().post(new UpdateCardList("添加地址"));
                } else if (bean.getCode() == 0) {
                    Toast.makeText(getContext(), bean.getMsg(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), bean.getMsg(), Toast.LENGTH_SHORT).show();
                    PreferenceManager.instance().removeToken();
                    mBaseActivity.showActivity(LoginActivity.class,null);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {

            }
        });
    }
    @Subscribe
    public void getaddress(Address card) {
        LogUtils.d("收到地址::::::");
       mEditText1.setText(card.getProvince()+card.getCity()+card.getTown());//接受身份证消息
    }
    @Subscribe
    public void getaddressid(AddressId addressid) {
        LogUtils.d("收到地址::::::");
        provinceId = addressid.getProvinceId();
        cityId = addressid.getCityId();
        townId = addressid.getTownId();
    }
    private boolean isNull(){
//        if (!TextUtils.isEmpty(PreferenceManager.instance().getProvince()) && !TextUtils.isEmpty(PreferenceManager.instance().getCity()) && !TextUtils.isEmpty(PreferenceManager.instance().getTown())) {
//            mEditText1.setText(PreferenceManager.instance().getProvince() + PreferenceManager.instance().getCity() + PreferenceManager.instance().getTown());
//        } else {
//            mEditText1.setText("");
//
//        }
        if (TextUtils.isEmpty(mEditText1.getText().toString().trim())) {
            Toast.makeText(getContext(), "所在地区不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}
