package com.xxjr.xxjr.activity.zx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.xxjr.xxjr.R;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.TextUtil;
import com.xxjr.xxjr.utils.common.SlidBackActivity;
import com.xxjr.xxjr.utils.network.MapCallback;
import com.xxjr.xxjr.utils.network.MyOkHttpUtils;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZxCreditRecordDetailActivity extends SlidBackActivity {
    private String reportId = "";
    private String creditId = "";
    private int overdue  = 0;
    private TextView mTvTitleNum,mTvTitleName,mTvTitleCountYq,mTvTitleTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zx_credit_record_detail);
        SetTitleBar.setTitleText(ZxCreditRecordDetailActivity.this,"个人征信报告");
        reportId = getIntent().getStringExtra("reportId");
        creditId = getIntent().getStringExtra("creditId");

        downloadRecord();
    }

    private void downloadRecord(){
        Map map = new HashMap();
        map.put("creditId",creditId);
        map.put("reportId",reportId);
        RequestCall requestCall = MyOkHttpUtils.postRequest(Urls.ZX_CHAXUNSHUJU_RECORD_ITEM, map);
        requestCall.execute(new MapCallback(ZxCreditRecordDetailActivity.this) {
            @Override
            public void onResponse(Map responseMap) {
                super.onResponse(responseMap);
                if (responseMap!=null) {
                    Map<String, Object> attrMap = (Map<String, Object>) responseMap.get("attr");
                    List<Map<String,Object>> infoList = (List<Map<String, Object>>) attrMap.get("info");
                    creditRecordView(infoList);
                    Map<String,Object> reportMap = (Map<String, Object>) attrMap.get("report");
                    setTitleView(reportMap);
                    List<Map<String,Object>>queryList = (List<Map<String, Object>>) attrMap.get("query");
                    setQueryRecord(queryList);
                }
            }
        });
    }

    /**
     *  信用卡记录  购房贷款记录 其他贷款记录
     */
    private void creditRecordView(List<Map<String,Object>> infoList){
        for (int i= 0; i< infoList.size(); i++) {
            switch (infoList.get(i).get("type").toString()){
                case "1":
                    TextView mTvXykZhs = (TextView) findViewById(R.id.creditDel_tv_xykzhs);
                    TextView mTvXykWxh = (TextView) findViewById(R.id.creditDel_tv_xykwxh);
                    TextView mTvXykYqs = (TextView) findViewById(R.id.creditDel_tv_xykyqs);
                    TextView mTvXykDbs = (TextView) findViewById(R.id.creditDel_tv_xykdbs);
                    mTvXykZhs.setText(infoList.get(i).get("accountCount").toString());
                    mTvXykWxh.setText(infoList.get(i).get("uncleared").toString());
                    mTvXykYqs.setText(infoList.get(i).get("overdue").toString());
                    mTvXykDbs.setText(infoList.get(i).get("guarantee").toString());
                    break;
                case "2":
                    TextView mTvGfdkZhs = (TextView) findViewById(R.id.creditDel_tv_dkjlzhs);
                    TextView mTvGfdkWxh = (TextView) findViewById(R.id.creditDel_tv_dkjlwxh);
                    TextView mTvGfdkYqs = (TextView) findViewById(R.id.creditDel_tv_dkjlyqs);
                    TextView mTvGfdkDbs = (TextView) findViewById(R.id.creditDel_tv_dkjldbs);
                    mTvGfdkZhs.setText(infoList.get(i).get("accountCount").toString());
                    mTvGfdkWxh.setText(infoList.get(i).get("uncleared").toString());
                    mTvGfdkYqs.setText(infoList.get(i).get("overdue").toString());
                    mTvGfdkDbs.setText(infoList.get(i).get("guarantee").toString());
                    break;
                case "3":
                    TextView mTvQtdkZhs = (TextView) findViewById(R.id.creditDel_tv_qtdkzhs);
                    TextView mTvQtdkWxh = (TextView) findViewById(R.id.creditDel_tv_qtdkwxh);
                    TextView mTvQtdkYqs = (TextView) findViewById(R.id.creditDel_tv_qtdkyqs);
                    TextView mTvQtdkDbs = (TextView) findViewById(R.id.creditDel_tv_qtdkdbs);
                    mTvQtdkZhs.setText(infoList.get(i).get("accountCount").toString());
                    mTvQtdkWxh.setText(infoList.get(i).get("uncleared").toString());
                    mTvQtdkYqs.setText(infoList.get(i).get("overdue").toString());
                    mTvQtdkDbs.setText(infoList.get(i).get("guarantee").toString());
                    break;
            }
        }

        for (int i = 0; i< infoList.size() ; i++){
            overdue += Integer.parseInt(infoList.get(i).get("overdue").toString());
        }

    }
    //公共记录
    private void setCommonRecord(){
        TextView mTvQsjl = (TextView) findViewById(R.id.creditDel_tv_ggjlzhs);
        TextView mTvMspdjl = (TextView) findViewById(R.id.creditDel_tv_ggjlwxh);
        TextView mTvQzzxjl = (TextView) findViewById(R.id.creditDel_tv_ggjlyqs);
        TextView mTvXzcfjl = (TextView) findViewById(R.id.creditDel_tv_ggjlxz);
        TextView mTvDxqfjl = (TextView) findViewById(R.id.creditDel_tv_ggjldbs);
    }
    //查询记录
    private void setQueryRecord( List<Map<String,Object>>queryList){
        TextView mTvQsjl = (TextView) findViewById(R.id.creditDel_tv_cxjlzhs);
        TextView mTvMspdjl = (TextView) findViewById(R.id.creditDel_tv_cxjlwxh);
        TextView mTvQzzxjl = (TextView) findViewById(R.id.creditDel_tv_cxjlgrcx);
        TextView mTvXzcfjl = (TextView) findViewById(R.id.creditDel_tv_cxjlyqs);
        TextView mTvDxqfjl = (TextView) findViewById(R.id.creditDel_tv_cxjldbs);
        for (int i = 0; i<queryList.size() ; i++) {
            switch (queryList.get(i).get("type").toString()){
                case "1":
                    mTvQsjl.setText(queryList.get(i).get("count1").toString());
                    break;
                case "2":
                    mTvMspdjl.setText(queryList.get(i).get("count1").toString());
                    break;
                case "3":
                    mTvQzzxjl.setText(queryList.get(i).get("count1").toString());
                    break;
                case "4":
                    mTvXzcfjl.setText(queryList.get(i).get("count1").toString());
                    break;
                case "5":
                    mTvDxqfjl.setText(queryList.get(i).get("count1").toString());
                    break;
            }
        }
    }

    /**
     * 标题部分的  逾期次数是累加的
     */
    private  void setTitleView(Map<String,Object> reportMap){
        mTvTitleNum = (TextView) findViewById(R.id.creditdel_tv_number);
        mTvTitleName = (TextView) findViewById(R.id.creditdel_tv_name);
        mTvTitleCountYq = (TextView) findViewById(R.id.creditdel_tv_countYq);
        mTvTitleTime = (TextView) findViewById(R.id.creditdel_tv_createTime);

        mTvTitleNum.setText(TextUtil.getTextToString(reportMap.get("reportNo")));
        mTvTitleName.setText(TextUtil.getTextToString(reportMap.get("realName")));
        mTvTitleCountYq.setText(overdue+"");
        mTvTitleTime.setText(TextUtil.getTextToString(reportMap.get("createTime")));

    }


}
