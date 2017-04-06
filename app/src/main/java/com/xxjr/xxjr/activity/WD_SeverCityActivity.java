package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lidroid.xutils.ViewUtils;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.adapter.WD_ChoiceFuWuCityAdapter;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.tongxun.ChineseToPinyinHelper;
import com.xxjr.xxjr.tongxun.SidebarView;
import com.xxjr.xxjr.tongxun.UserModel;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;
import com.ypy.eventbus.EventBus;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class WD_SeverCityActivity extends SlidBackActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private SidebarView mSvSidebar;
    private TextView mTvDialog;
    private ArrayList<UserModel> mCityList;
    private WD_ChoiceFuWuCityAdapter adapter;
    private String cityName;//  过来的城市
    private int count = 0;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case ConstantUtils.LOADING:
                    AppUtil.showProgress(WD_SeverCityActivity.this,ConstantUtils.DIALOG_SHOW);
                    break;
                case ConstantUtils.LOAD_SUCCESS:
//                    addHeadView();//   通讯录刷新完之后再刷新
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
        setContentView(R.layout.activity_wd__choice_fu_wu_city);
        SetTitleBar.setTitleText(WD_SeverCityActivity.this,"服务城市","保存");
        cityName = getIntent().getStringExtra("cityName");
        initViews();
        setListener();
        downLoadCity(Urls.WD_FUWU_CITY);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    private void setListener() {
        findViewById(R.id.ThreeTitleBar_ll_click).setOnClickListener(this);
    }

    private void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.wdfuwucity_lv_commonfriend);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSvSidebar = (SidebarView) findViewById(R.id.wdfuwucity_sidebarView);
        mTvDialog = (TextView) findViewById(R.id.wdfuwucity_dialog);


    }
    private void setChoice (){
        String[] split = cityName.split("、");
        for (int i = 0; i<split.length; i++){
            for (int j =0 ; j<mCityList.size(); j++){
                if (split[i].equals(mCityList.get(j).getCity())) {
                    adapter.statues[j] = true;
                    adapter.notifyItemChanged(j);
                }
            }
        }
    }

    private void downLoadCity(String url){
        handler.sendEmptyMessage(ConstantUtils.LOADING);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String,Object> map = JsonUtil.getInstance().json2Object(response.toString(),Map.class);
                        Map<String,Object> attrMap = (Map<String, Object>) map.get("attr");
                        List<Map<String,Object>> allCityList = (List<Map<String, Object>>) attrMap.get("allCity");
                        initViewConnact(allCityList);//搜索的通讯录

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.sendEmptyMessage(ConstantUtils.LOAD_ERROR);
                    }
                });
    }
    private void initViewConnact(List<Map<String, Object>> allCityList) {
        ViewUtils.inject(this);
        mSvSidebar.setTextView(mTvDialog);
        mCityList = new ArrayList<UserModel>();
        mCityList = getUserList(allCityList);

        Collections.sort(mCityList, new Comparator<UserModel>() {
            @Override
            public int compare(UserModel lhs, UserModel rhs) {
                if (lhs.getFirstLetter().equals("#")) {
                    return 1;
                } else if (rhs.getFirstLetter().equals("#")) {
                    return -1;
                } else {
                    return lhs.getFirstLetter().compareTo(rhs.getFirstLetter());
                }
            }
        });
        adapter = new WD_ChoiceFuWuCityAdapter(mCityList,this);
        mRecyclerView.setAdapter(adapter);
        setChoice();
        mSvSidebar.setOnLetterClickedListener(new SidebarView.OnLetterClickedListener() {
            @Override
            public void onLetterClicked(String str) {
                int position = adapter.getPositionForSection(str.charAt(0));
                mRecyclerView.scrollToPosition(position);
            }
        });
        handler.sendEmptyMessage(ConstantUtils.LOAD_SUCCESS);
    }

    private ArrayList<UserModel> getUserList(List<Map<String, Object>> allCityList) {
        ArrayList<UserModel> list = new ArrayList<UserModel>();//
        for (int i = 0; i < allCityList.size(); i++) {
            List<String> cityNamesList = (List<String>) allCityList.get(i).get("cityNames");
            for (int j = 0 ; j< cityNamesList.size();j++){
                String cityName = cityNamesList.get(j);
                UserModel userModel = new UserModel();
                String pinyin = ChineseToPinyinHelper.getInstance().getPinyin(cityName);
                String firstLetter = pinyin.substring(0, 1).toUpperCase();
                if (firstLetter.matches("[A-Z]")) {
                    userModel.setFirstLetter(firstLetter);
                } else {
                    userModel.setFirstLetter("#");
                }
                userModel.setCity(cityName);
                list.add(userModel);

            }
        }

        return list;
    }
    //   城市名称
    private String getCityName(){
        count = 0;
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<adapter.statues.length;i++){
            if (adapter.statues[i]){
                sb.append(mCityList.get(i).getCity()+"、");
                count++;
            }
            if (i==adapter.statues.length-1){
                if (!TextUtils.isEmpty(sb.toString())){
                    sb.deleteCharAt(sb.length()-1);
                }
            }
        }
        return sb.toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ThreeTitleBar_ll_click:
                String cityname = getCityName();
                if (count>5){
                    Toast.makeText(this, "城市选择不超过5个", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent();
                    intent.putExtra("cityname", cityname);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;
        }
    }
}
