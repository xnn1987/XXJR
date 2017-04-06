package com.xxjr.xxjr.activity;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xxjr.xxjr.R;
import com.xxjr.xxjr.adapter.CustOrderVpAdapter;
import com.xxjr.xxjr.adapter.WDBestSellersAdapter;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.SystemBarTintManager;
import com.xxjr.xxjr.utils.common.CommomAcitivity;

public class WDBestSellersActivity extends CommomAcitivity {

    private ViewPager mVpSeller;
    private TabLayout mTlSeller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wdbest_sellers);
        SetTitleBar.setTitleText(WDBestSellersActivity.this, "微店排行");
        initViews();
        setAdapter();
    }

    private void setAdapter() {
        WDBestSellersAdapter mVpAdapter = new WDBestSellersAdapter(getSupportFragmentManager());
        mVpSeller.setAdapter(mVpAdapter);
        mVpSeller.setOffscreenPageLimit(2);

        mTlSeller.setTabMode(TabLayout.MODE_FIXED);
        mTlSeller.setupWithViewPager(mVpSeller);
    }


    private void initViews() {
        mTlSeller = (TabLayout) findViewById(R.id.bestseller_TabLayout);
        mVpSeller = (ViewPager) findViewById(R.id.bestseller_ViewPager);
    }
}
