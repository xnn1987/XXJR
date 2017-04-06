package com.xxjr.xxjr.activity.zx;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.xxjr.xxjr.R;

public class ZxLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zx_login);
    }

    // 首次开通央行征信
    public void onClickZxFirst(View v){
        Intent intent = new Intent(getApplicationContext(),ZxRegister01Activity.class);
        startActivity(intent);
        
    }
    // 已有帐号
    public void onClickZxLogin(View v){
        Intent intent = new Intent(getApplicationContext(),ZxLogin02Activity.class);
        startActivity(intent);
    }
}
