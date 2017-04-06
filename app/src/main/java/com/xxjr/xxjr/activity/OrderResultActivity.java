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

import java.text.SimpleDateFormat;

public class OrderResultActivity extends SlidBackActivity implements View.OnClickListener {

    private LinearLayout mLlCheckMyOrder, mLlConnection;
    private Object intentDatas;
    private int commitType;
    private String produceType;
    private TextView mTvOrderType,mTvProduceTye,mTvOrderTime;
    private String strOrderType;
    private String titleName;
    private String SeviceTel = "4001892600";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_result);
        //TODO  判断是什么类型
        getIntentDatas();
        SetTitleBar.setTitleText(OrderResultActivity.this, "交单结果");
        initViews();
        setViewsDatas();
        setListener();
    }


    private void initViews() {
        mLlCheckMyOrder = (LinearLayout) findViewById(R.id.OrderResul_ll_CheckMyOrder);
        mLlConnection = (LinearLayout) findViewById(R.id.OrderResul_ll_connection);

        mTvOrderType = (TextView) findViewById(R.id.OrderResult_tv_type);
        mTvProduceTye = (TextView) findViewById(R.id.OrderResult_tv_produceType);
        mTvOrderTime = (TextView) findViewById(R.id.OrderResult_tv_time);
    }

    private void setViewsDatas() {
        if (commitType == 1){
            strOrderType = titleName+"·简单";
        }else if (commitType ==2){
            strOrderType = titleName+"·完整";
        }
        mTvOrderType.setText(strOrderType);
        mTvProduceTye.setText(produceType);

        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String data = format.format(new java.util.Date());
        mTvOrderTime.setText(data);


    }

    private void setListener() {
        mLlCheckMyOrder.setOnClickListener(this);
        mLlConnection.setOnClickListener(this);
    }

    private void setDialogConnection() {
        AlertDialog.Builder builder = new AlertDialog.Builder(OrderResultActivity.this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.OrderResul_ll_CheckMyOrder:
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),CustOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.OrderResul_ll_connection:
                setDialogConnection();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

    public void getIntentDatas() {
        Intent intent = getIntent();
        commitType = intent.getIntExtra("commitType", 0);
        produceType = intent.getStringExtra("produceType");
        titleName = intent.getStringExtra("titleName");
    }


}
