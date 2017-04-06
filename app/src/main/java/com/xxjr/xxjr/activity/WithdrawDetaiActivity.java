package com.xxjr.xxjr.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.xxjr.xxjr.R;
import com.xxjr.xxjr.adapter.WithdrawDetailAdapter;
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

public class WithdrawDetaiActivity extends SlidBackActivity {

    private ListView mLvWithdrawDetail;
    private ImageView mIvCheckIcon;
    private List<Map<String,Object>> mlist = new ArrayList<>();
    private WithdrawDetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_detai);
        SetTitleBar.setTitleText(WithdrawDetaiActivity.this,  "提现明细");
        initViews();
        lvSetAdapter();

        downLoadDatas(Urls.CUST_PERFORM_WITHDRAWdETAIL);

        judgeCheckCount(mLvWithdrawDetail,mIvCheckIcon);
    }

    private void initViews() {
        mLvWithdrawDetail = (ListView) findViewById(R.id.WithdrawDetail_lv_detail);
        mIvCheckIcon = (ImageView) findViewById(R.id.WithdrawDetail_iv_checkIcon);
    }

    private void lvSetAdapter() {
        adapter = new WithdrawDetailAdapter(getApplicationContext(),mlist);
        mLvWithdrawDetail.setAdapter(adapter);
    }

    /**
     * 判断是否有数据，没有就显示图标
     */
    private void judgeCheckCount(ListView listView,ImageView imageView){
        if (listView.getCount()!=0){
            imageView.setVisibility(View.GONE);
        }else {
            imageView.setVisibility(View.VISIBLE);
        }
    }

    //下载
    private  void downLoadDatas(String url){
        AppUtil.showProgress(this, ConstantUtils.DIALOG_SHOW);
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil(this);
        httpRequestUtil.jsonObjectRequestPostSuccess(
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> responseMap = JsonUtil.getInstance().json2Object(response.toString(), Map.class);
                        boolean success = (Boolean) responseMap.get("success");
                        if (success) {
                            List<Map<String,Object>> rows = (List<Map<String, Object>>) responseMap.get("rows");
                            mlist.addAll(rows);
                            adapter.notifyDataSetChanged();
                            judgeCheckCount(mLvWithdrawDetail,mIvCheckIcon);
                        } else {

                        }
                        AppUtil.dismissProgress();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AppUtil.errodDoanload(ConstantUtils.DIALOG_ERROR);
                    }
                }
        );
    }


}
