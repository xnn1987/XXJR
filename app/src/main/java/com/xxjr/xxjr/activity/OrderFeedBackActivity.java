package com.xxjr.xxjr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.adapter.OrderFeedBackAdapter;
import com.xxjr.xxjr.constant.ConstantUtils;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.utils.network.AppUtil;
import com.xxjr.xxjr.utils.network.HttpRequestUtil;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.common.SlidBackActivity;

import org.ddq.common.util.JsonUtil;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderFeedBackActivity extends SlidBackActivity {

    private ListView mLvFeedback;
    private int dtlId;
    private List<Map<String,Object>> mlist = new ArrayList<>();
    private OrderFeedBackAdapter adapter;
    private int status;
    private String orgName = "";
    private TextView mTvOrgName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_feed_back);
        SetTitleBar.setTitleText(this, ConstantUtils.TITLE_BAR_FEEDBACK);
        initView();
        LvAddView();
        getIntentDatas();
        setLvAdapter();
        downLoadDatas(Urls.ORDER_FEEDBACK);
    }



    public void getIntentDatas() {
        Intent intent = getIntent();
        dtlId = intent.getIntExtra("dtlId", 0);
        status = intent.getIntExtra("status", 0);
        orgName = intent.getStringExtra("orgName");
        mTvOrgName.setText(orgName);
    }

    private void initView() {
        mLvFeedback = (ListView) findViewById(R.id.OrderFeedBack_lv);
    }
    private void LvAddView(){
        View mViewProduce = LayoutInflater.from(OrderFeedBackActivity.this).inflate(R.layout.order_feedback_addhead,null);
        mTvOrgName = (TextView) mViewProduce.findViewById(R.id.OrderFeedback_tv_companyProduce);
        mLvFeedback.addHeaderView(mViewProduce);
    }
    private void setLvAdapter(){
        adapter = new OrderFeedBackAdapter(getApplicationContext(),mlist);
        mLvFeedback.setAdapter(adapter);
    }


    private void downLoadDatas(String url) {
        AppUtil.showProgress(this, "正在反馈，请稍候...");
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        Map<String, String> params = httpRequestUtil.params;
        params.put("dtlId",dtlId+"");
        httpRequestUtil.jsonObjectRequestPostSuccess(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String,Object> map = JsonUtil.getInstance().json2Object(response.toString(),Map.class);
                        List<Map<String,Object>> rowsList= (List<Map<String, Object>>) map.get("rows");

//                        Gson gson = new Gson();
//                        OrderFeedBackBean orderFeedBackBean = new OrderFeedBackBean();
//                        orderFeedBackBean = gson.fromJson(response.toString(), OrderFeedBackBean.class);
//                        List<OrderFeedBackBean.RowsEntity> rows = orderFeedBackBean.getRows();
                        mlist.addAll(rowsList);
                        adapter.notifyDataSetChanged();
                        AppUtil.dismissProgress();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AppUtil.errodDoanload("网络不稳定");
                    }
                });
    }


   /* private void setListener() {
        mBtnCancel.setOnClickListener(new View.OnClickListener() {//撤单
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LeaveMessageActivity.class);
                intent.putExtra("dtlId", dtlId);
                startActivityForResult(intent, 1);
            }
        });

    }*/

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         mBtnCancel.setText("已撤单");
        mBtnCancel.setClickable(false);
    }*/

   /* private void judgeStatus() {
        if (status ==4){
            mBtnCancel.setText("已撤单");
            mBtnCancel.setClickable(false);
        }else if (status==5){
            mBtnCancel.setText("放款完成");
            mBtnCancel.setClickable(false);
        }
    }*/
}
