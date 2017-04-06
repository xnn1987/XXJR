package com.xxjr.xxjr.activity;

import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.adapter.WDYixiangAdapter;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.custview.Pull2RefreshListView;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WDYixiangActivity02 extends SlidBackActivity implements
        Pull2RefreshListView.OnRefreshListener,Pull2RefreshListView.OnLoadMoreListener{

    private Pull2RefreshListView mLvMain;
    private boolean isFirst = true;
    private int currentPage = 0;
    private  List<Map<String,Object>> mList = new ArrayList<>();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case ConstantUtils.LOADING:
                    if (isFirst) {
                        AppUtil.showProgress(WDYixiangActivity02.this, ConstantUtils.DIALOG_SHOW);
                        isFirst = false;
                    }
                    break;
                case ConstantUtils.LOAD_SUCCESS:
                    AppUtil.dismissProgress();
                    handler.sendEmptyMessage(ConstantUtils.STOP_FRESH);
                    break;
                case ConstantUtils.LOAD_ERROR:
                    AppUtil.errodDoanload(ConstantUtils.DIALOG_ERROR);
                    currentPage--;
                    handler.sendEmptyMessage(ConstantUtils.STOP_FRESH);
                    break;
                case ConstantUtils.FOOT_REFRESH:
                    downLoadDatas(Urls.WD_YIXIANG_KEHU_QUERY);
                    break;
                case ConstantUtils.HEAD_REFRESH:
                    currentPage = 0;
                    mList.clear();
                    downLoadDatas(Urls.WD_YIXIANG_KEHU_QUERY);
                    break;
                case ConstantUtils.STOP_FRESH:
                    mLvMain.stopAllRefresh();
                    break;
            }
        }
    };
    private ImageView mIvIcon;
    private WDYixiangAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wdyixiang02);
        SetTitleBar.setTitleText(this, "微店意向客户");
        initViews();
        setAdapter();
        downLoadDatas(Urls.WD_YIXIANG_KEHU_QUERY);

    }

    private void setAdapter() {
        adapter = new WDYixiangAdapter(getApplicationContext(),mList);
        mLvMain.setAdapter(adapter);
    }

    private void initViews() {
        mLvMain = (Pull2RefreshListView) findViewById(R.id.custApply_lv_main);
        mIvIcon = (ImageView) findViewById(R.id.custapply_iv_icon);
        mLvMain.setOnRefreshListener(this);
    }

    //下载公司产品列表数据
    private void downLoadDatas(String url) {
        handler.sendEmptyMessage(ConstantUtils.LOADING);
        currentPage++;
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        Map<String, String> params = httpRequestUtil.params;
        params.put("currentPage",currentPage+"");
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> map = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        List<Map<String, Object>> rowsList = (List<Map<String, Object>>) map.get("rows");
                        if (isFirst &&  rowsList.size()>ConstantUtils.EVERYPAGER_6){
                            mLvMain.setOnLoadListener(WDYixiangActivity02.this);
                        }
                        if (rowsList.size() == 0) {
                            currentPage--;
                            Toast.makeText(WDYixiangActivity02.this, ConstantUtils.NO_ANYTHING_DATA, Toast.LENGTH_SHORT).show();
                        } else {
                            mList.addAll(rowsList);
                            adapter.notifyDataSetChanged();
                            isDataNull();
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

    /**
     * 判断有没有数据，  如果有就像是lv   没有就显示图片
     */
    private void isDataNull(){
            mLvMain.setVisibility(View.VISIBLE);
            mIvIcon.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh() {
        handler.sendEmptyMessage(ConstantUtils.HEAD_REFRESH);
    }

    @Override
    public void onLoadMore() {
        handler.sendEmptyMessage(ConstantUtils.FOOT_REFRESH);
    }
}
