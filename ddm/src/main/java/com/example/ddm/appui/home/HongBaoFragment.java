package com.example.ddm.appui.home;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.adapter.CommonAdapter;
import com.example.ddm.appui.adapter.ViewHolder;
import com.example.ddm.appui.bean.IntegralBean;
import com.example.ddm.appui.bean.SendCodeBean;
import com.example.ddm.appui.constant.FragmentTag;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.ShowFragmentUtils;
import com.example.ddm.appui.view.CircleImageView;
import com.example.ddm.appui.view.HMyScrollView;
import com.example.ddm.appui.view.ListViewForScrollView;
import com.example.ddm.appui.view.MyScrollView;
import com.example.ddm.appui.view.ObservableScrollView;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.editorpage.ShareActivity;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * 红包详情
 */
public class HongBaoFragment extends BaseFragment implements HMyScrollView.OnScrollListener {
    private TextView mBack;
    private TextView mMoney,mContent;
    private String mString;
    private Button mBtn1, mBtn2,mBtn3,mBtn4;
    private ListViewForScrollView mListView;
    private HMyScrollView myScrollView;
    private RelativeLayout mRelativeLayout, mRelativeLayout1;
    private ShareAction mShareAction;
    private UMShareListener mShareListener;
    private CommonAdapter<IntegralBean.DatasBean> mAdapter;
    private List<String> mList = new ArrayList<>();
    private List<IntegralBean.DatasBean> mDatasBeanList = new ArrayList<>();
    public HongBaoFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hong_bao, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mRelativeLayout = mFindViewUtils.findViewById(R.id.Relative);
        mRelativeLayout1 = mFindViewUtils.findViewById(R.id.Relative1);
        myScrollView = mFindViewUtils.findViewById(R.id.scrollView);
        myScrollView.setOnScrollListener(this);
        mBack = mFindViewUtils.findViewById(R.id.app_title_back);
        mMoney = mFindViewUtils.findViewById(R.id.money);
        mContent = mFindViewUtils.findViewById(R.id.share);
        mListView = mFindViewUtils.findViewById(R.id.list_view);
        mBtn1 = mFindViewUtils.findViewById(R.id.btn1);
        mBtn2 = mFindViewUtils.findViewById(R.id.btn2);
        mBtn3 = mFindViewUtils.findViewById(R.id.btn3);
        mBtn4 = mFindViewUtils.findViewById(R.id.btn4);
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
        mShareListener = new CustomShareListener(this);
        mShareAction = new ShareAction(getActivity()).setDisplayList(
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                        UMImage image = new UMImage(getContext(), R.drawable.share_hongbao);//资源文件
                        image.setThumb(new UMImage(getContext(), R.drawable.share_hongbao));
                        new ShareAction(getActivity()).withText("hello").withMedia(image).setPlatform(share_media).setCallback(mShareListener).share();
//                            UMWeb web = new UMWeb(Url.SHARE+PreferenceManager.instance().getShopId()+"&useId=");
//
//                            web.setTitle("分享一个产品，代购整座城市"+"\n"+"消费商—"+PreferenceManager.instance().getName()+"的店");
//                            web.setDescription("同城优选，助利商城");
//                            web.setThumb(new UMImage(getContext(), R.mipmap.logo_share));
//                            new ShareAction(getActivity()).withMedia(web)
//                                    .setPlatform(share_media)
//                                    .setCallback(mShareListener)
//                                    .share();
                    }
                });
    }
    @Override
    protected void setData() {
        super.setData();
        getIntegralNum();
        getActivity().findViewById(R.id.parent_layout).getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                onScroll(myScrollView.getScrollY());
                System.out.println(myScrollView.getScrollY());
            }
        });
    }
    private void getIntegralNum() {
        showLoading();
        Map<String, String> param = new HashMap<>();
        param.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        HttpHelper.getInstance().post(Url.HONGBAO, param, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.d("wyt", "获取首页分类成功：" + response);
                hiddenLoading();
                IntegralBean category = JsonUtils.parse(response, IntegralBean.class);
                LogUtils.d(response);
                if (category.getCode()==1) {
                    mMoney.setText(category.getDatas().getIntegral());
                      mString= category.getDatas().getVal();
                        String d[] = mString.split(":");
                    mBtn1.setText("共抢到"+(d.length-2)+"个红包");
                    mBtn3.setText("共抢到"+(d.length-2)+"个红包");
                    if (category.getDatas().isShare()) {
                        mBtn2.setText("分享"+"\n"+"此红包需要分享后存入账户");
                        mBtn4.setText("分享"+"\n"+"此红包需要分享后存入账户");
                        mContent.setText("此红包需分享后存入账户");
                        mBtn2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mShareAction.open();
                            }
                        });
                        mBtn4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mShareAction.open();
                            }
                        });

                    } else {
                        getIntegralSure();
                        mBtn2.setText("确定");
                        mBtn4.setText("确定");
                        mContent.setText("已存入账户，可提现");
                        mBtn2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popSelf();
                            }
                        });
                        mBtn4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popSelf();
                            }
                        });
                    }
                        for (int i = 2; i <d.length ; i++) {
                            mList.add(d[i]);
                        }
                        LogUtils.d("集合"+mList);
                    for (int i = 2; i <d.length ; i++) {
                        mDatasBeanList.add(category.getDatas());
                    }
                        LogUtils.d("集合2"+mDatasBeanList);
//                        mAdapter.update(mDatasBeanList);
                        mAdapter = new CommonAdapter<IntegralBean.DatasBean>(getContext(),mDatasBeanList,R.layout.hongbao_item) {
                            @Override
                            public void setViewData(ViewHolder holder, IntegralBean.DatasBean item, int position) {
                                ImageView view = holder.getView(R.id.pic_sp);
                                Glide.with(getContext()).load(item.getAvotor()).placeholder(R.drawable.ic_launcher).crossFade().into(view);
                                holder.setText(item.getUserName(), R.id.integral).setText(item.getTime(),R.id.content).setText(mList.get(position)+"元",R.id.time);
                            }
                        };
                        mListView.setAdapter(mAdapter);
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                Log.d("wyt", "获取首页分类失败：" + error_msg);
                hiddenLoading();
            }
        });
    }
    /**
     * 确认已领积分的接口
     */
    private void getIntegralSure() {
        Map<String, String> param = new HashMap<>();
        param.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        HttpHelper.getInstance().post(Url.HONGBAOSURE, param, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.d("wyt", "获取首页分类成功：" + response);
                SendCodeBean category = JsonUtils.parse(response, SendCodeBean.class);
                if (category.getCode()==1) {
                    Toast.makeText(mBaseActivity, "红包已领取", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                Log.d("wyt", "获取首页分类失败：" + error_msg);
            }
        });
    }
    /**
     * 确认已领积分的接口
     */
    private void getIntegralSure1() {
        Map<String, String> param = new HashMap<>();
        param.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        HttpHelper.getInstance().post(Url.HONGBAOSURE, param, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.d("wyt", "获取首页分类成功：" + response);
                SendCodeBean category = JsonUtils.parse(response, SendCodeBean.class);
                if (category.getCode()==1) {
                    Toast.makeText(mBaseActivity, "红包已领取", Toast.LENGTH_SHORT).show();
                    popSelf();
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                Log.d("wyt", "获取首页分类失败：" + error_msg);
            }
        });
    }
    @Override
    public void onScroll(int scrollY) {
        int mBuyLayout2ParentTop = Math.max(scrollY, mRelativeLayout1.getTop());
        mRelativeLayout.layout(0, mBuyLayout2ParentTop, mRelativeLayout.getWidth(), mBuyLayout2ParentTop + mRelativeLayout.getHeight());
    }
    private static class CustomShareListener implements UMShareListener {
        private WeakReference<HongBaoFragment> mActivity;
        private CustomShareListener(HongBaoFragment activity) {
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
                    mActivity.get().getIntegralSure1();
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
}
