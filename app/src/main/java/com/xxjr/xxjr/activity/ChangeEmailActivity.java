package com.xxjr.xxjr.activity;

import android.os.Bundle;
import android.text.TextUtils;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangeEmailActivity extends SlidBackActivity implements View.OnClickListener{

    private Button sendCodeButton = null;
    private AppUtil appUtil;

    private Button custEmailButton = null;
    private int recLen = 60;
    Timer timer = null;

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sendCode:
                EditText emailText = (EditText)ChangeEmailActivity.this.findViewById(R.id.cust_email);
                String email = emailText.getText().toString();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplication(), "请输入邮箱", Toast.LENGTH_SHORT).show();
                    return;
                }
                Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
                Matcher m = p.matcher(email);
                if(!m.matches()){
                    Toast.makeText(getApplication(), "请输入正确的邮箱地址", Toast.LENGTH_SHORT).show();
                    return;
                }

                appUtil.showProgress(this, ConstantUtils.DIALOG_SHOW);
                HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
                httpRequestUtil.params.put("telephone", MyApplication.userInfo.get("telephone").toString());
                httpRequestUtil.params.put("email",email);
                httpRequestUtil.jsonObjectRequestPostSuccess("/smsAction/login/email", new Response.Listener<JSONObject>() {
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
                                appUtil.dismissProgress();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                appUtil.errodDoanload(ConstantUtils.DIALOG_ERROR);
                            }
                        });
                break;
            case R.id.cust_save_chnage_email:
                EditText randomNoText = (EditText)ChangeEmailActivity.this.findViewById(R.id.email_send_randomNo);
                String randomNo = randomNoText.getText().toString();
                if(TextUtils.isEmpty(randomNo)){
                    Toast.makeText(getApplication(), "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }

                EditText saveEmailText = (EditText)ChangeEmailActivity.this.findViewById(R.id.cust_email);
                String saveemail = saveEmailText.getText().toString();
                appUtil.showProgress(this, ConstantUtils.DIALOG_SHOW);
                HttpRequestUtil saveHttpRequestUtil = new HttpRequestUtil();
                saveHttpRequestUtil.params.put("email",saveemail);
                saveHttpRequestUtil.params.put("randomNo",randomNo);
                saveHttpRequestUtil.params.put("telephone", MyApplication.userInfo.get("telephone").toString());
                saveHttpRequestUtil.jsonObjectRequestPostSuccess("/account/cust/email/updateEmail", new Response.Listener<JSONObject>() {
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
                                appUtil.dismissProgress();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                appUtil.errodDoanload(ConstantUtils.DIALOG_ERROR);
                            }
                        });
                break;
        }
    }
    private void SetListener() {
        sendCodeButton.setOnClickListener(this);
        custEmailButton.setOnClickListener(this);
    }

    private void initView(){
        sendCodeButton = (Button)this.findViewById(R.id.sendCode);
        custEmailButton = (Button)this.findViewById(R.id.cust_save_chnage_email);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);
        Bundle bundle = this.getIntent().getExtras();
        String email = bundle.get("email").toString();
        TextView emailTextView = (TextView)this.findViewById(R.id.cust_email_text);
        emailTextView.setText(email);
        initView();
        SetListener();
        SetTitleBar.setTitleText(ChangeEmailActivity.this, "修改邮箱");
    }
}
