package com.example.ddm.appui.greendao.daomanager;

import android.content.Context;

import com.example.ddm.appui.bean.dbbean.RecentCity;
import com.example.ddm.appui.greendao.RecentCityDao;


import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by WangYetong on 2017/7/18.
 * email : wytaper495@qq.com
 * mark:
 */

public class RecentCityOpe {
    /**
     * 添加数据至数据库
     *
     * @param context
     * @param stu
     */
    public static void insertData(Context context, RecentCity stu) {
        DbManager.getDaoSession(context).getRecentCityDao().insert(stu);
    }

    /**
     * 将数据实体通过事务添加至数据库
     *
     * @param context
     * @param list
     */
    public static void insertData(Context context, List<RecentCity> list) {
        if (null == list || list.size() <= 0) {
            return;
        }
        DbManager.getDaoSession(context).getRecentCityDao().insertInTx(list);
    }

    public static void deleteTable(Context context) {
    }

    /**
     * 添加数据至数据库，如果存在，将原来的数据覆盖
     * 内部代码判断了如果存在就update(entity);不存在就insert(entity)；
     *
     * @param context
     * @param student
     */
    public static void saveData(Context context, RecentCity student) {
        DbManager.getDaoSession(context).getRecentCityDao().save(student);
    }

    /**
     * 删除数据至数据库
     *
     * @param context
     * @param student 删除具体内容
     */
    public static void deleteData(Context context, RecentCity student) {
        DbManager.getDaoSession(context).getRecentCityDao().delete(student);
    }

    /**
     * 根据id删除数据至数据库
     *
     * @param context
     * @param id      删除具体内容
     */
    public static void deleteByKeyData(Context context, long id) {
        DbManager.getDaoSession(context).getRecentCityDao().deleteByKey(id);
    }

    /**
     * 删除全部数据
     *
     * @param context
     */
    public static void deleteAllData(Context context) {
        DbManager.getDaoSession(context).getRecentCityDao().deleteAll();
    }


    /**
     * 更新数据库
     *
     * @param context
     * @param student
     */
    public static void updateData(Context context, RecentCity student) {
        DbManager.getDaoSession(context).getRecentCityDao().update(student);
    }

    /**
     * 查询所有数据
     *
     * @param context
     * @return
     */
    public static List<RecentCity> queryAll(Context context) {
        QueryBuilder<RecentCity> builder = DbManager.getDaoSession(context).getRecentCityDao().queryBuilder();
        return builder.build().list();
    }

    /**
     * 根据name，其他的字段类似
     */
    public static List<RecentCity> queryForName(Context context, String name) {
        QueryBuilder<RecentCity> builder = DbManager.getDaoSession(context).getRecentCityDao().queryBuilder();
        /**
         * 返回当前id的数据集合,当然where(这里面可以有多组，做为条件);
         * 这里build.list()；与where(StudentDao.Properties.Id.eq(id)).list()结果是一样的；
         * 在QueryBuilder类中list()方法return build().list();
         *
         */
        // Query<Student> build = builder.where(StudentDao.Properties.Id.eq(id)).build();
        // List<Student> list = build.list();
        return builder.where(RecentCityDao.Properties.CityName.eq(name)).list();

    }
}
