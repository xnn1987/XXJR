package com.xxjr.xxjr.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
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

public class CustPerformanceActivity extends SlidBackActivity implements View.OnClickListener {

    private RelativeLayout mRlResidue, mRlReturn, mRlPrentice, mRlConnact, mRlWithdrawDetail;
    private String returnMoneyCount = "0";
    private String prenticeCommissionCount = "0";
    private String TAG = "CustPerformanceActivity";
    private String SeviceTel = "4001892600";
    private TextView mTvMoneyCoun;
    private String moneyCount ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_performance);
        initview();
        setListener();
        init();
    }

    private void init() {
        AppUtil.showProgress(this, ConstantUtils.DIALOG_SHOW);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil(this);
        httpRequestUtil.jsonObjectRequestPostSuccess("/account/reward/summary",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, response.toString());
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        Map<String, Object> infoMap = (Map<String, Object>) map.get("attr");
                        if (infoMap.size() != 0) {
                            Log.e("个人中心", infoMap.toString());
                            TextView allRewardText = (TextView) CustPerformanceActivity.this.findViewById(R.id.cust_summary_allReward);
                            allRewardText.setText(infoMap.get("allReward").toString());

                            //TODO wqg修改
                            moneyCount = "￥" + infoMap.get("usableAmount").toString();
                            mTvMoneyCoun.setText(moneyCount);

                            TextView myRewardText = (TextView) CustPerformanceActivity.this.findViewById(R.id.cust_summary_myReward);
                            returnMoneyCount = "￥" + infoMap.get("myReward").toString();
                            myRewardText.setText(returnMoneyCount);

                            TextView parentRewardText = (TextView) CustPerformanceActivity.this.findViewById(R.id.cust_summary_parentReward);
                            prenticeCommissionCount = "￥" + infoMap.get("parentReward").toString();
                            parentRewardText.setText(prenticeCommissionCount);
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

    private void setListener() {
        mRlResidue.setOnClickListener(this);
        mRlReturn.setOnClickListener(this);
        mRlPrentice.setOnClickListener(this);
        mRlConnact.setOnClickListener(this);
        mRlWithdrawDetail.setOnClickListener(this);
    }

    private void initview() {
        SetTitleBar.setTitleText(CustPerformanceActivity.this, "我的业绩");
        mRlResidue = (RelativeLayout) findViewById(R.id.CustPerfomance_rl_Residue);
        mRlReturn = (RelativeLayout) findViewById(R.id.CustPerfomance_rl_Return);
        mRlPrentice = (RelativeLayout) findViewById(R.id.CustPerfomance_rl_prentice);
        mRlConnact = (RelativeLayout) findViewById(R.id.CustPerfomance_rl_connact);
        mRlWithdrawDetail = (RelativeLayout) findViewById(R.id.CustPerfomance_rl_withdrawDetail);
        mTvMoneyCoun = (TextView) CustPerformanceActivity.this.findViewById(R.id.cust_summary_useableamount);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.CustPerfomance_rl_Residue:
                //TODO 判断是否实名--->>
                if (MyApplication.userInfo.get("cardNo") != null && !MyApplication.userInfo.get("cardNo").equals("")) {
                    intent.putExtra("momeyCount",moneyCount);
                    intent.setClass(getApplicationContext(), WithdrawActivity.class);
                    startActivity(intent);
                } else {
                    //实名对话框
                    dialogRealName();
                }
                break;
            case R.id.CustPerfomance_rl_Return:
                intent.setClass(getApplicationContext(), ReturnCommissionActivity.class);
                intent.putExtra("returnMoneyCount", returnMoneyCount);
                startActivity(intent);
                break;
            case R.id.CustPerfomance_rl_prentice:
                intent.setClass(getApplicationContext(), PrenticeCommisionActivity.class);
                intent.putExtra("prenticeCommissionCount", prenticeCommissionCount);
                startActivity(intent);
                break;
            case R.id.CustPerfomance_rl_withdrawDetail:
                intent.setClass(getApplicationContext(), WithdrawDetaiActivity.class);
                startActivity(intent);
                break;
            case R.id.CustPerfomance_rl_connact:
                Intent intent2 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + SeviceTel));//拨打客服电话
                startActivity(intent2);
                break;
        }
    }

    /**
     * 实名认证判断
     */
    private void dialogRealName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CustPerformanceActivity.this);
        Dialog dialog = builder.create();
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_real_name, null);

        setDialogListener(view, dialog);

        dialog.show();
        dialog.getWindow().setContentView(view);
    }

    /**
     * 对对话框按钮监听
     */
    private void setDialogListener(View view, final Dialog dialog) {
        Button mBtnNo = (Button) view.findViewById(R.id.reanNname_btn_DialogNO);
        Button mBtnRealName = (Button) view.findViewById(R.id.reanNname_btn_Dialog_realname);
        mBtnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(CustPerformanceActivity.this, "没有实名没有办法提现", Toast.LENGTH_LONG).show();
            }
        });
        mBtnRealName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO  对话框
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), CustRealNameActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        init();
    }


}
