package com.example.ddm.appui.fragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ddm.LoginActivity;
import com.example.ddm.MainActivity;
import com.example.ddm.R;
import com.example.ddm.RegisterActivity;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.bean.CodeLogin;
import com.example.ddm.appui.bean.GetKeyBean;
import com.example.ddm.appui.bean.PersonBean;
import com.example.ddm.appui.bean.SendCodeBean;
import com.example.ddm.appui.bean.eventbus.Location;
import com.example.ddm.appui.bean.eventbus.LoginSuccess;
import com.example.ddm.appui.constant.FragmentTag;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.mine.MineFragment;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.MD5;
import com.example.ddm.appui.utils.ShowFragmentUtils;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 账号登录界面
 */
public class AccountFragment extends BaseFragment {
    private TextView mForget;
    private AutoCompleteTextView mAutoCompleteTextView;
    private EditText mEditTextPwd;
    private String mPhone, mPwd;
    private TextView mDetail;
    private LoginActivity mLoginActivity;
    private CheckBox mPwdEyes,mAgree;
    private Button mLogin,mBtnAccountClear,mBtnPasswordClear;
    private TextWatcher mAccountWatcher;//账号输入框监听
    private TextWatcher mPasswordWatcher;//密码输入框的监听
    public AccountFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mDetail = mFindViewUtils.findViewById(R.id.deal);
        mLoginActivity = new LoginActivity();
        mBtnAccountClear = mFindViewUtils.findViewById(R.id.account_clear);
        mBtnPasswordClear = mFindViewUtils.findViewById(R.id.password_clear);
        mBtnAccountClear.setOnClickListener(this);
        mBtnPasswordClear.setOnClickListener(this);
        mAgree = mFindViewUtils.findViewById(R.id.checkbox);
        mPwdEyes = mFindViewUtils.findViewById(R.id.btn_eye);
        mAutoCompleteTextView = mFindViewUtils.findViewById(R.id.phoneNumEdit);
        mEditTextPwd = mFindViewUtils.findViewById(R.id.eidt_password);
        mForget = mFindViewUtils.findViewById(R.id.forget);
        mLogin = mFindViewUtils.findViewById(R.id.loginInBtn);
        initWatcher();//用来监听editText的内容并作出相应的动作
        mAutoCompleteTextView.addTextChangedListener(mAccountWatcher);
        mEditTextPwd.addTextChangedListener(mPasswordWatcher);
    }
    @Override
    protected void setListener() {
        super.setListener();
        isPasswordShowListener();
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
                    getKey();
                }
            }
        });
        mDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentUtils.showFragment(getActivity(),DealFragment.class, FragmentTag.DEALFRAGMENT,null,true);
            }
        });
    }
    @Override
    protected void setData() {
        super.setData();
        if (TextUtils.isEmpty(PreferenceManager.instance().getPhoneNum())) {
            mAutoCompleteTextView.setText("");
        } else {
            mAutoCompleteTextView.setText(PreferenceManager.instance().getPhoneNum());
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
    /*判断账号和密码*/
    private boolean isPhoneNumber(){
        mPhone = mAutoCompleteTextView.getText().toString().trim();
        mPwd = mEditTextPwd.getText().toString().trim();
        if (TextUtils.isEmpty(mPhone)) {
            Toast.makeText(getActivity(), "账号不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(mPwd)) {
            Toast.makeText(getActivity(), "请输入密码!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
//        if (StringUtils.isCorrectFormat(mPwd)) {
//            return true;
//        } else {
//            Toast.makeText(getActivity(), "密码格式不正确,只能由6-12位的数字,字母组成!", Toast.LENGTH_SHORT)
//                    .show();
//            return false;
//        }
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
//                mEditTextPwd.setText("");
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
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.account_clear:
                mAutoCompleteTextView.setText("");
                mEditTextPwd.setText("");
                break;
            case R.id.password_clear:
                mEditTextPwd.setText("");
                break;
        }
    }
    private void getKey(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.NAME, mPhone);
        HttpHelper.getInstance().post(Url.GetKey, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final GetKeyBean mGetKeyBean = JsonUtils.parse(response, GetKeyBean.class);
                if (mGetKeyBean.getCode() == 1) {
                    PreferenceManager.instance().saveKey(mGetKeyBean.getDatas().getUserKey());
                    login(mGetKeyBean.getDatas().getUserKey());
                } else if (mGetKeyBean.getCode()==0) {
                    StringUtils.showToast(getActivity(),mGetKeyBean.getMsg());
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                Toast.makeText(getContext(),"请检查网络",Toast.LENGTH_SHORT).show();
            }
        });
    }
    //账号登录
    private void login(String key){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.USERNAME, mPhone);
        hashMap.put(IAppKey.PASSWORD, MD5.getMD5(new StringBuffer(mPwd).append(key).toString()));
        HttpHelper.getInstance().post(Url.Login, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final CodeLogin mSendCodeBean= JsonUtils.parse(response, CodeLogin.class);
                if (mSendCodeBean.getCode()==1) {
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
                            PreferenceManager.instance().saveUserId(String.valueOf(mSendCodeBean.getDatas().getUserTypeValue()));//1,2
                            LogUtils.d("用户等级"+mSendCodeBean.getDatas().getUserTypeValue());
                            PreferenceManager.instance().saveUserIdName(mSendCodeBean.getDatas().getUserType());//普通用户
                            PreferenceManager.instance().saveName(mSendCodeBean.getDatas().getRealName());//姓名
                            PreferenceManager.instance().saveToken(mSendCodeBean.getDatas().getToken());//token
                            LogUtils.d("我的token"+mSendCodeBean.getDatas().getToken());
                            PreferenceManager.instance().savePhoneNum(mPhone);//手机号
//                    mBaseActivity.finish();
                    EventBus.getDefault().post(new LoginSuccess());
//                    mBaseActivity.showActivity(MainActivity.class,null);
                    mBaseActivity.finish();
                }
                if (mSendCodeBean.getCode()==0) {
                    Toast.makeText(getContext(),"密码错误,登录失败",Toast.LENGTH_SHORT).show();
//                            Toast.makeText(getContext(),mSendCodeBean.getMsg(),Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                Toast.makeText(getContext(),"请检查网络",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
