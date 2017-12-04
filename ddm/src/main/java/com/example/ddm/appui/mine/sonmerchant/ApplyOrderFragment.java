package com.example.ddm.appui.mine.sonmerchant;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ddm.LoginActivity;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.bean.CheckBean;
import com.example.ddm.appui.bean.CodeLogin;
import com.example.ddm.appui.bean.SendCodeBean;
import com.example.ddm.appui.constant.FragmentTag;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.mine.myhome.AcceptOrdersFragment;
import com.example.ddm.appui.mine.myhome.OfflineFragment;
import com.example.ddm.appui.utils.CountDownTimerUtils;
import com.example.ddm.appui.utils.DisplayUtils;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.MD5;
import com.example.ddm.appui.utils.ShowFragmentUtils;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * 申请做单
 */
public class ApplyOrderFragment extends BaseFragment {
    private EditText mNum,mEditPhone,mEditPwd,mEditShopName,mEditCost,mEditRemark,
            mEditCode;
    private int num;
    private TextView mBack,mTextView,mGetCode,mAll,mMoney;
    private Button mAdd, mMinus;
    private RelativeLayout mRelativeLayout;

    private TextWatcher mPhoneWatcher,mPwdWatcher,mShopNameWatcher,mCostWatcher, mRemarkWatcher,mCodeWatcher;
    private String mPhone,mPwd,mShopName,mCost,mRemark;
    private Button mBtnClear2,mBtnClear3,mBtnClear4, mBtnClear5,mSubmit;
    public ApplyOrderFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_apply_order, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mEditCode = mFindViewUtils.findViewById(R.id.phoneNumEdit);
        mMoney = mFindViewUtils.findViewById(R.id.money);
        mAll = mFindViewUtils.findViewById(R.id.code);
        mRelativeLayout = mFindViewUtils.findViewById(R.id.rl_username);
        mGetCode = mFindViewUtils.findViewById(R.id.get_verify_code);
        mTextView = mFindViewUtils.findViewById(R.id.but1);
        mBack = mFindViewUtils.findViewById(R.id.app_title_back);
        mSubmit = mFindViewUtils.findViewById(R.id.registerInBtn);
        mBtnClear2 = mFindViewUtils.findViewById(R.id.account_clear);
        mBtnClear3 = mFindViewUtils.findViewById(R.id.btn2);
        mBtnClear4 = mFindViewUtils.findViewById(R.id.btn3);
        mBtnClear5 = mFindViewUtils.findViewById(R.id.btn4);
        mBtnClear2.setOnClickListener(this);
        mBtnClear3.setOnClickListener(this);
        mBtnClear4.setOnClickListener(this);
        mBtnClear5.setOnClickListener(this);
        mNum = mFindViewUtils.findViewById(R.id.edt);
        mEditPhone = mFindViewUtils.findViewById(R.id.text_card);
        mEditPwd = mFindViewUtils.findViewById(R.id.text_card_hang);
        mEditShopName = mFindViewUtils.findViewById(R.id.text_card_num);
        mEditCost = mFindViewUtils.findViewById(R.id.text_card_num_sure);
        mEditRemark = mFindViewUtils.findViewById(R.id.text_remark);
        mMinus = mFindViewUtils.findViewById(R.id.subbt);
        mAdd = mFindViewUtils.findViewById(R.id.addbt);
        mMinus.setTag("-");
        mAdd.setTag("+");
        initWatcher();
        mEditPhone.addTextChangedListener(mPhoneWatcher);
        mEditPwd.addTextChangedListener(mPwdWatcher);
        mEditShopName.addTextChangedListener(mShopNameWatcher);
        mEditCost.addTextChangedListener(mCostWatcher);
        mEditRemark.addTextChangedListener(mRemarkWatcher);
        mEditCode.addTextChangedListener(mCodeWatcher);
    }
    @Override
    protected void setListener() {
        super.setListener();
        mMinus.setOnClickListener(new ApplyOrderFragment.OnButtonClickListener());
        mAdd.setOnClickListener(new ApplyOrderFragment.OnButtonClickListener());
        mNum.addTextChangedListener(new ApplyOrderFragment.OnTextChangeListener());
        mGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCode();
                CountDownTimerUtils utils = new CountDownTimerUtils(mGetCode, 60000, 1000);
                utils.start();
            }
        });
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPhoneNumber()) {
                    final Dialog dialog = new Dialog(getActivity(), R.style.translate_dialog);
                    View contentView = getActivity().getLayoutInflater().inflate(R.layout.dialog_change_city, null);
                    TextView tvTitle = (TextView) contentView.findViewById(R.id.tv_dialog_title);
                    TextView tvContent = (TextView) contentView.findViewById(R.id.tv_dialog_content);
                    Button btnCancel = (Button) contentView.findViewById(R.id.btn_dialog_cancel);
                    Button btnOk = (Button) contentView.findViewById(R.id.btn_dialog_ok);
                    btnOk.setText("确认");
                    tvTitle.setText("提示");
                    tvContent.setText("您好，为保障消费者合法权益，我们严厉打击做单虚假行为，请确认做单真实");
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            post();
                        }
                    });
                    dialog.setContentView(contentView);
                    Window window = dialog.getWindow();
                    WindowManager.LayoutParams params = window.getAttributes();
                    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    params.width = DisplayUtils.getWidthPx() / 4 * 3;
                    window.setAttributes(params);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();

                }
            }
        });
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popSelf();
            }
        });
    }
    //监听加减按钮
    class OnButtonClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            // 得到输入框里的数字
            String numString = mNum.getText().toString();
            //进行判断为空或是没文字设置为0
            if (numString == null || numString.equals(""))
            {
                num = 0;
                mNum.setText("0");
            } else{
                //当点击-的时候一次递减（num--）
                if (v.getTag().equals("-"))
                {
                    //  判断（大于0的才能往下减）
                    if (++num < 0)  //先加，再判断
                    {
                        num--;
                        Toast.makeText(getContext(), "请输入一个大于0的数字",
                                Toast.LENGTH_SHORT).show();
                    } else
                    {
                        mNum.setText(String.valueOf(num));
                    }
                } else if (v.getTag().equals("+"))
                {
                    // 判断（自减限制数不小于0）
                    if (--num < 1)  //先减，再判断
                    {
                        num++;
                        Toast.makeText(getContext(), "不能小于1", Toast.LENGTH_SHORT).show();
                    } else
                    {
                        mNum.setText(String.valueOf(num));
                    }
                }
            }
        }
    }
    //监听数量变化
    class OnTextChangeListener implements TextWatcher
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void afterTextChanged(Editable s)
        {
            String numString = s.toString();
            if(numString == null || numString.equals(""))
            {
                num = 0;
                mNum.setText("1");
            }
            else {
                int numInt = Integer.parseInt(numString);
                if (numInt < 0)
                {
                    Toast.makeText(getContext(), "请输入一个大于0的数字",
                            Toast.LENGTH_SHORT).show();
                } else if (numInt>0) {
                    //设置EditText光标位置 为文本末端
                    mNum.setSelection(mNum.getText().toString().length());
                    num = numInt;
                    if (TextUtils.isEmpty(mEditCost.getText().toString())) {
                        mAll.setText("0.00");
                        mMoney.setText("0.00");
                    } else {
                        mAll.setText(num*Double.valueOf(mEditCost.getText().toString().trim())+"0");
                        mMoney.setText(num*Double.valueOf(mEditCost.getText().toString().trim())/10+"0");
                    }

                }
            }
        }
    }
    /**
     * 获取editText中的值
     * @return
     */
    public int getNum()
    {
        if ( mNum.getText().toString() != null )
        {
            return Integer.parseInt(mNum.getText().toString());
        }
        else {
            return 0;
        }
    }
    //判断手机号
    private boolean isPhoneNumber(){
        mPhone = mEditPhone.getText().toString().trim();
        mPwd = mEditPwd.getText().toString().trim();
        mShopName = mEditShopName.getText().toString().trim();
        mCost = mEditCost.getText().toString().trim();
        if (TextUtils.isEmpty(mPhone)) {
            Toast.makeText(getActivity(), "手机不能为空!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(mPwd)) {
            Toast.makeText(getActivity(), "请输入交易密码!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(mShopName)) {
            Toast.makeText(getActivity(), "请输入商品!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(mCost)) {
            Toast.makeText(getActivity(), "请输入单价!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (StringUtils.isMobileNO(mPhone)) {
        } else {
            Toast.makeText(getActivity(), "手机号不合法!", Toast.LENGTH_SHORT).show();
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
    /*清空数据*/
    private void initWatcher() {
        mPhoneWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {//设置删除键
                if (s.toString().length()==11) {
                    Verify();
                }

            }
        };
        mPwdWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    mBtnClear2.setVisibility(View.VISIBLE);
                } else {
                    mBtnClear2.setVisibility(View.INVISIBLE);
                }
            }
        };
        mShopNameWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    mBtnClear3.setVisibility(View.VISIBLE);
                } else {
                    mBtnClear3.setVisibility(View.INVISIBLE);
                }
            }
        };
        mCostWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(mEditCost.getText().toString().trim())) {
                    mAll.setText("0.00");
                    mMoney.setText("0.00");
                } else {
                    Double a=Double.valueOf(mEditCost.getText().toString().trim());
                    Double b=Double.valueOf(mNum.getText().toString().trim());
                    mAll.setText(a*b+"0");
                    mMoney.setText(a*b/10+"0");
                }

            }
        };
        mRemarkWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    mBtnClear5.setVisibility(View.VISIBLE);
                } else {
                    mBtnClear5.setVisibility(View.INVISIBLE);
                }
            }
        };
        mCodeWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length()==6) {
                    RegisterUser1();
                }
            }
        };
    }
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.account_clear:
                mEditPwd.setText("");
                break;
            case R.id.btn2:
                mEditShopName.setText("");
                break;
            case R.id.btn4:
                mEditRemark.setText("");
                break;
        }
    }
    //申请做单
    private void post(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        hashMap.put(IAppKey.NAME, mPhone);
        hashMap.put(IAppKey.TRADEPASSWORD, MD5.getMD5(new StringBuffer(mPwd).append(PreferenceManager.instance().getKey()).toString()));
        hashMap.put(IAppKey.GOODNAME, mShopName);
        hashMap.put(IAppKey.GOODNUM,mNum.getText().toString().trim());
        hashMap.put(IAppKey.GOODONEPRICE,mCost);
        HttpHelper.getInstance().post(Url.APPLYORDER, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final SendCodeBean bean = JsonUtils.parse(response, SendCodeBean.class);
                if (bean.getCode()==1) {
                    final Dialog dialog = new Dialog(getActivity(), R.style.translate_dialog);
                    View contentView = getActivity().getLayoutInflater().inflate(R.layout.dialog_sure, null);
                    TextView tvTitle = (TextView) contentView.findViewById(R.id.tv_dialog_title);
                    TextView tvContent = (TextView) contentView.findViewById(R.id.tv_dialog_content);
                    Button btnOk = (Button) contentView.findViewById(R.id.btn_dialog_ok);
                    btnOk.setText("确认");
                    tvTitle.setText("提示");
                    tvContent.setText("做单成功");
                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            ShowFragmentUtils.showFragment(getActivity(), OrderRecordFragment.class, FragmentTag.ORDERRECORDFRAGMENT,null,true);
                        }
                    });
                    dialog.setContentView(contentView);
                    Window window = dialog.getWindow();
                    WindowManager.LayoutParams params = window.getAttributes();
                    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    params.width = DisplayUtils.getWidthPx() / 2;
                    window.setAttributes(params);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
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
            }
        });
    }
    //验证做单手机号
    private void Verify(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.USERNAME, mEditPhone.getText().toString().trim());
        HttpHelper.getInstance().post(Url.CHECK, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final CheckBean bean = JsonUtils.parse(response, CheckBean.class);
                if (bean.getCode()==1) {
                    mTextView.setText(bean.getDatas().getRealName());
                } else if (bean.getCode()==0) {
                    mRelativeLayout.setVisibility(View.VISIBLE);
                    mTextView.setText(bean.getMsg());
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {

            }
        });
    }
    //得到验证码
    private void getCode(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.PHONENUM, mEditPhone.getText().toString().trim());
        hashMap.put(IAppKey.PLATFORM, "android");
        HttpHelper.getInstance().post(Url.Send_Code, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final SendCodeBean mSendCodeBean= JsonUtils.parse(response, SendCodeBean.class);
                if (mSendCodeBean.getCode()==1) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(),"验证码已发送",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                if (mSendCodeBean.getCode()==0) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(),"验证码发送失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
            }
        });
    }
    //检验验证码并注册
    private void RegisterUser1(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("mobile", mEditPhone.getText().toString().trim());
        hashMap.put(IAppKey.PLATFORM, "android");
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        hashMap.put(IAppKey.MOBILE_CODE,mEditCode.getText().toString().trim());
        HttpHelper.getInstance().post(Url.DeviceDetail, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final CodeLogin mCodeLogin= JsonUtils.parse(response, CodeLogin.class);
                if (mCodeLogin.getCode()==1) {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(IAppKey.USERNAME, mEditPhone.getText().toString().trim());
                    HttpHelper.getInstance().post(Url.REGISTERUSER, hashMap, new RawResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, String response) {
                            final CodeLogin mCodeLogin= JsonUtils.parse(response, CodeLogin.class);
                            if (mCodeLogin.getCode()==1) {
                                Toast.makeText(getContext(),"注册成功",Toast.LENGTH_SHORT).show();
                            }
                            if (mCodeLogin.getCode()==0) {
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getContext(),"注册失败",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                        @Override
                        public void onFailure(int statusCode, String error_msg) {
                            Toast.makeText(getContext(),"请检查网络",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                if (mCodeLogin.getCode()==0) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(),"验证码不正确，请重新获取",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                Toast.makeText(getContext(),"请检查网络",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
