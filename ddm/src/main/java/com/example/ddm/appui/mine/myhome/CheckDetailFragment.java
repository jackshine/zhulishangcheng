package com.example.ddm.appui.mine.myhome;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ddm.LocationActivity;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.bean.OneOrderBean;
import com.example.ddm.appui.bean.SendCodeBean;
import com.example.ddm.appui.bean.WorkOrderBean;
import com.example.ddm.appui.bean.eventbus.LoginSuccess;
import com.example.ddm.appui.constant.FragmentTag;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.utils.DisplayUtils;
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
/**
 * A simple {@link Fragment} subclass.
 * 审核的详情界面
 */
public class CheckDetailFragment extends BaseFragment {
    private Button mReject,mSuccess;
    private String mString;
    private TextView mName,mOrder,mShopping,mNum,mPrice,mAll, mIntegral,mFail,mRemark,mBack;
    public CheckDetailFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_detail, container, false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mName = mFindViewUtils.findViewById(R.id.name1);
        mRemark = mFindViewUtils.findViewById(R.id.name2);
        mFail = mFindViewUtils.findViewById(R.id.name3);
        mOrder = mFindViewUtils.findViewById(R.id.order);
        mShopping = mFindViewUtils.findViewById(R.id.shop_name);
        mNum = mFindViewUtils.findViewById(R.id.num);
        mPrice = mFindViewUtils.findViewById(R.id.money);
        mAll = mFindViewUtils.findViewById(R.id.all);
        mIntegral = mFindViewUtils.findViewById(R.id.integral);
        mBack = mFindViewUtils.findViewById(R.id.app_title_back);
        mReject = mFindViewUtils.findViewById(R.id.reject);
        mSuccess = mFindViewUtils.findViewById(R.id.success);
        mReject.setOnClickListener(this);
        mSuccess.setOnClickListener(this);
        getDetail();
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
    }
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.reject:
               showFailDialog();
                break;
            case R.id.success:
               showSuccessDialog();
                break;
        }
    }
    private void getDetail(){
        showLoading("正在加载");
        HashMap<String, String> hashMap = new HashMap<>();
//        hashMap.put(IAppKey.PAGE, "1");//可以为空
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        hashMap.put(IAppKey.ORDERID, String.valueOf(mArguments.getInt("ID")));
        HttpHelper.getInstance().post(Url.APPLYORDERID, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final OneOrderBean bean = JsonUtils.parse(response, OneOrderBean.class);
                if (bean.getCode() == 1) {
                    mName.setText(bean.getDatas().getShopName());
                    mRemark.setText(bean.getDatas().getManagerName());
                    mFail.setText(bean.getDatas().getCustomerUserName());
                    mOrder.setText(bean.getDatas().getCode());
                    mShopping.setText(bean.getDatas().getGoodsName());
                    mNum.setText(bean.getDatas().getGoodsNum() + "个");
                    mPrice.setText(bean.getDatas().getGoodOnePrice());
                    mAll.setText(bean.getDatas().getShopYingYeMoney());
                    mIntegral.setText(bean.getDatas().getShopPayMoney());
                    hiddenLoading();
                } else if (bean.getCode() == 0) {
                    StringUtils.showToast(getActivity(), bean.getMsg());
                    hiddenLoading();
                } else {
                    StringUtils.showToast(getActivity(), bean.getMsg());
                    PreferenceManager.instance().removeToken();
                    StringUtils.showToast(getActivity(), bean.getMsg());
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                hiddenLoading();
                Toast.makeText(getContext(), "请检查网络", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //成功做单
    private void showSuccessDialog(){
        final Dialog dialog = new Dialog(getActivity(), R.style.translate_dialog);
        View contentView = getActivity().getLayoutInflater().inflate(R.layout.dialog_intput_pwd, null);
        TextView tvTitle = (TextView) contentView.findViewById(R.id.tv_dialog_title);
        final EditText etContent = (EditText) contentView.findViewById(R.id.et_dialog_content);
        Button btnCancel = (Button) contentView.findViewById(R.id.btn_dialog_cancel);
        Button btnOk = (Button) contentView.findViewById(R.id.btn_dialog_ok);
        mString = etContent.getText().toString().trim();
        tvTitle.setText("请输入交易密码");
        LogUtils.d("aaaaaaaaaaaaaaaaaaa"+mString);
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
                showLoading();
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
                hashMap.put(IAppKey.ZUODANID, String.valueOf(mArguments.getInt("ID")));
                hashMap.put(IAppKey.TRADEPASSWORD, MD5.getMD5(new StringBuffer(etContent.getText().toString().trim()).append(PreferenceManager.instance().getKey()).toString()));//交易密码加密之后的
                HttpHelper.getInstance().post(Url.AUDITORDER, hashMap, new RawResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response) {
                        hiddenLoading();
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
                                    EventBus.getDefault().post(new LoginSuccess());
                                    popSelf();
//                                    ShowFragmentUtils.showFragment(getActivity(), AcceptOrdersFragment.class, FragmentTag.ACCEPTORDERSFRAGMENT,null,true);
                                }
                            });
                            dialog.setContentView(contentView);
                            Window window = dialog.getWindow();
                            WindowManager.LayoutParams params = window.getAttributes();
                            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                            params.width = DisplayUtils.getWidthPx() /2;
                            window.setAttributes(params);
                            dialog.setCanceledOnTouchOutside(false);
                            dialog.show();
                        } else if (bean.getCode() == 0) {
                            StringUtils.showToast(getActivity(),bean.getMsg());
                        } else {
                            StringUtils.showToast(getActivity(),bean.getMsg());
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        hiddenLoading();
                        Toast.makeText(mBaseActivity, "请检查网络", Toast.LENGTH_SHORT).show();
                    }
                });
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
    //做单失败
    private void showFailDialog(){
        final Dialog dialog = new Dialog(getActivity(), R.style.translate_dialog);
        View contentView =getActivity().getLayoutInflater().inflate(R.layout.dialog_intput_txt, null);
        TextView tvTitle = (TextView) contentView.findViewById(R.id.tv_dialog_title);
        final EditText etContent = (EditText) contentView.findViewById(R.id.et_dialog_content);
        etContent.setHint("请输入拒绝原因");
        Button btnCancel = (Button) contentView.findViewById(R.id.btn_dialog_cancel);
        Button btnOk = (Button) contentView.findViewById(R.id.btn_dialog_ok);
        tvTitle.setText("请输入拒绝原因");
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
                showLoading();
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(IAppKey.ZUODANID, String.valueOf(mArguments.getInt("ID")));
                hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
                hashMap.put(IAppKey.ILLUSTRATE, etContent.getText().toString().trim());//拒绝的理由
//                hashMap.put(IAppKey.TRADEPASSWORD, "");//交易密码加密之后的
                HttpHelper.getInstance().post(Url.AUDITORDERFAIL, hashMap, new RawResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response) {
                        hiddenLoading();
                        final SendCodeBean bean = JsonUtils.parse(response, SendCodeBean.class);
                        if (bean.getCode()==1) {
                            final Dialog dialog = new Dialog(getActivity(), R.style.translate_dialog);
                            View contentView = getActivity().getLayoutInflater().inflate(R.layout.dialog_sure, null);
                            TextView tvTitle = (TextView) contentView.findViewById(R.id.tv_dialog_title);
                            TextView tvContent = (TextView) contentView.findViewById(R.id.tv_dialog_content);
                            Button btnOk = (Button) contentView.findViewById(R.id.btn_dialog_ok);
                            btnOk.setText("确认");
                            tvTitle.setText("提示");
                            tvContent.setText("已拒绝");
                            btnOk.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                    EventBus.getDefault().post(new LoginSuccess());
                                    popSelf();
//                                    ShowFragmentUtils.showFragment(getActivity(), AcceptOrdersFragment.class, FragmentTag.ACCEPTORDERSFRAGMENT,null,true);
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
                            StringUtils.showToast(getActivity(),bean.getMsg());
                        } else {
                            StringUtils.showToast(getActivity(),bean.getMsg());
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        hiddenLoading();
                        Toast.makeText(mBaseActivity, "请检查网络", Toast.LENGTH_SHORT).show();
                    }
                });
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
