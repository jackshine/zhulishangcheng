package com.example.ddm.appui.mine.myhome;
import android.app.Dialog;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ddm.R;
import com.example.ddm.appui.BaseFragment;
import com.example.ddm.appui.adapter.AreaBaseAdapter;
import com.example.ddm.appui.adapter.CityBaseAdapter;
import com.example.ddm.appui.adapter.ProviceBaseAdapter;
import com.example.ddm.appui.utils.ProviceCityUtil;
import com.example.ddm.appui.utils.StringUtils;

import org.xmlpull.v1.XmlPullParserException;
/**
 * A simple {@link Fragment} subclass.
 * 添加地址
 */
public class AddAddressFragment extends BaseFragment {
    private TextView mBack;
    private EditText et_name;
    private EditText et_phone;
    private EditText et_address;
    private LinearLayout ll_select_nomal;
    private Button btn_commit;
    private EditText et_place;
    private LinearLayout ll_select_place;
    private PopupWindow pop;
    private String AddressXML;
    private Button btn_province;
    private Button btn_city;
    private Button btn_county;
    private String uid;
    private CheckBox iv_select_default_address;
    private int pPosition;
    private int cPosition;
    private boolean isCity = true;
    private boolean isCounty = true;
    private String name;
    private String phone;
    private String address;
    private String detail_address;
    private boolean isSelect;
    private String defaultFlag="0";
    public AddAddressFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_address, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mBack = mFindViewUtils.findViewById(R.id.app_title_back);
        et_name = mFindViewUtils.findViewById(R.id.et_shouhuo_name);
        et_phone = mFindViewUtils.findViewById(R.id.et_phone_address);
        et_address = mFindViewUtils.findViewById(R.id.et_detail_address);
        ll_select_nomal = mFindViewUtils.findViewById(R.id.ll_select_editor_address);
        btn_commit = mFindViewUtils.findViewById(R.id.btn_submit_save_address);
        et_place = mFindViewUtils.findViewById(R.id.et_address_place);
        ll_select_place =mFindViewUtils.findViewById(R.id.ll_select_place);
        iv_select_default_address = mFindViewUtils.findViewById(R.id.iv_select_nomal_address);
        if (uid == null) {
            uid = getActivity().getIntent().getStringExtra("uid");
        }
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
        btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = et_name.getText().toString().trim();
                phone = et_phone.getText().toString().trim();
                address = et_place.getText().toString().trim();
                detail_address = et_address.getText().toString().trim();
                if (TextUtils.isEmpty(name) || name == null) {
                    Toast.makeText(getContext(), "请填写收货人", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phone) || phone == null) {
                    Toast.makeText(getContext(), "请填写手机号", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!StringUtils.isMobileNO(phone)) {
                    Toast.makeText(getContext(), "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(address) || address == null) {
                    Toast.makeText(getContext(), "请选择所在地", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(detail_address) || detail_address == null) {
                    Toast.makeText(getContext(), "请填写详细地址ַ", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
        ll_select_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initPopWindow();
                et_place.setText(btn_province.getText().toString() + btn_city.getText().toString() + btn_county.getText().toString());
            }
        });

        ll_select_nomal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }
    private void initPopWindow() {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        View view = inflater.inflate(R.layout.pop_select_city_provice, null);
        initFindView(view);
        initpopData();
        pop = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);

        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setAnimationStyle(R.style.main_menu_animstyle);

        pop.setOutsideTouchable(true);
        pop.setFocusable(true);
        pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);

    }
    public void initFindView(View view) {
        btn_province = (Button) view.findViewById(R.id.btn_province);
        btn_city = (Button) view.findViewById(R.id.btn_city);
        btn_county = (Button) view.findViewById(R.id.btn_county);
        btn_province.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog(1);
            }
        });
        btn_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCity == true) {
                    createDialog(2);
                }
            }
        });
        btn_county.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCounty == true) {
                    createDialog(3);
                }
            }
        });
    }

    public void createDialog(final int type) {
        ListView lv = new ListView(getContext());
        final Dialog dialog = new Dialog(getContext());
        dialog.setTitle("列表选择框");

        if (type == 1) {
            ProviceBaseAdapter pAdapter = new ProviceBaseAdapter(ProviceCityUtil.provinceList, getContext());
            lv.setAdapter(pAdapter);

        } else if (type == 2) {
            CityBaseAdapter cAdapter = new CityBaseAdapter(ProviceCityUtil.provinceList.get(pPosition).getCity_list(), getContext());
            lv.setAdapter(cAdapter);
        } else if (type == 3) {
            AreaBaseAdapter coAdapter = new AreaBaseAdapter(ProviceCityUtil.provinceList.get(pPosition).getCity_list().get(cPosition).getCounty_list(), getContext());
            lv.setAdapter(coAdapter);
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                if (type == 1) {
                    pPosition = position;
                    btn_province.setText(ProviceCityUtil.provinceList.get(position).getProvince());
                    et_place.setText(btn_province.getText().toString() + btn_city.getText().toString() + btn_county.getText().toString());
                    if (ProviceCityUtil.provinceList.get(position).getCity_list().size() < 1) {
                        btn_city.setText("");
                        btn_county.setText("");
                        isCity = false;
                        isCounty = false;
                    } else {
                        isCity = true;
                        btn_city.setText(ProviceCityUtil.provinceList.get(position).getCity_list().get(0).getCity());
                        et_place.setText(btn_province.getText().toString() + btn_city.getText().toString() + btn_county.getText().toString());
                        cPosition = 0;
                        if (ProviceCityUtil.provinceList.get(position).getCity_list().get(0).getCounty_list().size() < 1) {
                            btn_county.setText("");
                            isCounty = false;

                        } else {
                            isCounty = true;
                            btn_county.setText(ProviceCityUtil.provinceList.get(position).getCity_list().get(0).getCounty_list().get(0).getCounty());
                            et_place.setText(btn_province.getText().toString() + btn_city.getText().toString() + btn_county.getText().toString());
                        }

                    }

                } else if (type == 2) {
                    cPosition = position;
                    btn_city.setText(ProviceCityUtil.provinceList.get(pPosition).getCity_list().get(position).getCity());
                    et_place.setText(btn_province.getText().toString() + btn_city.getText().toString() + btn_county.getText().toString());
                    if (ProviceCityUtil.provinceList.get(pPosition).getCity_list().get(position).getCounty_list().size() < 1) {
                        btn_county.setText("");
                        isCounty = false;
                    } else {
                        isCounty = true;
                        btn_county.setText(ProviceCityUtil.provinceList.get(pPosition).getCity_list().get(cPosition).getCounty_list().get(0).getCounty());
                        et_place.setText(btn_province.getText().toString() + btn_city.getText().toString() + btn_county.getText().toString());
                    }

                } else if (type == 3) {
                    btn_county.setText(ProviceCityUtil.provinceList.get(pPosition).getCity_list().get(cPosition).getCounty_list().get(position).getCounty());
                    et_place.setText(btn_province.getText().toString() + btn_city.getText().toString() + btn_county.getText().toString());
                }

                dialog.dismiss();
            }
        });

        dialog.setContentView(lv);
        dialog.show();
        et_place.setText(btn_province.getText().toString() + btn_city.getText().toString() + btn_county.getText().toString());
    }
    public void initpopData() {
        AddressXML = ProviceCityUtil.getRawAddress(getContext()).toString();//获取中国省市区信息
        try {
            ProviceCityUtil.analysisXML(AddressXML);
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //初始化button数据
        btn_province.setText(ProviceCityUtil.provinceList.get(0).getProvince());
        btn_city.setText(ProviceCityUtil.provinceList.get(0).getCity_list().get(0).getCity());
        btn_county.setText(ProviceCityUtil.provinceList.get(0).getCity_list().get(0).getCounty_list().get(0).getCounty());
        //初始化列表下标
        pPosition = 0;
        cPosition = 0;
    }
}
