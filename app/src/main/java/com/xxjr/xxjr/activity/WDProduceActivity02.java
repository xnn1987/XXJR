package com.xxjr.xxjr.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.adapter.WD_ProduceAdapter;
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

public class WDProduceActivity02 extends SlidBackActivity implements Pull2RefreshListView.OnRefreshListener{
    private Pull2RefreshListView mLvProduce;
    private  List<Map<String, Object>> mList = new ArrayList<>();
    private boolean isFirst = true;
    private WD_ProduceAdapter adapter;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case ConstantUtils.LOADING:
                    if (isFirst) {
                        AppUtil.showProgress(WDProduceActivity02.this, ConstantUtils.DIALOG_SHOW);
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
                case ConstantUtils.HEAD_REFRESH:
                    mList.clear();
                    downLoadDatas(Urls.WD_PRODUCE_List);
                    break;
                case ConstantUtils.STOP_FRESH:
                    mLvProduce.stopAllRefresh();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wdproduce02);
        SetTitleBar.setTitleText(WDProduceActivity02.this,"微店产品");
        getIntentDatas();
        initViews();
        setAdapter();
    }

    private void getIntentDatas(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!=null) {
            mList = (List<Map<String, Object>>) bundle.getSerializable("rowsList");
        }else {
            downLoadDatas(Urls.WD_PRODUCE_List);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        handler.sendEmptyMessage(ConstantUtils.HEAD_REFRESH);
    }

    private void initViews() {
        this.mLvProduce = (Pull2RefreshListView) findViewById(R.id.wdproduce_lv_produce);
        mLvProduce.setOnRefreshListener(this);
    }

    private void setAdapter() {
        adapter = new WD_ProduceAdapter(WDProduceActivity02.this,mList,handler);
        mLvProduce.setAdapter(adapter);
    }

    //下载公司产品列表数据
    private void downLoadDatas(String url) {
        handler.sendEmptyMessage(ConstantUtils.LOADING);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        List<Map<String, Object>> rowsList = (List<Map<String, Object>>) map.get("rows");
                            mList.addAll(rowsList);
                            adapter.notifyDataSetChanged();
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

    public void onAddWdProduce(View v){
        if (mList.size()<10) {
            startActivity(new Intent(getApplicationContext(), WD_AddCreditProduceActivity.class));
        }else {
            Toast.makeText(this, "微店产品最多只能有10条", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRefresh() {
        handler.sendEmptyMessage(ConstantUtils.HEAD_REFRESH);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( resultCode == RESULT_OK ){
            switch (requestCode){
                case 2:
                    handler.sendEmptyMessage(ConstantUtils.HEAD_REFRESH);
                    break;
            }
        }
    }
}
