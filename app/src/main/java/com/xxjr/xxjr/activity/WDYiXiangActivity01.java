package com.xxjr.xxjr.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.xxjr.xxjr.R;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;

public class WDYiXiangActivity01 extends SlidBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wdyi_xiang01);
        SetTitleBar.setTitleText(WDYiXiangActivity01.this,"微店意向客户");
        findViewById(R.id.yixiang_btn_liaojie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(WDYiXiangActivity01.this);
        Dialog dialog = builder.create();
        View view = getLayoutInflater().inflate(R.layout.dialog_wd_yixiang01,null);
        initDialogView(dialog,view);
        dialog.show();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        window.setContentView(view);
    }
    private void initDialogView(final Dialog dialog, View view){
        view.findViewById(R.id.dialog_wdyx_tv_known).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


}
