package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.other.wdSharedModule.CoverFlowAdapter;
import com.xxjr.xxjr.other.wdSharedModule.CoverFlowView;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.CommomAcitivity;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WD_ChangeModulActivity extends CommomAcitivity {
    private ImageView mIvBackGroud;
    private CoverFlowView mCoverFlowView;
    private String tempId;
    private List<Map<String,Object>> mapList = new ArrayList<>();
    private MyCoverFlowAdapter adapter;
    private LinearLayout mLlTitle;
    private int currenPostion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wd__change_modul);
        SetTitleBar.setTitleText(WD_ChangeModulActivity.this,"微店模板","保存模板");
        initViews();
        setListener();
    }

    private void initViews() {
        mLlTitle = (LinearLayout) findViewById(R.id.threetitleNoStatusNar_rl_title);
        mCoverFlowView = (CoverFlowView) findViewById(R.id.wdchangModule_iv_coverflow);
        mIvBackGroud = (ImageView) findViewById(R.id.wdchangModule_iv_background);
        mapList = WD_SharedActivity.bitmapList;

        adapter = new MyCoverFlowAdapter();
        mCoverFlowView.setAdapter(adapter);
    }

    public class MyCoverFlowAdapter extends CoverFlowAdapter {
        public int getCount(){
            return mapList.size();
        }

        public  Bitmap getImage(int position){
            return (Bitmap) mapList.get(position).get("bitmap");
        }

        @Override
        public void getPosition(int position) {
            currenPostion = position;
            mIvBackGroud.setImageBitmap((Bitmap) mapList.get(position).get("bitmap"));
            mLlTitle.setBackgroundColor(Color.parseColor(mapList.get(position).get("btnColor").toString()));
        }
    }

    private void setListener() {
        findViewById(R.id.ThreeTitleBar_ll_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempId = mapList.get(currenPostion).get("tempId").toString();
                updataMudule(Urls.WD_ALTER_CARD);

            }
        });
    }

    private void updataMudule(String url){
        AppUtil.showProgress(WD_ChangeModulActivity.this, ConstantUtils.DIALOG_SHOW);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        Map<String, String> params = httpRequestUtil.params;
        params.put("tempId",tempId);
        params.put("tempFlag",true+"");
        params.put("isAdd",false+"");
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String,Object> map = JsonUtil.getInstance().json2Object(response.toString(),Map.class);
                        if ((Boolean) map.get("success")){
                            Intent intent = new Intent();
                            intent.putExtra("tempId",tempId);
                            setResult(RESULT_OK,intent);
                            finish();
                        }else {
                            Toast.makeText(WD_ChangeModulActivity.this, map.get("messege").toString(), Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
