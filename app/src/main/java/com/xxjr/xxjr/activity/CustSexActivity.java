package com.xxjr.xxjr.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

import java.util.Map;

public class CustSexActivity extends SlidBackActivity implements View.OnClickListener{
    private RelativeLayout sexMaleView = null;
    private RelativeLayout sexFemaleView = null;
    private String sex = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_sex);
        initView();

        String sex = this.getIntent().getStringExtra("sex");
        if("0".equals(sex)){
            ImageView saleView1 =  (ImageView)this.findViewById(R.id.cust_sex_male);
            saleView1.setVisibility(View.INVISIBLE);
            ImageView fesaleView1 =  (ImageView)this.findViewById(R.id.cust_sex_female);
            fesaleView1.setVisibility(View.VISIBLE);
            sex = "0";
        }
        SetListener();
        setTitleBack();
    }
    public void setTitleBack() {
        LinearLayout mLLRight = (LinearLayout) findViewById(R.id.ThreeTitleBar_ll_click);
        SetTitleBar.setTitleText(CustSexActivity.this, "性别", "保存");
        mLLRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDatas();
            }
        });
    }


    private void SetListener() {
        sexMaleView.setOnClickListener(this);
        sexFemaleView.setOnClickListener(this);
    }

    private void initView(){
        sexMaleView = (RelativeLayout)this.findViewById(R.id.cust_sex_view_male);
        sexFemaleView = (RelativeLayout)this.findViewById(R.id.cust_sex_view_female);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cust_sex_view_male:
                ImageView saleView =  (ImageView)this.findViewById(R.id.cust_sex_male);
                saleView.setVisibility(View.VISIBLE);

                ImageView fesaleView =  (ImageView)this.findViewById(R.id.cust_sex_female);
                fesaleView.setVisibility(View.INVISIBLE);
                sex = "1";
                break;
            case R.id.cust_sex_view_female:
                ImageView saleView1 =  (ImageView)this.findViewById(R.id.cust_sex_male);
                saleView1.setVisibility(View.INVISIBLE);

                ImageView fesaleView1 =  (ImageView)this.findViewById(R.id.cust_sex_female);
                fesaleView1.setVisibility(View.VISIBLE);
                sex = "0";
                break;

        }
    }

    private void saveDatas(){
        AppUtil.showProgress(this, ConstantUtils.DIALOG_SHOW);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.params.put("sex",sex);
        httpRequestUtil.jsonObjectRequestPostSuccess("/account/cust/insertInfo", new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        boolean success = (Boolean) map.get("success");
                        if (success) {
                            finish();
                        } else {
                            String message = map.get("message").toString();
                            Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show();
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


}
