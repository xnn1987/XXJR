package com.xxjr.xxjr.activity.zx;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xxjr.xxjr.R;
import com.xxjr.xxjr.constant.Urls;
import com.xxjr.xxjr.utils.SetTitleBar;
import com.xxjr.xxjr.utils.TextUtil;
import com.xxjr.xxjr.utils.common.SlidBackActivity;
import com.xxjr.xxjr.utils.network.MapCallback;
import com.xxjr.xxjr.utils.network.MyOkHttpUtils;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.List;
import java.util.Map;

public class ZxCustManageActivity extends SlidBackActivity {

    private LinearLayout mLlAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zx_cust_manage);
        SetTitleBar.setTitleText(ZxCustManageActivity.this,"央行信用报告用户管理");
        mLlAdd = (LinearLayout) findViewById(R.id.zxDredge_ll_addAccound);
        downLoadData();

    }

    private void downLoadData(){
        RequestCall requestCall = MyOkHttpUtils.postRequest(Urls.ZX_CHAXUNSHUJU, null);
        requestCall.execute(new MapCallback(ZxCustManageActivity.this) {
            @Override
            public void onResponse(Map responseMap) {
                super.onResponse(responseMap);
                if (responseMap != null){
                    Map<String,Object> attrMap = (Map<String, Object>) responseMap.get("attr");
                    final List<Map<String,Object>> personList = (List<Map<String, Object>>) attrMap.get("personList");
                    for (int i=0; i< personList.size(); i++){
                        View mItem = getLayoutInflater().inflate(R.layout.zx_dredge_item,null);
                        TextView mTvName  = (TextView) mItem.findViewById(R.id.zxDredge_tv_name);
                        TextView mTvCount = (TextView) mItem.findViewById(R.id.zxDredge_tv_count);
                        mTvName.setText(TextUtil.getTextToString(personList.get(i).get("realName")));
                        String count = TextUtil.getTextToString(personList.get(i).get("count"));
                        final String finalCount = count;
                        if (!TextUtils.isEmpty(count) && !count.equals("0")){
                            mTvCount.setText(count+"份征信报告");
                        }
                        final int finalI = i;
                        mTvCount.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!TextUtils.isEmpty(finalCount) && !finalCount.equals("0")){
                                    Intent intent = new Intent(getApplicationContext(),ZxCreditRecordActivity.class);
                                    intent.putExtra("creditId",personList.get(finalI).get("creditId").toString());
                                    startActivity(intent);
                                }
                            }
                        });

                        mLlAdd.addView(mItem);

                    }
                }
            }
        });

    }
}
