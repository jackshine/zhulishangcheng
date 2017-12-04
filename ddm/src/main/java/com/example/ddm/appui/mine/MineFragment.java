package com.example.ddm.appui.mine;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ddm.LoginActivity;
import com.example.ddm.MainActivity;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.bean.MineBean;
import com.example.ddm.appui.bean.NewsNumBean;
import com.example.ddm.appui.bean.eventbus.LoginSuccess;
import com.example.ddm.appui.bean.eventbus.Name;
import com.example.ddm.appui.bean.eventbus.UpdateCardList;
import com.example.ddm.appui.bean.picture.CardBeanList;
import com.example.ddm.appui.car.CarShoppingFragment;
import com.example.ddm.appui.constant.FragmentTag;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.mine.Charge.PayFragment;
import com.example.ddm.appui.mine.myhome.AcceptOrdersFragment;
import com.example.ddm.appui.mine.myhome.BillSummaryFragment;
import com.example.ddm.appui.mine.myhome.BindCardActivity;
import com.example.ddm.appui.mine.myhome.BindCardListFragment;
import com.example.ddm.appui.mine.Charge.ChargeFragment;
import com.example.ddm.appui.mine.myhome.CheckFragment;
import com.example.ddm.appui.mine.myhome.CreditsExchangeFragment;
import com.example.ddm.appui.mine.myhome.FundFragment;
import com.example.ddm.appui.mine.myhome.OfflineFragment;
import com.example.ddm.appui.mine.myhome.PokonyanDetailFragment;
import com.example.ddm.appui.mine.myhome.UseKnowFragment;
import com.example.ddm.appui.mine.sonmerchant.ApplyOrderFragment;
import com.example.ddm.appui.mine.sonmerchant.OrderRecordFragment;
import com.example.ddm.appui.order.OrderFragment;
import com.example.ddm.appui.order.OrderTwoFragment;
import com.example.ddm.appui.utils.DisplayUtils;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.ShowFragmentUtils;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.appui.view.CircleImageView;
import com.example.ddm.appui.view.PullToRefreshView;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;

import org.greenrobot.eventbus.Subscribe;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.ddm.appui.utils.UploadUtil.context;
/**
 * 我的个人界面
 */
public class MineFragment extends BaseFragment implements PullToRefreshView.OnHeaderRefreshListener,PullToRefreshView.OnFooterRefreshListener {
    private TextView mBillSummary,mUseKnow,mCharge,mBindCard,
            mMyOrder,mMyDd,mMyMoney, mMyIntegral,mOffline,mAcceptOrder,
            mCheck,mName,mOrderRecord,mApplyOrder,mQQ,mAbout;
    private TextView mGrade,mMoney,mSend,mDD;
    private PullToRefreshView mPullToRefreshView;
    private RelativeLayout mLayout,mRelativeLayout,mRelativeLayout1;//編輯按鈕
    private CircleImageView mPersonIcon;
    private ImageView mSet,mNews,mNewsNum;
    public MineFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mPullToRefreshView = mFindViewUtils.findViewById(R.id.main_pull_refresh_view);
        mPullToRefreshView.setOnHeaderRefreshListener(this);
        mPullToRefreshView.setOnFooterRefreshListener(this);
        mPullToRefreshView.setLastUpdated(new Date().toLocaleString());
        mNewsNum = mFindViewUtils.findViewById(R.id.news_num);
        mAbout = mFindViewUtils.findViewById(R.id.serviceq);
        mQQ = mFindViewUtils.findViewById(R.id.service);
        mApplyOrder = mFindViewUtils.findViewById(R.id.my_zdsq);
        mOrderRecord = mFindViewUtils.findViewById(R.id.my_zdjl);
        mGrade = mFindViewUtils.findViewById(R.id.user_grade);
        mMoney = mFindViewUtils.findViewById(R.id.account_money);
        mSend = mFindViewUtils.findViewById(R.id.user_code);
        mDD = mFindViewUtils.findViewById(R.id.send_code);
        mRelativeLayout1 = mFindViewUtils.findViewById(R.id.RL3);
        mRelativeLayout = mFindViewUtils.findViewById(R.id.RL4);
        mName = mFindViewUtils.findViewById(R.id.user_name);
        mNews = mFindViewUtils.findViewById(R.id.news);
        mPersonIcon = mFindViewUtils.findViewById(R.id.iv_photo_person_ziliao);
        mLayout = mFindViewUtils.findViewById(R.id.Relative);
        mSet = mFindViewUtils.findViewById(R.id.set);
        mBillSummary = mFindViewUtils.findViewById(R.id.bill_summary);
        mUseKnow = mFindViewUtils.findViewById(R.id.use_know);
        mCharge = mFindViewUtils.findViewById(R.id.charge);
        mBindCard = mFindViewUtils.findViewById(R.id.bind_card);
        mMyOrder = mFindViewUtils.findViewById(R.id.my_order);
        mMyDd = mFindViewUtils.findViewById(R.id.my_dd);
        mMyMoney = mFindViewUtils.findViewById(R.id.my_money);
        mMyIntegral = mFindViewUtils.findViewById(R.id.my_integral);
        mOffline = mFindViewUtils.findViewById(R.id.my_zd);
        mAcceptOrder = mFindViewUtils.findViewById(R.id.my_zdmx);
        mCheck = mFindViewUtils.findViewById(R.id.my_zdsh);

//        if (PreferenceManager.instance().getUserId().equals("1")) {
//            mRelativeLayout1.setVisibility(View.GONE);
//            mGrade.setText("普通用户");
//            mOffline.setVisibility(View.GONE);
//            mAcceptOrder.setVisibility(View.GONE);
//            mCheck.setVisibility(View.GONE);
//            mApplyOrder.setVisibility(View.GONE);
//            mOrderRecord.setVisibility(View.GONE);
//        } else if (PreferenceManager.instance().getUserId().equals("5")) {
//            LogUtils.d("zzzzzsdddf" + PreferenceManager.instance().getUserId());
//            mRelativeLayout.setVisibility(View.GONE);
//            mGrade.setText("子商户");
//            mOffline.setVisibility(View.GONE);
//            mAcceptOrder.setVisibility(View.GONE);
//            mCheck.setVisibility(View.GONE);
//        } else {
//            mApplyOrder.setVisibility(View.GONE);
//            mOrderRecord.setVisibility(View.GONE);
//        }
//        if (TextUtils.isEmpty(PreferenceManager.instance().getName())) {
//            mName.setText("");
//        } else {
//            mName.setText(PreferenceManager.instance().getName());
//        }
    }
    @Subscribe
    public void getWCMsg(Name name) {
        LogUtils.d("收到QQ::::::");
        mName.setText(name.getName());//接受姓名消息
    }
    @Subscribe
    public void UpdatePic(UpdateCardList loginSuccess){
        if (loginSuccess.getMsg().equals("图片更新")) {
            setData();
        }
    }
    @Subscribe
    public void UpdateMoney(UpdateCardList money){
        if (money.getMsg().equals("刷新我的界面")) {
            setData();
        }
    }
    @Override
    protected void setListener() {
        super.setListener();
        mNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ShowFragmentUtils.showFragment(getActivity(),NewsFragment.class,FragmentTag.NEWSFRAGMENT,null,true);
                ShowFragmentUtils.showFragment(getActivity(),CarShoppingFragment.class,FragmentTag.CARSHOPPINGFRAGMENT,null,true);
            }
        });
        mPersonIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentUtils.showFragment(getActivity(),SetFragment.class,FragmentTag.SETFRAGMENT,null,true);
//

            }
        });
        mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentUtils.showFragment(getActivity(),SetFragment.class,FragmentTag.SETFRAGMENT,null,true);
//
            }
        });
        mSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentUtils.showFragment(getActivity(),SetFragment.class,FragmentTag.SETFRAGMENT,null,true);
//
            }
        });
        mBillSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentUtils.showFragment(getActivity(),BillSummaryFragment.class,FragmentTag.BILLSUMMARYFRAGMENT,null,true);
            }
        });
        mUseKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentUtils.showFragment(getActivity(),UseKnowFragment.class,FragmentTag.USEKNOWFRAGMENT,null,true);
            }
        });
        mCharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentUtils.showFragment(getActivity(),PayFragment.class,FragmentTag.PAYFRAGMENT,null,true);
            }
        });
        mBindCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentUtils.showFragment(getActivity(),BindCardListFragment.class,FragmentTag.BINDCARDLISTFRAGMENT,null,true);
            }
        });
        mMyOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                MainActivity activity = (MainActivity) MineFragment.this.getActivity();
//                activity.receverEventPost();
                ShowFragmentUtils.showFragment(getActivity(),OrderTwoFragment.class,FragmentTag.ORDERTWOFRAGMENT,null,true);
            }
        });
        mMyDd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentUtils.showFragment(getActivity(),PokonyanDetailFragment.class,FragmentTag.POKONYANDETAILFRAGMENT,null,true);
            }
        });
        mMyMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentUtils.showFragment(getActivity(),FundFragment.class,FragmentTag.FUNDFRAGMENT,null,true);
            }
        });
        mMyIntegral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    ShowFragmentUtils.showFragment(getActivity(),CreditsExchangeFragment.class,FragmentTag.CREDITSEXCHANGEFRAGMENT,null,true);
            }
        });
        mOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentUtils.showFragment(getActivity(), OfflineFragment.class,FragmentTag.OFFLINEFRAGMENT,null,true);
            }
        });
        mAcceptOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentUtils.showFragment(getActivity(), AcceptOrdersFragment.class,FragmentTag.ACCEPTORDERSFRAGMENT,null,true);
            }
        });
        mOrderRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentUtils.showFragment(getActivity(), OrderRecordFragment.class,FragmentTag.ORDERRECORDFRAGMENT,null,true);
            }
        });
        mApplyOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentUtils.showFragment(getActivity(), ApplyOrderFragment.class,FragmentTag.APPLYORDERFRAGMENT,null,true);
            }
        });
        mCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentUtils.showFragment(getActivity(), CheckFragment.class, FragmentTag.CHECKFRAGMENT, null, true);
//                ShowFragmentUtils.showFragment(getActivity(), CheckDetailFragment.class,FragmentTag.CHECKDETAILFRAGMENT,null,true);
            }
        });
        mQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isQQClientAvailable(getActivity())){
                    String url = "mqqwpa://im/chat?chat_type=crm&uin=938193800&version=1&src_type=web&web_src=http:://wpa.b.qq.com";
                    getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                }else{
                    Toast.makeText(getContext(), "您的手机暂未安装QQ客户端", Toast.LENGTH_SHORT).show();
                }
//                ShowFragmentUtils.showFragment(getActivity(),QqServiceFragment.class,FragmentTag.QQSERVICEFRAGMENT,null,true);
            }
        });
        mAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentUtils.showFragment(getActivity(),AboutUsFragment.class,FragmentTag.ABOUTUSFRAGMENT,null,true);
            }
        });
    }
    /**
     * 判断qq是否可用
     * @param context
     * @return
     */
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    protected void setData() {
        super.setData();
        getPersonPic();
        getNewsNum();
    }
    //获得个人图片
    private void getPersonPic(){
        showLoading("正在加载。。。");
        HashMap<String, String> param = new HashMap<>();
        param.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        HttpHelper.getInstance().post(Url.PERSONAL_PIC, param, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                hiddenLoading();
                final MineBean bean = JsonUtils.parse(response, MineBean.class);
                if (bean.getCode()==1) {
                    LogUtils.d("token="+PreferenceManager.instance().getToken());
                    Glide.with(getContext()).load(bean.getDatas().getAvotorr()).placeholder(R.drawable.ic_launcher).crossFade().into(mPersonIcon);
                    mName.setText(bean.getDatas().getRealName());
                    mMoney.setText(bean.getDatas().getAccountMoney()+"积分");
                    mGrade.setText(bean.getDatas().getUserType());
                    mSend.setText(bean.getDatas().getCddNum()+"个");
                    if (bean.getDatas().getUserTypeValue() == 2) {
//                        mDD.setText(bean.getDatas().getSddNum()+"个");
                        mApplyOrder.setVisibility(View.GONE);
                        mOrderRecord.setVisibility(View.GONE);
                    } else if (bean.getDatas().getUserTypeValue() == 1) {
                        mUseKnow.setText("用户须知");
//                        mRelativeLayout.setVisibility(View.GONE);
                        mOffline.setVisibility(View.GONE);
                        mAcceptOrder.setVisibility(View.GONE);
                        mCheck.setVisibility(View.GONE);
                        mApplyOrder.setVisibility(View.GONE);
                        mOrderRecord.setVisibility(View.GONE);
                    } else if (bean.getDatas().getUserTypeValue() == 5) {
//                        mRelativeLayout.setVisibility(View.GONE);
                        mOffline.setVisibility(View.GONE);
                        mAcceptOrder.setVisibility(View.GONE);
                        mCheck.setVisibility(View.GONE);
                    }
                } else if (bean.getCode()==0) {
                    StringUtils.showToast(getActivity(),bean.getMsg());
                } else if (bean.getCode()==-1) {
                    StringUtils.showToast(getActivity(),bean.getMsg());
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
    //首页消息个数接口
    private void getNewsNum() {
        Map<String, String> param = new HashMap<>();
        param.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        HttpHelper.getInstance().post(Url.NEWSNUM, param, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.d("wyt", "获取首页分类成功：" + response);
                NewsNumBean category = JsonUtils.parse(response, NewsNumBean.class);
                if (category.getCode()==1) {
                    if (category.getDatas().getCount()>0) {
                        mNewsNum.setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                Log.d("wyt", "获取首页分类失败：" + error_msg);
            }
        });
    }
    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        mPullToRefreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
                getPersonPic();
                getNewsNum();
                mPullToRefreshView.onHeaderRefreshComplete("更新于:"
                        + Calendar.getInstance().getTime().toLocaleString());
                mPullToRefreshView.onHeaderRefreshComplete();
                Toast.makeText(getContext(), "数据刷新完成!", Toast.LENGTH_LONG).show();
            }
        }, 1000);
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        mPullToRefreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
                LogUtils.d("ddddddddddddddddddd");
                getPersonPic();
                getNewsNum();
                    LogUtils.d("ddddddddddddddddddd");
                mPullToRefreshView.onFooterRefreshComplete();
                Toast.makeText(getContext(), "加载更多数据", Toast.LENGTH_LONG).show();
                }
        }, 1000);
    }
}
