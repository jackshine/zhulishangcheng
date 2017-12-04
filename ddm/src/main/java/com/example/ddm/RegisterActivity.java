package com.example.ddm;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.example.ddm.application.PokonyanApplication;
import com.example.ddm.appui.BaseActivity;
import com.example.ddm.appui.bean.CodeLogin;
import com.example.ddm.appui.bean.GetKeyBean;
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
import com.example.ddm.appui.utils.MD5;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;
import org.greenrobot.eventbus.EventBus;
import java.util.HashMap;
import java.util.UUID;

public class RegisterActivity extends BaseActivity {
    private EditText mEditTextPhone,mEditTextCode,mEditTextPwd, mEditTextPwdSure;
    private TextView mLogin,mGetCode;
    private String mPhone,mCode,mPwd, mPwdSure;
    private Button mSure,mBtnAccountClear,mBtnPasswordClear,mBtnPasswordSureClear;
    private CheckBox mPwdEyes,mPwdEye;
    private TextWatcher mAccountWatcher;//账号输入框监听
    private TextWatcher mPasswordWatcher,mPasswordWatcherSure;//密码输入框的监听
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        PokonyanApplication.getInstance().addActivity(this);
    }
    @Override
    protected void initView() {
        super.initView();
        mBtnAccountClear = (Button) findViewById(R.id.account_clear);
        mBtnPasswordClear = (Button) findViewById(R.id.password_clear);
        mBtnPasswordSureClear = (Button) findViewById(R.id.password_clear_1);
        mBtnAccountClear.setOnClickListener(this);
        mBtnPasswordClear.setOnClickListener(this);
        mBtnPasswordSureClear.setOnClickListener(this);
        mGetCode = (TextView) findViewById(R.id.get_verify_code);
        mPwdEye = (CheckBox) findViewById(R.id.btn_eye_1);
        mPwdEyes = (CheckBox) findViewById(R.id.btn_eye);
        mSure = (Button) findViewById(R.id.registerInBtn);
        mLogin = (TextView) findViewById(R.id.login);
        mEditTextPhone = (EditText) findViewById(R.id.phoneNumEdit);
        mEditTextCode = (EditText) findViewById(R.id.edit_code);
        mEditTextPwd = (EditText) findViewById(R.id.eidt_password);
        mEditTextPwdSure = (EditText) findViewById(R.id.eidt_password_1);
        initWatcher();
        mEditTextPhone.addTextChangedListener(mAccountWatcher);
        mEditTextPwd.addTextChangedListener(mPasswordWatcher);
        mEditTextPwdSure.addTextChangedListener(mPasswordWatcherSure);
    }
    @Override
    protected void setListener() {
        super.setListener();
        isPasswordShowListener();
        isPasswordShowListenerSure();
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showActivity(LoginActivity.class,null);
            }
        });
        mSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPhoneNumber()) {
                    PreferenceManager.instance().savePhoneNum(mPhone);
                          getKey();
                }
            }
        });
        mGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNullPhone()&&Net()) {
                   CheckPhone();
//                    CountDownTimerUtils utils = new CountDownTimerUtils(mGetCode, 60000, 1000);
//                    utils.start();
                }
            }
        });
    }
    @Override
    protected void setData(){
        super.setData();
    }
    private boolean isNullPhone(){
        mPhone = mEditTextPhone.getText().toString().trim();
        if (TextUtils.isEmpty(mPhone)) {
            Toast.makeText(this, "手机号不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (StringUtils.isMobileNO(mPhone)) {
            return true;
        } else {
            Toast.makeText(this, "输入的手机号格式不正确!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    /*判断账号*/
    private boolean isPhoneNumber(){
        mPhone = mEditTextPhone.getText().toString().trim();
        mCode = mEditTextCode.getText().toString().trim();
        mPwd = mEditTextPwd.getText().toString().trim();
        mPwdSure = mEditTextPwdSure.getText().toString().trim();
        if (TextUtils.isEmpty(mPhone)) {
            Toast.makeText(this, "手机号不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(mCode)) {
            Toast.makeText(this, "验证码不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(mPwd)) {
            Toast.makeText(this, "请输入密码!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(mPwdSure)) {
            Toast.makeText(this, "请再次输入密码!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!mPwdSure.equals(mPwd)) {
            Toast.makeText(this, "两次输入的密码不一致!", Toast.LENGTH_SHORT).show();
            return false;
        }else if (StringUtils.isMobileNO(mPhone)) {

        }else {
            Toast.makeText(this, "输入的手机号格式不正确!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (StringUtils.isCorrectFormat(mPwd)) {
            return true;
        } else {
            Toast.makeText(this, "密码格式不正确,只能由6-12位的数字,字母组成!", Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
    }
/*显示密码*/
    private void isPasswordShowListener() {
        mPwdEyes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int passwordLength = mEditTextPwd.getText().length();
                mEditTextPwd.setInputType(isChecked ?
                        (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) :
                        (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD));
                mEditTextPwd.setSelection(passwordLength);
            }
        });
    }
    private void isPasswordShowListenerSure() {
        mPwdEye.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int passwordLength = mEditTextPwdSure.getText().length();
                mEditTextPwdSure.setInputType(isChecked ?
                        (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) :
                        (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD));
                mEditTextPwdSure.setSelection(passwordLength);
            }
        });
    }
    /*清空数据*/
    private void initWatcher() {
        mAccountWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {//设置删除键

                if (s.toString().length() > 0) {//当输入框有内容时，显示删除按钮
                    mBtnAccountClear.setVisibility(View.VISIBLE);
                } else {
                    mBtnAccountClear.setVisibility(View.INVISIBLE);
                }
            }
        };
        mPasswordWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    mBtnPasswordClear.setVisibility(View.VISIBLE);
                } else {
                    mBtnPasswordClear.setVisibility(View.INVISIBLE);
                }
            }
        };
        mPasswordWatcherSure= new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    mBtnPasswordSureClear.setVisibility(View.VISIBLE);
                } else {
                    mBtnPasswordSureClear.setVisibility(View.INVISIBLE);
                }
            }
        };
    }
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.account_clear:
                mEditTextPhone.setText("");
                mEditTextPwd.setText("");
                mEditTextPwdSure.setText("");
                break;
            case R.id.password_clear:
                mEditTextPwd.setText("");
                break;
            case R.id.password_clear_1:
                mEditTextPwdSure.setText("");
                break;
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
                                    final Dialog dialog = new Dialog(RegisterActivity.this, R.style.translate_dialog);
                                    View contentView = getLayoutInflater().inflate(R.layout.dialog_get_code, null);
                                    TextView tvTitle = (TextView) contentView.findViewById(R.id.tv_dialog_title);
                                    final EditText editText = (EditText) contentView.findViewById(R.id.tv_dialog_content);
                                    final ImageView imageView = (ImageView) contentView.findViewById(R.id.code_pic);
                                    ImageView back = (ImageView) contentView.findViewById(R.id.back);
                                    ImageView refresh = (ImageView) contentView.findViewById(R.id.refresh_pic);
                                    tvTitle.setText("请输入图片验证码");
                                    Glide.with(RegisterActivity.this).load(Url.validateCode+"mobile="+mPhone).signature(new StringSignature(UUID.randomUUID().toString())) .placeholder(R.drawable.ic_launcher).into(imageView);
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
                                                                            StringUtils.showToast(RegisterActivity.this, mSendCodeBean.getMsg());
                                                                            Glide.with(RegisterActivity.this).load(Url.validateCode+"mobile="+mPhone).signature(new StringSignature(UUID.randomUUID().toString())) .placeholder(R.drawable.ic_launcher).into(imageView);
                                                                        }
                                                                    }
                                                                    @Override
                                                                    public void onFailure(int statusCode, String error_msg) {
                                                                        StringUtils.showToast(RegisterActivity.this, error_msg);
                                                                    }
                                                                });
                                                            } else {
                                                                StringUtils.showToast(RegisterActivity.this,"图形验证失败，请重新输入");
                                                                Glide.with(RegisterActivity.this).load(Url.validateCode+"mobile="+mPhone).signature(new StringSignature(UUID.randomUUID().toString())) .placeholder(R.drawable.ic_launcher).into(imageView);
                                                            }
                                                        }
                                                        if (verifyCodeBean.getCode()==0) {
                                                            StringUtils.showToast(RegisterActivity.this,verifyCodeBean.getMsg());
                                                            Glide.with(RegisterActivity.this).load(Url.validateCode+"mobile="+mPhone).signature(new StringSignature(UUID.randomUUID().toString())) .placeholder(R.drawable.ic_launcher).into(imageView);
                                                        }
                                                    }
                                                    @Override
                                                    public void onFailure(int statusCode, String error_msg) {
                                                        StringUtils.showToast(RegisterActivity.this,error_msg);
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
                                            Glide.with(RegisterActivity.this).load(Url.validateCode+"mobile="+mPhone).signature(new StringSignature(UUID.randomUUID().toString())) .placeholder(R.drawable.ic_launcher).into(imageView);
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
                                StringUtils.showToast(RegisterActivity.this, error_msg);
                            }
                        });
                    } else {
                        final Dialog dialog = new Dialog(RegisterActivity.this, R.style.translate_dialog);
                        View contentView = getLayoutInflater().inflate(R.layout.dialog_get_code, null);
                        TextView tvTitle = (TextView) contentView.findViewById(R.id.tv_dialog_title);
                        final EditText editText = (EditText) contentView.findViewById(R.id.tv_dialog_content);
                        final ImageView imageView = (ImageView) contentView.findViewById(R.id.code_pic);
                        ImageView back = (ImageView) contentView.findViewById(R.id.back);
                        ImageView refresh = (ImageView) contentView.findViewById(R.id.refresh_pic);
                        tvTitle.setText("请输入图片验证码");
                        Glide.with(RegisterActivity.this).load(Url.validateCode+"mobile="+mPhone).signature(new StringSignature(UUID.randomUUID().toString())) .placeholder(R.drawable.ic_launcher).into(imageView);
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
                                                                StringUtils.showToast(RegisterActivity.this,mSendCodeBean.getMsg());
                                                                Glide.with(RegisterActivity.this).load(Url.validateCode+"mobile="+mPhone).signature(new StringSignature(UUID.randomUUID().toString())) .placeholder(R.drawable.ic_launcher).into(imageView);
                                                            }
                                                        }
                                                        @Override
                                                        public void onFailure(int statusCode, String error_msg) {
                                                            StringUtils.showToast(RegisterActivity.this, error_msg);
                                                        }
                                                    });
                                                } else {
                                                    StringUtils.showToast(RegisterActivity.this,"图形验证失败，请重新输入");
                                                    Glide.with(RegisterActivity.this).load(Url.validateCode+"mobile="+mPhone).signature(new StringSignature(UUID.randomUUID().toString())) .placeholder(R.drawable.ic_launcher).into(imageView);
                                                }
                                            }
                                            if (verifyCodeBean.getCode()==0) {
                                                StringUtils.showToast(RegisterActivity.this,verifyCodeBean.getMsg());
                                                Glide.with(RegisterActivity.this).load(Url.validateCode+"mobile="+mPhone).signature(new StringSignature(UUID.randomUUID().toString())) .placeholder(R.drawable.ic_launcher).into(imageView);
                                            }
                                        }
                                        @Override
                                        public void onFailure(int statusCode, String error_msg) {
                                            StringUtils.showToast(RegisterActivity.this,error_msg);
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
                                Glide.with(RegisterActivity.this).load(Url.validateCode+"mobile="+mPhone).signature(new StringSignature(UUID.randomUUID().toString())) .placeholder(R.drawable.ic_launcher).into(imageView);
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
                StringUtils.showToast(RegisterActivity.this, error_msg);
            }
        });
    }
    /**
     * 得到key
     */
    private void getKey(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.NAME, mPhone);
        HttpHelper.getInstance().post(Url.GetKey, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final GetKeyBean mGetKeyBean = JsonUtils.parse(response, GetKeyBean.class);
                if (mGetKeyBean.getCode()==1) {
                    PreferenceManager.instance().saveKey(mGetKeyBean.getDatas().getUserKey());
                    login(mGetKeyBean.getDatas().getUserKey());
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                StringUtils.showToast(RegisterActivity.this,error_msg);
            }
        });
    }
    //完成更改密码
    private void login(String key){
        HashMap<String, String> hash = new HashMap<>();
        hash.put("phone", mPhone);
        hash.put("password", MD5.getMD5(new StringBuffer(mPwd).append(key).toString()));
        hash.put(IAppKey.MOBILE_CODE, mCode);
        hash.put(IAppKey.PLATFORM, "android");
        HttpHelper.getInstance().post(Url.FINGCARD, hash, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final CodeLogin mCodeLogin= JsonUtils.parse(response, CodeLogin.class);
                if (mCodeLogin.getCode()==1) {
                    final Dialog dialog = new Dialog(RegisterActivity.this, R.style.translate_dialog);
                    View contentView = getLayoutInflater().inflate(R.layout.dialog_sure, null);
                    TextView tvTitle = (TextView) contentView.findViewById(R.id.tv_dialog_title);
                    TextView tvContent = (TextView) contentView.findViewById(R.id.tv_dialog_content);
                    Button btnOk = (Button) contentView.findViewById(R.id.btn_dialog_ok);
                    btnOk.setText("确认");
                    tvTitle.setText("提示");
                    tvContent.setText("密码更改成功");
                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            finish();
                        }
                    });
                    dialog.setContentView(contentView);
                    Window window = dialog.getWindow();
//                    window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                    WindowManager.LayoutParams params = window.getAttributes();
                    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    params.width = DisplayUtils.getWidthPx()/4*3;
                    window.setAttributes(params);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                }
                if (mCodeLogin.getCode()==0) {
                    StringUtils.showToast(RegisterActivity.this,mCodeLogin.getMsg());
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
               StringUtils.showToast(RegisterActivity.this,error_msg);
            }
        });
    }
}
