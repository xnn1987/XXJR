package com.xxjr.xxjr.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xxjr.xxjr.R;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;

public class CustomInfoCheckActivity extends SlidBackActivity implements View.OnClickListener {

    private EditText mEtName,mEtID;
    private Button mBtnNext;
    private String TAG = "CustomInfoCheckActivity  ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_info_check);
        SetTitleBar.setTitleText(CustomInfoCheckActivity.this, "客户信息查询");
        initViews();
        setListener();
    }

    private void setListener() {
        mBtnNext.setOnClickListener(this);
    }

    private void initViews() {
        mEtName = (EditText) findViewById(R.id.CustomInfoCheck_et_name);
        mEtID = (EditText) findViewById(R.id.CustomInfoCheck_et_ID);
        mBtnNext = (Button) findViewById(R.id.CustomInfoCheck_btn_next);
    }


    private void DiaLogCommit(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CustomInfoCheckActivity.this);
        Dialog dialog = builder.create();
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_custominfo_check,null);
        setDialogBtnListener(view, dialog);

        dialog.show();
        dialog.getWindow().setContentView(view);

    }

    private void setDialogBtnListener(View view, final Dialog dialog){
        Button mBtnKnow = (Button) view.findViewById(R.id.CustominfoCheck_btn_know);
        mBtnKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                Intent intent = new Intent(getApplicationContext(), ChoiceOrganizationActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.CustomInfoCheck_btn_next:
                if (TextUtils.isEmpty(mEtName.getText().toString())){
                    Toast.makeText(getApplicationContext(),"请输入姓名",Toast.LENGTH_SHORT).show();
                }else if (mEtID.getText().toString().trim().length() != 18){
                    Toast.makeText(getApplicationContext(),"请输入有效身份证",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(getApplicationContext(), ChoiceOrgActivity.class);
                    intent.putExtra("realName", mEtName.getText().toString());
                    intent.putExtra("cardNo", mEtID.getText().toString());
                    startActivity(intent);
                }
                break;
        }
    }
}
