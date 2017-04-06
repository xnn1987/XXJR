package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.xxjr.xxjr.R;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;

public class WDActivity01 extends SlidBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wei_dian01);
        SetTitleBar.setTitleText(this, "我的微店");
        findViewById(R.id.weidian01_btn_toWD02).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WDCardActivity.class);
                intent.putExtra("isAdd", true);
                intent.putExtra(ConstantUtils.WDCARD_FROM, ConstantUtils.WDCARD_FROM_ACTIVITY);
                startActivity(intent);
                finish();
            }
        });
    }
}
