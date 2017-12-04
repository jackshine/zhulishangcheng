package com.example.ddm.appui.mine;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ddm.LoginActivity;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.bean.CheckCodeBean;
import com.example.ddm.appui.bean.CodeLogin;
import com.example.ddm.appui.bean.SendCodeBean;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.utils.CountDownTimerUtils;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.MD5;
import com.example.ddm.appui.utils.NetUtils;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * 更改登录密码
 */
public class LoginPwdFragment extends BaseFragment {
    private EditText mEditTextCode,mEditTextPwd, mEditTextPwdSure;
    private TextView mBack,mGetCode;
    private String mCode,mPwd, mPwdSure;
    private Button mSure,mBtnPasswordClear,mBtnPasswordSureClear;
    private CheckBox mPwdEyes,mPwdEye;
    private TextWatcher mPasswordWatcher,mPasswordWatcherSure;//密码输入框的监听
    public LoginPwdFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_pwd, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        initWatcher();
        mBack = mFindViewUtils.findViewById(R.id.app_title_back);
        mBtnPasswordClear = mFindViewUtils.findViewById(R.id.password_clear);
        mBtnPasswordSureClear =mFindViewUtils.findViewById(R.id.password_clear_1);
        mBtnPasswordSureClear.setOnClickListener(this);
        mBtnPasswordClear.setOnClickListener(this);
        mEditTextCode = mFindViewUtils.findViewById(R.id.edit_code);
        mEditTextPwd = mFindViewUtils.findViewById(R.id.eidt_password);
        mEditTextPwdSure = mFindViewUtils.findViewById(R.id.eidt_password_1);
        mEditTextPwd.addTextChangedListener(mPasswordWatcher);
        mEditTextPwdSure.addTextChangedListener(mPasswordWatcherSure);
        mSure = mFindViewUtils.findViewById(R.id.registerInBtn);
        mGetCode = mFindViewUtils.findViewById(R.id.get_verify_code);
        mPwdEye = mFindViewUtils.findViewById(R.id.btn_eye_1);
        mPwdEyes = mFindViewUtils.findViewById(R.id.btn_eye);
    }
    @Override
    protected void setListener() {
        super.setListener();
        isPasswordShowListener();
        isPasswordShowListenerSure();
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popSelf();
            }
        });
        mGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Net()) {
                    getCode();

                }

            }
        });
        mSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPhoneNumber()&&Net()) {
                    login();

                }
            }
        });
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
    /*判断账号*/
    private boolean isPhoneNumber(){
        mCode = mEditTextCode.getText().toString().trim();
        mPwd = mEditTextPwd.getText().toString().trim();
        mPwdSure = mEditTextPwdSure.getText().toString().trim();
        if (TextUtils.isEmpty(mCode)) {
            Toast.makeText(getActivity(), "验证码不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(mPwd)) {
            Toast.makeText(getActivity(), "请输入密码!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(mPwdSure)) {
            Toast.makeText(getActivity(), "请再次输入密码!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!mPwdSure.equals(mPwd)) {
            Toast.makeText(getActivity(), "两次输入的密码不一致!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!mPwdSure.equals(mPwd)) {
            Toast.makeText(getActivity(), "两次输入的密码不一致!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (StringUtils.isCorrectFormat(mPwd)) {
            return true;
        } else {
            Toast.makeText(getActivity(), "密码格式不正确,只能由6-12位的数字,字母组成!", Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
    }
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.account_clear:
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
    //得到验证码
    private void getCode(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        hashMap.put(IAppKey.PLATFORM, "android");
        HttpHelper.getInstance().post(Url.Send_Code, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final SendCodeBean mSendCodeBean= JsonUtils.parse(response, SendCodeBean.class);
                if (mSendCodeBean.getCode()==1) {
                    Toast.makeText(getContext(),"验证码已发送",Toast.LENGTH_SHORT).show();
                    CountDownTimerUtils utils = new CountDownTimerUtils(mGetCode, 60000, 1000);
                    utils.start();
                }
                if (mSendCodeBean.getCode()==0) {
                    StringUtils.showToast(getActivity(),mSendCodeBean.getMsg());
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                StringUtils.showToast(getActivity(),error_msg);
            }
        });
    }
    //完成更改密码
private void login(){
        HashMap<String, String> hash = new HashMap<>();
        hash.put("password", MD5.getMD5(new StringBuffer(mPwd).append(PreferenceManager.instance().getKey()).toString()));
        hash.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        hash.put(IAppKey.PHONENUM, PreferenceManager.instance().getPhoneNum());
        hash.put(IAppKey.PLATFORM, "android");
        hash.put(IAppKey.MOBILE_CODE, mCode);
        HttpHelper.getInstance().post(Url.Alter_Pwd, hash, new RawResponseHandler() {
@Override
public void onSuccess(int statusCode, String response) {
final CodeLogin mCodeLogin= JsonUtils.parse(response, CodeLogin.class);
        if (mCodeLogin.getCode()==1) {
        Toast.makeText(getContext(),"更改成功",Toast.LENGTH_SHORT).show();
//            PreferenceManager.instance().removeToken();
            mBaseActivity.showActivity(LoginActivity.class,null);
        }
        if (mCodeLogin.getCode()==0) {
        Toast.makeText(getContext(),"更改失败",Toast.LENGTH_SHORT).show();
        }
        }
@Override
public void onFailure(int statusCode, String error_msg) {
    Toast.makeText(getContext(),"请检查网络",Toast.LENGTH_SHORT).show();
}});
}
        }
