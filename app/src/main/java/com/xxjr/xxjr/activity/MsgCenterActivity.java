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


public class MsgCenterActivity extends SlidBackActivity implements View.OnClickListener{

    private LinearLayout mLlKefu,mLlOrder,mLlXiTong;
    private TextView mTvKefu,mTvOrder,mTvXiTong;
    private ImageView mIvKefu,mIvOrder,mIvXitong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_center);
        SetTitleBar.setTitleText(MsgCenterActivity.this, "消息中心");
        initViews();
        setListener();
    }

    private void setInitViewDatas() {
        if (MainActivity.chatStatus!=0) {
            mTvKefu.setText(R.string.new_message);
            mIvKefu.setImageResource(R.mipmap.kefutongzhiyou);
        }else {
            mTvKefu.setText(R.string.no_new_message);
            mIvKefu.setImageResource(R.mipmap.kefutongzhi);
        }
        if (MainActivity.orderStatus!=0) {
            mTvOrder.setText(R.string.new_message);
            mIvOrder.setImageResource(R.mipmap.jiaodantongzhiyou);
        }else {
            mTvOrder.setText(R.string.no_new_message);
            mIvOrder.setImageResource(R.mipmap.jiaodantongzhi);
        }
        if (MainActivity.messageStatus!=0) {
            mTvXiTong.setText(R.string.new_message);
            mIvXitong.setImageResource(R.mipmap.xitongtongzhiyou);
        }else {
            mTvXiTong.setText(R.string.no_new_message);
            mIvXitong.setImageResource(R.mipmap.xitongtongzhi);
        }
    }

    private void initViews() {
        mLlKefu = (LinearLayout) findViewById(R.id.MsgNotify_ll_kefu);
        mLlOrder = (LinearLayout) findViewById(R.id.MsgNotify_ll_jiaodan);
        mLlXiTong = (LinearLayout) findViewById(R.id.MsgNotify_llxitong);
        mTvKefu = (TextView) findViewById(R.id.MsgNotify_tv_kefu);
        mTvOrder = (TextView) findViewById(R.id.MsgNotify_tv_jiaodan);
        mTvXiTong = (TextView) findViewById(R.id.MsgNotify_tv_xitong);
        mIvKefu = (ImageView) findViewById(R.id.MsgNotify_iv_kefu);
        mIvOrder = (ImageView) findViewById(R.id.MsgNotify_iv_jiaodan);
        mIvXitong = (ImageView) findViewById(R.id.MsgNotify_iv_xitong);
    }

    private void setListener() {
        mLlKefu.setOnClickListener(this);
        mLlOrder.setOnClickListener(this);
        mLlXiTong.setOnClickListener(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        setInitViewDatas();
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.MsgNotify_ll_kefu://客服
                intent.setClass(MsgCenterActivity.this,KefuChatActivity.class);
                startActivity(intent);
                break;
            case R.id.MsgNotify_ll_jiaodan://交单
                intent.setClass(MsgCenterActivity.this,OrderNotifyActivity.class);
                startActivity(intent);
                break;
            case R.id.MsgNotify_llxitong://系统
                intent.setClass(MsgCenterActivity.this,XitongNotifyActivity.class);
                startActivity(intent);
                break;
        }
    }
}
