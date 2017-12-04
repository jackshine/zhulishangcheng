package com.example.ddm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ddm.appui.BaseActivity;
import com.example.ddm.appui.summary.detail.GoodsDetailFragment;
import com.example.ddm.appui.summary.detail.GoodsPictureFragment;
import com.example.ddm.appui.view.DragLayout;

public class ShopDetailActivity extends BaseActivity {
    private GoodsDetailFragment mGoodsDetailFragment;
    private GoodsPictureFragment mGoodsPictureFragment;
    private DragLayout mDragLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
    }

    @Override
    protected void initView() {
        super.initView();
        mGoodsDetailFragment = new GoodsDetailFragment();
        mGoodsPictureFragment = new GoodsPictureFragment();
        mDragLayout = mFindViewUtils.findViewById(R.id.draglayout);
        try {
          getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_product_details_first, mGoodsDetailFragment).add(R.id.activity_product_details_second, mGoodsPictureFragment)
                    .commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        DragLayout.ShowNextPageNotifier nextIntf = new DragLayout.ShowNextPageNotifier() {
            @Override
            public void onDragNext() {
                //    mFragmentBottom.initView();
            }
        };
        mDragLayout.setNextPageListener(nextIntf);
    }

    @Override
    protected void setListener() {
        super.setListener();
    }

    @Override
    protected void setData() {
        super.setData();
    }
}
