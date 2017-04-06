package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

import java.util.Map;

public class CustRealNameActivity extends SlidBackActivity implements View.OnClickListener{

    private Button custRealNameButton = null;
    private String fromActivity;
    private int from;
    private int applyId;
    private int loanId;
    private int commitType;
    private String produceType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_real_name);
        SetTitleBar.setTitleText(CustRealNameActivity.this, "实名认证");
        getIntentDatas();
        initView();
        SetListener();
    }

    private void SetListener() {
        custRealNameButton.setOnClickListener(this);
    }
    private void initView(){
        custRealNameButton = (Button)this.findViewById(R.id.cust_realName_btn);
    }

    private String titleName;
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cust_realName_btn:
                EditText realNameText = (EditText)this.findViewById(R.id.cust_realName);
                String realName = realNameText.getText().toString();
                EditText cardNoText = (EditText)this.findViewById(R.id.cust_cardNo);
                String cardNo = cardNoText.getText().toString();
                AppUtil.showProgress(this, ConstantUtils.DIALOG_SHOW);
                HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
                httpRequestUtil.params.put("realName",realName);
                httpRequestUtil.params.put("cardNo",cardNo);
                httpRequestUtil.jsonObjectRequestPostSuccess("/account/cust/validateName", new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                                boolean success = (Boolean) map.get("success");
                                if (success) {
                                   finish();
                                } else {
                                    String message = map.get("message").toString();
                                    Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show();
                                }
                                AppUtil.dismissProgress();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                AppUtil.errodDoanload(ConstantUtils.DIALOG_ERROR);
                            }
                        });

                break;

        }
    }

    public void getIntentDatas() {
        Intent intent = getIntent();
        from = intent.getIntExtra("from", -1);
        if (from!=-1) {
            applyId = intent.getIntExtra("applyId", -1);
            loanId = intent.getIntExtra("loanId", -1);
            commitType = intent.getIntExtra("commitType", 1);
            produceType = intent.getStringExtra("produceType");
            titleName = intent.getStringExtra("titleName");
        }
    }
}
