package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.adapter.HomeCompanyAdapter;
import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.bean.HomeCompanyBean;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.custview.Pull2RefreshListView;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.common.SlidBackActivity;
import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChoiceProduceActivity extends SlidBackActivity implements View.OnClickListener,
        Pull2RefreshListView.OnRefreshListener,Pull2RefreshListView.OnLoadMoreListener{

    private LinearLayout mLlBack;
    private EditText mEtProduceName;
    private HomeCompanyAdapter adapter;
    private String TAG = "ChoiceProduceActivity  ";
    private List<Map<String,Object>> mlist = new ArrayList<>();
    private HomeCompanyBean homeCompanyBean;
    private String from;
    private Pull2RefreshListView mPTRListview;
    private int currentPage = 0;
    private String searchContent  = "";
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case ConstantUtils.LOADING:
                    AppUtil.showProgress(ChoiceProduceActivity.this, ConstantUtils.DIALOG_SHOW);
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
                    downLoadProduce(Urls.COMPANY_PRODUCE_CHECK, searchContent);
                    break;
                case ConstantUtils.HEAD_REFRESH:
                    currentPage = 0;
                    mlist.clear();
                    downLoadProduce(Urls.COMPANY_PRODUCE_CHECK, "");
                    break;
                case ConstantUtils.STOP_FRESH:
                    mPTRListview.stopAllRefresh();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_produce);
        getIntentDatas();
        setInitViews();
        setListener();
        edSetListener();
        lvSetAdapter();
        mPTRSetItemListener();
        handler.sendEmptyMessage(ConstantUtils.LOADING);
        downLoadProduce(Urls.COMPANY_PRODUCE_CHECK, "");
    }
    private void setInitViews() {
        mLlBack = (LinearLayout) findViewById(R.id.ChoiceProduce_ll_back);
        mEtProduceName = (EditText) findViewById(R.id.ChoiceProduce_et_produceName);
//        mTvDeny = (TextView) findViewById(R.id.ChoiceProduce_tv_deny);
        mPTRListview = (Pull2RefreshListView) findViewById(R.id.ChoiceProduce_Lv_ProduceName);
    }

    private void setListener() {
        mLlBack.setOnClickListener(this);
        mPTRListview.setOnLoadListener(this);
        mPTRListview.setOnRefreshListener(this);
    }
    private void lvSetAdapter() {
        adapter = new HomeCompanyAdapter(getApplicationContext(),mlist);
        mPTRListview.setAdapter(adapter);
    }

    private void edSetListener() {
        mEtProduceName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mlist.clear();
                currentPage = 0;
                searchContent  = s.toString();
                downLoadProduce(Urls.COMPANY_PRODUCE_CHECK, searchContent);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ChoiceProduce_ll_back:
                finish();
                break;

        }
    }
    private void downLoadProduce(String url,String text){
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        Map<String, String> params = httpRequestUtil.params;
        params.put("cityCode",MyApplication.city);
        params.put("loanName",text);
        params.put("currentPage",( currentPage +=1 )+"");
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        homeCompanyBean = new Gson().fromJson(response.toString(), HomeCompanyBean.class);
                        Map<String, Object> mapss = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        List<Map<String, Object>> rowsList = (List<Map<String, Object>>) mapss.get("rows");
                        if (rowsList.size() == 0) {
                            currentPage--;
                            Toast.makeText(ChoiceProduceActivity.this, "没有更多数据", Toast.LENGTH_SHORT).show();
                        }else {
                            mlist.addAll(rowsList);
                            adapter.notifyDataSetChanged();
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

    private void mPTRSetItemListener() {
        mPTRListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), CompanyProduceActivity.class);
                int loadId = homeCompanyBean.getRows().get(position - 1).getOrgId();
                intent.putExtra("loadId", loadId);
                intent.putExtra("applyCount", homeCompanyBean.getRows().get(position - 1).getApplyCount() + "");
                intent.putExtra("prgName", homeCompanyBean.getRows().get(position - 1).getOrgName());
                intent.putExtra("orgImage", homeCompanyBean.getRows().get(position - 1).getOrgImage());
                startActivity(intent);
            }
        });
    }

    public void getIntentDatas() {
        Intent intent = getIntent();
        from = intent.getStringExtra("from");

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
