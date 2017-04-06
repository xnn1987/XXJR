package com.xxjr.xxjr.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

import java.util.Map;

public class CustDescActivity extends SlidBackActivity implements View.OnClickListener{

    private Button custCustDescButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_cust_desc);
        SetTitleBar.setTitleText(CustDescActivity.this, "个人描述");
        initView();
        SetListener();
        if(MyApplication.userInfo.get("custDesc") != null){
            EditText DesxText = (EditText)this.findViewById(R.id.cust_custDesc);
            DesxText.setText(MyApplication.userInfo.get("custDesc").toString());
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cust_custDesc_btn:
                EditText custDescTest = (EditText)this.findViewById(R.id.cust_custDesc);
                String custDesc = custDescTest.getText().toString();
                if(TextUtils.isEmpty(custDesc)){
                    Toast.makeText(getApplication(), "请输入个人描述", Toast.LENGTH_SHORT).show();
                    return;
                }
                AppUtil.showProgress(this, ConstantUtils.DIALOG_SHOW);
                HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
                httpRequestUtil.params.put("custDesc",custDesc);
                httpRequestUtil.jsonObjectRequestPostSuccess("/account/cust/insertInfo", new Response.Listener<JSONObject>() {
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
    private void SetListener() {
        custCustDescButton.setOnClickListener(this);

    }

    private void initView(){
        custCustDescButton = (Button)this.findViewById(R.id.cust_custDesc_btn);
    }
}
