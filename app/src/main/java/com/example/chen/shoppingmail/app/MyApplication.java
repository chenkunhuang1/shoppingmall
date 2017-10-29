package com.example.chen.shoppingmail.app;

import android.app.Application;
import android.content.Context;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by chen on 2017/10/16.
 * 作用：
 */

public class MyApplication extends Application {
    public static Context getmContext() {
        return mContext;
    }

    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        //初始化OkhttpClient
        initOkhttpClient();
    }

    private void initOkhttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }
}
