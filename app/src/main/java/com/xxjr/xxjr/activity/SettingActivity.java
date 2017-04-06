package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.bean.DestroyMainActivityEvenbus;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.ChacheUtil;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.SharedPrefUtil;
import com.xxjr.xxjr.utils.common.SlidBackActivity;
import com.ypy.eventbus.EventBus;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

import java.util.Map;

import cn.jpush.android.api.JPushInterface;

public class SettingActivity extends SlidBackActivity implements View.OnClickListener {


    private Button mBtnExit;
    private LinearLayout mLlFeedback, mLlAboutXXJR;

    public LinearLayout modifyLoginPwdButton = null;
    private LinearLayout mLlPush,mLlCache;
    private TextView mTvCache;
    private ImageView mIvOpenPush, mIvClosePush;
    private boolean isPush;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        SetTitleBar.setTitleText(SettingActivity.this, "设置");
        initViews();
        initViewData(mTvCache);//缓存
        setListener();
        judgePush();//判断有没有极光推送
    }



    private void initViews() {
        mLlFeedback = (LinearLayout) findViewById(R.id.Setting_ll_feedback);
        mLlAboutXXJR = (LinearLayout) findViewById(R.id.Setting_ll_AboutXXJR);
        mLlPush = (LinearLayout) findViewById(R.id.Setting_ll_open);
        mIvOpenPush = (ImageView) findViewById(R.id.Setting_iv_open);
        mIvClosePush = (ImageView) findViewById(R.id.Setting_iv_close);
        mLlCache = (LinearLayout) findViewById(R.id.Setting_LL_Cache);
        mTvCache = (TextView) findViewById(R.id.Setting_tv_cache);


        mBtnExit = (Button) findViewById(R.id.Setting_btn_exit);
        modifyLoginPwdButton = (LinearLayout) this.findViewById(R.id.modify_login_pwd);

    }

    /**
     * 初始化控件数据
     */
    private void initViewData(TextView mTvCache) {
        String cache ;
        try {
            cache = ChacheUtil.getCacheSize(getApplicationContext().getExternalCacheDir());
            mTvCache.setText(cache);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void setListener() {
        mBtnExit.setOnClickListener(this);
        mLlFeedback.setOnClickListener(this);
        mLlAboutXXJR.setOnClickListener(this);
        modifyLoginPwdButton.setOnClickListener(this);
        mLlPush.setOnClickListener(this);
        mLlCache.setOnClickListener(this);
    }

    /**
     * 判断有没有极光推送
     */
    private void judgePush(){
        SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(getApplicationContext(),ConstantUtils.SP_JPUSH_NAME);
        isPush = sharedPrefUtil.getBoolean(ConstantUtils.SP_JPUSH_OPEN_STATUS,true);
        if (isPush) {
            mIvClosePush.setVisibility(View.GONE);
            mIvOpenPush.setVisibility(View.VISIBLE);
            JPushInterface.resumePush(getApplication());
        } else {

            mIvClosePush.setVisibility(View.VISIBLE);
            mIvOpenPush.setVisibility(View.GONE);
            JPushInterface.stopPush(getApplication());
        }
    }

    /**
     * 写入是否要推送
     * @param isPush
     */
    private void writePush(boolean isPush){
        SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(getApplicationContext(),ConstantUtils.SP_JPUSH_NAME);
        sharedPrefUtil.putBoolean(ConstantUtils.SP_JPUSH_OPEN_STATUS,isPush);
        sharedPrefUtil.commit();
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.Setting_btn_exit:
                AppUtil.showProgress(this, "正在退出，请稍候...");
                HttpRequestUtil httpRequestUtil = new HttpRequestUtil(this);
                httpRequestUtil.jsonObjectRequestPostSuccess("/cust/logout",
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                                boolean success = (Boolean) map.get("success");
                                if (success) {
                                    SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(getApplicationContext(),"user");
                                    sharedPrefUtil.clear();
                                    MyApplication.uid = null;
                                    MyApplication.userInfo = null;
                                    MyApplication.userQuanJu = null;

                                    DestroyMainActivityEvenbus destroyMainActivityEvenbus = new DestroyMainActivityEvenbus();
                                    destroyMainActivityEvenbus.setIsExit(true);
                                    EventBus.getDefault().post(destroyMainActivityEvenbus);
                                    Toast.makeText(SettingActivity.this, "退出登录", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent();
                                    intent.setClass(getApplicationContext(), LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    Toast.makeText(getApplicationContext(),map.get("message").toString(),Toast.LENGTH_LONG).show();
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
                break;
            case R.id.Setting_ll_feedback:
                intent.setClass(getApplicationContext(), SetFeedBackActivity.class);
                startActivity(intent);
                break;
            case R.id.Setting_ll_AboutXXJR:
                intent.setClass(getApplicationContext(), AboutXXJRActivity.class);
                startActivity(intent);
                break;
            case R.id.modify_login_pwd:
                intent.setClass(getApplicationContext(), CustModifyPwdActivity.class);
                startActivity(intent);
                break;
            case R.id.Setting_ll_open:
                if (isPush) {
                    writePush(false);//不在推送
                } else {
                    writePush(true);//推送
                }
                judgePush();//极光推送状态的改变
                break;
            case R.id.Setting_LL_Cache:
                ChacheUtil.cleanApplicationData(getApplicationContext());
                initViewData(mTvCache);
                break;
        }
    }
}
