package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;
import com.xxjr.xxjr.utils.Shared.SocialUmeng;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class WithdrawActivity extends SlidBackActivity implements View.OnClickListener {
    private TextView mTvUsername, mTvWXName, mTvForgetPsw;
    private EditText mEtInputCount, mEtInputPsw;
    private Button mBtnCommit;
    private RelativeLayout mRlCheckBank;
    private Button mBtnMsgCode, mBtnVoidCode;
    private int recLen = 60;
    Timer timer = null;
    private String moneyCount;
    private TextView mTvRealName;
    private String result = null;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case ConstantUtils.LOADING:
                    AppUtil.showProgress(WithdrawActivity.this,ConstantUtils.DIALOG_SHOW);
                    break;
                case ConstantUtils.LOAD_SUCCESS:
                    AppUtil.dismissProgress();
                    break;
                case ConstantUtils.LOAD_ERROR:
                    AppUtil.errodDoanload(ConstantUtils.DIALOG_ERROR);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        SetTitleBar.setTitleText(WithdrawActivity.this,  "提现微信");
        getIntentDatas();
        initView();
        initViewDatas();
        setListener();

    }

    private void initView() {
        mTvUsername = (TextView) findViewById(R.id.Withdraw_tv_username);
        mTvWXName = (TextView) findViewById(R.id.Withdraw_tv_WXname);
        mEtInputCount = (EditText) findViewById(R.id.Withdraw_et_input);
        mBtnCommit = (Button) findViewById(R.id.Withdraw_btn_commit);
        mRlCheckBank = (RelativeLayout) findViewById(R.id.Withdraw_rl_AddBankCard);
        mBtnMsgCode = (Button) this.findViewById(R.id.Withdraw_btn_sendCode);
        mBtnVoidCode = (Button) this.findViewById(R.id.Withdraw_btn_sendVoidCode);
        mTvRealName = (TextView) findViewById(R.id.Withdraw_tv_username);
    }

    // 初始化控件
    private void initViewDatas() {
        mEtInputCount.setHint("可提现金额" + moneyCount);
        if (MyApplication.userInfo.get("wxNickName")!= null) {
            mTvWXName.setText(MyApplication.userInfo.get("wxNickName").toString());
        }
        if (MyApplication.userInfo.get("realName") != null) {
            mTvRealName.setText(MyApplication.userInfo.get("realName").toString());
        }
    }

    private void setListener() {
        mRlCheckBank.setOnClickListener(this);
        mBtnCommit.setOnClickListener(this);
        mBtnMsgCode.setOnClickListener(this);
        mBtnVoidCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //TODO  银行回传
            case R.id.Withdraw_rl_AddBankCard:
                /*Platform wechat= ShareSDK.getPlatform(this, Wechat.NAME);
                wechat.setPlatformActionListener(this);
                wechat.authorize();
                wechat.SSOSetting(true);
                wechat.showUser(null);*/
                SocialUmeng.threeLogin(WithdrawActivity.this,handler);
                break;
            case R.id.Withdraw_btn_commit:
                try {
                    double amount = Double.parseDouble(mEtInputCount.getText().toString());
                    EditText codeText = (EditText) this.findViewById(R.id.Withdraw_et_authCode);
                    String code = codeText.getText().toString();
                    if (TextUtils.isEmpty(mTvWXName.getText().toString())) {
                        Toast.makeText(getApplication(), "请绑定微信", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (TextUtils.isEmpty(code)) {
                        Toast.makeText(getApplication(), "请输入验证码", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (amount <= 0) {
                        Toast.makeText(getApplication(), "请输入正确的提现金额", Toast.LENGTH_SHORT).show();
                        return;
                    } else
                    AppUtil.showProgress(this, "正在提交，请稍候...");
                    HttpRequestUtil httpRequestUtil = new HttpRequestUtil(this);
                    httpRequestUtil.params.put("amount", mEtInputCount.getText().toString());
                    httpRequestUtil.params.put("randomNo", code);
                    httpRequestUtil.jsonObjectRequestPostSuccess("/account/fund/addWithdraw", new Response.Listener<JSONObject>() {
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
                } catch (Exception e) {
                    Toast.makeText(getApplication(), "请输入正确的提现金额", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.Withdraw_btn_sendCode:
                getCheckCode(Urls.WX_SENDCODE, null, mBtnMsgCode);
                break;
            case R.id.Withdraw_btn_sendVoidCode:
                getCheckCode(Urls.WX_SENDCODE, null, mBtnVoidCode);
                break;
        }
    }

    /**
     * 验证码
     * @param url
     * @param isVoice
     * @param btn
     */
    private void getCheckCode(String url, String isVoice, final Button btn) {
        mBtnMsgCode.setClickable(false);
        mBtnVoidCode.setClickable(false);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        Map<String, String> params = httpRequestUtil.params;
        params.put("telephone", MyApplication.userInfo.get("telephone").toString());
        if (isVoice != null) {
            params.put("isVoice", isVoice);
        }
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        boolean success = (Boolean) map.get("success");
                        if (success) {
                            recLen = 60;
                            TimerTask task = new TimerTask() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {      // UI thread
                                        @Override
                                        public void run() {
                                            recLen--;
                                            btn.setText(recLen + "秒后重试");
                                            if (recLen == 0) {
                                                btn.setText("重新获取");
                                                mBtnMsgCode.setClickable(true);
                                                mBtnVoidCode.setClickable(true);
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
    }


    private void updateUserInfo(String url) {
        SharedPreferences read = getSharedPreferences(ConstantUtils.SP_USER_NAME, MODE_PRIVATE);
        String uid = read.getString(ConstantUtils.SP_USER_UID, null);
        MyApplication.uid = uid;
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        List<Map<String, Object>> infoList = (List<Map<String, Object>>) map.get("rows");
                        if (infoList != null && infoList.size() > 0) {
                            Map<String, Object> infoMap = infoList.get(0);
                            MyApplication.userInfo = infoMap;
                            initViewDatas();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
    }

    public void getIntentDatas() {
        Intent intent = getIntent();
        moneyCount = intent.getStringExtra("momeyCount");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }

    private void uploadWX2Sevice(String url,HashMap<String, Object> hashMap) {
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        Map<String, String> params = httpRequestUtil.params;
        params.put("sex", hashMap.get("sex").toString());
        params.put("nickname", hashMap.get("nickname").toString());
        params.put("openid", hashMap.get("openid").toString());
        params.put("privilege", hashMap.get("privilege").toString());
        params.put("unionid", hashMap.get("unionid").toString());
        params.put("province", hashMap.get("province").toString());
        params.put("language", hashMap.get("language").toString());
        params.put("headimgurl", hashMap.get("headimgurl").toString());
        params.put("city", hashMap.get("city").toString());
        params.put("country", hashMap.get("country").toString());
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        if ((boolean) map.get("success")) {
                            updateUserInfo(Urls.CUST_INFO);
                        } else {
                            Toast.makeText(getApplicationContext(), map.get("message").toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
    }
}
