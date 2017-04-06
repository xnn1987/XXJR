package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.other.luckyPan.LuckyPanView;
import com.xxjr.xxjr.utils.DebugLog;
import com.xxjr.xxjr.utils.TextUtil;
import com.xxjr.xxjr.utils.common.CommomAcitivity;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.network.MapCallback;
import com.zhy.http.okhttp.OkHttpUtils;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;

public class SignRobOrderActivity extends CommomAcitivity {
    private LuckyPanView mLuckyPanView;
    private int recLen;
    private TextView mTvDayCount, mTvJiFen;
    private TextView mTvSign;
    private Integer hasSignin = 0;
    private final int INIT_UPDATE = 111;
    private Timer timer;
    private String[] mStrs = new String[]{"50积分", "100积分",
            "200积分", "500积分", "贷款客户", "5积分"};
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ConstantUtils.LOADING:
                    AppUtil.showProgress(SignRobOrderActivity.this, ConstantUtils.DIALOG_SHOW);
                    break;
                case ConstantUtils.LOAD_SUCCESS:

                    AppUtil.dismissProgress();

                    break;
                case ConstantUtils.LOAD_ERROR:
                    AppUtil.errodDoanload(ConstantUtils.DIALOG_ERROR);

                    break;
                case INIT_UPDATE:
                    downInit(Urls.SIGN_INIT);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_rob_order);

        mTvDayCount = (TextView) findViewById(R.id.signRob_tv_dayCount);
        mTvSign = (TextView) findViewById(R.id.signRob_tv_qiandao);
        mTvJiFen = (TextView) findViewById(R.id.signRob_tv_jifen);


        mLuckyPanView = (LuckyPanView) findViewById(R.id.signRob_lp_pan);

        downInit(Urls.SIGN_INIT);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 签到
     *
     * @param view
     */
    public void onClickSign(View view) {
        if (hasSignin == 0) {
            signUpload(Urls.SIGN_SIGN);
        }
    }

    /**
     * 抽奖
     *
     * @param view
     */
    public void onClickChouJiang(View view) {
        if (!mLuckyPanView.isStart()) {
            downChouJiang(Urls.RANQING + Urls.SIGN_CHOU_JIANG);
        }
    }

    /**
     * 返回
     *
     * @param view
     */
    public void onClickBack(View view) {
        finish();
    }

    /**
     * 初始化
     *
     * @param url
     */
    private void downInit(String url) {
        handler.sendEmptyMessage(ConstantUtils.LOADING);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil(this);
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        DebugLog.e("sign  init-", response.toString());
                        Map attrMap = (Map) map.get("attr");
                        setInitViewData(attrMap);
                        handler.sendEmptyMessage(ConstantUtils.LOAD_SUCCESS);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.sendEmptyMessage(ConstantUtils.LOAD_ERROR);
                    }
                });
    }

    /**
     * 签到
     *
     * @param url
     */
    private void signUpload(String url) {
        handler.sendEmptyMessage(ConstantUtils.LOADING);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil(this);
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        DebugLog.e("sign  签到-", response.toString());
                        if ((Boolean) map.get("success")) {
                            Map<String, Object> attrMap = (Map<String, Object>) map.get("attr");
                            hasSignin = 1;
                            mTvDayCount.setText(TextUtil.getTextToString(attrMap.get("signinDays").toString()));
                            signinJudge();
                        } else {
                            hasSignin = 0;
                            Toast.makeText(SignRobOrderActivity.this, "签到不成功", Toast.LENGTH_SHORT).show();
                        }

                        handler.sendEmptyMessage(ConstantUtils.LOAD_SUCCESS);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.sendEmptyMessage(ConstantUtils.LOAD_ERROR);
                    }
                });
    }

    /**
     * 抽奖
     *
     * @param url
     */
    private void downChouJiang(String url) {
        handler.sendEmptyMessage(ConstantUtils.LOADING);
        OkHttpUtils
                .post()
                .url(url)
                .addParams("uid", MyApplication.uid)
                .addParams("UUID", MyApplication.device)
                .build()
                .execute(new MapCallback(this, false) {
                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(SignRobOrderActivity.this, "抽奖不成功", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onResponse( Map map) {
                        super.onResponse(map);
                        handler.sendEmptyMessage(ConstantUtils.LOAD_SUCCESS);
                        if (map != null) {
                            DebugLog.e("抽奖    成功", map.toString());
                            final Map attrMap = (Map) map.get("attr");
//                            String lotteryName = attrMap.get("lotteryName").toString();//奖品名称
//                            final String finalLotteryName = lotteryName;
                            handler.sendEmptyMessage(ConstantUtils.LOAD_SUCCESS);
                            for (int i = 0; i < mStrs.length; i++) {
                                if (attrMap.get("lotteryName").toString().equals(mStrs[i])) {
                                    mLuckyPanView.luckyStart(i);
                                    recLen = 3;
                                    TimerTask task = new TimerTask() {
                                        @Override
                                        public void run() {
                                            runOnUiThread(new Runnable() {      // UI thread
                                                @Override
                                                public void run() {
                                                    recLen--;
                                                    if (recLen == 0) {
                                                        mLuckyPanView.luckyEnd();
                                                        timer.cancel();
                                                        handler.postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                handler.sendEmptyMessage(INIT_UPDATE);
                                                                Toast.makeText(SignRobOrderActivity.this, "抽到了" +attrMap.get("lotteryName").toString(), Toast.LENGTH_SHORT).show();
                                                                return;
                                                            }
                                                        }, 4000);
                                                    }
                                                }
                                            });
                                        }
                                    };
                                    timer = new Timer();
                                    timer.schedule(task, 1000, 1000);
                                }

                            }
                        }
                    }

                });

    }

    private void setInitViewData(Map attrMap) {
        mTvJiFen.setText(attrMap.get("totalScore").toString());
        mTvDayCount.setText(attrMap.get("signinDays").toString());
        mTvSign.setText(attrMap.get("hasSignin").toString());
        hasSignin = Integer.parseInt(attrMap.get("hasSignin").toString());
        signinJudge();
    }

    //    签到  的判断
    private void signinJudge() {
        if (hasSignin == 0) {
            mTvSign.setText("立即签到");
            mTvSign.setClickable(true);
        } else {
            mTvSign.setText("已经签到");
            mTvSign.setClickable(false);
        }
    }

    public void onClickIntegral(View view) {
        Intent intent = new Intent(getApplicationContext(), SignRobIntegralActivity.class);
        startActivity(intent);
    }

}
