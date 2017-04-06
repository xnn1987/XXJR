package com.xxjr.xxjr.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.adapter.ProviceAdapter;
import com.xxjr.xxjr.application.MyApplication;
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

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CityActivity extends SlidBackActivity implements View.OnClickListener {

    private ListView mLvProvice;
    private List<CityBean.AttrEntity.AreasEntity> mProviceList = new ArrayList<>();
    private ProviceAdapter adapter;
    private CityBean cityBean;
    private Button mBtnBeijing, mBtnShanghai, mBtnguangzhou, mBtnshenzhen,
            mBtnwuhan, mBtnchangsha, mBtntianjin, mBtnchongqing;
    private String fromFlag;
    private String TAG= "CityActivity";
    private AppUtil appUtil;
    private TextView mTvLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provice);
        SetTitleBar.setTitleText(CityActivity.this, "城市");
        getIntentDatas();
        initViews();
        headViewInitViews();
        lvSetAdapterDatas();
        lvSetListener();
        SharedPreferences sp = getSharedPreferences("city", Context.MODE_PRIVATE);
        String cityItem = sp.getString("cityItem", "");
        if (cityItem.equals("")) {
            downloadingDatas(Urls.RANQING + Urls.CITY_LIEBIAO);
        }else {
            getJsonCity(cityItem);
        }

    }

    public  void getCity(String json){
        File path = new File(Environment.getExternalStorageDirectory(),"city");
        File file = new File(path,"cityJson.json");
        if (!path.exists()){
            path.mkdirs();
        }
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(json);
            bufferedWriter.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedWriter != null){
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }



    private void lvSetAdapterDatas() {
        adapter = new ProviceAdapter(getApplicationContext(), mProviceList);
        mLvProvice.setAdapter(adapter);

    }

    private void initViews() {
        mLvProvice = (ListView) findViewById(R.id.City_lv_provice);
        mTvLocation = (TextView) findViewById(R.id.city_tv_location);
        mTvLocation.setText(MyApplication.city);
    }


    private void headViewInitViews() {
        View view = getLayoutInflater().inflate(R.layout.hot_city, null);
        mLvProvice.addHeaderView(view);


        mBtnBeijing = (Button) view.findViewById(R.id.hotCity_btn_beijing);
        mBtnShanghai = (Button) view.findViewById(R.id.hotCity_btn_shanghai);
        mBtnguangzhou = (Button) view.findViewById(R.id.hotCity_btn_guangzhou);
        mBtnshenzhen = (Button) view.findViewById(R.id.hotCity_btn_shenzhen);
        mBtnwuhan = (Button) view.findViewById(R.id.hotCity_btn_wuhan);
        mBtnchangsha = (Button) view.findViewById(R.id.hotCity_btn_changsha);
        mBtntianjin = (Button) view.findViewById(R.id.hotCity_btn_tianjin);
        mBtnchongqing = (Button) view.findViewById(R.id.hotCity_btn_chongqing);

        mBtnBeijing.setOnClickListener(this);
        mBtnShanghai.setOnClickListener(this);
        mBtnguangzhou.setOnClickListener(this);
        mBtnshenzhen.setOnClickListener(this);
        mBtnwuhan.setOnClickListener(this);
        mBtnchangsha.setOnClickListener(this);
        mBtntianjin.setOnClickListener(this);
        mBtnchongqing.setOnClickListener(this);
    }


    private void lvSetListener() {
        mLvProvice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), CityItemActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("position", position - 1);
                bundle.putSerializable("CityBean", cityBean);
                bundle.putString("fromFlag", fromFlag);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
    }

    private void downloadingDatas(String url) {
        StringRequest mRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        SharedPreferences sp = getSharedPreferences("city",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("cityItem", response.toString());
                        editor.commit();
                        getJsonCity(response);

                        getCity(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        MyApplication.mQueue.add(mRequest);
    }


    private void getJsonCity(String response){
        Gson gson = new Gson();
        cityBean = gson.fromJson(response, CityBean.class);
        List<CityBean.AttrEntity.AreasEntity> mBufferCityList = cityBean.getAttr().getAreas();
        mProviceList.addAll(mBufferCityList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hotCity_btn_beijing:
                returnCityDatas("北京", "1100");//返回城市信息
                break;
            case R.id.hotCity_btn_shanghai:
                returnCityDatas("上海市", "3101");//返回城市信息
                break;
            case R.id.hotCity_btn_guangzhou:
                returnCityDatas("广州市", "4401");//返回城市信息
                break;
            case R.id.hotCity_btn_shenzhen:
                returnCityDatas("深圳市", "4403");//返回城市信息
                break;
            case R.id.hotCity_btn_wuhan:
                returnCityDatas("武汉市", "4201");//返回城市信息
                break;
            case R.id.hotCity_btn_changsha:
                returnCityDatas("长沙市", "4301");//返回城市信息
                break;
            case R.id.hotCity_btn_tianjin:
                returnCityDatas("天津市", "1200");//返回城市信息
                break;
            case R.id.hotCity_btn_chongqing:
                returnCityDatas("重庆市", "5000");//返回城市信息
                break;
        }
    }

    public void getIntentDatas() {
        Intent intent = getIntent();
        fromFlag = intent.getStringExtra("fromFlag");//  应该有order从交单那边来，custInfo从消息那边来,bankInfo從銀行卡傳過來
    }

    /**
     * 返回城市相对应的信息的，   有交单来到城市和  个人信息来到城市
     */
    private void returnCityDatas(String cityName, String CityCode) {
        if (fromFlag.equals("order")) {
            cityCallBack(cityName, CityCode);
        }else if (fromFlag.equals("custInfo")){
            uploadCity(Urls.CUST_INSER_INFO,CityCode);
        }else if (fromFlag.equals("bankInfo")){
            cityCallBack(cityName,CityCode);
        }else if (fromFlag.equals("HomeFm")){
            cityCallBack(cityName,CityCode);
        }else if (fromFlag.equals("WDCardActivity")){
            cityCallBack(cityName,CityCode);
        }
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

    /**
     * 上传城市列表
     *
     * @param url
     * @param code 城市列表代码
     */
    private void uploadCity(String url, String code) {
        appUtil.showProgress(this, "正在加载数据，请稍候...");
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        final String pro = code.substring(0,2);
        final String city =code ;
        httpRequestUtil.params.put("pro", pro);
        httpRequestUtil.params.put("city", city);
        httpRequestUtil.jsonObjectRequestPostSuccess(url, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        boolean success = (Boolean) map.get("success");
                        if (success) {
                            Log.e(TAG, pro + "======" + city);
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
