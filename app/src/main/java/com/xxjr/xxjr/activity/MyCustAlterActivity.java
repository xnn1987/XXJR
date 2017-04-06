package com.xxjr.xxjr.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.bean.PassBackEvenbuss;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;
import com.ypy.eventbus.EventBus;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyCustAlterActivity extends SlidBackActivity implements View.OnClickListener {
    private EditText mEtRealName;
    private EditText mEtNickName;
    private EditText mEtTelephone;
    private LinearLayout custTagLayou;
    private EditText mEtDescText;
    private LinearLayout myCustTag;
    private PopupWindow mPopupWindow;
    private Map<String, Object> mUserInfoMap = new HashMap<>();
    private String realName;

    private void initView() {
        //标题
        mEtRealName = (EditText) this.findViewById(R.id.myCustAlter_et_realName);
        mEtNickName = (EditText) this.findViewById(R.id.myCustAlter_et_nickNmae);
        mEtTelephone = (EditText) this.findViewById(R.id.myCustAlter_et_telephone);
        custTagLayou = (LinearLayout) this.findViewById(R.id.MyCustAlter_ll_tag);
        mEtDescText = (EditText) this.findViewById(R.id.myCustAlter_et_contactDesc);
        myCustTag = (LinearLayout) this.findViewById(R.id.MyCustAlter_Ll_TagBig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cust_alter);
        initView();
        getIntentDatas();
        setTitleBack();
        setListener();
        if (!realName.equals("")) {
            downloadInitDate();
        }
        initPopupWindow();
    }

    public void setTitleBack() {
        SetTitleBar.setTitleText(MyCustAlterActivity.this, "我的客户", "保存");
        findViewById(R.id.ThreeTitleBar_ll_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUserInfoMap.get("contactId")!=null) {
                    alterUserInfon(Urls.UPDATE_USERINFO);
                }else {
                    addnewCust(Urls.ADD_CUST);
                }
            }
        });
    }
    private void setListener() {
        myCustTag.setOnClickListener(this);
    }

    private void initPopupWindow() {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mVPopupMenu = layoutInflater.inflate(R.layout.my_custom_popupwindow, null);
        mPopupWindow = new PopupWindow(mVPopupMenu, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.MyCustAlter_Ll_TagBig:
                if (mUserInfoMap.size()!=0){
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), MyTagActivity.class);
                    intent.putExtra("contactId", mUserInfoMap.get("contactId").toString());
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),"请先保存在选择标签",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    private void downloadInitDate() {
        AppUtil.showProgress(this, ConstantUtils.DIALOG_SHOW);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.params.put("realName", realName);
        httpRequestUtil.jsonObjectRequestPostSuccess("/account/contact/queryList",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        List<Map<String, Object>> infoList = (List<Map<String, Object>>) map.get("rows");
                        if (infoList != null && infoList.size() > 0) {
                            Map<String, Object> infoMap = infoList.get(0);
                            mUserInfoMap.clear();
                            mUserInfoMap.putAll(infoMap);
//                            concatMap = infoMap;
                            if (infoMap.get("realName") != null) {
                                mEtRealName.setText(infoMap.get("realName") + "");
                            }
                            if (infoMap.get("nickName") != null) {
                                mEtNickName.setText(infoMap.get("nickName") + "");
                            }
                            if (infoMap.get("telephone") != null) {
                                mEtTelephone.setText(infoMap.get("telephone") + "");
                            }
                            if (infoMap.get("tagNames") != null) {
                                String[] tagNames = infoMap.get("tagNames").toString().split(",");
                                for (int i = 0; i < tagNames.length; i++) {
                                    TextView tagTextView = new TextView(getApplicationContext());
                                    tagTextView.setTextSize(14);
                                    tagTextView.setPadding(15, 0, 15, 0);
                                    if (i <= 2) {
                                        tagTextView.setText(tagNames[i]);
                                        custTagLayou.addView(tagTextView);
                                    } else if (i == 3) {
                                        tagTextView.setText("••••••");
                                        custTagLayou.addView(tagTextView);
                                    }
                                }
                            }
                            mEtDescText.setText(infoMap.get("contactDesc") + "");
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
     * b保存信息
     * @param url
     */
    private void alterUserInfon(String url) {
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.params.put("contactId", mUserInfoMap.get("contactId").toString());
        httpRequestUtil.params.put("realName", mEtRealName.getText().toString());
        httpRequestUtil.params.put("nickName", mEtNickName.getText().toString());
        httpRequestUtil.params.put("telephone", mEtTelephone.getText().toString());
        httpRequestUtil.params.put("contactDesc", mEtDescText.getText().toString());
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        if ((boolean) map.get("success")) {
                            passBack();
                            finish();

                        } else {
                            Toast.makeText(getApplicationContext(), map.get("message").toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
    }

    /**
     * 添加新客户
     * @param url
     */
    private void addnewCust(String url){
        String realName = mEtRealName.getText().toString();
        String nickName = mEtNickName.getText().toString();
        String telephone = mEtTelephone.getText().toString();
        String concatDesc = mEtDescText.getText().toString();
        if (TextUtils.isEmpty(telephone)){
            Toast.makeText(getApplication(), "请输入手机号码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(realName)){
            Toast.makeText(getApplication(), "请输真实姓名", Toast.LENGTH_SHORT).show();
            return;
        }
        AppUtil.showProgress(this, ConstantUtils.DIALOG_SHOW);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.params.put("realName",realName);
        httpRequestUtil.params.put("nickName",nickName);
        httpRequestUtil.params.put("telephone",telephone);
        httpRequestUtil.params.put("contactDesc",concatDesc);
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        boolean success = (Boolean)map.get("success");
                        if(success){
                            passBack();
                            finish();

                        }else{
                            String message = map.get("message").toString();
                            Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show();
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

    /**
     * 回传到客户界面
     */
    private void passBack(){
        PassBackEvenbuss backPressEvenbus = new PassBackEvenbuss();
        backPressEvenbus.setIsBackPress(true);
        EventBus.getDefault().post(backPressEvenbus);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        custTagLayou.removeAllViews();
        downloadInitDate();
    }

    public void getIntentDatas() {
        Intent intent = getIntent();
        realName = intent.getStringExtra("realName");
        if (realName==null){
            realName = "";
        }
        //从通讯录进来
        if (intent.getStringExtra("name")!=null && intent.getStringExtra("telephone")!=null){
            mEtRealName.setText(intent.getStringExtra("name"));
            mEtTelephone.setText(intent.getStringExtra("telephone"));
        }
    }
}
