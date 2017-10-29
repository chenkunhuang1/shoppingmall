package com.example.chen.shoppingmail.home.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.chen.shoppingmail.R;
import com.example.chen.shoppingmail.base.BaseFragment;
import com.example.chen.shoppingmail.home.adapter.HomeFragmentAdapter;
import com.example.chen.shoppingmail.home.bean.ResultBeanData;
import com.example.chen.shoppingmail.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by chen on 2017/10/16.
 * 作用：
 */

public class HomeFragment extends BaseFragment {
    private static final String TAG =
            HomeFragment.class.getSimpleName();
    private RecyclerView rvHome;
    private ImageView ib_top;
    private TextView tv_search_home;
    private TextView tv_message_home;
    private ResultBeanData.ResultBean result;
    private HomeFragmentAdapter homefragmentadapter;

    @Override
    public View initView() {
        Log.e(TAG, " 主页视图被初始化了");
        View view = View.inflate(mContext, R.layout.fragment_home,
                null);
        rvHome = (RecyclerView) view.findViewById(R.id.rv_home);
        ib_top = (ImageView) view.findViewById(R.id.ib_top);
        tv_search_home = (TextView)
                view.findViewById(R.id.tv_search_home);
        tv_message_home = (TextView)
                view.findViewById(R.id.tv_message_home);
// 设置点击事件
        initListener();
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        //联网请求网页的数据
        getDataFromNet();
    }

    private void getDataFromNet() {
        String url = Constants.HOME_URL;
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    /**
                     * 当请求失败是回调
                     * @param call
                     * @param e
                     * @param id
                     */

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e.getMessage());
                    }

                    /**
                     * 当联网成功时回调
                     * @param response
                     * @param id
                     */

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "onResponse: " + response);
                        //解析数据
                        process(response);
                    }
                });
    }

    private void process(String response) {
        ResultBeanData resultBeanData = JSON.parseObject(response, ResultBeanData.class);
        result = resultBeanData.getResult();
        if (response != null) {
            //有数据
            //设置设配器
            homefragmentadapter = new HomeFragmentAdapter(mContext, result);
            rvHome.setAdapter(homefragmentadapter);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position <= 3) {
                        //隐藏
                        ib_top.setVisibility(View.GONE);
                    } else {
                        //显示
                        ib_top.setVisibility(View.VISIBLE);
                    }
                    return 1;
                }
            });
            //设置布局管理器
            rvHome.setLayoutManager(gridLayoutManager);
        } else {
            //没有数据
        }
        Log.e(TAG, "process: " + result.getHot_info().get(0).getName());
    }

    private void initListener() {
// 置顶的监听
        ib_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// 回到顶部
                rvHome.scrollToPosition(0);
            }
        });
// 搜素的监听
        tv_search_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, " 搜索",
                        Toast.LENGTH_SHORT).show();
            }
        });
// 消息的监听
        tv_message_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, " 进入消息中心",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
