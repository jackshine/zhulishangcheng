package com.example.ddm.appui.mine.Charge;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.alipay.sdk.app.PayTask;
import com.example.ddm.LoginActivity;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.adapter.CommonAdapter;
import com.example.ddm.appui.adapter.ViewHolder;
import com.example.ddm.appui.bean.Charge;
import com.example.ddm.appui.bean.PayOrderBean;
import com.example.ddm.appui.bean.eventbus.Pay;
import com.example.ddm.appui.bean.eventbus.UpdateCardList;
import com.example.ddm.appui.constant.FragmentTag;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.ShowFragmentUtils;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;
import com.example.ddm.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
 * A simple {@link Fragment} subclass.
 * 待支付
 */
public class WaitPayFragment extends BaseFragment {
    private ListView mListView;
    private List<PayOrderBean.DatasBean> mList = new ArrayList<>();
    private CommonAdapter<PayOrderBean.DatasBean> mAdapter;
    private Double mNum;
    private String mOrderInfo;
    private RelativeLayout mRelativeLayout;
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
                        ShowFragmentUtils.showFragment(getActivity(),PaySuccessFragment.class, FragmentTag.PAYSUCCESSFRAGMENT,null,true);
//                        Toast.makeText(getContext(), "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
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
    public WaitPayFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wait_pay, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mRelativeLayout = mFindViewUtils.findViewById(R.id.Relative);
        mListView = mFindViewUtils.findViewById(R.id.list_view);
    }
    @Override
    protected void setListener() {
        super.setListener();
        mAdapter = new CommonAdapter<PayOrderBean.DatasBean>(getContext(),null,R.layout.wait_pay_item) {
            @Override
            public void setViewData(ViewHolder holder, PayOrderBean.DatasBean item, int position) {
                holder.setText(item.getCode(), R.id.integral).setText(item.getMoney(), R.id.money);
            }
        };
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (TextUtils.isEmpty(mList.get(position).getMoney())&&TextUtils.isEmpty(mList.get(position).getCode())) {
                    Toast.makeText(mBaseActivity, "参数错误", Toast.LENGTH_SHORT).show();
                } else if (Double.valueOf(mList.get(position).getMoney())>=100) {
                    mNum = Double.valueOf(mList.get(position).getMoney());
                    getMoney(String.valueOf(mNum));
                } else if (Double.valueOf(mList.get(position).getMoney())<100) {
                    Toast.makeText(mBaseActivity, "金额参数少于100", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Subscribe
    public void waitPay(UpdateCardList list){
        if (list.getMsg().equals("待支付")) {
            //支付失败
            WaitPay();
        }

    }
    @Subscribe
    public void pay(Pay list){
       //待支付完成
            WaitPay();
    }
    @Override
    protected void setData() {
        super.setData();
        WaitPay();
    }
    private void WaitPay(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        HttpHelper.getInstance().post(Url.WAITPAY, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final PayOrderBean bean = JsonUtils.parse(response, PayOrderBean.class);
                if (bean.getCode()==1&&bean.getDatas().size()>0) {
                    mRelativeLayout.setVisibility(View.GONE);
                    if (mList!=null) {
                        mList.clear();
                        mList.addAll(bean.getDatas());
                        mAdapter.update(mList);
                    } else if (bean.getCode() == 0) {
                        Toast.makeText(mBaseActivity, bean.getMsg(), Toast.LENGTH_SHORT).show();
                    } else if (bean.getCode()==-1){
                        PreferenceManager.instance().removeToken();
                        Toast.makeText(mBaseActivity, bean.getMsg(), Toast.LENGTH_SHORT).show();
                        mBaseActivity.showActivity(LoginActivity.class,null);
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                Toast.makeText(getContext(), "请检查网络和接口", Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**
     * 支付宝支付业务
     *
     * @param
     */
    public void payV2() {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(getActivity());
//                Map<String, String> result = alipay.payV2(orderInfo, true);
                Map<String, String> result = alipay.payV2(mOrderInfo, true);
                Log.i("msp", result.toString());
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
    private void getMoney(String money){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        hashMap.put(IAppKey.MONEY, money);
        hashMap.put("mark", "12");
        HttpHelper.getInstance().post(Url.CHARGE, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final Charge charge = JsonUtils.parse(response, Charge.class);
                LogUtils.d("测试"+response);
                if (charge.getCode()==1) {
//                    mOrderInfo = charge.getDatas().substring(44,charge.getDatas().length());
                    mOrderInfo = charge.getDatas();
                    LogUtils.d("测试"+mOrderInfo);
                    payV2();
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
            }
        });
    }
}
