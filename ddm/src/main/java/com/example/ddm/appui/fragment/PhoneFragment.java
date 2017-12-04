package com.example.ddm.appui.fragment;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.example.ddm.LocationActivity;
import com.example.ddm.LoginActivity;
import com.example.ddm.MainActivity;
import com.example.ddm.R;
import com.example.ddm.RegisterActivity;
import com.example.ddm.application.PokonyanApplication;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.animation.MyAnimation;
import com.example.ddm.appui.bean.CheckCodeBean;
import com.example.ddm.appui.bean.CodeLogin;
import com.example.ddm.appui.bean.GetKeyBean;
import com.example.ddm.appui.bean.GetRegisterRedPacket;
import com.example.ddm.appui.bean.IfCodeBean;
import com.example.ddm.appui.bean.PersonBean;
import com.example.ddm.appui.bean.SendCodeBean;
import com.example.ddm.appui.bean.VerifyCodeBean;
import com.example.ddm.appui.bean.eventbus.LoginSuccess;
import com.example.ddm.appui.constant.FragmentTag;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.home.HongBaoFragment;
import com.example.ddm.appui.utils.CountDownTimerUtils;
import com.example.ddm.appui.utils.DisplayUtils;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.ShowFragmentUtils;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * A simple {@link Fragment} subclass.
 * 手机号登录
 */
public class PhoneFragment extends BaseFragment {
    private EditText mEditTextPhone,mEditTextCode;
    private TextView mForget,mGetCode;
    private Button mLogin;
    private TextView mDeal;
    private CheckBox mAgree;
    private String mPhone, mCode,mCodePic;
    public PhoneFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phone, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mDeal = mFindViewUtils.findViewById(R.id.deal);
        mAgree = mFindViewUtils.findViewById(R.id.checkbox);
        mForget = mFindViewUtils.findViewById(R.id.forget);
        mGetCode = mFindViewUtils.findViewById(R.id.get_verify_code);
        mEditTextPhone = mFindViewUtils.findViewById(R.id.phoneNumEdit);
        mEditTextCode = mFindViewUtils.findViewById(R.id.edit_code);
        mLogin = mFindViewUtils.findViewById(R.id.registerInBtn);
    }
    @Override
    protected void setListener() {
        super.setListener();
        mDeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentUtils.showFragment(getActivity(),DealFragment.class, FragmentTag.DEALFRAGMENT,null,true);
            }
        });
        mGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNullPhone()&&Net()) {
                    CheckPhone();

                }
            }
        });
        mForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBaseActivity.showActivity(RegisterActivity.class,null);
            }
        });
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPhoneNumber()&&isAgree()&&Net()) {
                    login();
                }
            }
        });
    }
    @Override
    protected void setData() {
        super.setData();
        if (TextUtils.isEmpty(PreferenceManager.instance().getPhoneNum())) {
            mEditTextPhone.setText("");
        } else {
            mEditTextPhone.setText(PreferenceManager.instance().getPhoneNum());
        }
    }

    private boolean isNullPhone(){
        mPhone = mEditTextPhone.getText().toString().trim();
        if (TextUtils.isEmpty(mPhone)) {
            Toast.makeText(getActivity(), "手机号不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (StringUtils.isMobileNO(mPhone)) {
            return true;
        } else {
            Toast.makeText(getActivity(), "输入的手机号格式不正确!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    private boolean isPhoneNumber(){
        mPhone = mEditTextPhone.getText().toString().trim();
        mCode = mEditTextCode.getText().toString().trim();
        if (TextUtils.isEmpty(mPhone)) {
            Toast.makeText(getActivity(), "手机号不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(mCode)) {
            Toast.makeText(getActivity(), "验证码不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (StringUtils.isMobileNO(mPhone)) {
            return true;
        } else {
            Toast.makeText(getActivity(), "输入的手机号格式不正确!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    private boolean isAgree(){
        if (mAgree.isChecked()) {
            return true;
        } else {
            Toast.makeText(getActivity(), "请同意协议", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    //得到key
    private void getKey(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.NAME, mPhone);
        HttpHelper.getInstance().post(Url.GetKey, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final GetKeyBean mGetKeyBean = JsonUtils.parse(response, GetKeyBean.class);
                if (mGetKeyBean.getCode() == 1) {
                    PreferenceManager.instance().saveKey(mGetKeyBean.getDatas().getUserKey());
                } else {
                    Toast.makeText(getContext(),"key为空",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
            }
        });
    }
    /**
     * 检验手机号
     */
    private void CheckPhone(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.PHONENUM, mPhone);
        HttpHelper.getInstance().post(Url.IMAGEVERIFYC, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final IfCodeBean ifCodeBean = JsonUtils.parse(response, IfCodeBean.class);
                if (ifCodeBean.getCode()==1) {
                    if (ifCodeBean.isIfCode()) {
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put(IAppKey.PHONENUM, mPhone);
                        hashMap.put("validateCode", PreferenceManager.instance().getPwd());
                        hashMap.put(IAppKey.PLATFORM, "android");
                        HttpHelper.getInstance().post(Url.Send_Code1, hashMap, new RawResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, String response) {
                                final SendCodeBean mSendCodeBean = JsonUtils.parse(response, SendCodeBean.class);
                                if (mSendCodeBean.getCode() == 1) {
                                    CountDownTimerUtils utils = new CountDownTimerUtils(mGetCode, 60000, 1000);
                                    utils.start();
                                }
                                if (mSendCodeBean.getCode() == 0) {
                                    final Dialog dialog = new Dialog(getContext(), R.style.translate_dialog);
                                    View contentView = getActivity().getLayoutInflater().inflate(R.layout.dialog_get_code, null);
                                    TextView tvTitle = (TextView) contentView.findViewById(R.id.tv_dialog_title);
                                    final EditText editText = (EditText) contentView.findViewById(R.id.tv_dialog_content);
                                    final ImageView imageView = (ImageView) contentView.findViewById(R.id.code_pic);
                                    ImageView back = (ImageView) contentView.findViewById(R.id.back);
                                    ImageView refresh = (ImageView) contentView.findViewById(R.id.refresh_pic);
                                    tvTitle.setText("请输入图片验证码");
                                    Glide.with(getContext()).load(Url.validateCode+"mobile="+mPhone).signature(new StringSignature(UUID.randomUUID().toString())) .placeholder(R.drawable.ic_launcher).into(imageView);
                                    editText.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                        }
                                        @Override
                                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        }
                                        @Override
                                        public void afterTextChanged(final Editable s) {
                                            if (s.length()==5) {
                                                PreferenceManager.instance().savePwd(editText.getText().toString());
                                                LogUtils.d(editText.getText().toString());
                                                HashMap<String, String> hashMap = new HashMap<>();
                                                hashMap.put(IAppKey.PHONENUM, mPhone);
                                                hashMap.put("validateCode", editText.getText().toString());
                                                HttpHelper.getInstance().post(Url.verifyCode, hashMap, new RawResponseHandler() {
                                                    @Override
                                                    public void onSuccess(int statusCode, String response) {
                                                        final VerifyCodeBean verifyCodeBean= JsonUtils.parse(response, VerifyCodeBean.class);
                                                        if (verifyCodeBean.getCode()==1) {
                                                            if (verifyCodeBean.isVerifyCode()) {
                                                                HashMap<String, String> hashMap = new HashMap<>();
                                                                hashMap.put(IAppKey.PHONENUM, mPhone);
                                                                hashMap.put("validateCode",editText.getText().toString());
                                                                hashMap.put(IAppKey.PLATFORM, "android");
                                                                HttpHelper.getInstance().post(Url.Send_Code1, hashMap, new RawResponseHandler() {
                                                                    @Override
                                                                    public void onSuccess(int statusCode, String response) {
                                                                        final SendCodeBean mSendCodeBean = JsonUtils.parse(response, SendCodeBean.class);
                                                                        if (mSendCodeBean.getCode() == 1) {
                                                                            dialog.dismiss();
                                                                            CountDownTimerUtils utils = new CountDownTimerUtils(mGetCode, 60000, 1000);
                                                                            utils.start();
                                                                        }
                                                                        if (mSendCodeBean.getCode() == 0) {
                                                                            StringUtils.showToast(getActivity(), mSendCodeBean.getMsg());
                                                                            Glide.with(getContext()).load(Url.validateCode+"mobile="+mPhone).signature(new StringSignature(UUID.randomUUID().toString())) .placeholder(R.drawable.ic_launcher).into(imageView);
                                                                        }
                                                                    }
                                                                    @Override
                                                                    public void onFailure(int statusCode, String error_msg) {
                                                                        StringUtils.showToast(getActivity(), error_msg);
                                                                    }
                                                                });
                                                            } else {
                                                                StringUtils.showToast(getActivity(),"图形验证失败，请重新输入");
                                                                Glide.with(getContext()).load(Url.validateCode+"mobile="+mPhone).signature(new StringSignature(UUID.randomUUID().toString())) .placeholder(R.drawable.ic_launcher).into(imageView);
                                                            }
                                                        }
                                                        if (verifyCodeBean.getCode()==0) {
                                                            StringUtils.showToast(getActivity(),verifyCodeBean.getMsg());
                                                            Glide.with(getContext()).load(Url.validateCode+"mobile="+mPhone).signature(new StringSignature(UUID.randomUUID().toString())) .placeholder(R.drawable.ic_launcher).into(imageView);
                                                        }
                                                    }
                                                    @Override
                                                    public void onFailure(int statusCode, String error_msg) {
                                                        StringUtils.showToast(getActivity(),error_msg);
                                                    }
                                                });
                                            }
                                        }
                                    });
                                    back.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });
                                    refresh.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Glide.with(getContext()).load(Url.validateCode+"mobile="+mPhone).signature(new StringSignature(UUID.randomUUID().toString())) .placeholder(R.drawable.ic_launcher).into(imageView);
                                        }
                                    });
                                    dialog.setContentView(contentView);
                                    Window window = dialog.getWindow();
                                    WindowManager.LayoutParams params = window.getAttributes();
                                    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                                    params.width = DisplayUtils.getWidthPx();
                                    window.setAttributes(params);
                                    dialog.setCanceledOnTouchOutside(false);
                                    dialog.show();
                                }
                            }
                            @Override
                            public void onFailure(int statusCode, String error_msg) {
                                StringUtils.showToast(getActivity(), error_msg);
                            }
                        });
                    } else {
                        final Dialog dialog = new Dialog(getContext(), R.style.translate_dialog);
                        View contentView = getActivity().getLayoutInflater().inflate(R.layout.dialog_get_code, null);
                        TextView tvTitle = (TextView) contentView.findViewById(R.id.tv_dialog_title);
                        final EditText editText = (EditText) contentView.findViewById(R.id.tv_dialog_content);
                        final ImageView imageView = (ImageView) contentView.findViewById(R.id.code_pic);
                        ImageView back = (ImageView) contentView.findViewById(R.id.back);
                        ImageView refresh = (ImageView) contentView.findViewById(R.id.refresh_pic);
                        tvTitle.setText("请输入图片验证码");
                        Glide.with(getContext()).load(Url.validateCode+"mobile="+mPhone).signature(new StringSignature(UUID.randomUUID().toString())) .placeholder(R.drawable.ic_launcher).into(imageView);
                        editText.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }
                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                            }
                            @Override
                            public void afterTextChanged(final Editable s) {
                                if (s.length()==5) {
                                    PreferenceManager.instance().savePwd(editText.getText().toString());
                                    LogUtils.d(editText.getText().toString());
                                    HashMap<String, String> hashMap = new HashMap<>();
                                    hashMap.put(IAppKey.PHONENUM, mPhone);
                                    hashMap.put("validateCode", editText.getText().toString());
                                    HttpHelper.getInstance().post(Url.verifyCode, hashMap, new RawResponseHandler() {
                                        @Override
                                        public void onSuccess(int statusCode, String response) {
                                            final VerifyCodeBean verifyCodeBean= JsonUtils.parse(response, VerifyCodeBean.class);
                                            if (verifyCodeBean.getCode()==1) {
                                                if (verifyCodeBean.isVerifyCode()) {
                                                    HashMap<String, String> hashMap = new HashMap<>();
                                                    hashMap.put(IAppKey.PHONENUM, mPhone);
                                                    hashMap.put("validateCode",editText.getText().toString());
                                                    hashMap.put(IAppKey.PLATFORM, "android");
                                                    HttpHelper.getInstance().post(Url.Send_Code1, hashMap, new RawResponseHandler() {
                                                        @Override
                                                        public void onSuccess(int statusCode, String response) {
                                                            final SendCodeBean mSendCodeBean = JsonUtils.parse(response, SendCodeBean.class);
                                                            if (mSendCodeBean.getCode() == 1) {
                                                                dialog.dismiss();
                                                                CountDownTimerUtils utils = new CountDownTimerUtils(mGetCode, 60000, 1000);
                                                                utils.start();
                                                            }
                                                            if (mSendCodeBean.getCode() == 0) {
                                                                StringUtils.showToast(getActivity(),mSendCodeBean.getMsg());
                                                                Glide.with(getContext()).load(Url.validateCode+"mobile="+mPhone).signature(new StringSignature(UUID.randomUUID().toString())) .placeholder(R.drawable.ic_launcher).into(imageView);
                                                            }
                                                        }
                                                        @Override
                                                        public void onFailure(int statusCode, String error_msg) {
                                                            StringUtils.showToast(getActivity(), error_msg);
                                                        }
                                                    });
                                                } else {
                                                    StringUtils.showToast(getActivity(),"图形验证失败，请重新输入");
                                                    Glide.with(getContext()).load(Url.validateCode+"mobile="+mPhone).signature(new StringSignature(UUID.randomUUID().toString())) .placeholder(R.drawable.ic_launcher).into(imageView);
                                                }
                                            }
                                            if (verifyCodeBean.getCode()==0) {
                                                StringUtils.showToast(getActivity(),verifyCodeBean.getMsg());
                                                Glide.with(getContext()).load(Url.validateCode+"mobile="+mPhone).signature(new StringSignature(UUID.randomUUID().toString())) .placeholder(R.drawable.ic_launcher).into(imageView);
                                            }
                                        }
                                        @Override
                                        public void onFailure(int statusCode, String error_msg) {
                                            StringUtils.showToast(getActivity(),error_msg);
                                        }
                                    });
                                }
                            }
                        });
                        back.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        refresh.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Glide.with(getContext()).load(Url.validateCode+"mobile="+mPhone).signature(new StringSignature(UUID.randomUUID().toString())) .placeholder(R.drawable.ic_launcher).into(imageView);
                            }
                        });
                        dialog.setContentView(contentView);
                        Window window = dialog.getWindow();
                        WindowManager.LayoutParams params = window.getAttributes();
                        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                        params.width = DisplayUtils.getWidthPx();
                        window.setAttributes(params);
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.show();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                StringUtils.showToast(getActivity(), error_msg);
            }
        });
    }

    //通过登录
    private void login(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.USERNAME, mPhone);
        hashMap.put(IAppKey.MOBILE_CODE, mCode);
        hashMap.put(IAppKey.PLATFORM, "android");
        HttpHelper.getInstance().post(Url.Note_Login, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final CodeLogin mCodeLogin= JsonUtils.parse(response, CodeLogin.class);
                if (mCodeLogin.getCode()==1) {
                      /*开启别名推送设置*/
                    if (JPushInterface.isPushStopped(getContext())) {
                        JPushInterface.resumePush(getContext());
                    }
                    /*推送并设置别名*/
                    JPushInterface.setAlias(getContext(), mPhone, new TagAliasCallback() {
                        @Override
                        public void gotResult(int i, String s, Set<String> set) {
                            if (!(i==0)) {
                                Toast.makeText(getContext(), "设置别名失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    //保存用戶的級別
                    PreferenceManager.instance().saveUserId(String.valueOf(mCodeLogin.getDatas().getUserTypeValue()));
                    //普通用戶或者高級用戶
                    PreferenceManager.instance().saveUserIdName(String.valueOf(mCodeLogin.getDatas().getUserType()));
                    //保存token
                    PreferenceManager.instance().saveToken(mCodeLogin.getDatas().getToken());
                    //保持姓名
                    PreferenceManager.instance().saveName(mCodeLogin.getDatas().getRealName());
                    //保存手机号
                    PreferenceManager.instance().savePhoneNum(mPhone);
                    Toast.makeText(getContext(),"登录成功",Toast.LENGTH_SHORT).show();
                    getKey();
                    EventBus.getDefault().post(new LoginSuccess());
                    mBaseActivity.finish();
                }
                if (mCodeLogin.getCode()==0) {
                            Toast.makeText(getContext(),"验证码不正确，请重新获取",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                StringUtils.showToast(getActivity(), error_msg);
            }
        });
    }
}
