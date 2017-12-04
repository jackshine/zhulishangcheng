package com.example.ddm.appui.summary.detail;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.view.DragLayout;

/**
 * A simple {@link Fragment} subclass.
 * 商品详情
 */
public class GoodsFragment extends BaseFragment {
//    private GoodsDetailFragment mGoodsDetailFragment;
//    private GoodsPictureFragment mGoodsPictureFragment;
//
//    private DragLayout mDragLayout;
    public GoodsFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_goods, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
//        mGoodsDetailFragment = new GoodsDetailFragment();
//        mGoodsPictureFragment = new GoodsPictureFragment();
//        mDragLayout = mFindViewUtils.findViewById(R.id.draglayout);
//        try {
//            getActivity().getSupportFragmentManager().beginTransaction()
//                    .add(R.id.activity_product_details_first, mGoodsDetailFragment).add(R.id.activity_product_details_second, mGoodsPictureFragment)
//                    .commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        DragLayout.ShowNextPageNotifier nextIntf = new DragLayout.ShowNextPageNotifier() {
//            @Override
//            public void onDragNext() {
//                //    mFragmentBottom.initView();
//            }
//        };
//        mDragLayout.setNextPageListener(nextIntf);
    }
}
