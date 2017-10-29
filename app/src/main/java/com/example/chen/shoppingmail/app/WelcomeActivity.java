package com.example.chen.shoppingmail.app;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.chen.shoppingmail.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //两秒后进入主界面
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //启动主界面
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                //结束当前页面
                finish();

            }
        },2000);
    }
}
