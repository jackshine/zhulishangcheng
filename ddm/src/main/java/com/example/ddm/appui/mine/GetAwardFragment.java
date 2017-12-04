package com.example.ddm.appui.mine;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.example.ddm.LoginActivity;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.adapter.recycle.CommenRecycleAdapter;
import com.example.ddm.appui.adapter.recycle.SuperViewHolder;
import com.example.ddm.appui.bean.GetZongJiangRecord;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;
import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * A simple {@link Fragment} subclass.
 * 领奖
 */
public class GetAwardFragment extends BaseFragment {
    private LRecyclerView mLRecyclerView;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private int mPage=1;
    private CommenRecycleAdapter<GetZongJiangRecord.DatasBean> mAdapter;
    private List<GetZongJiangRecord.DatasBean> mList = new ArrayList<>();
    public GetAwardFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_get_award, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mLRecyclerView = mFindViewUtils.findViewById(R.id.lrv_public_recyclerview);
        mAdapter = new CommenRecycleAdapter<GetZongJiangRecord.DatasBean>(getContext(), R.layout.award_get_item) {
            @Override
            public void setViewData(SuperViewHolder holder, GetZongJiangRecord.DatasBean item, int position) {
                ImageView view = holder.getView(R.id.glide_icon);
                Glide.with(getContext()).load(item.getImg()).placeholder(R.drawable.ic_launcher).crossFade().into(view);
                if (item.getJiangPinLevelValue() == 8) {
                    holder.setText(item.getCode(), R.id.get_award).setText("奖品编码", R.id.award_num).setText(item.getJiangPinName(),R.id.goods_name).setText("中奖编码："+item.getCode(),R.id.award_code)
                            .setText("中奖时间："+item.getCreateTime(),R.id.award_time).
                            setText(item.getJiangPinLevel(),R.id.award_grade);
                } else {
                    holder.setText(item.getJiangPinName(),R.id.goods_name).setText("中奖编码："+item.getCode(),R.id.award_code)
                            .setText("中奖时间："+item.getCreateTime(),R.id.award_time).
                            setText(item.getJiangPinLevel(),R.id.award_grade).setText(item.getLogisticsCode(),R.id.get_award);
                }
            }
            @Override
            public void setListener(SuperViewHolder holder, View view) {
                holder.setOnClickListener(R.id.copy);
            }
            @Override
            public void onClickBack(SuperViewHolder holder, int position, View view) {
                copyToClipboard(getContext(),holder.getText(R.id.get_award));
                StringUtils.showToast(getActivity(),"复制成功");
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
        mLRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAward(mPage);
            }
        });
        mLRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
    @Override
         public void onLoadMore() {
        mPage++;
        getAward(mPage);
    }
});
    }
    public static void copyToClipboard(Context context, String text) {
        ClipboardManager systemService = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        systemService.setPrimaryClip(ClipData.newPlainText("text", text));
    }
    @Override
    protected void setData() {
        super.setData();
        getAward(mPage);
    }

    /**
     * @param page
     * 已领奖
     */
    private void getAward(int page){
        showLoading();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        hashMap.put("page", String.valueOf(page));
        hashMap.put("isobtain", "true");
        HttpHelper.getInstance().post(Url.getZongJiangRecord, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                hiddenLoading();
                GetZongJiangRecord getZongJiangRecord = JsonUtils.parse(response, GetZongJiangRecord.class);
                if (getZongJiangRecord.getCode()==1) {
                    mList.addAll(getZongJiangRecord.getDatas());
                    mAdapter.setDataList(mList);
                    mLRecyclerView.refreshComplete(1000);
                } else if (getZongJiangRecord.getCode()==0) {
                    StringUtils.showToast(getActivity(),getZongJiangRecord.getMsg());
                } else if (getZongJiangRecord.getCode()==-1) {
                    StringUtils.showToast(getActivity(),getZongJiangRecord.getMsg());
                    PreferenceManager.instance().removeToken();
                    mBaseActivity.showActivity(LoginActivity.class, null);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hiddenLoading();
                mLRecyclerView.refreshComplete(1000);
                StringUtils.showToast(getActivity(),error_msg);

            }
        });
    }
}
