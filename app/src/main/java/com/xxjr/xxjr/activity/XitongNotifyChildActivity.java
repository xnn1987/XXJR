package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.bean.SeeStatus;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.utils.DebugLog;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;
import com.ypy.eventbus.EventBus;

import org.json.JSONObject;

import java.util.Map;

public class XitongNotifyChildActivity extends SlidBackActivity {

    private TextView mTvTitle;
    private TextView mTvTime;
    private TextView mTvConten;
    private String messageId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xitong_notify_child);
        SetTitleBar.setTitleText(XitongNotifyChildActivity.this,"系统通知");
        initViews();
        getIntentDatas();
        updataSeeStatus(Urls.MSG_XITONG_NOTIFY_CHILD_SEE);
    }


    private void initViews() {
        mTvTitle = (TextView) findViewById(R.id.XitongNotifyChild_tv_title);
        mTvTime = (TextView) findViewById(R.id.XitongNotifyChild_tv_time);
        mTvConten = (TextView) findViewById(R.id.XitongNotifyChild_tv_conten);
    }


    public void getIntentDatas() {
        Intent initent = getIntent();
        String title = initent.getStringExtra("subject");
        String content = initent.getStringExtra("content");
        String sendTime = initent.getStringExtra("sendTime");
        messageId = initent.getStringExtra("messageId");
        initViewDatas(title, content, sendTime);
    }
    //初始化控件
    private void initViewDatas(String title,String content,String sendTime){
        mTvTitle.setText(title);
        mTvConten.setText(content);
        mTvTime.setText("发件时间: " + sendTime);
    }
    private void updataSeeStatus(String url) {
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        Map<String, String> params = httpRequestUtil.params;
        params.put("messageId", messageId);
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        sendSeeStatus();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        DebugLog.e("更新失败","失败");
                    }
                });
    }

    private void sendSeeStatus(){
        SeeStatus seeStatus = new SeeStatus();
        seeStatus.setIsSee(true);
        EventBus.getDefault().post(seeStatus);
    }
}
