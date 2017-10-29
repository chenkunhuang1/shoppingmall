package com.example.chen.shoppingmail.shoppingcart.view;


import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chen.shoppingmail.R;


/**
 * Created by chen on 2017/10/28.
 * 作用：自定义增加删除按钮
 */

public class AddSubView extends LinearLayout implements View.OnClickListener {
    private final Context mContext;
    private ImageView iv_sub;
    private ImageView iv_add;
    private TextView tv_value;
    private int value = 1;
    private int minValue = 1;
    private int maxValue = 5;

    public AddSubView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        //把布局文件实例化，并且加载到AddSubView类中
        View.inflate(context, R.layout.add_sub_view, this);
        iv_sub = (ImageView) findViewById(R.id.iv_sub);
        tv_value = (TextView) findViewById(R.id.tv_value);
        iv_add = (ImageView) findViewById(R.id.iv_add);

        int value = getValue();
        setValue(value);
        //设置点击事件
        iv_sub.setOnClickListener(this);
        iv_add.setOnClickListener(this);

    }

    public int getValue() {
        String str = tv_value.getText().toString().trim();
        if(!TextUtils.isEmpty(str)){
            value = Integer.parseInt(str);
        }
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        tv_value.setText(value+"");
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_sub:
                subNumber();
                break;
            case R.id.iv_add:
                addNumber();
                break;

        }
        //Toast.makeText(mContext, "当前商品数=="+value, Toast.LENGTH_SHORT).show();
    }

    private void addNumber() {
        if (value < maxValue){
            value ++;
        }
        setValue(value);
        if (onNumberChangeListener != null){
            onNumberChangeListener.OnNumberChange(value);
        }
    }

    private void subNumber() {
        if(minValue < value){
            value --;
        }
        setValue(value);
        if (onNumberChangeListener != null){
            onNumberChangeListener.OnNumberChange(value);
        }
    }

    /**
     * 当数量发生变化时调用
     */
    public interface OnNumberChangeListener{
        void OnNumberChange(int value);
    }

    /**
     * 设置数量变化的监听
     * @param onNumberChangeListener
     */
    public void setOnNumberChangeListener(OnNumberChangeListener onNumberChangeListener) {
        this.onNumberChangeListener = onNumberChangeListener;
    }

    private OnNumberChangeListener onNumberChangeListener;
}
