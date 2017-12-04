package com.example.ddm.appui.mine;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.example.ddm.LoginActivity;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.bean.IfCodeBean;
import com.example.ddm.appui.bean.SendCodeBean;
import com.example.ddm.appui.bean.VerifyCodeBean;
import com.example.ddm.appui.bean.eventbus.LoginSuccess;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.utils.CountDownTimerUtils;
import com.example.ddm.appui.utils.DisplayUtils;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.UUID;

import cn.jpush.android.api.JPushInterface;

/**
 * 修改手机号的界面
 */
public class PhoneNumFragment extends BaseFragment {
    private TextView mBack;
    private TextView mGetCode1,mGetCode2;
    private EditText mTextCode1,mTextPhone, mTextCode2;
    private String mPhone,mCode1,mCode2;
    private Button mSure;
    public PhoneNumFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phone_num, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mGetCode1 = mFindViewUtils.findViewById(R.id.get_verify_code);
        mGetCode2 = mFindViewUtils.findViewById(R.id.get_verify_code1);
        mBack = mFindViewUtils.findViewById(R.id.app_title_back);
        mTextCode1 = mFindViewUtils.findViewById(R.id.edit_code);
        mTextPhone = mFindViewUtils.findViewById(R.id.eidt_password);
        mTextCode2 = mFindViewUtils.findViewById(R.id.edit_code1);
        mSure = mFindViewUtils.findViewById(R.id.registerInBtn);
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
        mGetCode1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCode1();
            }
        });
        mGetCode2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getCode2();
                if (isNull1()) {
                    CheckPhone();
                }

            }
        });
        mSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNull()) {
                        Change();
                }
            }
        });
    }
    //得到旧手机验证码
    private void getCode1(){
        showLoading("正在获取旧手机验证码");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        hashMap.put(IAppKey.PLATFORM, "android");
        HttpHelper.getInstance().post(Url.Send_Code, hashMap, new RawResponseHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                hiddenLoading();
                final SendCodeBean mSendCodeBean= JsonUtils.parse(response, SendCodeBean.class);
                if (mSendCodeBean.getCode()==1) {
                    Toast.makeText(getContext(),"验证码已发送",Toast.LENGTH_SHORT).show();
                    CountDownTimerUtils utils = new CountDownTimerUtils(mGetCode1, 60000, 1000);
                    utils.start();
                } else if (mSendCodeBean.getCode() == 0) {

                    Toast.makeText(getContext(), "验证码发送失败", Toast.LENGTH_SHORT).show();
                } else {
                    StringUtils.showToast(getActivity(),mSendCodeBean.getMsg());
                    PreferenceManager.instance().removeToken();
                    mBaseActivity.showActivity(LoginActivity.class,null);
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                hiddenLoading();
            }
        });
    }
    //得到新手机验证码
    private void getCode2(){
        showLoading("正在获取新手机验证码");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.PHONENUM, mPhone);
        hashMap.put(IAppKey.PLATFORM, "android");
        HttpHelper.getInstance().post(Url.Send_Code, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final SendCodeBean mSendCodeBean= JsonUtils.parse(response, SendCodeBean.class);
                if (mSendCodeBean.getCode()==1) {
                    hiddenLoading();
                    Toast.makeText(getContext(),"验证码已发送",Toast.LENGTH_SHORT).show();
                } else if (mSendCodeBean.getCode() == 0) {
                    hiddenLoading();
                    Toast.makeText(getContext(), "验证码发送失败", Toast.LENGTH_SHORT).show();
                } else {
                    hiddenLoading();
                    StringUtils.showToast(getActivity(),mSendCodeBean.getMsg());
                    PreferenceManager.instance().removeToken();
                    mBaseActivity.showActivity(LoginActivity.class,null);
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                hiddenLoading();
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
                                    CountDownTimerUtils utils = new CountDownTimerUtils(mGetCode2, 60000, 1000);
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
                                                                            CountDownTimerUtils utils = new CountDownTimerUtils(mGetCode2, 60000, 1000);
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
                                                                CountDownTimerUtils utils = new CountDownTimerUtils(mGetCode2, 60000, 1000);
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
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                StringUtils.showToast(getActivity(), error_msg);
            }
        });
    }
    private boolean isNull(){
        mPhone = mTextPhone.getText().toString().trim();
        mCode1 = mTextCode1.getText().toString().trim();
        mCode2 = mTextCode2.getText().toString().trim();
        if (TextUtils.isEmpty(mPhone)) {
            Toast.makeText(getContext(), "手机号不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(mCode1)) {
            Toast.makeText(getContext(), "验证码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }else if (TextUtils.isEmpty(mCode2)) {
            Toast.makeText(getContext(), "验证码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (StringUtils.isMobileNO(mPhone)) {
            return true;
        } else {

            Toast.makeText(getContext(), "手机号不合法", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    private boolean isNull1(){
        mPhone = mTextPhone.getText().toString().trim();
        mCode1 = mTextCode1.getText().toString().trim();
        if (TextUtils.isEmpty(mPhone)) {
            Toast.makeText(getContext(), "手机号不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(mCode1)) {
            Toast.makeText(getContext(), "验证码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }else if (StringUtils.isMobileNO(mPhone)) {
            return true;
        } else {

            Toast.makeText(getContext(), "手机号不合法", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    //修改手机号
    private void Change(){
        showLoading();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.MOBILEOLD, PreferenceManager.instance().getPhoneNum());
        hashMap.put(IAppKey.CODE1, mCode1);
        hashMap.put(IAppKey.MOBILENEW, mPhone);
        hashMap.put(IAppKey.CODE2, mCode2);
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        hashMap.put(IAppKey.PLATFORM, "android");
        HttpHelper.getInstance().post(Url.CHANGEPHONE, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                hiddenLoading();
                final SendCodeBean bean = JsonUtils.parse(response, SendCodeBean.class);
                if (bean.getCode()==1) {
                    if (!JPushInterface.isPushStopped(getContext())) {
                        JPushInterface.stopPush(getContext());
                    }
                    PreferenceManager.instance().removeToken();
                    mBaseActivity.showActivity(LoginActivity.class,null);
                } else if (bean.getCode() == 0) {
                    StringUtils.showToast(getActivity(), bean.getMsg());
                } else {
                    StringUtils.showToast(getActivity(),bean.getMsg());
                    PreferenceManager.instance().removeToken();
                    mBaseActivity.showActivity(LoginActivity.class,null);
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                hiddenLoading();
              StringUtils.showToast(getActivity(),error_msg);
            }
        });
    }
}
