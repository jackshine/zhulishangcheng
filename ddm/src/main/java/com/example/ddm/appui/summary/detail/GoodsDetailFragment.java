package com.example.ddm.appui.summary.detail;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
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
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ddm.MainActivity;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.adapter.ISliderAdapter;
import com.example.ddm.appui.adapter.SliderAdapter;
import com.example.ddm.appui.adapter.ViewPagerScroller;
import com.example.ddm.appui.bean.StoreDetail;
import com.example.ddm.appui.constant.FragmentTag;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.home.HomeFragment;
import com.example.ddm.appui.home.SliderFragment;
import com.example.ddm.appui.utils.DensityUtils;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.ShowFragmentUtils;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.appui.widget.ImgTextView;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.ddm.appui.home.ShoppingDetailFragment.REQUEST_CODE_ASK_CALL_PHONE;

/**
 * A simple {@link Fragment} subclass.
 * 商品详情顶部
 */
public class GoodsDetailFragment extends BaseFragment {
    private ViewPager mViewPagerSlider;
    private LinearLayout mPoints;
    private boolean isRunning;
    private int lastPosition;
    private TextView mTextView;/*货到付款*/
    private TextView mGoodsDetail;
    private TextView mIntegral;
    private TextView mLocation;
    private TextView marketPrice;/*会员价*/
    private TextView memberPrice;/*真实价格*/
    private TextView mDetail_1,mDetail_2,mDetail_3, mDetail_4;
    private List<ImgTextView> mImgTextViews=new ArrayList<>();
    private List<String> mList = new ArrayList<>();/*轮播图集合*/
    private String mPhone;/*商家电话*/
    private ImageView mImageViewPhone;
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
    public GoodsDetailFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_goods_detail, container, false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mLocation = mFindViewUtils.findViewById(R.id.address);
        mTextView = mFindViewUtils.findViewById(R.id.text_1);
        mImageViewPhone = mFindViewUtils.findViewById(R.id.phone);
        mDetail_1 = mFindViewUtils.findViewById(R.id.detail_1);
        mDetail_2 = mFindViewUtils.findViewById(R.id.detail_2);
        mDetail_3 = mFindViewUtils.findViewById(R.id.detail_3);
        mDetail_4 = mFindViewUtils.findViewById(R.id.detail_4);
        memberPrice = mFindViewUtils.findViewById(R.id.ture_money);
        marketPrice = mFindViewUtils.findViewById(R.id.member);
        mIntegral = mFindViewUtils.findViewById(R.id.qiang_hongbao);
        mGoodsDetail = mFindViewUtils.findViewById(R.id.shop_detail);
        mViewPagerSlider = mFindViewUtils.findViewById(R.id.banner_viewpager);
        mPoints = mFindViewUtils.findViewById(R.id.banner_viewpager_points);
        getDetail("2");
    }

    @Override
    protected void setListener() {
        super.setListener();
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
    }
    @Override
    protected void setData() {
        super.setData();
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
                    mLocation.setText(storeDetail.getDatas().getShopAddress());
                    mPhone = storeDetail.getDatas().getPhone();
                    mDetail_1.setText(storeDetail.getDatas().getRemark().get(0));
                    mDetail_2.setText(storeDetail.getDatas().getRemark().get(1));
                    mDetail_3.setText(storeDetail.getDatas().getRemark2().get(0));
                    mDetail_4.setText(storeDetail.getDatas().getRemark2().get(1));
                    memberPrice.setText(storeDetail.getDatas().getMemberPrice());
                    memberPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
                    marketPrice.setText(storeDetail.getDatas().getMarketPrice());
                    mIntegral.setText("可抢红包"+storeDetail.getDatas().getIntegral());
                    mGoodsDetail.setText(storeDetail.getDatas().getGoodsName()+storeDetail.getDatas().getSummary());
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
        });
    }

    private void initArgs() {
        isRunning = true;
//        types = new String[]{"blue", "red", "blue", "red","blue","red"};
//        tips = new String[]{"叮当猫", "我的最爱", "我在助利商城", "等你!","OneDay","不见不散"};
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
}
