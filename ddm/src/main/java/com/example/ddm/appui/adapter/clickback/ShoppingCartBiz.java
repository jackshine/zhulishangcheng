package com.example.ddm.appui.adapter.clickback;
import java.util.HashMap;
import java.util.List;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ddm.R;
import com.example.ddm.appui.bean.SendCodeBean;
import com.example.ddm.appui.bean.ShoppingCar;
import com.example.ddm.appui.constant.IAppKey;
import com.example.ddm.appui.constant.Url;
import com.example.ddm.appui.utils.DecimalUtil;
import com.example.ddm.appui.utils.JsonUtils;
import com.example.ddm.appui.utils.LogUtils;
import com.example.ddm.appui.utils.StringUtils;
import com.example.ddm.manager.PreferenceManager;
import com.example.ddm.okthhp.HttpHelper;
import com.example.ddm.okthhp.response.RawResponseHandler;

public class ShoppingCartBiz {

    /**
     * 选择全部，点下全部按钮，改变所有商品选中状态
     */
    public static boolean selectAll(List<ShoppingCar.DatasBean> list, boolean isSelectAll, ImageView ivCheck) {
        isSelectAll = !isSelectAll;
        ShoppingCartBiz.checkItem(isSelectAll, ivCheck);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setShopSelect(isSelectAll);
            for (int j = 0; j < list.get(i).getGoods().size(); j++) {
                list.get(i).getGoods().get(j).setChildSelected(isSelectAll);
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
                hashMap.put("id",list.get(i).getGoods().get(j).getShoppingCartId());
                hashMap.put("option", String.valueOf(isSelectAll));
                HttpHelper.getInstance().post(Url.optionCart, hashMap, new RawResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response) {
                        SendCodeBean bean = JsonUtils.parse(response, SendCodeBean.class);
                        LogUtils.d("结果3"+response);
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {

                    }
                });
            }
        }
        return isSelectAll;
    }
    /**
     * 族内的所有组，是否都被选中，即全选
     *
     * @param list
     * @return
     */
    private static boolean isSelectAllGroup(List<ShoppingCar.DatasBean> list) {
        for (int i = 0; i < list.size(); i++) {
            boolean isSelectGroup = list.get(i).isShopSelect();
            if (!isSelectGroup) {
                return false;
            }
        }
        return true;
    }
    /**
     * 组内所有子选项是否全部被选中
     *
     * @param list
     * @return
     */
    private static boolean isSelectAllChild(List<ShoppingCar.DatasBean.GoodsBean> list) {
        for (int i = 0; i < list.size(); i++) {

            boolean isSelectGroup = list.get(i).isChildSelected();
            if (list.get(i).getShoppingCartStateValue()==4) {
                return false;
            }
        }
        return true;
    }
    /**
     * 单选一个，需要判断整个组的标志，整个族的标志，是否被全选，取消，则
     * 除了选择全部和选择单个可以单独设置背景色，其他都是通过改变值，然后notify；
     *
     * @param list
     * @param groudPosition
     * @param childPosition
     * @return 是否选择全部
     */
    public static boolean selectOne(List<ShoppingCar.DatasBean> list, int groudPosition, int childPosition) {
        boolean isSelectAll;
        if ((list.get(groudPosition).getGoods().get(childPosition).getShoppingCartStateValue()) == 1) {
            boolean isSelectedOne = true;
            list.get(groudPosition).getGoods().get(childPosition).setChildSelected(false);//单个图标的处理
            boolean isSelectCurrentGroup = isSelectAllChild(list.get(groudPosition).getGoods());
            list.get(groudPosition).setShopSelect(isSelectCurrentGroup);//组图标的处理
            isSelectAll = isSelectAllGroup(list);
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
            hashMap.put("id",list.get(groudPosition).getGoods().get(childPosition).getShoppingCartId());
            hashMap.put("option", String.valueOf(isSelectedOne));
            HttpHelper.getInstance().post(Url.optionCart, hashMap, new RawResponseHandler() {
                @Override
                public void onSuccess(int statusCode, String response) {
                    SendCodeBean bean = JsonUtils.parse(response, SendCodeBean.class);
                    LogUtils.d("结果2"+response);
                }
                @Override
                public void onFailure(int statusCode, String error_msg) {
                }
            });
        } else {
          boolean isSelectedOne = false;
            list.get(groudPosition).getGoods().get(childPosition).setChildSelected(true);//单个图标的处理
            boolean isSelectCurrentGroup = isSelectAllChild(list.get(groudPosition).getGoods());
            list.get(groudPosition).setShopSelect(isSelectCurrentGroup);//组图标的处理
            isSelectAll = isSelectAllGroup(list);
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
            hashMap.put("id",list.get(groudPosition).getGoods().get(childPosition).getShoppingCartId());
            hashMap.put("option", String.valueOf(isSelectedOne));
            HttpHelper.getInstance().post(Url.optionCart, hashMap, new RawResponseHandler() {
                @Override
                public void onSuccess(int statusCode, String response) {
                    SendCodeBean bean = JsonUtils.parse(response, SendCodeBean.class);
                    LogUtils.d("结果2"+response);
                }
                @Override
                public void onFailure(int statusCode, String error_msg) {

                }
            });
        }
//        boolean isSelectedOne = !(list.get(groudPosition).getGoods().get(childPosition).getShoppingCartStateValue());

        return isSelectAll;
    }

    public static boolean selectGroup(List<ShoppingCar.DatasBean> list, int groudPosition) {
        boolean isSelectAll;
        boolean isSelected = !(list.get(groudPosition).isShopSelect());
        list.get(groudPosition).setShopSelect(isSelected);
        for (int i = 0; i < list.get(groudPosition).getGoods().size(); i++) {
            list.get(groudPosition).getGoods().get(i).setChildSelected(isSelected);
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
            hashMap.put("id",list.get(groudPosition).getGoods().get(i).getShoppingCartId());
            hashMap.put("option", String.valueOf(isSelected));
            HttpHelper.getInstance().post(Url.optionCart, hashMap, new RawResponseHandler() {
                @Override
                public void onSuccess(int statusCode, String response) {
                    SendCodeBean bean = JsonUtils.parse(response, SendCodeBean.class);
                    LogUtils.d("结果"+response);
                }

                @Override
                public void onFailure(int statusCode, String error_msg) {

                }
            });
        }
        isSelectAll = isSelectAllGroup(list);
        return isSelectAll;
    }
//    mListGoods.get(groupPosition).getGoods().get(childPosition).getShoppingCartId();
    private void isCheck(List<ShoppingCar.DatasBean> list, int groudPosition, int childPosition){


    }
    /**
     * 勾与不勾选中选项
     *
     * @param isSelect 原先状态
     * @param ivCheck
     * @return 是否勾上，之后状态
     */
    public static void checkItem(boolean isSelect, ImageView ivCheck) {
        if (isSelect) {
            ivCheck.setImageResource(R.drawable.selected);
        } else {
            ivCheck.setImageResource(R.drawable.select);
        }

    }

    /**=====================上面是界面改动部分，下面是数据变化部分=========================*/

    /**
     * 获取结算信息，肯定需要获取总价和数量，但是数据结构改变了，这里处理也要变；
     *
     * @return 0=选中的商品数量；1=选中的商品总价
     */
    public static String[] getShoppingCount(List<ShoppingCar.DatasBean> listGoods) {
        String[] infos = new String[2];
        String selectedCount = "0";
        String selectedMoney = "0";
        for (int i = 0; i < listGoods.size(); i++) {
            for (int j = 0; j < listGoods.get(i).getGoods().size(); j++) {
                boolean isSelectd = listGoods.get(i).getGoods().get(j).isChildSelected();
                if (listGoods.get(i).getGoods().get(j).getShoppingCartStateValue()==1) {
                    String price = listGoods.get(i).getGoods().get(j).getMemberPrice();
                    int num = listGoods.get(i).getGoods().get(j).getCount();
                    String countMoney = DecimalUtil.multiply(price, String.valueOf(num));
                    selectedMoney = DecimalUtil.add(selectedMoney, countMoney);
                    selectedCount = DecimalUtil.add(selectedCount, "1");
                }
            }
        }
        infos[0] = selectedCount;
        infos[1] = selectedMoney;
        return infos;
    }


    public static boolean hasSelectedGoods(List<ShoppingCar.DatasBean> listGoods) {
        String count = getShoppingCount(listGoods)[0];
        if ("0".equals(count)) {
            return false;
        }
        return true;
    }
    /**
     * 添加某商品的数量到数据库（非通用部分，都有这个动作，但是到底存什么，未可知）
     *
     * @param productID 此商品的规格ID
     * @param num       此商品的数量
     */
    public static void addGoodToCart(String productID, String num) {
    }
    /**
     * 删除某个商品,即删除其ProductID
     *
     * @param productID 规格ID
     */
    public static void delGood(String productID) {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        hashMap.put("id", productID);
        HttpHelper.getInstance().post(Url.deleteUserShoppingCart, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                SendCodeBean bean = JsonUtils.parse(response, SendCodeBean.class);
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
            }
        });
//        ShoppingCartDao.getInstance().deleteShoppingInfo(productID);
    }

    /** 删除全部商品 */
    public static void delAllGoods() {
//        ShoppingCartDao.getInstance().delAllGoods();
    }

    /** 增减数量，操作通用，数据不通用 */
    public static void addOrReduceGoodsNum(boolean isPlus, ShoppingCar.DatasBean.GoodsBean goods, TextView tvNum) {
        int currentNum = goods.getCount();
        int num = 1;
        if (isPlus) {
            num = currentNum+ 1;
        } else {
            int i = currentNum;
            if (i > 1) {
                num = i - 1;
            } else {
                num = 1;
            }
        }
        String productID = goods.getShoppingCartId();
        tvNum.setText(""+num);
        goods.setCount(num);
        updateGoodsNumber(productID, num);
    }

    /**
     * 更新购物车的单个商品数量
     *
     * @param productID
     * @param num
     */
    public static void updateGoodsNumber(String productID, int num) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(IAppKey.TOKEN, PreferenceManager.instance().getToken());
        hashMap.put("modifydata", productID+":"+num);
        HttpHelper.getInstance().post(Url.modifyUserShoppingCart, hashMap, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                SendCodeBean bean = JsonUtils.parse(response, SendCodeBean.class);
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {

            }
        });
    }
    /**
     * 查询购物车商品总数量
     * <p/>
     * 统一使用该接口，而就行是通过何种方式获取数据，数据库、SP、文件、网络，都可以
     *
     * @return
     */
//    public static int getGoodsCount() {
//        return ;
//    }

    /**
     * 获取所有商品ID，用于向服务器请求数据（非通用部分）
     *
     * @return
     */
//    public static List<String> getAllProductID() {
////        return ShoppingCartDao.getInstance().getProductList();
//    }

    /** 由于这次服务端没有保存商品数量，需要此步骤来处理数量（非通用部分） */
    public static void updateShopList(List<ShoppingCar.DatasBean> list) {
        if (list == null) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            ShoppingCar.DatasBean scb = list.get(i);
            if (scb == null) {
                continue;
            }
            List<ShoppingCar.DatasBean.GoodsBean> list2 = scb.getGoods();
            if (list2 == null) {
                continue;
            }
            for (int j = 0; j < list2.size(); j++) {
                ShoppingCar.DatasBean.GoodsBean goods = list2.get(j);
                if (goods == null) {
                    continue;
                }
//                String productID = goods.getShoppingCartId();
//                String num = ShoppingCartDao.getInstance().getNumByProductID(productID);
//                list.get(i).getGoods().get(j).setCount(num);
            }
        }
    }

}
