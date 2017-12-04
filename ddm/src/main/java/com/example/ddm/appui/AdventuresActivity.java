package com.example.ddm.appui;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.example.ddm.LoginActivity;
import com.example.ddm.R;
import com.example.ddm.appui.adapter.CommonAdapter;
import com.example.ddm.appui.adapter.ViewHolder;
import com.example.ddm.appui.bean.DaZhuangPanMessage;
import com.example.ddm.appui.bean.GetUserIdBean;
import com.example.ddm.appui.bean.RollMessage;
import com.example.ddm.appui.bean.SlyderAdventures;
import com.example.ddm.appui.constant.FragmentTag;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.home.HomeFragment;
import com.example.ddm.appui.home.HongBaoFragment;
import com.example.ddm.appui.utils.DisplayUtils;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.ShowFragmentUtils;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.appui.view.ListViewForScrollView;
import com.example.ddm.appui.view.ObservableScrollView;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;
import com.nineoldandroids.animation.ObjectAnimator;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
/*大转盘*/
public class AdventuresActivity extends BaseActivity {
    private ImageView mImageView;
    private ImageView mPanPic;
    private ObservableScrollView mScrollView;
    private TextView mTop;
    private int userId;
    private LinearLayout mSubmit;
    private PopupWindow mPopupWindow;
    private ShareAction mShareAction;
    private UMShareListener mShareListener;
    private TextView mInvite;/*邀请*/
    private ListViewForScrollView mListViewForScrollView;
    private ViewFlipper mViewFlipper;
    private int mPersonNum;
    private RelativeLayout mRelativeLayout;
    private TextView mInform1,mInform2,mInform3, mInform4,mInform5,mInform6,mInform7,
            mInform8,mInform9,mInform10, mInform11,mInform12,mInform13,mInform14,mInform15,mInform16, mInform17,mInform18,mInform19,mInform20;
    private List<String> mList = new ArrayList<>();
    private CommonAdapter<String> mAdapter;
    private ImageView mImageView1,mImageView2,mImageView3,mImageView4,mImageView5,mImageView6,mImageView7,mImageView8,mImageView9, mImageView10;
    private TextView mTextView1,mTextView2,mTextView3,mTextView4,mTextView5,mTextView6,mTextView7,mTextView8,mTextView9, mTextView10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adventures);
    }
    @Override
    protected void initView() {
        super.initView();
        mInvite = (TextView) findViewById(R.id.yaoqin);
        mScrollView = (ObservableScrollView) findViewById(R.id.scrollView);
        mTop = (TextView) findViewById(R.id.draw);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.Relative);
        mListViewForScrollView = (ListViewForScrollView) findViewById(R.id.list_view);
        mImageView = (ImageView) findViewById(R.id.id_pin);
        mPanPic = (ImageView) findViewById(R.id.id_pan);
        mViewFlipper = (ViewFlipper) findViewById(R.id.marquee_view);
        mViewFlipper.addView(View.inflate(this, R.layout.noticelayout_news, null));
        mViewFlipper.addView(View.inflate(this, R.layout.noticelayout_news_two, null));
        mInform1 = (TextView) findViewById(R.id.inform1);
        mInform2 = (TextView) findViewById(R.id.inform2);
        mInform3=(TextView) findViewById(R.id.inform3);
        mInform4 = (TextView) findViewById(R.id.inform4);
        mInform5 = (TextView) findViewById(R.id.inform5);
        mInform6 = (TextView) findViewById(R.id.inform6);
        mInform7 = (TextView) findViewById(R.id.inform7);
        mInform8 = (TextView) findViewById(R.id.inform8);
        mInform9 = (TextView) findViewById(R.id.inform9);
        mInform11 =(TextView) findViewById(R.id.inform11);
        mInform12 = (TextView) findViewById(R.id.inform12);
        mInform13 = (TextView) findViewById(R.id.inform13);
        mInform14 = (TextView) findViewById(R.id.inform14);
        mInform15 = (TextView) findViewById(R.id.inform15);
        mInform16 = (TextView) findViewById(R.id.inform16);
        mInform17 = (TextView) findViewById(R.id.inform17);
        mInform18 = (TextView) findViewById(R.id.inform18);
        mInform19 = (TextView) findViewById(R.id.inform19);
        mTextView1 = (TextView) findViewById(R.id.text_1);
        mTextView2 = (TextView) findViewById(R.id.text_2);
        mTextView3 = (TextView) findViewById(R.id.text_3);
        mTextView4 = (TextView) findViewById(R.id.text_4);
        mTextView5 = (TextView) findViewById(R.id.text_5);
        mTextView6 = (TextView) findViewById(R.id.text_6);
        mTextView7 = (TextView) findViewById(R.id.text_7);
        mTextView8 = (TextView) findViewById(R.id.text_8);
        mTextView9 = (TextView) findViewById(R.id.text_9);
        mTextView10 = (TextView) findViewById(R.id.text_10);
        mImageView1 = (ImageView) findViewById(R.id.image1);
        mImageView2 = (ImageView) findViewById(R.id.image2);
        mImageView3 = (ImageView) findViewById(R.id.image3);
        mImageView4 = (ImageView) findViewById(R.id.image4);
        mImageView5 = (ImageView) findViewById(R.id.image5);
        mImageView6 = (ImageView) findViewById(R.id.image6);
        mImageView7 = (ImageView) findViewById(R.id.image7);
        mImageView8 = (ImageView) findViewById(R.id.image8);
        mImageView9 = (ImageView) findViewById(R.id.image9);
        mImageView10 = (ImageView) findViewById(R.id.image10);
    }
    @Override
    protected void setListener() {
        super.setListener();
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lotto();
            }
        });
        mInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareBoardConfig config = new ShareBoardConfig();
                config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_CENTER);
                config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_NONE);
                mShareAction.open(config);
            }
        });
        mShareListener = new CustomShareListener(this);
        mShareAction = new ShareAction(this).setDisplayList(
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA,
                SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.MORE)
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                        UMWeb web = new UMWeb(Url.link+"id="+userId+"&isdzp=true");
                        web.setTitle("快来和我一起在助利商城抢iPhone 8");
                        web.setDescription("同城优选，助利商城");
                        web.setThumb(new UMImage(AdventuresActivity.this, R.mipmap.logo_share));
                        new ShareAction(AdventuresActivity.this).withMedia(web)
                                .setPlatform(share_media)
                                .setCallback(mShareListener)
                                .share();
                    }
                });
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
        mListViewForScrollView.setFocusable(false);
        mAdapter = new CommonAdapter<String>(this,null,R.layout.item_adventures) {
            @Override
            public void setViewData(ViewHolder holder, String item, int position) {
                holder.getView(R.id.pic).setVisibility(View.GONE);
                holder.setText(position+1+"、"+mList.get(position), R.id.tv_search_content);
            }
        };
        mListViewForScrollView.setAdapter(mAdapter);
    }
    @Override
    protected void setData() {
        super.setData();
        getDaMessage();
        getDaNews();
        GetId();
    }
    /**
     * 大转盘详情
     */
    private void getDaMessage(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        HttpHelper.getInstance().post(Url.daZhuangPanMessage, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                DaZhuangPanMessage daZhuangPanMessage = JsonUtils.parse(response, DaZhuangPanMessage.class);
                if (daZhuangPanMessage.isIslogin()) {
                    mRelativeLayout.setVisibility(View.VISIBLE);
                } else {
                    mRelativeLayout.setVisibility(View.GONE);
                }
                Glide.with(AdventuresActivity.this).load(daZhuangPanMessage.getImg()).signature(new StringSignature(UUID.randomUUID().toString())) .placeholder(R.drawable.ic_launcher).into(mPanPic);
                mList.addAll(daZhuangPanMessage.getRemark());
                LogUtils.d("集合"+mList);
                mAdapter.update(mList);
                mPersonNum = daZhuangPanMessage.getInvitationCount();
                if (mPersonNum > 0) {
                    if (mPersonNum%10==0) {
                        getInvite0();
                    } else if (mPersonNum%10==1) {
                        getInvite1();
                    } else if (mPersonNum%10==2) {
                        getInvite2();
                    } else if (mPersonNum%10==3) {
                        getInvite3();
                    } else if (mPersonNum%10==4) {
                        getInvite4();
                    } else if (mPersonNum%10==5) {
                        getInvite5();
                    } else if (mPersonNum%10==6) {
                        getInvite6();
                    } else if (mPersonNum%10==7) {
                        getInvite7();
                    } else if (mPersonNum%10==8) {
                        getInvite8();
                    } else if (mPersonNum%10==9) {
                        getInvite9();
                    }
                } else {
                }

            }
            @Override
            public void onFailure(int statusCode, String error_msg) {

            }
        });
    }
    private void getInvite0(){
        mImageView1.setImageResource(R.mipmap.isb);
        mTextView1.setText((mPersonNum-9)+"、已邀请"+(mPersonNum-9)+"为好友");
        mImageView2.setImageResource(R.mipmap.isb);
        mTextView2.setText((mPersonNum-7)+"、已邀请"+(mPersonNum-7)+"为好友");
        mImageView3.setImageResource(R.mipmap.isb);
        mTextView3.setText((mPersonNum-5)+"、已邀请"+(mPersonNum-5)+"为好友");
        mImageView4.setImageResource(R.mipmap.isb);
        mTextView4.setText((mPersonNum-3)+"、已邀请"+(mPersonNum-3)+"为好友");
        mImageView5.setImageResource(R.mipmap.isb);
        mTextView5.setText((mPersonNum-1)+"、已邀请"+(mPersonNum-1)+"为好友");
        mImageView6.setImageResource(R.mipmap.isb);
        mTextView6.setText((mPersonNum-8)+"、已邀请"+(mPersonNum-8)+"为好友");
        mImageView7.setImageResource(R.mipmap.isb);
        mTextView7.setText((mPersonNum-6)+"、已邀请"+(mPersonNum-6)+"为好友");
        mImageView8.setImageResource(R.mipmap.isb);
        mTextView8.setText((mPersonNum-4)+"、已邀请"+(mPersonNum-4)+"为好友");
        mImageView9.setImageResource(R.mipmap.isb);
        mTextView9.setText((mPersonNum-2)+"、已邀请"+(mPersonNum-2)+"为好友");
        mImageView10.setImageResource(R.mipmap.isb);
        mTextView10.setText((mPersonNum)+"、已邀请"+(mPersonNum)+"为好友");
    }
    private void getInvite1(){
        mImageView1.setImageResource(R.mipmap.isb);/*成功*/
        mTextView1.setText((mPersonNum)+"、已邀请"+(mPersonNum)+"为好友");
//        mImageView2.setImageResource(R.mipmap.isb);
        mTextView2.setText((mPersonNum+2)+"、需要邀请第"+(mPersonNum+2)+"位好友");
//        mImageView3.setImageResource(R.mipmap.isb);
        mTextView3.setText((mPersonNum+4)+"、需要邀请第"+(mPersonNum+4)+"位好友");
//        mImageView4.setImageResource(R.mipmap.isb);
        mTextView4.setText((mPersonNum+6)+"、需要邀请第"+(mPersonNum+6)+"位好友");
//        mImageView5.setImageResource(R.mipmap.isb);
        mTextView5.setText((mPersonNum+8)+"、需要邀请第"+(mPersonNum+8)+"位好友");
//        mImageView6.setImageResource(R.mipmap.isb);
        mTextView6.setText((mPersonNum+1)+"、需要邀请第"+(mPersonNum+1)+"位好友");
//        mImageView7.setImageResource(R.mipmap.isb);
        mTextView7.setText((mPersonNum+3)+"、需要邀请第"+(mPersonNum+3)+"位好友");
//        mImageView8.setImageResource(R.mipmap.isb);
        mTextView8.setText((mPersonNum+5)+"、需要邀请第"+(mPersonNum+5)+"位好友");
//        mImageView9.setImageResource(R.mipmap.isb);
        mTextView9.setText((mPersonNum+7)+"、需要邀请第"+(mPersonNum+7)+"位好友");
//        mImageView10.setImageResource(R.mipmap.isb);
        mTextView10.setText((mPersonNum+9)+"、需要邀请第"+(mPersonNum+9)+"位好友");
    }
    private void getInvite2(){
        mImageView1.setImageResource(R.mipmap.isb);/*成功*/
        mTextView1.setText((mPersonNum-1)+"、已邀请"+(mPersonNum-1)+"为好友");
//        mImageView2.setImageResource(R.mipmap.isb);
        mTextView2.setText((mPersonNum+1)+"、需要邀请第"+(mPersonNum+1)+"位好友");
//        mImageView3.setImageResource(R.mipmap.isb);
        mTextView3.setText((mPersonNum+3)+"、需要邀请第"+(mPersonNum+3)+"位好友");
//        mImageView4.setImageResource(R.mipmap.isb);
        mTextView4.setText((mPersonNum+5)+"、需要邀请第"+(mPersonNum+5)+"位好友");
//        mImageView5.setImageResource(R.mipmap.isb);
        mTextView5.setText((mPersonNum+7)+"、需要邀请第"+(mPersonNum+7)+"位好友");
        mImageView6.setImageResource(R.mipmap.isb);/*成功*/
        mTextView6.setText((mPersonNum)+"、已邀请"+(mPersonNum)+"为好友");
//        mImageView7.setImageResource(R.mipmap.isb);
        mTextView7.setText((mPersonNum+2)+"、需要邀请第"+(mPersonNum+2)+"位好友");
//        mImageView8.setImageResource(R.mipmap.isb);
        mTextView8.setText((mPersonNum+4)+"、需要邀请第"+(mPersonNum+4)+"位好友");
//        mImageView9.setImageResource(R.mipmap.isb);
        mTextView9.setText((mPersonNum+6)+"、需要邀请第"+(mPersonNum+6)+"位好友");
//        mImageView10.setImageResource(R.mipmap.isb);
        mTextView10.setText((mPersonNum+8)+"、需要邀请第"+(mPersonNum+8)+"位好友");
    }
    private void getInvite3(){
        mImageView1.setImageResource(R.mipmap.isb);/*成功*/
        mTextView1.setText((mPersonNum-2)+"、已邀请"+(mPersonNum-2)+"为好友");
        mImageView2.setImageResource(R.mipmap.isb);/*成功*/
        mTextView2.setText((mPersonNum)+"、已邀请"+(mPersonNum)+"为好友");
//        mImageView3.setImageResource(R.mipmap.isb);
        mTextView3.setText((mPersonNum+2)+"、需要邀请第"+(mPersonNum+2)+"位好友");
//        mImageView4.setImageResource(R.mipmap.isb);
        mTextView4.setText((mPersonNum+4)+"、需要邀请第"+(mPersonNum+4)+"位好友");
//        mImageView5.setImageResource(R.mipmap.isb);
        mTextView5.setText((mPersonNum+6)+"、需要邀请第"+(mPersonNum+6)+"位好友");
        mImageView6.setImageResource(R.mipmap.isb);/*成功*/
        mTextView6.setText((mPersonNum-1)+"、已邀请"+(mPersonNum-1)+"为好友");
//        mImageView7.setImageResource(R.mipmap.isb);
        mTextView7.setText((mPersonNum+1)+"、需要邀请第"+(mPersonNum+1)+"位好友");
//        mImageView8.setImageResource(R.mipmap.isb);
        mTextView8.setText((mPersonNum+3)+"、需要邀请第"+(mPersonNum+3)+"位好友");
//        mImageView9.setImageResource(R.mipmap.isb);
        mTextView9.setText((mPersonNum+5)+"、需要邀请第"+(mPersonNum+5)+"位好友");
//        mImageView10.setImageResource(R.mipmap.isb);
        mTextView10.setText((mPersonNum+7)+"、需要邀请第"+(mPersonNum+7)+"位好友");
    }
    private void getInvite4(){
        mImageView1.setImageResource(R.mipmap.isb);/*成功*/
        mTextView1.setText((mPersonNum-3)+"、已邀请"+(mPersonNum-3)+"为好友");
        mImageView2.setImageResource(R.mipmap.isb);/*成功*/
        mTextView2.setText((mPersonNum-1)+"、已邀请"+(mPersonNum-1)+"为好友");
//        mImageView3.setImageResource(R.mipmap.isb);
        mTextView3.setText((mPersonNum+1)+"、需要邀请第"+(mPersonNum+1)+"位好友");
//        mImageView4.setImageResource(R.mipmap.isb);
        mTextView4.setText((mPersonNum+3)+"、需要邀请第"+(mPersonNum+3)+"位好友");
//        mImageView5.setImageResource(R.mipmap.isb);
        mTextView5.setText((mPersonNum+5)+"、需要邀请第"+(mPersonNum+5)+"位好友");
        mImageView6.setImageResource(R.mipmap.isb);/*成功*/
        mTextView6.setText((mPersonNum-2)+"、已邀请"+(mPersonNum-2)+"为好友");
        mImageView7.setImageResource(R.mipmap.isb);/*成功*/
        mTextView7.setText((mPersonNum)+"、已邀请"+(mPersonNum)+"为好友");
//        mImageView8.setImageResource(R.mipmap.isb);
        mTextView8.setText((mPersonNum+2)+"、需要邀请第"+(mPersonNum+2)+"位好友");
//        mImageView9.setImageResource(R.mipmap.isb);
        mTextView9.setText((mPersonNum+4)+"、需要邀请第"+(mPersonNum+4)+"位好友");
//        mImageView10.setImageResource(R.mipmap.isb);
        mTextView10.setText((mPersonNum+6)+"、需要邀请第"+(mPersonNum+6)+"位好友");
    }
    private void getInvite5(){
        mImageView1.setImageResource(R.mipmap.isb);/*成功*/
        mTextView1.setText((mPersonNum-4)+"、已邀请"+(mPersonNum-4)+"为好友");
        mImageView2.setImageResource(R.mipmap.isb);/*成功*/
        mTextView2.setText((mPersonNum-2)+"、已邀请"+(mPersonNum-2)+"为好友");
        mImageView3.setImageResource(R.mipmap.isb);/*成功*/
        mTextView3.setText((mPersonNum)+"、已邀请"+(mPersonNum)+"为好友");
//        mImageView4.setImageResource(R.mipmap.isb);
        mTextView4.setText((mPersonNum+2)+"、需要邀请第"+(mPersonNum+2)+"位好友");
//        mImageView5.setImageResource(R.mipmap.isb);
        mTextView5.setText((mPersonNum+4)+"、需要邀请第"+(mPersonNum+4)+"位好友");
        mImageView6.setImageResource(R.mipmap.isb);/*成功*/
        mTextView6.setText((mPersonNum-3)+"、已邀请"+(mPersonNum-3)+"为好友");
        mImageView7.setImageResource(R.mipmap.isb);/*成功*/
        mTextView7.setText((mPersonNum-1)+"、已邀请"+(mPersonNum-1)+"为好友");
//        mImageView8.setImageResource(R.mipmap.isb);
        mTextView8.setText((mPersonNum+1)+"、需要邀请第"+(mPersonNum+1)+"位好友");
//        mImageView9.setImageResource(R.mipmap.isb);
        mTextView9.setText((mPersonNum+3)+"、需要邀请第"+(mPersonNum+3)+"位好友");
//        mImageView10.setImageResource(R.mipmap.isb);
        mTextView10.setText((mPersonNum+5)+"、需要邀请第"+(mPersonNum+5)+"位好友");
    }
    private void getInvite6(){
        mImageView1.setImageResource(R.mipmap.isb);/*成功*/
        mTextView1.setText((mPersonNum-5)+"、已邀请"+(mPersonNum-5)+"为好友");
        mImageView2.setImageResource(R.mipmap.isb);/*成功*/
        mTextView2.setText((mPersonNum-3)+"、已邀请"+(mPersonNum-3)+"为好友");
        mImageView3.setImageResource(R.mipmap.isb);/*成功*/
        mTextView3.setText((mPersonNum-1)+"、已邀请"+(mPersonNum-1)+"为好友");
//        mImageView4.setImageResource(R.mipmap.isb);
        mTextView4.setText((mPersonNum+1)+"、需要邀请第"+(mPersonNum+1)+"位好友");
//        mImageView5.setImageResource(R.mipmap.isb);
        mTextView5.setText((mPersonNum+3)+"、需要邀请第"+(mPersonNum+3)+"位好友");
        mImageView6.setImageResource(R.mipmap.isb);/*成功*/
        mTextView6.setText((mPersonNum-4)+"、已邀请"+(mPersonNum-4)+"为好友");
        mImageView7.setImageResource(R.mipmap.isb);/*成功*/
        mTextView7.setText((mPersonNum-2)+"、已邀请"+(mPersonNum-2)+"为好友");
        mImageView8.setImageResource(R.mipmap.isb);/*成功*/
        mTextView8.setText((mPersonNum)+"、已邀请"+(mPersonNum)+"为好友");
//        mImageView9.setImageResource(R.mipmap.isb);
        mTextView9.setText((mPersonNum+2)+"、需要邀请第"+(mPersonNum+2)+"位好友");
//        mImageView10.setImageResource(R.mipmap.isb);
        mTextView10.setText((mPersonNum+4)+"、需要邀请第"+(mPersonNum+4)+"位好友");
    }
    private void getInvite7(){
        mImageView1.setImageResource(R.mipmap.isb);/*成功*/
        mTextView1.setText((mPersonNum-6)+"、已邀请"+(mPersonNum-6)+"为好友");
        mImageView2.setImageResource(R.mipmap.isb);/*成功*/
        mTextView2.setText((mPersonNum-4)+"、已邀请"+(mPersonNum-4)+"为好友");
        mImageView3.setImageResource(R.mipmap.isb);/*成功*/
        mTextView3.setText((mPersonNum-2)+"、已邀请"+(mPersonNum-2)+"为好友");
        mImageView4.setImageResource(R.mipmap.isb);/*成功*/
        mTextView4.setText((mPersonNum)+"、已邀请"+(mPersonNum)+"为好友");
//        mImageView5.setImageResource(R.mipmap.isb);
        mTextView5.setText((mPersonNum+2)+"、需要邀请第"+(mPersonNum+2)+"位好友");
        mImageView6.setImageResource(R.mipmap.isb);/*成功*/
        mTextView6.setText((mPersonNum-5)+"、已邀请"+(mPersonNum-5)+"为好友");
        mImageView7.setImageResource(R.mipmap.isb);/*成功*/
        mTextView7.setText((mPersonNum-3)+"、已邀请"+(mPersonNum-3)+"为好友");
        mImageView8.setImageResource(R.mipmap.isb);/*成功*/
        mTextView8.setText((mPersonNum-1)+"、已邀请"+(mPersonNum-1)+"为好友");
//        mImageView9.setImageResource(R.mipmap.isb);
        mTextView9.setText((mPersonNum+1)+"、需要邀请第"+(mPersonNum+1)+"位好友");
//        mImageView10.setImageResource(R.mipmap.isb);
        mTextView10.setText((mPersonNum+3)+"、需要邀请第"+(mPersonNum+3)+"位好友");
    }
    private void getInvite8(){
        mImageView1.setImageResource(R.mipmap.isb);/*成功*/
        mTextView1.setText((mPersonNum-7)+"、已邀请"+(mPersonNum-7)+"为好友");
        mImageView2.setImageResource(R.mipmap.isb);/*成功*/
        mTextView2.setText((mPersonNum-5)+"、已邀请"+(mPersonNum-5)+"为好友");
        mImageView3.setImageResource(R.mipmap.isb);/*成功*/
        mTextView3.setText((mPersonNum-3)+"、已邀请"+(mPersonNum-3)+"为好友");
        mImageView4.setImageResource(R.mipmap.isb);/*成功*/
        mTextView4.setText((mPersonNum-1)+"、已邀请"+(mPersonNum-1)+"为好友");
//        mImageView5.setImageResource(R.mipmap.isb);
        mTextView5.setText((mPersonNum+1)+"、需要邀请第"+(mPersonNum+1)+"位好友");
        mImageView6.setImageResource(R.mipmap.isb);/*成功*/
        mTextView6.setText((mPersonNum-6)+"、已邀请"+(mPersonNum-6)+"为好友");
        mImageView7.setImageResource(R.mipmap.isb);/*成功*/
        mTextView7.setText((mPersonNum-4)+"、已邀请"+(mPersonNum-4)+"为好友");
        mImageView8.setImageResource(R.mipmap.isb);/*成功*/
        mTextView8.setText((mPersonNum-2)+"、已邀请"+(mPersonNum-2)+"为好友");
        mImageView9.setImageResource(R.mipmap.isb);/*成功*/
        mTextView9.setText((mPersonNum)+"、已邀请"+(mPersonNum)+"为好友");
//        mImageView10.setImageResource(R.mipmap.isb);
        mTextView10.setText((mPersonNum+2)+"、需要邀请第"+(mPersonNum+2)+"位好友");
    }
    private void getInvite9(){
        mImageView1.setImageResource(R.mipmap.isb);/*成功*/
        mTextView1.setText((mPersonNum-8)+"、已邀请"+(mPersonNum-8)+"为好友");
        mImageView2.setImageResource(R.mipmap.isb);/*成功*/
        mTextView2.setText((mPersonNum-6)+"、已邀请"+(mPersonNum-6)+"为好友");
        mImageView3.setImageResource(R.mipmap.isb);/*成功*/
        mTextView3.setText((mPersonNum-4)+"、已邀请"+(mPersonNum-4)+"为好友");
        mImageView4.setImageResource(R.mipmap.isb);/*成功*/
        mTextView4.setText((mPersonNum-2)+"、已邀请"+(mPersonNum-2)+"为好友");
        mImageView5.setImageResource(R.mipmap.isb);/*成功*/
        mTextView5.setText((mPersonNum)+"、已邀请"+(mPersonNum)+"为好友");
        mImageView6.setImageResource(R.mipmap.isb);/*成功*/
        mTextView6.setText((mPersonNum-7)+"、已邀请"+(mPersonNum-7)+"为好友");
        mImageView7.setImageResource(R.mipmap.isb);/*成功*/
        mTextView7.setText((mPersonNum-5)+"、已邀请"+(mPersonNum-5)+"为好友");
        mImageView8.setImageResource(R.mipmap.isb);/*成功*/
        mTextView8.setText((mPersonNum-3)+"、已邀请"+(mPersonNum-3)+"为好友");
        mImageView9.setImageResource(R.mipmap.isb);/*成功*/
        mTextView9.setText((mPersonNum-1)+"、已邀请"+(mPersonNum-1)+"为好友");
//        mImageView10.setImageResource(R.mipmap.isb);
        mTextView10.setText((mPersonNum+1)+"、需要邀请第"+(mPersonNum+1)+"位好友");
    }
    /**
     * 中奖消息名单
     */
    private void getDaNews(){
        HashMap<String, String> hashMap = new HashMap<>();
        HttpHelper.getInstance().post(Url.rollMessage2, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                RollMessage rollMessage = JsonUtils.parse(response, RollMessage.class);
                mInform1.setText(rollMessage.getData().get(0).getPhone()+rollMessage.getData().get(0).getTime()+rollMessage.getData().get(0).getMessage()+rollMessage.getData().get(0).getJiangPinLevel());
                mInform2.setText(rollMessage.getData().get(1).getPhone()+rollMessage.getData().get(1).getTime()+rollMessage.getData().get(1).getMessage()+rollMessage.getData().get(1).getJiangPinLevel());
                mInform3.setText(rollMessage.getData().get(2).getPhone()+rollMessage.getData().get(2).getTime()+rollMessage.getData().get(2).getMessage()+rollMessage.getData().get(2).getJiangPinLevel());
                mInform4.setText(rollMessage.getData().get(3).getPhone()+rollMessage.getData().get(3).getTime()+rollMessage.getData().get(3).getMessage()+rollMessage.getData().get(3).getJiangPinLevel());
                mInform5.setText(rollMessage.getData().get(4).getPhone()+rollMessage.getData().get(4).getTime()+rollMessage.getData().get(4).getMessage()+rollMessage.getData().get(4).getJiangPinLevel());
                mInform6.setText(rollMessage.getData().get(5).getPhone()+rollMessage.getData().get(5).getTime()+rollMessage.getData().get(5).getMessage()+rollMessage.getData().get(5).getJiangPinLevel());
                mInform7.setText(rollMessage.getData().get(6).getPhone()+rollMessage.getData().get(6).getTime()+rollMessage.getData().get(6).getMessage()+rollMessage.getData().get(6).getJiangPinLevel());
                mInform8.setText(rollMessage.getData().get(7).getPhone()+rollMessage.getData().get(7).getTime()+rollMessage.getData().get(7).getMessage()+rollMessage.getData().get(7).getJiangPinLevel());
                mInform9.setText(rollMessage.getData().get(8).getPhone()+rollMessage.getData().get(8).getTime()+rollMessage.getData().get(8).getMessage()+rollMessage.getData().get(8).getJiangPinLevel());
                mInform11.setText(rollMessage.getData().get(9).getPhone()+rollMessage.getData().get(9).getTime()+rollMessage.getData().get(9).getMessage()+rollMessage.getData().get(9).getJiangPinLevel());
                mInform12.setText(rollMessage.getData().get(10).getPhone()+rollMessage.getData().get(10).getTime()+rollMessage.getData().get(10).getMessage()+rollMessage.getData().get(10).getJiangPinLevel());
                mInform13.setText(rollMessage.getData().get(11).getPhone()+rollMessage.getData().get(11).getTime()+rollMessage.getData().get(11).getMessage()+rollMessage.getData().get(11).getJiangPinLevel());
                mInform14.setText(rollMessage.getData().get(12).getPhone()+rollMessage.getData().get(12).getTime()+rollMessage.getData().get(12).getMessage()+rollMessage.getData().get(12).getJiangPinLevel());
                mInform15.setText(rollMessage.getData().get(13).getPhone()+rollMessage.getData().get(13).getTime()+rollMessage.getData().get(13).getMessage()+rollMessage.getData().get(13).getJiangPinLevel());
                mInform16.setText(rollMessage.getData().get(14).getPhone()+rollMessage.getData().get(14).getTime()+rollMessage.getData().get(14).getMessage()+rollMessage.getData().get(14).getJiangPinLevel());
                mInform17.setText(rollMessage.getData().get(15).getPhone()+rollMessage.getData().get(15).getTime()+rollMessage.getData().get(15).getMessage()+rollMessage.getData().get(15).getJiangPinLevel());
                mInform18.setText(rollMessage.getData().get(16).getPhone()+rollMessage.getData().get(16).getTime()+rollMessage.getData().get(16).getMessage()+rollMessage.getData().get(16).getJiangPinLevel());
                mInform19.setText(rollMessage.getData().get(17).getPhone()+rollMessage.getData().get(17).getTime()+rollMessage.getData().get(17).getMessage()+rollMessage.getData().get(17).getJiangPinLevel());
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {

            }
        });
    }
    /**
     * 抽奖
     */
    private void  Lotto(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        HttpHelper.getInstance().post(Url.slyderAdventures, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final SlyderAdventures slyderAdventures = JsonUtils.parse(response, SlyderAdventures.class);
                if (slyderAdventures.getCode()==1) {
                    slyderAdventures.getJiangPinLevelValue();
                    float i = (slyderAdventures.getJiangPinLevelValue()-1)*45+360*5;
                    ObjectAnimator animator = ObjectAnimator.ofFloat(mImageView, "rotation", 0f, i);
                    animator.setDuration(2000);
                    animator.start();
                    mImageView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (slyderAdventures.getJiangPinLevelValue() == 8) {

                            } else {
//                                hongPopWindow();
                            }
//                            StringUtils.showToast(AdventuresActivity.this,slyderAdventures.getJiangPinLevel());
                        }
                    },2500);

                } else if (slyderAdventures.getCode() == 1) {
                    StringUtils.showToast(AdventuresActivity.this,slyderAdventures.getMsg());

                } else if (slyderAdventures.getCode()==-1) {
                    StringUtils.showToast(AdventuresActivity.this,slyderAdventures.getMsg());
                    PreferenceManager.instance().removeToken();
                    showActivity(LoginActivity.class,null);
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {

            }
        });
    }
    private static class CustomShareListener implements UMShareListener {
        private WeakReference<AdventuresActivity> mActivity;
        private CustomShareListener(AdventuresActivity activity) {
            mActivity = new WeakReference(activity);
        }
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }
        @Override
        public void onResult(SHARE_MEDIA platform) {
            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(mActivity.get().getApplication(), " 收藏成功啦", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(mActivity.get().getApplication(), " 分享成功啦", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(mActivity.get().getApplication(), " 分享失败啦", Toast.LENGTH_SHORT).show();
                if (t != null) {
                    com.umeng.socialize.utils.Log.d("throw", "throw:" + t.getMessage());
                }
            }
        }
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(mActivity.get().getApplication(), " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mShareAction.close();

    }

    /**
     * 用户的id
     */
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
                    StringUtils.showToast(AdventuresActivity.this,bean.getMsg());
                }

            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                hiddenLoading();
                StringUtils.showToast(AdventuresActivity.this,error_msg);
            }
        });
    }

}
