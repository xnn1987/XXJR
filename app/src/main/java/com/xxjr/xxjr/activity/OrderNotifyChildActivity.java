package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.adapter.OrderNotifyChildAdapter;
import com.xxjr.xxjr.bean.SeeStatus;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.custview.Pull2RefreshListView;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;
import com.ypy.eventbus.EventBus;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderNotifyChildActivity extends SlidBackActivity implements View.OnClickListener, Pull2RefreshListView.OnRefreshListener, Pull2RefreshListView.OnLoadMoreListener {

    private Button mBtnOrderDtl;
    private Pull2RefreshListView mWLvFeedback;
    private boolean isFirst = true;
    private int currentPage = 0;
    private List<Map<String,Object>> mList = new ArrayList<>();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantUtils.LOADING:
                    if (isFirst) {
                        AppUtil.showProgress(OrderNotifyChildActivity.this, ConstantUtils.DIALOG_SHOW);
                        isFirst = false;
                    }
                    break;
                case ConstantUtils.LOAD_SUCCESS:
                    AppUtil.dismissProgress();
                    handler.sendEmptyMessage(ConstantUtils.STOP_FRESH);
                    sendSeeStatus();
                    break;
                case ConstantUtils.LOAD_ERROR:
                    AppUtil.errodDoanload(ConstantUtils.DIALOG_ERROR);
                    handler.sendEmptyMessage(ConstantUtils.STOP_FRESH);
                    break;
                case ConstantUtils.FOOT_REFRESH:
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            currentPage = 0;
                            mList.clear();
                            downloadDatas(Urls.MSG_ORDER_NOTIFY_CHILD);
                        }
                    }, 2000);
                    break;
                case ConstantUtils.HEAD_REFRESH:
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            currentPage = 0;
                            mList.clear();
                            downloadDatas(Urls.MSG_ORDER_NOTIFY_CHILD);
                        }
                    }, 2000);
                    break;
                case ConstantUtils.STOP_FRESH:
                    mWLvFeedback.stopAllRefresh();
                    break;
            }
        }
    };
    private OrderNotifyChildAdapter adapter;
    private String applyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_notify_child);
        SetTitleBar.setTitleText(OrderNotifyChildActivity.this, "交单通知");
        getIntentDatas();
        initView();
        setListener();
        setAdapter();
        downloadDatas(Urls.MSG_ORDER_NOTIFY_CHILD);
    }

    private void setAdapter() {
        adapter = new OrderNotifyChildAdapter(getApplicationContext(),mList);
        mWLvFeedback.setAdapter(adapter);
    }

    private void initView() {
        mWLvFeedback = (Pull2RefreshListView) findViewById(R.id.orderNotifyChile_lv);
        mBtnOrderDtl = (Button) findViewById(R.id.orderNotifyChile_btn_fast1);
    }

    private void setListener() {
        mBtnOrderDtl.setOnClickListener(this);
        mWLvFeedback.setOnRefreshListener(this);
        mWLvFeedback.setOnLoadListener(this);
    }

    private void downloadDatas(String url){
        currentPage++;
        handler.sendEmptyMessage(ConstantUtils.LOADING);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        Map<String, String> params = httpRequestUtil.params;
        params.put("currentPage",currentPage+"");
        params.put("applyId", applyId);
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        if ((boolean) map.get("success")) {
                            List<Map<String, Object>> rowList = (List<Map<String, Object>>) map.get("rows");
                            if (rowList.size() == 0) {
                                currentPage--;
                                Toast.makeText(OrderNotifyChildActivity.this, ConstantUtils.NO_ANYTHING_DATA, Toast.LENGTH_SHORT).show();
                                return;
                            }
                            mList.addAll(rowList);
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(OrderNotifyChildActivity.this, map.get("message").toString(), Toast.LENGTH_SHORT).show();
                        }
                        handler.sendEmptyMessage(ConstantUtils.LOAD_SUCCESS);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        currentPage--;
                        handler.sendEmptyMessage(ConstantUtils.LOAD_ERROR);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.orderNotifyChile_btn_fast1:
                Intent intent = new Intent(getApplicationContext(),OrderDetailActivity.class);
                intent.putExtra("applyId",Integer.parseInt(applyId));
                startActivity(intent);
                break;
        }
    }

    public void getIntentDatas() {
        Intent intent = getIntent();
        applyId = intent.getStringExtra("applyId");
    }

    private void sendSeeStatus(){
        SeeStatus seeStatus = new SeeStatus();
        seeStatus.setIsSee(true);
        EventBus.getDefault().post(seeStatus);
    }

    @Override
    public void onRefresh() {
        handler.sendEmptyMessage(ConstantUtils.FOOT_REFRESH);
    }

    @Override
    public void onLoadMore() {
        handler.sendEmptyMessage(ConstantUtils.FOOT_REFRESH);
    }
}
