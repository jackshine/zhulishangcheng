package com.example.ddm.appui.home;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.adapter.CommonAdapter;
import com.example.ddm.appui.adapter.ViewHolder;
import com.example.ddm.appui.bean.OpenSearch;
import com.example.ddm.appui.bean.ShopSuggest;
import com.example.ddm.appui.constant.FragmentTag;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.db.RecordSQLiteOpenHelper;
import com.example.ddm.appui.utils.DisplayUtils;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.ShowFragmentUtils;
import com.example.ddm.appui.view.Search_Listview;
import com.example.ddm.appui.view.ZFlowLayout;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * 搜索界面
 */
public class SearchFragment extends BaseFragment {
    private TextView tv_clear;
    private EditText et_search;
    private TextView tv_tip;
    private ZFlowLayout mFlowLayout;
    private TextView iv_search;
    private ImageView mImageView;
    /*列表及其适配器*/
    private Search_Listview listView;
    private BaseAdapter adapter;
    /*数据库变量*/
    private RecordSQLiteOpenHelper helper ;
    private SQLiteDatabase db;
    private String[] mLabels;
    private List<String> mShopSuggest = new ArrayList<>();//店铺类型的集合
    public SearchFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mImageView = mFindViewUtils.findViewById(R.id.pic);
        mFlowLayout = mFindViewUtils.findViewById(R.id.flowLayout);
        et_search = mFindViewUtils.findViewById(R.id.et_search);
        tv_clear = mFindViewUtils.findViewById(R.id.tv_clear);
        tv_tip = mFindViewUtils.findViewById(R.id.tv_tip);
        listView = mFindViewUtils.findViewById(R.id.listView);
        iv_search = mFindViewUtils.findViewById(R.id.iv_search);
        openSearch();
    }
    @Override
    protected void setListener() {
        super.setListener();
        //实例化数据库SQLiteOpenHelper子类对象
        helper = new RecordSQLiteOpenHelper(getContext());
        // 第一次进入时查询所有的历史记录
        queryData("");
        //"清空搜索历史"按钮
        tv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清空数据库
                deleteData();
                queryData("");
            }
        });
        //搜索框的文本变化实时监听
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (s.toString() == null || "".equals(s.toString())) {
//                    Toast.makeText(mBaseActivity, "内容不能为空", Toast.LENGTH_SHORT).show();
//                } else {
//                    popSuggest(s.toString());
//                }
            }
            //输入后调用该方法
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() == 0) {
                    //若搜索框为空,则模糊搜索空字符,即显示所有的搜索历史
                    tv_tip.setText("搜索历史");
                } else if (s.toString().length()>=1) {
                    popSuggest(et_search.getText().toString().trim());
                    tv_tip.setText("搜索结果");
                }
                //每次输入后都查询数据库并显示
                //根据输入的值去模糊查询数据库中有没有数据
                String tempName = et_search.getText().toString();
                queryData(tempName);
            }
        });
        // 搜索框的键盘搜索键
        // 点击回调
        et_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showSearchPop(v);

            }
        });
        et_search.setOnKeyListener(new View.OnKeyListener() {// 输入完后按键盘上的搜索键
            // 修改回车键功能
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    // 隐藏键盘，这里getCurrentFocus()需要传入Activity对象，如果实际不需要的话就不用隐藏键盘了，免得传入Activity对象，这里就先不实现了
//                    ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
//                            getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    // 按完搜索键后将当前查询的关键字保存起来,如果该关键字已经存在就不执行保存
                    boolean hasData = hasData(et_search.getText().toString().trim());
                    if (!hasData) {
                        insertData(et_search.getText().toString().trim());

                        queryData("");
                    }
                    //根据输入的内容模糊查询商品，并跳转到另一个界面，这个需要根据需求实现
                    Toast.makeText(getContext(), "点击搜索", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        //列表监听
        //即当用户点击搜索历史里的字段后,会直接将结果当作搜索字段进行搜索
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获取到用户点击列表里的文字,并自动填充到搜索框内
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                String name = textView.getText().toString();
                et_search.setText(name);
                mArguments.putString("hot",name);
                ShowFragmentUtils.showFragment(getActivity(),SearchShoppingFragment.class, FragmentTag.SEARCHSHOPPINGFRAGMENT,mArguments,true);
            }
        });
//        // 插入数据，便于测试，否则第一次进入没有数据怎么测试呀？
//        Date date = new Date();
//        long time = date.getTime();
//        insertData("Leo" + time);

        //点击搜索按钮后的事件
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean hasData = hasData(et_search.getText().toString().trim());
                if (!hasData) {
                    insertData(et_search.getText().toString().trim());
                    //搜索后显示数据库里所有搜索历史是为了测试
                    queryData("");
                }
                //根据输入的内容模糊查询商品，并跳转到另一个界面，这个根据需求实现
                mArguments.putString("hot",et_search.getText().toString().trim());
                ShowFragmentUtils.showFragment(getActivity(),SearchShoppingFragment.class, FragmentTag.SEARCHSHOPPINGFRAGMENT,mArguments,true);
            }
        });
        mImageView.setOnClickListener(new View.OnClickListener() {
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
    /*插入数据*/
    private void insertData(String tempName) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into records(name) values('" + tempName + "')");
        db.close();
    }
    /*模糊查询数据 并显示在ListView列表上*/
    private void queryData(String tempName) {
        //模糊搜索
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name like '%" + tempName + "%' order by id desc ", null);
        // 创建adapter适配器对象,装入模糊搜索的结果
        adapter = new SimpleCursorAdapter(getContext(), android.R.layout.simple_list_item_1, cursor, new String[] { "name" },
                new int[] { android.R.id.text1 }, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        // 设置适配器
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    /*检查数据库中是否已经有该条记录*/
    private boolean hasData(String tempName) {
        //从Record这个表里找到name=tempName的id
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        //判断是否有下一个
        return cursor.moveToNext();
    }
    /*清空数据*/
    private void deleteData() {
        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
    }
    // 初始化标签
    private void initLabel() {
       ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(30, 30, 10, 10);// 设置边距
        for (int i = 0; i < mLabels.length; i++) {
            final TextView textView = new TextView(getContext());
            textView.setTag(i);
            textView.setTextSize(15);
            textView.setText(mLabels[i]);
            textView.setPadding(24, 11, 24, 11);
            textView.setTextColor(Color.BLACK);
            textView.setBackgroundResource(R.drawable.lable_item_bg_normal);
            mFlowLayout.addView(textView, layoutParams);
            // 标签点击事件
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mArguments.putString("hot",mLabels[(int) textView.getTag()]);
                    ShowFragmentUtils.showFragment(getActivity(),SearchShoppingFragment.class, FragmentTag.SEARCHSHOPPINGFRAGMENT,mArguments,true);
//                    Toast.makeText(getContext(), mLabels[(int) textView.getTag()], Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    /**
     * 展示搜索pop
     */
    private void showSearchPop(List<String> shopList) {
        View popView = getActivity().getLayoutInflater().inflate(R.layout.item_search_pop, null);
        ListView lvSearch = (ListView) popView.findViewById(R.id.lv_search_pop);
        lvSearch.setAdapter(new CommonAdapter<String>(getActivity(),shopList,R.layout.item_search) {
            @Override
            public void setViewData(ViewHolder holder, String item, int position) {
                holder.setText(item, R.id.tv_search_content);
            }
        });

        final PopupWindow popSearch = new PopupWindow(popView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        popSearch.setFocusable(true);
        popSearch.setBackgroundDrawable(new BitmapDrawable());
//        popSearch.showAtLocation(popView, Gravity.BOTTOM,0,50);
        popSearch.showAsDropDown(mImageView, 0, 45);
//        popSearch.showAsDropDown(getView(), DisplayUtils.getWidthPx() / 2 - popSearch.getWidth() / 2
//                ,0);//备注：什么时候dissmiss就将它dissmiss，自己设计好
        popSearch.setBackgroundDrawable(new BitmapDrawable());
        popSearch.setOutsideTouchable(true);
        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    popSearch.dismiss();
                boolean hasData = hasData(mShopSuggest.get(position));
                if (!hasData) {
                    insertData(mShopSuggest.get(position));
                    //搜索后显示数据库里所有搜索历史是为了测试
                    queryData("");
                }
                mArguments.putString("hot",mShopSuggest.get(position));
                ShowFragmentUtils.showFragment(getActivity(),SearchShoppingFragment.class, FragmentTag.SEARCHSHOPPINGFRAGMENT,mArguments,true);
            }
        });
    }
    //搜索框
    private void popSuggest(String key) {
        Map<String, String> param = new HashMap<>();
        param.put("keywords", key);
        HttpHelper.getInstance().post(Url.SHOP_SUGGEST, param, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                LogUtils.d("我的人盾冬大法好==="+response);
                ShopSuggest su = JsonUtils.parse(response, ShopSuggest.class);
                if (su.getCode()==1&&su.getResult().size()>0) {
                    mShopSuggest.addAll(su.getResult());
                    showSearchPop(mShopSuggest);
                } else if (su.getCode()==0) {
                    Toast.makeText(getContext(), su.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                Log.d("wyt", "搜索提示失败：" + error_msg);
            }
        });
    }
    //热门搜索
    private void openSearch() {
        Map<String, String> param = new HashMap<>();
        param.put("city", PreferenceManager.instance().getLocation());
        HttpHelper.getInstance().post(Url.SEARCH, param, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.d("wyt", "热门搜索成功：" + response);
                OpenSearch openSearch = JsonUtils.parse(response, OpenSearch.class);
                mLabels = openSearch.getDatas();
                initLabel();
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                Log.d("wyt", "热门搜索失败:" + error_msg);
            }
        });
    }
}
