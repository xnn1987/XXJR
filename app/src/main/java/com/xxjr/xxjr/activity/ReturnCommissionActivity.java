package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.adapter.ReturnCommissionAdapter;
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

public class ReturnCommissionActivity extends SlidBackActivity {

    private ListView mLvCompany;
    private TextView mTvReturnCommision;
    private ImageView mIvCheckIcon;
    private ReturnCommissionAdapter adapter;
    private List<Map<String,Object>> mList = new ArrayList<>();
    private String returnMoneyCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_commission);
        SetTitleBar.setTitleText(ReturnCommissionActivity.this, "已返佣金");
        getIntentDatas();
        initViews();
        setInitViewDatas();
        lvSetAdapter();

        judgeCheckCount(mLvCompany,mIvCheckIcon);
        downLoadDatas(Urls.CUST_PERFORM_RETURN_COMMISSION);
    }


    private void initViews() {
        mLvCompany = (ListView) findViewById(R.id.ReturnCommission_lv_company);
        mTvReturnCommision = (TextView) findViewById(R.id.ReturnCommission_tv_ReturnCommision);
        mIvCheckIcon = (ImageView) findViewById(R.id.returnCommission_iv_checkIcon);
        
    }


    //初始化控件数据
    private void setInitViewDatas() {
        mTvReturnCommision.setText(returnMoneyCount);
    }

    private void lvSetAdapter() {
        adapter = new ReturnCommissionAdapter(getApplicationContext(),mList);
        mLvCompany.setAdapter(adapter);
    }


    private  void downLoadDatas(String url){
        AppUtil.showProgress(this, ConstantUtils.DIALOG_SHOW);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.jsonObjectRequestPostSuccess(
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> responseMap = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        boolean success = (Boolean) responseMap.get("success");
                        if (success) {
                            Gson gson = new Gson();
                            List<Map<String,Object>> rows = (List<Map<String, Object>>) responseMap.get("rows");
                            mList.addAll(rows);
                            adapter.notifyDataSetChanged();
                            judgeCheckCount(mLvCompany, mIvCheckIcon);

                        } else {
                            Toast.makeText(ReturnCommissionActivity.this, responseMap.get("message").toString(), Toast.LENGTH_SHORT).show();
                        }
                        AppUtil.dismissProgress();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AppUtil.errodDoanload("网络不稳定");
                    }
                }
        );
    }

    /**
     * 判断是否有数据，没有就显示图标
     */
    private void judgeCheckCount(ListView listView,ImageView imageView){
        if (listView.getCount()!=0){
            imageView.setVisibility(View.GONE);
        }else {
            imageView.setVisibility(View.VISIBLE);
        }
    }

    public void getIntentDatas() {
        Intent intent = getIntent();
        returnMoneyCount = intent.getStringExtra("returnMoneyCount");
    }
}
