package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.adapter.CommentFriendAdpter;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
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

public class ConcatAddTagActivity extends SlidBackActivity implements View.OnClickListener{
//    private ImageView backView;
    private int SAVETAG = 1;//保存联系人
    private int ADDTAG =2 ;// 添加联系人
    private int DEDTAG =3 ;// 删除联系人
    private int fromTo = 1;

    private ImageView mIvAddConcat, mIvDelConcat;
    private String tagId;
    private ListView mLvContent;
    private List<Map<String,Object>> mList = new ArrayList<>();
    private CommentFriendAdpter adapter;
    private String tagName;
    private EditText mEtTagName;

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ThreeTitleBar_ll_click:
                fromTo = SAVETAG;
                saveTag();
                break;
            case R.id.concat_add_concat:
                fromTo = ADDTAG;
                saveTag();//先保存标签后再跳转到添加联系人
                break;
            case R.id.concat_delect_concat:
                fromTo = DEDTAG;
                saveTag();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concat_add_tag);
        setTitleDatas();
        initView();
        getIntentDatas();
        setListener();
        lvSetAdapter();
        downLoadDatas(Urls.TAG_CONSTOM_QUERY);
    }

    private void setTitleDatas() {
        SetTitleBar.setTitleText(ConcatAddTagActivity.this, "我的标签", "保存");
        findViewById(R.id.ThreeTitleBar_ll_click).setOnClickListener(this);
    }

    private void initView(){
        mIvAddConcat = (ImageView)this.findViewById(R.id.concat_add_concat);
        mIvDelConcat = (ImageView)this.findViewById(R.id.concat_delect_concat);
        mLvContent = (ListView) findViewById(R.id.ConcatAddTag_lv_concat);
        mEtTagName = (EditText) findViewById(R.id.ConcatAddTag_et_tagname);
    }

    private void lvSetAdapter() {
        adapter = new CommentFriendAdpter(getApplicationContext(),mList);
        mLvContent.setAdapter(adapter);
    }

    private void setListener(){
        mIvAddConcat.setOnClickListener(this);
        mIvDelConcat.setOnClickListener(this);
    }


    /**
     * 保存标签
     */
    private void saveTag(){
        if (TextUtils.isEmpty(mEtTagName.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(),"请输入标签名",Toast.LENGTH_LONG).show();
            return;
        }
        if (!tagId.equals("")){//  修改已有的标签
            saveUpdataTag(Urls.TAG_CHANG);
        }else {       //  增加标签
            saveAddTag(Urls.ADD_TAG);
        }
    }


    /**
     * 判断这个保存是  直接保存跳转出去   还是保存跳转到添加联系人去
     * @param fromTo  类型
     */
    private void fromToIntent(int fromTo){
        if (fromTo == SAVETAG){
            ConcatAddTagActivity.this.setResult(RESULT_OK);
            finish();
        }else if (fromTo == ADDTAG){
            addConcatIntent();
        }else if (fromTo == DEDTAG){
            delConcatIntent();
        }
    }

    /**
     * 跳转新添联系人
     */
    private void addConcatIntent(){
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(),SelectConcatActivity.class);
        intent.putExtra("tagId",tagId);
        intent.putExtra("tagName",mEtTagName.getText().toString());
        startActivityForResult(intent, 1);
    }

    /**
     * 跳转删除联系人
     */
    private void delConcatIntent(){
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), ConcatDelTagActivity.class);
        intent.putExtra("tagId", tagId);
        intent.putExtra("tagName",mEtTagName.getText().toString());
        intent.putExtra("mList",(Serializable)mList);
        startActivityForResult(intent, 1);
    }


    /**
     * 保存已有的标签
     * @param url
     */
    private void saveUpdataTag(String url){
        AppUtil.showProgress(ConcatAddTagActivity.this, ConstantUtils.DIALOG_SHOW);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        Map<String, String> params = httpRequestUtil.params;
        params.put("tagId", tagId);
        params.put("tagName", mEtTagName.getText().toString().trim());
        params.put("contactIds",getContactId() );
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String,Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        if ((boolean)map.get("success")){
                            fromToIntent(fromTo);
                        }else {
                            Toast.makeText(getApplicationContext(),map.get("message").toString(),Toast.LENGTH_SHORT).show();
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
    /**
     * 添加新的联系人
     * @param url
     */
    private void saveAddTag(String url) {
        AppUtil.showProgress(ConcatAddTagActivity.this, ConstantUtils.DIALOG_SHOW);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        Map<String, String> params = httpRequestUtil.params;
        params.put("tagName", mEtTagName.getText().toString().trim());
        params.put("contactIds", getContactId());
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        if ((boolean) map.get("success")) {
                            Map<String, Object> map1 = (Map<String, Object>) map.get("attr");
                            tagId = map1.get("tagId").toString();
                            fromToIntent(fromTo);

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
                });

    }

    /**
     * 下载初始化标签所属的联系人
     * @param url
     */
    private void downLoadDatas(String url){
        AppUtil.showProgress(ConcatAddTagActivity.this, ConstantUtils.DIALOG_ERROR);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        Map<String, String> params = httpRequestUtil.params;
        params.put("tagId",tagId);
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String,Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        List<Map<String,Object>> rowsList = (List<Map<String, Object>>) map.get("rows");
                        mList.clear();
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

    /**
     * @return 获取contactId的拼接
     */
    private String getContactId() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mList.size(); i++) {
            sb.append(mList.get(i).get("contactId").toString());
            if (i != mList.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
    public void getIntentDatas() {
        Intent intent = getIntent();
        tagId = intent.getStringExtra("tagId");
        tagName = intent.getStringExtra("tagName");
        mEtTagName.setText(tagName);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK){
            downLoadDatas(Urls.TAG_CONSTOM_QUERY);
        }
    }
}
