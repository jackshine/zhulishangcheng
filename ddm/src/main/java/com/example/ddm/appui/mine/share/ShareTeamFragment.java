package com.example.ddm.appui.mine.share;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.bean.ShareTeam;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;
import com.idtk.smallchart.chart.PieChart;
import com.idtk.smallchart.data.PieData;
import com.idtk.smallchart.interfaces.iData.IPieData;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * 分享团队
 */
public class ShareTeamFragment extends BaseFragment {
    private PieChart mPieChart;
    private int mOne,mTwo, mThree;
    private ArrayList<IPieData> mPieDataList = new ArrayList<>();
    private PieData mPieData,mPieData1,mPieData2;
    public ShareTeamFragment() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_share_team, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mPieData = new PieData();
        mPieData1 = new PieData();
        mPieData2 = new PieData();
        mPieChart = mFindViewUtils.findViewById(R.id.pieChart);
        mPieChart.setAxisColor(Color.WHITE);
        mPieChart.setAxisTextSize(pxTodp(20));
        initData();
    }
    @Override
    protected void setListener() {
        super.setListener();
    }
    @Override
    protected void setData() {
        super.setData();
    }
    private void initData(){
        mPieData.setName("区域");
        mPieData.setColor(mColors[1]);
        mPieData.setValue(Float.parseFloat(PreferenceManager.instance().getOnePerson()));
        mPieData1.setValue(Float.parseFloat(PreferenceManager.instance().getTwoPerson()));
        mPieData2.setValue(Float.parseFloat(PreferenceManager.instance().getThressPerson()));
        mPieDataList.add(mPieData);
        mPieDataList.add(mPieData1);
        mPieDataList.add(mPieData2);
    }
}
