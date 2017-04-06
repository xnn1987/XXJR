package com.xxjr.xxjr.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Window;

import com.xxjr.xxjr.R;
import com.xxjr.xxjr.adapter.CustOrderVpAdapter;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.CommomAcitivity;


public class CustOrderActivity extends CommomAcitivity {

    private ViewPager mVpOrder;
    private TabLayout mTlOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        SetTitleBar.setTitleText(CustOrderActivity.this, "我的交单");
        initViews();

    }
    private void initViews() {
        mVpOrder = (ViewPager) findViewById(R.id.CustOrder_ViewPager);
        mTlOrder = (TabLayout) findViewById(R.id.CustOrder_TabLayout);
        FragmentManager fragmentManager = getFragmentManager();
        CustOrderVpAdapter mVpAdapter = new CustOrderVpAdapter(getSupportFragmentManager());
        mVpOrder.setAdapter(mVpAdapter);
        mVpOrder.setOffscreenPageLimit(5);

        mTlOrder.setTabMode(TabLayout.MODE_FIXED);
        mTlOrder.setupWithViewPager(mVpOrder);

    }

}
