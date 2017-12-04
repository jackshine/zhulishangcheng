package com.example.ddm.appui.home;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.adapter.ComListviewAdapter;
import com.example.ddm.appui.adapter.CommentAdapter;
import com.example.ddm.appui.adapter.EvaluateAdapter;
import com.example.ddm.appui.bean.CommentBean;
import com.example.ddm.appui.bean.EvaluateBean;
import com.example.ddm.appui.bean.ShopDetail;
import com.example.ddm.appui.constant.FragmentTag;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.ShowFragmentUtils;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.appui.view.ZFlowLayout;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * 用户评价界面
 */
public class UserEvaluationFragment extends BaseFragment{
    private ZFlowLayout mFlowLayout;
    private List<CommentBean.CommentsSumdatasBean> mList = new ArrayList<>();
    private ListView mListView;
    private int mTypeValue;
    private List<EvaluateBean.DatasBean> mCustomersdatasBeen = new ArrayList<>();
    private EvaluateAdapter mCommentAdapter;
    public UserEvaluationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_evaluation, container, false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mListView = mFindViewUtils.findViewById(R.id.list_view);
        mFlowLayout = mFindViewUtils.findViewById(R.id.flowLayout);
    }
    @Override
    protected void setListener() {
        super.setListener();
        mCommentAdapter = new EvaluateAdapter(getContext(), null);
        mListView.setAdapter(mCommentAdapter);
    }
    @Override
    protected void setData() {
        super.setData();
        getComment("");
        getList("1");
    }
    // 初始化标签
    private void initLabel() {
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(30, 30, 10, 10);// 设置边距
        TextView[]  textViews = new TextView[mList.size()];

        for ( int i = 0; i < mList.size(); i++) {
            final TextView textView = new TextView(getContext());
            textViews[i] = textView;
//            textViews[i].setBackgroundResource(R.drawable.def_white);
//            textViews[i].setTextColor(Color.parseColor("#535353"));
//            textViews[i].setText(lables.get(i).getContent());
            textViews[i].setTag(i);
//            textView.setTag(i);
//            textView.setTextSize(15);
            mTypeValue = mList.get(i).getCommentsTypeValue();
            textViews[i].setText(mList.get(i).getCommentsType()+"("+mList.get(i).getCommentsSum()+")");
            textViews[i].setPadding(24, 11, 24, 11);
            textViews[i].setTextColor(0xFFFF8D5F);
            textViews[i].setBackgroundResource(R.drawable.lable_item_normal);
            mFlowLayout.addView(textView, layoutParams);
            // 标签点击事件
//            textView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    StringUtils.showToast(getActivity(),""+mTypeValue);
//                    getList(String.valueOf(mTypeValue));
//                }
//            });
        }
        for(int j = 0 ; j < textViews.length;j++){
            textViews[j].setTag(textViews);

//            mOne = String.valueOf(lables.get(j).getId());
            textViews[j].setOnClickListener(new LableClickListener(String.valueOf(mList.get(j).getCommentsTypeValue())));
        }

    }
    private void getComment(String goodsId){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.SHOPID, PreferenceManager.instance().getShopId());
        hashMap.put("goodsId", goodsId);
        HttpHelper.getInstance().post(Url.COMMENTSSUMDATAS, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final CommentBean commentBean = JsonUtils.parse(response, CommentBean.class);
                if (commentBean.getCode()==1) {
                    if (mList!=null) {
                        mList.clear();
                        mList.addAll(commentBean.getCommentsSumdatas());
                    }
                    initLabel();
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {

            }
        });
    }

    private void getList(String commentsTypeValue){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.SHOPID, PreferenceManager.instance().getShopId());
        hashMap.put("goodsId", commentsTypeValue);
        HttpHelper.getInstance().post(Url.COMMENTSC, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                final EvaluateBean evaluateBean = JsonUtils.parse(response, EvaluateBean.class);
                if (evaluateBean.getCode()==1) {
                    if (mCustomersdatasBeen!=null) {
                        mCustomersdatasBeen.clear();
                        mCustomersdatasBeen.addAll(evaluateBean.getDatas());
                    }
                    mCommentAdapter.update(mCustomersdatasBeen);
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                StringUtils.showToast(getActivity(), error_msg);
            }
        });
    }
    class LableClickListener implements View.OnClickListener {
        private String mNum;
        public LableClickListener( String num){
            this.mNum = num;
        }
        @Override
        public void onClick(View v) {
            TextView[] textViews = (TextView[]) v.getTag();
            TextView tv = (TextView) v;
            for (int i = 0; i < textViews.length; i++) {
                //让点击的标签背景变成橙色，字体颜色变为白色
                if (tv.equals(textViews[i])) {
                    textViews[i].setBackgroundResource(R.drawable.def_yellow_bom);
                    textViews[i].setTextColor(Color.parseColor("#FFFFFF"));
                    //传递属性选择后的商品数据
                    getList(mNum);
                } else {
                    //其他标签背景变成白色，字体颜色为黑色
                    textViews[i].setBackgroundResource(R.drawable.lable_item_normal);
                    textViews[i].setTextColor(Color.parseColor("#FFFF8D5F"));
                }
            }
        }
    }
}
