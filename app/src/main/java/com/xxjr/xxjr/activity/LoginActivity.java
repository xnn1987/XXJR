package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.bean.DestroyMainActivityEvenbus;
import com.xxjr.xxjr.bean.RegisterSuccesEvenbusbean;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.utils.DebugLog;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.utils.Md5Utils;
import com.xxjr.xxjr.utils.SharedPrefUtil;
import com.xxjr.xxjr.utils.common.CommomAcitivity;
import com.xxjr.xxjr.utils.network.MapCallback;
import com.xxjr.xxjr.utils.network.MyOkHttpUtils;
import com.ypy.eventbus.EventBus;
import com.zhy.http.okhttp.request.RequestCall;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;


public class LoginActivity extends CommomAcitivity implements View.OnClickListener {

    private Button mBtnLogin;
    private Button mBtnRegist ;
    private TextView mBtnForgetPwd ;
    private RelativeLayout mRlFinish;
    private EditText mEtTel;
    private EditText mEtPwd;
    MapCallback mapCallback = new MapCallback(this,false) {
        @Override
        public void onBefore(Request request) {
            super.onBefore(request);
            AppUtil.showProgress(LoginActivity.this, "正在登录");
        }

        @Override
        public void onError(Call call, Exception e) {
            AppUtil.errodDoanload(ConstantUtils.DIALOG_ERROR);
        }
        @Override
        public void onResponse(Map responseMap) {
            super.onResponse(responseMap);
            if ( responseMap!=null) {
                List<Map<String, Object>> rowsLsist = (List<Map<String, Object>>) responseMap.get("rows");
                Map<String, Object> userMap = rowsLsist.get(0);
                String uid = userMap.get("customerId").toString();
                MyApplication.uid = uid;
                putUserLoginSp(uid,mEtPwd.getText().toString().trim());
                destroyMainActivity();
                intent2MainAndFinish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_login);
        initView();
        SetListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView(){
        mRlFinish = (RelativeLayout) findViewById(R.id.LoginActivity_rl_finish);
        mBtnLogin = (Button)this.findViewById(R.id.cust_btn_login);
        mBtnRegist = (Button)this.findViewById(R.id.cust_btn_register);
        mBtnForgetPwd = (TextView)this.findViewById(R.id.cust_btn_forgetPwd);
        mEtTel = (EditText)this.findViewById(R.id.telephone);
        mEtPwd = (EditText)this.findViewById(R.id.passwprd);
    }

    private void SetListener() {
        mBtnLogin.setOnClickListener(this);
        mBtnRegist.setOnClickListener(this);
        mBtnForgetPwd.setOnClickListener(this);
        mRlFinish.setOnClickListener(this);
    }

    //销毁主函数
    private void destroyMainActivity(){
        DestroyMainActivityEvenbus destroyMainActivityEvenbus = new DestroyMainActivityEvenbus();
        destroyMainActivityEvenbus.setIsExit(true);
        EventBus.getDefault().post(destroyMainActivityEvenbus);
    }
    private void intent2MainAndFinish(){
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cust_btn_login:
                if (mEtTel.getText().toString().trim().length()!=11){
                    Toast.makeText(getApplicationContext(),"请正确输入手机号码",Toast.LENGTH_SHORT).show();
                    return;
                }else if ( mEtPwd.getText().toString().trim().length()<6){
                    Toast.makeText(getApplicationContext(),"请输入6位以上的密码",Toast.LENGTH_SHORT).show();
                    return;
                }

                // todo okhttp  处理
                Map<String,String> map = new HashMap<>();
                map.put("telephone", mEtTel.getText().toString().trim());
                map.put("password", mEtPwd.getText().toString().trim());

                RequestCall build = MyOkHttpUtils.postRequest(Urls.LOGGIN, map);
                build.execute(mapCallback);


                /*HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
                Map<String, String> params = httpRequestUtil.params;
                params.put("telephone", mEtTel.getText().toString().trim());
                params.put("password", mEtPwd.getText().toString().trim());
                httpRequestUtil.jsonObjectRequestPostSuccess(Urls.LOGGIN,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Map<String, Object> responseMap = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                                if ((Boolean) responseMap.get("success")) {
                                    List<Map<String, Object>> rowsLsist = (List<Map<String, Object>>) responseMap.get("rows");
                                    Map<String, Object> userMap = rowsLsist.get(0);
                                    String uid = userMap.get("customerId").toString();
                                    MyApplication.uid = uid;
                                    putUserLoginSp(uid,mEtPwd.getText().toString().trim());
                                    destroyMainActivity();
                                    intent2MainAndFinish();
                                } else {
                                    String message = responseMap.get("message").toString();
                                    Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show();
                                    AppUtil.dismissProgress();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                AppUtil.errodDoanload(ConstantUtils.DIALOG_ERROR);
                            }
                        }
                );*/
                break;
            case R.id.cust_btn_register:
                Intent intent1 = new Intent();
                intent1.setClass(getApplicationContext(), RegisterActivity.class);
                startActivity(intent1);
                break;
            case R.id.cust_btn_forgetPwd:
                Intent intent2 = new Intent();
                intent2.setClass(getApplicationContext(), ForgetPwdActivity.class);
                startActivity(intent2);
                break;
            case R.id.LoginActivity_rl_finish:
                Intent intent3 = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent3);
                finish();
                break;
        }
    }

    private void putUserLoginSp(String uid,String psw){
        SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(getApplicationContext(),ConstantUtils.SP_USER_NAME);
        sharedPrefUtil.putString(ConstantUtils.SP_USER_UID,uid);
        sharedPrefUtil.putString(ConstantUtils.SP_USER_PSW, Md5Utils.string2MD5(psw));
        sharedPrefUtil.commit();
    }

    /**
     * 注册成功直接跳转主界面， 销毁登录界面
     */
    public void onEventBackgroundThread(RegisterSuccesEvenbusbean bean){
        boolean succes = bean.isSucces();
        if (succes){
            finish();
        }
    }


    /**
     * 返回
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }

}
