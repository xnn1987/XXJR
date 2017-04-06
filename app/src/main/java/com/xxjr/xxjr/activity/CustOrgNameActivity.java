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

public class CustOrgNameActivity extends SlidBackActivity implements View.OnClickListener{

    private Button custOrgNameButton = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_org_name);
        SetTitleBar.setTitleText(CustOrgNameActivity.this, "机构");
        initView();
        SetListener();
        if(MyApplication.userInfo.get("orgName") != null){
            EditText orgNameText = (EditText)this.findViewById(R.id.cust_org_name);
            orgNameText.setText(MyApplication.userInfo.get("orgName").toString());
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cust_org_name_btn:

                EditText orgNameText = (EditText)this.findViewById(R.id.cust_org_name);
                String orgName = orgNameText.getText().toString();
                if (TextUtils.isEmpty(orgName)){
                    Toast.makeText(getApplication(), "请输入机构名称", Toast.LENGTH_SHORT).show();
                    return;
                }
                AppUtil.showProgress(this, ConstantUtils.DIALOG_SHOW);
                HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
                httpRequestUtil.params.put("orgName",orgName);
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
        custOrgNameButton.setOnClickListener(this);

    }

    private void initView(){
        custOrgNameButton = (Button)this.findViewById(R.id.cust_org_name_btn);
    }
}
