package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InsertNewEmailActivity extends SlidBackActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_new_email);
        setTitleBack();
    }
    public void setTitleBack() {
        LinearLayout mLLRight = (LinearLayout) findViewById(R.id.ThreeTitleBar_ll_click);
        SetTitleBar.setTitleText(InsertNewEmailActivity.this,"修改邮箱","保存");
        mLLRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savaDatas();
            }
        });
    }
    private void savaDatas(){
        EditText emailText = (EditText)this.findViewById(R.id.cust_email);
        String email = emailText.getText().toString();
        EditText passwordText = (EditText)this.findViewById(R.id.cust_pwssword);
        String password = passwordText.getText().toString();
        Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        if(!m.matches()){
            Toast.makeText(getApplication(), "请输入正确的邮箱地址", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplication(), "请输入登录密码", Toast.LENGTH_SHORT).show();
            return;
        }
        AppUtil.showProgress(this, ConstantUtils.DIALOG_SHOW);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.params.put("password",password);
        httpRequestUtil.params.put("email",email);
        httpRequestUtil.jsonObjectRequestPostSuccess("/account/cust/email/insertEmail", new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        boolean success = (Boolean) map.get("success");
                        if (success) {
                            Intent intent = new Intent();
                            intent.setClass(getApplicationContext(), CustInfoActivity.class);
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
                        AppUtil.errodDoanload(ConstantUtils.DIALOG_ERROR);
                    }
                });
    }

}
