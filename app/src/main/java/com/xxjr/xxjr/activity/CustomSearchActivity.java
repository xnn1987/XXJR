package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.adapter.CommentFriendAdpter;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.common.SlidBackActivity;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomSearchActivity extends SlidBackActivity implements View.OnClickListener {

    private LinearLayout mLlBack;
    private TextView mTvDeny;
    private TextView mTvSearchCount;
    private ListView mPtrSearch;
    private EditText mEtSearch;
    private String TAG = "CustomSearchActivity -->";
    private CommentFriendAdpter adappter;
    private List<Map<String, Object>> mConcatList = new ArrayList<>();
    private ListView mLvSearch;
    private long firstTime = 0;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Custom_ll_back:
                finish();
                break;
            case R.id.CustomSearch_tv_deny:
                mEtSearch.setText("");
                break;

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_search);
        initViews();
        setListener();
        etSetChangeListener();
        downLoadSearch(Urls.SEARCH_CUSTOM_INFO, "");
        lvSetAdapter();
        lvSetListener();
    }

    private void lvSetListener() {
        mLvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position>=1) {
                    Intent intent = new Intent(getApplicationContext(), MyCustActivity.class);
                    intent.putExtra("realName", mConcatList.get(position).get("realName").toString());
                    intent.putExtra("isImportant", mConcatList.get(position).get("isImportant").toString());
                    startActivity(intent);
                }
            }
        });
    }

    private void etSetChangeListener() {
        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    downLoadSearch(Urls.SEARCH_CUSTOM_INFO, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initViews() {
        mLlBack = (LinearLayout) findViewById(R.id.Custom_ll_back);
        mTvDeny = (TextView) findViewById(R.id.CustomSearch_tv_deny);
        mTvSearchCount = (TextView) findViewById(R.id.CustomSearch_tv_searchCount);
        mLvSearch = (ListView) findViewById(R.id.CustomSearch_Lv_search);
        mEtSearch = (EditText) findViewById(R.id.ChoiceProduce_et_produceName);
    }

    private void setListener() {
        mLlBack.setOnClickListener(this);
        mTvDeny.setOnClickListener(this);
    }

    private void lvSetAdapter() {
        adappter = new CommentFriendAdpter(this, mConcatList);
        mLvSearch.setAdapter(adappter);
    }

    private void downLoadSearch(String url, final String realName){
        AppUtil.showProgress(this, ConstantUtils.DIALOG_SHOW);
         HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        Map<String, String> params = httpRequestUtil.params;
        params.put("realName",realName);
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mConcatList.clear();
                        Map<String,Object> map= JsonUtil.getInstance().json2Object(response.toString(),Map.class);
                        List<Map<String,Object>> rowsList = (List<Map<String, Object>>) map.get("rows");
                        mConcatList.addAll(rowsList);
                        adappter.notifyDataSetChanged();

                        mTvSearchCount.setText(rowsList.size()+"");
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
}
