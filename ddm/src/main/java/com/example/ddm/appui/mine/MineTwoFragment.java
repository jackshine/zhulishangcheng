package com.example.ddm.appui.mine;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.ddm.LoginActivity;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.adapter.CommonAdapter;
import com.example.ddm.appui.adapter.ViewHolder;
import com.example.ddm.appui.bean.MineBean;
import com.example.ddm.appui.bean.MineItem;
import com.example.ddm.appui.bean.NewsNumBean;
import com.example.ddm.appui.bean.ShareTeam;
import com.example.ddm.appui.car.CarShoppingFragment;
import com.example.ddm.appui.constant.FragmentTag;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.evaluate.EvaluateActivity;
import com.example.ddm.appui.gridview.MyGridView;
import com.example.ddm.appui.home.CodeFragment;
import com.example.ddm.appui.mine.Charge.PayFragment;
import com.example.ddm.appui.mine.coupon.CouponFragment;
import com.example.ddm.appui.mine.myhome.AcceptOrdersFragment;
import com.example.ddm.appui.mine.myhome.BillSummaryFragment;
import com.example.ddm.appui.mine.myhome.BindCardListFragment;
import com.example.ddm.appui.mine.myhome.CheckFragment;
import com.example.ddm.appui.mine.myhome.CreditsExchangeFragment;
import com.example.ddm.appui.mine.myhome.FundFragment;
import com.example.ddm.appui.mine.myhome.OfflineFragment;
import com.example.ddm.appui.mine.myhome.PokonyanDetailFragment;
import com.example.ddm.appui.mine.myhome.UseKnowFragment;
import com.example.ddm.appui.mine.share.ShareFragment;
import com.example.ddm.appui.mine.sonmerchant.ApplyOrderFragment;
import com.example.ddm.appui.mine.sonmerchant.OrderRecordFragment;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.ShowFragmentUtils;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.appui.view.CircleImageView;
import com.example.ddm.appui.view.PullToRefreshView;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.example.ddm.appui.mine.MineFragment.isQQClientAvailable;
/**
 * A simple {@link Fragment} subclass.
 */
public class MineTwoFragment extends BaseFragment implements PullToRefreshView.OnHeaderRefreshListener,PullToRefreshView.OnFooterRefreshListener {
    private MyGridView mMyGridView;
    private CommonAdapter<MineItem> mAdapter;
    private TextView mName;
    private LinearLayout mLinearLayout;
    private TextView mGrade,mMoney,mSend;
    private PullToRefreshView mPullToRefreshView;
    private CircleImageView mPersonIcon;
    private ImageView mSet,mNews,mNewsNum;
    private TextView bill_summary,use_know, charge;
    private TextView bill_summary_1,use_know_1, charge_1;

//    private CommonAdapter
    public MineTwoFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mine_two2, container, false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mLinearLayout = mFindViewUtils.findViewById(R.id.layout);
        bill_summary = mFindViewUtils.findViewById(R.id.bill_summary);
        use_know = mFindViewUtils.findViewById(R.id.use_know);
        charge = mFindViewUtils.findViewById(R.id.charge);
        bill_summary_1 = mFindViewUtils.findViewById(R.id.bill_summary_1);
        use_know_1 = mFindViewUtils.findViewById(R.id.use_know_1);
        charge_1 = mFindViewUtils.findViewById(R.id.charge_1);
        mName = mFindViewUtils.findViewById(R.id.user_name);
        mMyGridView = mFindViewUtils.findViewById(R.id.grid_view);
        mPullToRefreshView = mFindViewUtils.findViewById(R.id.main_pull_refresh_view);
        mPullToRefreshView.setOnHeaderRefreshListener(this);
        mPullToRefreshView.setOnFooterRefreshListener(this);
        mPullToRefreshView.setLastUpdated(new Date().toLocaleString());
        mSet = mFindViewUtils.findViewById(R.id.set);
        mPersonIcon = mFindViewUtils.findViewById(R.id.iv_photo_person_ziliao);
        mGrade = mFindViewUtils.findViewById(R.id.user_grade);
        mMoney = mFindViewUtils.findViewById(R.id.account_money);
        mSend = mFindViewUtils.findViewById(R.id.user_code);
        mNews = mFindViewUtils.findViewById(R.id.news);
        mNewsNum = mFindViewUtils.findViewById(R.id.news_num);
    }
    @Override
    protected void setListener() {
        super.setListener();
        bill_summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentUtils.showFragment(getActivity(),MyOrderFragment.class,FragmentTag.MYORDERFRAGMENT,null,true);
            }
        });
        use_know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentUtils.showFragment(getActivity(),MyOrderFragment.class,FragmentTag.MYORDERFRAGMENT,null,true);
            }
        });
        charge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentUtils.showFragment(getActivity(),MyOrderFragment.class,FragmentTag.MYORDERFRAGMENT,null,true);
            }
        });
        mNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ShowFragmentUtils.showFragment(getActivity(),NewsFragment.class,FragmentTag.NEWSFRAGMENT,null,true);
             mBaseActivity.showActivity(EvaluateActivity.class,null);
//                ShowFragmentUtils.showFragment(getActivity(),CarShoppingFragment.class,FragmentTag.CARSHOPPINGFRAGMENT,null,true);

            }
        });
        mSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentUtils.showFragment(getActivity(),SetFragment.class, FragmentTag.SETFRAGMENT,null,true);
            }
        });
    }
    List<MineItem> getDataS() {
        List<MineItem> mineList = new ArrayList<>();
        int[] imgRes = {
                R.drawable.yhj,
                R.drawable.tgm,
                R.drawable.zxcz,
                R.drawable.sjxz,
                R.drawable.zhgk,
                R.drawable.zjls,
                R.drawable.yhk,
                R.drawable.jfdh,
                R.drawable.xxzd,
                R.drawable.zdmx,
                R.drawable.zdsh,
                R.drawable.ddmx,
                R.drawable.zjjl,
                R.drawable.fxtd,
                R.drawable.qqkf,
                R.drawable.qqkf
        };
        String[] settingStrings = getResources().getStringArray(R.array.person_item_title);
        for (int i = 0; i < settingStrings.length; i++) {
            MineItem item = new MineItem(i, imgRes[i], settingStrings[i]);
            mineList.add(item);
        }
        return mineList;
    }
    List<MineItem> getDataZ() {
        List<MineItem> mineList = new ArrayList<>();
        int[] imgRes = {
                R.drawable.yhj,
                R.drawable.tgm,
                R.drawable.zxcz,
                R.drawable.sjxz,
                R.drawable.zhgk,
                R.drawable.zjls,
                R.drawable.yhk,
                R.drawable.jfdh,
                R.drawable.xxzd,
                R.drawable.zdmx,
                R.drawable.ddmx,
                R.drawable.zjjl,
                R.drawable.fxtd,
                R.drawable.qqkf,
                R.drawable.qqkf
        };
        String[] settingStrings = getResources().getStringArray(R.array.person_item_title_z);
        for (int i = 0; i < settingStrings.length; i++) {
            MineItem item = new MineItem(i, imgRes[i], settingStrings[i]);
            mineList.add(item);
        }
        return mineList;
    }
    List<MineItem> getDataP() {
        List<MineItem> mineList = new ArrayList<>();
        int[] imgRes = {
                R.drawable.yhj,
                R.drawable.tgm,
                R.drawable.zxcz,
                R.drawable.sjxz,
                R.drawable.zhgk,
                R.drawable.zjls,
                R.drawable.yhk,
                R.drawable.jfdh,
                R.drawable.ddmx,
                R.drawable.zjjl,
                R.drawable.fxtd,
                R.drawable.qqkf,
                R.drawable.qqkf
        };
        String[] settingStrings = getResources().getStringArray(R.array.person_item_title_p);
        for (int i = 0; i < settingStrings.length; i++) {
            MineItem item = new MineItem(i, imgRes[i], settingStrings[i]);
            mineList.add(item);
        }
        return mineList;
    }
    @Override
    protected void setData() {
        super.setData();
//        if (PreferenceManager.instance().getUserId().equals("2")) {
//            mAdapter = new CommonAdapter<MineItem>(getContext(),getDataS(),R.layout.grid_view_item) {
//                @Override
//                public void setViewData(ViewHolder holder, MineItem item, int position) {
//                    holder.setImageRes(item.getImgResId(), R.id.icon).setText(item.getName(), R.id.title);
//                }
//            };
//            mMyGridView.setAdapter(mAdapter);
//
//        } else if (PreferenceManager.instance().getUserId().equals("5")) {
//            mAdapter = new CommonAdapter<MineItem>(getContext(),getDataZ(),R.layout.grid_view_item) {
//                @Override
//                public void setViewData(ViewHolder holder, MineItem item, int position) {
//                    holder.setImageRes(item.getImgResId(), R.id.icon).setText(item.getName(), R.id.title);
//                }
//            };
//            mMyGridView.setAdapter(mAdapter);
//        } else if (PreferenceManager.instance().getUserId().equals("1")) {
//            mAdapter = new CommonAdapter<MineItem>(getContext(),getDataP(),R.layout.grid_view_item) {
//                @Override
//                public void setViewData(ViewHolder holder, MineItem item, int position) {
//                    holder.setImageRes(item.getImgResId(), R.id.icon).setText(item.getName(), R.id.title);
//                }
//            };
//            mMyGridView.setAdapter(mAdapter);
//        }

        getPersonPic();
        getNewsNum();
        shareTeam();
    }
    /**
     * 普通
     */
    private void setAdapter1(){
        mMyGridView.setFocusable(false);
        mAdapter = new CommonAdapter<MineItem>(getContext(),getDataP(),R.layout.grid_view_item) {
            @Override
            public void setViewData(ViewHolder holder, MineItem item, int position) {
                holder.setImageRes(item.getImgResId(), R.id.icon).setText(item.getName(), R.id.title);
            }
        };
        mMyGridView.setAdapter(mAdapter);
       mMyGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               switch (position) {
                   case 0:
//                       StringUtils.showToast(getActivity(),"优惠券");
                       ShowFragmentUtils.showFragment(getActivity(), CouponFragment.class,FragmentTag.COUPONFRAGMENT,null,true);
                       break;
                   case 1:
//                       StringUtils.showToast(getActivity(),"推广码");
                       ShowFragmentUtils.showFragment(getActivity(), CodeFragment.class,FragmentTag.CODEFRAGMENT,null,true);
                       break;
                   case 2:
                       ShowFragmentUtils.showFragment(getActivity(),PayFragment.class,FragmentTag.PAYFRAGMENT,null,true);
                       break;
                   case 3:
                       ShowFragmentUtils.showFragment(getActivity(),UseKnowFragment.class,FragmentTag.USEKNOWFRAGMENT,null,true);
                       break;
                   case 4:
                       ShowFragmentUtils.showFragment(getActivity(),BillSummaryFragment.class,FragmentTag.BILLSUMMARYFRAGMENT,null,true);
                       break;
                   case 5:
                       ShowFragmentUtils.showFragment(getActivity(),FundFragment.class,FragmentTag.FUNDFRAGMENT,null,true);
                       break;
                   case 6:
                       ShowFragmentUtils.showFragment(getActivity(),BindCardListFragment.class,FragmentTag.BINDCARDLISTFRAGMENT,null,true);
                       break;
                   case 7:
                       ShowFragmentUtils.showFragment(getActivity(),CreditsExchangeFragment.class,FragmentTag.CREDITSEXCHANGEFRAGMENT,null,true);
                       break;
                   case 8:
                       ShowFragmentUtils.showFragment(getActivity(),PokonyanDetailFragment.class,FragmentTag.POKONYANDETAILFRAGMENT,null,true);
                       break;
                   case 9:
//                       StringUtils.showToast(getActivity(),"中奖记录");
                       ShowFragmentUtils.showFragment(getActivity(),AwardFragment.class,FragmentTag.AWARDFRAGMENT,null,true);
                       break;
                   case 10:
                       StringUtils.showToast(getActivity(),"分享团队");
                       ShowFragmentUtils.showFragment(getActivity(),ShareFragment.class,FragmentTag.SHAREFRAGMENT,null,true);

                       break;
                   case 11:
                       if(isQQClientAvailable(getActivity())){
                           String url = "mqqwpa://im/chat?chat_type=crm&uin=938193800&version=1&src_type=web&web_src=http:://wpa.b.qq.com";
                           getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                       }else{
                           Toast.makeText(getContext(), "您的手机暂未安装QQ客户端", Toast.LENGTH_SHORT).show();
                       }
                       break;
                   case 12:
                       ShowFragmentUtils.showFragment(getActivity(),AboutUsFragment.class,FragmentTag.ABOUTUSFRAGMENT,null,true);
                       break;
               }
           }
       });
    }
    /**
     * 商户的列表
     */
    private void setAdapter2(){
        mMyGridView.setFocusable(false);
        mAdapter = new CommonAdapter<MineItem>(getContext(),getDataS(),R.layout.grid_view_item) {
            @Override
            public void setViewData(ViewHolder holder, MineItem item, int position) {
                holder.setImageRes(item.getImgResId(), R.id.icon).setText(item.getName(), R.id.title);
            }
        };
        mMyGridView.setAdapter(mAdapter);
        mMyGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
//                        StringUtils.showToast(getActivity(),"优惠券");
                        ShowFragmentUtils.showFragment(getActivity(), CouponFragment.class,FragmentTag.COUPONFRAGMENT,null,true);

                        break;
                    case 1:
//                        StringUtils.showToast(getActivity(),"推广码");
                        ShowFragmentUtils.showFragment(getActivity(), CodeFragment.class,FragmentTag.CODEFRAGMENT,null,true);
                        break;
                    case 2:
                        ShowFragmentUtils.showFragment(getActivity(),PayFragment.class,FragmentTag.PAYFRAGMENT,null,true);
                        break;
                    case 3:
                        ShowFragmentUtils.showFragment(getActivity(),UseKnowFragment.class,FragmentTag.USEKNOWFRAGMENT,null,true);
                        break;
                    case 4:
                        ShowFragmentUtils.showFragment(getActivity(),BillSummaryFragment.class,FragmentTag.BILLSUMMARYFRAGMENT,null,true);
                        break;
                    case 5:
                        ShowFragmentUtils.showFragment(getActivity(),FundFragment.class,FragmentTag.FUNDFRAGMENT,null,true);
                        break;
                    case 6:
                        ShowFragmentUtils.showFragment(getActivity(),BindCardListFragment.class,FragmentTag.BINDCARDLISTFRAGMENT,null,true);
                        break;
                    case 7:
                        ShowFragmentUtils.showFragment(getActivity(),CreditsExchangeFragment.class,FragmentTag.CREDITSEXCHANGEFRAGMENT,null,true);
                        break;
                    case 8:
                        ShowFragmentUtils.showFragment(getActivity(), OfflineFragment.class,FragmentTag.OFFLINEFRAGMENT,null,true);
                        break;
                    case 9:
                        ShowFragmentUtils.showFragment(getActivity(), AcceptOrdersFragment.class,FragmentTag.ACCEPTORDERSFRAGMENT,null,true);
                        break;
                    case 10:
                        ShowFragmentUtils.showFragment(getActivity(), CheckFragment.class, FragmentTag.CHECKFRAGMENT, null, true);
                        break;
                    case 11:
                        ShowFragmentUtils.showFragment(getActivity(),PokonyanDetailFragment.class,FragmentTag.POKONYANDETAILFRAGMENT,null,true);
                        break;
                    case 12:
//                        StringUtils.showToast(getActivity(),"中奖记录");
                        ShowFragmentUtils.showFragment(getActivity(),AwardFragment.class,FragmentTag.AWARDFRAGMENT,null,true);

                        break;
                    case 13:
                        StringUtils.showToast(getActivity(),"分享团队");
                        ShowFragmentUtils.showFragment(getActivity(),ShareFragment.class,FragmentTag.SHAREFRAGMENT,null,true);
                        break;
                    case 14:
                        if(isQQClientAvailable(getActivity())){
                            String url = "mqqwpa://im/chat?chat_type=crm&uin=938193800&version=1&src_type=web&web_src=http:://wpa.b.qq.com";
                            getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                        }else{
                            Toast.makeText(getContext(), "您的手机暂未安装QQ客户端", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 15:
                        ShowFragmentUtils.showFragment(getActivity(),AboutUsFragment.class,FragmentTag.ABOUTUSFRAGMENT,null,true);
                        break;
                }
            }
        });
    }
    /**
     * 子商户的列表
     */
    private void setAdapter5(){
        mMyGridView.setFocusable(false);
        mAdapter = new CommonAdapter<MineItem>(getContext(),getDataZ(),R.layout.grid_view_item) {
            @Override
            public void setViewData(ViewHolder holder, MineItem item, int position) {
                holder.setImageRes(item.getImgResId(), R.id.icon).setText(item.getName(), R.id.title);
            }
        };
        mMyGridView.setAdapter(mAdapter);
        mMyGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
//                        StringUtils.showToast(getActivity(),"优惠券");
                        ShowFragmentUtils.showFragment(getActivity(), CouponFragment.class,FragmentTag.COUPONFRAGMENT,null,true);
                        break;
                    case 1:
//                        StringUtils.showToast(getActivity(),"推广码");
                        ShowFragmentUtils.showFragment(getActivity(), CodeFragment.class,FragmentTag.CODEFRAGMENT,null,true);
                        break;
                    case 2:
                        ShowFragmentUtils.showFragment(getActivity(),PayFragment.class,FragmentTag.PAYFRAGMENT,null,true);
                        break;
                    case 3:
                        ShowFragmentUtils.showFragment(getActivity(),UseKnowFragment.class,FragmentTag.USEKNOWFRAGMENT,null,true);
                        break;
                    case 4:
                        ShowFragmentUtils.showFragment(getActivity(),BillSummaryFragment.class,FragmentTag.BILLSUMMARYFRAGMENT,null,true);
                        break;
                    case 5:
                        ShowFragmentUtils.showFragment(getActivity(),FundFragment.class,FragmentTag.FUNDFRAGMENT,null,true);
                        break;
                    case 6:
                        ShowFragmentUtils.showFragment(getActivity(),BindCardListFragment.class,FragmentTag.BINDCARDLISTFRAGMENT,null,true);
                        break;
                    case 7:
                        ShowFragmentUtils.showFragment(getActivity(),CreditsExchangeFragment.class,FragmentTag.CREDITSEXCHANGEFRAGMENT,null,true);
                        break;
                    case 8:
                        ShowFragmentUtils.showFragment(getActivity(), ApplyOrderFragment.class,FragmentTag.APPLYORDERFRAGMENT,null,true);
                        break;
                    case 9:
                        ShowFragmentUtils.showFragment(getActivity(), OrderRecordFragment.class,FragmentTag.ORDERRECORDFRAGMENT,null,true);
                        break;
                    case 10:
                        ShowFragmentUtils.showFragment(getActivity(),PokonyanDetailFragment.class,FragmentTag.POKONYANDETAILFRAGMENT,null,true);
                        break;
                    case 11:
//                        StringUtils.showToast(getActivity(),"中奖记录");
                        ShowFragmentUtils.showFragment(getActivity(),AwardFragment.class,FragmentTag.AWARDFRAGMENT,null,true);
                        break;
                    case 12:
                        StringUtils.showToast(getActivity(),"分享团队");
                        ShowFragmentUtils.showFragment(getActivity(),ShareFragment.class,FragmentTag.SHAREFRAGMENT,null,true);
                        break;
                    case 13:
                        if(isQQClientAvailable(getActivity())){
                            String url = "mqqwpa://im/chat?chat_type=crm&uin=938193800&version=1&src_type=web&web_src=http:://wpa.b.qq.com";
                            getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                        }else{
                            Toast.makeText(getContext(), "您的手机暂未安装QQ客户端", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 14:
                        ShowFragmentUtils.showFragment(getActivity(),AboutUsFragment.class,FragmentTag.ABOUTUSFRAGMENT,null,true);
                        break;
                }
            }
        });
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
                    Glide.with(getContext()).load(bean.getDatas().getAvotorr()).into(mPersonIcon);
                    mName.setText(bean.getDatas().getRealName());
                    mMoney.setText(bean.getDatas().getAccountMoney()+"积分");
                    mGrade.setText(bean.getDatas().getUserType());
                    mSend.setText(bean.getDatas().getCddNum()+"个");
                    if (bean.getDatas().getUserTypeValue() == 2) {
                        setAdapter2();
                    } else if (bean.getDatas().getUserTypeValue() == 1) {
                        setAdapter1();
                        mLinearLayout.setVisibility(View.GONE);
                    } else if (bean.getDatas().getUserTypeValue() == 5) {
                        setAdapter5();
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
    private void shareTeam(){
        showLoading();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        HttpHelper.getInstance().post(Url.shareTeam, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                hiddenLoading();
                LogUtils.d("扇形"+response);
                ShareTeam shareTeam = JsonUtils.parse(response, ShareTeam.class);
                if (shareTeam.getCode()==1) {
                   PreferenceManager.instance().saveOnePerson(String.valueOf(shareTeam.getDatas().getOne().getPersons()));
                   PreferenceManager.instance().saveTwoPerson(String.valueOf(shareTeam.getDatas().getTwo().getPersons()));
                   PreferenceManager.instance().saveThressPerson(String.valueOf(shareTeam.getDatas().getThree().getPersons()));
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                hiddenLoading();
            }
        });
    }
}
