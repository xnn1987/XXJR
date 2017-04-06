package com.xxjr.xxjr.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.xxjr.xxjr.R;
import com.xxjr.xxjr.adapter.ChoiceOrgAdapter;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;


public class ChoiceOrganizationActivity extends SlidBackActivity {

    private ListView mLvCheckOrg;
    private LinearLayout mLlCommit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_organization);
        setTitleBars();
        initViews();
        setListener();
        lvSetAdapter();
    }



    private void initViews() {
        mLvCheckOrg = (ListView) findViewById(R.id.CheckOrg_lv_Org);
    }
    private void lvSetAdapter() {
        ChoiceOrgAdapter adapter= new ChoiceOrgAdapter(getApplicationContext());
        mLvCheckOrg.setAdapter(adapter);
    }
    public void setTitleBars(){
        mLlCommit = (LinearLayout) findViewById(R.id.ThreeTitleBar_ll_click);
        SetTitleBar.setTitleText(ChoiceOrganizationActivity.this,  "选择机构",  "提交");
    }

    private void setListener() {
        mLlCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDialogContinueCheck();
            }
        });
    }
    private void setDialogContinueCheck() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ChoiceOrganizationActivity.this);
        Dialog dialog = builder.create();
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_choice_org,null);
        setDialogListener(view,dialog);
        dialog.show();
        dialog.getWindow().setContentView(view);
    }

    private void setDialogListener(View view, final Dialog dialog) {
        Button mBtnKnow = (Button) view.findViewById(R.id.ChoiceOrg_btn_know1);
        Button mBtnContinueCheck = (Button) view.findViewById(R.id.ChoiceOrg_btn_continue1);
        mBtnKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mBtnContinueCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CheckResultActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
    }


}
