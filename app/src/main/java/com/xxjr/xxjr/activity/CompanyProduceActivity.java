package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.adapter.CompanyProduceAdapter;
import com.xxjr.xxjr.application.MyApplication;
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

public class CompanyProduceActivity extends SlidBackActivity {

    private static final int ADD_HEADCOUNT = 2;
    private ListView mLvCompany;
    private View mVCompany, mVCompanyProduceCount;
    private int loadId = -1;
    private String loadId2, produceType, companyName;
    private CompanyProduceAdapter adapter;
    private String applyCount, prgName, orgImage;
    private ImageView mIvCompanyIcono;
    private TextView mTvCompanyName, mTvApplyCount;
    private List<Map<String,Object>> mList = new ArrayList<Map<String,Object>>();
    private TextView mTvProduceSum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_produce);
        SetTitleBar.setTitleText(CompanyProduceActivity.this, "公司产品");
        getIntentDatas();
        initViews();
        lvAddHeadView();
        headViewInitViews();
        setAddHeadDatas();
        lvSetAdapter();
        lvSetListener();
        downloadDatas(Urls.COMPANY_PRODUCE);

    }


    private void initViews() {
        mLvCompany = (ListView) findViewById(R.id.CompanyProduce_lv_produce);
        mVCompany = getLayoutInflater().inflate(R.layout.headview_company_produce, null);
        mVCompanyProduceCount = getLayoutInflater().inflate(R.layout.headview_companyproduce_count, null);

    }

    private void lvAddHeadView() {
        mLvCompany.addHeaderView(mVCompany);
        mLvCompany.addHeaderView(mVCompanyProduceCount);
    }

    private void headViewInitViews() {
        mTvCompanyName = (TextView) mVCompany.findViewById(R.id.CompanyProduce_tv_headName);
        mTvApplyCount = (TextView) mVCompany.findViewById(R.id.CompanyProduce_tv_ProduceCount);
        mIvCompanyIcono = (ImageView) mVCompany.findViewById(R.id.CompanyProduce_iv_companyIcon);

        mTvProduceSum = (TextView) mVCompanyProduceCount.findViewById(R.id.CompanyProduce_tv_sum);
    }

    private void lvSetAdapter() {
        adapter = new CompanyProduceAdapter(getApplicationContext(), mList,prgName);//加上公司名称
        mLvCompany.setAdapter(adapter);
    }

    private void getIntentDatas() {
        Intent intent = getIntent();
        loadId = intent.getIntExtra("loadId", -1);
        applyCount = intent.getStringExtra("applyCount");
        prgName = intent.getStringExtra("prgName");
        orgImage = intent.getStringExtra("orgImage");
    }


    private void setAddHeadDatas() {
        mTvCompanyName.setText(prgName);
        mTvApplyCount.setText(applyCount);
        MyApplication.imageLoader.displayImage(Urls.RANQING + orgImage, mIvCompanyIcono, MyApplication.options);
    }


    private void lvSetListener() {
        mLvCompany.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= ADD_HEADCOUNT) {
                    Intent intent = new Intent(getApplicationContext(), ProduceDetailActivity.class);
                    loadId2 = mList.get(position - ADD_HEADCOUNT).get("loanId") + "";
                    String loadType = mList.get(position-ADD_HEADCOUNT).get("loanType")+"";
                    intent.putExtra("loanId", loadId2);
                    intent.putExtra("companyName", prgName);
                    intent.putExtra("loadType", loadType);
                    intent.putExtra("type", ConstantUtils.COMPANY_PRODUCE_COMMIT_TYPE);
                    produceType = mList.get(position - ADD_HEADCOUNT).get("loanIntro").toString();
                    intent.putExtra("produceType", "-"+produceType);
                    startActivity(intent);
                }
            }
        });
    }

    private void downloadDatas(String url) {
        AppUtil.showProgress(this, "正在加载数据，请稍候...");
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        Map<String, String> params = httpRequestUtil.params;
        params.put("orgId", loadId + "");
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String,Object> map = JsonUtil.getInstance().json2Object(response.toString(),Map.class);
                        List<Map<String,Object>> rowsList = (List<Map<String, Object>>) map.get("rows");
                        mList.addAll(rowsList);
                        mTvProduceSum.setText("(" + mList.size() + ")");
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
