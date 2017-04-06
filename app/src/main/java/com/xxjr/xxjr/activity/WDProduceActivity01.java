package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.xxjr.xxjr.R;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;

public class WDProduceActivity01 extends SlidBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wdproduce01);
        SetTitleBar.setTitleText(WDProduceActivity01.this, "微店产品");
        findViewById(R.id.wdchangpiin_tv_tianjia).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),WD_AddCreditProduceActivity.class));
                finish();
            }
        });
    }

}
