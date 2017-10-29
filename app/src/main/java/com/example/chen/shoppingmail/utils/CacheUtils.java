package com.example.chen.shoppingmail.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by chen on 2017/10/27.
 * 作用：缓存工具类
 */

public class CacheUtils {
    /**
     * 得到String类型的数据
     * @param context
     * @param key
     * @return
     */
    public static String getString(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("shop",Context.MODE_PRIVATE);
        return sp.getString(key,"");
    }

    /**
     * 保存String类型的数据
     */
    public static void  saveString(Context context, String key,String value) {
        SharedPreferences sp = context.getSharedPreferences("shop",Context.MODE_PRIVATE);
        sp.edit().putString(key,value).commit();
    }
}
