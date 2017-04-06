package com.xxjr.xxjr.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.utils.DebugLog;
import com.xxjr.xxjr.utils.SharedPrefUtil;
import com.xxjr.xxjr.utils.common.CommomAcitivity;
import cn.jpush.android.api.JPushInterface;


public class InitActivity extends CommomAcitivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("isFirst", Context.MODE_PRIVATE);
                if (preferences.getBoolean("firststart", true)) {
                    editFirstLogin(preferences);
                    intent2ViewPagerActivity();
                } else {
                    intent2MainActivity();
                }
            }
        }, 3000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        judgePush();
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    //是否第一次登录
    private void editFirstLogin(SharedPreferences preferences){
        SharedPreferences.Editor edit = preferences.edit();
        edit.putBoolean("firststart", false);
        edit.commit();
    }

    private void intent2ViewPagerActivity(){
        Intent intent = new Intent(getApplicationContext(), ViewPagerActivity.class);
        startActivity(intent);
        finish();
    }


    // 跳转主函数
    private void intent2MainActivity(){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 判断有没有极光推送
     */
    private void judgePush() {
        SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(getApplicationContext(), ConstantUtils.SP_JPUSH_NAME);
        boolean isPush = sharedPrefUtil.getBoolean(ConstantUtils.SP_JPUSH_OPEN_STATUS,true);
        if (!isPush) {
            JPushInterface.stopPush(getApplication());
        }
    }

}