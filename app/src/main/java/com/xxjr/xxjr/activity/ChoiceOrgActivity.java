package com.xxjr.xxjr.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.adapter.ChoiceOrg2Adapter;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.TextUtil;
import com.xxjr.xxjr.utils.common.SlidBackActivity;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChoiceOrgActivity extends SlidBackActivity {

    private ListView mLvChoiceOrg;
    private List<Map<String ,Object>> mList = new ArrayList<Map<String ,Object>>();
    private ChoiceOrg2Adapter adapter;
    private String realName;
    private String cardNo;
    private String orgId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_org);
        getIntentDatas();
        SetTitleBar.setTitleText(ChoiceOrgActivity.this, "现在机构");
        initViews();
        lvSetAdapter();
        lvSetItemListener();
        downloadDatas(Urls.HOME_COMPANY);
    }



    private void initViews() {
        mLvChoiceOrg = (ListView) findViewById(R.id.ChoiceOrg2_lv_listview);

    }

    private void lvSetAdapter() {
        adapter = new ChoiceOrg2Adapter(getApplicationContext(),mList);
        mLvChoiceOrg.setAdapter(adapter);
    }

    private void lvSetItemListener() {
        mLvChoiceOrg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.updateView(position, mLvChoiceOrg);
                orgId = mList.get(position).get("orgId").toString();
                addQuery(Urls.ADD_QUERY);
            }
        });
    }
    private void downloadDatas(String url){
        AppUtil.showProgress(this, ConstantUtils.DIALOG_SHOW);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        List<Map<String, Object>> rowsList = (List<Map<String, Object>>) map.get("rows");

                        mList.addAll(rowsList);
                        adapter.notifyDataSetChanged();
                        AppUtil.dismissProgress();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AppUtil.errodDoanload(ConstantUtils.DIALOG_ERROR);
                    }
                });
    }
    private void addQuery(String url){
        AppUtil.showProgress(this, "正在查询，请稍后...");
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        Map<String, String> params = httpRequestUtil.params;
        params.put("realName",realName);
        params.put("cardNo",cardNo);
        params.put("orgId",orgId);
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        if ((boolean)map.get("success")){
                            dialogViews();
                        }else {
                            Toast.makeText(ChoiceOrgActivity.this, TextUtil.getTextToString(map.get("message")), Toast.LENGTH_SHORT).show();
                        }
                        AppUtil.dismissProgress();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AppUtil.errodDoanload(ConstantUtils.DIALOG_ERROR);
                    }
                });
    }

    public void getIntentDatas() {
        Intent intent = getIntent();
        realName = intent.getStringExtra("realName");
        cardNo = intent.getStringExtra("cardNo");
    }

    private void dialogViews(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ChoiceOrgActivity.this);
        Dialog dialog = builder.create();
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.choice_org2_dialog, null);


        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        dialog.getWindow().setContentView(view);
        initDialog(view,dialog);
    }

    private void initDialog(View view, final Dialog dialog) {
        Button button = (Button) view.findViewById(R.id.ChoiceDialog_btn_know);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),QueryResultActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
    }
}
