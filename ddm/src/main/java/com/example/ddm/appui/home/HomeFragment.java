package com.example.ddm.appui.home;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.example.ddm.LocationActivity;
import com.example.ddm.LoginActivity;
import com.example.ddm.MainActivity;
import com.example.ddm.R;
import com.example.ddm.SaoYiSaoActivity;
import com.example.ddm.application.ExecutorFactory;
import com.example.ddm.appui.AdventuresActivity;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.ShoppingCarActivity;
import com.example.ddm.appui.adapter.CommonAdapter;
import com.example.ddm.appui.adapter.DealOfViewPagerBaseAdapter;
import com.example.ddm.appui.adapter.ISliderAdapter;
import com.example.ddm.appui.adapter.PopBaseAdapter;
import com.example.ddm.appui.adapter.SliderAdapter;
import com.example.ddm.appui.adapter.ViewHolder;
import com.example.ddm.appui.adapter.ViewPagerScroller;
import com.example.ddm.appui.animation.MyAnimation;
import com.example.ddm.appui.bean.Category;
import com.example.ddm.appui.bean.CheckCodeBean;
import com.example.ddm.appui.bean.GetRegisterRedPacket;
import com.example.ddm.appui.bean.GetUserIdBean;
import com.example.ddm.appui.bean.HomeGridView;
import com.example.ddm.appui.bean.HomeListViewList;
import com.example.ddm.appui.bean.HotShop;
import com.example.ddm.appui.bean.InformBean;
import com.example.ddm.appui.bean.IntegralBean;
import com.example.ddm.appui.bean.NewShopBean;
import com.example.ddm.appui.bean.NewsNumBean;
import com.example.ddm.appui.bean.PopItem;
import com.example.ddm.appui.bean.SendCodeBean;
import com.example.ddm.appui.bean.SlideBean;
import com.example.ddm.appui.bean.SliderNewsBean;
import com.example.ddm.appui.bean.eventbus.Card;
import com.example.ddm.appui.bean.eventbus.Location;
import com.example.ddm.appui.bean.eventbus.LoginSuccess;
import com.example.ddm.appui.bean.eventbus.Name;
import com.example.ddm.appui.bean.eventbus.NowLocation;
import com.example.ddm.appui.bean.eventbus.WeChat;
import com.example.ddm.appui.constant.FragmentTag;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.gridview.GridViewDataOne;
import com.example.ddm.appui.gridview.GridViewDataTwo;
import com.example.ddm.appui.mine.NewsFragment;
import com.example.ddm.appui.mine.myhome.UseKnowFragment;
import com.example.ddm.appui.summary.NearbySearchFragment;
import com.example.ddm.appui.utils.DensityUtils;
import com.example.ddm.appui.utils.DisplayUtils;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.ShowFragmentUtils;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.appui.utils.Utils;
import com.example.ddm.appui.view.HorizontalListView;
import com.example.ddm.appui.view.ListViewForScrollView;
import com.example.ddm.appui.view.ObservableScrollView;
import com.example.ddm.appui.view.PullToRefreshView;
import com.example.ddm.appui.widget.ImgTextView;
import com.example.ddm.appui.zxing.android.CaptureActivity;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.NetClient;
import com.example.ddm.okthhp.body.ResponseProgressBody;
import com.example.ddm.okthhp.response.RawResponseHandler;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;
import static com.example.ddm.R.id.view;
/**
 * A simple {@link Fragment} subclass.
 * 首页界面
 */
public class HomeFragment extends BaseFragment implements ObservableScrollView.ScrollViewListener,PullToRefreshView.OnHeaderRefreshListener,PullToRefreshView.OnFooterRefreshListener {
    private ViewPager mViewPagerSlider;
    private LinearLayout mPoints;
    private ViewFlipper mViewFlipper;
    private PullToRefreshView mPullToRefreshView;
    private GridView mGvCategory;
    private PopupWindow mPopupWindow;
    private ImageView mPan;
    private List<PopItem> mPopItemList = new ArrayList<>();
    private CommonAdapter<PopItem> mPopItemCommonAdapter;
    private ListView mView;
    private ListViewForScrollView mListView;//首页产品详情界面
    private HorizontalListView mHorizontalListView;//横向滑动商品
    private TextView mInform1,mInform2,mInform3, mInform4;
    private TextView mMore;
    private TextView mWeiZhi,mSliderNews,mSliderMoney,mSliderValue;
    private CommonAdapter<HotShop.DatasBean> mAdapter;
    private List<HotShop.DatasBean> mList=new ArrayList<>();//最热商家或更多
    private CommonAdapter<NewShopBean.DatasBean> mHomeListViewListCommonAdapter;
    private List<NewShopBean.DatasBean> mHomeListViewLists=new ArrayList<>();//最新商家
    List<String> list = new ArrayList<String>();
    private  ImageView mTop,mNew,mNewNum,mAdd,mPersonPic;
    private RelativeLayout mLocation,mRelativeLayout,mRelativeLayoutNews;
    private List<SlideBean.DatasBean> mDatasBeanList = new ArrayList<>();
    private boolean isRunning;
    private int lastPosition;
    private EditText mEditText;
    private int userId;
    private ObservableScrollView mScrollView;//自定义scrollview
    private LinearLayout mLayout,mSliderLayout;//占位用的布局
    private String[] types;
    private String[] tips;
    private Context context;
    private TextView mHongBao1;//红包
    private TextView mPromptly;/*立即领取*/
    private String mNewMoney;/*新人红包*/
//    private TextView mNewTextMoney;
//    private TextView mSure;/*确认领取*/
    private ValueAnimator valueAnimator1;
    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    private static final int REQUEST_CODE_SCAN = 0x0000;
    private String mMoney;
    private int page = 1;
    private boolean hasMoreData = true;
    private ShareAction mShareAction;
    private UMShareListener mShareListener;
    private Timer mTimer;
    private CommonAdapter<Category.DataBeans> mCategoryAdapter;
    private List<Category.DataBeans> mCategoryList = new ArrayList<>();//分类的集合
    private int height,top_height;
    private List<ImgTextView> mImgTextViews=new ArrayList<>();
    private static final int delay = 3000;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                mViewPagerSlider.setCurrentItem(mViewPagerSlider.getCurrentItem() + 1, false);
                if (isRunning) {
                    mHandler.sendEmptyMessageDelayed(0, delay);
                }
            } else if (msg.what==1) {
                LogUtils.d("我走了2");
                getSliderNews();
            }
        }
    };
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }
    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    @Subscribe
    public void getWCMsg(Location location) {
        LogUtils.d("收到地址::::::");
        mWeiZhi.setText(location.getName());//接受了地址消息
        mHomeListViewLists.clear();
            ListItem(page,mWeiZhi.getText().toString().trim());
        HorizontalListView(mWeiZhi.getText().toString().trim());//热门商家
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        Utils.countDevicePixels(getActivity());
        mPan = mFindViewUtils.findViewById(R.id.deal_viewpager_item1_panic_buying_textview);
        mSliderMoney = mFindViewUtils.findViewById(R.id.Slider_money);
        mSliderValue = mFindViewUtils.findViewById(R.id.Slider_value);
        mSliderNews = mFindViewUtils.findViewById(R.id.Slider_news);
        mPersonPic = mFindViewUtils.findViewById(R.id.person_pic);
        mSliderLayout = mFindViewUtils.findViewById(R.id.Slider1);
        mAdd = mFindViewUtils.findViewById(R.id.mainactivity_title_position_map);
        mNewNum = mFindViewUtils.findViewById(R.id.news_num);
        mWeiZhi = mFindViewUtils.findViewById(R.id.mainactivity_title_positioncity_txt);
        mPullToRefreshView = mFindViewUtils.findViewById(R.id.main_pull_refresh_view);
        mPullToRefreshView.setOnHeaderRefreshListener(this);
        mPullToRefreshView.setOnFooterRefreshListener(this);
        mPullToRefreshView.setLastUpdated(new Date().toLocaleString());
        mMore = mFindViewUtils.findViewById(R.id.more);
        mNew = mFindViewUtils.findViewById(R.id.news);
        mEditText = mFindViewUtils.findViewById(R.id.mainactivity_title_edit);
        mEditText.setInputType(InputType.TYPE_NULL);
        mRelativeLayoutNews = mFindViewUtils.findViewById(R.id.deal_viewpager_item1_panic_buying_time_layout);
        mTop = mFindViewUtils.findViewById(R.id.top_pic);
        mViewFlipper = mFindViewUtils.findViewById(R.id.marquee_view);
        mRelativeLayout = mFindViewUtils.findViewById(R.id.title_relativelayout);
        mScrollView = mFindViewUtils.findViewById(R.id.scrollview);
        mLayout = mFindViewUtils.findViewById(R.id.layout_zhanwei);
        mLocation = mFindViewUtils.findViewById(R.id.location_layout);
        mHorizontalListView = mFindViewUtils.findViewById(R.id.horizontal_list_view);
        mListView = mFindViewUtils.findViewById(R.id.you_like_listview);
        initList();

        mHomeListViewListCommonAdapter = new CommonAdapter<NewShopBean.DatasBean>(getContext(),null,R.layout.home_list_view_item) {
            @Override
            public void setViewData(ViewHolder holder, NewShopBean.DatasBean item, int position) {
                mListView.setFocusable(false);
                if (item.getIsNewShop() == 1) {
                    holder.getView(R.id.new_shop).setVisibility(View.VISIBLE);
                } else {
                    holder.getView(R.id.new_shop).setVisibility(View.GONE);
                }
                ImageView view = holder.getView(R.id.pic_sp);
                Glide.with(getContext()).load(item.getMinImg()).placeholder(R.drawable.ic_launcher).crossFade().into(view);
                holder.setText(item.getShopName(), R.id.txt_sp).setText(item.getPhone(), R.id.phone)
                        .setText(item.getShopAddress(), R.id.place).setText(item.getRealName(), R.id.sp_name);
            }
        };
        mListView.setAdapter(mHomeListViewListCommonAdapter);
        ListItem(page,mWeiZhi.getText().toString().trim());//最新商家
        HorizontalListView(mWeiZhi.getText().toString().trim());//热门商家
        mGvCategory = mFindViewUtils.findViewById(R.id.gv_category);
        mViewPagerSlider = mFindViewUtils.findViewById(R.id.banner_viewpager);
        mPoints = mFindViewUtils.findViewById(R.id.banner_viewpager_points);
        ViewTreeObserver vto = mLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                height = mLayout.getHeight();
                top_height = 10*mLayout.getHeight();
                mScrollView.setScrollViewListener(HomeFragment.this);
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PreferenceManager.instance().saveShopId(String.valueOf(mHomeListViewLists.get(position).getID()));
//                ShowFragmentUtils.showFragment(getActivity(),ShoppingDetailFragment.class,FragmentTag.SHOPPINGDETAILFRAGMENT,null,true);
                ShowFragmentUtils.showFragment(getActivity(),ShopDetailFragment.class,FragmentTag.SHOPDETAILFRAGMENT,null,true);
            }
        });
    }
    //最新商家的接口调用
    private void ListItem(int page,String city){
        showLoading("正在加载。。。");
        mPullToRefreshView.onFooterRefreshComplete();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.PAGE, String.valueOf(page));
        hashMap.put("city",city);
        HttpHelper.getInstance().post(Url.New_SHOP, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.d("tag", "fanhuishuju++++"+response
                );
                hiddenLoading();
                final NewShopBean mSendCodeBean= JsonUtils.parse(response, NewShopBean.class);
                if (mSendCodeBean.getCode() == 1) {
                    if (mHomeListViewLists != null) {
                        mHomeListViewLists.addAll(mSendCodeBean.getDatas());
                        if (mSendCodeBean.getDatas().size() <1) {
                            hasMoreData = false;
                        }
                        mHomeListViewListCommonAdapter.update(mHomeListViewLists);
                    }
                } else {
                    hiddenLoading();
                    Toast.makeText(getContext(), mSendCodeBean.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                hiddenLoading();
            }
        });
    }
    /**
     * @param address 城市地址
     * 热门商家
     */
    private void HorizontalListView(String address){

        mAdapter = new CommonAdapter<HotShop.DatasBean>(getContext(), null, R.layout.home_grid_view_item) {
            @Override
            public void setViewData(ViewHolder holder, HotShop.DatasBean item, int position) {
                mHorizontalListView.setFocusable(false);
                ImageView view = holder.getView(R.id.pic_sp);
                Glide.with(getContext()).load(item.getMinImg()).placeholder(R.drawable.ic_launcher).crossFade().into(view);
                holder.setText(item.getShopName(), R.id.txt_sp);
            }
        };
        mHorizontalListView.setAdapter(mAdapter);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.PAGE, "1");
        hashMap.put(IAppKey.MORE, "0");
        hashMap.put("city", address);
        HttpHelper.getInstance().post(Url.SHOP_HOT, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final HotShop mSendCodeBean= JsonUtils.parse(response, HotShop.class);
                if (mSendCodeBean.getCode()==1) {
                    if (mList!=null) {
                        mList.clear();
                        mList.addAll(mSendCodeBean.getDatas());
                        mAdapter.update(mList);
                    }
                    mHorizontalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            PreferenceManager.instance().saveShopId(String.valueOf(mList.get(position).getID()));
                            ShowFragmentUtils.showFragment(getActivity(),ShoppingDetailFragment.class,FragmentTag.SHOPPINGDETAILFRAGMENT,null,true);
//                            ShowFragmentUtils.showFragment(getActivity(),ShopDetailFragment.class,FragmentTag.SHOPDETAILFRAGMENT,null,true);
//                            mBaseActivity.showActivity(ShoppingCarActivity.class,null);
                        }
                    });
                }
                LogUtils.d("==========="+mSendCodeBean.getCode());
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                LogUtils.d("dddddddd"+"dddddddddd");
            }
        });
    }
    @Override
    protected void setListener() {
        super.setListener();
        mPan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBaseActivity.showActivity(AdventuresActivity.class,null);
            }
        });
        mCategoryAdapter = new CommonAdapter<Category.DataBeans>(getActivity(), mCategoryList,
                R.layout.mainactivity_tab_content_item1_gridview_item) {
            @Override
            public void setViewData(ViewHolder holder, Category.DataBeans item, int position) {
                ImageView view = holder.getView(R.id.gridview_item_imageview);
                holder.setText(item.getName(), R.id.gridview_item_textview);
                Glide.with(getContext()).load(item.getImg())
                        .placeholder(R.drawable.ic_launcher).crossFade()
                        .into(view);
            }
        };
        mGvCategory.setAdapter(mCategoryAdapter);
        mGvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mArguments.putInt("classid", mCategoryAdapter.getItem(i).getID());
                PreferenceManager.instance().saveClassName(mCategoryAdapter.getItem(i).getName());
               ShowFragmentUtils.showFragment(getActivity(),ShoppingFragment.class,FragmentTag.SHOPPINGFRAGMENT,mArguments,true);
            }
        });
        mViewPagerSlider.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mHandler.removeMessages(0);
                        break;
                    case MotionEvent.ACTION_UP:
                        mHandler.sendEmptyMessageDelayed(0, delay);
                        break;
                }
                return false;
            }
        });
        mViewPagerSlider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                // 控制底部导航点的显示
                position %=mDatasBeanList.size();
                mPoints.getChildAt(position).setEnabled(true);
                mPoints.getChildAt(lastPosition).setEnabled(false);
                lastPosition = position;
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        //首页定位按钮
        mLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    mBaseActivity.finish();
                    startActivity(new Intent(getActivity(),LocationActivity.class));
            }
        });
        mViewFlipper.addView(View.inflate(getContext(), R.layout.noticelayout, null));
        mViewFlipper.addView(View.inflate(getContext(), R.layout.noticelayout_two, null));
        mInform1 = mFindViewUtils.findViewById(R.id.inform1);
        mInform2 = mFindViewUtils.findViewById(R.id.inform2);
        mInform3=mFindViewUtils.findViewById(R.id.inform3);
        mInform4 = mFindViewUtils.findViewById(R.id.inform4);
        News();
//        getMsg();
        getPic();
//        返回顶部按钮监听
        mScrollView.setFocusable(true);
        mTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        mScrollView.fullScroll(ObservableScrollView.FOCUS_UP);
                    }
                });
            }
        });
        //消息通知监听
        mRelativeLayoutNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentUtils.showFragment(getActivity(), InformFragment.class,FragmentTag.INFORMFRAGMENT,null,true);
            }
        });
        mEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    ShowFragmentUtils.showFragment(getActivity(), SearchFragment.class,FragmentTag.SEARCHFRAGMENT,null,true);
//                ShowFragmentUtils.showFragment(getActivity(),NearbySearchFragment.class, FragmentTag.NEARBYSEARCHFRAGMENT,null,true);

            }
        });
        mNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(PreferenceManager.instance().getToken())) {
                    mBaseActivity.showActivity(LoginActivity.class, null);
                } else {
                    ShowFragmentUtils.showFragment(getActivity(),NewsFragment.class,FragmentTag.NEWSFRAGMENT,null,true);
                }
            }
        });
        mMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // MoreItem();
            ShowFragmentUtils.showFragment(getActivity(),MoreFragment.class,FragmentTag.MOREFRAGMENT,null,true);
            }
        });
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopWindow(mPopItemList);
                mView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mPopupWindow.dismiss();
                        switch (position) {
                            case 0:
//                                mBaseActivity.showActivity(SaoYiSaoActivity.class,null);
                                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                                getActivity().startActivityForResult(intent, REQUEST_CODE_SCAN);
                                break;
                            case 1:
                                ShowFragmentUtils.showFragment(getActivity(),CodeFragment.class,FragmentTag.CODEFRAGMENT,null,true);
                                break;
                            case 2:
                                ShowFragmentUtils.showFragment(getActivity(),InviteCodeFragment.class,FragmentTag.INVITECODEFRAGMENT,null,true);
                                break;
                            case 3:
                                if (!TextUtils.isEmpty(PreferenceManager.instance().getToken())) {
                                    ShareBoardConfig config = new ShareBoardConfig();
                                    config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_CENTER);
                                    config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_NONE);
                                    mShareAction.open(config);
                                } else {
                                    mBaseActivity.showActivity(LoginActivity.class,null);
                                }
                                break;
                        }
                    }
                });
            }
        });
        mShareListener = new CustomShareListener(this);
        mShareAction = new ShareAction(getActivity()).setDisplayList(
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA,
                SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.MORE)
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                            UMWeb web = new UMWeb(Url.link+"useId="+userId);
                            web.setTitle("分享一个产品，代购整座城市"+"\n"+"消费商—"+PreferenceManager.instance().getName()+"的商城");
                            web.setDescription("同城优选，助利商城");
                            web.setThumb(new UMImage(getContext(), R.mipmap.logo_share));
                            new ShareAction(getActivity()).withMedia(web)
                                    .setPlatform(share_media)
                                    .setCallback(mShareListener)
                                    .share();
                    }
                });
        mTimer = new Timer(true);
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
//                getSliderNews();
                Message message=new Message();
                message.what=1;
                mHandler.sendMessage(message);
                LogUtils.d("我走了");
            }
        },10000,7500);
    }
    private void initList(){
        mPopItemList.add(new PopItem("扫一扫",R.mipmap.sys));
        mPopItemList.add(new PopItem("收款",R.mipmap.sk));
        mPopItemList.add(new PopItem("邀请码",R.mipmap.zxing_p));
        mPopItemList.add(new PopItem("分享",R.mipmap.share));
    }
    @Override
    protected void setData() {
        super.setData();
        PreferenceManager.instance().saveLocation(mWeiZhi.getText().toString());
        getCategory();//aq
        getIntegralNum();
        getNewsNum();
        GetId();
        Register(PreferenceManager.instance().getToken());
        Intent intent = getActivity().getIntent();
        String location=intent.getStringExtra("location");
        if (TextUtils.isEmpty(location)) {
        } else {
            mWeiZhi.setText(location);
        }
    }
    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        //当向上滑动距离大于占位布局的高度值，就调整标题的背景
        if (y > top_height) {
//            float alpha = (10);//0~255    完全透明~不透明
//            //4个参数，第一个是透明度，后三个是红绿蓝三元色参数
            mTop.setVisibility(getView().VISIBLE);
        } else if (y >height&&y<top_height) {
            mRelativeLayout.setBackgroundResource(R.color.green);
            mTop.setVisibility(getView().GONE);
        } else {
            mTop.setVisibility(getView().GONE);
            mRelativeLayout.setBackgroundResource(R.color.green);
        }
    }

    @Override
    public void onScroll(int scrollY) {

    }

    /*下拉刷新*/
    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        mPullToRefreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
                News();
                getCategory();
                getNewsNum();
                getIntegralNum();
                HorizontalListView(mWeiZhi.getText().toString().trim());
                mPullToRefreshView.onHeaderRefreshComplete("更新于:"
                        + Calendar.getInstance().getTime().toLocaleString());
                mPullToRefreshView.onHeaderRefreshComplete();
                Toast.makeText(getContext(), "数据刷新完成!", Toast.LENGTH_LONG).show();
            }
        }, 1000);
    }
   /*上拉加载*/
    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        mPullToRefreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
                LogUtils.d("ddddddddddddddddddd");
                if (hasMoreData) {
                    page++;
                    ListItem(page,mWeiZhi.getText().toString());
                    LogUtils.d("ddddddddddddddddddd");
                    Toast.makeText(getContext(), "加载更多数据", Toast.LENGTH_LONG).show();
                } else {
                    mPullToRefreshView.onFooterRefreshComplete();
                    Toast.makeText(context, "没有更多数据了", Toast.LENGTH_SHORT).show();
                }}
        }, 1000);
    }
    private static class CustomShareListener implements UMShareListener {
        private WeakReference<HomeFragment> mActivity;
        private CustomShareListener(HomeFragment activity) {
            mActivity = new WeakReference(activity);
        }
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }
        @Override
        public void onResult(SHARE_MEDIA platform) {
            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(mActivity.get().getContext(), " 收藏成功啦", Toast.LENGTH_SHORT).show();
            } else {
                if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                        && platform != SHARE_MEDIA.EMAIL
                        && platform != SHARE_MEDIA.FLICKR
                        && platform != SHARE_MEDIA.FOURSQUARE
                        && platform != SHARE_MEDIA.TUMBLR
                        && platform != SHARE_MEDIA.POCKET
                        && platform != SHARE_MEDIA.PINTEREST
                        && platform != SHARE_MEDIA.INSTAGRAM
                        && platform != SHARE_MEDIA.GOOGLEPLUS
                        && platform != SHARE_MEDIA.YNOTE
                        && platform != SHARE_MEDIA.EVERNOTE) {
                    Toast.makeText(mActivity.get().getContext(), " 分享成功啦", Toast.LENGTH_SHORT).show();
                }
            }
        }
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                    && platform != SHARE_MEDIA.EMAIL
                    && platform != SHARE_MEDIA.FLICKR
                    && platform != SHARE_MEDIA.FOURSQUARE
                    && platform != SHARE_MEDIA.TUMBLR
                    && platform != SHARE_MEDIA.POCKET
                    && platform != SHARE_MEDIA.PINTEREST
                    && platform != SHARE_MEDIA.INSTAGRAM
                    && platform != SHARE_MEDIA.GOOGLEPLUS
                    && platform != SHARE_MEDIA.YNOTE
                    && platform != SHARE_MEDIA.EVERNOTE) {
                Toast.makeText(mActivity.get().getContext(), " 分享失败啦", Toast.LENGTH_SHORT).show();
                if (t != null) {
                    com.umeng.socialize.utils.Log.d("throw", "throw:" + t.getMessage());
                }
            }
        }
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(mActivity.get().getContext(), " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(getContext()).onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mShareAction.close();
    }
    @Override
    public void onDestroy() {
        if (mTimer!=null) {
            mTimer.cancel();
            mTimer = null;
        }
        super.onDestroy();
    }
    /**
     * 解析轮播图
     */
    private void getMsg() {
        ExecutorFactory.instance().getExecutorService().submit(new Runnable() {
            @Override
            public void run() {
                final Request request = new Request.Builder()
                        .url(Url.BANNERC)
                        .build();
                Call call = NetClient.instance.mHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                            }
                        });
                    }
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        final String value = new String(response.body().bytes());
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                LogUtils.d("tag"+value);
                                SlideBean bean = JsonUtils.parse(value, SlideBean.class);
//                                bean.getDatas();
                                if (mDatasBeanList!=null) {
                                    mDatasBeanList.clear();
                                    mDatasBeanList = bean.getDatas();
                                }
                                initArgs();
//                                for (SlideBean.DatasBean url:bean.getDatas()) {
//                                    list.add(url.getImgUrl());
//                                }
                                LogUtils.d("轮播集合"+list.toString());
                            }
                        });
                    }
                });
            }
        });
    }
    private void getPic(){
        HashMap<String, String> hashMap = new HashMap<>();
        HttpHelper.getInstance().post(Url.BANNERC, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                SlideBean bean = JsonUtils.parse(response, SlideBean.class);
                if (bean.getDatas().size()>0) {
                    if (mDatasBeanList!=null) {
                        mDatasBeanList.clear();
                        mDatasBeanList = bean.getDatas();
                    }
                    initArgs();
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                StringUtils.showToast(getActivity(), error_msg);
            }
        });

    }
    /**
     * 填充首页轮播图
     */
    private void initArgs() {
        isRunning = true;
        types = new String[]{"blue", "red", "blue", "red","blue","red"};
        tips = new String[]{"叮当猫", "我的最爱", "我在助利商城", "等你!","OneDay","不见不散"};
        for (int i = 0; i < mDatasBeanList.size(); i++) {
            ImgTextView imageTextView = new ImgTextView(context);
            imageTextView.setSource(mDatasBeanList.get(i).getImg(),getActivity());
            mImgTextViews.add(imageTextView);
            /*滚动点，运动轨迹*/
            ImageView point = new ImageView(context);
            point.setBackgroundResource(R.drawable.viewpager_point);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.rightMargin = DensityUtils.dp2px(context, 10f);
            params.bottomMargin = DensityUtils.dp2px(context, 10f);
            point.setLayoutParams(params);
            if (i == 0) {
                point.setEnabled(true);
            } else {
                point.setEnabled(false);
            }
            mPoints.addView(point);
        }
        mViewPagerSlider.setAdapter(new SliderAdapter(mImgTextViews, new ISliderAdapter() {
            @Override
            public void onClickItem(int i) {
                /*轮播图跳转*/
                if (i == 4) {
                    MainActivity activity = (MainActivity) HomeFragment.this.getActivity();
                    activity.receverEventPost();
                } else {
                    mArguments.putString("banurl",mDatasBeanList.get(i).getUrl());
                    ShowFragmentUtils.showFragment(getActivity(),SliderFragment.class,FragmentTag.SLIDERFRAGMENT,mArguments,true);
                }
            }
        }));
        // 设置动画
        ViewPagerScroller scroller = new ViewPagerScroller(context);
        // 控制自动播放时时间
        scroller.setScrollDuration(delay);
        scroller.initViewPagerScroll(mViewPagerSlider);
        mHandler.sendEmptyMessageDelayed(0, delay);
    }
    /**
     * 轮播滚动消息列表
     */
    private void getSliderNews(){
        LogUtils.d("我走了3");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        HttpHelper.getInstance().post(Url.SHOWREDISMESSAGE, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                LogUtils.d("首页轮播消息"+response);
                final SliderNewsBean bean = JsonUtils.parse(response, SliderNewsBean.class);
                if (bean.getCode()==1) {
                    if (bean.getDatas().isIfShow()) {
                        mSliderLayout.setVisibility(View.VISIBLE);
                        if (bean.getDatas().getUrl()!=null) {
                            Glide.with(getContext()).load(bean.getDatas().getUrl()).placeholder(R.drawable.ic_launcher).crossFade().into(mPersonPic);
                        }
                        mSliderNews.setText(bean.getDatas().getMessage());
                        mSliderMoney.setText(bean.getDatas().getValue());
                        mSliderValue.setText(bean.getDatas().getType()+"   ");
                        ObjectAnimator transX = ObjectAnimator.ofFloat(mSliderLayout, "translationY", 0f,220f);
                        transX.setDuration(3000);//时间3秒
                        transX.start();//开始
                        ObjectAnimator alpha = ObjectAnimator.ofFloat(mSliderLayout, "alpha", 1.0f, 0.0f);
                        alpha.setDuration(8000);
                        alpha.start();
                        LogUtils.d("我走了4");
                    } else {
                        mSliderLayout.setVisibility(View.GONE);
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
            }
        });
    }
    /**
     * 首页通知接口
     */
    private void News(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.PAGE,"1");
        HttpHelper.getInstance().post(Url.INFORM, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final InformBean bean = JsonUtils.parse(response, InformBean.class);
                if (bean.getCode()==1) {
                    mInform1.setText(bean.getDatas().get(0).getTitle());
                    mInform2.setText(bean.getDatas().get(1).getTitle());
                    mInform3.setText(bean.getDatas().get(2).getTitle());
                    mInform4.setText(bean.getDatas().get(3).getTitle());
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
            }
        });
    }
    /**
     * 首页分类接口
     */
    private void getCategory() {
        Map<String, String> param = new HashMap<>();
        HttpHelper.getInstance().post(Url.GET_CATEGORY, param, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.d("wyt", "获取首页分类成功：" + response);
                Category category = JsonUtils.parse(response, Category.class);
                if (category.getCode()==1&&category.getDatas().size()>0) {
                    if (mCategoryList!=null) {
                        mCategoryList.clear();
                        mCategoryList.addAll(category.getDatas());
                        mCategoryAdapter.update(mCategoryList);
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                Log.d("wyt", "获取首页分类失败：" + error_msg);
            }
        });
    }
    /**
     * 消息个数的接口
     */
    private void getNewsNum() {
        Map<String, String> param = new HashMap<>();
        param.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        HttpHelper.getInstance().post(Url.NEWSNUM, param, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.d("wyt", "获取首页分类成功：" + response);
                NewsNumBean category = JsonUtils.parse(response, NewsNumBean.class);
                if (category.getCode()==1) {
                    LogUtils.d("消息个数"+category.getDatas().getCount());
                    if (category.getDatas().getCount() > 0) {
                        mNewNum.setVisibility(View.VISIBLE);
                         /*下面是角标的开启*/
                        Intent intent = new Intent();
                        intent.setAction("com.mz.aidnd.mservice.ser");
                        intent.setPackage(getActivity().getPackageName());
                        intent.putExtra("number", category.getDatas().getCount());
                        getActivity().startService(intent);
                    } else {
                        mNewNum.setVisibility(View.GONE);
                         /*下面是角标的开启*/
                        Intent intent = new Intent();
                        intent.setAction("com.mz.aidnd.mservice.ser");
                        intent.setPackage(getActivity().getPackageName());
                        intent.putExtra("number",0);
                        getActivity().startService(intent);
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                Log.d("wyt", "获取首页分类失败：" + error_msg);
            }
        });
    }
    /**
     * 积分数量接口
     */
    private void getIntegralNum() {
        Map<String, String> param = new HashMap<>();
        param.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        HttpHelper.getInstance().post(Url.HONGBAO, param, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.d("wyt", "获取首页分类成功：" + response);
                IntegralBean category = JsonUtils.parse(response, IntegralBean.class);
                if (category.getCode()==1) {
                    if (Double.valueOf(category.getDatas().getIntegral()) > 0) {
//                        mHongBao.setVisibility(View.VISIBLE);
                        mMoney = category.getDatas().getIntegral();
                        hongPopWindow();
                    } else {
//                        mHongBao.setVisibility(View.GONE);
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                Log.d("wyt", "获取首页分类失败：" + error_msg);
            }
        });
    }
    /**
     * 初始化poupwindow,首页小popwindow
     */
    private void initPopWindow(List<PopItem> datas) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
//		// 引入窗口配置文件
        View view = inflater.inflate(R.layout.poup_window_small, null,false);
        mView = (ListView) view.findViewById(R.id.listview_pop);
        mPopupWindow = new PopupWindow(view, DisplayUtils.dip2px(52f)*2, WindowManager.LayoutParams.WRAP_CONTENT, true);
        // 需要设置一下此参数，点击外边可消失
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击窗口外边窗口消失
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setAnimationStyle(R.style.popupAnimation);
        mPopItemCommonAdapter = new CommonAdapter<PopItem>(getContext(),datas,R.layout.pop_item) {
            @Override
            public void setViewData(ViewHolder holder, PopItem item, int position) {
                holder.setText(item.getName(), R.id.content).setImageRes(item.getIcon(), R.id.pic);
            }
        };
        mView.setAdapter(mPopItemCommonAdapter);
        // 设置此参数获得焦点，否则无法点击
        mPopupWindow.setFocusable(true);
        mPopupWindow.showAsDropDown(mAdd, 0, 25);
    }
    /**
     * 红包的的popwindow
     */
    private void hongPopWindow() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
//		// 引入窗口配置文件
        View view = inflater.inflate(R.layout.poup_window_hong_bao, null,false);
        mHongBao1 = (TextView) view.findViewById(R.id.hong_bao);
        mPopupWindow = new PopupWindow(view, DisplayUtils.getWidthPx()/5*3, DisplayUtils.getHeightPx()/2, false);
        // 需要设置一下此参数，点击外边可消失
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击窗口外边窗口消失
        setBackgroundAlpha(1.0f);
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.setAnimationStyle(R.style.popupAnimation);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
            }
        });
        // 设置此参数获得焦点，否则无法点击
        mPopupWindow.setFocusable(true);
        mPopupWindow.showAtLocation(view, Gravity.CENTER,0,0);
        mHongBao1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*绕y轴旋转的动画*/
                MyAnimation myYAnimation = new MyAnimation();
                myYAnimation.setRepeatCount(Animation.INFINITE); //旋转的次数（无数次）
                mHongBao1.startAnimation(myYAnimation);
//                getIntegralSure();
                mHongBao1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPopupWindow.dismiss();
                        ShowFragmentUtils.showFragment(getActivity(), HongBaoFragment.class, FragmentTag.HONGBAOFRAGMENT, mArguments, true);
                    }
                },1000);
            }
        });
    }
//    /**
//     * @param bgAlpha 透明度的参数 0.5f代表半透明
//     */
//    public void setBackgroundAlpha(float bgAlpha) {
//        WindowManager.LayoutParams lp = getActivity().getWindow()
//                .getAttributes();
//        lp.alpha = bgAlpha;
//        getActivity().getWindow().setAttributes(lp);
//    }
    private void hongPopWindow2() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
//		// 引入窗口配置文件
        View view = inflater.inflate(R.layout.poup_window_hong_bao_one, null,false);
        mPromptly = (TextView) view.findViewById(R.id.promptly);
        mPopupWindow = new PopupWindow(view, DisplayUtils.getWidthPx()/5*3, DisplayUtils.getHeightPx()/2, false);
        // 需要设置一下此参数，点击外边可消失
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击窗口外边窗口消失
        setBackgroundAlpha(1.0f);
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.setAnimationStyle(R.style.popupAnimation);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
            }
        });
        // 设置此参数获得焦点，否则无法点击
        mPopupWindow.setFocusable(true);
        mPopupWindow.showAtLocation(view, Gravity.CENTER,0,0);
        mPromptly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                updateRegisterRedPacket(PreferenceManager.instance().getToken());
            }
        });
    }
//    private void hongPopWindow3() {
//        LayoutInflater inflater = LayoutInflater.from(getActivity());
////		// 引入窗口配置文件
//        View view = inflater.inflate(R.layout.poup_window_hong_bao_two, null,false);
//        mSure = (TextView) view.findViewById(R.id.sure);
//        mNewTextMoney=(TextView) view.findViewById(R.id.new_hong_bao);
//        mPopupWindow = new PopupWindow(view, DisplayUtils.getWidthPx()/5*3, DisplayUtils.getHeightPx()/2, false);
//        // 需要设置一下此参数，点击外边可消失
//        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
//        //设置点击窗口外边窗口消失
//        setBackgroundAlpha(1.0f);
//        mPopupWindow.setOutsideTouchable(false);
//        mPopupWindow.setAnimationStyle(R.style.popupAnimation);
//        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                setBackgroundAlpha(1.0f);
//            }
//        });
//        // 设置此参数获得焦点，否则无法点击
//        mPopupWindow.setFocusable(true);
//        mPopupWindow.showAtLocation(view, Gravity.CENTER,0,0);
//        mNewTextMoney.setText(mNewMoney);
//        mSure.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mPopupWindow.dismiss();
//            }
//        });
//    }
    /**
     * @param token
     */
    /*注册红包接口*/
    private void Register(String token){
        showLoading();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, token);
        HttpHelper.getInstance().post(Url.GETREGISTERREDPACKET, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                LogUtils.d("红包"+response);
                hiddenLoading();
                final GetRegisterRedPacket getRegisterRedPacket = JsonUtils.parse(response, GetRegisterRedPacket.class);
                if (getRegisterRedPacket.getCode()==1) {
                    if (Double.valueOf(getRegisterRedPacket.getDatas().getRedPacket())>0) {
                        mNewMoney = getRegisterRedPacket.getDatas().getRedPacket();
                        hongPopWindow2();
                    }
                } else if (getRegisterRedPacket.getCode()==0) {
                    StringUtils.showToast(getActivity(),getRegisterRedPacket.getMsg());
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                hiddenLoading();
            }
        });
    }
    /*确认领取注册红包*/
    private void updateRegisterRedPacket(String token){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, token);
        HttpHelper.getInstance().post(Url.UPDATEREGISTERREDPACKET, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                SendCodeBean category = JsonUtils.parse(response, SendCodeBean.class);
                if (category.getCode()==1) {
//                    hongPopWindow3();
                    mArguments.putString("hongbaonum",mNewMoney);
                    ShowFragmentUtils.showFragment(getActivity(),HongBaoDetailFragment.class,FragmentTag.HONGBAODETAILFRAGMENT,mArguments,true);
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {

            }
        });
    }
    private void GetId(){
        showLoading("正在加载");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        HttpHelper.getInstance().post(Url.GETUSERID, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                hiddenLoading();
                final GetUserIdBean bean = JsonUtils.parse(response, GetUserIdBean.class);
                if (bean.getCode()==1) {
                    userId = bean.getDatas().getUserId();
                } else if (bean.getCode()==0) {
                    StringUtils.showToast(getActivity(),bean.getMsg());
                }

            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                hiddenLoading();
                StringUtils.showToast(getActivity(),error_msg);
            }
        });
    }
}
