package com.xxjr.xxjr.activity;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xxjr.xxjr.R;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;


public class OrderResultFailActivity extends SlidBackActivity implements View.OnClickListener{

    private TextView mTvReason;
    private LinearLayout mLlCheckOrder;
    private LinearLayout mLlConnection;
    private String message;
    private String SeviceTel = "4001892600";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_result_fail);
        getIntentDatas();
        SetTitleBar.setTitleText(OrderResultFailActivity.this, "交单结果");
        initViews();
        setListener();
        ViewSetDatas();
    }

    private void initViews() {
        mTvReason = (TextView) findViewById(R.id.OrderResultFail_tv_resson);
        mLlCheckOrder = (LinearLayout) findViewById(R.id.OrderResulFail_ll_CheckMyOrder);
        mLlConnection = (LinearLayout) findViewById(R.id.OrderResulFail_ll_connection);
    }

    private void setListener() {
        mLlCheckOrder.setOnClickListener(this);
        mLlConnection.setOnClickListener(this);
    }
    private void ViewSetDatas() {
        mTvReason.setText(message);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.OrderResulFail_ll_CheckMyOrder:
                intent.setClass(getApplicationContext(),CustOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.OrderResulFail_ll_connection:
                setDialogConnection();
                break;
        }
    }

    private void setDialogConnection() {
        AlertDialog.Builder builder = new AlertDialog.Builder(OrderResultFailActivity.this);
        Dialog dialog = builder.create();
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_check_result_contace, null);
        setDialogListener(view,dialog);//监听按钮

        dialog.show();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;//设置对话框到底部
        window.setAttributes(wlp);
        window.setContentView(view);
    }

    //联系客服对话框
    private void setDialogListener(View view, final Dialog dialog) {
        TextView mTvTel = (TextView) view.findViewById(R.id.dialCheckResult_tv_tel);
        TextView mTvCancel = (TextView) view.findViewById(R.id.dialCheckResult_tv_cancel);
        mTvTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + SeviceTel));//拨打客服电话
                startActivity(intent2);
                dialog.dismiss();
            }
        });
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }



    public void getIntentDatas() {
        Intent intent = getIntent();
        message = intent.getStringExtra("message");
    }
}
