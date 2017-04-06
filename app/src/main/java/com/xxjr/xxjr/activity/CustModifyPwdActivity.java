package com.xxjr.xxjr.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

public class CustModifyPwdActivity extends SlidBackActivity {

    private TextView modifyPwdButton = null;
    private ImageView mIvBck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_modify_pwd);
        setTitleBack();
    }



    public void setTitleBack() {
        SetTitleBar.setTitleText(CustModifyPwdActivity.this, "修改登录密码", "保存");
        findViewById(R.id.ThreeTitleBar_ll_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alterPsw();
            }
        });
    }

    private void alterPsw(){
        EditText oldPwdText = (EditText)this.findViewById(R.id.cust_old_pwd);
        String oldPwd = oldPwdText.getText().toString();
        EditText newPwdText = (EditText)this.findViewById(R.id.cust_new_pwd);
        String newPwd = newPwdText.getText().toString();
        EditText comfirPwdText = (EditText)this.findViewById(R.id.cust_comfir_pwd);
        String comfirPwd = comfirPwdText.getText().toString();
        if (TextUtils.isEmpty(oldPwd)){
            Toast.makeText(getApplication(), "请输入原始密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(newPwd)){
            Toast.makeText(getApplication(), "请输入新密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(comfirPwd)){
            Toast.makeText(getApplication(), "请输入确认密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!comfirPwd.equals(newPwd)){
            Toast.makeText(getApplication(), "密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        AppUtil.showProgress(this, ConstantUtils.DIALOG_SHOW);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.params.put("oldPassword",oldPwd);
        httpRequestUtil.params.put("newPassword",newPwd);
        httpRequestUtil.params.put("confirmPassword",comfirPwd);
        httpRequestUtil.jsonObjectRequestPostSuccess("/account/cust/modifyLoginPwd", new Response.Listener<JSONObject>() {
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
    }

}
