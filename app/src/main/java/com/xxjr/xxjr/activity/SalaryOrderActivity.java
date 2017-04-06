package com.xxjr.xxjr.activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.xxjr.xxjr.bean.ChoiceProduceBean;
import com.xxjr.xxjr.bean.SingleCityEvenbus;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.utils.TextUtil;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;
import com.ypy.eventbus.EventBus;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class SalaryOrderActivity extends SlidBackActivity implements View.OnClickListener {

    private Button mBtnCommit;
    private EditText mEdtUserName, mEdtIDCard, mEdtTel, mEdtMoneySum;
    private RelativeLayout mRlCity;
    private TextView mTvCityName;
    private String cityCode = "4403";
    private int loanId;
    private String loanType;
    private int applyId;
    private Intent intent1;
    /**
     * 确定类型
     */
    private TextView mTvProduceName;
    private String errorCode = "";
    private String produceName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_salary_order);
        initView();
        getIntentDatas();
        setListener();
        setTitleBack();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        mTvProduceName = (TextView) findViewById(R.id.SalaryOrder_tv_ChoiceProduce);
        mBtnCommit = (Button) findViewById(R.id.SalaryOrder_btn_Commit);
        mTvCityName = (TextView) findViewById(R.id.SalaryOrder_tv_cityName);
        mEdtUserName = (EditText) findViewById(R.id.SalaryOrder_edt_username);
        mEdtIDCard = (EditText) findViewById(R.id.SalaryOrder_edt_IDCard);
        mEdtTel = (EditText) findViewById(R.id.SalaryOrder_edt_Tel);
        mEdtMoneySum = (EditText) findViewById(R.id.SalaryOrder_edt_MoneySum);
        mRlCity = (RelativeLayout) findViewById(R.id.SalaryOrder_Rl_city);
    }

    public void setTitleBack() {
        LinearLayout mLlSave = (LinearLayout) findViewById(R.id.ThreeTitleBar_ll_click);
        SetTitleBar.setTitleText(SalaryOrderActivity.this, "完整交单", "查号");
        mLlSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addQuery(Urls.QUERY_RESULT);
            }
        });
    }

    private void setListener() {
        mBtnCommit.setOnClickListener(this);
        mRlCity.setOnClickListener(this);
        findViewById(R.id.SalaryOrder_Rl_ChoiceProduce).setOnClickListener(this);//选择产品.setOnClickListener(this);
    }

    /**
     * 对话框提交
     */
    private void DiaLogCommit(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SalaryOrderActivity.this);
        Dialog dialog = builder.create();
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.salary_order_dialog, null);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        dialog.getWindow().setContentView(view);
        setDialogBtnListener(view, dialog, message);//  todo  需要添加对话框
    }

    /**
     * 对话框按钮设置监听
     *
     * @param view
     */
    private void setDialogBtnListener(View view, final Dialog dialog, final String message) {
        Button mBtnRealName = (Button) view.findViewById(R.id.SalaryOrderDialog_btn_RealName);
        mBtnRealName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // 跳转
                startActivity(new Intent(getApplicationContext(), CustRealNameActivity.class));
            }
        });
    }

    //上传从公司的产品列表------>  完整交单
    public void uploadDatas(String url) {
        int inputMoney = Integer.parseInt(mEdtMoneySum.getText().toString());//限定输入金额
        AppUtil.showProgress(this, "正在加载数据，请稍候...");
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        Map<String, String> params = httpRequestUtil.params;
        params.put("borrower", mEdtUserName.getText().toString());
        params.put("cityCode", cityCode);
        params.put("cardNo", mEdtIDCard.getText().toString());
        params.put("telephone", mEdtTel.getText().toString());
        params.put("applyAmount", inputMoney + "");
        params.put("loanId", loanId + "");
        params.put("loanType", loanType);
        httpRequestUtil.jsonObjectRequestPostSuccess(
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> responseMap = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        Map<String, Object> applyUser = (Map<String, Object>) responseMap.get("attr");
                        Intent intent1 = new Intent(getApplicationContext(), SumbitUserMaterialActivity.class);
                        intent1.putExtra("applyId", applyId);
                        intent1.putExtra("loanId", loanId);
                        intent1.putExtra("from", "SalaryOrderActivity");
                        if (applyUser.isEmpty() == false) {//不为空的情况下才获取applyId
                            applyId = (int) applyUser.get("applyId");
                        }
                        boolean isSuccess = (Boolean) responseMap.get("success");
                        if (isSuccess) {
                            intentCompleteActivity("");
                        } else {
                            errorCode = TextUtil.getTextToString(responseMap.get("errorCode"));
                            if (errorCode.equals("98")) {
                                DiaLogCommit(responseMap.get("message").toString());
                            } else {
                                Intent intent = new Intent(getApplicationContext(), OrderResultFailActivity.class);
                                intent.putExtra("message", responseMap.get("message").toString());
                                startActivity(intent);
                            }
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

    /**
     * 上传-->首页的类型传过来的  老板交单，什么交单
     *
     * @throws Exception
     */


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.SalaryOrder_btn_Commit:
                if (mTvProduceName.getText().toString().equals("请选择产品")) {
                    Toast.makeText(SalaryOrderActivity.this, "请选择产品", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(mEdtUserName.getText().toString().trim())) {
                    Toast.makeText(SalaryOrderActivity.this, "请输入姓名", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(mEdtTel.getText().toString().trim())) {
                    Toast.makeText(SalaryOrderActivity.this, "请输入电话号码", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(mEdtMoneySum.getText().toString().trim())) {
                    Toast.makeText(SalaryOrderActivity.this, "请输入贷款金额", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(mEdtIDCard.getText().toString().trim())) {
                    Toast.makeText(SalaryOrderActivity.this, "请输入身份证号", Toast.LENGTH_SHORT).show();
                } else if (Double.parseDouble(mEdtMoneySum.getText().toString().trim()) > 1000 ||
                        Double.parseDouble(mEdtMoneySum.getText().toString().trim()) < 1) {
                    Toast.makeText(SalaryOrderActivity.this, "请输入限定的贷款金额", Toast.LENGTH_SHORT).show();
                } else {
                    uploadDatas(Urls.SALARY_ORDER);//产品列表--和完整交单  提交服务器
                }
                break;
            case R.id.SalaryOrder_Rl_city:
                intent.setClass(getApplicationContext(), CityActivity.class);
                intent.putExtra("fromFlag", "order");
                startActivity(intent);
                break;

            case R.id.SalaryOrder_Rl_ChoiceProduce:
                intent.setClass(getApplicationContext(), ChoiceProduceActivity.class);
                intent.putExtra("cityCode", cityCode);
                intent.putExtra("from", "SalaryOrderActivity");
                startActivity(intent);
                break;

        }
    }

    //获取城市名称
    public void onEventMainThread(SingleCityEvenbus singleCityEvenbus) {
        mTvCityName.setText(singleCityEvenbus.getCityName());
        cityCode = singleCityEvenbus.getCityCode();
        //先选城市   在选产品
        mTvProduceName.setText("请选择产品");//   城市相对应的产品，因此重新选择城市之后需要重新的更改产品
        loanId = -1;
    }

    //获取产品消息
    public void onEventMainThread(ChoiceProduceBean choiceProduceBean) {
        loanId = choiceProduceBean.getLoadId();
        loanType = choiceProduceBean.getLoanType();
        produceName = choiceProduceBean.getTitleName();
        mTvProduceName.setText(produceName);
    }

    //直接跳转到完整交单
    private void intentCompleteActivity(String message) {
        if (errorCode.equals("98")) {
            Intent intent = new Intent(getApplicationContext(), OrderResultFailActivity.class);
            intent.putExtra("message", message);
            startActivity(intent);
            errorCode = "0";
            return;
        } else {
            Intent intent1 = new Intent(getApplicationContext(), SumbitUserMaterialActivity.class);
            intent1.putExtra("applyId", applyId);
            intent1.putExtra("loanId", loanId);
            intent1.putExtra("commitType", ConstantUtils.COMMIT_RESULT_SIMPLE);
            intent1.putExtra("produceType", produceName);
            intent1.putExtra("titleName", produceName);
            intent1.putExtra("from", "SalaryOrderActivity");
            startActivity(intent1);
        }
    }

    public void getIntentDatas() {
        intent1 = getIntent();
        loanId = intent1.getIntExtra("loadId", -1);
        loanType = intent1.getStringExtra("loanType");
        String prgName = intent1.getStringExtra("prgName");
        if (prgName!=null) {
            mTvProduceName.setText(prgName);
        }
    }

    /**
     * 判断是否有查号过的
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

}
