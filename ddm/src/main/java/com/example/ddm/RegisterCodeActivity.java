package com.example.ddm;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.example.ddm.application.PokonyanApplication;
import com.example.ddm.appui.BaseActivity;
import com.example.ddm.appui.bean.CodeLogin;
import com.example.ddm.appui.bean.IfCodeBean;
import com.example.ddm.appui.bean.InviteBean;
import com.example.ddm.appui.bean.SendCodeBean;
import com.example.ddm.appui.bean.VerifyCodeBean;
import com.example.ddm.appui.constant.FragmentTag;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.fragment.DealFragment;
import com.example.ddm.appui.utils.CountDownTimerUtils;
import com.example.ddm.appui.utils.DisplayUtils;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.ShowFragmentUtils;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;


/**
 * 邀请码注册
 */
public class RegisterCodeActivity extends BaseActivity {
    private EditText mEditInvitationCode,mEditPhone, mEditPwd;
    private TextView mGetCode,mBack,mDetail;
    private String mPhone,mInvitationCode,mCode,Id;
    private Button mButton;
    private CheckBox mAgree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_code);
        PokonyanApplication.getInstance().addActivity(this);
    }
    @Override
    protected void initView() {
        super.initView();
        mDetail = (TextView) findViewById(R.id.deal);
        mAgree = (CheckBox) findViewById(R.id.checkbox);
        mBack = (TextView) findViewById(R.id.app_title_back);
        mGetCode = (TextView) findViewById(R.id.get_verify_code);
        mEditInvitationCode = (EditText) findViewById(R.id.Invitation_code);
        mEditPhone = (EditText) findViewById(R.id.phoneNumEdit);
        mEditPwd = (EditText) findViewById(R.id.edit_code);
        mButton = (Button) findViewById(R.id.loginInBtn);
    }
    @Override
    protected void setListener() {
        super.setListener();
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
        mEditPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0&&isNullPhone()) {
                    mButton.setEnabled(true);
                    mButton.setBackgroundResource(R.drawable.def_green_bom);
                    if (isNull()&&isAgree()) {
                        mButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                checkCode(Id, mCode);
                            }
                        });
                    }
                } else {
                    mButton.setEnabled(false);
                    mButton.setBackgroundResource(R.drawable.def_green_top);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentUtils.showFragment(RegisterCodeActivity.this,DealFragment.class, FragmentTag.DEALFRAGMENT,null,true);
            }
        });

    }
    @Override
    protected void setData() {
        super.setData();
        Intent intent = getIntent();
        LogUtils.d("wode"+intent.getStringExtra("Invitation_code"));
        Id = intent.getStringExtra("Invitation_code");
        if (!TextUtils.isEmpty(Id)) {
            mEditInvitationCode.setText(Id);
        }
    }
    private boolean isNullPhone(){
        mPhone = mEditPhone.getText().toString().trim();
        mInvitationCode = mEditInvitationCode.getText().toString().trim();
        mCode = mEditPwd.getText().toString().trim();
        if (TextUtils.isEmpty(mPhone)) {
            Toast.makeText(RegisterCodeActivity.this, "手机号不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (StringUtils.isMobileNO(mPhone)) {
            return true;
        } else {
            Toast.makeText(RegisterCodeActivity.this, "输入的手机号格式不正确!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    private boolean isNull(){
        mPhone = mEditPhone.getText().toString().trim();
        mInvitationCode = mEditInvitationCode.getText().toString().trim();
        mCode = mEditPwd.getText().toString().trim();
        if (TextUtils.isEmpty(mPhone)) {
            Toast.makeText(RegisterCodeActivity.this, "手机号不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(mInvitationCode)) {
            Toast.makeText(RegisterCodeActivity.this, "邀请码不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(mCode)) {
            Toast.makeText(RegisterCodeActivity.this, "验证码不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (StringUtils.isMobileNO(mPhone)) {
            return true;
        } else {
            Toast.makeText(RegisterCodeActivity.this, "输入的手机号格式不正确!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    private boolean isAgree(){
        if (mAgree.isChecked()) {
            return true;
        } else {
            Toast.makeText(RegisterCodeActivity.this, "请同意协议", Toast.LENGTH_SHORT).show();
            return false;
        }
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
                                    final Dialog dialog = new Dialog(RegisterCodeActivity.this, R.style.translate_dialog);
                                    View contentView = getLayoutInflater().inflate(R.layout.dialog_get_code, null);
                                    TextView tvTitle = (TextView) contentView.findViewById(R.id.tv_dialog_title);
                                    final EditText editText = (EditText) contentView.findViewById(R.id.tv_dialog_content);
                                    final ImageView imageView = (ImageView) contentView.findViewById(R.id.code_pic);
                                    ImageView back = (ImageView) contentView.findViewById(R.id.back);
                                    ImageView refresh = (ImageView) contentView.findViewById(R.id.refresh_pic);
                                    tvTitle.setText("请输入图片验证码");
                                    Glide.with(RegisterCodeActivity.this).load(Url.validateCode+"mobile="+mPhone).signature(new StringSignature(UUID.randomUUID().toString())) .placeholder(R.drawable.ic_launcher).into(imageView);
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
                                                                            StringUtils.showToast(RegisterCodeActivity.this, mSendCodeBean.getMsg());
                                                                            Glide.with(RegisterCodeActivity.this).load(Url.validateCode+"mobile="+mPhone).signature(new StringSignature(UUID.randomUUID().toString())) .placeholder(R.drawable.ic_launcher).into(imageView);
                                                                        }
                                                                    }
                                                                    @Override
                                                                    public void onFailure(int statusCode, String error_msg) {
                                                                        StringUtils.showToast(RegisterCodeActivity.this, error_msg);
                                                                    }
                                                                });
                                                            } else {
                                                                StringUtils.showToast(RegisterCodeActivity.this,"图形验证失败，请重新输入");
                                                                Glide.with(RegisterCodeActivity.this).load(Url.validateCode+"mobile="+mPhone).signature(new StringSignature(UUID.randomUUID().toString())) .placeholder(R.drawable.ic_launcher).into(imageView);
                                                            }
                                                        }
                                                        if (verifyCodeBean.getCode()==0) {
                                                            StringUtils.showToast(RegisterCodeActivity.this,verifyCodeBean.getMsg());
                                                            Glide.with(RegisterCodeActivity.this).load(Url.validateCode+"mobile="+mPhone).signature(new StringSignature(UUID.randomUUID().toString())) .placeholder(R.drawable.ic_launcher).into(imageView);
                                                        }
                                                    }
                                                    @Override
                                                    public void onFailure(int statusCode, String error_msg) {
                                                        StringUtils.showToast(RegisterCodeActivity.this,error_msg);
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
                                            Glide.with(RegisterCodeActivity.this).load(Url.validateCode+"mobile="+mPhone).signature(new StringSignature(UUID.randomUUID().toString())) .placeholder(R.drawable.ic_launcher).into(imageView);
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
                                StringUtils.showToast(RegisterCodeActivity.this, error_msg);
                            }
                        });
                    } else {
                        final Dialog dialog = new Dialog(RegisterCodeActivity.this, R.style.translate_dialog);
                        View contentView = getLayoutInflater().inflate(R.layout.dialog_get_code, null);
                        TextView tvTitle = (TextView) contentView.findViewById(R.id.tv_dialog_title);
                        final EditText editText = (EditText) contentView.findViewById(R.id.tv_dialog_content);
                        final ImageView imageView = (ImageView) contentView.findViewById(R.id.code_pic);
                        ImageView back = (ImageView) contentView.findViewById(R.id.back);
                        ImageView refresh = (ImageView) contentView.findViewById(R.id.refresh_pic);
                        tvTitle.setText("请输入图片验证码");
                        Glide.with(RegisterCodeActivity.this).load(Url.validateCode+"mobile="+mPhone).signature(new StringSignature(UUID.randomUUID().toString())) .placeholder(R.drawable.ic_launcher).into(imageView);
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
                                                                StringUtils.showToast(RegisterCodeActivity.this,mSendCodeBean.getMsg());
                                                                Glide.with(RegisterCodeActivity.this).load(Url.validateCode+"mobile="+mPhone).signature(new StringSignature(UUID.randomUUID().toString())) .placeholder(R.drawable.ic_launcher).into(imageView);
                                                            }
                                                        }
                                                        @Override
                                                        public void onFailure(int statusCode, String error_msg) {
                                                            StringUtils.showToast(RegisterCodeActivity.this, error_msg);
                                                        }
                                                    });
                                                } else {
                                                    StringUtils.showToast(RegisterCodeActivity.this,"图形验证失败，请重新输入");
                                                    Glide.with(RegisterCodeActivity.this).load(Url.validateCode+"mobile="+mPhone).signature(new StringSignature(UUID.randomUUID().toString())) .placeholder(R.drawable.ic_launcher).into(imageView);
                                                }
                                            }
                                            if (verifyCodeBean.getCode()==0) {
                                                StringUtils.showToast(RegisterCodeActivity.this,verifyCodeBean.getMsg());
                                                Glide.with(RegisterCodeActivity.this).load(Url.validateCode+"mobile="+mPhone).signature(new StringSignature(UUID.randomUUID().toString())) .placeholder(R.drawable.ic_launcher).into(imageView);
                                            }
                                        }
                                        @Override
                                        public void onFailure(int statusCode, String error_msg) {
                                            StringUtils.showToast(RegisterCodeActivity.this,error_msg);
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
                                Glide.with(RegisterCodeActivity.this).load(Url.validateCode+"mobile="+mPhone).signature(new StringSignature(UUID.randomUUID().toString())) .placeholder(R.drawable.ic_launcher).into(imageView);
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
                StringUtils.showToast(RegisterCodeActivity.this, error_msg);
            }
        });
    }

    //得到验证码
    private void getCode(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.PHONENUM, mPhone);
        hashMap.put(IAppKey.PLATFORM, "android");
        HttpHelper.getInstance().post(Url.SENDMOBILECODE, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final SendCodeBean mSendCodeBean= JsonUtils.parse(response, SendCodeBean.class);
                if (mSendCodeBean.getCode()==1) {
                            Toast.makeText(RegisterCodeActivity.this,"验证码已发送",Toast.LENGTH_SHORT).show();
                } else if (mSendCodeBean.getCode() == 0) {
                    Toast.makeText(RegisterCodeActivity.this, mSendCodeBean.getMsg(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterCodeActivity.this, "不能重复注册，请登录", Toast.LENGTH_SHORT).show();
                    showActivity(LoginActivity.class,null);
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                Toast.makeText(RegisterCodeActivity.this, error_msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
    //验证验证码
    private void checkCode(String id,String code){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.PHONENUM, mPhone);
        hashMap.put(IAppKey.PLATFORM, "android");
        hashMap.put("id", id);
        hashMap.put(IAppKey.MOBILE_CODE,code);
        HttpHelper.getInstance().post(Url.CHECKMOBILECODE, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final InviteBean mSendCodeBean= JsonUtils.parse(response, InviteBean.class);
                if (mSendCodeBean.getCode()==1) {
                    final Dialog dialog = new Dialog(RegisterCodeActivity.this, R.style.translate_dialog);
                    View contentView = getLayoutInflater().inflate(R.layout.dialog_change_city, null);
                    TextView tvTitle = (TextView) contentView.findViewById(R.id.tv_dialog_title);
                    TextView tvContent = (TextView) contentView.findViewById(R.id.tv_dialog_content);
                    Button btnCancel = (Button) contentView.findViewById(R.id.btn_dialog_cancel);
                    Button btnOk = (Button) contentView.findViewById(R.id.btn_dialog_ok);
                    tvTitle.setText("请核对信息");
                    tvContent.setText("推荐人："+mSendCodeBean.getDatas().getUpperName()+"手机号:"+mSendCodeBean.getDatas().getUpperPhone());
                    btnCancel.setText("返回");
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            finish();
                        }
                    });
                    btnOk.setText("继续");
                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            Register(Id);
                        }
                    });
                    dialog.setContentView(contentView);
                    Window window = dialog.getWindow();
                    WindowManager.LayoutParams params = window.getAttributes();
                    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    params.width = DisplayUtils.getWidthPx()/4*3;
                    window.setAttributes(params);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                } else if (mSendCodeBean.getCode() == 0) {
                    Toast.makeText(RegisterCodeActivity.this, mSendCodeBean.getMsg(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterCodeActivity.this, "不能重复注册，请登录", Toast.LENGTH_SHORT).show();
                    showActivity(LoginActivity.class,null);
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                Toast.makeText(RegisterCodeActivity.this, error_msg, Toast.LENGTH_SHORT).show();

            }
        });
    }
    //注册
    private void Register(String id){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.PHONENUM, mPhone);
        hashMap.put("id", id);
        HttpHelper.getInstance().post(Url.REGISTER, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final CodeLogin mSendCodeBean= JsonUtils.parse(response, CodeLogin.class);
                if (mSendCodeBean.getCode()==1) {
                     /*开启别名推送设置*/
                    if (JPushInterface.isPushStopped(RegisterCodeActivity.this)) {
                        JPushInterface.resumePush(RegisterCodeActivity.this);
                    }
                    /*推送并设置别名*/
                    JPushInterface.setAlias(RegisterCodeActivity.this, mPhone, new TagAliasCallback() {
                        @Override
                        public void gotResult(int i, String s, Set<String> set) {
                            if (!(i==0)) {
                                Toast.makeText(RegisterCodeActivity.this, "设置别名失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    PreferenceManager.instance().saveUserId(String.valueOf(mSendCodeBean.getDatas().getUserTypeValue()));//1,2
                    LogUtils.d("用户等级"+mSendCodeBean.getDatas().getUserTypeValue());
                    PreferenceManager.instance().saveUserIdName(mSendCodeBean.getDatas().getUserType());//普通用户
                    PreferenceManager.instance().saveName(mSendCodeBean.getDatas().getRealName());//姓名
                    PreferenceManager.instance().saveToken(mSendCodeBean.getDatas().getToken());//token
                    LogUtils.d("我的token"+mSendCodeBean.getDatas().getToken());
                    PreferenceManager.instance().savePhoneNum(mPhone);//手机号
                    Toast.makeText(RegisterCodeActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else if (mSendCodeBean.getCode() == 0) {
                    Toast.makeText(RegisterCodeActivity.this, mSendCodeBean.getMsg(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterCodeActivity.this, mSendCodeBean.getMsg(), Toast.LENGTH_SHORT).show();
                    showActivity(LoginActivity.class,null);
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                Toast.makeText(RegisterCodeActivity.this, error_msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
