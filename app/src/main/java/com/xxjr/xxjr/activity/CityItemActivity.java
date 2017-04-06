package com.xxjr.xxjr.activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.adapter.CityAdapter;
import com.xxjr.xxjr.bean.CityBean;
import com.xxjr.xxjr.bean.SingleCityEvenbus;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;
import com.ypy.eventbus.EventBus;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class CityItemActivity extends SlidBackActivity {

    private ListView mLvCity;
    private Bundle bundle;
    private int position;
    private CityBean cityBean;
    private String TAG = "CityItemActivity";
    private List<CityBean.AttrEntity.AreasEntity.CitysEntity> mCitysList;
    private String fromFlag;//标记看从哪里来order  custInfo
    private AppUtil appUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        SetTitleBar.setTitleText(CityItemActivity.this, "城市");
        initViews();
        bundle = getIntentDatas();
        cityBean = (CityBean) bundle.getSerializable("CityBean");
        fromFlag = bundle.getString("fromFlag");//标记看从哪里来order  custInfo
        position = bundle.getInt("position");
        mCitysList = cityBean.getAttr().getAreas().get(position).getCitys();
        lvSetAdapter();
        lvSetListener();

    }
    private void lvSetListener() {
        mLvCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (fromFlag.equals("order")) {//从定订单来的城市
                    Intent intent = new Intent(getApplicationContext(), SalaryOrderActivity.class);
                    cityCallBack(mCitysList.get(position).getNameCn(), mCitysList.get(position).getCode());
                    startActivity(intent);
                } else if (fromFlag.equals("custInfo")) {//从个人中心来的
                    uploadCity(Urls.CUST_INSER_INFO,position);
                }else if (fromFlag.equals("WDCardActivity")){
                    cityCallBack(cityBean.getAttr().getAreas().get(position).getProvice()+
                            mCitysList.get(position).getNameCn(),mCitysList.get(position).getCode());
                    Intent intent = new Intent(getApplicationContext(),WDCardActivity.class);
                    startActivity(intent);
                }
                /*else if (fromFlag.equals("bankInfo")){
                    Intent intent = new Intent(getApplicationContext(),WithdrawBankInfoActivity.class);
                    SingleCityEvenbus singleCityEvenbus = new SingleCityEvenbus();
                    singleCityEvenbus.setCityName(mCitysList.get(position).getNameCn());
                    singleCityEvenbus.setCityCode(mCitysList.get(position).getCode());
                    EventBus.getDefault().post(singleCityEvenbus);
                    startActivity(intent);
                }*/
            }
        });
    }

    /**
     * 回调城市
     * @param cityName
     * @param CityCode
     */
    private void cityCallBack(String cityName,String CityCode){
        SingleCityEvenbus singleCityEvenbus = new SingleCityEvenbus();
        singleCityEvenbus.setCityName(cityName);
        singleCityEvenbus.setCityCode(CityCode);
        EventBus.getDefault().post(singleCityEvenbus);
        finish();
    }

    private void lvSetAdapter() {
        CityAdapter adapter = new CityAdapter(getApplicationContext(),mCitysList);
        mLvCity.setAdapter(adapter);
    }

    private void initViews() {
        mLvCity = (ListView) findViewById(R.id.City_lv_city);
    }
    public Bundle getIntentDatas(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        return bundle;
    }

    /**
     * 上传城市列表
     * @param url
     * @param position
     */
    private void uploadCity(String url,int position){
        appUtil.showProgress(this, "正在加载数据，请稍候...");
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        final String pro=mCitysList.get(position).getCode().substring(0,2);
        final String city = mCitysList.get(position).getCode();
        httpRequestUtil.params.put("pro",pro);
        httpRequestUtil.params.put("city",city);
        httpRequestUtil.jsonObjectRequestPostSuccess(url, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        boolean success = (Boolean) map.get("success");
                        if (success) {
                            Log.e(TAG, pro + "======"+city);
                            Log.e(TAG, response.toString());
                            Intent intent = new Intent();
                            intent.setClass(getApplicationContext(), CustInfoActivity.class);
                            startActivity(intent);

                        } else {
                            String message = map.get("message").toString();
                            Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show();
                        }
                        appUtil.dismissProgress();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        appUtil.errodDoanload("网络不稳定");
                    }
                });
    }


}
