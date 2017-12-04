package com.example.ddm.appui.order;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.adapter.ShouhuoAddressBaseAdapter;
import com.example.ddm.appui.adapter.clickback.Callback;
import com.example.ddm.appui.adapter.recycle.CommenRecycleAdapter;
import com.example.ddm.appui.adapter.recycle.SuperViewHolder;
import com.example.ddm.appui.bean.GetUserAddress;
import com.example.ddm.appui.bean.SearchUserCoupon;
import com.example.ddm.appui.bean.SendCodeBean;
import com.example.ddm.appui.bean.eventbus.LoginSuccess;
import com.example.ddm.appui.constant.FragmentTag;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.ShowFragmentUtils;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;
import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * 管理收货地址
 */
public class ManageAddressFragment extends BaseFragment {
    private LRecyclerView mLRecyclerView;
//    private ShouhuoAddressBaseAdapter mAdapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private Button mButton;
    private TextView mBack;
    private CommenRecycleAdapter<GetUserAddress.DatasBean.AddressLestBean> mAdapter;
    private List<GetUserAddress.DatasBean.AddressLestBean> mList = new ArrayList<>();
    public ManageAddressFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manage_address, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mBack = mFindViewUtils.findViewById(R.id.app_title_back);
        mButton = mFindViewUtils.findViewById(R.id.btn_add_address);
        mLRecyclerView = mFindViewUtils.findViewById(R.id.list_view);
        mAdapter = new CommenRecycleAdapter<GetUserAddress.DatasBean.AddressLestBean>(getContext(), R.layout.item_listview_shouhuo_address) {
            @Override
            public void setViewData(SuperViewHolder holder, GetUserAddress.DatasBean.AddressLestBean item, int position) {
                if (item.isIfDefault()) {
                    holder.getView(R.id.iv_item_select_default_address).setBackgroundResource(R.mipmap.dh);
                    holder.setText("默认地址", R.id.tv_nomal_address);
                } else {
                    holder.getView(R.id.iv_item_select_default_address).setBackgroundResource(R.mipmap.bty);
                    holder.setText("设为默认", R.id.tv_nomal_address);
                }
                holder.setText(item.getName(), R.id.tv_shouhuo_address_name).
                        setText(item.getPhone(),R.id.tv_shouhuo_address_phone).setText(item.getDetail(),R.id.tv_detail_address);
            }
            @Override
            public void setListener(SuperViewHolder holder, View view) {
                holder.setOnClickListener(R.id.ll_select_nomal_address);
                holder.setOnClickListener(R.id.ll_delete_address);/*删除*/
                holder.setOnClickListener(R.id.ll_editor_address);/*编辑*/

            }
            @Override
            public void onClickBack(SuperViewHolder holder, final int position, View view) {
                switch (view.getId()) {
                    case R.id.ll_select_nomal_address:
                        setDefault(mList.get(position).getId());
                        break;
                    case R.id.ll_delete_address:
                        new AlertDialog.Builder(getContext()).setTitle("确认").setMessage("确定删除吗？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        HashMap<String, String> hashMap = new HashMap<>();
                                        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
                                        hashMap.put("addressId", String.valueOf(mList.get(position).getId()));
                                        HttpHelper.getInstance().post(Url.deleteUserAddress, hashMap, new RawResponseHandler() {
                                            @Override
                                            public void onSuccess(int statusCode, String response) {
                                                final SendCodeBean userAddress = JsonUtils.parse(response, SendCodeBean.class);
                                                if (userAddress.getCode()==1) {
                                                    remove(position);
                                                    EventBus.getDefault().post(new LoginSuccess());
                                                    Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                                                } else if (userAddress.getCode()==0) {
                                                    Toast.makeText(getContext(), userAddress.getMsg(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                            @Override
                                            public void onFailure(int statusCode, String error_msg) {

                                            }
                                        });
                                    }
                                })
                                .setNegativeButton("取消", null).show();
                        break;
                    case R.id.ll_editor_address:
                        mArguments.putString("addressId", String.valueOf(mList.get(position).getId()));
                        mArguments.putString("name",mList.get(position).getName());
                        mArguments.putString("phone",mList.get(position).getPhone());
                        mArguments.putString("detail",mList.get(position).getDetail());
                        mArguments.putString("provinceId", String.valueOf(mList.get(position).getProvinceId()));
                        mArguments.putString("cityId", String.valueOf(mList.get(position).getCityId()));
                        mArguments.putString("countyId", String.valueOf(mList.get(position).getCountyId()));
                        mArguments.putString("address",mList.get(position).getProvince()+mList.get(position).getCity()+mList.get(position).getCounty());
                        ShowFragmentUtils.showFragment(getActivity(), AddressUpdateFragment.class, FragmentTag.ADDRESSUPDATEFRAGMENT,mArguments,true);
                        break;
                }
            }
        };
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
        mLRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerDecoration divider = new DividerDecoration.Builder(getContext())
                .setHeight(R.dimen.a_5dp)
                .setColorResource(R.color.gray_background)
                .build();
        mLRecyclerView.addItemDecoration(divider);
        mLRecyclerView.setAdapter(mLRecyclerViewAdapter);
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
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentUtils.showFragment(getActivity(), EditorAddressFragment.class, FragmentTag.EDITORADDRESSFRAGMENT,null,true);
            }
        });
        mLRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mList.clear();
                mLRecyclerViewAdapter.notifyDataSetChanged();
                getUserAddress();
            }
        });
    }
    @Override
    protected void setData() {
        super.setData();
        getUserAddress();
    }
    private void setDefault(int id){
        HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
                hashMap.put("addressId", String.valueOf(id));
                HttpHelper.getInstance().post(Url.userAddressToBeDefault, hashMap, new RawResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response) {
                        final SendCodeBean userAddress = JsonUtils.parse(response, SendCodeBean.class);
                        if (userAddress.getCode()==1) {
                            EventBus.getDefault().post(new LoginSuccess());
                            popSelf();
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                    }
                });

        }

    /**
     * 收货地址列表
     */
        private void getUserAddress(){
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
            HttpHelper.getInstance().post(Url.getUserAddress, hashMap, new RawResponseHandler() {
        @Override
         public void onSuccess(int statusCode, String response) {
            final GetUserAddress userAddress = JsonUtils.parse(response, GetUserAddress.class);
            if (userAddress.getCode()==1) {
                if (mList!=null) {
                    mList.clear();
                    mList.addAll(userAddress.getDatas().getAddressLest());
                }
                mAdapter.setDataList(mList);
                mLRecyclerView.refreshComplete(1000);
            }
        }
        @Override
        public void onFailure(int statusCode, String error_msg) {
            StringUtils.showToast(getActivity(),error_msg);
            mLRecyclerView.refreshComplete(1000);
        }
    });
}

}
