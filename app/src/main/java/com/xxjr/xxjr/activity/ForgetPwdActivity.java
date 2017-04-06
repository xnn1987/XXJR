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
import java.util.Timer;
import java.util.TimerTask;

public class ForgetPwdActivity extends SlidBackActivity implements View.OnClickListener {

    private Button msgButton = null;
    private Button forgetButton = null;

    private int recLen = 60;
    Timer timer = null;
    private EditText mEtTel;
    private EditText mEtRandNo;
    private EditText mEtPsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
        SetTitleBar.setTitleText(ForgetPwdActivity.this, "忘记密码");
        initView();
        SetListener();
    }
    private void SetListener() {
        msgButton.setOnClickListener(this);
        forgetButton.setOnClickListener(this);
    }

    private void initView(){
        msgButton = (Button)this.findViewById(R.id.sendCode);
        mEtTel = (EditText)this.findViewById(R.id.telephone);
        forgetButton = (Button)this.findViewById(R.id.cust_btn_forget);
        mEtRandNo = (EditText)this.findViewById(R.id.validateCode);
        mEtPsw = (EditText)this.findViewById(R.id.password);
    }
    @Override
    public void onClick(View view) {
        String telephone = mEtTel.getText().toString();
        switch (view.getId()) {
            case R.id.sendCode:
                if(telephone != ""){
                    AppUtil.showProgress(this, "正在加载数据，请稍候...");
                    HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
                    httpRequestUtil.params.put("telephone", telephone);
                    httpRequestUtil.jsonObjectRequestPostSuccess("/smsAction/nologin/forgetPwd", new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Map<String,Object> msgMap = (Map<String,Object>) JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                                    boolean success = (Boolean)msgMap.get("success");
                                    if(success){
                                        msgButton.setClickable(false);
                                        recLen = 60;
                                        TimerTask task = new TimerTask() {
                                            @Override
                                            public void run() {
                                                runOnUiThread(new Runnable() {      // UI thread
                                                    @Override
                                                    public void run() {
                                                        recLen--;
                                                        msgButton.setText(recLen+"秒后重试");
                                                        if (recLen == 0) {
                                                            msgButton.setText("重新获取验证码");
                                                            msgButton.setClickable(true);
                                                            timer.cancel();
                                                        }
                                                    }
                                                });
                                            }
                                        };
                                        timer = new Timer();
                                        timer.schedule(task, 1000, 1000);
                                    }else {
                                        Toast.makeText(getApplication(), msgMap.get("message").toString(), Toast.LENGTH_SHORT).show();
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
                }
                break;
            case R.id.cust_btn_forget:
                String randomNo = mEtRandNo.getText().toString();
                String password = mEtPsw.getText().toString();
                HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
                httpRequestUtil.params.put("telephone", telephone);
                httpRequestUtil.params.put("randomNo", randomNo);
                httpRequestUtil.params.put("password", password);
                httpRequestUtil.jsonObjectRequestPostSuccess("/cust/forgetPwd", new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Map<String,Object> msgMap = (Map<String,Object>) JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                                boolean success = (Boolean)msgMap.get("success");
                                if(success) {
                                    Toast.makeText(getApplication(), "密码修改成功", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent();
                                    intent.setClass(getApplicationContext(),LoginActivity.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(getApplication(), msgMap.get("message").toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(ForgetPwdActivity.this,  ConstantUtils.DIALOG_ERROR, Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
        }
    }


}
