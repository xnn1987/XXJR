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
import java.util.Timer;
import java.util.TimerTask;

public class ModifyTelNewSendCodeActivity extends SlidBackActivity implements View.OnClickListener{

    private Button sendCodeButton= null;

    private Button changeTelephoneButton = null;

    private int recLen = 60;
    Timer timer = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_tel_new_send_code);
        initView();
        SetListener();
        SetTitleBar.setTitleText(ModifyTelNewSendCodeActivity.this, "修改手机号码");
    }
    private void SetListener() {
        sendCodeButton.setOnClickListener(this);
        changeTelephoneButton.setOnClickListener(this);
    }

    private void initView(){
        sendCodeButton = (Button)this.findViewById(R.id.sendCode);
        changeTelephoneButton = (Button)this.findViewById(R.id.cust_change_telephone_btn);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sendCode:
                EditText newTelephoneText = (EditText)this.findViewById(R.id.cust_new_telephone);
                String newTelephone = newTelephoneText.getText().toString();
                if(TextUtils.isEmpty(newTelephone)){
                    Toast.makeText(getApplication(), "请输入手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
                httpRequestUtil.params.put("newTelephone",newTelephone);
                httpRequestUtil.jsonObjectRequestPostSuccess("/smsAction/login/changeTelNew", new Response.Listener<JSONObject>() {
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
            case R.id.cust_change_telephone_btn:
                EditText newTelephoneText1 = (EditText)this.findViewById(R.id.cust_new_telephone);
                String newTelephone1 = newTelephoneText1.getText().toString();
                if(TextUtils.isEmpty(newTelephone1)){
                    Toast.makeText(getApplication(), "请输入手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                EditText randomNoText = (EditText)this.findViewById(R.id.email_send_randomNo);
                String randomNo = randomNoText.getText().toString();
                if(TextUtils.isEmpty(randomNo)){
                    Toast.makeText(getApplication(), "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                AppUtil.showProgress(this, ConstantUtils.DIALOG_SHOW);
                HttpRequestUtil httpRequestUtil1 = new HttpRequestUtil();
                httpRequestUtil1.params.put("newTelephone",newTelephone1);
                httpRequestUtil1.params.put("randomNo",randomNo);
                httpRequestUtil1.jsonObjectRequestPostSuccess("/account/cust/chanageNewTel", new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                                boolean success = (Boolean) map.get("success");
                                if (success) {
                                    Intent intent = new Intent();
                                    intent.setClass(getApplicationContext(), CustInfoActivity.class);
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
