package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.utils.DebugLog;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.Shared.Shared;
import com.xxjr.xxjr.utils.common.SlidBackActivity;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WDActivity02 extends SlidBackActivity implements View.OnClickListener{
    private LinearLayout mLlNoApply;
    private LinearLayout mLlFoot,mLlApplyDes;
    private ImageView mIvHead;
    private int hasCustApply = 0;

    private String bufferTimpId ;
    private Map<String,Object> wdInfoMap = new HashMap<>();
    private TextView mTvShopName, mTvDes, mTvDianZanCount, mTvLiuLanCount, mTvServeCity, mTvChanPinCount, mTvLiLv, mTvShenQingCount;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case ConstantUtils.LOADING:
                        AppUtil.showProgress(WDActivity02.this, ConstantUtils.DIALOG_SHOW);
                    break;
                case ConstantUtils.LOAD_SUCCESS:
                    AppUtil.dismissProgress();
                    break;
                case ConstantUtils.LOAD_ERROR:
                    AppUtil.errodDoanload(ConstantUtils.DIALOG_ERROR);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wei_dian02);
        SetTitleBar.setTitleText(this, "我的微店");
        initViews();
        setListener();
        dowloadDatas(Urls.WD_HOME_INIT);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (bufferTimpId!=MyApplication.tempId){
            dowloadDatas(Urls.WD_HOME_INIT);
        }
        if (MyApplication.isChangeWDCardActivity){
            dowloadDatas(Urls.WD_HOME_INIT);
            MyApplication.isChangeWDCardActivity = false;
        }

    }


    private void initViews() {
        mLlNoApply = (LinearLayout) findViewById(R.id.wd02_ll_noApply);
        mLlFoot = (LinearLayout) findViewById(R.id.wd02_ll_foot);
        mLlApplyDes = (LinearLayout) findViewById(R.id.wd02_ll_apply);
        mIvHead = (ImageView) findViewById(R.id.wd02_iv_head);
        mTvShopName = (TextView) findViewById(R.id.wd02_tv_shopname);
        mTvDes = (TextView) findViewById(R.id.wd02_tv_des);
        mTvDianZanCount = (TextView) findViewById(R.id.wd02_tv_dianzanCount);
        mTvLiuLanCount = (TextView) findViewById(R.id.wd02_tv_liulanCount);
        mTvServeCity = (TextView) findViewById(R.id.wd02_tv_fuwudiqu);
        mTvChanPinCount = (TextView) findViewById(R.id.wd02_tv_chanpinCount);
        mTvLiLv = (TextView) findViewById(R.id.wd02_tv_lilv);
        mTvShenQingCount = (TextView) findViewById(R.id.wd02_tv_shenqingCount);

    }

    private void setListener() {
        findViewById(R.id.Weidian_ll_custApply).setOnClickListener(this);
        findViewById(R.id.Weidian_ll_WDproduce).setOnClickListener(this);
        findViewById(R.id.Weidian_ll_WDShared).setOnClickListener(this);
        findViewById(R.id.Weidian_ll_WDOrder).setOnClickListener(this);
        findViewById(R.id.Weidian_ll_setCard).setOnClickListener(this);
        findViewById(R.id.Weidian_ll_BussyCard).setOnClickListener(this);
        findViewById(R.id.Weidian_ll_zhanyeQutu).setOnClickListener(this);
        findViewById(R.id.Weidian_ll_WDTontji).setOnClickListener(this);
        findViewById(R.id.wd02_btn_more).setOnClickListener(this);//  底部的查看更多
        mIvHead.setOnClickListener(this);
    }

    private void dowloadDatas(String url) {
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map1 = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        if ((Boolean) map1.get("success")) {
                            wdInfoMap.clear();
                            wdInfoMap.putAll(map1);
                            setData(wdInfoMap);
                            downloadQueryList(Urls.WD_YIXIANG_KEHU_QUERY);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
    }
    private void setData(Map<String, Object> map) {
        Map<String, Object> attrMap = (Map<String, Object>) map.get("attr");
        Map<String, Object> wdInfoMap = (Map<String, Object>) attrMap.get("wdInfo");
        MyApplication.tempId = wdInfoMap.get("tempId").toString();//  微店模板
        bufferTimpId = MyApplication.tempId;
        String headUrl = Urls.RANQING + Urls.BUSSINESS_CUSTCARD_HEADICON + wdInfoMap.get("headImage");
        MyApplication.imageLoader.displayImage(headUrl,mIvHead, MyApplication.optionsHead);
        mTvShopName.setText(wdInfoMap.get("shopName").toString());
        mTvDes.setText(wdInfoMap.get("shopDesc").toString());
        mTvDianZanCount.setText(wdInfoMap.get("clickCount").toString());
        mTvLiuLanCount.setText(wdInfoMap.get("browseCount").toString());

        mTvShenQingCount.setText(wdInfoMap.get("applyCount").toString());
        mTvServeCity.setText("服务地区：" + attrMap.get("serviceArea").toString());
        mTvChanPinCount.setText(attrMap.get("proCount").toString());
        Map<String, Object> rateRangeMap = (Map<String, Object>) attrMap.get("rateRange");
        mTvLiLv.setText(rateRangeMap.get("rateMin").toString() + "%-" + rateRangeMap.get("rateMax").toString()+"%");
        //  存储微店分享信息
        new Shared().spWeidianShared(this,mTvShopName.getText().toString(),mTvDes.getText().toString(),
                Urls.BUSSINESS_CUSTCARD_HEADICON + wdInfoMap.get("headImage"),attrMap.get("serviceArea").toString());
    }
    //  首页列表
    private void downloadQueryList(String url){
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        Map<String, String> params = httpRequestUtil.params;
        params.put("everyPage",1+"");
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String,Object> map = JsonUtil.getInstance().json2Object(response.toString(),Map.class);
                        setQueryDatas(map);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
    }
    //下载公司产品列表数据
    private void downLoadDatas(String url) {
        handler.sendEmptyMessage(ConstantUtils.LOADING);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        List<Map<String, Object>> rowsList = (List<Map<String, Object>>) map.get("rows");
                        Intent intent = new Intent();
                        if (rowsList.size()==0){
                            intent.setClass(getApplicationContext(),WDProduceActivity01.class);
                        }else {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("rowsList", (Serializable) rowsList);
                            intent.putExtras(bundle);
                            intent.setClass(getApplicationContext(),WDProduceActivity02.class);
                        }
                        startActivity(intent);
                        handler.sendEmptyMessage(ConstantUtils.LOAD_SUCCESS);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.sendEmptyMessage(ConstantUtils.LOAD_ERROR);
                    }
                });
    }
    //  最近申请  的记录
    private void setQueryDatas(Map<String, Object> map) {
        final List<Map<String,Object>>rowsList = (List<Map<String, Object>>) map.get("rows");
        hasCustApply = rowsList.size();
        if (rowsList==null || rowsList.size()==0){
            mLlNoApply.setVisibility(View.VISIBLE);
            mLlFoot.setVisibility(View.GONE);
        }else {
            mLlNoApply.setVisibility(View.GONE);
            mLlFoot.setVisibility(View.VISIBLE);
            for (int i = 0; i < rowsList.size(); i++) {
                View view = getLayoutInflater().inflate(R.layout.wd_yixiang_kehu_item,null);
                LinearLayout mLlItem = (LinearLayout) view.findViewById(R.id.yixiangkehu_ll_item);
                TextView mUserName = (TextView) view.findViewById(R.id.WD02_tv_name);
                TextView mOrgName = (TextView) view.findViewById(R.id.wd02_tv_companyName);
                LinearLayout mLlTel = (LinearLayout) view.findViewById(R.id.WD02_ll_tel);//  监听
                TextView mTvTime = (TextView) view.findViewById(R.id.wd02_tv_time);
                TextView mTvcontent = (TextView) view.findViewById(R.id.wd02_tv_content);

                mUserName.setText(rowsList.get(i).get("applyName").toString());
                mOrgName.setText(rowsList.get(i).get("productName").toString());
                mTvTime.setText(rowsList.get(i).get("createTime").toString());
                mTvcontent.setText(rowsList.get(i).get("applyDesc").toString());
                final int finalI = i;
                mLlTel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + rowsList.get(finalI).get("telephone").toString()));
                        startActivity(intent);//内部类
                    }
                });
                mLlApplyDes.addView(view);

            }
        }
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.wd02_iv_head:
                intent.setClass(getApplicationContext(),WDCardActivity.class);
                intent.putExtra("isAdd", false);
                intent.putExtra(ConstantUtils.WDCARD_FROM, ConstantUtils.WDCARD_FROM_ACTIVITY);
                break;
            case R.id.Weidian_ll_custApply:
                if (hasCustApply >0){
                    intent.setClass(getApplicationContext(),WDYixiangActivity02.class);
                }else {
                    intent.setClass(getApplicationContext(), WDYiXiangActivity01.class);
                }
                break;
            case R.id.Weidian_ll_WDproduce:
                downLoadDatas(Urls.WD_PRODUCE_List);
                return;
            case R.id.Weidian_ll_WDShared:
                intent.setClass(getApplicationContext(),WD_SharedActivity.class);
                intent.putExtra("shopeName",mTvShopName.getText().toString());
                intent.putExtra("wdDes",mTvDes.getText().toString());
                break;
            case R.id.Weidian_ll_WDOrder:
                intent.setClass(getApplicationContext(), WDBestSellersActivity.class);
                break;
            case R.id.Weidian_ll_setCard:
                wdCardDownLoad();
                return;
            case R.id.Weidian_ll_BussyCard:
                intent.setClass(getApplicationContext(),BussyCardActivity.class);
                break;
            case R.id.Weidian_ll_zhanyeQutu:
                intent.setClass(getApplicationContext(),QuTuActivity.class);
                break;
            case R.id.Weidian_ll_WDTontji:
                intent.setClass(getApplicationContext(),WDTongJiActivity.class);
                break;
            case R.id.wd02_btn_more:
                intent.setClass(getApplicationContext(),WDYixiangActivity02.class);
                break;
        }
        startActivity(intent);
    }
    //  判断  微店名片是否有数据
    private void wdCardDownLoad() {
        handler.sendEmptyMessage(ConstantUtils.LOADING);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.jsonObjectRequestPostSuccess(Urls.WD_CARD_SETTING_QUERY,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        if ((boolean) map.get("success")) {
                            Map<String, Object> attrMap = (Map<String, Object>) map.get("attr");
                            if ((boolean) attrMap.get("haveWdCard")) {
                                Intent intent = new Intent(getApplicationContext(),WDCardActivity02.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("attrMap", (Serializable) attrMap);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }else {
                                Intent intent = new Intent(getApplicationContext(),WDCardSetActivity.class);
                                intent.putExtra(ConstantUtils.WDCARD_ISFIRST_WD_CARD,true);
                                startActivity(intent);
                            }
                        } else {
                            Toast.makeText(WDActivity02.this, map.get("message").toString(), Toast.LENGTH_SHORT).show();
                        }
                        handler.sendEmptyMessage(ConstantUtils.LOAD_SUCCESS);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.sendEmptyMessage(ConstantUtils.LOAD_ERROR);
                    }
                });
    }

}
