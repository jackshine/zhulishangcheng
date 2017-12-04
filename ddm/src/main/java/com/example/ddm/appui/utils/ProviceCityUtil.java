package com.example.ddm.appui.utils;
import android.content.Context;

import com.example.ddm.R;
import com.example.ddm.appui.model.CityModel;
import com.example.ddm.appui.model.CountyModel;
import com.example.ddm.appui.model.ProvinceModel;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Administrator on 2017/3/21.
 *
 */
public class ProviceCityUtil {
    public static List<ProvinceModel> provinceList; //地址列表

    /**
     * 获取地区raw里的地址xml内容
     * */
    public static StringBuffer getRawAddress(Context context){
        InputStream in = context.getResources().openRawResource(R.raw.address);
        InputStreamReader isr = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(isr);
        StringBuffer sb = new StringBuffer();
        String line = null;
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        try {
            br.close();
            isr.close();
            in.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return sb;
    }

    /**
     * 解析省市区xml，
     * 采用的是pull解析，
     * 为什么选择pull解析：因为pull解析简单浅显易懂！
     * */
    public static void analysisXML(String data) throws XmlPullParserException {
        try {
            ProvinceModel provinceModel = null;
            CityModel cityModel= null;
            CountyModel countyModel= null;
            List<CityModel> cityList = null;
            List<CountyModel> countyList= null;

            InputStream xmlData = new ByteArrayInputStream(data.getBytes("UTF-8"));
            XmlPullParserFactory factory = null;
            factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser;
            parser = factory.newPullParser();
            parser.setInput(xmlData, "utf-8");
            String currentTag = null;

            String province;
            String city;
            String county;

            int type = parser.getEventType();
            while (type != XmlPullParser.END_DOCUMENT) {
                String typeName = parser.getName();

                if (type == XmlPullParser.START_TAG) {
                    if("root".equals(typeName)){
                        provinceList = new ArrayList<ProvinceModel>();

                    }else if("province".equals(typeName)){
                        province = parser.getAttributeValue(0);//获取标签里第一个属性,例如<city name="北京市" index="1">中的name属性
                        provinceModel = new ProvinceModel();
                        provinceModel.setProvince(province);
                        cityList = new ArrayList<CityModel>();

                    }else if("city".equals(typeName)){
                        city = parser.getAttributeValue(0);
                        cityModel = new CityModel();
                        cityModel.setCity(city);
                        countyList = new ArrayList<CountyModel>();

                    }else if("area".equals(typeName)){
                        county = parser.getAttributeValue(0);
                        countyModel  = new CountyModel();
                        countyModel.setCounty(county);

                    }

                    currentTag = typeName;

                } else if (type == XmlPullParser.END_TAG) {
                    if("root".equals(typeName)){

                    }else if("province".equals(typeName)){
                        provinceModel.setCity_list(cityList);
                        provinceList.add(provinceModel);

                    }else if("city".equals(typeName)){
                        cityModel.setCounty_list(countyList);
                        cityList.add(cityModel);

                    }else if("area".equals(typeName)){
                        countyList.add(countyModel);
                    }


                } else if (type == XmlPullParser.TEXT) {

                    currentTag = null;
                }

                type = parser.next();
            }

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
