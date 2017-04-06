package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.xxjr.xxjr.R;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;

public class ContactDetailActivity extends SlidBackActivity {

    private TextView mTvUserName,mTvCardId,mTvTel,mTvApplyAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        SetTitleBar.setTitleText(ContactDetailActivity.this, "客户详情");
        initView();
        getIntentDatas();
    }

    private void initView() {
        mTvUserName = (TextView) findViewById(R.id.ContactDetail_tv_userNamme);
        mTvCardId = (TextView) findViewById(R.id.ContactDetail_tv_userID);
        mTvTel = (TextView) findViewById(R.id.ContactDetail_tv_tel);
        mTvApplyAmount = (TextView) findViewById(R.id.ContactDetail_tv_ApplyAmount);

    }

    private void getIntentDatas(){
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String cardNo = intent.getStringExtra("cardNo");
        String tel = intent.getStringExtra("Tel");
        String applyAmount = intent.getStringExtra("ApplyAmount");
        setUserInfo(cardNo,name,tel,applyAmount);
    }

    private void setUserInfo(String cardNo, String name, String tel, String applyAmount) {
        if (name!=null){
            mTvUserName.setText(name);
        }
       if (cardNo!=null){
           mTvCardId.setText(cardNo);
       }
       if (tel!=null){
           mTvTel.setText(tel);
       }
       if (applyAmount!=null){
           mTvApplyAmount.setText(applyAmount+"万");
       }

    }
}
