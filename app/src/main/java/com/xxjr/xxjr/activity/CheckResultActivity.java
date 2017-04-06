package com.xxjr.xxjr.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.xxjr.xxjr.R;
import com.xxjr.xxjr.adapter.CheckResultAdapter;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;


public class CheckResultActivity extends SlidBackActivity {

    private ListView mLvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_result);
        setTitleBars();
        initViews();
        setListener();
        lvSetAdapter();

    }


    private void setListener() {

    }

    private void initViews() {
        mLvResult = (ListView) findViewById(R.id.ChoiceResult_lv_result);
    }

    public void setTitleBars(){
        LinearLayout mLLRight = (LinearLayout) findViewById(R.id.ThreeTitleBar_ll_click);
        SetTitleBar.setTitleText(CheckResultActivity.this, "查号结果",  "新建查号");
        mLLRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    private void lvSetAdapter() {
        CheckResultAdapter adapter = new CheckResultAdapter(getApplicationContext());
        mLvResult.setAdapter(adapter);
    }

}
