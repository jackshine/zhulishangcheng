package com.example.ddm.appui.mine.Charge;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.alipay.sdk.app.PayTask;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.bean.Charge;
import com.example.ddm.appui.bean.Money;
import com.example.ddm.appui.bean.eventbus.UpdateCardList;
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
import org.greenrobot.eventbus.EventBus;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
/**
 * 在线充值
 */
public class ChargeFragment extends BaseFragment {
    private Button mSure;
    private EditText mMoney;
    private CheckBox mCheckBox;
    private TextWatcher mWatcher;
    private String mOrderInfo;
    private Double mNum;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")

                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        EventBus.getDefault().post(new UpdateCardList("刷新我的界面"));
                        ShowFragmentUtils.showFragment(getActivity(),PaySuccessFragment.class, FragmentTag.PAYSUCCESSFRAGMENT,null,true);
//                        Toast.makeText(getContext(), "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        EventBus.getDefault().post(new UpdateCardList("待支付"));
                        ShowFragmentUtils.showFragment(getActivity(),PayFailFragment.class, FragmentTag.PAYFAILFRAGMENT,null,true);
//                        Toast.makeText(getContext(), "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(getContext(),
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(getContext(),
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };
    public ChargeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_charge, container, false);
    }
    /**
     * 支付宝支付业务
     *
     * @param
     */
    public void payV2() {
        LogUtils.d("执行1");
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(getActivity());
//                Map<String, String> result = alipay.payV2(orderInfo, true);
                LogUtils.d("支付"+mOrderInfo);
                Map<String, String> result = alipay.payV2(mOrderInfo, true);
                LogUtils.d("支付"+result.toString());
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                LogUtils.d("执行2");
                mHandler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mCheckBox = mFindViewUtils.findViewById(R.id.checkbox);
        mMoney = mFindViewUtils.findViewById(R.id.money);
        mSure = mFindViewUtils.findViewById(R.id.registerInBtn);
        initWatcher();
        mMoney.addTextChangedListener(mWatcher);
    }
    @Override
    protected void setListener() {
        super.setListener();
        mSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNull()) {
//                    post();
                    getMoney(String.valueOf(mNum));
                }
            }
        });
    }
private void initWatcher(){
    mWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };
}
    @Override
    protected void setData() {
        super.setData();
    }
    private boolean isNull(){
        if (mCheckBox.isChecked()) {
            if (TextUtils.isEmpty(mMoney.getText().toString().trim())) {
                Toast.makeText(mBaseActivity, "请输入金额", Toast.LENGTH_SHORT).show();
            } else if (Double.valueOf(mMoney.getText().toString().trim()) >= 100) {
                mNum = Double.valueOf(mMoney.getText().toString().trim());
            } else {
                Toast.makeText(mBaseActivity, "充值金额至少为100", Toast.LENGTH_SHORT).show();
            }
            return true;
        } else {
            Toast.makeText(mBaseActivity, "请选择充值类型", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    /**
     * @param money：充值金额
     */
    private void getMoney(String money){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        hashMap.put(IAppKey.MONEY, money);
        hashMap.put("mark", "12");
        HttpHelper.getInstance().post(Url.CHARGE, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final Charge charge = JsonUtils.parse(response, Charge.class);
                LogUtils.d("支付"+response);
                LogUtils.d("测试"+response);
                if (charge.getCode() == 1) {
                    mOrderInfo = charge.getDatas();
                    LogUtils.d("测试" + mOrderInfo);
                    payV2();
                } else if (charge.getCode()==0) {
                    StringUtils.showToast(getActivity(),charge.getMsg());
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
            }
        });
    }
}

