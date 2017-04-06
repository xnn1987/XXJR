package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class ModifyTelPwdActivity extends SlidBackActivity implements View.OnClickListener{

    private Button loginPwdButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_tel_pwd);
        initView();
        SetListener();
        SetTitleBar.setTitleText(ModifyTelPwdActivity.this, "修改手机号码");
    }
    private void SetListener() {
        loginPwdButton.setOnClickListener(this);
    }

    private void initView(){
        loginPwdButton = (Button)this.findViewById(R.id.cust_login_pwd_btn);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cust_login_pwd_btn:
                EditText loginPwdText = (EditText)this.findViewById(R.id.cust_login_pwd);
                String loginPwd = loginPwdText.getText().toString();
                if(TextUtils.isEmpty(loginPwd)){
                    Toast.makeText(getApplication(), "请输入登录密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                AppUtil.showProgress(this, ConstantUtils.DIALOG_SHOW);
                HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
                httpRequestUtil.params.put("password",loginPwd);
                httpRequestUtil.jsonObjectRequestPostSuccess("/account/cust/validateLoginPwd",
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                                boolean success = (Boolean) map.get("success");
                                if (success) {
                                    Intent intent = new Intent();
                                    intent.setClass(getApplicationContext(), ModifyTelSendCodeActivity.class);
                                    startActivity(intent);
                                } else {
                                    String message = (String)map.get("message");
                                    Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show();
                                }
                                AppUtil.dismissProgress();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                AppUtil.errodDoanload("网络不稳定");
                            }
                        });

                break;
        }
    }
}
