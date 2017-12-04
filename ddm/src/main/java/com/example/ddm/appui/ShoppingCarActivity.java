package com.example.ddm.appui;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.ddm.R;
import com.example.ddm.appui.adapter.MyExpandableListAdapter;
import com.example.ddm.appui.adapter.clickback.OnShoppingCartChangeListener;
import com.example.ddm.appui.adapter.clickback.ShoppingCartBiz;
import com.example.ddm.appui.bean.ShoppingCar;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class ShoppingCarActivity extends BaseActivity {
   private ExpandableListView expandableListView;
    private ImageView ivSelectAll;
    private TextView btnSettle;
    private TextView tvCountMoney;
    private TextView tvTitle;
    private RelativeLayout rlShoppingCartEmpty;
    private RelativeLayout rlBottomBar;
    private MyExpandableListAdapter adapter;
    private List<ShoppingCar.DatasBean> mDatasBeen = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_car);
    }
    @Override
    protected void initView() {
        super.initView();
        expandableListView= (ExpandableListView) findViewById(R.id.expandableListView);
        ivSelectAll= (ImageView) findViewById(R.id.ivSelectAll);
        btnSettle= (TextView) findViewById(R.id.btnSettle);
        tvCountMoney= (TextView) findViewById(R.id.tvCountMoney);
        tvTitle= (TextView) findViewById(R.id.tvTitle);
        rlShoppingCartEmpty= (RelativeLayout) findViewById(R.id.rlShoppingCartEmpty);
        rlBottomBar= (RelativeLayout) findViewById(R.id.rlBottomBar);
    }
    @Override
    protected void setListener() {
        super.setListener();
    }
    private void setAdapter() {
        adapter = new MyExpandableListAdapter(this);
        expandableListView.setAdapter(adapter);
        adapter.setOnShoppingCartChangeListener(new OnShoppingCartChangeListener() {
            public void onDataChange(String selectCount, String selectMoney) {
                    showEmpty(false);//其实不需要做这个判断，因为没有商品的时候，必须退出去添加商品；
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
    @Override
    protected void setData() {
        super.setData();
        getCarList();
        setAdapter();
    }
    private void getCarList(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        HttpHelper.getInstance().post(Url.SHOPPINGCARTC, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                ShoppingCar shoppingCar = JsonUtils.parse(response, ShoppingCar.class);
                if (shoppingCar.getCode()==1) {
                    if (mDatasBeen!=null) {
                        mDatasBeen.clear();
                        mDatasBeen.addAll(shoppingCar.getDatas());
                        ShoppingCartBiz.updateShopList(mDatasBeen);
                        updateListView();
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                StringUtils.showToast(ShoppingCarActivity.this,error_msg);
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
}
