package com.example.ddm.appui.order;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.bean.OrderBean;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;
import java.util.HashMap;
/**
 * A simple {@link Fragment} subclass.
 * 订单详情界面
 */
public class OrderDeailFragment extends BaseFragment {
    private TextView mName,mOrder,mShopping,mNum,mPrice,mAll, mIntegral,mBack;
    public OrderDeailFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_deail, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mName = mFindViewUtils.findViewById(R.id.name);
        mOrder = mFindViewUtils.findViewById(R.id.order);
        mShopping = mFindViewUtils.findViewById(R.id.shop_name);
        mNum = mFindViewUtils.findViewById(R.id.num);
        mPrice = mFindViewUtils.findViewById(R.id.money);
        mAll = mFindViewUtils.findViewById(R.id.all);
        mIntegral = mFindViewUtils.findViewById(R.id.integral);
        mBack = mFindViewUtils.findViewById(R.id.app_title_back);
        post();
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
    protected void setData() {
        super.setData();
    }
    private void post(){
        showLoading();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
//        hashMap.put(IAppKey.PAGE, "1");
        HttpHelper.getInstance().post(Url.My_Order, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                hiddenLoading();
                final OrderBean bean = JsonUtils.parse(response, OrderBean.class);
                if (bean.getCode()==1) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            int i = Integer.parseInt(PreferenceManager.instance().getOrderId());
                            mName.setText(bean.getDatas().get(i).getShopName());
                            mOrder.setText(bean.getDatas().get(i).getCode());
                            mShopping.setText(bean.getDatas().get(i).getGoodsName());
                            mNum.setText(""+bean.getDatas().get(i).getGoodsNum());
                            mPrice.setText(bean.getDatas().get(i).getGoodOnePrice());
                            mAll.setText(bean.getDatas().get(i).getShopYingYeMoney());
                            mIntegral.setText(""+bean.getDatas().get(i).getJifen());
                        }
                    });
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hiddenLoading();
            }
        });
    }
}
