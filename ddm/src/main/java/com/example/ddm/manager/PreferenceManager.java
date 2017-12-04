package com.example.ddm.manager;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hongchuanwei .
 * on 2017/2/23
 */
public class PreferenceManager {

    private static PreferenceManager mInstance;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private PreferenceManager() {
    }

    public static PreferenceManager instance() {
        if (mInstance == null) {
            mInstance = new PreferenceManager();
        }
        return mInstance;
    }
    public void init(Context context) {
        mSharedPreferences = context.getSharedPreferences("userdata", Context.MODE_PRIVATE);
        if (mSharedPreferences != null) {
            mEditor = mSharedPreferences.edit();
            mEditor.apply();
        }
    }
    /**
     * 保存token
     *
     * @param token :
     */
    public void saveToken(String token) {
        if (mEditor != null) {
            mEditor.putString(Key.TOKEN, token);
            mEditor.commit();
        }
    }
    public String getToken() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.TOKEN, "");
        }
        return "";
    }
    /**
     * 保存时间
     *
     * @param time :
     */
    public void saveID(String time) {
        if (mEditor != null) {
            mEditor.putString(Key.EXPIRES, time);
            mEditor.commit();
        }
    }
    public String getID() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.EXPIRES,"");
        }
        return "";
    }
    /**
     * 保存name
     *
     * @param name :
     */
    public void saveName(String name) {
        if (mEditor != null) {
            mEditor.putString(Key.USERNAME, name);
            mEditor.commit();
        }
    }

    public String getName() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.USERNAME, "");
        }
        return "";
    }
    /**保存手机号
     * @param phone
     */
    public void savePhoneNum(String phone) {
        if (mEditor != null) {
            mEditor.putString(Key.PHONE, phone);
            mEditor.commit();
        }
    }
    public String getPhoneNum() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.PHONE, "");
        }
        return "";
    }
    /**保存的key
     * @param key
     */
    public void saveKey(String key) {
        if (mEditor != null) {
            mEditor.putString(Key.KEY, key);
            mEditor.commit();
        }
    }
    public String getKey() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.KEY, "");
        }
        return "";
    }

    /**
     * 保存密码
     *
     * @param pwd :
     */
    public void savePwd(String pwd) {
        if (mEditor != null) {
            mEditor.putString(Key.PASSWORD, pwd);
            mEditor.commit();
        }
    }

    public String getPwd() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.PASSWORD, "");
        }
        return "";
    }
    /**保存userID
     * @param id :
     */
    public void saveUserId(String id) {
        if (mEditor != null) {
            mEditor.putString(Key.USER_ID, id);
            mEditor.commit();
        }
    }
    public String getUserId() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.USER_ID, "");
        }
        return "";
    }
    /**保存商品ID
     * @param shop_id :
     */
    public void saveShopId(String shop_id) {
        if (mEditor != null) {
            mEditor.putString(Key.SHOP_ID, shop_id);
            mEditor.commit();
        }
    }
    public String getShopId() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.SHOP_ID, "");
        }
        return "";
    }
    /**保存订单ID
     * @param order_id :
     */
    public void saveOrderId(String order_id) {
        if (mEditor != null) {
            mEditor.putString(Key.ORDER_ID, order_id);
            mEditor.commit();
        }
    }
    public String getOrderId() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.ORDER_ID, "");
        }
        return "";
    }
    /**保存分类名称
     * @param classname :
     */
    public void saveClassName(String classname) {
        if (mEditor != null) {
            mEditor.putString(Key.CLASS_NAME, classname);
            mEditor.commit();
        }
    }
    public String getClassName() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.CLASS_NAME, "");
        }
        return "";
    }
    /**保存开户卡号
     * @param cardnum :
     */
    public void saveCardnum(String cardnum) {
        if (mEditor != null) {
            mEditor.putString(Key.CARDNUM, cardnum);
            mEditor.commit();
        }
    }
    public String getCardnum() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.CARDNUM, "");
        }
        return "";
    }
    /**保存开户支行
     * @param kaihuhang :
     */
    public void saveKaihuhang(String kaihuhang) {
        if (mEditor != null) {
            mEditor.putString(Key.KAIHUHANG, kaihuhang);
            mEditor.commit();
        }
    }
    public String getKaihuhang() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.KAIHUHANG, "");
        }
        return "";
    }
    /**保存开户行ID
     * @param bankid :
     */
    public void savebankid(String bankid) {
        if (mEditor != null) {
            mEditor.putString(Key.BANKID, bankid);
            mEditor.commit();
        }
    }
    public String getbankid() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.BANKID, "");
        }
        return "";
    }
    /**保存开户人
     * @param accountname :
     */
    public void saveAccountname(String accountname) {
        if (mEditor != null) {
            mEditor.putString(Key.ACCOUNTNAME, accountname);
            mEditor.commit();
        }
    }
    public String getAccountname() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.ACCOUNTNAME, "");
        }
        return "";
    }
    /**保存邮箱
     * @param email :
     */
    public void saveEmail(String email) {
        if (mEditor != null) {
            mEditor.putString(Key.EMAIL, email);
            mEditor.commit();
        }
    }
    public String getEmail() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.EMAIL, "");
        }
        return "";
    }
    /**保存微信
     * @param weixin :
     */
    public void saveWeixin(String weixin) {
        if (mEditor != null) {
            mEditor.putString(Key.WEIXIN, weixin);
            mEditor.commit();
        }
    }
    public String getWeixin() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.WEIXIN, "");
        }
        return "";
    }
    /**保存QQ
     * @param qq :
     */
    public void saveQq(String qq) {
        if (mEditor != null) {
            mEditor.putString(Key.QQ, qq);
            mEditor.commit();
        }
    }
    public String getQq() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.QQ, "");
        }
        return "";
    }
    /**保存性别
     * @param sex :
     */
    public void saveSex(String sex) {
        if (mEditor != null) {
            mEditor.putString(Key.SEX, sex);
            mEditor.commit();
        }
    }
    public String getSex() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.SEX, "");
        }
        return "";
    }
    /**保存身份证号
     * @param identity :
     */
    public void saveIdentity(String identity) {
        if (mEditor != null) {
            mEditor.putString(Key.IDENTITY, identity);
            mEditor.commit();
        }
    }
    public String getIdentity() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.IDENTITY, "");
        }
        return "";
    }
    /**保存消息id
     * @param news_id :
     */
    public void saveNewsId(String news_id) {
        if (mEditor != null) {
            mEditor.putString(Key.NEWS_ID, news_id);
            mEditor.commit();
        }
    }
    public String getNewsId() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.NEWS_ID, "");
        }
        return "";
    }
    /**保存市
     * @param city :
     */
    public void saveCity(String city) {
        if (mEditor != null) {
            mEditor.putString(Key.CITY, city);
            mEditor.commit();
        }
    }
    public String getCity() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.CITY, "");
        }
        return "";
    }
    /**保存省
     * @param province :
     */
    public void saveProvince(String province) {
        if (mEditor != null) {
            mEditor.putString(Key.PROVINCE, province);
            mEditor.commit();
        }
    }
    public String getProvince() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.PROVINCE, "");
        }
        return "";
    }
    /**保存省id
     * @param town :
     */
    public void saveTown(String town) {
        if (mEditor != null) {
            mEditor.putString(Key.TOWN, town);
            mEditor.commit();
        }
    }
    public String getTown() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.TOWN, "");
        }
        return "";
    }
    /**具体等級
     * @param useridname :
     */
    public void saveUserIdName(String useridname) {
        if (mEditor != null) {
            mEditor.putString(Key.USER_ID_Name, useridname);
            mEditor.commit();
        }
    }

    public String getUserIdName() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.USER_ID_Name, "");
        }
        return "";
    }
    /**位置
     * @param location :
     */
    public void saveLocation(String location) {
        if (mEditor != null) {
            mEditor.putString(Key.LOCATION, location);
            mEditor.commit();
        }
    }

    public String getLocation() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.LOCATION, "");
        }
        return "";
    }

    /**1级人物
     * @param oneperson :
     */
    public void saveOnePerson(String oneperson) {
        if (mEditor != null) {
            mEditor.putString(Key.ONEPERSON, oneperson);
            mEditor.commit();
        }
    }

    public String getOnePerson() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.ONEPERSON, "");
        }
        return "";
    }
    /**2级人物
     * @param twoperson :
     */
    public void saveTwoPerson(String twoperson) {
        if (mEditor != null) {
            mEditor.putString(Key.TWOPERSON, twoperson);
            mEditor.commit();
        }
    }

    public String getTwoPerson() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.TWOPERSON, "");
        }
        return "";
    }
    /**3级人物
     * @param thressperson :
     */
    public void saveThressPerson(String thressperson) {
        if (mEditor != null) {
            mEditor.putString(Key.THRESSPERSON, thressperson);
            mEditor.commit();
        }
    }

    public String getThressPerson() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.THRESSPERSON, "");
        }
        return "";
    }
    /**地址
     * @param address :
     */
    public void saveAddress(String address) {
        if (mEditor != null) {
            mEditor.putString(Key.ADDRESS, address);
            mEditor.commit();
        }
    }

    public String getAddress() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.ADDRESS, "");
        }
        return "";
    }
    /**地址详情
     * @param detail :
     */
    public void saveDetail(String detail) {
        if (mEditor != null) {
            mEditor.putString(Key.DETAIL, detail);
            mEditor.commit();
        }
    }

    public String getDetail() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.DETAIL, "");
        }
        return "";
    }
    /**
     * 移除token
     */
    public void removeToken() {
        if (mEditor != null) {
            mEditor.remove(Key.TOKEN);
            mEditor.remove(Key.USERNAME);
            mEditor.remove(Key.USER_ID_Name);
            mEditor.remove(Key.QQ);
            mEditor.remove(Key.IDENTITY);
            mEditor.remove(Key.WEIXIN);
            mEditor.remove(Key.EMAIL);
            mEditor.remove(Key.USER_ID);
            mEditor.remove(Key.KEY);
            mEditor.commit();
        }
    }
    /**
     * 移除user_id
     */
    public void removeUser_id() {
        if (mEditor != null) {
            mEditor.remove(Key.USER_ID);
            mEditor.commit();
        }
    }
    public interface Key {
        String USERNAME = "username";
        String PHONE = "phone";
        String USER_ID = "user_id";//保存使用者的id(等級)
        String USER_ID_Name = "user_id_name";//具體等級
        String PASSWORD = "password";//保存的密码
        String USER_IS_LOGIN = "user_is_login";//判断是否登录
        String TOKEN = "token";//保存的本地token
        String EXPIRES = "expires";//token的时间
        String KEY = "key";
        String SHOP_ID = "shopid";
        String CLASS_NAME = "CLASS_NAME";//分类名称
        String ACCOUNTNAME = "accountname";//开户人
        String CARDNUM = "cardnum";//开户卡号
        String KAIHUHANG = "kaihuhang";//开户支行
        String BANKID = "bankid";//支行id
        String EMAIL = "email";//邮箱地址
        String WEIXIN = "weixin";//微信
        String QQ = "qq";//QQ
        String IDENTITY = "identity";//身份证号码
        String SEX = "sex";//性别
        String ORDER_ID = "order_id";//订单id
        String NEWS_ID = "news_id";//新闻id
        String LOCATION = "Location";//地理位置
        String PROVINCE = "province";//省
        String CITY = "city";//市
        String TOWN = "town";//县
        String ADDRESS = "address";//地址
        String DETAIL = "detail";//地址详情
        String ONEPERSON = "oneperson";//
        String TWOPERSON = "twoperson";//
        String THRESSPERSON = "thressperson";//

    }
}
