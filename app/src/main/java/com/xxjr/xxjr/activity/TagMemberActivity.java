package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.adapter.ConcatMyTagAdpter;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.other.FlowTagView.Tag;
import com.xxjr.xxjr.other.FlowTagView.TagListView;
import com.xxjr.xxjr.other.FlowTagView.TagView;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TagMemberActivity extends SlidBackActivity {

    private List<Map<String,Object>> custTagList = new ArrayList<Map<String,Object>>();
    private ConcatMyTagAdpter adappter;
    private ListView mLvHome;
    private static List<Map<String, Object>> rowsList = null;
    private View mytagView;
    private View defaultTagView;
    private TagListView mTagListView;//标签包裹
    private final List<String> mTitleList = new ArrayList<>();//标签名字
    private final List<Tag> mTags = new ArrayList<Tag>();//标签

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cust_myt_tag);
        setTitleBack();
        initViews();
        ListViewAddHeadView();
        lvSetAdapter();
        lvSetListener();
        initDate();
    }


    public void setTitleBack() {
        TextView mTvSave = (TextView) findViewById(R.id.ThreeTitleBar_tv_click);
        SetTitleBar.setTitleText(TagMemberActivity.this, "标签成员", "新建");
        mTvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ConcatAddTagActivity.class);
                intent.putExtra("tagId", "");
                intent.putExtra("tagName","");
                startActivityForResult(intent,2);
            }
        });
    }

    private void lvSetAdapter() {
        adappter = new ConcatMyTagAdpter(this.getApplicationContext(), custTagList);
        mLvHome.setAdapter(adappter);
    }
    private void lvSetListener() {
        mLvHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position>0) {
                    Intent intent = new Intent(getApplicationContext(), ConcatAddTagActivity.class);
                    intent.putExtra("tagId", custTagList.get(position - 1).get("tagId").toString());
                    intent.putExtra("tagName", custTagList.get(position - 1).get("tagName").toString());
                    startActivityForResult(intent, 2);
                }
            }
        });
        mTagListView.setOnTagClickListener(new TagListView.OnTagClickListener() {


            @Override
            public void onTagClick(TagView tagView, Tag tag) {
                //TODO 怎么添加tagID
                Intent intent = new Intent(getApplicationContext(),ConcatAddTagActivity.class);
                intent.putExtra("tagId", "");
                intent.putExtra("tagName",tag.getTitle() );
                startActivityForResult(intent,2);
            }
        });
    }

    private void initViews() {
        mLvHome = (ListView) this.findViewById(R.id.my_concat_cust_tag_list);
        mytagView = LayoutInflater.from(this.getApplicationContext()).inflate(R.layout.head_concat_my_tag, null);
        defaultTagView = LayoutInflater.from(this.getApplicationContext()).inflate(R.layout.concat_default_tag, null);
        mTagListView = (TagListView) defaultTagView.findViewById(R.id.ConcatDefaltTag_tlv_tag);
    }

    private void ListViewAddHeadView(){
        mLvHome.addHeaderView(mytagView);
        mLvHome.addFooterView(defaultTagView);
    }


    private void initDate(){
        AppUtil.showProgress(this, ConstantUtils.DIALOG_SHOW);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.jsonObjectRequestPostSuccess("/account/tag/queryList",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        custTagList.clear();
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        rowsList = (List<Map<String, Object>>) map.get("rows");
                        custTagList.addAll(rowsList);

                        Map<String, Object> attrMap = JsonUtil.getInstance().json2Object(map.get("attr").toString(), Map.class);
                        List<String> defaultTagList = (List)attrMap.get("defaultTags");
                        setUpData(defaultTagList);//增加数据
                        mTagListView.setTags(mTags);
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
    private void setUpData(List<String> list) {
        mTags.clear();
        for (int i = 0; i < list.size(); i++) {
            Tag tag = new Tag();
            tag.setId(i);
            tag.setChecked(true);
            tag.setTitle(list.get(i));
            mTags.add(tag);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK){
            initDate();
        }
    }
}
