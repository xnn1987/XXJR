package com.xxjr.xxjr.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.bean.SingleCityEvenbus;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.utils.TextUtil;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.Shared.SocialUmeng;
import com.xxjr.xxjr.utils.common.SlidBackActivity;
import com.ypy.eventbus.EventBus;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class SimpleOrderActivity extends SlidBackActivity implements View.OnClickListener {
    private Button mBtnCommit;
    private EditText mEdtUserName, mEdtRandomNo, mEdtTel, mEdtMoneySum,mEtCradNo;
    private RelativeLayout mRlCity;
    private TextView mTvCityName;
    private String cityCode = "4403";
    private int applyId;
    private String titleName = "简单交单";
    private int recLen = 60;
    private Timer timer = null;
    private Button mBtnRandomNo;
    private LinearLayout mLlNote;
    private LinearLayout mLlWeiXin;
    private String errorCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_simple_order);
        initView();
        setListener();
        setTitleBack();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //获取城市名称
    public void onEventMainThread(SingleCityEvenbus singleCityEvenbus) {
        mTvCityName.setText(singleCityEvenbus.getCityName());
        cityCode = singleCityEvenbus.getCityCode();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.Simple_Rl_city:
                intent.setClass(getApplicationContext(), CityActivity.class);
                intent.putExtra("fromFlag", "order");
                startActivity(intent);
                break;
            case R.id.Simple_btn_randomNo:
                getRegisterCode(mEdtTel.getText().toString(), mBtnRandomNo);
                break;
            case R.id.Simple_ll_duanxin://短信交单
                if (TextUtils.isEmpty(mEdtUserName.getText().toString().trim())) {
                    Toast.makeText(SimpleOrderActivity.this, "请输入姓名", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(mEdtTel.getText().toString().trim())) {
                    Toast.makeText(SimpleOrderActivity.this, "请输入电话号码", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(mEdtMoneySum.getText().toString().trim())) {
                    Toast.makeText(SimpleOrderActivity.this, "请输入贷款金额", Toast.LENGTH_SHORT).show();
                } else if (Double.parseDouble(mEdtMoneySum.getText().toString().trim()) > 1000 ||
                        Double.parseDouble(mEdtMoneySum.getText().toString().trim()) < 1) {
                    Toast.makeText(SimpleOrderActivity.this, "请输入贷款金额", Toast.LENGTH_SHORT).show();
                } else {
                    msgCommitOrder(Urls.MSG_COMMIT_ORDER);
                }
                break;
            case R.id.Simple_ll_weixinfenxiang://微信分享
                SocialUmeng.SharedWeiXin(this, "小小金融贷款申请","", "/app/share/applyLoan?uid=" + MyApplication.uid, null);

                break;
            case R.id.Simple_btn_Commit://提交
                if (TextUtils.isEmpty(mEdtUserName.getText().toString().trim())) {
                    Toast.makeText(SimpleOrderActivity.this, "请输入姓名", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(mEdtTel.getText().toString().trim())) {
                    Toast.makeText(SimpleOrderActivity.this, "请输入电话号码", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(mEdtRandomNo.getText().toString().trim())) {
                    Toast.makeText(SimpleOrderActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(mEdtMoneySum.getText().toString().trim())) {
                    Toast.makeText(SimpleOrderActivity.this, "请输入贷款金额", Toast.LENGTH_SHORT).show();
                } else if (Double.parseDouble(mEdtMoneySum.getText().toString().trim()) > 1000 ||
                        Double.parseDouble(mEdtMoneySum.getText().toString().trim()) < 1) {
                    Toast.makeText(SimpleOrderActivity.this, "请输入限定的贷款金额", Toast.LENGTH_SHORT).show();
                } else {
                    uploadHomeDatas(Urls.SALARY_ORDER);//从首页进去的简单交单
                }
                break;
        }
    }

    public void setTitleBack() {
        LinearLayout mLlSave = (LinearLayout) findViewById(R.id.ThreeTitleBar_ll_click);
        SetTitleBar.setTitleText(SimpleOrderActivity.this, titleName, "查号");
        mLlSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addQuery(Urls.QUERY_RESULT);//  todo  需啊哟查号
            }
        });
    }

    private void initView() {
        mBtnCommit = (Button) findViewById(R.id.Simple_btn_Commit);
        mTvCityName = (TextView) findViewById(R.id.Simple_tv_cityName);
        mEdtUserName = (EditText) findViewById(R.id.Simple_edt_username);
        mEdtRandomNo = (EditText) findViewById(R.id.Simple_edt_randomNo);
        mBtnRandomNo = (Button) findViewById(R.id.Simple_btn_randomNo);
        mEdtTel = (EditText) findViewById(R.id.Simple_edt_Tel);
        mEtCradNo = (EditText) findViewById(R.id.Simple_edt_cardNo);
        mEdtMoneySum = (EditText) findViewById(R.id.Simple_edt_MoneySum);
        mRlCity = (RelativeLayout) findViewById(R.id.Simple_Rl_city);
        mLlNote = (LinearLayout) findViewById(R.id.Simple_ll_duanxin);
        mLlWeiXin = (LinearLayout) findViewById(R.id.Simple_ll_weixinfenxiang);
    }

    private void setListener() {
        mBtnCommit.setOnClickListener(this);
        mBtnRandomNo.setOnClickListener(this);
        mLlNote.setOnClickListener(this);
        mLlWeiXin.setOnClickListener(this);
        mRlCity.setOnClickListener(this);
    }
    /**
     * 对话框提交
     */
    private void DiaLogCommit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SimpleOrderActivity.this);
        Dialog dialog = builder.create();
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.salary_order_dialog, null);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        dialog.getWindow().setContentView(view);
        setDialogBtnListener(view, dialog);//todo 需啊哟添加对话框
    }

    /**
     * 对话框按钮设置监听
     *
     * @param view
     */
    private void setDialogBtnListener(View view, final Dialog dialog) {
        Button mBtnRealName = (Button) view.findViewById(R.id.SalaryOrderDialog_btn_RealName);
        mBtnRealName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(getApplicationContext(), CustRealNameActivity.class));
            }
        });
    }

    /**
     * 上传-->首页的类型传过来的  老板交单，什么交单
     *
     * @param url
     * @throws Exception
     */
    public void uploadHomeDatas(String url) {
        AppUtil.showProgress(this,ConstantUtils.DIALOG_SHOW);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        Map<String, String> params = httpRequestUtil.params;
        params.put("borrower", mEdtUserName.getText().toString());
        params.put("cityCode", cityCode);
        params.put("randomNo", mEdtRandomNo.getText().toString());
        params.put("telephone", mEdtTel.getText().toString());
        params.put("applyAmount", mEdtMoneySum.getText().toString());
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> responseMap = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        Map<String, Object> applyUser = (Map<String, Object>) responseMap.get("attr");
                        if (applyUser.isEmpty() == false) {//不为空的情况下才获取applyId
                            applyId = (int) applyUser.get("applyId");
                            Log.e("applyId==", applyId + "");
                        }
                        errorCode = TextUtil.getTextToString(responseMap.get("errorCode"));
                        if (errorCode.equals("98")) {
                            DiaLogCommit();
                        } else {
                            Intent intent = new Intent(getApplicationContext(), OrderResultFailActivity.class);
                            intent.putExtra("message", responseMap.get("message").toString());
                            startActivity(intent);
                        }
                        AppUtil.dismissProgress();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AppUtil.errodDoanload(ConstantUtils.DIALOG_ERROR);
                    }
                }
        );

    }
    /**
     * 判断是否有查号过的
     * @param url
     */
    private void addQuery(String url) {
        AppUtil.showProgress(this, "正在查询，请稍后...");
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        List<Map<String, Object>> rowsList = (List<Map<String, Object>>) map.get("rows");
                        Intent intent = new Intent();
                        if (rowsList.size() == 0) {
                            intent.setClass(getApplicationContext(), CheckNumberActivity.class);
                            startActivity(intent);
                        } else {
                            intent.setClass(getApplicationContext(), QueryResultActivity.class);
                            startActivity(intent);
                        }
                        AppUtil.dismissProgress();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AppUtil.errodDoanload("网络不稳定,无法为您查号");
                    }
                });
    }

    /**
     * 短信验证
     * @param telephone
     * @param btn
     */
    private void getRegisterCode(String telephone, final Button btn) {
        if (telephone.trim().length() == 11) {
            AppUtil.showProgress(SimpleOrderActivity.this, "正在发送验证码，请稍后...");
            HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
            httpRequestUtil.params.put("telephone", telephone);
            httpRequestUtil.jsonObjectRequestPostSuccess("/smsAction/nologin/simpleSubmit", new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Map<String, Object> msgMap = (Map<String, Object>) JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                            boolean success = (Boolean) msgMap.get("success");
                            timer = new Timer();
                            AppUtil.dismissProgress();
                            if (success) {
                                btn.setClickable(false);
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
                                                    btn.setText("重新获取验证码");
                                                    btn.setClickable(true);
                                                    timer.cancel();
                                                }
                                            }
                                        });
                                    }
                                };
                                timer.schedule(task, 1000, 1000);
                            } else {
                                Toast.makeText(getApplicationContext(), msgMap.get("message").toString(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            AppUtil.errodDoanload("网络不给力");
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "请输入正确的验证码", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 短信交单
     */
    private void msgCommitOrder(String url) {
        AppUtil.showProgress(SimpleOrderActivity.this, "正在发送，请稍后...");
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.params.put("borrower", mEdtUserName.getText().toString());
        httpRequestUtil.params.put("cityCode", cityCode);
        httpRequestUtil.params.put("telephone", mEdtTel.getText().toString());
        httpRequestUtil.params.put("applyAmount", mEdtMoneySum.getText().toString());
        httpRequestUtil.jsonObjectRequestPostSuccess(url, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> msgMap = (Map<String, Object>) JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        boolean success = (Boolean) msgMap.get("success");
                        AppUtil.dismissProgress();
                        if (success) {
                            Toast.makeText(SimpleOrderActivity.this, "已发送至" + mEdtTel.getText().toString(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SimpleOrderActivity.this, msgMap.get("message").toString(), Toast.LENGTH_SHORT).show();
                        }
                        AppUtil.dismissProgress();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AppUtil.errodDoanload("网络不给力");
                    }
                });
    }

}
