package com.example.chen.shoppingmail.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by chen on 2017/9/20.
 * 作用：Toast的优化，解决一直点击一个按钮，toast会一直显示
 */

public class ToastHelper {
    public static Toast mToast = null;
    public static void showToast(Context context, String text, int duration){
        if (mToast == null){
            mToast = Toast.makeText(context,text,duration);
        }else {
            mToast.setText(text);
            mToast.setDuration(duration);
        }
        mToast.show();
    }
}
