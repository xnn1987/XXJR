package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xxjr.xxjr.R;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;


public class CheckNumberActivity extends SlidBackActivity implements View.OnClickListener {

    private TextView mTvCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_number);
        SetTitleBar.setTitleText(CheckNumberActivity.this, "查号");
        initViews();
        setListener();
    }
    private void setListener() {
        mTvCheck.setOnClickListener(this);
    }

    private void initViews() {
        mTvCheck = (TextView) findViewById(R.id.CheckNumber_tv_checknumber);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.CheckNumber_tv_checknumber:
                Intent intent = new Intent(getApplicationContext(),CustomInfoCheckActivity.class);
                startActivity(intent);
        }
    }
}
