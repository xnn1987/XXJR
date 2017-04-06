package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.adapter.ChoiceConstanceAdpter;
import com.xxjr.xxjr.bean.ChangeTagEvenbus;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
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

public class SelectConcatActivity extends SlidBackActivity {

    private List<Map<String, Object>> mConcatList = new ArrayList<Map<String, Object>>();
    private ChoiceConstanceAdpter adappter;
    private ListView mLvHome;
    private static List<Map<String, Object>> rowsList = null;

    private List<Integer> mCheckList;
    private String tagId;
    private String tagName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_select_concat);
        getIntentDatas();
        initViews();
        setTitleBack();
        lvSetAdapter();
        downloadDatas();
    }
    public void setTitleBack() {
        SetTitleBar.setTitleText(SelectConcatActivity.this, "选择联系人",  "确定");
        findViewById(R.id.ThreeTitleBar_ll_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUpdaterContact(Urls.TAG_CHANG);
            }
        });
    }

    private void initViews() {
        mLvHome = (ListView) this.findViewById(R.id.concat_my_all_cust_list);
    }

    private void lvSetAdapter() {
        adappter = new ChoiceConstanceAdpter(this.getApplicationContext(), mConcatList,tagName);
        mLvHome.setAdapter(adappter);
    }

    /**
     * 下载搜索有联系人
     */
    private void downloadDatas() {
        AppUtil.showProgress(this, ConstantUtils.DIALOG_SHOW);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.jsonObjectRequestPostSuccess("/account/contact/queryList",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        rowsList = (List<Map<String, Object>>) map.get("rows");
                        mConcatList.addAll(rowsList);
                        adappter.notifyDataSetChanged();
                        AppUtil.dismissProgress();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AppUtil.errodDoanload(ConstantUtils.DIALOG_ERROR);
                    }
                }
        );
    }

    private void saveUpdaterContact(String url) {
        AppUtil.showProgress(this, ConstantUtils.DIALOG_SHOW);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        Map<String, String> params = httpRequestUtil.params;
        params.put("tagId", tagId);
        params.put("tagName", tagName);
        params.put("contactIds", getDatas("contactId"));
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        if ((boolean) map.get("success")) {
                            SelectConcatActivity.this.setResult(RESULT_OK);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), map.get("message").toString(), Toast.LENGTH_SHORT).show();
                        }
                        AppUtil.dismissProgress();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AppUtil.errodDoanload(ConstantUtils.DIALOG_ERROR);
                    }
                }
        );
    }

    public void onEventMainThread(ChangeTagEvenbus changeTagEvenbus) {
        mCheckList = changeTagEvenbus.getList();
    }

    /**
     * @param postName
     * @return
     */
    private String getDatas(String postName) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mCheckList.size(); i++) {
            sb.append(mConcatList.get(mCheckList.get(i)).get(postName).toString());
            if (i != mCheckList.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void getIntentDatas() {
        Intent intent = getIntent();
        tagId = intent.getStringExtra("tagId");
        tagName = intent.getStringExtra("tagName");
    }
}
