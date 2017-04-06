package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.adapter.XitongNotifyAdapter;
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

public class XitongNotifyActivity extends SlidBackActivity implements Pull2RefreshListView.OnRefreshListener, Pull2RefreshListView.OnLoadMoreListener {
    private android.support.v4.widget.SwipeRefreshLayout mSrfFresh;
    private int currentPage = 0;
    private List<Map<String,Object>> mList = new ArrayList<>();
    private boolean isFirst = true;
    private Pull2RefreshListView mWlvXitong;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case ConstantUtils.LOADING:
                    if (isFirst) {
                        AppUtil.showProgress(XitongNotifyActivity.this, ConstantUtils.DIALOG_SHOW);
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
                            downloadDatas(Urls.MSG_XITONG_NOTIFY);
                        }
                    }, 1000);
                    break;
                case ConstantUtils.HEAD_REFRESH:
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            currentPage = 0;
                            mList.clear();
                            downloadDatas(Urls.MSG_XITONG_NOTIFY);
                        }
                    }, 1000);
                    break;
                case ConstantUtils.STOP_FRESH:
                    mWlvXitong.stopAllRefresh();
                    break;
            }
        }
    };
    private XitongNotifyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xitong_notify);
        SetTitleBar.setTitleText(XitongNotifyActivity.this,"系统通知");
        initViews();
        lvSetAdapter();
        lvSetListener();
        downloadDatas(Urls.MSG_XITONG_NOTIFY);
    }

    private void lvSetAdapter() {
        adapter = new XitongNotifyAdapter(getApplicationContext(),mList,mWlvXitong);
        mWlvXitong.setAdapter(adapter);
    }
    private void initViews() {
        mWlvXitong = (Pull2RefreshListView) findViewById(R.id.xitongNotify_xlv_xitong);
        mWlvXitong.setOnRefreshListener(this);
        mWlvXitong.setOnLoadListener(this);
    }

    private void lvSetListener() {
        mWlvXitong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Intent intent = new Intent(getApplicationContext(),XitongNotifyChildActivity.class);
                intent.putExtra("subject",mList.get(position-1).get("subject").toString());
                intent.putExtra("content",mList.get(position-1).get("content").toString());
                intent.putExtra("sendTime",mList.get(position-1).get("sendTime").toString());
                intent.putExtra("messageId", mList.get(position - 1).get("messageId").toString());
                startActivity(intent);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.updateView(position);
                    }
                }, 1000);
            }
        });
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
                            handler.sendEmptyMessage(ConstantUtils.LOAD_SUCCESS);
                            List<Map<String, Object>> rowList = (List<Map<String, Object>>) map.get("rows");
                            if (rowList.size() == 0) {
                                currentPage--;
                                Toast.makeText(XitongNotifyActivity.this, ConstantUtils.NO_ANYTHING_DATA, Toast.LENGTH_SHORT).show();
                                return;
                            }
                            mList.addAll(rowList);
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(XitongNotifyActivity.this, map.get("message").toString(), Toast.LENGTH_SHORT).show();
                        }
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
    public void onRefresh() {
        handler.sendEmptyMessage(ConstantUtils.HEAD_REFRESH);
    }

    @Override
    public void onLoadMore() {
        handler.sendEmptyMessage(ConstantUtils.FOOT_REFRESH);
    }
}
