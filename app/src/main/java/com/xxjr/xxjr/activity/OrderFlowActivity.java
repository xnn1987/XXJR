package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xxjr.xxjr.R;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;

import java.util.ArrayList;
import java.util.List;

public class OrderFlowActivity extends SlidBackActivity {

    private int orgStatus;
    private View view01,view02,view03,view04,view05;
    private ImageView mIvOrgStatus01,mIvOrgStatus02,mIvOrgStatus03,mIvOrgStatus04,mIvOrgStatus05,mIvOrgStatus06;
    private List<View> viewList = new ArrayList<>();
    private List<ImageView> imageViewList = new ArrayList<>();
    private int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_flow);
        SetTitleBar.setTitleText(OrderFlowActivity.this, "订单流程");
        getIntentDatas();
        intiView();

        changeState();
    }


    private void intiView() {
        view01 = findViewById(R.id.OrderFlow_view_orgStatus01);
        view02 = findViewById(R.id.OrderFlow_view_orgStatus02);
        view03 = findViewById(R.id.OrderFlow_view_orgStatus03);
        view04 = findViewById(R.id.OrderFlow_view_orgStatus04);
        view05 = findViewById(R.id.OrderFlow_view_orgStatus05);
        viewList.add(view01);
        viewList.add(view02);
        viewList.add(view03);
        viewList.add(view04);
        viewList.add(view05);
        mIvOrgStatus01 = (ImageView) findViewById(R.id.OrderFlow_iv_orgStatus01);
        mIvOrgStatus02 = (ImageView) findViewById(R.id.OrderFlow_iv_orgStatus02);
        mIvOrgStatus03 = (ImageView) findViewById(R.id.OrderFlow_iv_orgStatus03);
        mIvOrgStatus04 = (ImageView) findViewById(R.id.OrderFlow_iv_orgStatus04);
        mIvOrgStatus05 = (ImageView) findViewById(R.id.OrderFlow_iv_orgStatus05);
        mIvOrgStatus06 = (ImageView) findViewById(R.id.OrderFlow_iv_orgStatus06);
        imageViewList.add(mIvOrgStatus01);
        imageViewList.add(mIvOrgStatus02);
        imageViewList.add(mIvOrgStatus03);
        imageViewList.add(mIvOrgStatus04);
        imageViewList.add(mIvOrgStatus05);
        imageViewList.add(mIvOrgStatus06);


    }
    public void getIntentDatas() {
        Intent intent = getIntent();
        orgStatus = intent.getIntExtra("orgStatus", 0);

    }


    private void changeState() {
        for (int i=0;i<=orgStatus;i++){
            if (i<imageViewList.size()) {
                ImageView imageView = imageViewList.get(i);
                imageView.setImageResource(R.mipmap.yuandian);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.topMargin = 0;
                params.bottomMargin = 0;
                imageView.setLayoutParams(params);
                if (orgStatus >= 1 && i < orgStatus - 1) {
                    viewList.get(i).setBackgroundColor(Color.argb(252, 252, 105, 35));
                }
            }

        }
    }




}
