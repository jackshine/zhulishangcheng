package com.example.ddm.appui.home;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.adapter.CommentAdapter;
import com.example.ddm.appui.adapter.CommonAdapter;
import com.example.ddm.appui.adapter.ViewHolder;
import com.example.ddm.appui.bean.ShopDetail;
import com.example.ddm.appui.constant.FragmentTag;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.gaode.GaoDeMapActivity;
import com.example.ddm.appui.summary.detail.GoodsFragment;
import com.example.ddm.appui.summary.detail.GoodsTwoFragment;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.ShowFragmentUtils;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.appui.view.CircleFlowIndicator;
import com.example.ddm.appui.view.ListViewForScrollView;
import com.example.ddm.appui.view.ViewFlow;
import com.example.ddm.appui.view.ZFlowLayout;
import com.example.ddm.appui.widget.ImgTextView;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.ddm.appui.home.ShoppingDetailFragment.REQUEST_CODE_ASK_CALL_PHONE;

/**
 * A simple {@link Fragment} subclass.
 * 新商铺详情
 */
public class ShopDetailFragment extends BaseFragment {
    private ImageView mImageView;/*主图*/
    private TextView mShopName;
    private TextView mAddress;
    private String mLocation,mPhoneNum;
    private LinearLayout mLayout;/*位置*/
    private TextView mPhone;
    private ListViewForScrollView mListViewForScrollView;
    private ListViewForScrollView mListViewForScrollView1;
    private RelativeLayout mRelativeLayout;
    private TextView mShopNum;
    private TextView mNum;/*评分*/
    private TextView mAppraise;
    private ZFlowLayout mFlowLayout;
    private WebView mWebView;
    private List<ShopDetail.DatasBean.CommentsSumdatasBean> mCommentsSumdatasBeen = new ArrayList<>();
    private List<ShopDetail.DatasBean.CustomersdatasBean> mCustomersdatasBeen = new ArrayList<>();
    private List<ShopDetail.DatasBean.GoodsDatasBean> mList = new ArrayList<>();
    private CommonAdapter<ShopDetail.DatasBean.GoodsDatasBean> mAdapter;
    private CommentAdapter mCommentAdapter;
    public ShopDetailFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shop_detail, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mPhone = mFindViewUtils.findViewById(R.id.phone);
        mLayout = mFindViewUtils.findViewById(R.id.location);
        mWebView = mFindViewUtils.findViewById(R.id.webview);
        mAppraise = mFindViewUtils.findViewById(R.id.appraise);
        mListViewForScrollView = mFindViewUtils.findViewById(R.id.list_view);
        mListViewForScrollView1 = mFindViewUtils.findViewById(R.id.list_view1);
        mImageView = mFindViewUtils.findViewById(R.id.iv);
        mShopName = mFindViewUtils.findViewById(R.id.shop_name);
        mAddress = mFindViewUtils.findViewById(R.id.address);
        mFlowLayout = mFindViewUtils.findViewById(R.id.flowLayout);
        mRelativeLayout = mFindViewUtils.findViewById(R.id.Relative);
        mShopNum = mFindViewUtils.findViewById(R.id.shop_num);
        mNum = mFindViewUtils.findViewById(R.id.num);
    }
    /*评论*/
    @Override
    protected void setListener() {
        super.setListener();
        mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBaseActivity.showActivity(GaoDeMapActivity.class,null);
            }
        });
        mPhone.setOnClickListener(new View.OnClickListener() {
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
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mPhoneNum));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                } else {
                    // 上面已经写好的拨号方法 callDirectly(mobile);
                    Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + mPhoneNum));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
        mAdapter = new CommonAdapter<ShopDetail.DatasBean.GoodsDatasBean>(getContext(),null,R.layout.shopping_item_list) {
            @Override
            public void setViewData(ViewHolder holder, ShopDetail.DatasBean.GoodsDatasBean item, int position) {
                ImageView view = holder.getView(R.id.pic);
                if (mList.size() <= 1) {
                    mRelativeLayout.setVisibility(View.GONE);
                    Glide.with(getContext()).load(item.getMainImage()).placeholder(R.drawable.ic_launcher).crossFade().into(view);
                    holder.setText(item.getMarketPrice(), R.id.money2).setText(item.getMemberPrice(), R.id.money).setText(item.getName(), R.id.shop_name);
                } else {
                    if (position <= 1) {
                        mRelativeLayout.setVisibility(View.VISIBLE);
                        mShopNum.setText("其他" + (mList.size() - 1) + "件商品");
                        Glide.with(getContext()).load(item.getMainImage()).placeholder(R.drawable.ic_launcher).crossFade().into(view);
                        holder.setText(item.getMarketPrice(), R.id.money2).setText(item.getMemberPrice(), R.id.money).setText(item.getName(), R.id.shop_name);
                    } else {
                        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        });
                    }
                }
            }
        };
        mCommentAdapter = new CommentAdapter(getContext(), null);
        mListViewForScrollView.setAdapter(mAdapter);
        mListViewForScrollView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mArguments.putString("goodsId", String.valueOf(mList.get(position).getId()));
                ShowFragmentUtils.showFragment(getActivity(), GoodsTwoFragment.class,FragmentTag.GOODSTWOFRAGMENT,mArguments,true);
            }
        });
        mListViewForScrollView1.setAdapter(mCommentAdapter);
        getShopDetail("1");
//        getShopDetail("1");
    }
    @Override
    protected void setData() {
        super.setData();
    }
    // 初始化标签
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
                    ShowFragmentUtils.showFragment(getActivity(),UserEvaluationFragment.class,FragmentTag.USEREVALUATIONFRAGMENT,null,true);
                }
            });
        }
    }
    /**
     * @param id 店铺详情接口
     */
   private void getShopDetail(String id){
       showLoading();
       HashMap<String, String> hashMap = new HashMap<>();
       hashMap.put(IAppKey.SHOPID, id);
       HttpHelper.getInstance().post(Url.SHOPDETAILED, hashMap, new RawResponseHandler() {
           @Override
           public void onSuccess(int statusCode, String response) {
               hiddenLoading();
               LogUtils.d("集合"+response);
               final ShopDetail shopDetail = JsonUtils.parse(response, ShopDetail.class);
               if (shopDetail.getCode()==1) {
                   Glide.with(getContext()).load(shopDetail.getDatas().getMainImg()).placeholder(R.drawable.ic_launcher).crossFade().into(mImageView);
                   mShopName.setText(shopDetail.getDatas().getShopName());
                   mAddress.setText(shopDetail.getDatas().getShopAddress());
                   mLocation = shopDetail.getDatas().getAddress();
                   mNum.setText(shopDetail.getDatas().getShopGraded());
                   mPhoneNum = shopDetail.getDatas().getPhone();
                   mAppraise.setText("("+shopDetail.getDatas().getUserCustomersSum()+")");
                   Document doc= Jsoup.parse(shopDetail.getDatas().getDetailImg());
                   Elements elements=doc.getElementsByTag("img");
                   for (Element element : elements) {
                       element.attr("style","width:100%").attr("height","auto");
                   }
                   LogUtils.d("aaaaaaaaaaaaa"+doc.toString());
                   mWebView.loadDataWithBaseURL(null,doc.toString(),"text/html","utf-8", null);
                   mList.addAll(shopDetail.getDatas().getGoodsDatas());
                   mCommentsSumdatasBeen.addAll(shopDetail.getDatas().getCommentsSumdatas());
                   if (shopDetail.getDatas().getCustomersdatas().size() > 1) {
                       for (int i = 0; i <2 ; i++) {
                           mCustomersdatasBeen.add(shopDetail.getDatas().getCustomersdatas().get(i));
                       }
                   } else {
                       mCustomersdatasBeen.addAll(shopDetail.getDatas().getCustomersdatas());
                   }
                   mCommentAdapter.update(mCustomersdatasBeen);
                   initLabel();
                  mAdapter.update(mList);
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
