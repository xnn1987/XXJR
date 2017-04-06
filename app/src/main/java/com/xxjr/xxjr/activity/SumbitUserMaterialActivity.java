package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.adapter.SumbitUserMaterialAdapter;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.utils.DebugLog;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SumbitUserMaterialActivity extends SlidBackActivity implements View.OnClickListener {
    private Button mBtnCommit, mBtnSkin;
    private int applyId;
    private ListView mLvMaterial;
    private String Tag = "SumbitUserMaterialActivity";
    private List<Map<String, Object>> mMaterialGroupList = new ArrayList<>();
    private SumbitUserMaterialAdapter adapter;
    private int loanId;
    private int commitType;
    private String produceType;
    private String titleName;
    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sumbit_user_material);
        SetTitleBar.setTitleText(SumbitUserMaterialActivity.this, "完整交单");
        getIntentDatas();
        downloadDatas(Urls.COMPLE_ORDER);//数据下载

        initView();
        setListener();
        lvSetAdapter();
        lvSetListener();


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        downloadDatas(Urls.COMPLE_ORDER);//数据下载
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMaterialGroupList.clear();
    }

    private void setListener() {
        mBtnCommit.setOnClickListener(this);
        mBtnSkin.setOnClickListener(this);
    }

    private void initView() {
        View foodView = getLayoutInflater().inflate(R.layout.sumbit_user_material_food, null);
        mBtnCommit = (Button) foodView.findViewById(R.id.SumbitUserMateria_btn_commit);
        mBtnSkin = (Button) foodView.findViewById(R.id.SumbitUserMateria_btn_Skin);
        mLvMaterial = (ListView) findViewById(R.id.SumbitUserMateria_lv_provide);
        mLvMaterial.addFooterView(foodView);
        if (produceType.equals("-1")) {//从交单详情过来，隐藏跳过按钮
            mBtnSkin.setVisibility(View.GONE);
        }

    }

    //  适配
    private void lvSetAdapter() {
        adapter = new SumbitUserMaterialAdapter(getApplicationContext(), mMaterialGroupList);
        mLvMaterial.setAdapter(adapter);
    }

    // 监听
    private void lvSetListener() {
        mLvMaterial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<String> successPosition = adapter.getSuccessPosition();
                String materialFlag = successPosition.get(position);
                if (!materialFlag.equals("1")) {//通过审核的情况下是不能再点击了
                    List<Map<String, Objects>> bufferList = (List<Map<String, Objects>>) mMaterialGroupList.get(position).get("list");
                    Intent intent = new Intent(getApplicationContext(), MaterialUploadActivity02.class);
                    intent.putExtra("title", mMaterialGroupList.get(position).get("groupName").toString());
                    intent.putExtra("bufferList", (Serializable) bufferList);
                    intent.putExtra("applyId", applyId);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            //btn  提交
            case R.id.SumbitUserMateria_btn_commit:
                if (from.equals("OrderDetailActivity")) {
                    upLoadMetarial(Urls.COMPLE_REPITE_UPLOAD);
                } else if (from.equals("SalaryOrderActivity")) {
                    upLoadMetarial(Urls.COMPLE_ORDER_UPLOAD);
                }
                break;
            case R.id.SumbitUserMateria_btn_Skin:
                // 不需要判断
                intent.putExtra("commitType", ConstantUtils.COMMIT_RESULT_SIMPLE);
                intent.putExtra("produceType", titleName);
                intent.putExtra("titleName", titleName);
                intent.setClass(getApplicationContext(), OrderResultActivity.class);
                startActivity(intent);
                break;

        }

    }

    //上个页面传值
    public void getIntentDatas() {
        Intent intent = getIntent();
        applyId = intent.getIntExtra("applyId", -1);
        loanId = intent.getIntExtra("loanId", -1);
        commitType = intent.getIntExtra("commitType", 1);
        produceType = intent.getStringExtra("produceType");
        titleName = intent.getStringExtra("titleName");
        from = intent.getStringExtra("from");

    }

    //下载列表
    private void downloadDatas(String url) {
        AppUtil.showProgress(this, "正在加载数据，请稍候...");
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        Map<String, String> params = httpRequestUtil.params;
        params.put("applyId", applyId + "");
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mMaterialGroupList.clear();
                        Map<String, Object> responseMap = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        boolean success = (Boolean) responseMap.get("success");
                        if (success) {
                            Map<String, Object> attr = (Map<String, Object>) responseMap.get("attr");
                            List<Map<String, Object>> materialGroupList = (List<Map<String, Object>>) attr.get("materialGroup");
                            mMaterialGroupList.addAll(materialGroupList);
                            adapter.notifyDataSetChanged();
                        } else {
                            DebugLog.Toast(SumbitUserMaterialActivity.this,responseMap.get("message").toString());
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

    //上传列表
    private void upLoadMetarial(String url) {
        AppUtil.showProgress(this, "正在上传，请稍候...");
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        Map<String, String> params = httpRequestUtil.params;
        params.put("applyId", applyId + "");
        httpRequestUtil.jsonObjectRequestPostSuccess(
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(Tag, response.toString());
                        Map<String, Object> responseMap = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        boolean success = (Boolean) responseMap.get("success");
                        if (success) {
                            if (produceType.equals("-1")) {//从交单详情过来，不需要  看到结果
                                finish();
                            } else {
                                Intent intent = new Intent();
                                intent.setClass(getApplicationContext(), OrderResultActivity.class);
                                intent.putExtra("commitType", ConstantUtils.COMMIT_RESULT_COMPLETE);
                                intent.putExtra("produceType", titleName);
                                intent.putExtra("titleName", titleName);
                                startActivity(intent);
                            }


                        } else {
                            Log.e(Tag, "下载列表错误" + response.toString());
                            Toast.makeText(SumbitUserMaterialActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), OrderResultFailActivity.class);
                            intent.putExtra("message", responseMap.get("message").toString());
                            startActivity(intent);
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

}


