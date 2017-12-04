package com.example.ddm.appui.summary.detail;


import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.ShoppingCarActivity;
import com.example.ddm.appui.adapter.AdapterPopuoShopCarGridView;
import com.example.ddm.appui.adapter.ComListviewAdapter;
import com.example.ddm.appui.adapter.CommonAdapter;
import com.example.ddm.appui.adapter.ISliderAdapter;
import com.example.ddm.appui.adapter.PopBaseAdapter;
import com.example.ddm.appui.adapter.SliderAdapter;
import com.example.ddm.appui.adapter.ViewHolder;
import com.example.ddm.appui.adapter.ViewPagerScroller;
import com.example.ddm.appui.adapter.clickback.OnPopWinDisMisBack;
import com.example.ddm.appui.adapter.clickback.ShoppingAdapter;
import com.example.ddm.appui.bean.GetGoodSpecification;
import com.example.ddm.appui.bean.GetSpecification;
import com.example.ddm.appui.bean.StoreDetail;
import com.example.ddm.appui.bean.eventbus.UpdateCardList;
import com.example.ddm.appui.constant.FragmentTag;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.order.ShoppingCarFragment;
import com.example.ddm.appui.utils.DensityUtils;
import com.example.ddm.appui.utils.DisplayUtils;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.ShowFragmentUtils;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.appui.view.ActionSheet;
import com.example.ddm.appui.view.CustomDialog;
import com.example.ddm.appui.view.ListViewForScrollView;
import com.example.ddm.appui.view.ObservableScrollView;
import com.example.ddm.appui.view.ZFlowLayout;
import com.example.ddm.appui.widget.ImgTextView;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.example.ddm.appui.home.ShoppingDetailFragment.REQUEST_CODE_ASK_CALL_PHONE;
import static com.example.ddm.appui.mine.MineFragment.isQQClientAvailable;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoodsTwoFragment extends BaseFragment implements ObservableScrollView.ScrollViewListener {
    private ObservableScrollView mScrollView;//自定义scrollview
    private LinearLayout mLayout,mShopCar_Btn,mFocus_Btn;
    private int height,top_height,mHeight;
    private String mShopCode;
    private String mShopName;
    private String mSmall,mBig, mDetail;
    private String mPicUrl;
    private TextView mTextViewShopping,mTextViewParticulars, mTextViewAppraise;
    private RelativeLayout mRelativeLayout,mRelativeLayoutBoss,mRelativeLayoutTop,mPopRelative;
    private ViewPager mViewPagerSlider;
    private LinearLayout mPoints;
    private boolean isRunning;
    private int lastPosition;
    private String mGoodsName;
    private TextView mTextView;/*货到付款*/
    private TextView mGoodsDetail;
    private TextView mIntegral;
    private TextView mLocation;
    private TextView mBuy_Now;
    private TextView mNum;/*评分*/
    private TextView marketPrice;
    private TextView memberPrice;/*会员价*/
    private TextView mDetail_1,mDetail_2,mDetail_3, mDetail_4;
    private List<ImgTextView> mImgTextViews=new ArrayList<>();
    private List<String> mList = new ArrayList<>();/*轮播图集合*/
    private String mPhone;/*商家电话*/
    private TextView mAppraise;
    private ImageView mImageViewPhone;
    private boolean flagSingle;
    private GetGoodSpecification model;
    public GetGoodSpecification mSingleModel = new GetGoodSpecification();
    private WebView mWebView;
    private ZFlowLayout mFlowLayout;
    private ShoppingAdapter mCommentAdapter;
    private TextView mActionNum, mActionToPay;
    private ImageView mShopPic,mPopBack;
    private String mGoodsNum,mGoodsPic;
    private ActionSheet mChoseTypeSheet;
    private TextView mPutIn;
    private String type_url;
    private String type_two;
    private String mPropertiesid="";
    private String mGoodsId;
    private PopupWindow mPopupWindow;
    private ListView mListView;
    private TextView dialog_txt;
    private CustomDialog dialog;
    private TextView tv_item_minus_comm_detail,tv_item_number_comm_detail,tv_item_add_comm_detail,goods_type,dialog_goods_price;
    private ComListviewAdapter comListviewAdapter;
    private final int SWITCH_PAGE =  0x00123;
    private GetSpecification dateModle;
    private ImageView dialog_img;
    private int goods_nmb = 1;
    private int specification;
    private String string = "";
    private Boolean isboolean = false;
    private CommonAdapter<GetSpecification.DatasBean> mAdapter;
    private List<GetSpecification.DatasBean> mGetSpecification = new ArrayList<>();
    private List<String> mStringList = new ArrayList<>();
    private List<String> mStringList1 = new ArrayList<>();
    private AdapterPopuoShopCarGridView mAdapterPopuoShopCarGridView;
    private List<StoreDetail.DatasBean.CommentsSumdatasBean> mCommentsSumdatasBeen = new ArrayList<>();
    private List<StoreDetail.DatasBean.CustomersdatasBean> mCustomersdatasBeen = new ArrayList<>();
    private List<String> mlist = new ArrayList<>();
    private ListViewForScrollView mListViewForScrollView1;
    private static final int delay = 3000;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                mViewPagerSlider.setCurrentItem(mViewPagerSlider.getCurrentItem() + 1, false);
                if (isRunning) {
                    mHandler.sendEmptyMessageDelayed(0, delay);
                }
            }
        }
    };
    public GoodsTwoFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_goods_two, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mBuy_Now = mFindViewUtils.findViewById(R.id.buy_now);
        mFocus_Btn = mFindViewUtils.findViewById(R.id.focus_btn);
        mShopCar_Btn = mFindViewUtils.findViewById(R.id.shopcar_btn);
        mPutIn = mFindViewUtils.findViewById(R.id.put_in);
        mPopRelative = mFindViewUtils.findViewById(R.id.layout);
        mWebView = mFindViewUtils.findViewById(R.id.webview);
        mTextViewShopping = mFindViewUtils.findViewById(R.id.shopping);
        mTextViewParticulars = mFindViewUtils.findViewById(R.id.particulars);
        mTextViewAppraise = mFindViewUtils.findViewById(R.id.appraise1);
        mScrollView = mFindViewUtils.findViewById(R.id.scrollView);
        mLayout = mFindViewUtils.findViewById(R.id.layout_zhanwei);
        mRelativeLayoutBoss = mFindViewUtils.findViewById(R.id.boss);
        mRelativeLayout = mFindViewUtils.findViewById(R.id.title_relativelayout);
        mRelativeLayoutTop = mFindViewUtils.findViewById(R.id.top_title);
        mLocation = mFindViewUtils.findViewById(R.id.address);
        mAppraise = mFindViewUtils.findViewById(R.id.appraise);
        mTextView = mFindViewUtils.findViewById(R.id.text_1);
        mImageViewPhone = mFindViewUtils.findViewById(R.id.phone);
        mDetail_1 = mFindViewUtils.findViewById(R.id.detail_1);
        mDetail_2 = mFindViewUtils.findViewById(R.id.detail_2);
        mDetail_3 = mFindViewUtils.findViewById(R.id.detail_3);
        mDetail_4 = mFindViewUtils.findViewById(R.id.detail_4);
        mFlowLayout = mFindViewUtils.findViewById(R.id.flowLayout);
        marketPrice = mFindViewUtils.findViewById(R.id.ture_money);
        memberPrice = mFindViewUtils.findViewById(R.id.member);
        mListViewForScrollView1 = mFindViewUtils.findViewById(R.id.list_view1);
        mIntegral = mFindViewUtils.findViewById(R.id.qiang_hongbao);
        mGoodsDetail = mFindViewUtils.findViewById(R.id.shop_detail);
        mViewPagerSlider = mFindViewUtils.findViewById(R.id.banner_viewpager);
        mPoints = mFindViewUtils.findViewById(R.id.banner_viewpager_points);
        mNum = mFindViewUtils.findViewById(R.id.num);
        getDetail("2");
    }
    @Override
    protected void setListener() {
        super.setListener();
        mBuy_Now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSpecification(mArguments.getString("goodsId"));
            }
        });
        mFocus_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isQQClientAvailable(getActivity())){
                    String url = "mqqwpa://im/chat?chat_type=crm&uin=938193800&version=1&src_type=web&web_src=http:://wpa.b.qq.com";
                    getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                }else{
                    Toast.makeText(getContext(), "您的手机暂未安装QQ客户端", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mShopCar_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ShowFragmentUtils.showFragment(getActivity(),ShoppingCarFragment.class,FragmentTag.SHOPPINGCARFRAGMENT,null,true);
            }
        });
        mPutIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSpecification(mArguments.getString("goodsId"));
            }
        });
        ViewTreeObserver vto = mLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                height = mLayout.getHeight();

                top_height = mRelativeLayoutBoss.getHeight()-mWebView.getHeight();
                mScrollView.setScrollViewListener(GoodsTwoFragment.this);
            }
        });
        mTextViewShopping.setOnClickListener(new View.OnClickListener() {
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
        mTextViewParticulars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        mScrollView.scrollTo(0,10*height);
                        mTextViewParticulars.setTextColor(0xFF49C9BB);
                        mTextViewShopping.setTextColor(0xFF313131);
                        mTextViewAppraise.setTextColor(0xFF313131);
                    }
                });
            }
        });
        mTextViewAppraise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        mScrollView.scrollTo(0,top_height);
                        mTextViewAppraise.setTextColor(0xFF49C9BB);
                        mTextViewShopping.setTextColor(0xFF313131);
                        mTextViewParticulars.setTextColor(0xFF313131);
                    }
                });
            }
        });
        mImageViewPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.CALL_PHONE);
                    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[] {
                                Manifest.permission.CALL_PHONE
                        }, REQUEST_CODE_ASK_CALL_PHONE);
                        return;
                    } else {
                        // 上面已经写好的拨号方法 callDirectly(mobile);
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mPhone));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                } else {
                    // 上面已经写好的拨号方法 callDirectly(mobile);
                    Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + mPhone));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
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
        mPopRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSpecification(mArguments.getString("goodsId"));
//                mBaseActivity.showActivity(ShoppingCarActivity.class,null);
            }
        });
        mViewPagerSlider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                // 控制底部导航点的显示
                position %=mList.size();
                mPoints.getChildAt(position).setEnabled(true);
                mPoints.getChildAt(lastPosition).setEnabled(false);
                lastPosition = position;
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mCommentAdapter = new ShoppingAdapter(getContext(), null);
        mListViewForScrollView1.setAdapter(mCommentAdapter);
    }

    @Override
    protected void setData() {
        super.setData();
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y==0) {
            mRelativeLayoutTop.setVisibility(View.GONE);
        } else if (y>0&&y<height) {
            mRelativeLayoutTop.setVisibility(View.VISIBLE);
            mRelativeLayout.setBackgroundResource(R.color.white);
        } else if (y==height) {
//            mRelativeLayoutTop.getBackground().setAlpha(255);
            mRelativeLayoutTop.setVisibility(View.VISIBLE);
            mRelativeLayout.setBackgroundResource(R.color.white);
        } else if (y>height&&y<10*height) {
            mTextViewShopping.setTextColor(0xFF49C9BB);
            mTextViewAppraise.setTextColor(0xFF313131);
            mTextViewParticulars.setTextColor(0xFF313131);
        } else {
            if (y > 10 * height && y < top_height) {
                mRelativeLayout.setBackgroundResource(R.color.white);
                mTextViewParticulars.setTextColor(0xFF49C9BB);
                mTextViewShopping.setTextColor(0xFF313131);
                mTextViewAppraise.setTextColor(0xFF313131);
//            float alpha = (10);//0~255    完全透明~不透明
//            //4个参数，第一个是透明度，后三个是红绿蓝三元色参数
            } else if (y >= top_height) {
                mTextViewAppraise.setTextColor(0xFF49C9BB);
                mTextViewShopping.setTextColor(0xFF313131);
                mTextViewParticulars.setTextColor(0xFF313131);
            }
        }
    }
    @Override
    public void onScroll(int scrollY) {
    }
    /**
     * @param id 商品的id
     */
    private void getDetail(String id){
        showLoading();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", id);
        HttpHelper.getInstance().post(Url.GOODSC, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                hiddenLoading();
                StoreDetail storeDetail = JsonUtils.parse(response, StoreDetail.class);
                if (storeDetail.getCode() == 1) {
                    if (storeDetail.getDatas().getPayOnDelivery() == 1) {
                        mTextView.setText("货到付款");
                    } else {
                        mTextView.setText("暂不支持货到付款");
                    }
                    if (storeDetail.getDatas().isSpecification()) {
                        specification = 1;
                    } else {
                        specification = 0;
                    }
                    mShopName = storeDetail.getDatas().getShopName();
                    mShopCode=storeDetail.getDatas().getGoodsCode();
                    mAppraise.setText("("+storeDetail.getDatas().getUserCustomersSum()+")");
                    mNum.setText(storeDetail.getDatas().getShopGraded());
                    mLocation.setText(storeDetail.getDatas().getShopAddress());
                    mPhone = storeDetail.getDatas().getPhone();
                    mGoodsPic=storeDetail.getDatas().getMainImage();
                    mGoodsNum = String.valueOf(storeDetail.getDatas().getInventory());
                    mDetail_1.setText(storeDetail.getDatas().getRemark().get(0));
                    mDetail_2.setText(storeDetail.getDatas().getRemark().get(1));
                    mDetail_3.setText(storeDetail.getDatas().getRemark2().get(0));
                    mDetail_4.setText(storeDetail.getDatas().getRemark2().get(1));
                    memberPrice.setText(storeDetail.getDatas().getMemberPrice());

                    marketPrice.setText("￥"+storeDetail.getDatas().getMarketPrice());
                    marketPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
                    mIntegral.setText("可抢红包"+storeDetail.getDatas().getIntegral());
                    mGoodsName = storeDetail.getDatas().getGoodsName();
                    mGoodsDetail.setText(storeDetail.getDatas().getGoodsName()+storeDetail.getDatas().getSummary());
                    mCommentsSumdatasBeen.addAll(storeDetail.getDatas().getCommentsSumdatas());
                    initLabel();
                    Document doc= Jsoup.parse(storeDetail.getDatas().getDetails());
                    Elements elements=doc.getElementsByTag("img");
                    for (Element element : elements) {
                        element.attr("style","width:100%").attr("height","auto");
                    }
                    LogUtils.d("aaaaaaaaaaaaa"+doc.toString());
                    mWebView.loadDataWithBaseURL(null,doc.toString(),"text/html","utf-8", null);
                    if (storeDetail.getDatas().getCustomersdatas().size() > 1) {
                        for (int i = 0; i <2 ; i++) {
                            mCustomersdatasBeen.add(storeDetail.getDatas().getCustomersdatas().get(i));
                        }
                    } else {
                        mCustomersdatasBeen.addAll(storeDetail.getDatas().getCustomersdatas());
                    }
                    mCommentAdapter.update(mCustomersdatasBeen);
                    if (!TextUtils.isEmpty(storeDetail.getDatas().getIntegral())) {
                        String d[] = storeDetail.getDatas().getMainImage().split(",");
                        for (int i = 0; i <d.length ; i++) {
                            mList.add(d[i]);/*轮播图集合*/
                        }
                        initArgs();
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                hiddenLoading();
                StringUtils.showToast(getActivity(),error_msg);
            }
        });}
    private void initLabel() {
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(30, 30, 10, 10);// 设置边距
        for (int i = 0; i < mCommentsSumdatasBeen.size(); i++) {
            final TextView textView = new TextView(getContext());
            textView.setTag(i);
            textView.setTextSize(15);
            textView.setText(mCommentsSumdatasBeen.get(i).getCommentsType()+"("+mCommentsSumdatasBeen.get(i).getCommentsSum()+")");
            textView.setPadding(24, 11, 24, 11);
            textView.setTextColor(0xFFFF8D5F);
            textView.setBackgroundResource(R.drawable.lable_item_normal);
            mFlowLayout.addView(textView, layoutParams);
            // 标签点击事件
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    ShowFragmentUtils.showFragment(getActivity(),UserEvaluationFragment.class, FragmentTag.USEREVALUATIONFRAGMENT,null,true);
                }
            });
        }
    }
    private void initArgs() {
        isRunning = true;
        for (int i = 0; i < mList.size(); i++) {
            ImgTextView imageTextView = new ImgTextView(getContext());
            imageTextView.setSource(mList.get(i),getActivity());
            mImgTextViews.add(imageTextView);
            /*滚动点，运动轨迹*/
            ImageView point = new ImageView(getContext());
            point.setBackgroundResource(R.drawable.viewpager_point);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.rightMargin = DensityUtils.dp2px(getContext(), 10f);
            params.bottomMargin = DensityUtils.dp2px(getContext(), 10f);
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
            }
        }));
        // 设置动画
        ViewPagerScroller scroller = new ViewPagerScroller(getContext());
        // 控制自动播放时时间
        scroller.setScrollDuration(delay);
        scroller.initViewPagerScroll(mViewPagerSlider);
        mHandler.sendEmptyMessageDelayed(0, delay);
    }
    /**
     * @param id 商品规格
     */
    private void getSpecification(String id){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("goodid", id);
        HttpHelper.getInstance().post(Url.GETSPECIFICATION, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                LogUtils.d("集合"+response);
                dateModle = JsonUtils.parse(response, GetSpecification.class);
                GetSpecification.DatasBean datasBean = JsonUtils.parse(response, GetSpecification.DatasBean.class);
                Dialog();
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                StringUtils.showToast(getActivity(),error_msg);
            }
        });
    }
    //自定义dialog弹窗
    public void Dialog(){
        dialog = new CustomDialog(getContext(),R.style.Dialog);//设置dialog的样式
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.mystyle); // 添加动画
        dialog.show();
        WindowManager m = getActivity().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams lp = window.getAttributes();
        //这句就是设置dialog横向满屏了。
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = (int) (d.getHeight()*0.7);     //dialog屏幕占比
        window.setAttributes(lp);
        ListView dialog_listView = (ListView) dialog.findViewById(R.id.dialog_listView);
        LinearLayout dialog_confirm_ll = (LinearLayout) dialog.findViewById(R.id.dialog_confirm_ll);
        LinearLayout dialog_confirm_buy = (LinearLayout) dialog.findViewById(R.id.dialog_confirm_buy);
        RelativeLayout custom_dialog_close = (RelativeLayout) dialog.findViewById(R.id.custom_dialog_close);
        final TextView dialog_goods_name = (TextView) dialog.findViewById(R.id.dialog_goods_name);
        dialog_img = (ImageView) dialog.findViewById(R.id.dialog_img);
        dialog_goods_price = (TextView) dialog.findViewById(R.id.dialog_goods_price);
        final TextView dialog_goods_nmb = (TextView) dialog.findViewById(R.id.dialog_goods_nmb);
        tv_item_minus_comm_detail = (TextView) dialog.findViewById(R.id.tv_item_minus_comm_detail);
        tv_item_number_comm_detail = (TextView) dialog.findViewById(R.id.tv_item_number_comm_detail);
        tv_item_add_comm_detail = (TextView) dialog.findViewById(R.id.tv_item_add_comm_detail);
        goods_type = (TextView) dialog.findViewById(R.id.goods_type);
        dialog_goods_name.setText("商品价格: "+memberPrice.getText().toString());
        dialog_goods_price.setText("库存： "+mGoodsNum);
        dialog_goods_nmb.setText("商品编号： "+mShopCode);
            Glide.with(getContext())
                    .load(mGoodsPic)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(dialog_img);
//        PayMent(string);
        Handler mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case  SWITCH_PAGE:
                        //接收dialog点击以后得返回数据
                        String type = msg.getData().getString("type");
                        String type_name = msg.getData().getString("type_name");
                        if (!TextUtils.isEmpty(msg.getData().getString("type_url"))) {
                            type_url = msg.getData().getString("type_url");
                        }
                        if (!TextUtils.isEmpty(msg.getData().getString("type_two"))) {
                            type_two = msg.getData().getString("type_two");
                        }
                        if (dateModle.getDatas().size()>1) {
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("goodid",mArguments.getString("goodsId"));
                            hashMap.put("propertiesid",dateModle.getDatas().get(0).getId()+":"+type_url+","+ dateModle.getDatas().get(1).getId()+":"+type_two);
                            LogUtils.d("这是"+type_url);
                            LogUtils.d("这是2="+type_two);
                            HttpHelper.getInstance().post(Url.GETGOODSPECIFICATION, hashMap, new RawResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, String response) {
                                    LogUtils.d("集合"+response);
                                    GetGoodSpecification bean = JsonUtils.parse(response, GetGoodSpecification.class);
                                    GetGoodSpecification.DatasBean datasBean = JsonUtils.parse(response, GetGoodSpecification.DatasBean.class);
                                    if (bean.getCode()==1) {
                                        mPropertiesid = String.valueOf(bean.getDatas().getId());
                                        LogUtils.d("规格"+mPropertiesid);
                                        Glide.with(getContext()).load(bean.getDatas().getImage()).signature(new StringSignature(UUID.randomUUID().toString())) .placeholder(R.drawable.ic_launcher).into(dialog_img);
                                        dialog_goods_name.setText(bean.getDatas().getMemberPrice());
                                        dialog_goods_price.setText("库存： "+bean.getDatas().getIntegral());
                                        mBig=bean.getDatas().getMaketPirce();
                                        mSmall= bean.getDatas().getMemberPrice();
                                        mDetail= bean.getDatas().getPropertiesName();
                                        mGoodsPic = bean.getDatas().getImage();

                                    } else if (bean.getCode()==0) {
                                    }
                                }
                                @Override
                                public void onFailure(int statusCode, String error_msg) {
                                    StringUtils.showToast(getActivity(),error_msg);
                                }
                            });
                        } else if (dateModle.getDatas().size() < 1) {

                        } else {
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("goodid", mArguments.getString("goodsId"));
                            hashMap.put("propertiesid", dateModle.getDatas().get(0).getId() + ":" + type_url);
                            LogUtils.d("这是" + type_url);
                            LogUtils.d("这是2=" + type_two);
                            HttpHelper.getInstance().post(Url.GETGOODSPECIFICATION, hashMap, new RawResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, String response) {
                                    LogUtils.d("集合" + response);
                                    GetGoodSpecification bean = JsonUtils.parse(response, GetGoodSpecification.class);
                                    GetGoodSpecification.DatasBean datasBean = JsonUtils.parse(response, GetGoodSpecification.DatasBean.class);
                                    if (bean.getCode() == 1) {
                                        mPropertiesid = String.valueOf(bean.getDatas().getId());
                                        LogUtils.d("规格" + mPropertiesid);
                                        Glide.with(getContext()).load(bean.getDatas().getImage()).signature(new StringSignature(UUID.randomUUID().toString())).placeholder(R.drawable.ic_launcher).into(dialog_img);
                                        dialog_goods_name.setText(bean.getDatas().getMemberPrice());
                                        dialog_goods_price.setText("库存： "+bean.getDatas().getIntegral());
                                        mBig=bean.getDatas().getMaketPirce();
                                       mSmall= bean.getDatas().getMemberPrice();
                                       mDetail= bean.getDatas().getPropertiesName();
                                        mGoodsPic = bean.getDatas().getImage();
//                                        dialog_goods_nmb.setText("库存： " + bean.getDatas().getIntegral());
                                    } else if (bean.getCode() == 0) {

                                    }
                                }
                                @Override
                                public void onFailure(int statusCode, String error_msg) {
                                    StringUtils.showToast(getActivity(), error_msg);
                                }
                            });
                        }
                        break;
                }}};
        comListviewAdapter = new ComListviewAdapter(dateModle,getContext(),mHandler);
        dialog_listView.setAdapter(comListviewAdapter);
        custom_dialog_close.setOnClickListener(new DialogClick());
        tv_item_minus_comm_detail.setOnClickListener(new DialogClick());
        tv_item_add_comm_detail.setOnClickListener(new DialogClick());
        dialog_confirm_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*加入购物车*/
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
                hashMap.put("shopId", "1");
                hashMap.put("goodsId",mArguments.getString("goodsId"));
                hashMap.put("count", tv_item_number_comm_detail.getText().toString());
                hashMap.put("specificationId", mPropertiesid);
                HttpHelper.getInstance().post(Url.addUserShoppingCart, hashMap, new RawResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response) {
                        LogUtils.d("集合"+response);
                        GetGoodSpecification bean = JsonUtils.parse(response, GetGoodSpecification.class);
                        GetGoodSpecification.DatasBean datasBean = JsonUtils.parse(response, GetGoodSpecification.DatasBean.class);
                        if (bean.getCode() == 1) {
                            StringUtils.showToast(getActivity(),bean.getMsg());
                            EventBus.getDefault().post(new UpdateCardList("购物车"));
                            dialog.dismiss();
                        } else if (bean.getCode()==0) {
                            StringUtils.showToast(getActivity(),bean.getMsg());
                        }}
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        StringUtils.showToast(getActivity(),error_msg);
                    }
                });
            }
        });
        dialog_confirm_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (specification==0) {
                    mArguments.putString("goodsNum",tv_item_number_comm_detail.getText().toString());
                    mArguments.putString("goodsState",mTextView.getText().toString());
                    mArguments.putString("shopName",mShopName);
                    mArguments.putString("goodsName",mGoodsName);
                    mArguments.putString("goodsUrl",mGoodsPic);
                    mArguments.putString("goodsId",mArguments.getString("goodsId"));
                    mArguments.putString("goodsDetail",mGoodsDetail.getText().toString());
                    mArguments.putString("goodsPrise",memberPrice.getText().toString());
                    mArguments.putString("goodsMarket",marketPrice.getText().toString());
                    mArguments.putString("specificationId",mPropertiesid);
                    ShowFragmentUtils.showFragment(getActivity(),ConfirmOrderTwoFragment.class,FragmentTag.CONFIRMORDERTWOFRAGMENT,mArguments,true);
                } else if (specification==1) {
                    mArguments.putString("goodsNum",tv_item_number_comm_detail.getText().toString());
                    mArguments.putString("goodsState",mTextView.getText().toString());
                    mArguments.putString("shopName",mShopName);
                    mArguments.putString("goodsName",mGoodsName);
                    mArguments.putString("goodsDetail",mDetail);
                    mArguments.putString("goodsUrl",mGoodsPic);
                    mArguments.putString("goodsId",mArguments.getString("goodsId"));
                    mArguments.putString("goodsPrise",mSmall);
                    mArguments.putString("goodsMarket",mBig);
                    mArguments.putString("specificationId",mPropertiesid);
                    ShowFragmentUtils.showFragment(getActivity(),ConfirmOrderTwoFragment.class,FragmentTag.CONFIRMORDERTWOFRAGMENT,mArguments,true);}
                dialog.dismiss();
            }
        });
    }
    public class DialogClick implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.custom_dialog_close:
                    dialog.dismiss();
                    break;
                case R.id.tv_item_minus_comm_detail:
                    //减少购买数量
                    goods_nmb--;
                    if (goods_nmb<1){
                        Toast.makeText(getActivity(),"已是最低购买量",Toast.LENGTH_SHORT).show();
                    }else {
                        tv_item_number_comm_detail.setText(String.valueOf(goods_nmb));
                    }
                    break;
                case R.id.tv_item_add_comm_detail:
                    //增加购买数量
                    goods_nmb++;
                    tv_item_number_comm_detail.setText(String.valueOf(goods_nmb));
                    break;
            }
        }
    }
}
