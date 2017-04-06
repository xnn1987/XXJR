package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xxjr.xxjr.R;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;


public class ConcatMyNewCustActivity extends SlidBackActivity implements View.OnClickListener{
    private LinearLayout concatNewAddCust,mLlTongxunlu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concat_my_new_cust);
        SetTitleBar.setTitleText(ConcatMyNewCustActivity.this, "我的新客户");
        initView();
        setListener();
    }
    private void initView(){
        concatNewAddCust = (LinearLayout)this.findViewById(R.id.my_new_cust_add);
        mLlTongxunlu = (LinearLayout)this.findViewById(R.id.ConcatMyCust_ll_addContract);
    }

    private void setListener(){
        concatNewAddCust.setOnClickListener(this);
        mLlTongxunlu.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.my_new_cust_add:
                intent.setClass(this.getApplicationContext(),MyCustAlterActivity.class);
                this.startActivity(intent);
                break;
            case R.id.ConcatMyCust_ll_addContract:
                intent.setClass(this.getApplicationContext(),TongXunluActivity.class);
                this.startActivity(intent);
                break;
        }
    }
}
