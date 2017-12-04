package com.example.ddm;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.ddm.application.PokonyanApplication;
import com.example.ddm.appui.adapter.CommonAdapter;
import com.example.ddm.appui.adapter.HotInterface;
import com.example.ddm.appui.adapter.IAdapterClickBack;
import com.example.ddm.appui.adapter.ListViewAdapter;
import com.example.ddm.appui.adapter.SearchResultAdapter;
import com.example.ddm.appui.adapter.ViewHolder;
import com.example.ddm.appui.adapter.clickback.ICityClickBack;
import com.example.ddm.appui.bean.AllCity;
import com.example.ddm.appui.bean.City;
import com.example.ddm.appui.bean.CityDetail;
import com.example.ddm.appui.bean.HotCity;
import com.example.ddm.appui.bean.RecentCityO;
import com.example.ddm.appui.bean.ShopSuggest;
import com.example.ddm.appui.bean.dbbean.RecentCity;
import com.example.ddm.appui.bean.eventbus.Location;
import com.example.ddm.appui.bean.eventbus.Update;
import com.example.ddm.appui.bean.eventbus.UpdateCardList;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.db.AllCitySqliteOpenHelper;
import com.example.ddm.appui.db.CitySqliteOpenHelper;
import com.example.ddm.appui.greendao.daomanager.RecentCityOpe;
import com.example.ddm.appui.home.CommonMethod;
import com.example.ddm.appui.utils.DisplayUtils;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.PingYinUtil;
import com.example.ddm.appui.widget.MyLetterView;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class LocationActivity extends Activity {
    protected static final String TAG = "MainActivity";
    private MyLetterView myLetterView;//自定义的View
    private TextView tvDialog;//主界面显示字母的TextView
    private ListView lvCity;//进行城市列表展示
    private EditText etSearch;
    private ImageView mBack;
    private boolean isNeedRefresh;
    protected android.os.Handler mHandler = new android.os.Handler(Looper.getMainLooper());
    private String currentCity;
    private ListView lvResult;//搜索结果列表展示
    private TextView tvNoResult;//搜索无结果时文字展示
    private List<AllCity.DatasBean.DatasBeanTow> allCityList;//所有的城市
    private List<HotCity.DatasBean> mHotCities;
    private List<CityDetail.DatasBean> mDetailCities;
    private List<City> searchCityList;//搜索城市列表
    private List<String> recentCityList;//最近访问城市列表
    private List<RecentCityO.DatasBean> recentCitis = new ArrayList<>();//最近访问城市列表
    public CitySqliteOpenHelper cityOpenHelper;//对保存了最近访问城市的数据库操作的帮助类
    public SQLiteDatabase cityDb;//保存最近访问城市的数据库***
    //    public CityListAdapter cityListAdapter;
    private ListViewAdapter mListViewAdapter;
    public SearchResultAdapter searchResultAdapter;
    private boolean isScroll = false;
    private boolean mReady = false;
    private Handler handler;
    private OverlayThread overlayThread; //显示首字母对话框
    private String cityName;
    private String ClickName;
    private List<String> mShopSuggest = new ArrayList<>();//店铺类型的集合
    private CommonAdapter<String> mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //防止底部菜单栏被软键盘顶上去
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_location);
        PokonyanApplication.getInstance().addActivity(this);
        initView();
        initData();
        setListener();//点击字母设置监听到对应位置
        //初始化所有城市列表
        initAllCityData();
        if (TextUtils.isEmpty(PreferenceManager.instance().getbankid())) {

            getCityDetail("郑州市");
        } else {
            getCityDetail(PreferenceManager.instance().getbankid());
        }

//        initRecentVisitCityData();//初始化最近访问的城市数据
        getRecentCity();
        getHotCity();//初始化热门城市
        setAdapter();//设置适配器
        mReady = true;
//        popSuggest();
    }
    /**
     * 设置适配器
     */
    private void setAdapter() {
        mListViewAdapter = new ListViewAdapter(LocationActivity.this, mDetailCities,
                mHotCities, recentCitis, allCityList, new ICityClickBack() {
            @Override
            public void onCityDetailClickBack(String city) {
                //这里是各item点击事件的回调，需要参数可以自行添加
                //弹出切换城市的dialog，测试用

                PreferenceManager.instance().saveLocation(city);
                EventBus.getDefault().post(new Location(city));
                finish();
            }
            @Override
            public void onRecentCityClickBack(String recent) {
                //最近城市
                PreferenceManager.instance().saveLocation(recent);
                EventBus.getDefault().post(new Location(recent));
                finish();
            }
            @Override
            public void onHotCityClickBack(String hot) {
                //热门城市
                PreferenceManager.instance().saveLocation(hot);
                CommonMethod.addRecentCity(hot);
                EventBus.getDefault().post(new Location(hot));
                finish();
            }
            @Override
            public void onAllCityClickBack(String all) {
                //所有城市，
                PreferenceManager.instance().saveLocation(all);
                CommonMethod.addRecentCity(all);
                EventBus.getDefault().post(new Location(all));
                finish();
            }
        });
/*搜索的*/
        mAdapter = new CommonAdapter<String>(this,null,R.layout.item_search) {
            @Override
            public void setViewData(ViewHolder holder, String item, int position) {
                holder.setText(item, R.id.tv_search_content);
            }
        };
//        searchResultAdapter = new SearchResultAdapter(this, searchCityList, new HotInterface() {
//            @Override
//            public void Back(String name) {
//                EventBus.getDefault().post(new Location(name+""));
//                finish();
//            }
//        });//搜索城市列表适配器
        lvCity.setAdapter(mListViewAdapter);
        lvResult.setAdapter(mAdapter);
        lvResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EventBus.getDefault().post(new Location(mShopSuggest.get(position)));
                finish();
            }
        });
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.back_location);
        myLetterView = (MyLetterView) findViewById(R.id.my_letterview);
        tvDialog = (TextView) findViewById(R.id.tv_dialog);
        myLetterView.setTextView(tvDialog);
        lvCity = (ListView) findViewById(R.id.lv_city);
        etSearch = (EditText) findViewById(R.id.et_search);
        lvResult = (ListView) findViewById(R.id.lv_result);
        tvNoResult = (TextView) findViewById(R.id.tv_noresult);
    }
    private void setListener() {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationActivity.this.finish();
            }
        });
        //自定义myLetterView的一个监听
        myLetterView.setOnSlidingListener(new MyLetterView.OnSlidingListener() {
            @Override
            public void sliding(int position, String s) {
                int i = mListViewAdapter.getPositonBySort(s);
                lvCity.setSelection(i);
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString() == null || "".equals(s.toString())) {
                    myLetterView.setVisibility(View.VISIBLE);
                    lvCity.setVisibility(View.VISIBLE);
                    lvResult.setVisibility(View.GONE);
                    tvNoResult.setVisibility(View.GONE);
                } else {
                    myLetterView.setVisibility(View.GONE);
                    lvCity.setVisibility(View.GONE);
//                    getResultCityList(s.toString());
                    if (s.toString().trim().length()>0) {
                        LogUtils.d("输入详情" + s.toString());
                        getSearchCity(s.toString().trim());
                        LogUtils.d("这是新集合"+mShopSuggest);
                        lvResult.setVisibility(View.VISIBLE);
                        tvNoResult.setVisibility(View.GONE);
                    }
                }
//                if (s.toString() == null || "".equals(s.toString())) {
//                    myLetterView.setVisibility(View.VISIBLE);
//                    lvCity.setVisibility(View.VISIBLE);
//                    lvResult.setVisibility(View.GONE);
//                    tvNoResult.setVisibility(View.GONE);
//                } else {
//                    searchCityList.clear();
//                    myLetterView.setVisibility(View.GONE);
//                    lvCity.setVisibility(View.GONE);
//                    getSearchCity(s.toString());
//                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        lvCity.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_TOUCH_SCROLL
                        || scrollState == SCROLL_STATE_FLING) {
                    isScroll = true;
                }
            }
            @SuppressLint("DefaultLocale")
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if (!isScroll) {
                    return;
                }
                if (mReady) {
                    String text;
                    String name = allCityList.get(firstVisibleItem).getAreaName();
                    String pinyin = allCityList.get(firstVisibleItem).getSort();
                    if (firstVisibleItem < 4) {
                        text = name;
                    } else {
                        text = PingYinUtil.converterToFirstSpell(pinyin)
                                .substring(0, 1).toUpperCase();
                    }
                    tvDialog.setText(text);
                    tvDialog.setVisibility(View.VISIBLE);
                    handler.removeCallbacks(overlayThread);
//					Toast.makeText(MainActivity.this,"测试",0).show();
//					 延迟一秒后执行，让中间显示的TextView为不可见
                    handler.postDelayed(overlayThread, 1000);
                }
            }
        });
    }
    private void initData() {
        cityOpenHelper = new CitySqliteOpenHelper(LocationActivity.this);
        cityDb = cityOpenHelper.getWritableDatabase();
        allCityList = new ArrayList<>();
        mHotCities = new ArrayList<>();
        mDetailCities = new ArrayList<>();
        searchCityList = new ArrayList<City>();
        recentCityList = new ArrayList<String>();//最近访问城市
        handler = new Handler();
        overlayThread = new OverlayThread();//设置显示字母的TextView为不可见
    }
    /**
     * 初始化所有城市列表
     */
    private void initAllCityData() {
        AllCity.DatasBean.DatasBeanTow city = new AllCity.DatasBean.DatasBeanTow(); // 当前定位城市
        city.setAreaName("最近");
        city.setSort("1");
        allCityList.add(city);
        city.setAreaName("热门");
        city.setSort("2");
        allCityList.add(city);
        city.setAreaName("全部");
        city.setSort("3");
        allCityList.add(city);
        getAllCity();
    }
    @SuppressWarnings("unchecked")
    private ArrayList<City> getCityList() {
        SQLiteDatabase db;
        Cursor cursor = null;
        //获取assets目录下的数据库中的所有城市的openHelper
        AllCitySqliteOpenHelper op = new AllCitySqliteOpenHelper(LocationActivity.this);
        ArrayList<City> cityList = new ArrayList<City>();
        try {
            op.createDataBase();
            db = op.getWritableDatabase();
            cursor = db.rawQuery("select * from city", null);
            while (cursor.moveToNext()) {
                String cityName = cursor.getString(cursor.getColumnIndex("name"));
                String cityPinyin = cursor.getString(cursor.getColumnIndex("pinyin"));
                City city = new City(cityName, cityPinyin);
                cityList.add(city);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        Collections.sort(cityList, comparator);
        return cityList;
    }
    private void saveClickCity() {
    }
    /**
     * 自定义的排序规则，按照A-Z进行排序
     */
    @SuppressWarnings("rawtypes")
    Comparator comparator = new Comparator<City>() {
        @Override
        public int compare(City lhs, City rhs) {
            String a = lhs.getPinyin().substring(0, 1);
            String b = rhs.getPinyin().substring(0, 1);
            int flag = a.compareTo(b);
            if (flag == 0) {
                return a.compareTo(b);
            } else {
                return flag;
            }
        }
    };
    @SuppressWarnings("unchecked")
    private void getResultCityList(String keyword) {
        AllCitySqliteOpenHelper dbHelper = new AllCitySqliteOpenHelper(this);
        try {
            dbHelper.createDataBase();
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery(
                    "select * from city where name like \"%" + keyword
                            + "%\" or pinyin like \"%" + keyword + "%\"", null);
            City city;
            while (cursor.moveToNext()) {
                String cityName = cursor.getString(cursor.getColumnIndex("name"));
                String cityPinyin = cursor.getString(cursor.getColumnIndex("pinyin"));
                city = new City(cityName, cityPinyin);
                searchCityList.add(city);
            }
            cursor.close();
            db.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将得到的集合按照自定义的comparator的规则进行排序
        Collections.sort(searchCityList, comparator);
    }

    // 设置显示字母的TextView为不可见
    private class OverlayThread implements Runnable {
        @Override
        public void run() {
            tvDialog.setVisibility(View.INVISIBLE);
        }
    }
    //插入数据到最近访问的城市
    public void InsertCity(String name) {
        SQLiteDatabase db = cityOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from recentcity where name = '"
                + name + "'", null);
        if (cursor.getCount() > 0) { //
            db.delete("recentcity", "name = ?", new String[]{name});
        }
        db.execSQL("insert into recentcity(name, date) values('" + name + "', "
                + System.currentTimeMillis() + ")");
        db.close();
    }
    /**
     * 全部城市
     */
    private void getAllCity() {
        HashMap<String, String> param = new HashMap<>();
        HttpHelper.getInstance().post(Url.GET_ALLCITY, param, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                LogUtils.d("获取全部城市成功：" + response);
                AllCity city = JsonUtils.parse(response, AllCity.class);
                JsonUtils.parse(response,HotCity.DatasBean.class);
                allCityList.addAll(city.getDatas().getCityPosition());
                mListViewAdapter.updateAll(allCityList);
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                Log.d("wyt", "获取全部城市失败：" + error_msg);
            }
        });
    }
    /**
     * 获得最近访问的三个城市
     */
    private void getRecentCity() {
        Map<String, String> param = new HashMap<>();
        param.put("token", PreferenceManager.instance().getToken());
        HttpHelper.getInstance().post(Url.GET_RECENT, param, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.d("wyt", "获得最近访问三个城市成功：" + response);
                RecentCityO.DatasBean re = new RecentCityO.DatasBean();
                re.setCity("总部");
                re.setId(1);
                recentCitis.add(re);
                RecentCityO cityO = JsonUtils.parse(response, RecentCityO.class);
                JsonUtils.parse(response, RecentCityO.DatasBean.class);
                if (cityO.getCode()==1) {
                    recentCitis.addAll(cityO.getDatas());
                    mListViewAdapter.updateRecent(recentCitis);
                } else if (cityO.getCode() == 0) {
                    Toast.makeText(LocationActivity.this, cityO.getMsg(), Toast.LENGTH_SHORT).show();
                    mListViewAdapter.updateRecent(recentCitis);
                } else {
                    PreferenceManager.instance().removeToken();
                    mListViewAdapter.updateRecent(recentCitis);
//                    Toast.makeText(LocationActivity.this, cityO.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                Log.d("wyt", "获得最近访问三个城市失败：" + error_msg);
            }
        });
    }
    /**
     * 获取热门城市
     */
    private void getHotCity() {
        Map<String, String> param = new HashMap<>();
        HttpHelper.getInstance().post(Url.GET_HOTCITY, param, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.d("wyt", "获取热门城市成功：" + response);
                HotCity city = JsonUtils.parse(response, HotCity.class);
                JsonUtils.parse(response, HotCity.DatasBean.class);
                if (city.getCode()==1&&city.getDatas().size()>0) {
                    mHotCities.addAll(city.getDatas());
                }
                //根据sort排序
                Collections.sort(mHotCities, new Comparator<HotCity.DatasBean>() {
                    @Override
                    public int compare(HotCity.DatasBean datasBean, HotCity.DatasBean t1) {
                        int i1 = Integer.valueOf(datasBean.getSort());
                        int i2 = Integer.valueOf(t1.getSort());
                        if (i1 < i2) {
                            Log.d("wyt", "1");
                            return 1;
                        }
                        return -1;
                    }
                });
                mListViewAdapter.updateHot(mHotCities);
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                Log.d("wyt", "获取热门城市失败：" + error_msg);
            }
        });
    }
    /**
     * 获取城市详情
     */
    private void getCityDetail(String cityName) {
        Map<String, String> param = new HashMap<>();
        param.put("cityName", cityName);
        HttpHelper.getInstance().post(Url.GET_CITYDETAIL, param, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.d("wyt", "获取城市详情成功：" + response);
                CityDetail cityDetail = JsonUtils.parse(response, CityDetail.class);
                JsonUtils.parse(response, CityDetail.DatasBean.class);
                if (cityDetail.getCode()==1&&cityDetail.getDatas().size()>0) {
                    mDetailCities.addAll(cityDetail.getDatas());
                    mListViewAdapter.updateDetail(mDetailCities);
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                Log.d("wyt", "获取城市详情失败：" + error_msg);
            }
        });
    }
    /**
     * 展示搜索pop
     */
    private void showSearchPop(List<String> shopList) {
        View popView = getLayoutInflater().inflate(R.layout.item_search_pop, null);
        ListView lvSearch = (ListView) popView.findViewById(R.id.lv_search_pop);
        lvSearch.setAdapter(new CommonAdapter<String>(this,shopList,R.layout.item_search) {
            @Override
            public void setViewData(ViewHolder holder, String item, int position) {
                holder.setText(item, R.id.tv_search_content);
            }
        });
        final PopupWindow popSearch = new PopupWindow(popView,
                ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT, false);
        popSearch.setFocusable(true);
        popSearch.setBackgroundDrawable(new BitmapDrawable());
//        popSearch.showAtLocation(popView, Gravity.BOTTOM,0,50);
        popSearch.showAsDropDown(mBack, 0, 130);
//        popSearch.showAsDropDown(getView(), DisplayUtils.getWidthPx() / 2 - popSearch.getWidth() / 2
//                ,0);//备注：什么时候dissmiss就将它dissmiss，自己设计好
        popSearch.setBackgroundDrawable(new BitmapDrawable());
        popSearch.setOutsideTouchable(true);
        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EventBus.getDefault().post(new Location(mShopSuggest.get(position)));
                finish();
            }
        });
    }
////热门城市搜索
//    private void popSuggest() {
//        Map<String, String> param = new HashMap<>();
//        HttpHelper.getInstance().post(Url.SHOP_SUGGEST, param, new RawResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, String response) {
//                Log.d("wyt", "搜索提示成功：" + response);
//                ShopSuggest su = JsonUtils.parse(response, ShopSuggest.class);
//                if (su.getCode()==1&&su.getResult().size()>0) {
//                    mShopSuggest.addAll(su.getResult());
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, String error_msg) {
//                Log.d("wyt", "搜索提示失败：" + error_msg);
//            }
//        });
//    }
    private void getSearchCity(String key){
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("keywords", key);
            HttpHelper.getInstance().post(Url.OPEN_SEARCH, hashMap, new RawResponseHandler() {
                @Override
                public void onSuccess(int statusCode, String response) {
                    ShopSuggest su = JsonUtils.parse(response, ShopSuggest.class);
                    LogUtils.d("woowjdjddj"+response);
                    if (su.getCode()==1&&su.getResult().size()>0) {
                        if (mShopSuggest!=null) {
                            mShopSuggest.clear();
                        }
                        mShopSuggest.addAll(su.getResult());
                        LogUtils.d("集合"+mShopSuggest);
                        mAdapter.update(mShopSuggest);
//                        showSearchPop(mShopSuggest);
//                        searchCityList.addAll(su.getResult())
                    } else if (su.getCode()==0) {
                        Toast.makeText(LocationActivity.this, su.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(int statusCode, String error_msg) {
                }
            });
    }



}
