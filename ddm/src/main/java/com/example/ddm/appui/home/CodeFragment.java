package com.example.ddm.appui.home;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.example.ddm.LoginActivity;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.bean.CodeBean;
import com.example.ddm.appui.bean.RecordBean;
import com.example.ddm.appui.bean.UserTypeBean;
import com.example.ddm.appui.constant.FragmentTag;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.ShowFragmentUtils;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;
import java.util.HashMap;
import java.util.UUID;
/**
 * A simple {@link Fragment} subclass.
 * 我的二维码
 */
public class CodeFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener{
    private ImageView mImageView;
    private TextView mBack,mRecord,mTransfer;
    private RadioButton mSelf, mMerchant;
    private RadioGroup mRadioGroup;
    private String mType;
    public CodeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_code, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mRadioGroup = mFindViewUtils.findViewById(R.id.radio_group);
        mRadioGroup.setOnCheckedChangeListener(this);
        onCheckedChanged(mRadioGroup, R.id.self);
        mTransfer = mFindViewUtils.findViewById(R.id.txt_zz);
        mRecord = mFindViewUtils.findViewById(R.id.txt_sp);
        mImageView = mFindViewUtils.findViewById(R.id.pic);
        mBack = mFindViewUtils.findViewById(R.id.app_title_back);
        mSelf = mFindViewUtils.findViewById(R.id.self);
        mMerchant = mFindViewUtils.findViewById(R.id.merchant);
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
        mRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentUtils.showFragment(getActivity(),TransferRecordFragment.class, FragmentTag.TRANSFERRECORDFRAGMENT,null,true);
            }
        });
        mTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentUtils.showFragment(getActivity(),CollectionRecordFragment.class, FragmentTag.COLLECTIONRECORDFRAGMENT,null,true);
            }
        });
    }
    @Override
    protected void setData() {
        super.setData();
        GetId();
    }
    /*子和普通*/
    private void GetCode(String payee){
        showLoading();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        hashMap.put("payee", payee);
        HttpHelper.getInstance().post(Url.GETQRCODE, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                hiddenLoading();
                CodeBean bean = JsonUtils.parse(response, CodeBean.class);
                LogUtils.d("我的二维码"+response);
                if (bean.getCode()==1) {
//                    这里要重新加载图片.signature(new StringSignature(UUID.randomUUID().toString()))
                    Glide.with(getContext()).load(Url.MYCODE+String.valueOf(bean.getDatas().getId())+".jpg").signature(new StringSignature(UUID.randomUUID().toString())) .placeholder(R.drawable.ic_launcher).into(mImageView);
                } else if (bean.getCode()==0) {
                    StringUtils.showToast(getActivity(),bean.getMsg());
                }
//                else if (bean.getCode()==-1) {
//                    StringUtils.showToast(getActivity(),bean.getMsg());
//                    PreferenceManager.instance().removeToken();
//                    mBaseActivity.showActivity(LoginActivity.class,null);
//                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                hiddenLoading();
                Toast.makeText(mBaseActivity, "请检查网络", Toast.LENGTH_SHORT).show();
            }
        });
    }
    /*主*/
    private void GetCode2(String payee){
        showLoading();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        hashMap.put("payee", payee);
        HttpHelper.getInstance().post(Url.GETQRCODE, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                hiddenLoading();
                CodeBean bean = JsonUtils.parse(response, CodeBean.class);
                LogUtils.d("我的二维码"+response);
                if (bean.getCode()==1) {
//                    这里要重新加载图片.signature(new StringSignature(UUID.randomUUID().toString()))
                    Glide.with(getContext()).load(Url.MYCODE+String.valueOf(bean.getDatas().getId())+".jpg?childId="+bean.getDatas().getChildId()).signature(new StringSignature(UUID.randomUUID().toString())) .placeholder(R.drawable.ic_launcher).into(mImageView);
                } else if (bean.getCode()==0) {
                    StringUtils.showToast(getActivity(),bean.getMsg());
                }
//                else if (bean.getCode()==-1) {
//                    StringUtils.showToast(getActivity(),bean.getMsg());
//                    PreferenceManager.instance().removeToken();
//                    mBaseActivity.showActivity(LoginActivity.class,null);
//                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                hiddenLoading();
                Toast.makeText(mBaseActivity, "请检查网络", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void GetId(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        HttpHelper.getInstance().post(Url.GETUSERTYPE, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                UserTypeBean bean = JsonUtils.parse(response, UserTypeBean.class);
                if (bean.getCode()==1) {
                    if (bean.getDatas().getUserType() == 1) {
//                        mSelf.setVisibility(View.GONE);
//                        mMerchant.setVisibility(View.GONE);
                        mRadioGroup.setVisibility(View.GONE);
                        GetCode("self");
                    } else if (bean.getDatas().getUserType()==2) {
//                        mSelf.setVisibility(View.GONE);
//                        mMerchant.setVisibility(View.GONE);
                        mRadioGroup.setVisibility(View.GONE);
                        GetCode2("merchant");
                    } else if (bean.getDatas().getUserType()==5) {
//                        mSelf.setVisibility(View.VISIBLE);
//                        mMerchant.setVisibility(View.VISIBLE);
                        mRadioGroup.setVisibility(View.VISIBLE);
                        GetCode("self");
                    }
                } else if (bean.getCode()==0) {
                    StringUtils.showToast(getActivity(), bean.getMsg());
                } else if (bean.getCode() == -1) {
                        StringUtils.showToast(getActivity(), bean.getMsg());
                        PreferenceManager.instance().removeToken();
                        mBaseActivity.showActivity(LoginActivity.class, null);
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
            }
        });
    }
    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId){
            case R.id.self:
                GetCode("self");
                break;
            case R.id.merchant:
                GetCode2("merchant");
                break;
        }
    }
}
