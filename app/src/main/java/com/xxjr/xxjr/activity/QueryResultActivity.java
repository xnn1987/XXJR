package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.adapter.QueryResultAdapter;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QueryResultActivity extends SlidBackActivity {

    private ListView mLvResult;
    private List<Map<String,Object>> mList = new ArrayList<>();
    private QueryResultAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_result);
        setTitleBack();
        initViews();
        lvSetAdapter();
        addQuery(Urls.QUERY_RESULT);
    }
    public void setTitleBack() {
        LinearLayout mLLRight = (LinearLayout)findViewById(R.id.ThreeTitleBar_ll_click);
        SetTitleBar.setTitleText(QueryResultActivity.this, "查号结果",  "新建查号");
        mLLRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CustomInfoCheckActivity.class);
                startActivity(intent);
            }
        });
    }
    private void initViews() {
        mLvResult = (ListView) findViewById(R.id.QueryResult_lv_result);
    }

    private void lvSetAdapter() {
        adapter = new QueryResultAdapter(getApplicationContext(),mList);
        mLvResult.setAdapter(adapter);
    }

    private void addQuery(String url){
        AppUtil.showProgress(this, ConstantUtils.DIALOG_SHOW);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        List<Map<String,Object>> rowsList = (List<Map<String, Object>>) map.get("rows");
                        mList.addAll(rowsList);
                        adapter.notifyDataSetChanged();

                        AppUtil.dismissProgress();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AppUtil.errodDoanload("网络不稳定");
                    }
                });
    }
}
