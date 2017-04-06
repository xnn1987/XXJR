package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.bean.RegisterSuccesEvenbusbean;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.Md5Utils;
import com.xxjr.xxjr.utils.SharedPrefUtil;
import com.xxjr.xxjr.utils.common.CommomAcitivity;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class RegisterActivity extends CommomAcitivity implements View.OnClickListener {

    private Button mBtnMsgRandNo,mBtnVoice;
    private Button mBtnRegister = null;
    private TextView mBtnLogin = null;
    boolean isCheck = true;

    private int recLen = 60;
    Timer timer = null;
    private String from;
    private LinearLayout mLlFinish;
    private TextView mTvDeal;
    private RelativeLayout mRlCheck;
    private ImageView mIvUncheck,mIvCheck;
    private SharedPreferences read;
    private EditText mEtRandNo;
    private EditText mEtPsw;
    private EditText mTvRepeatPsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        SetListener();
    }
    private void SetListener() {
        mBtnMsgRandNo.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);
        mBtnVoice.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
        mLlFinish.setOnClickListener(this);
        mTvDeal.setOnClickListener(this);
        mRlCheck.setOnClickListener(this);

    }

    private void initView(){
        mLlFinish = (LinearLayout) findViewById(R.id.register_btn_finish);
        mBtnMsgRandNo = (Button)this.findViewById(R.id.Register_btn_sendCode);
        mBtnVoice = (Button)this.findViewById(R.id.Register_btn_Tel);
        mBtnRegister = (Button)this.findViewById(R.id.Register_btn_register);
        mBtnLogin = (TextView)this.findViewById(R.id.register_btn_login);
        mTvDeal = (TextView) findViewById(R.id.Register_tv_deal);
        mEtRandNo = (EditText)this.findViewById(R.id.validateCode);
        mEtPsw = (EditText)this.findViewById(R.id.password);
        mTvRepeatPsw = (EditText) findViewById(R.id.RegisterRepeat_et_password);
//        mCbCheck = (CheckBox) findViewById(R.id.Register_cb_check);
        //勾选与否
        mRlCheck = (RelativeLayout) findViewById(R.id.Register_rl_check);
        mIvUncheck = (ImageView) findViewById(R.id.Register_iv_uncheck);
        mIvCheck = (ImageView) findViewById(R.id.Register_iv_check);
    }
    @Override
    public void onClick(View view) {
        EditText telephoneText =  (EditText)this.findViewById(R.id.telephone);
        String telephone = telephoneText.getText().toString();
        switch (view.getId()) {
            case R.id.Register_btn_sendCode:
                getRegisterCode(telephone,null, mBtnMsgRandNo);
                break;
            case R.id.Register_btn_Tel:
                getRegisterCode(telephone,"voice",mBtnVoice);
                break;
            case  R.id.Register_btn_register:
                String randomNo = mEtRandNo.getText().toString();
                String password = mEtPsw.getText().toString();
                String repeatPassword = mTvRepeatPsw.getText().toString();
                EditText refererText = (EditText)this.findViewById(R.id.referer);
                String referer = refererText.getText().toString();
                 if(telephone.length()!=11){
                    Toast.makeText(getApplicationContext(),"请输入正确的手机号码",Toast.LENGTH_SHORT).show();
                }else if (!password.equals(repeatPassword)){
                    Toast.makeText(getApplicationContext(),"两次密码输入不一致，请重新输入",Toast.LENGTH_SHORT).show();
                }
                else if (isAllNumber(password)){
                    Toast.makeText(getApplicationContext(),"密码不能全为数字",Toast.LENGTH_SHORT).show();
                }else if (isCheck==false){
                    Toast.makeText(getApplicationContext(),"请您仔细看注册协议在注册",Toast.LENGTH_SHORT).show();
                }else  {  //  注册
                    registerDatas(telephone,randomNo,password);//注册
                }

                break;
            case R.id.register_btn_login:
                finish();
                break;
            case R.id.register_btn_finish:
                finish();
                break;
            case R.id.Register_tv_deal:
                Intent intent = new Intent(getApplicationContext(),RegisterDealActivity.class);
                startActivity(intent);
                break;
            case R.id.Register_rl_check://勾选没
                if (isCheck==true){
                    mIvUncheck.setVisibility(View.VISIBLE);
                    mIvCheck.setVisibility(View.GONE);
                    isCheck = false;
                }else {
                    mIvCheck.setVisibility(View.VISIBLE);
                    mIvUncheck.setVisibility(View.GONE);
                    isCheck = true;
                }
                break;
        }
    }
    private boolean isAllNumber(String password){
        boolean flag = true;
        char[] chars = password.toCharArray();
        for (int i = 0; i<chars.length; i++){
            if (!Character.isDigit(chars[i])){
                flag = false;
                break;
            }
        }
        return flag;
    }

    /**
     * 短信验证
     * @param telephone
     * @param isVoice
     * @param btn
     */
    private void getRegisterCode(String telephone,String isVoice, final Button btn){
        if(!TextUtils.isEmpty(telephone.trim())){
            AppUtil.showProgress(this, "正在发送，请稍候...");
            HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
            httpRequestUtil.params.put("telephone", telephone);
            if (isVoice!= null) {
                httpRequestUtil.params.put("isVoice", isVoice);
            }
            httpRequestUtil.jsonObjectRequestPostSuccess("/smsAction/nologin/register", new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Map<String,Object> msgMap = (Map<String,Object>) JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                            boolean success = (Boolean)msgMap.get("success");
                            if(success){
                                btn.setClickable(false);
                                recLen = 60;
                                TimerTask task = new TimerTask() {
                                    @Override
                                    public void run() {
                                        runOnUiThread(new Runnable() {      // UI thread
                                            @Override
                                            public void run() {
                                                recLen--;
                                                btn.setText(recLen+"秒后重试");
                                                if (recLen == 0) {
                                                    btn.setText("重新获取验证码");
                                                    btn.setClickable(true);
                                                    timer.cancel();
                                                }
                                            }
                                        });
                                    }
                                };
                                timer = new Timer();
                                timer.schedule(task, 1000, 1000);
                            }else {
                                Toast.makeText(getApplicationContext(),msgMap.get("message").toString(),Toast.LENGTH_SHORT).show();
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
        }else {
            Toast.makeText(getApplicationContext(),"请输入正确的手机号码",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 注册
     * @param telephone
     * @param randomNo
     * @param password
     */
    private void registerDatas(final String telephone,String randomNo, final String  password){
        AppUtil.showProgress(this, "正在注册，请稍候...");
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.params.put("telephone", telephone);
        httpRequestUtil.params.put("randomNo", randomNo);
        httpRequestUtil.params.put("password", password);
        httpRequestUtil.jsonObjectRequestPostSuccess("/cust/register",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtil.dismissProgress();
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        boolean success = (Boolean) map.get("success");
                        if (success) {
                            RegisterSuccesEvenbusbean bean = new RegisterSuccesEvenbusbean();
                            bean.setIsSucces(success);
                            loginDatas(telephone,password);
                        } else {
                            String message = map.get("message").toString();
                            Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AppUtil.errodDoanload("网络不稳定");
                    }
                });
    }

    /**
     * 登录
     * @param telephone
     * @param password
     */
    private void loginDatas(String telephone,String password){
        AppUtil.showProgress(this, "正在登录");
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        Map<String, String> params = httpRequestUtil.params;
        params.put("telephone", telephone);
        params.put("password", password);
        httpRequestUtil.jsonObjectRequestPostSuccess(Urls.LOGGIN,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> responseMap = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        boolean success = (Boolean) responseMap.get("success");
                        if (success) {
                            List<Map<String, Object>> rowsLsist = (List<Map<String, Object>>) responseMap.get("rows");
                            Map<String, Object> userMap = rowsLsist.get(0);
                            String uid = userMap.get("customerId").toString();
                            MyApplication.uid = uid;
                            putUidSp(uid,mEtPsw.getText().toString().trim());
                            intent2MainAndFinish();
                        } else {
                            String message = responseMap.get("message").toString();
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
                }
        );
    }

    private void putUidSp(String uid,String psw){
        SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(getApplicationContext(), ConstantUtils.SP_USER_NAME);
        sharedPrefUtil.putString(ConstantUtils.SP_USER_UID,uid);
        sharedPrefUtil.putString(ConstantUtils.SP_USER_PSW, Md5Utils.string2MD5(psw));
        sharedPrefUtil.commit();
    }
    private void intent2MainAndFinish(){
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

}
