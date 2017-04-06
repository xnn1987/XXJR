package com.xxjr.xxjr.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.adapter.WeiDianTongJiAdapter;
import com.xxjr.xxjr.application.MyApplication;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.utils.MapSortUtil;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.CommomAcitivity;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class WDTongJiActivity extends CommomAcitivity {
    private String[] months = {"一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"};
    private TabLayout mTlTongJi;
    private ViewPager mVpTongJi;
    private Map<String,Object> attrMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wei_dian_tong_ji);
        SetTitleBar.setTitleText(WDTongJiActivity.this, "微店统计");
        initViews();
        downTongji(Urls.WD_TONGJI);

    }

    private void setAdapter() {
        WeiDianTongJiAdapter mVpAdapter = new WeiDianTongJiAdapter
                (getSupportFragmentManager(),attrMap);
        mVpTongJi.setAdapter(mVpAdapter);

        mTlTongJi.setupWithViewPager(mVpTongJi);
        setTabView();
    }

    private void setTabView() {
        /*int screenWidth ;
        for (int i= 0 ;i< mTlTongJi.getTabCount(); i++ ){
            TabLayout.Tab tab = mTlTongJi.getTabAt(i);
            if (tab!=null){
                tab.setCustomView(getTabView(i));
                screenWidth+= 60*MyApplication.density;//  60为tv的宽度
            }
        }*/
        Map<String, Object> countDataMap = new MapSortUtil().sortMap((Map<String, Object>) attrMap.get("countData"));
        Set<String> set = countDataMap.keySet();
        Iterator<String> iterator = set.iterator();
        int i = 0;
        int screenWidth ;
        while (iterator.hasNext()){
            TabLayout.Tab tab = mTlTongJi.getTabAt(i);
            if (tab!=null){
                View v = LayoutInflater.from(this).inflate(R.layout.tablayout_tabchild, null);
                TextView tv = (TextView) v.findViewById(R.id.tablayout_tabChild);
                String month  = months[Integer.parseInt(iterator.next().toString())];
                tv.setText(month);
                tab.setCustomView(tv);
//                screenWidth+= 60*MyApplication.density;//  60为tv的宽度
            }
            i++;
        }

        AppUtil.dismissProgress();

    }

    private void initViews() {
        mTlTongJi = (TabLayout) findViewById(R.id.tongji_TabLayout);
        mVpTongJi = (ViewPager) findViewById(R.id.bestseller_ViewPager);
    }

    private void downTongji(String url){
        AppUtil.showProgress(WDTongJiActivity.this, ConstantUtils.DIALOG_SHOW);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String,Object> map = (Map<String, Object>) JsonUtil.getInstance().json2Object(response.toString(),Map.class);
                        Map<String,Object> attr = (Map<String, Object>) map.get("attr");
                        attrMap.putAll(attr);
                        setAdapter();
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
