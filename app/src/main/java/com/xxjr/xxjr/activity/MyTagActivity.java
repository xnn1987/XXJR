package com.xxjr.xxjr.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.adapter.RecommendTagAdapter;
import com.xxjr.xxjr.bean.PassBackEvenbuss;
import com.xxjr.xxjr.bean.TuiJianEvenBus;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.other.tagcheckbox.TagBaseAdapter;
import com.xxjr.xxjr.other.tagcheckbox.TagCloudLayout;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;
import com.ypy.eventbus.EventBus;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MyTagActivity extends SlidBackActivity {

    private TagCloudLayout mLvContainer,mTCLTuiJian;
    private List<Map<String, Object>> mList = new ArrayList<>();
    private TagBaseAdapter mAdapter;
    private MyAdapter adapter;
    private EditText mEtTag;
    private String contactId = "";
    private List<String> mTagIdList = new ArrayList<>();
    private List<String> mTuiJianList = new ArrayList<>();
    private RecommendTagAdapter tJAdapter;


    /**
     * 遍历所有的TagId
     *
     * @return
     */
    private String allTagId() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mTagIdList.size(); i++) {
            if (i != mTagIdList.size() - 1) {
                sb.append(mTagIdList.get(i)).append(",");
            } else {
                sb.append(mTagIdList.get(i));
            }
        }
        return sb.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_my_tag);
        contactId = getIntent().getStringExtra("contactId");

        initView();
        downloadDatas();
        lvsetAdapater();
        setTitleBack();
    }

    private void initView() {
        mEtTag = (EditText) this.findViewById(R.id.my_tag_text);
        mLvContainer = (TagCloudLayout) findViewById(R.id.mytag_tcl_contain);
        mTCLTuiJian = (TagCloudLayout) findViewById(R.id.mytag_tcl_contain);
    }

    public void setTitleBack() {
        SetTitleBar.setTitleText(MyTagActivity.this, "标签","保存");
        findViewById(R.id.ThreeTitleBar_ll_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mEtTag.getText().toString().trim().equals("")) {
                    addTag(Urls.ADD_TAG);
                }
                updateTag(Urls.UPDATE_TAG);//修改标签
            }
        });
    }

    private void lvsetAdapater() {
        adapter = new MyAdapter();
        mLvContainer.setAdapter(adapter);

        tJAdapter = new RecommendTagAdapter(mTuiJianList,getApplicationContext());
        mTCLTuiJian.setAdapter(tJAdapter);
    }

    public void onEventMainThread (TuiJianEvenBus bean){
        if (bean.isCheck()){
            mEtTag.setText(bean.getTagText());
        }else {
            mEtTag.setText("");
        }
    }

    public class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return mList.size();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = getLayoutInflater().inflate(R.layout.tag_checkbox_item, null);
                viewHolder.tv_tag = (CheckBox) convertView.findViewById(R.id.mytag_checkbox_tag);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (mList.get(position).get("flag").toString().equals("1")) {//没有添加这个标签
                viewHolder.tv_tag.setChecked(true);
                mTagIdList.add(mList.get(position).get("tagId").toString());
            } else {
                viewHolder.tv_tag.setChecked(false);
            }
            viewHolder.tv_tag.setText(mList.get(position).get("tagName").toString());
            viewHolder.tv_tag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        mTagIdList.add(mList.get(position).get("tagId").toString());
                        Collections.sort(mTagIdList);
                    } else {
                        for (int i = 0; i < mList.size(); i++) {
                            if (mList.get(i).get("tagName").toString().equals(buttonView.getText().toString().trim())) {
                                for (int j = 0; j < mTagIdList.size(); j++) {
                                    if (mTagIdList.get(j).equals(mList.get(i).get("tagId").toString())) {
                                        mTagIdList.remove(j);
                                        Collections.sort(mTagIdList);
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            });
            return convertView;
        }

        class ViewHolder {
            CheckBox tv_tag;
        }

        /**
         * 判断是否包含tagId
         *
         * @param position
         * @return
         */
        private boolean isContainTagId(int position) {
            boolean flag = false;
            for (int i = 0; i < mTagIdList.size(); i++) {
                if (mTagIdList.get(i).equals(mList.get(position).get("tagId").toString())) {
                    return true;
                }
            }
            return false;
        }
    }

    private void downloadDatas() {
        AppUtil.showProgress(this, ConstantUtils.DIALOG_SHOW);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.params.put("contactId", contactId);
        httpRequestUtil.jsonObjectRequestPostSuccess("/account/contact/queryTagDtl", new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtil.dismissProgress();
                        Map<String, Object> map = (Map<String, Object>) JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        boolean success = (Boolean) map.get("success");
                        if (success) {
                            mList.clear();
                            mTuiJianList.clear();
                            // 已有标签
                            List<Map<String, Object>> tagList = (List<Map<String, Object>>) map.get("rows");
                            if (tagList != null && tagList.size() > 0) {
                                mList.addAll(tagList);
                                adapter.notifyDataSetChanged();
                            }
                            //推荐标签
                            Map<String, Object> map1 = (Map<String, Object>) map.get("attr");
                            List<String> defaultTagsList = (List<String>) map1.get("defaultTags");
                            if (defaultTagsList!=null && tagList.size()>0){
                                mTuiJianList.addAll(defaultTagsList);
                                tJAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getApplication(), map.get("message").toString(), Toast.LENGTH_SHORT).show();
                        }
                        passBack();

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
     * 添加标签
     * @param url
     */
    private void addTag(String url) {
        String tagName = mEtTag.getText().toString();
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.params.put("tagName", tagName);
        httpRequestUtil.params.put("contactIds", contactId);
        httpRequestUtil.jsonObjectRequestPostSuccess(url, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> msgMap = (Map<String, Object>) JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        boolean success = (Boolean) msgMap.get("success");
                        if (success) {
                            mEtTag.setText("");
                            downloadDatas();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
    }

    /**
     * 更新标签
     * @param url
     */
    private void updateTag(String url) {
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.params.put("contactId", contactId);
        httpRequestUtil.params.put("tagIds", allTagId());
        httpRequestUtil.jsonObjectRequestPostSuccess(url, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> msgMap = (Map<String, Object>) JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        boolean success = (Boolean) msgMap.get("success");
                        if (success) {
                            mEtTag.setText("");
                            Toast.makeText(getApplication(), "保存成功".toString(), Toast.LENGTH_SHORT).show();
                            downloadDatas();
                        } else {
                            Toast.makeText(getApplication(), msgMap.get("message").toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
    }

    /**
     * 回传到客户界面
     */
    private void passBack(){
        PassBackEvenbuss backPressEvenbus = new PassBackEvenbuss();
        backPressEvenbus.setIsBackPress(true);
        EventBus.getDefault().post(backPressEvenbus);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
