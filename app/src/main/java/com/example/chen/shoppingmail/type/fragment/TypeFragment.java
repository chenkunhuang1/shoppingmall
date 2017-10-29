package com.example.chen.shoppingmail.type.fragment;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.chen.shoppingmail.base.BaseFragment;

/**
 * Created by chen on 2017/10/16.
 * 作用：
 */

public class TypeFragment extends BaseFragment {
    private TextView textview;
    @Override
    public View initView() {
        textview = new TextView(mContext);
        textview.setGravity(Gravity.CENTER);
        textview.setTextSize(20);
        return textview;
    }

    @Override
    public void initData() {
        textview.setText("分类界面");
        super.initData();
    }
}
