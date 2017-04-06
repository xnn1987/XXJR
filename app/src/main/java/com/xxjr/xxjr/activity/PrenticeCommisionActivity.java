package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xxjr.xxjr.R;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;


public class PrenticeCommisionActivity extends SlidBackActivity {

    private TextView mTvPreniceCommision;
    private ListView mLvPrenticeCommision;
    private ImageView mIvCheckIcon;
    private String prenticeCommissionCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prentice_commision);
        SetTitleBar.setTitleText(PrenticeCommisionActivity.this, "收徒佣金");
        getIntentDatas();
        initView() ;
        setInitViewDatas();
        mLvPreComSetAdapter();

        judgeCheckCount(mLvPrenticeCommision,mIvCheckIcon);

    }



    private void initView() {
        mTvPreniceCommision = (TextView) findViewById(R.id.PrenticeCommission_tv_Prentive);
        mLvPrenticeCommision = (ListView) findViewById(R.id.PrenticeCommission_lv_Prentive);
        mIvCheckIcon = (ImageView) findViewById(R.id.PrenticeCommission_iv_checkIcon);
    }

    private void setInitViewDatas() {
        mTvPreniceCommision.setText(prenticeCommissionCount);
    }

    private void mLvPreComSetAdapter() {
        PrenticeCommisionAdapter adapter = new PrenticeCommisionAdapter(getApplicationContext());
        mLvPrenticeCommision.setAdapter(adapter);
    }

    /**
     * 判断是否有数据，没有就显示图标
     */
    private void judgeCheckCount(ListView listView,ImageView imageView){
        if (listView.getCount()!=0){
            imageView.setVisibility(View.GONE);
        }else {
            imageView.setVisibility(View.VISIBLE);
        }
    }

    public void getIntentDatas() {
        Intent intent = getIntent();
        prenticeCommissionCount = intent.getStringExtra("prenticeCommissionCount");

    }
}
