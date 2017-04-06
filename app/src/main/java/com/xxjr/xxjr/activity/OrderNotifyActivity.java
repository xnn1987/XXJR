package com.xxjr.xxjr.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.adapter.OrderNotifyAdapter;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.custview.Pull2RefreshListView;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderNotifyActivity extends SlidBackActivity implements Pull2RefreshListView.OnRefreshListener, Pull2RefreshListView.OnLoadMoreListener {

    private Pull2RefreshListView mWlvOrder;
    private int currentPage = 0;
    private List<Map<String,Object>> mList = new ArrayList<>();
    private OrderNotifyAdapter adapter;
    private boolean isFirst = true;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case ConstantUtils.LOADING:
                    if (isFirst) {
                        AppUtil.showProgress(OrderNotifyActivity.this, ConstantUtils.DIALOG_SHOW);
                        isFirst = false;
                    }
                    break;
                case ConstantUtils.LOAD_SUCCESS:
                    AppUtil.dismissProgress();
                    handler.sendEmptyMessage(ConstantUtils.STOP_FRESH);
                    break;
                case ConstantUtils.LOAD_ERROR:
                    AppUtil.errodDoanload(ConstantUtils.DIALOG_ERROR);
                    handler.sendEmptyMessage(ConstantUtils.STOP_FRESH);
                    break;
                case ConstantUtils.FOOT_REFRESH:
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            downloadDatas(Urls.MSG_ORDER_NOTIFY);
                        }
                    }, 1000);
                    break;
                case ConstantUtils.HEAD_REFRESH:

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            currentPage = 0;
                            mList.clear();
                            downloadDatas(Urls.MSG_ORDER_NOTIFY);
                        }
                    }, 1000);
                    break;
                case ConstantUtils.STOP_FRESH:
                    mWlvOrder.stopAllRefresh();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_order_notify);
        SetTitleBar.setTitleText(OrderNotifyActivity.this,  "交单通知");
        initViews();
        rvSetAdapter();
        downloadDatas(Urls.MSG_ORDER_NOTIFY);

    }

    private void rvSetAdapter() {
        adapter = new OrderNotifyAdapter(getApplicationContext(),mList,mWlvOrder);
        mWlvOrder.setAdapter(adapter);
    }

    private void initViews() {
        mWlvOrder = (Pull2RefreshListView) findViewById(R.id.orderNority_xlv_chat);
        mWlvOrder.setOnRefreshListener(this);
        mWlvOrder.setOnLoadListener(this);
    }

    private void downloadDatas(String url){
        currentPage++;
        handler.sendEmptyMessage(ConstantUtils.LOADING);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        Map<String, String> params = httpRequestUtil.params;
        params.put("currentPage",currentPage+"");
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        if ((boolean) map.get("success")) {
                            List<Map<String, Object>> rowList = (List<Map<String, Object>>) map.get("rows");
                            if (rowList.size() == 0) {
                                currentPage--;
                                Toast.makeText(OrderNotifyActivity.this, ConstantUtils.NO_ANYTHING_DATA, Toast.LENGTH_SHORT).show();
                            }else {
                                mList.addAll(rowList);
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(OrderNotifyActivity.this, map.get("message").toString(), Toast.LENGTH_SHORT).show();
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
   /* *//**
     * 获取see 状态
     *//*
    public void onEventMainThread(SeeStatus seeStatus) {
        if (seeStatus.isSee()) {
            adapter.updateView();
        }
    }*/


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onRefresh() {
        handler.sendEmptyMessage(ConstantUtils.HEAD_REFRESH);
    }

    @Override
    public void onLoadMore() {
        handler.sendEmptyMessage(ConstantUtils.FOOT_REFRESH);
    }
}
