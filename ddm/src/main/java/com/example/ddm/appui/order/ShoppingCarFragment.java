package com.example.ddm.appui.order;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ddm.LoginActivity;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.ShoppingCarActivity;
import com.example.ddm.appui.adapter.MyExpandableListAdapter;
import com.example.ddm.appui.adapter.clickback.OnShoppingCartChangeListener;
import com.example.ddm.appui.adapter.clickback.ShoppingCartBiz;
import com.example.ddm.appui.bean.ShoppingCar;
import com.example.ddm.appui.bean.eventbus.LoginSuccess;
import com.example.ddm.appui.bean.eventbus.UpdateCardList;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingCarFragment extends BaseFragment {
    private ExpandableListView expandableListView;
    private ImageView ivSelectAll;
    private TextView btnSettle;
    private TextView tvCountMoney;
    private TextView tvTitle;
    private RelativeLayout rlShoppingCartEmpty;
    private RelativeLayout rlBottomBar;
    private MyExpandableListAdapter adapter;
    private List<ShoppingCar.DatasBean> mDatasBeen = new ArrayList<>();
    public ShoppingCarFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopping_car, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        expandableListView= mFindViewUtils.findViewById(R.id.expandableListView);
        ivSelectAll= mFindViewUtils.findViewById(R.id.ivSelectAll);
        btnSettle= mFindViewUtils.findViewById(R.id.btnSettle);
        tvCountMoney= mFindViewUtils.findViewById(R.id.tvCountMoney);
        tvTitle= mFindViewUtils.findViewById(R.id.tvTitle);
        rlShoppingCartEmpty= mFindViewUtils.findViewById(R.id.rlShoppingCartEmpty);
        rlBottomBar= mFindViewUtils.findViewById(R.id.rlBottomBar);
    }

    @Subscribe
    public void Car(UpdateCardList loginSuccess){
        /*刷新数据和ui*/
        if (loginSuccess.getMsg().equals("购物车")) {
            getCarList();
        }
    }
    @Override
    protected void setListener() {
        super.setListener();

        setAdapter();
    }
    @Override
    protected void setData() {
        super.setData();
        getCarList();
    }
    private void setAdapter() {
        adapter = new MyExpandableListAdapter(getActivity());
        expandableListView.setAdapter(adapter);
        adapter.setOnShoppingCartChangeListener(new OnShoppingCartChangeListener() {
            public void onDataChange(String selectCount, String selectMoney) {
//
                String countMoney = String.format("合计：￥%s", selectMoney);
                String countGoods = String.format("结算(%s)", selectCount);
                String title = String.format("购物车","");
                tvCountMoney.setText(countMoney);
                btnSettle.setText(countGoods);
                tvTitle.setText(title);
            }
            public void onSelectItem(boolean isSelectedAll) {
                ShoppingCartBiz.checkItem(isSelectedAll, ivSelectAll);
            }
        });
        //通过监听器关联Activity和Adapter的关系，解耦；
        View.OnClickListener listener = adapter.getAdapterListener();
        if (listener != null) {
            //即使换了一个新的Adapter，也要将“全选事件”传递给adapter处理；
            ivSelectAll.setOnClickListener(adapter.getAdapterListener());
            //结算时，一般是需要将数据传给订单界面的
            btnSettle.setOnClickListener(adapter.getAdapterListener());
        }
        expandableListView.setGroupIndicator(null);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return true;
            }
        });
    }
    public void showEmpty(boolean isEmpty) {
        if (isEmpty) {
            expandableListView.setVisibility(View.GONE);
            rlShoppingCartEmpty.setVisibility(View.VISIBLE);
            rlBottomBar.setVisibility(View.GONE);
        } else {
            expandableListView.setVisibility(View.VISIBLE);
            rlShoppingCartEmpty.setVisibility(View.GONE);
            rlBottomBar.setVisibility(View.VISIBLE);
        }
    }
    private void updateListView() {
        adapter.setList(mDatasBeen);
        adapter.notifyDataSetChanged();
        expandAllGroup();
    }
    private void expandAllGroup() {
        for (int i = 0; i < mDatasBeen.size(); i++) {
            expandableListView.expandGroup(i);
        }
    }

    /**
     * 购物车集合
     */
    private void getCarList(){
        showLoading();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        HttpHelper.getInstance().post(Url.SHOPPINGCARTC, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                hiddenLoading();
                ShoppingCar shoppingCar = JsonUtils.parse(response, ShoppingCar.class);
                if (shoppingCar.getCode()==1) {
                    if (shoppingCar.getDatas().size() > 0) {
                        rlShoppingCartEmpty.setVisibility(View.GONE);
                    } else {
                        rlShoppingCartEmpty.setVisibility(View.VISIBLE);
                    }
                    if (mDatasBeen!=null) {
                        mDatasBeen.clear();
                        mDatasBeen.addAll(shoppingCar.getDatas());
                        ShoppingCartBiz.updateShopList(mDatasBeen);
                        updateListView();
                    }
                } else if (shoppingCar.getCode()==0) {
                    StringUtils.showToast(getActivity(),shoppingCar.getMsg());
                } else if (shoppingCar.getCode()==-1) {
                    StringUtils.showToast(getActivity(),shoppingCar.getMsg());
                    PreferenceManager.instance().removeToken();
                    mBaseActivity.showActivity(LoginActivity.class,null);
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
