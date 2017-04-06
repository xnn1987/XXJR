package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.adapter.ConcatDelAdapter;
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

public class ConcatDelTagActivity extends SlidBackActivity {

    private String tagId;
    private String tagName;
    private ListView mLvDel;
    private ConcatDelAdapter adapter;
    private List<Map<String,Object>> mList = new ArrayList<>();
    private List<Integer> mCheckList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_concat_del_tag);
        getIntentDatas();
        initViews();
        setTitleBack();
        lvSetAdapter();
    }

    public void setTitleBack() {
        SetTitleBar.setTitleText(ConcatDelTagActivity.this, "选择联系人","确定");
        findViewById(R.id.ThreeTitleBar_ll_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUpdataTag(Urls.TAG_CHANG);
            }
        });
    }

    private void initViews() {
        mLvDel = (ListView) this.findViewById(R.id.ConcatDelTag_lv_ded);
    }

    private void lvSetAdapter() {
        adapter = new ConcatDelAdapter(getApplicationContext(),mList,tagName);
        mLvDel.setAdapter(adapter);
    }

    public void getIntentDatas() {
        Intent intent = getIntent();
        tagId = intent.getStringExtra("tagId");
        tagName = intent.getStringExtra("tagName");
       mList = (List<Map<String, Object>>) intent.getSerializableExtra("mList");
    }

    /**
     * 保存标签
     * @param url
     */
    private void saveUpdataTag(String url){
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
                            ConcatDelTagActivity.this.setResult(RESULT_OK);
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

    /**
     * 删除选中的，  取出剩余的
     * @param postName
     * @return
     */
    private String getDatas(String postName) {
        StringBuilder sb = new StringBuilder();
        List<Map<String,Object>> bufferList = new ArrayList<>();
        bufferList.addAll(mList);
        for (int i = 0; i < bufferList.size(); i++) {
            for (int j = 0 ; j<mCheckList.size(); j++){
                if (i==mCheckList.get(j)){// 通过原先  bufferList  的角标   和    mCheckList选中的position  进行一个判断，  相同就说明删除
                    bufferList.remove(i);
                    mCheckList.remove(j);
                    i--;
                    j--;
                }
            }
        }
        if (sb.length()!=0) {
            for (int i = 0; i < bufferList.size(); i++) {
                sb.append(bufferList.get(i).get(postName).toString() + ",");
            }
            //  去掉头尾的标点符号
            sb = sb.charAt(sb.length() - 1) == ',' ? sb.deleteCharAt(sb.length() - 1) : sb;
        }

        return sb.toString();
    }
    public void onEventMainThread(ChangeTagEvenbus changeTagEvenbus) {
        mCheckList = changeTagEvenbus.getList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mList.clear();
        mCheckList.clear();
        EventBus.getDefault().unregister(this);
    }
}
