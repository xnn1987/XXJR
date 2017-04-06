package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import java.util.Timer;
import java.util.TimerTask;

public class ModifyTelSendCodeActivity extends SlidBackActivity implements View.OnClickListener{

    private Button sendCodeButton = null;

    private Button validateCodeBuntton = null;

    private int recLen = 60;
    Timer timer = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_tel_send_code);
        TextView telephoenTextView = (TextView)this.findViewById(R.id.cust_telephone_text);
        telephoenTextView.setText(MyApplication.userInfo.get("telephone").toString());
        initView();
        SetListener();
        SetTitleBar.setTitleText(ModifyTelSendCodeActivity.this, "修改手机号码");
    }
    private void SetListener() {
        sendCodeButton.setOnClickListener(this);
        validateCodeBuntton.setOnClickListener(this);
    }

    private void initView(){
        sendCodeButton = (Button)this.findViewById(R.id.sendCode);
        validateCodeBuntton = (Button)this.findViewById(R.id.cust_send_code_btn);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sendCode:
                HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
                httpRequestUtil.params.put("telephone", MyApplication.userInfo.get("telephone").toString());
                httpRequestUtil.jsonObjectRequestPostSuccess("/smsAction/login/changeTel", new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                                boolean success = (Boolean) map.get("success");
                                if (success) {
                                    sendCodeButton.setClickable(false);
                                    recLen = 60;
                                    TimerTask task = new TimerTask() {
                                        @Override
                                        public void run() {
                                            runOnUiThread(new Runnable() {      // UI thread
                                                @Override
                                                public void run() {
                                                    recLen--;
                                                    sendCodeButton.setText(recLen+"秒后重试");
                                                    if (recLen == 0) {
                                                        sendCodeButton.setText("重新获取验证码");
                                                        sendCodeButton.setClickable(true);
                                                        timer.cancel();
                                                    }
                                                }
                                            });
                                        }
                                    };
                                    timer = new Timer();
                                    timer.schedule(task, 1000, 1000);
                                } else {
                                    String message = map.get("message").toString();
                                    Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                break;
            case R.id.cust_send_code_btn:
                AppUtil.showProgress(this, ConstantUtils.DIALOG_SHOW);
                EditText randomNoText = (EditText)this.findViewById(R.id.email_send_randomNo);
                String randomNo = randomNoText.getText().toString();
                HttpRequestUtil httpRequestUtil1 = new HttpRequestUtil();
                httpRequestUtil1.params.put("telephone", MyApplication.userInfo.get("telephone").toString());
                httpRequestUtil1.params.put("smsNo",randomNo);
                httpRequestUtil1.jsonObjectRequestPostSuccess("/smsAction/valid/login/changeTel", new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                                boolean success = (Boolean) map.get("success");
                                if (success) {
                                    Intent intent = new Intent();
                                    intent.setClass(getApplicationContext(), ModifyTelNewSendCodeActivity.class);
                                    startActivity(intent);
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
}
