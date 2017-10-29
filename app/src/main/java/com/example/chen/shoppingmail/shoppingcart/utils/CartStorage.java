package com.example.chen.shoppingmail.shoppingcart.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import com.example.chen.shoppingmail.app.MyApplication;
import com.example.chen.shoppingmail.home.bean.GoodsBean;
import com.example.chen.shoppingmail.utils.CacheUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 2017/10/23.
 * 作用：
 */

public class CartStorage {
    public static final String JSON_CACHE = "json_cache";
    private static CartStorage instance;
    private final Context mContext;
    private SparseArray<GoodsBean> mSparseArray;

    public CartStorage(Context mContext) {
        this.mContext = mContext;
        //读取之前存储的数据
        mSparseArray = new SparseArray<>(100);
        listToSparseArray();
    }

    /**
     * 将本地的list数据转换车姑获鸟sparseArray数据
     */

    private void listToSparseArray() {
        List<GoodsBean> listbean = getAllData();
        //将list数据转换为sparse数据
        for (int i = 0; i < listbean.size(); i++) {
            GoodsBean goodbean = listbean.get(i);
            mSparseArray.put(Integer.parseInt(goodbean.getProduct_id()),goodbean);
        }
    }

    /**
     * 获取本地所有数据
     * @return
     */

    public List<GoodsBean> getAllData() {
        List<GoodsBean> list = new ArrayList<>();
        //获取本地数据
        String json = CacheUtils.getString(mContext, JSON_CACHE);
        //使用Gson转换为列表
        if (!TextUtils.isEmpty(json)){
                list = new Gson().fromJson(json,new TypeToken<List<GoodsBean>>(){}.getType());
        }

        return list;
    }

    /**
     * 得到购物车的实例
     * @return
     */

    public static CartStorage getInstance(){
        if (instance == null){
            instance = new CartStorage(MyApplication.getmContext());
        }
        return instance;
    }
    public void addDate(GoodsBean goodbean){
        //1.添加到内存sparseArray中
        //如果商品存在则递增
        GoodsBean tempData = mSparseArray.get(Integer.parseInt(goodbean.getProduct_id()));
        if (tempData != null){
            tempData.setNumber(tempData.getNumber()+1);
        }else {
            tempData = goodbean;
            tempData.setNumber(1);
        }
        //同步到内存
        mSparseArray.put(Integer.parseInt(tempData.getProduct_id()),tempData);

        //同步到本地
        savaLocal();
    }


    /**
     * 删除数据
     * @param goodbean
     */
    public void deleteDate(GoodsBean goodbean){
        //从内存中删除
        mSparseArray.delete(Integer.parseInt(goodbean.getProduct_id()));
        //保存到本地
        savaLocal();
    }
    /**
     * 更新数据
     * @param goodbean
     */
    public void updateDate(GoodsBean goodbean){
        //从内存中更新
        mSparseArray.put(Integer.parseInt(goodbean.getProduct_id()),goodbean);
        //保存到本地
        savaLocal();
    }
    /**
     * 保存到本地
     */

    private void savaLocal() {
        //1.将sparsearray转换为list
        List<GoodsBean> listGoodBean = sparsearrayToList();
        //2.使用Gson将列表转换为String类型数据
        String json = new Gson().toJson(listGoodBean);
        //3.将String类型数据保存
        CacheUtils.saveString(mContext,JSON_CACHE,json);
    }

    private List<GoodsBean> sparsearrayToList() {
        List<GoodsBean> list = new ArrayList<>();
        for (int i = 0; i < mSparseArray.size(); i++) {
            GoodsBean goodbean = mSparseArray.valueAt(i);
            list.add(goodbean);

        }
        return list;
    }

}
