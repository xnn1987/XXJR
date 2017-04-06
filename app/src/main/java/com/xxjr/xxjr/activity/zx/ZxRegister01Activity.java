package com.xxjr.xxjr.activity.zx;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.xxjr.xxjr.R;

public class ZxRegister01Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zx_register01);
    }

    public void onClickNext(View v){
        Intent intent = new Intent(this,ZxRegister02Activity.class);
        startActivity(intent);
    }
}
